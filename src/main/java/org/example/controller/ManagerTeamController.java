package org.example.controller;

import org.example.models.Employee;
import org.example.service.ManagerTeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ManagerTeamController {

        private static final Logger log = LoggerFactory.getLogger(ManagerTeamController.class);

        private final ManagerTeamService teamService;

        public ManagerTeamController() {
            this.teamService = new ManagerTeamService();
        }

        public void viewMyTeam() {

            List<Employee> team = teamService.getMyTeam();

            if (team.isEmpty()) {
                log.info("You have no team members assigned");
                return;
            }

            log.info("===== MY TEAM MEMBERS =====");
            for (Employee emp : team) {
                log.info(
                        "ID={} | Name={} {} | Email={} | DeptId={} | DesigId={}",
                        emp.getEmployeeId(),
                        emp.getFirstName(),
                        emp.getLastName(),
                        emp.getEmail(),
                        emp.getDepartmentId(),
                        emp.getDesignationId()
                );
            }
        }
    }


