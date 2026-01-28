package org.example.controller;

import org.example.models.LeaveApplication;
import org.example.models.LoggedInUserContext;
import org.example.service.ManagerLeaveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class ManagerLeaveController {

    private static final Logger log = LoggerFactory.getLogger(ManagerLeaveController.class);

    private final ManagerLeaveService managerLeaveService;
    private final Scanner sc;

    public ManagerLeaveController() {
        this.managerLeaveService = new ManagerLeaveService();
        this.sc = new Scanner(System.in);
    }

    // View pending leaves
    public void viewPendingLeaves() {

        Integer managerId = LoggedInUserContext.getEmployeeId();

        if (managerId == null) {
            log.error("Manager not logged in");
            return;
        }

        List<LeaveApplication> leaves = managerLeaveService.getPendingLeaves(managerId);

        if (leaves.isEmpty()) {
            log.info("No pending leave requests");
            return;
        }

        log.info("===== PENDING LEAVE REQUESTS =====");

        for (LeaveApplication leave : leaves) {
            log.info(
                    "LeaveId={} | EmpId={} | TypeId={} | From={} | To={} | Days={} | Reason={}",
                    leave.getLeaveAppId(),
                    leave.getEmployeeId(),
                    leave.getLeaveTypeId(),
                    leave.getStartDate(),
                    leave.getEndDate(),
                    leave.getWorkingDays(),
                    leave.getReason()
            );
        }
    }

    // Approve or Reject leave
    public void approveOrRejectLeave() {

        log.info("Enter Leave Application ID:");
        int leaveAppId = sc.nextInt();
        sc.nextLine();

        log.info("1. Approve");
        log.info("2. Reject");
        int choice = sc.nextInt();
        sc.nextLine();

        log.info("Enter Manager Comment:");
        String comment = sc.nextLine();

        boolean result;

        switch (choice) {

            case 1 -> {
                result = managerLeaveService.approveLeave(leaveAppId, comment);

                if (result) {
                    log.info("Leave approved successfully and balance updated");
                } else {
                    log.warn("Leave approval failed (insufficient balance or invalid request)");
                }
            }

            case 2 -> {
                result = managerLeaveService.rejectLeave(leaveAppId, comment);

                if (result) {
                    log.info("Leave rejected successfully");
                } else {
                    log.warn("Leave rejection failed");
                }
            }

            default -> log.warn("Invalid choice");
        }
    }

}