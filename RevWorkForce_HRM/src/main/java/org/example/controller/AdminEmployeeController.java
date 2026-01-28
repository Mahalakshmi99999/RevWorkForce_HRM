package org.example.controller;

import org.example.models.Employee;
import org.example.service.AdminEmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AdminEmployeeController {

        private static final Logger log = LoggerFactory.getLogger(AdminEmployeeController.class);

        private final AdminEmployeeService adminEmployeeService;

        public AdminEmployeeController() {
            this.adminEmployeeService = new AdminEmployeeService();
        }

        public void viewAllEmployees() {

            List<Employee> employees = adminEmployeeService.getAllEmployees();

            if (employees.isEmpty()) {
                log.info("No employees found.");
                return;
            }

            log.info("===== ALL EMPLOYEES =====");

            for (Employee emp : employees) {
                log.info(
                        "ID={} | Name={} {} | Email={} | Status={} | RoleId={}",
                        emp.getEmployeeId(),
                        emp.getFirstName(),
                        emp.getLastName(),
                        emp.getEmail(),
                        emp.getStatus(),
                        emp.getRoleId()
                );
            }
        }

    // to add employee
    public void addEmployee() {

        Scanner sc = new Scanner(System.in);

        Employee emp = new Employee();

        log.info("Enter First Name:");
        emp.setFirstName(sc.nextLine());

        log.info("Enter Last Name:");
        emp.setLastName(sc.nextLine());

        log.info("Enter Email:");
        emp.setEmail(sc.nextLine());

        log.info("Enter Phone:");
        emp.setPhone(sc.nextLine());

        log.info("Enter Address:");
        emp.setAddress(sc.nextLine());

        log.info("Enter Date of Birth (yyyy-mm-dd):");
        emp.setDob(LocalDate.parse(sc.nextLine()));

        log.info("Enter Salary:");
        emp.setSalary(new BigDecimal(sc.nextLine()));


        emp.setStatus("ACTIVE");
        emp.setJoiningDate(LocalDate.now());

        log.info("Enter Role ID (1=EMPLOYEE, 2=MANAGER, 3=ADMIN):");
        emp.setRoleId(Integer.parseInt(sc.nextLine()));

        boolean success = adminEmployeeService.addEmployee(emp);

        if (success) {
            log.info("Employee added successfully.");
        } else {
            log.warn("Failed to add employee.");
        }
    }

}

