package org.example.Dao;

import org.example.Config.DBconnection;
import org.example.models.LeaveApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;

import java.util.List;
import java.util.ArrayList;

public class LeaveApplicationDAO {

    private static final Logger log = LoggerFactory.getLogger(LeaveApplicationDAO.class);

    private static final String INSERT_SQL =
            "INSERT INTO leave_application " +
                    "(employee_id, manager_id, leave_type_id, start_date, end_date, " +
                    "working_days, reason, status, applied_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, 'PENDING', ?)";

    public boolean applyLeave(LeaveApplication leave) {

        try (Connection conn = DBconnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            ps.setInt(1, leave.getEmployeeId());
            ps.setInt(2, leave.getManagerId());
            ps.setInt(3, leave.getLeaveTypeId());
            ps.setDate(4, Date.valueOf(leave.getStartDate()));
            ps.setDate(5, Date.valueOf(leave.getEndDate()));
            ps.setInt(6, leave.getWorkingDays());
            ps.setString(7, leave.getReason());
            ps.setDate(8, Date.valueOf(leave.getAppliedDate()));

            int rows = ps.executeUpdate();

            if (rows > 0) {
                log.info("Leave applied successfully for employeeId={}",
                        leave.getEmployeeId());
                return true;
            }

            log.warn("Leave application failed for employeeId={}",
                    leave.getEmployeeId());
            return false;

        } catch (SQLException e) {
            log.error("Error applying leave for employeeId={}",
                    leave.getEmployeeId(), e);
            throw new RuntimeException(e);
        }
    }

