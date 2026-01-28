package org.example.service;

import org.example.Dao.GoalDAO;
import org.example.models.Goal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class GoalService {

        private static final Logger log = LoggerFactory.getLogger(GoalService.class);

        private final GoalDAO goalDAO;

        public GoalService() {
            this.goalDAO = new GoalDAO();
        }

        public boolean addGoal(
                int reviewId,
                String goalDescription,
                LocalDate deadline,
                String priority,
                String successMetric
        ) {

            Goal goal = new Goal();
            goal.setReviewId(reviewId);
            goal.setGoalDescription(goalDescription);
            goal.setDeadline(deadline);
            goal.setPriority(priority);
            goal.setSuccessMetric(successMetric);
            goal.setProgressStatus("NOT_STARTED");

            boolean result = goalDAO.addGoal(goal);

            if (result) {
                log.info("Service: Goal added successfully for reviewId={}", reviewId);
            } else {
                log.warn("Service: Failed to add goal for reviewId={}", reviewId);
            }

            return result;
        }
    }


