package org.example.Dao;

import org.example.Config.DBconnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

    private static final Logger log = LoggerFactory.getLogger(LoginDAO.class);

    private static final String LOGIN_SQL =
            "SELECT e.employee_id " +
                    "FROM login_credentials lc " +
                    "JOIN employee e ON lc.employee_id = e.employee_id " +
                    "WHERE lc.employee_id = ? " +
                    "AND lc.password_hash = ? " +
                    "AND e.status = 'ACTIVE'";

    public int validateLogin(int employeeId, String password) {

        try (Connection conn = DBconnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(LOGIN_SQL)) {

            ps.setInt(1, employeeId);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                log.info("Login validated for employeeId={}", employeeId);
                return employeeId;
            }

            log.warn("Invalid login for employeeId={}", employeeId);
            return -1;

        } catch (SQLException e) {
            log.error("DB error during login for employeeId={}", employeeId, e);
            throw new RuntimeException(e);
        }
    }


    public boolean createLoginCredentials(int employeeId, String passwordHash) {

        String sql =
                "INSERT INTO login_credentials (employee_id, password_hash) " +
                        "VALUES (?, ?)";

        try (Connection conn = DBconnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            ps.setString(2, passwordHash);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                log.info("Login credentials created for employeeId={}", employeeId);
                return true;
            }

            log.warn("Failed to create login credentials for employeeId={}", employeeId);
            return false;

        } catch (SQLException e) {
            log.error("Error creating login credentials for employeeId={}", employeeId, e);
            throw new RuntimeException(e);
        }
    }

    public boolean updatePassword(int employeeId, String newPassword) {

        String sql =
                "UPDATE login_credentials " +
                        "SET password_hash = ? " +
                        "WHERE employee_id = ?";

        try (Connection con = DBconnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setInt(2, employeeId);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getPassword(int employeeId) {

        String sql =
                "SELECT password_hash FROM login_credentials WHERE employee_id = ?";

        try (Connection con = DBconnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("password_hash");
            }
            return null;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getSecurityQuestion(int employeeId) {

        String sql =
                "SELECT security_question " +
                        "FROM login_credentials " +
                        "WHERE employee_id = ?";

        try (Connection con = DBconnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("security_question");
            }
            return null;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
