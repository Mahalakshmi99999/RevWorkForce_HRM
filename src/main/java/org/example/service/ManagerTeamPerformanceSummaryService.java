package org.example.service;

import org.example.Dao.ManagerTeamPerformanceSummaryDAO;
import org.example.models.LoggedInUserContext;
import org.example.dto.TeamPerformanceSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ManagerTeamPerformanceSummaryService {

    private static final Logger log =
            LoggerFactory.getLogger(ManagerTeamPerformanceSummaryService.class);

    private final ManagerTeamPerformanceSummaryDAO summaryDAO;

    public ManagerTeamPerformanceSummaryService() {
        this.summaryDAO = new ManagerTeamPerformanceSummaryDAO();
    }


    public List<TeamPerformanceSummary> getTeamPerformanceSummary() {

        int managerId = LoggedInUserContext.getEmployeeId();

        List<TeamPerformanceSummary> summaryList =
                summaryDAO.fetchTeamPerformanceSummary(managerId);

        log.info(
                "Service: {} team performance records fetched for managerId={}",
                summaryList.size(),
                managerId
        );

        return summaryList;
    }
}
