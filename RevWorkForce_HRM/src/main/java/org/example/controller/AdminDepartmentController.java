package org.example.controller;
import org.example.models.Department;
import org.example.service.AdminDepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class AdminDepartmentController {

        private static final Logger log = LoggerFactory.getLogger(AdminDepartmentController.class);

        private final AdminDepartmentService service =
                new AdminDepartmentService();

        private final Scanner sc = new Scanner(System.in);

    public void addDepartment() {

        sc.nextLine(); // consume leftover newline
        log.info("Enter Department Name:");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            log.warn("Department name cannot be empty");
            return;
        }

        boolean success = service.addDepartment(name);

        if (success) {
            log.info("Department added successfully");
        } else {
            log.warn("Failed to add department");
        }
    }


    // View all departments
        public void viewDepartments() {

            List<Department> departments = service.getAllDepartments();

            if (departments.isEmpty()) {
                log.info("No departments found");
                return;
            }

            log.info("===== DEPARTMENTS =====");
            for (Department d : departments) {
                log.info("ID={} | Name={}",
                        d.getDepartmentId(),
                        d.getDepartmentName());
            }
        }
    }


