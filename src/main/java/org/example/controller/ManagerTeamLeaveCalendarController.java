package org.example.controller;

import org.example.models.LeaveApplication;
import org.example.service.ManagerTeamLeaveCalendarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ManagerTeamLeaveCalendarController {

        private static final Logger log = LoggerFactory.getLogger(ManagerTeamLeaveCalendarController.class);

        private final ManagerTeamLeaveCalendarService calendarService;

        public ManagerTeamLeaveCalendarController() {
            this.calendarService = new ManagerTeamLeaveCalendarService();
        }

        public void viewTeamLeaveCalendar() {

            List<LeaveApplication> leaves =
                    calendarService.getTeamLeaveCalendar();

            if (leaves.isEmpty()) {
                log.info("No approved leaves found for your team");
                return;
            }

            log.info("===== TEAM LEAVE CALENDAR =====");
            for (LeaveApplication la : leaves) {
                log.info(
                        "LeaveId={} | EmpId={} | LeaveTypeId={} | {} to {}",
                        la.getLeaveAppId(),
                        la.getEmployeeId(),
                        la.getLeaveTypeId(),
                        la.getStartDate(),
                        la.getEndDate()
                );
            }
        }
    }

