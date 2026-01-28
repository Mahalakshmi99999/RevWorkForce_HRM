package org.example.controller;
import org.example.models.LeaveBalance;
import org.example.models.LoggedInUserContext;
import org.example.service.LeaveBalanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class LeaveBalanceController {

        private static final Logger log = LoggerFactory.getLogger(LeaveBalanceController.class);

        private final LeaveBalanceService leaveBalanceService;

        public LeaveBalanceController() {
            this.leaveBalanceService = new LeaveBalanceService();
        }

        public void viewLeaveBalance() {

            Integer employeeId = LoggedInUserContext.getEmployeeId();

            if (employeeId == null) {
                log.error("Employee not logged in. Cannot view leave balance.");
                return;
            }

            List<LeaveBalance> balances =
                    leaveBalanceService.getLeaveBalance(employeeId);

            if (balances.isEmpty()) {
                log.info("No leave balance records found.");
                return;
            }

            log.info("===== LEAVE BALANCE =====");

            for (LeaveBalance balance : balances) {
                log.info(
                        "LeaveTypeId={} | AvailableDays={}",
                        balance.getLeaveTypeId(),
                        balance.getAvailableDays()
                );
            }
        }
    }


