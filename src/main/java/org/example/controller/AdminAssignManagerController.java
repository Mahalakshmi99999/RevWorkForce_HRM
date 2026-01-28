package org.example.controller;

import org.example.service.AdminAssignManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class AdminAssignManagerController {

        private static final Logger log = LoggerFactory.getLogger(AdminAssignManagerController.class);

        private final AdminAssignManagerService service;
        private final Scanner sc;

        public AdminAssignManagerController() {
            this.service = new AdminAssignManagerService();
            this.sc = new Scanner(System.in);
        }

        public void assignManager() {

            log.info("Enter Employee ID:");
            int employeeId = sc.nextInt();

            log.info("Enter Manager ID:");
            int managerId = sc.nextInt();

            boolean success = service.assignManager(employeeId, managerId);

            if (success) {
                log.info("Manager assigned successfully");
            } else {
                log.warn("Manager assignment failed");
            }
        }
    }


