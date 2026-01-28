package org.example.Dao;

import org.example.Config.DBconnection;
import org.example.dto.EmployeeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDirectoryDAO {

    private static final Logger log =
            LoggerFactory.getLogger(EmployeeDirectoryDAO.class);

    private static final String FETCH_EMPLOYEE_DIRECTORY_SQL =
            "SELECT employee_id, first_name, last_name, email, phone, address, " +
                    "dob, joining_date, salary, status, department_id, designation_id, " +
                    "manager_id, role_id " +
                    "FROM employee WHERE status = 'ACTIVE'";

    public List<EmployeeDTO> fetchEmployeeDirectory() {

        List<EmployeeDTO> employees = new ArrayList<>();

        try (Connection conn = DBconnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(FETCH_EMPLOYEE_DIRECTORY_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                EmployeeDTO dto = new EmployeeDTO();

                dto.setEmployeeId(rs.getInt("employee_id"));
                dto.setFirstName(rs.getString("first_name"));
                dto.setLastName(rs.getString("last_name"));
                dto.setEmail(rs.getString("email"));
                dto.setPhone(rs.getString("phone"));
                dto.setAddress(rs.getString("address"));

                if (rs.getDate("dob") != null) {
                    dto.setDob(rs.getDate("dob").toLocalDate());
                }

                if (rs.getDate("joining_date") != null) {
                    dto.setJoiningDate(rs.getDate("joining_date").toLocalDate());
                }

                dto.setSalary(rs.getBigDecimal("salary"));
                dto.setStatus(rs.getString("status"));
                dto.setDepartmentId((Integer) rs.getObject("department_id"));
                dto.setDesignationId((Integer) rs.getObject("designation_id"));
                dto.setManagerId((Integer) rs.getObject("manager_id"));
                dto.setRoleId((Integer) rs.getObject("role_id"));

                employees.add(dto);
            }

            log.info("Employee Directory fetched. count={}", employees.size());
            return employees;

        } catch (SQLException e) {
            log.error("Error fetching employee directory", e);
            throw new RuntimeException(e);
        }
    }
}

