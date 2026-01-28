package org.example.controller;

import org.example.models.LeaveApplication;
import org.example.models.LoggedInUserContext;
import org.example.service.LeaveStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LeaveStatusController {

    private static final Logger log = LoggerFactory.getLogger(LeaveStatusController.class);

    private final LeaveStatusService leaveStatusService = new LeaveStatusService();

    public void viewLeaveStatus() {

        int employeeId = LoggedInUserContext.getEmployeeId();

        List<LeaveApplication> leaves = leaveStatusService.getLeaveStatus(employeeId);

        if (leaves.isEmpty()) {
            log.info("No leave applications found");
            return;
        }

        log.info("===== LEAVE STATUS =====");

        for (LeaveApplication la : leaves) {
            log.info(
                    "LeaveId={} | TypeId={} | {} to {} | Days={} | Status={} | ManagerComment={}",
                    la.getLeaveAppId(),
                    la.getLeaveTypeId(),
                    la.getStartDate(),
                    la.getEndDate(),
                    la.getWorkingDays(),
                    la.getStatus(),
                    la.getManagerComment()
            );
        }
    }
}

