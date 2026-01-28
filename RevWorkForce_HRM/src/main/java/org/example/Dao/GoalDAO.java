package org.example.Dao;

import org.example.Config.DBconnection;
import org.example.models.Goal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class GoalDAO {
        private static final Logger log = LoggerFactory.getLogger(GoalDAO.class);

        private static final String INSERT_GOAL_SQL = "INSERT INTO goal (" +
                        "review_id, goal_description, deadline, priority, success_metric, progress_status" +
                        ") VALUES (?, ?, ?, ?, ?, ?)";

        public boolean addGoal(Goal goal) {

            try (Connection conn = DBconnection.getInstance();
                 PreparedStatement ps = conn.prepareStatement(INSERT_GOAL_SQL)) {

                ps.setInt(1, goal.getReviewId());
                ps.setString(2, goal.getGoalDescription());
                ps.setDate(3,
                        goal.getDeadline() != null
                                ? Date.valueOf(goal.getDeadline())
                                : null
                );
                ps.setString(4, goal.getPriority());
                ps.setString(5, goal.getSuccessMetric());
                ps.setString(6, goal.getProgressStatus());

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    log.info("Goal added successfully for reviewId={}",
                            goal.getReviewId());
                    return true;
                }

                log.warn("Goal insertion failed for reviewId={}",
                        goal.getReviewId());
                return false;

            } catch (SQLException e) {
                log.error("Error adding goal for reviewId={}",
                        goal.getReviewId(), e);
                throw new RuntimeException(e);
            }
        }
    }


