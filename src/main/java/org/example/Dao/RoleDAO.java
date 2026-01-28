package org.example.Dao;

import org.example.Config.DBconnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDAO {

    private static final Logger log =
            LoggerFactory.getLogger(RoleDAO.class);

    private static final String ROLE_SQL =
            "SELECT r.role_name " +
                    "FROM employee e " +
                    "JOIN role r ON e.role_id = r.role_id " +
                    "WHERE e.employee_id = ?";

    public String getRoleNameByEmployeeId(int employeeId) {

        try (Connection conn = DBconnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(ROLE_SQL)) {

            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String roleName = rs.getString("role_name");
                log.info("Role fetched for employeeId={} : {}", employeeId, roleName);
                return roleName;
            }

            log.warn("No role found for employeeId={}", employeeId);
            return null;

        } catch (SQLException e) {
            log.error("Error fetching role for employeeId={}", employeeId, e);
            throw new RuntimeException(e);
        }
    }
}

