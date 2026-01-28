package org.example.service;

import org.example.Dao.PerformanceReviewDAO;
import org.example.models.LoggedInUserContext;
import org.example.models.PerformanceReview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EmployeePerformanceFeedbackService {

        private static final Logger log = LoggerFactory.getLogger(EmployeePerformanceFeedbackService.class);

        private final PerformanceReviewDAO reviewDAO;

        public EmployeePerformanceFeedbackService() {
            this.reviewDAO = new PerformanceReviewDAO();
        }

        public List<PerformanceReview> getMyPerformanceFeedback() {

            int employeeId = LoggedInUserContext.getEmployeeId();

            List<PerformanceReview> reviews =
                    reviewDAO.findFeedbackByEmployeeId(employeeId);

            log.info("Service: {} feedback records fetched for employeeId={}",
                    reviews.size(), employeeId);

            return reviews;
        }
    }


