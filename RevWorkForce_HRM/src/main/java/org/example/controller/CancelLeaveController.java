package org.example.controller;

import org.example.models.LeaveApplication;
import org.example.models.LoggedInUserContext;
import org.example.service.CancelLeaveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class CancelLeaveController {

    private static final Logger log = LoggerFactory.getLogger(CancelLeaveController.class);

    private final CancelLeaveService cancelLeaveService = new CancelLeaveService();

    private final Scanner sc = new Scanner(System.in);

    public void cancelPendingLeave() {

        int employeeId = LoggedInUserContext.getEmployeeId();

        // Fetch pending leaves
        List<LeaveApplication> pendingLeaves = cancelLeaveService.getPendingLeaves(employeeId);

        if (pendingLeaves.isEmpty()) {
            log.info("No pending leaves available to cancel");
            return;
        }

        //  Display pending leaves
        log.info("===== PENDING LEAVES =====");
        for (LeaveApplication la : pendingLeaves) {
            log.info(
                    "LeaveId={} | TypeId={} | {} to {} | Days={} | Reason={}",
                    la.getLeaveAppId(),
                    la.getLeaveTypeId(),
                    la.getStartDate(),
                    la.getEndDate(),
                    la.getWorkingDays(),
                    la.getReason()
            );
        }

        // Ask user to choose leave id
        log.info("Enter LeaveId to cancel:");
        int leaveAppId = sc.nextInt();

        //  Cancel leave
        boolean cancelled =
                cancelLeaveService.cancelLeave(leaveAppId, employeeId);

        if (cancelled) {
            log.info("Leave cancelled successfully");
        } else {
            log.warn("Unable to cancel leave (may not be pending)");
        }
    }
}

