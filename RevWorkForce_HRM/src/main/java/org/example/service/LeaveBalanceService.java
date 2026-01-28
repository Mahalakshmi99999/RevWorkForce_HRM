package org.example.service;
import org.example.Dao.LeaveBalanceDAO;
import org.example.models.LeaveBalance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class LeaveBalanceService {

        private static final Logger log = LoggerFactory.getLogger(LeaveBalanceService.class);

        private final LeaveBalanceDAO leaveBalanceDAO;

        public LeaveBalanceService() {
            this.leaveBalanceDAO = new LeaveBalanceDAO();
        }

        public List<LeaveBalance> getLeaveBalance(int employeeId) {

            List<LeaveBalance> balances =
                    leaveBalanceDAO.getLeaveBalanceByEmployeeId(employeeId);

            log.info("Service returning {} leave balance records for employeeId={}",
                    balances.size(), employeeId);

            return balances;
        }
    }


