package org.example.Dao;

import org.example.Config.DBconnection;
import org.example.models.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

        private static final Logger log = LoggerFactory.getLogger(NotificationDAO.class);

        //  Create notification
        private static final String INSERT_NOTIFICATION_SQL =
                "INSERT INTO notification (employee_id, message, notification_type, is_read) " +
                        "VALUES (?, ?, ?, false)";

        public void createNotification(int employeeId, String message, String type) {

            try (Connection conn = DBconnection.getInstance();
                 PreparedStatement ps = conn.prepareStatement(INSERT_NOTIFICATION_SQL)) {

                ps.setInt(1, employeeId);
                ps.setString(2, message);
                ps.setString(3, type);

                ps.executeUpdate();
                log.info("Notification created for employeeId={}", employeeId);

            } catch (SQLException e) {
                log.error("Error creating notification for employeeId={}", employeeId, e);
                throw new RuntimeException(e);
            }
        }

        // Fetch notifications for employee
        private static final String FETCH_NOTIFICATIONS_SQL =
                "SELECT notification_id, employee_id, message, notification_type, is_read, created_at " +
                        "FROM notification " +
                        "WHERE employee_id = ? " +
                        "ORDER BY created_at DESC";

        public List<Notification> getNotificationsByEmployeeId(int employeeId) {

            List<Notification> notifications = new ArrayList<>();

            try (Connection conn = DBconnection.getInstance();
                 PreparedStatement ps = conn.prepareStatement(FETCH_NOTIFICATIONS_SQL)) {

                ps.setInt(1, employeeId);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Notification n = new Notification();
                    n.setNotificationId(rs.getInt("notification_id"));
                    n.setEmployeeId(rs.getInt("employee_id"));
                    n.setMessage(rs.getString("message"));
                    n.setNotificationType(rs.getString("notification_type"));
                    n.setRead(rs.getBoolean("is_read"));
                    n.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                    notifications.add(n);
                }

                log.info("Fetched {} notifications for employeeId={}",
                        notifications.size(), employeeId);

                return notifications;

            } catch (SQLException e) {
                log.error("Error fetching notifications for employeeId={}", employeeId, e);
                throw new RuntimeException(e);
            }
        }

        //  Mark notification as read
        private static final String MARK_AS_READ_SQL =
                "UPDATE notification SET is_read = true WHERE notification_id = ?";

        public boolean markAsRead(int notificationId) {

            try (Connection conn = DBconnection.getInstance();
                 PreparedStatement ps = conn.prepareStatement(MARK_AS_READ_SQL)) {

                ps.setInt(1, notificationId);
                int rows = ps.executeUpdate();

                if (rows > 0) {
                    log.info("Notification marked as read. notificationId={}", notificationId);
                    return true;
                }

                log.warn("Notification not found to mark as read. notificationId={}", notificationId);
                return false;

            } catch (SQLException e) {
                log.error("Error marking notification as read. notificationId={}", notificationId, e);
                throw new RuntimeException(e);
            }
        }
    }

