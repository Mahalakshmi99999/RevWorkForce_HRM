package org.example.Dao;

import org.example.Config.DBconnection;
import org.example.models.AuditLog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuditLogDAO {

        // INSERT
        private static final String INSERT_SQL =
                "INSERT INTO audit_log (employee_id, action, remarks) VALUES (?, ?, ?)";

        public void logAction(int employeeId, String action, String remarks) {

            try (Connection conn = DBconnection.getInstance();
                 PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

                ps.setInt(1, employeeId);
                ps.setString(2, action);
                ps.setString(3, remarks);

                ps.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        // FETCH (ADMIN VIEW)
        private static final String FETCH_SQL =
                "SELECT log_id, employee_id, action, remarks, timestamp " +
                        "FROM audit_log ORDER BY timestamp DESC";

        public List<AuditLog> fetchAllLogs() {

            List<AuditLog> logs = new ArrayList<>();

            try (Connection conn = DBconnection.getInstance();
                 PreparedStatement ps = conn.prepareStatement(FETCH_SQL);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    AuditLog log = new AuditLog();
                    log.setLogId(rs.getInt("log_id"));
                    log.setEmployeeId(rs.getInt("employee_id"));
                    log.setAction(rs.getString("action"));
                    log.setRemarks(rs.getString("remarks"));
                    log.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());

                    logs.add(log);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return logs;
        }
    }


