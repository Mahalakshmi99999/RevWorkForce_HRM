package org.example.controller;

import org.example.models.PerformanceReview;
import org.example.service.ManagerPerformanceReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class ManagerPerformanceReviewController {

    private static final Logger log =
            LoggerFactory.getLogger(ManagerPerformanceReviewController.class);

    private final ManagerPerformanceReviewService service =
            new ManagerPerformanceReviewService();

    private final Scanner sc = new Scanner(System.in);

    // View submitted reviews
    public void viewSubmittedReviews() {

        List<PerformanceReview> reviews = service.getSubmittedReviews();

        if (reviews.isEmpty()) {
            log.info("No submitted performance reviews to review");
            return;
        }

        log.info("===== SUBMITTED PERFORMANCE REVIEWS =====");
        for (PerformanceReview pr : reviews) {
            log.info(
                    "ReviewId={} | EmployeeId={} | Year={}",
                    pr.getReviewId(),
                    pr.getEmployeeId(),
                    pr.getReviewYear()
            );
        }
    }

    // Review (rate + feedback)
    public void reviewEmployeePerformance() {

        viewSubmittedReviews();

        log.info("Enter Review ID to review:");
        int reviewId = sc.nextInt();
        sc.nextLine(); // consume newline

        log.info("Enter Manager Rating (1–5):");
        int rating = sc.nextInt();
        sc.nextLine();

        log.info("Enter Manager Feedback:");
        String feedback = sc.nextLine();

        boolean success =
                service.reviewEmployeePerformance(reviewId, rating, feedback);

        if (success) {
            log.info("Performance review completed successfully");
        } else {
            log.warn("Performance review failed");
        }
    }
}
