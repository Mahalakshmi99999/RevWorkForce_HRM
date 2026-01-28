package org.example.Dao;

import org.example.Config.DBconnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminAssignManagerDAO {

        private static final Logger log = LoggerFactory.getLogger(AdminAssignManagerDAO.class);

        private static final String UPDATE_MANAGER_SQL =
                "UPDATE employee SET manager_id = ? WHERE employee_id = ?";

        public boolean assignManager(int employeeId, int managerId) {

            try (Connection conn = DBconnection.getInstance();
                 PreparedStatement ps = conn.prepareStatement(UPDATE_MANAGER_SQL)) {

                ps.setInt(1, managerId);
                ps.setInt(2, employeeId);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    log.info("Manager assigned. employeeId={}, managerId={}",
                            employeeId, managerId);
                    return true;
                }

                log.warn("Manager assignment failed. employeeId={}, managerId={}",
                        employeeId, managerId);
                return false;

            } catch (SQLException e) {
                log.error("Error assigning manager. employeeId={}, managerId={}",
                        employeeId, managerId, e);
                throw new RuntimeException(e);
            }
        }
    }


