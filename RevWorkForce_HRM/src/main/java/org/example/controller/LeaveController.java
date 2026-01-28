package org.example.controller;

import org.example.models.LeaveType;
import org.example.models.LoggedInUserContext;
import org.example.service.LeaveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class LeaveController {

    private static final Logger log =
            LoggerFactory.getLogger(LeaveController.class);

    private final LeaveService leaveService = new LeaveService();
    private final Scanner sc = new Scanner(System.in);

    public void applyLeave() {

        int employeeId = LoggedInUserContext.getEmployeeId();
        int managerId = LoggedInUserContext.getManagerId(); // must be set at login

        // Show leave types
        List<LeaveType> leaveTypes = leaveService.getAllLeaveTypes();

        if (leaveTypes.isEmpty()) {
            log.warn("No leave types configured");
            return;
        }

        log.info("===== AVAILABLE LEAVE TYPES =====");
        for (LeaveType lt : leaveTypes) {
            log.info("ID: {} | {} (Max {} days/year)",
                    lt.getLeaveTypeId(),
                    lt.getLeaveName(),
                    lt.getMaxDaysPerYear());
        }

        // Take leave type input
        log.info("Enter Leave Type ID:");
        int leaveTypeId = sc.nextInt();

        // Take date inputs
        log.info("Enter Start Date (YYYY-MM-DD):");
        LocalDate startDate = LocalDate.parse(sc.next());

        log.info("Enter End Date (YYYY-MM-DD):");
        LocalDate endDate = LocalDate.parse(sc.next());

        sc.nextLine(); // consume newline

        //  Reason
        log.info("Enter Reason:");
        String reason = sc.nextLine();

        //  Call service
        boolean success = leaveService.applyLeave(
                employeeId,
                managerId,
                leaveTypeId,
                startDate,
                endDate,
                reason
        );

        if (success) {
            log.info("Leave applied successfully and sent to manager");
        } else {
            log.warn("Leave application failed");
        }
    }
}
