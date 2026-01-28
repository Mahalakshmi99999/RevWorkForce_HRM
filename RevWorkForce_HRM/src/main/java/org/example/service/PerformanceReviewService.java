package org.example.service;

import org.example.Dao.PerformanceReviewDAO;
import org.example.models.PerformanceReview;
import org.example.models.LoggedInUserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

public class PerformanceReviewService {

    private static final Logger log =
            LoggerFactory.getLogger(PerformanceReviewService.class);

    private final PerformanceReviewDAO reviewDAO;
    private final AuditLogService auditLogService;

    public PerformanceReviewService() {
        this.reviewDAO = new PerformanceReviewDAO();
        this.auditLogService = new AuditLogService();
    }

    public int createSelfReview(
            int reviewYear,
            String deliverables,
            String accomplishments,
            String improvements,
            int selfRating,
            boolean submit
    ) {

        PerformanceReview review = new PerformanceReview();

        review.setEmployeeId(LoggedInUserContext.getEmployeeId());
        review.setManagerId(LoggedInUserContext.getManagerId());
        review.setReviewYear(reviewYear);

        review.setDeliverables(deliverables);
        review.setAccomplishments(accomplishments);
        review.setImprovements(improvements);
        review.setSelfRating(selfRating);

        if (submit) {
            review.setStatus("SUBMITTED");
            review.setSubmittedDate(LocalDate.now());
        } else {
            review.setStatus("DRAFT");
            review.setSubmittedDate(null);
        }

        int reviewId = reviewDAO.createSelfReview(review);

        if (reviewId != -1) {

            log.info("Service: Performance review created. reviewId={}", reviewId);


            if (submit) {
                auditLogService.logAction(
                        LoggedInUserContext.getEmployeeId(),
                        "SUBMIT_PERFORMANCE_REVIEW",
                        "ReviewId=" + reviewId + ", Year=" + reviewYear
                );
            }

        } else {
            log.warn("Service: Performance review creation failed");
        }

        return reviewId;
    }

    public List<PerformanceReview> getMyReviews() {
        return reviewDAO.findByEmployeeId(
                LoggedInUserContext.getEmployeeId()
        );
    }
}
