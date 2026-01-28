package org.example.Dao;
import org.example.Config.DBconnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminLeaveBalanceDAO {

        private static final Logger log =
                LoggerFactory.getLogger(AdminLeaveBalanceDAO.class);

        private static final String INSERT_LEAVE_BALANCE_SQL =
                "INSERT INTO leave_balance (employee_id, leave_type_id, available_days) " +
                        "VALUES (?, ?, ?)";

        public boolean assignLeaveBalance(int employeeId, int leaveTypeId, int days) {

            try (Connection conn = DBconnection.getInstance();
                 PreparedStatement ps = conn.prepareStatement(INSERT_LEAVE_BALANCE_SQL)) {

                ps.setInt(1, employeeId);
                ps.setInt(2, leaveTypeId);
                ps.setInt(3, days);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    log.info("Leave balance assigned. employeeId={}, leaveTypeId={}, days={}",
                            employeeId, leaveTypeId, days);
                    return true;
                }

                log.warn("Leave balance assignment failed. employeeId={}, leaveTypeId={}",
                        employeeId, leaveTypeId);
                return false;

            } catch (SQLException e) {
                log.error("Error assigning leave balance. employeeId={}, leaveTypeId={}",
                        employeeId, leaveTypeId, e);
                throw new RuntimeException(e);
            }
        }
    }


