package org.example.controller;

import org.example.dto.TeamPerformanceSummary;
import org.example.service.ManagerTeamPerformanceSummaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ManagerTeamPerformanceSummaryController {

        private static final Logger log = LoggerFactory.getLogger(ManagerTeamPerformanceSummaryController.class);

        private final ManagerTeamPerformanceSummaryService service;

        public ManagerTeamPerformanceSummaryController() {
            this.service = new ManagerTeamPerformanceSummaryService();
        }

        public void viewTeamPerformanceSummary() {

            List<TeamPerformanceSummary> summaries =
                    service.getTeamPerformanceSummary();

            if (summaries.isEmpty()) {
                log.info("No team performance reviews found");
                return;
            }

            log.info("===== TEAM PERFORMANCE SUMMARY =====");

            for (TeamPerformanceSummary s : summaries) {
                log.info(
                        "EmpId={} | Name={} | Year={} | SelfRating={} | ManagerRating={} | Status={}",
                        s.getEmployeeId(),
                        s.getEmployeeName(),
                        s.getReviewYear(),
                        s.getSelfRating(),
                        s.getManagerRating(),
                        s.getStatus()
                );
            }
        }
    }


