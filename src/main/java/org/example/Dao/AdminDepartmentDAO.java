package org.example.Dao;
import org.example.Config.DBconnection;
import org.example.models.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDepartmentDAO {

        private static final Logger log = LoggerFactory.getLogger(AdminDepartmentDAO.class);


        private static final String INSERT_DEPARTMENT_SQL =
                "INSERT INTO department (department_name) VALUES (?)";

        public boolean addDepartment(String departmentName) {

            try (Connection conn = DBconnection.getInstance();
                 PreparedStatement ps = conn.prepareStatement(INSERT_DEPARTMENT_SQL)) {

                ps.setString(1, departmentName);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    log.info("Department added: {}", departmentName);
                    return true;
                }

                log.warn("Failed to add department: {}", departmentName);
                return false;

            } catch (SQLException e) {
                log.error("Error adding department: {}", departmentName, e);
                throw new RuntimeException(e);
            }
        }


        private static final String FETCH_ALL_DEPARTMENTS_SQL =
                "SELECT department_id, department_name FROM department ORDER BY department_name";

        public List<Department> getAllDepartments() {

            List<Department> departments = new ArrayList<>();

            try (Connection conn = DBconnection.getInstance();
                 PreparedStatement ps = conn.prepareStatement(FETCH_ALL_DEPARTMENTS_SQL);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Department d = new Department();
                    d.setDepartmentId(rs.getInt("department_id"));
                    d.setDepartmentName(rs.getString("department_name"));
                    departments.add(d);
                }

                log.info("Fetched {} departments", departments.size());
                return departments;

            } catch (SQLException e) {
                log.error("Error fetching departments", e);
                throw new RuntimeException(e);
            }
        }
    }


