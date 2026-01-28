package org.example.service;

import org.example.Dao.NotificationDAO;
import org.example.models.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NotificationService {

        private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

        private final NotificationDAO notificationDAO;

        public NotificationService() {
            this.notificationDAO = new NotificationDAO();
        }

        // Create notification
        public void notifyEmployee(int employeeId, String message, String type) {

            notificationDAO.createNotification(employeeId, message, type);
            log.info("Service: notification sent to employeeId={}", employeeId);
        }

        //  View notifications for employee
        public List<Notification> getNotifications(int employeeId) {

            List<Notification> notifications = notificationDAO.getNotificationsByEmployeeId(employeeId);

            log.info("Service: {} notifications fetched for employeeId={}",
                    notifications.size(), employeeId);

            return notifications;
        }

        // Mark notification as read
        public boolean markAsRead(int notificationId) {

            boolean updated = notificationDAO.markAsRead(notificationId);

            if (updated) {
                log.info("Service: notification marked as read. notificationId={}",
                        notificationId);
            } else {
                log.warn("Service: failed to mark notification as read. notificationId={}",
                        notificationId);
            }

            return updated;
        }
    }


