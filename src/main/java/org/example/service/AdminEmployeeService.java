package org.example.service;


import org.example.service.AuditLogService;
import org.example.models.LoggedInUserContext;

import org.example.Dao.AdminEmployeeDAO;
import org.example.Dao.LoginDAO;
import org.example.models.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AdminEmployeeService {

    private static final Logger log =
            LoggerFactory.getLogger(AdminEmployeeService.class);

    private final AdminEmployeeDAO adminEmployeeDAO;
    private final LoginDAO loginDAO;
    private final AuditLogService auditLogService;


    public AdminEmployeeService() {
        this.adminEmployeeDAO = new AdminEmployeeDAO();
        this.loginDAO = new LoginDAO();
        this.auditLogService = new AuditLogService();
    }

    public List<Employee> getAllEmployees() {

        List<Employee> employees = adminEmployeeDAO.findAllEmployees();

        log.info("Service returning {} employees to admin", employees.size());
        return employees;
    }

    public boolean addEmployee(Employee employee) {

        int employeeId = adminEmployeeDAO.addEmployeeAndReturnId(employee);

        if (employeeId == -1) {
            log.error("Service: Employee creation failed. email={}",
                    employee.getEmail());
            return false;
        }

        String defaultPassword = "welcome123";

        boolean loginCreated =
                loginDAO.createLoginCredentials(employeeId, defaultPassword);

        if (!loginCreated) {
            log.error("Service: Employee created but login failed. employeeId={}",
                    employeeId);
            return false;
        }


        auditLogService.logAction(
                LoggedInUserContext.getEmployeeId(),
                "ADD_EMPLOYEE",
                "Employee created with ID=" + employeeId
        );

        log.info("Service: Employee and login created successfully. employeeId={}",
                employeeId);

        return true;
    }
}
