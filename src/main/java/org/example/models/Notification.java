package org.example.models;
import java.time.LocalDateTime;

public class Notification {
        private int notificationId;
        private int employeeId;
        private String message;
        private String notificationType;
        private boolean isRead;
        private LocalDateTime createdAt;

        public int getNotificationId() {
            return notificationId;
        }
        public void setNotificationId(int notificationId) {
            this.notificationId = notificationId;
        }
        public int getEmployeeId() {
            return employeeId;
        }
        public void setEmployeeId(int employeeId) {
            this.employeeId = employeeId;
        }
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
        public String getNotificationType() {
            return notificationType;
        }
        public void setNotificationType(String notificationType) {
            this.notificationType = notificationType;
        }
        public boolean isRead() {
            return isRead;
        }
        public void setRead(boolean isRead) {
            this.isRead = isRead;
        }
        public LocalDateTime getCreatedAt() {
            return createdAt;
        }
        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }
}


