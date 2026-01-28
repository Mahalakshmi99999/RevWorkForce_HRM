package org.example.service;

import org.example.Dao.AdminLeaveBalanceDAO;
import org.example.models.LoggedInUserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminAssignLeaveBalanceService {

    private static final Logger log =
            LoggerFactory.getLogger(AdminAssignLeaveBalanceService.class);

    private final AdminLeaveBalanceDAO dao;
    private final AuditLogService auditLogService;

    public AdminAssignLeaveBalanceService() {
        this.dao = new AdminLeaveBalanceDAO();
        this.auditLogService = new AuditLogService();
    }

    public boolean assignLeaveBalance(int employeeId, int leaveTypeId, int days) {

        if (days < 0) {
            log.warn("Invalid leave days: {}", days);
            return false;
        }

        boolean result = dao.assignLeaveBalance(employeeId, leaveTypeId, days);

        if (result) {


            auditLogService.logAction(
                    LoggedInUserContext.getEmployeeId(),   // admin id
                    "ASSIGN_LEAVE_BALANCE",
                    "EmployeeId=" + employeeId +
                            ", LeaveTypeId=" + leaveTypeId +
                            ", Days=" + days
            );

            log.info("Service: Leave balance assigned. empId={}, leaveTypeId={}, days={}",
                    employeeId, leaveTypeId, days);

        } else {
            log.warn("Service: Leave balance assignment failed. empId={}, leaveTypeId={}",
                    employeeId, leaveTypeId);
        }

        return result;
    }
}
