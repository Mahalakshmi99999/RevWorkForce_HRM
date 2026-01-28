package org.example.Dao;

import org.example.Config.DBconnection;
import org.example.models.LeaveType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeaveTypeDAO {

    private static final Logger log =
            LoggerFactory.getLogger(LeaveTypeDAO.class);

    private static final String FETCH_ALL_SQL =
            "SELECT leave_type_id, leave_name, max_days_per_year " +
                    "FROM leave_type ORDER BY leave_name";


    public List<LeaveType> findAll() {

        List<LeaveType> types = new ArrayList<>();

        try (Connection conn = DBconnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(FETCH_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LeaveType lt = new LeaveType();
                lt.setLeaveTypeId(rs.getInt("leave_type_id"));
                lt.setLeaveName(rs.getString("leave_name"));
                lt.setMaxDaysPerYear(rs.getInt("max_days_per_year"));
                types.add(lt);
            }

            log.info("Fetched {} leave types", types.size());
            return types;

        } catch (SQLException e) {
            log.error("Error fetching leave types", e);
            throw new RuntimeException(e);
        }
    }
}

