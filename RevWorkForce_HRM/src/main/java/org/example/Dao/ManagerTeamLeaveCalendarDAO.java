package org.example.Dao;

import org.example.Config.DBconnection;
import org.example.models.LeaveApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManagerTeamLeaveCalendarDAO {

        private static final Logger log =
                LoggerFactory.getLogger(ManagerTeamLeaveCalendarDAO.class);

        private static final String TEAM_LEAVE_CALENDAR_SQL =
                "SELECT leave_app_id, employee_id, start_date, end_date, leave_type_id " +
                        "FROM leave_application " +
                        "WHERE manager_id = ? AND status = 'APPROVED' " +
                        "ORDER BY start_date";

        public List<LeaveApplication> getTeamLeaveCalendar(int managerId) {

            List<LeaveApplication> leaves = new ArrayList<>();

            try (Connection conn = DBconnection.getInstance();
                 PreparedStatement ps =
                         conn.prepareStatement(TEAM_LEAVE_CALENDAR_SQL)) {

                ps.setInt(1, managerId);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    LeaveApplication la = new LeaveApplication();
                    la.setLeaveAppId(rs.getInt("leave_app_id"));
                    la.setEmployeeId(rs.getInt("employee_id"));
                    la.setLeaveTypeId(rs.getInt("leave_type_id"));
                    la.setStartDate(rs.getDate("start_date").toLocalDate());
                    la.setEndDate(rs.getDate("end_date").toLocalDate());

                    leaves.add(la);
                }

                log.info("Fetched {} approved team leaves for managerId={}",
                        leaves.size(), managerId);

                return leaves;

            } catch (SQLException e) {
                log.error("Error fetching team leave calendar for managerId={}",
                        managerId, e);
                throw new RuntimeException(e);
            }
        }
    }


