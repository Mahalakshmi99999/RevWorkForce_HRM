package org.example.Dao;

import org.example.Config.DBconnection;
import org.example.models.LeaveBalance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeaveBalanceDAO {

    private static final Logger log = LoggerFactory.getLogger(LeaveBalanceDAO.class);

    private static final String FIND_BY_EMP_AND_TYPE_SQL =
            "SELECT balance_id, employee_id, leave_type_id, available_days " +
                    "FROM leave_balance WHERE employee_id = ? AND leave_type_id = ?";

    public LeaveBalance findByEmployeeAndType(int employeeId, int leaveTypeId) {

        try (Connection conn = DBconnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_EMP_AND_TYPE_SQL)) {

            ps.setInt(1, employeeId);
            ps.setInt(2, leaveTypeId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                LeaveBalance lb = new LeaveBalance();
                lb.setBalanceId(rs.getInt("balance_id"));
                lb.setEmployeeId(rs.getInt("employee_id"));
                lb.setLeaveTypeId(rs.getInt("leave_type_id"));
                lb.setAvailableDays(rs.getInt("available_days"));

                log.info("Fetched leave balance for employeeId={}, leaveTypeId={}",
                        employeeId, leaveTypeId);
                return lb;
            }

            log.warn("No leave balance found for employeeId={}, leaveTypeId={}",
                    employeeId, leaveTypeId);
            return null;

        } catch (SQLException e) {
            log.error("Error fetching leave balance for employeeId={}, leaveTypeId={}",
                    employeeId, leaveTypeId, e);
            throw new RuntimeException(e);
        }
    }


    private static final String DEDUCT_LEAVE_BALANCE_SQL =
            "UPDATE leave_balance " +
                    "SET available_days = available_days - ? " +
                    "WHERE employee_id = ? " +
                    "AND leave_type_id = ? " +
                    "AND available_days >= ?";


    public boolean deductLeaveBalance(
            int employeeId,
            int leaveTypeId,
            int days
    ) {

        try (Connection conn = DBconnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(DEDUCT_LEAVE_BALANCE_SQL)) {

            ps.setInt(1, days);
            ps.setInt(2, employeeId);
            ps.setInt(3, leaveTypeId);
            ps.setInt(4, days);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                log.info(
                        "Leave balance deducted. empId={}, leaveTypeId={}, days={}",
                        employeeId, leaveTypeId, days
                );
                return true;
            }

            log.warn(
                    "Leave balance deduction failed. Insufficient balance or record missing. empId={}, leaveTypeId={}",
                    employeeId, leaveTypeId
            );
            return false;

        } catch (SQLException e) {
            log.error("Error deducting leave balance", e);
            throw new RuntimeException(e);
        }
    }

    public List<LeaveBalance> getLeaveBalanceByEmployeeId(int employeeId) {

        String sql =
                "SELECT balance_id, employee_id, leave_type_id, available_days " +
                        "FROM leave_balance " +
                        "WHERE employee_id = ?";

        List<LeaveBalance> balances = new ArrayList<>();

        try (Connection conn = DBconnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LeaveBalance balance = new LeaveBalance();
                balance.setBalanceId(rs.getInt("balance_id"));
                balance.setEmployeeId(rs.getInt("employee_id"));
                balance.setLeaveTypeId(rs.getInt("leave_type_id"));
                balance.setAvailableDays(rs.getInt("available_days"));

                balances.add(balance);
            }

            log.info("Fetched {} leave balance records for employeeId={}",
                    balances.size(), employeeId);

            return balances;

        } catch (SQLException e) {
            log.error("Error fetching leave balance for employeeId={}", employeeId, e);
            throw new RuntimeException(e);
        }
    }

}