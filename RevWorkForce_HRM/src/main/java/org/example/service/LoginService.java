package org.example.service;

import org.example.Dao.LoginDAO;
import org.example.Dao.EmployeeDAO;
import org.example.models.Employee;
import org.example.models.LoggedInUserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginService {

    private static final Logger log =
            LoggerFactory.getLogger(LoginService.class);

    private final LoginDAO loginDAO;
    private final EmployeeDAO employeeDAO;

    public LoginService() {
        this.loginDAO = new LoginDAO();
        this.employeeDAO = new EmployeeDAO();
    }


    public boolean login(int employeeId, String password) {

        // Validate credentials
        int result = loginDAO.validateLogin(employeeId, password);

        if (result == -1) {
            log.warn("Login failed for employeeId={}", employeeId);
            return false;
        }

        // Fetch employee + role + manager info
        Employee emp = employeeDAO.findEmployeeWithRole(employeeId);

        if (emp == null) {
            log.error("Login failed: unable to fetch role for employeeId={}", employeeId);
            return false;
        }

        // Set logged-in user context
        LoggedInUserContext.setEmployeeId(emp.getEmployeeId());
        LoggedInUserContext.setManagerId(emp.getManagerId());
        LoggedInUserContext.setRoleName(emp.getRoleName());

        log.info(
                "Login successful. empId={}, role={}, managerId={}",
                emp.getEmployeeId(),
                emp.getRoleName(),
                emp.getManagerId()
        );

        return true;
    }

    public boolean changePassword(
            int employeeId,
            String currentPassword,
            String newPassword) {

        String storedPassword = loginDAO.getPassword(employeeId);

        if (storedPassword == null) {
            System.out.println("User not found");
            return false;
        }

        if (!storedPassword.equals(currentPassword)) {
            System.out.println("Current password incorrect");
            return false;
        }

        return loginDAO.updatePassword(employeeId, newPassword);
    }

    public String fetchSecurityQuestion(int employeeId) {
        return loginDAO.getSecurityQuestion(employeeId);
    }


}
