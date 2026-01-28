package org.example.service;

import org.example.Dao.LeaveApplicationDAO;
import org.example.models.LeaveApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CancelLeaveService {

    private static final Logger log = LoggerFactory.getLogger(CancelLeaveService.class);

    private final LeaveApplicationDAO leaveApplicationDAO = new LeaveApplicationDAO();


    public List<LeaveApplication> getPendingLeaves(int employeeId) {

        List<LeaveApplication> pendingLeaves = leaveApplicationDAO.findPendingLeavesByEmployee(employeeId);

        if (pendingLeaves.isEmpty()) {
            log.info("No pending leaves found for employeeId={}", employeeId);
        } else {
            log.info("Returning {} pending leaves for employeeId={}",
                    pendingLeaves.size(), employeeId);
        }

        return pendingLeaves;
    }

    // to  cancel pendig leaves
    public boolean cancelLeave(int leaveAppId, int employeeId) {

        boolean cancelled = leaveApplicationDAO.cancelPendingLeave(leaveAppId, employeeId);

        if (cancelled) {
            log.info("Leave cancelled successfully. leaveAppId={}, employeeId={}",
                    leaveAppId, employeeId);
        } else {
            log.warn("Leave cancel failed. leaveAppId={}, employeeId={}",
                    leaveAppId, employeeId);
        }

        return cancelled;
    }
}

