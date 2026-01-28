package org.example.service;

import org.example.Dao.ManagerTeamLeaveCalendarDAO;
import org.example.models.LeaveApplication;
import org.example.models.LoggedInUserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ManagerTeamLeaveCalendarService {
        private static final Logger log = LoggerFactory.getLogger(ManagerTeamLeaveCalendarService.class);

        private final ManagerTeamLeaveCalendarDAO calendarDAO;

        public ManagerTeamLeaveCalendarService() {
            this.calendarDAO = new ManagerTeamLeaveCalendarDAO();
        }

        public List<LeaveApplication> getTeamLeaveCalendar() {

            int managerId = LoggedInUserContext.getEmployeeId();

            List<LeaveApplication> leaves =
                    calendarDAO.getTeamLeaveCalendar(managerId);

            log.info("Service: {} team leave entries fetched for managerId={}",
                    leaves.size(), managerId);

            return leaves;
        }
    }


