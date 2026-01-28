package org.example.Dao;
import org.example.Config.DBconnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminEmployeeAssignmentDAO {

        private static final Logger log = LoggerFactory.getLogger(AdminEmployeeAssignmentDAO.class);

        private static final String ASSIGN_DEPT_DESIG_SQL =
                "UPDATE employee " +
                        "SET department_id = ?, designation_id = ? " +
                        "WHERE employee_id = ?";

        public boolean assignDepartmentAndDesignation(
                int employeeId,
                int departmentId,
                int designationId
        ) {

            try (Connection conn = DBconnection.getInstance();
                 PreparedStatement ps =
                         conn.prepareStatement(ASSIGN_DEPT_DESIG_SQL)) {

                ps.setInt(1, departmentId);
                ps.setInt(2, designationId);
                ps.setInt(3, employeeId);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    log.info(
                            "Assigned departmentId={} and designationId={} to employeeId={}",
                            departmentId, designationId, employeeId
                    );
                    return true;
                }

                log.warn(
                        "Assignment failed. employeeId={} not found",
                        employeeId
                );
                return false;

            } catch (SQLException e) {
                log.error(
                        "Error assigning department/designation. employeeId={}",
                        employeeId, e
                );
                throw new RuntimeException(e);
            }
        }
    }


