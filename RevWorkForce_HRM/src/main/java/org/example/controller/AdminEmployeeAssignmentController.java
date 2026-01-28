package org.example.controller;
import org.example.service.AdminEmployeeAssignmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;


public class AdminEmployeeAssignmentController {

        private static final Logger log = LoggerFactory.getLogger(AdminEmployeeAssignmentController.class);

        private final AdminEmployeeAssignmentService assignmentService;
        private final Scanner sc = new Scanner(System.in);

        public AdminEmployeeAssignmentController() {
            this.assignmentService = new AdminEmployeeAssignmentService();
        }

        public void assignDepartmentAndDesignation() {

            log.info("Enter Employee ID:");
            int employeeId = sc.nextInt();

            log.info("Enter Department ID:");
            int departmentId = sc.nextInt();

            log.info("Enter Designation ID:");
            int designationId = sc.nextInt();

            boolean success =
                    assignmentService.assignDepartmentAndDesignation(
                            employeeId,
                            departmentId,
                            designationId
                    );

            if (success) {
                log.info("Department & Designation assigned successfully");
            } else {
                log.warn("Failed to assign Department & Designation");
            }
        }
    }