    public List<LeaveApplication> findByEmployeeId(int employeeId) {

        String SQL =
                "SELECT leave_app_id, employee_id, manager_id, leave_type_id, " +
                        "start_date, end_date, working_days, reason, status, applied_date, manager_comment " +
                        "FROM leave_application WHERE employee_id = ? ORDER BY applied_date DESC";

        List<LeaveApplication> leaves = new ArrayList<>();

        try (Connection conn = DBconnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(SQL)) {

            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LeaveApplication la = new LeaveApplication();
                la.setLeaveAppId(rs.getInt("leave_app_id"));
                la.setEmployeeId(rs.getInt("employee_id"));
                la.setManagerId(rs.getInt("manager_id"));
                la.setLeaveTypeId(rs.getInt("leave_type_id"));
                la.setStartDate(rs.getDate("start_date").toLocalDate());
                la.setEndDate(rs.getDate("end_date").toLocalDate());
                la.setWorkingDays(rs.getInt("working_days"));
                la.setReason(rs.getString("reason"));
                la.setStatus(rs.getString("status"));
                la.setAppliedDate(rs.getDate("applied_date").toLocalDate());
                la.setManagerComment(rs.getString("manager_comment"));

                leaves.add(la);
            }

            log.info("Fetched {} leave records for employeeId={}", leaves.size(), employeeId);
            return leaves;

        } catch (SQLException e) {
            log.error("Error fetching leave status for employeeId={}", employeeId, e);
            throw new RuntimeException(e);
        }
    }

    // employee cancelling pending leave requests

    public List<LeaveApplication> findPendingLeavesByEmployee(int employeeId) {

        String SQL =
                "SELECT leave_app_id, employee_id, leave_type_id, start_date, end_date, " +
                        "working_days, reason, status, applied_date " +
                        "FROM leave_application " +
                        "WHERE employee_id = ? AND status = 'PENDING' " +
                        "ORDER BY applied_date DESC";

        List<LeaveApplication> pendingLeaves = new ArrayList<>();

        try (Connection conn = DBconnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(SQL)) {

            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LeaveApplication la = new LeaveApplication();
                la.setLeaveAppId(rs.getInt("leave_app_id"));
                la.setEmployeeId(rs.getInt("employee_id"));
                la.setLeaveTypeId(rs.getInt("leave_type_id"));
                la.setStartDate(rs.getDate("start_date").toLocalDate());
                la.setEndDate(rs.getDate("end_date").toLocalDate());
                la.setWorkingDays(rs.getInt("working_days"));
                la.setReason(rs.getString("reason"));
                la.setStatus(rs.getString("status"));
                la.setAppliedDate(rs.getDate("applied_date").toLocalDate());

                pendingLeaves.add(la);
            }

            log.info("Fetched {} pending leaves for employeeId={}",
                    pendingLeaves.size(), employeeId);

            return pendingLeaves;

        } catch (SQLException e) {
            log.error("Error fetching pending leaves for employeeId={}",
                    employeeId, e);
            throw new RuntimeException(e);
        }
    }

    // this is to cancel pending leave by employee
    public boolean cancelPendingLeave(int leaveAppId, int employeeId) {

        String SQL =
                "UPDATE leave_application " +
                        "SET status = 'CANCELLED' " +
                        "WHERE leave_app_id = ? " +
                        "AND employee_id = ? " +
                        "AND status = 'PENDING'";

        try (Connection conn = DBconnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(SQL)) {

            ps.setInt(1, leaveAppId);
            ps.setInt(2, employeeId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                log.info("Leave cancelled successfully. leaveAppId={}, employeeId={}",
                        leaveAppId, employeeId);
                return true;
            }

            log.warn("Cancel failed (not PENDING or not owned). leaveAppId={}, employeeId={}",
                    leaveAppId, employeeId);
            return false;

        } catch (SQLException e) {
            log.error("Error cancelling leave. leaveAppId={}, employeeId={}",
                    leaveAppId, employeeId, e);
            throw new RuntimeException(e);
        }
    }


    // manger will fetch leaves
    private static final String FETCH_PENDING_FOR_MANAGER_SQL =
            "SELECT leave_app_id, employee_id, manager_id, leave_type_id, " +
                    "start_date, end_date, working_days, reason, status, applied_date, manager_comment " +
                    "FROM leave_application " +
                    "WHERE manager_id = ? AND status = 'PENDING'";

    public List<LeaveApplication> getPendingLeavesForManager(int managerId) {

        List<LeaveApplication> leaves = new ArrayList<>();

        try (Connection conn = DBconnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(FETCH_PENDING_FOR_MANAGER_SQL)) {

            ps.setInt(1, managerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LeaveApplication leave = mapRowToLeaveApplication(rs);
                leaves.add(leave);
            }

            log.info("Fetched {} pending leave requests for managerId={}",
                    leaves.size(), managerId);

        } catch (SQLException e) {
            log.error("Error fetching pending leaves for managerId={}", managerId, e);
            throw new RuntimeException(e);
        }

        return leaves;
    }

    private LeaveApplication mapRowToLeaveApplication(ResultSet rs) throws SQLException {

        LeaveApplication leave = new LeaveApplication();

        leave.setLeaveAppId(rs.getInt("leave_app_id"));
        leave.setEmployeeId(rs.getInt("employee_id"));
        leave.setManagerId(rs.getInt("manager_id"));
        leave.setLeaveTypeId(rs.getInt("leave_type_id"));

        leave.setStartDate(rs.getDate("start_date").toLocalDate());
        leave.setEndDate(rs.getDate("end_date").toLocalDate());

        leave.setWorkingDays(rs.getInt("working_days"));
        leave.setReason(rs.getString("reason"));
        leave.setStatus(rs.getString("status"));

        if (rs.getDate("applied_date") != null) {
            leave.setAppliedDate(rs.getDate("applied_date").toLocalDate());
        }

        leave.setManagerComment(rs.getString("manager_comment"));

        return leave;
    }
    // to fetch pending leaves for manager

    public List<LeaveApplication> findPendingByManagerId(int managerId) {

        String sql = """
        
                SELECT leave_app_id, employee_id, manager_id, leave_type_id,
               start_date, end_date, working_days, reason,
               status, applied_date, manager_comment
        FROM leave_application
        WHERE manager_id = ? AND status = 'PENDING'
        """;

        List<LeaveApplication> leaves = new ArrayList<>();

        try (Connection conn = DBconnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, managerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LeaveApplication leave = mapRowToLeaveApplication(rs);
                leaves.add(leave);
            }

            log.info("Fetched {} pending leaves for managerId={}", leaves.size(), managerId);
            return leaves;

        } catch (SQLException e) {
            log.error("Error fetching pending leaves for managerId={}", managerId, e);
            throw new RuntimeException(e);
        }
    }
    // manager approve leave
    public boolean approveLeave(int leaveAppId, String managerComment) {

        String sql =
                """
                UPDATE leave_application
                SET
                    status = 'APPROVED',
                    manager_comment = ?
                WHERE leave_app_id = ?
                  AND status = 'PENDING'
                """;

        try (Connection conn = DBconnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, managerComment);
            ps.setInt(2, leaveAppId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                log.info("Leave approved for leaveAppId={}", leaveAppId);
                return true;
            }

            log.warn("Approve failed. Leave not pending or not found. leaveAppId={}", leaveAppId);
            return false;

        } catch (SQLException e) {
            log.error("Error approving leave leaveAppId={}", leaveAppId, e);
            throw new RuntimeException(e);
        }
    }

    //manager reject leave

    public boolean rejectLeave(int leaveAppId, String managerComment) {

        String sql =
                """
        UPDATE
                leave_application
        SET
                status = 'REJECTED',
            manager_comment = ?
        WHERE leave_app_id = ? AND status = 'PENDING'
        """;

        try (Connection conn = DBconnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, managerComment);
            ps.setInt(2, leaveAppId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                log.info("Leave rejected for leaveAppId={}", leaveAppId);
                return true;
            }

            log.warn("Reject failed. Leave not pending or not found. leaveAppId={}", leaveAppId);
            return false;

        } catch (SQLException e) {
            log.error("Error rejecting leave leaveAppId={}", leaveAppId, e);
            throw new RuntimeException(e);
        }
    }

    //finfing using leaveappid
    public LeaveApplication findByLeaveAppId(int leaveAppId) {

        String sql =
                "SELECT leave_app_id, employee_id, manager_id, leave_type_id, " +
                        "start_date, end_date, working_days, reason, status, applied_date, manager_comment " +
                        "FROM leave_application " +
                        "WHERE leave_app_id = ?";

        try (Connection conn = DBconnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, leaveAppId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                LeaveApplication leave = mapRowToLeaveApplication(rs);
                log.info("Leave fetched for leaveAppId={}", leaveAppId);
                return leave;
            }

            log.warn("No leave found for leaveAppId={}", leaveAppId);
            return null;

        } catch (SQLException e) {
            log.error("Error fetching leave for leaveAppId={}", leaveAppId, e)
;
            throw new RuntimeException(e);
        }
    }
}