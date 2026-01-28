package org.example.service;
import org.example.Dao.ManagerTeamDAO;
import org.example.models.Employee;
import org.example.models.LoggedInUserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ManagerTeamService {

        private static final Logger log = LoggerFactory.getLogger(ManagerTeamService.class);

        private final ManagerTeamDAO teamDAO;

        public ManagerTeamService() {
            this.teamDAO = new ManagerTeamDAO();
        }

        public List<Employee> getMyTeam() {

            int managerId = LoggedInUserContext.getEmployeeId();

            List<Employee> team = teamDAO.findTeamMembers(managerId);

            log.info("Service: {} team members fetched for managerId={}",
                    team.size(), managerId);

            return team;
        }
    }


