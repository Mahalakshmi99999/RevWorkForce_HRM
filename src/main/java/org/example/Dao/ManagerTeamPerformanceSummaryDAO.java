package org.example.Dao;

import org.example.Config.DBconnection;
import org.example.dto.TeamPerformanceSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class ManagerTeamPerformanceSummaryDAO {

    private static final Logger log =
            LoggerFactory.getLogger(ManagerTeamPerformanceSummaryDAO.class);

    private static final String TEAM_PERFORMANCE_SQL =
            """
            SELECT 
                e.employee_id,
                e.first_name,
                e.last_name,
                pr.review_year,
                pr.self_rating,
                pr.manager_rating,
                pr.status
            FROM employee e
            JOIN performance_review pr
                ON e.employee_id = pr.employee_id
            WHERE e.manager_id = ?
              AND pr.manager_rating IS NOT NULL
            ORDER BY pr.review_year DESC
            """;

    public List<TeamPerformanceSummary> fetchTeamPerformanceSummary(int managerId) {

        List<TeamPerformanceSummary> list = new ArrayList<>();

        try (Connection conn = DBconnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(TEAM_PERFORMANCE_SQL)) {

            ps.setInt(1, managerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TeamPerformanceSummary dto = new TeamPerformanceSummary();

                dto.setEmployeeId(rs.getInt("employee_id"));
                dto.setEmployeeName(
                        rs.getString("first_name") + " " + rs.getString("last_name")
                );
                dto.setReviewYear(rs.getInt("review_year"));
                dto.setSelfRating(rs.getInt("self_rating"));
                dto.setManagerRating(rs.getInt("manager_rating"));
                dto.setStatus(rs.getString("status"));

                list.add(dto);
            }

            log.info("DAO: {} team performance records fetched for managerId={}",
                    list.size(), managerId);

            return list;

        } catch (Exception e) {
            log.error("Error fetching team performance summary", e);
            throw new RuntimeException(e);
        }
    }
}
