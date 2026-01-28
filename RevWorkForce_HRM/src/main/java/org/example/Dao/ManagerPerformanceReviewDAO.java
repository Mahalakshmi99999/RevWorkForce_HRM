package org.example.Dao;

import org.example.Config.DBconnection;
import org.example.models.PerformanceReview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ManagerPerformanceReviewDAO {

        private static final Logger log = LoggerFactory.getLogger(ManagerPerformanceReviewDAO.class);

        // 1. Fetch submitted reviews for manager
        private static final String FETCH_SUBMITTED_REVIEWS_SQL =
                "SELECT review_id, employee_id, review_year, status, submitted_date " +
                        "FROM performance_review " +
                        "WHERE manager_id = ? AND status = 'SUBMITTED' " +
                        "ORDER BY submitted_date DESC";

        public List<PerformanceReview> getSubmittedReviewsForManager(int managerId) {

            List<PerformanceReview> reviews = new ArrayList<>();

            try (Connection conn = DBconnection.getInstance();
                 PreparedStatement ps = conn.prepareStatement(FETCH_SUBMITTED_REVIEWS_SQL)) {

                ps.setInt(1, managerId);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    PerformanceReview pr = new PerformanceReview();
                    pr.setReviewId(rs.getInt("review_id"));
                    pr.setEmployeeId(rs.getInt("employee_id"));
                    pr.setReviewYear(rs.getInt("review_year"));
                    pr.setStatus(rs.getString("status"));

                    if (rs.getDate("submitted_date") != null) {
                        pr.setSubmittedDate(
                                rs.getDate("submitted_date").toLocalDate()
                        );
                    }

                    reviews.add(pr);
                }

                log.info("Fetched {} submitted reviews for managerId={}",
                        reviews.size(), managerId);

                return reviews;

            } catch (SQLException e) {
                log.error("Error fetching submitted reviews for managerId={}", managerId, e);
                throw new RuntimeException(e);
            }
        }

        // 2. Manager gives rating & feedback
        private static final String UPDATE_REVIEW_FEEDBACK_SQL =
                "UPDATE performance_review " +
                        "SET manager_rating = ?, manager_feedback = ?, status = 'REVIEWED' " +
                        "WHERE review_id = ? AND status = 'SUBMITTED'";

        public boolean reviewEmployeePerformance(
                int reviewId,
                int managerRating,
                String managerFeedback
        ) {

            try (Connection conn = DBconnection.getInstance();
                 PreparedStatement ps = conn.prepareStatement(UPDATE_REVIEW_FEEDBACK_SQL)) {

                ps.setInt(1, managerRating);
                ps.setString(2, managerFeedback);
                ps.setInt(3, reviewId);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    log.info("Performance review updated by manager. reviewId={}", reviewId);
                    return true;
                }

                log.warn("Review update failed (not SUBMITTED or not found). reviewId={}",
                        reviewId);
                return false;

            } catch (SQLException e) {
                log.error("Error updating performance review. reviewId={}", reviewId, e);
                throw new RuntimeException(e);
            }
        }
    }


