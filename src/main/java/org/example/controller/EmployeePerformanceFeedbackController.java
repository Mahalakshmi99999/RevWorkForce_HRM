package org.example.controller;

import org.example.models.PerformanceReview;
import org.example.service.EmployeePerformanceFeedbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EmployeePerformanceFeedbackController {
    private static final Logger log = LoggerFactory.getLogger(EmployeePerformanceFeedbackController.class);

        private final EmployeePerformanceFeedbackService service =
                new EmployeePerformanceFeedbackService();

        public void viewMyFeedback() {

            List<PerformanceReview> reviews = service.getMyPerformanceFeedback();

            if (reviews.isEmpty()) {
                log.info("No performance feedback available yet");
                return;
            }

            log.info("===== PERFORMANCE FEEDBACK =====");

            for (PerformanceReview pr : reviews) {
                log.info(
                        "Year={} | Status={} | SelfRating={} | ManagerRating={} | Feedback={}",
                        pr.getReviewYear(),
                        pr.getStatus(),
                        pr.getSelfRating(),
                        pr.getManagerRating(),
                        pr.getManagerFeedback()
                );
            }
        }
    }


