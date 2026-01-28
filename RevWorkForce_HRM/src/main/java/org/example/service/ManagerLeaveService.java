package org.example.service;

import org.example.Dao.LeaveApplicationDAO;
import org.example.Dao.LeaveBalanceDAO;
import org.example.models.LeaveApplication;
import org.example.models.LoggedInUserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ManagerLeaveService {

    private static final Logger log =
            LoggerFactory.getLogger(ManagerLeaveService.class);

    private final LeaveApplicationDAO leaveApplicationDAO;
    private final LeaveBalanceDAO leaveBalanceDAO;
    private final NotificationService notificationService;
    private final AuditLogService auditLogService;

    public ManagerLeaveService() {
        this.leaveApplicationDAO = new LeaveApplicationDAO();
        this.leaveBalanceDAO = new LeaveBalanceDAO();
        this.notificationService = new NotificationService();
        this.auditLogService = new AuditLogService();
    }


    public boolean approveLeave(int leaveAppId, String managerComment) {

        LeaveApplication leave =
                leaveApplicationDAO.findByLeaveAppId(leaveAppId);

        if (leave == null) {
            log.warn("Leave not found. leaveAppId={}", leaveAppId);
            return false;
        }

        boolean approved =
                leaveApplicationDAO.approveLeave(leaveAppId, managerComment);

        if (!approved) {
            log.warn("Leave approval failed. leaveAppId={}", leaveAppId);
            return false;
        }

        boolean balanceUpdated = leaveBalanceDAO.deductLeaveBalance(
                leave.getEmployeeId(),
                leave.getLeaveTypeId(),
                leave.getWorkingDays()
        );

        if (!balanceUpdated) {
            log.error(
                    "Leave approved but balance deduction failed. leaveAppId={}",
                    leaveAppId
            );
            return false;
        }

        // Notification
        notificationService.notifyEmployee(
                leave.getEmployeeId(),
                "Your leave request (ID: " + leaveAppId + ") has been APPROVED.",
                "LEAVE_APPROVED"
        );


        auditLogService.logAction(
                LoggedInUserContext.getEmployeeId(),
                "APPROVE_LEAVE",
                "LeaveAppId=" + leaveAppId
        );

        log.info("Leave approved, balance updated, notification sent. leaveAppId={}",
                leaveAppId);

        return true;
    }

    // REJECT leave and notify
    public boolean rejectLeave(int leaveAppId, String managerComment) {

        LeaveApplication leave = leaveApplicationDAO.findByLeaveAppId(leaveAppId);

        if (leave == null) {
            log.warn("Leave not found. leaveAppId={}", leaveAppId);
            return false;
        }

        boolean rejected =
                leaveApplicationDAO.rejectLeave(leaveAppId, managerComment);

        if (!rejected) {
            log.warn("Leave rejection failed. leaveAppId={}", leaveAppId);
            return false;
        }

        // Notification
        notificationService.notifyEmployee(
                leave.getEmployeeId(),
                "Your leave request (ID: " + leaveAppId + ") has been REJECTED.",
                "LEAVE_REJECTED"
        );


        auditLogService.logAction(
                LoggedInUserContext.getEmployeeId(), // manager id
                "REJECT_LEAVE",
                "LeaveAppId=" + leaveAppId
        );

        log.info("Leave rejected and notification sent. leaveAppId={}",
                leaveAppId);

        return true;
    }


    public List<LeaveApplication> getPendingLeaves(int managerId) {

        List<LeaveApplication> leaves = leaveApplicationDAO.findPendingByManagerId(managerId);

        log.info("Fetched {} pending leaves for managerId={}",
                leaves.size(), managerId);

        return leaves;
    }
}
