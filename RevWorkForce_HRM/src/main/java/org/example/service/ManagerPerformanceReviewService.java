package org.example.service;

import org.example.Dao.ManagerPerformanceReviewDAO;
import org.example.models.LoggedInUserContext;
import org.example.models.PerformanceReview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ManagerPerformanceReviewService {

    private static final Logger log =
            LoggerFactory.getLogger(ManagerPerformanceReviewService.class);

    private final ManagerPerformanceReviewDAO reviewDAO;

    public ManagerPerformanceReviewService() {
        this.reviewDAO = new ManagerPerformanceReviewDAO();
    }


    public List<PerformanceReview> getSubmittedReviews() {

        int managerId = LoggedInUserContext.getEmployeeId();

        List<PerformanceReview> reviews =
                reviewDAO.getSubmittedReviewsForManager(managerId);

        log.info("Service: {} submitted reviews fetched for managerId={}",
                reviews.size(), managerId);

        return reviews;
    }

    public boolean reviewEmployee(
            int reviewId,
            int rating,
            String feedback
    ) {

        boolean updated = reviewDAO.reviewEmployeePerformance(
                reviewId,
                rating,
                feedback
        );

        if (updated) {
            log.info("Service: Review completed. reviewId={}", reviewId);
        } else {
            log.warn("Service: Review failed. reviewId={}", reviewId);
        }

        return updated;
    }

    public boolean reviewEmployeePerformance(
            int reviewId,
            int rating,
            String feedback
    ) {
        return reviewEmployee(reviewId, rating, feedback);
    }
}


