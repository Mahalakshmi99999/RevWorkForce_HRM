package org.example.service;

import org.example.Dao.LeaveApplicationDAO;
import org.example.Dao.LeaveBalanceDAO;
import org.example.Dao.LeaveTypeDAO;
import org.example.models.LeaveApplication;
import org.example.models.LeaveBalance;
import org.example.models.LeaveType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class LeaveService {

    private static final Logger log =
            LoggerFactory.getLogger(LeaveService.class);

    private final LeaveTypeDAO leaveTypeDAO = new LeaveTypeDAO();
    private final LeaveBalanceDAO leaveBalanceDAO = new LeaveBalanceDAO();
    private final LeaveApplicationDAO leaveApplicationDAO = new LeaveApplicationDAO();

    public boolean applyLeave(int employeeId,
                              int managerId,
                              int leaveTypeId,
                              LocalDate startDate,
                              LocalDate endDate,
                              String reason) {

        //  Validating dates
        if (startDate.isAfter(endDate)) {
            log.warn("Invalid date range: {} to {}", startDate, endDate);
            return false;
        }

        //  Calculating working days
        int days = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;

        // Fetching leave balance
        LeaveBalance balance =
                leaveBalanceDAO.findByEmployeeAndType(employeeId, leaveTypeId);

        if (balance == null) {
            log.warn("No leave balance found for employeeId={}, leaveTypeId={}",
                    employeeId, leaveTypeId);
            return false;
        }

        if (balance.getAvailableDays() < days) {
            log.warn("Insufficient leave balance. Available={}, Required={}",
                    balance.getAvailableDays(), days);
            return false;
        }

        // Create LeaveApplication model
        LeaveApplication leave = new LeaveApplication();
        leave.setEmployeeId(employeeId);
        leave.setManagerId(managerId);
        leave.setLeaveTypeId(leaveTypeId);
        leave.setStartDate(startDate);
        leave.setEndDate(endDate);
        leave.setWorkingDays(days);
        leave.setReason(reason);
        leave.setAppliedDate(LocalDate.now());

        //  Save leave application
        boolean applied = leaveApplicationDAO.applyLeave(leave);

        if (applied) {
            log.info("Leave applied successfully. employeeId={}, days={}",
                    employeeId, days);
        }

        return applied;
    }


    public List<LeaveType> getAllLeaveTypes() {
        return leaveTypeDAO.findAll();
    }
}

