package org.example.service;

import org.example.Dao.AdminAssignManagerDAO;
import org.example.models.LoggedInUserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminAssignManagerService {

    private static final Logger log =
            LoggerFactory.getLogger(AdminAssignManagerService.class);

    private final AdminAssignManagerDAO assignManagerDAO;
    private final AuditLogService auditLogService;

    public AdminAssignManagerService() {
        this.assignManagerDAO = new AdminAssignManagerDAO();
        this.auditLogService = new AuditLogService();
    }

    public boolean assignManager(int employeeId, int managerId) {

        // basic validation
        if (employeeId == managerId) {
            log.warn("Invalid manager assignment: employeeId == managerId ({})", employeeId);
            return false;
        }

        boolean result = assignManagerDAO.assignManager(employeeId, managerId);

        if (result) {


            auditLogService.logAction(
                    LoggedInUserContext.getEmployeeId(),
                    "ASSIGN_MANAGER",
                    "ManagerId=" + managerId + " assigned to EmployeeId=" + employeeId
            );

            log.info("Service: Manager assigned successfully. employeeId={}, managerId={}",
                    employeeId, managerId);

        } else {
            log.warn("Service: Manager assignment failed. employeeId={}, managerId={}",
                    employeeId, managerId);
        }

        return result;
    }
}
