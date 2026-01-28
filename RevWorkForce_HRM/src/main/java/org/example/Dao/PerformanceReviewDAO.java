package org.example.Dao;

import org.example.Config.DBconnection;
import org.example.models.PerformanceReview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class PerformanceReviewDAO {

        private static final Logger log =
                LoggerFactory.getLogger(PerformanceReviewDAO.class);

        // create performance review (employee self review)
        private static final String INSERT_REVIEW_SQL =
                "INSERT INTO performance_review (" +
                        "employee_id, manager_id, review_year, " +
                        "deliverables, accomplishments, improvements, " +
                        "self_rating, status, submitted_date" +
                        ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        public int createSelfReview(PerformanceReview review) {

            try (Connection conn = DBconnection.getInstance();
                 PreparedStatement ps =
                         conn.prepareStatement(
                                 INSERT_REVIEW_SQL,
                                 PreparedStatement.RETURN_GENERATED_KEYS
                         )) {

                ps.setInt(1, review.getEmployeeId());
                ps.setInt(2, review.getManagerId());
                ps.setInt(3, review.getReviewYear());
                ps.setString(4, review.getDeliverables());
                ps.setString(5, review.getAccomplishments());
                ps.setString(6, review.getImprovements());
                ps.setInt(7, review.getSelfRating());
                ps.setString(8, review.getStatus()); // DRAFT / SUBMITTED
                ps.setDate(9,
                        review.getSubmittedDate() != null
                                ? Date.valueOf(review.getSubmittedDate())
                                : null
                );

                int rows = ps.executeUpdate();

                if (rows == 0) {
                    log.warn("Performance review insert failed. employeeId={}, year={}",
                            review.getEmployeeId(), review.getReviewYear());
                    return -1;
                }

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int reviewId = rs.getInt(1);
                        log.info("Performance review created. reviewId={}, employeeId={}",
                                reviewId, review.getEmployeeId());
                        return reviewId;
                    }
                }

                log.error("Review inserted but review_id not generated");
                return -1;

            } catch (SQLException e) {
                log.error(
                        "Error creating performance review. employeeId={}, year={}",
                        review.getEmployeeId(),
                        review.getReviewYear(),
                        e
                );
                throw new RuntimeException(e);
            }
        }

    public List<PerformanceReview> findByEmployeeId(int employeeId) {

        String sql =
                "SELECT review_id, review_year, status, submitted_date " +
                        "FROM performance_review " +
                        "WHERE employee_id = ? " +
                        "ORDER BY review_year DESC";

        List<PerformanceReview> reviews = new ArrayList<>();

        try (Connection conn = DBconnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PerformanceReview pr = new PerformanceReview();
                pr.setReviewId(rs.getInt("review_id"));
                pr.setReviewYear(rs.getInt("review_year"));
                pr.setStatus(rs.getString("status"));
                pr.setSubmittedDate(
                        rs.getDate("submitted_date") != null
                                ? rs.getDate("submitted_date").toLocalDate()
                                : null
                );
                reviews.add(pr);
            }

            log.info("Fetched {} reviews for employeeId={}", reviews.size(), employeeId);
            return reviews;

        } catch (SQLException e) {
            log.error("Error fetching reviews for employeeId={}", employeeId, e);
            throw new RuntimeException(e);
        }
    }

    public List<PerformanceReview> findFeedbackByEmployeeId(int employeeId) {

        String sql =
                "SELECT review_id, review_year, self_rating, manager_rating, " +
                        "manager_feedback, status, submitted_date " +
                        "FROM performance_review " +
                        "WHERE employee_id = ? " +
                        "ORDER BY review_year DESC";

        List<PerformanceReview> reviews = new ArrayList<>();

        try (Connection conn = DBconnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PerformanceReview pr = new PerformanceReview();
                pr.setReviewId(rs.getInt("review_id"));
                pr.setReviewYear(rs.getInt("review_year"));
                pr.setSelfRating(rs.getInt("self_rating"));
                pr.setManagerRating((Integer) rs.getObject("manager_rating"));
                pr.setManagerFeedback(rs.getString("manager_feedback"));
                pr.setStatus(rs.getString("status"));

                if (rs.getDate("submitted_date") != null) {
                    pr.setSubmittedDate(rs.getDate("submitted_date").toLocalDate());
                }

                reviews.add(pr);
            }

            log.info("Fetched {} feedback records for employeeId={}",
                    reviews.size(), employeeId);

            return reviews;

        } catch (SQLException e) {
            log.error("Error fetching feedback for employeeId={}", employeeId, e);
            throw new RuntimeException(e);
        }
    }


}


