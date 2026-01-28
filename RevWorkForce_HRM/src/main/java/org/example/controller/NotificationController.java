package org.example.controller;
import org.example.models.LoggedInUserContext;
import org.example.models.Notification;
import org.example.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class NotificationController {

        private static final Logger log = LoggerFactory.getLogger(NotificationController.class);

        private final NotificationService notificationService;
        private final Scanner sc;

        public NotificationController() {
            this.notificationService = new NotificationService();
            this.sc = new Scanner(System.in);
        }


        public void viewNotifications() {

            Integer employeeId = LoggedInUserContext.getEmployeeId();

            if (employeeId == null) {
                log.error("Employee not logged in. Cannot view notifications.");
                return;
            }

            List<Notification> notifications = notificationService.getNotifications(employeeId);

            if (notifications.isEmpty()) {
                log.info("No notifications found.");
                return;
            }

            log.info("===== NOTIFICATIONS =====");

            for (Notification n : notifications) {
                log.info(
                        "Id={} | {} | Read={}",
                        n.getNotificationId(),
                        n.getMessage(),
                        n.isRead()
                );
            }

            log.info("Enter Notification ID to mark as read (0 to skip):");
            int choice = sc.nextInt();

            if (choice != 0) {
                boolean updated = notificationService.markAsRead(choice);

                if (updated) {
                    log.info("Notification marked as read.");
                } else {
                    log.warn("Failed to mark notification as read.");
                }
            }
        }
    }

