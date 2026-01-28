package org.example.controller;

import org.example.service.AdminAssignLeaveBalanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class AdminAssignLeaveBalanceController {

        private static final Logger log = LoggerFactory.getLogger(AdminAssignLeaveBalanceController.class);

        private final AdminAssignLeaveBalanceService service;
        private final Scanner sc;

        public AdminAssignLeaveBalanceController() {
            this.service = new AdminAssignLeaveBalanceService();
            this.sc = new Scanner(System.in);
        }

        public void assignLeaveBalance() {

            log.info("Enter Employee ID:");
            int employeeId = sc.nextInt();

            log.info("Enter Leave Type ID:");
            int leaveTypeId = sc.nextInt();

            log.info("Enter Available Days:");
            int days = sc.nextInt();

            boolean success =
                    service.assignLeaveBalance(employeeId, leaveTypeId, days);

            if (success) {
                log.info("Leave balance assigned successfully");
            } else {
                log.warn("Leave balance assignment failed");
            }
        }
    }


