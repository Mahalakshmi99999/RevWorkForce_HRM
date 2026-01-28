package org.example.controller;

import org.example.service.PerformanceReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class PerformanceReviewController {

        private static final Logger log = LoggerFactory.getLogger(PerformanceReviewController.class);

        private final PerformanceReviewService service;
        private final Scanner sc;

        public PerformanceReviewController() {
            this.service = new PerformanceReviewService();
            this.sc = new Scanner(System.in);
        }

        public void createSelfReview() {

            log.info("Enter Review Year (e.g. 2025):");
            int reviewYear = sc.nextInt();
            sc.nextLine();

            log.info("Enter Key Deliverables:");
            String deliverables = sc.nextLine();

            log.info("Enter Major Accomplishments:");
            String accomplishments = sc.nextLine();

            log.info("Enter Areas of Improvement:");
            String improvements = sc.nextLine();

            log.info("Enter Self Rating (1-5):");
            int selfRating = sc.nextInt();
            sc.nextLine(); // consume newline

            log.info("Submit review now? (yes/no):");
            String choice = sc.nextLine();

            boolean submit = choice.equalsIgnoreCase("yes");

            int reviewId = service.createSelfReview(
                    reviewYear,
                    deliverables,
                    accomplishments,
                    improvements,
                    selfRating,
                    submit
            );

            if (reviewId != -1) {
                log.info("Performance review saved successfully. reviewId={}", reviewId);
            } else {
                log.warn("Failed to save performance review");
            }
        }

        // viewing performance review

    public void viewMyReviews() {

        var reviews = service.getMyReviews();

        if (reviews == null || reviews.isEmpty()) {
            log.info("No performance reviews found");
            return;
        }

        log.info("===== MY PERFORMANCE REVIEWS =====");
        for (var pr : reviews) {
            log.info(
                    "ReviewId={} | Year={} | Status={}",
                    pr.getReviewId(),
                    pr.getReviewYear(),
                    pr.getStatus()
            );
        }
    }

}




