package org.example.controller;

import org.example.models.Employee;
import org.example.models.LoggedInUserContext;
import org.example.service.EmployeeService;
import org.example.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class EmployeeController {

    private static final Logger log =
            LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService = new EmployeeService();

    private final LoginService loginService;

    public EmployeeController() {
        this.loginService = new LoginService();
    }


    private final Scanner sc = new Scanner(System.in);

    public void viewProfile() {

        int empId = LoggedInUserContext.getEmployeeId();
        Employee emp = employeeService.viewProfile(empId);

        if (emp == null) {
            log.warn("Employee profile not found for employeeId={}", empId);
            return;
        }

        log.info("===== EMPLOYEE PROFILE =====");
        log.info("Employee ID : {}", emp.getEmployeeId());
        log.info("Name        : {} {}", emp.getFirstName(), emp.getLastName());
        log.info("Email       : {}", emp.getEmail());
        log.info("Phone       : {}", emp.getPhone());
        log.info("Status      : {}", emp.getStatus());


        log.info("Department  : {}", emp.getDepartmentName());
        log.info("Designation : {}", emp.getDesignationName());
    }

    public void updateProfile() {

        int employeeId = LoggedInUserContext.getEmployeeId();

        log.info("Enter new phone number:");
        String phone = sc.next();

        sc.nextLine();

        log.info("Enter new address:");
        String address = sc.nextLine();

        boolean updated = employeeService.updateProfile(employeeId, phone, address);

        if (updated) {
            log.info("Profile updated successfully");
        } else {
            log.warn("Profile update failed");
        }
    }

    public void showChangePassword() {

        Scanner sc = new Scanner(System.in);

        int employeeId = LoggedInUserContext.getEmployeeId();

        System.out.print("Enter current password: ");
        String currentPassword = sc.nextLine();

        System.out.print("Enter new password: ");
        String newPassword = sc.nextLine();

        boolean success =
                loginService.changePassword(
                        employeeId,
                        currentPassword,
                        newPassword
                );

        if (success) {
            System.out.println("Password changed successfully");
        } else {
            System.out.println("Password change failed");
        }
    }

}
