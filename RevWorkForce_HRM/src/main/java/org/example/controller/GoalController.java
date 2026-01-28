package org.example.controller;

import org.example.service.GoalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Scanner;

public class GoalController {
        private static final Logger log = LoggerFactory.getLogger(GoalController.class);

        private final GoalService goalService;
        private final Scanner sc;

        public GoalController() {
            this.goalService = new GoalService();
            this.sc = new Scanner(System.in);
        }

        public void addGoal() {

            log.info("Enter Performance Review ID:");
            int reviewId = sc.nextInt();
            sc.nextLine(); // consume newline

            log.info("Enter Goal Description:");
            String description = sc.nextLine();

            log.info("Enter Deadline (YYYY-MM-DD):");
            LocalDate deadline = LocalDate.parse(sc.nextLine());

            log.info("Enter Priority (HIGH / MEDIUM / LOW):");
            String priority = sc.nextLine().toUpperCase();

            log.info("Enter Success Metric:");
            String successMetric = sc.nextLine();

            boolean success = goalService.addGoal(
                    reviewId,
                    description,
                    deadline,
                    priority,
                    successMetric
            );

            if (success) {
                log.info("Goal added successfully");
            } else {
                log.warn("Failed to add goal");
            }
        }
    }


