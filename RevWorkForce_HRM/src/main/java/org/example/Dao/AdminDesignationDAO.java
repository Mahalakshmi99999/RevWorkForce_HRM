package org.example.Dao;
import org.example.Config.DBconnection;
import org.example.models.Designation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDesignationDAO {

        private static final Logger log = LoggerFactory.getLogger(AdminDesignationDAO.class);

        // 1. Add designation
        private static final String INSERT_DESIGNATION_SQL =
                "INSERT INTO designation (designation_name, department_id) VALUES (?, ?)";

        public boolean addDesignation(String designationName, int departmentId) {

            try (Connection conn = DBconnection.getInstance();
                 PreparedStatement ps = conn.prepareStatement(INSERT_DESIGNATION_SQL)) {

                ps.setString(1, designationName);
                ps.setInt(2, departmentId);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    log.info("Designation added: {} (departmentId={})",
                            designationName, departmentId);
                    return true;
                }

                log.warn("Failed to add designation: {}", designationName);
                return false;

            } catch (SQLException e) {
                log.error("Error adding designation: {}", designationName, e);
                throw new RuntimeException(e);
            }
        }

        // 2. Fetch all designations
        private static final String FETCH_ALL_DESIGNATIONS_SQL =
                "SELECT d.designation_id, d.designation_name, d.department_id " +
                        "FROM designation d ORDER BY d.designation_name";

        public List<Designation> getAllDesignations() {

            List<Designation> designations = new ArrayList<>();

            try (Connection conn = DBconnection.getInstance();
                 PreparedStatement ps = conn.prepareStatement(FETCH_ALL_DESIGNATIONS_SQL);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Designation des = new Designation();
                    des.setDesignationId(rs.getInt("designation_id"));
                    des.setDesignationName(rs.getString("designation_name"));
                    des.setDepartmentId(rs.getInt("department_id"));
                    designations.add(des);
                }

                log.info("Fetched {} designations", designations.size());
                return designations;

            } catch (SQLException e) {
                log.error("Error fetching designations", e);
                throw new RuntimeException(e);
            }
        }
    }


