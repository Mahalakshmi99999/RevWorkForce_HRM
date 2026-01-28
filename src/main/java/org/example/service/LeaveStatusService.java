package org.example.service;

import org.example.Dao.LeaveApplicationDAO;
import org.example.models.LeaveApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LeaveStatusService {

    private static final Logger log =
            LoggerFactory.getLogger(LeaveStatusService.class);

    private final LeaveApplicationDAO leaveApplicationDAO = new LeaveApplicationDAO();

    public List<LeaveApplication> getLeaveStatus(int employeeId) {

        List<LeaveApplication> leaves =
                leaveApplicationDAO.findByEmployeeId(employeeId);

        if (leaves.isEmpty()) {
            log.info("No leave records found for employeeId={}", employeeId);
        } else {
            log.info("Returning {} leave records for employeeId={}",
                    leaves.size(), employeeId);
        }

        return leaves;
    }
}
