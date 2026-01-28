package org.example.Dao;

import org.example.Config.DBconnection;
import org.example.models.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAO {

    private static final Logger log =
            LoggerFactory.getLogger(EmployeeDAO.class);


    private static final String FIND_BY_ID_SQL =
            """
            SELECT 
                e.employee_id,
                e.first_name,
                e.last_name,
                e.email,
                e.phone,
                e.address,
                e.dob,
                e.joining_date,
                e.salary,
                e.status,
                e.department_id,
                d.department_name,
                e.designation_id,
                des.designation_name,
                e.manager_id,
                e.role_id
            FROM employee e
            LEFT JOIN department d 
                ON e.department_id = d.department_id
            LEFT JOIN designation des 
                ON e.designation_id = des.designation_id
            WHERE e.employee_id = ?
            """;


    public Employee findById(int employeeId) {

        try (Connection conn = DBconnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)) {

            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Employee emp = mapRowToEmployee(rs);
                log.info("Employee fetched for employeeId={}", employeeId);
                return emp;
            }

            log.warn("No employee found for employeeId={}", employeeId);
            return null;

        } catch (SQLException e) {
            log.error("Error fetching employee for employeeId={}", employeeId, e);
            throw new RuntimeException(e);
        }
    }

    private Employee mapRowToEmployee(ResultSet rs) throws SQLException {

        Employee emp = new Employee();

        emp.setEmployeeId(rs.getInt("employee_id"));
        emp.setFirstName(rs.getString("first_name"));
        emp.setLastName(rs.getString("last_name"));
        emp.setEmail(rs.getString("email"));
        emp.setPhone(rs.getString("phone"));
        emp.setAddress(rs.getString("address"));

        if (rs.getDate("dob") != null) {
            emp.setDob(rs.getDate("dob").toLocalDate());
        }

        if (rs.getDate("joining_date") != null) {
            emp.setJoiningDate(rs.getDate("joining_date").toLocalDate());
        }

        emp.setSalary(rs.getBigDecimal("salary"));
        emp.setStatus(rs.getString("status"));

        emp.setDepartmentId((Integer) rs.getObject("department_id"));
        emp.setDesignationId((Integer) rs.getObject("designation_id"));

        emp.setDepartmentName(rs.getString("department_name"));
        emp.setDesignationName(rs.getString("designation_name"));

        emp.setManagerId((Integer) rs.getObject("manager_id"));
        emp.setRoleId((Integer) rs.getObject("role_id"));

        return emp;
    }


    private static final String UPDATE_PROFILE_SQL =
            "UPDATE employee SET phone = ?, address = ? WHERE employee_id = ?";

    public boolean updateProfile(int employeeId, String phone, String address) {

        try (Connection conn = DBconnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(UPDATE_PROFILE_SQL)) {

            ps.setString(1, phone);
            ps.setString(2, address);
            ps.setInt(3, employeeId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            log.error("Error updating profile for employeeId={}", employeeId, e);
            throw new RuntimeException(e);
        }
    }


    public Employee findEmployeeWithRole(int employeeId) {

        String SQL =
                """
                SELECT e.employee_id, e.manager_id, r.role_name
                FROM employee e
                JOIN role r ON e.role_id = r.role_id
                WHERE e.employee_id = ?
                """;

        try (Connection conn = DBconnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(SQL)) {

            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Employee emp = new Employee();
                emp.setEmployeeId(rs.getInt("employee_id"));
                emp.setManagerId((Integer) rs.getObject("manager_id"));
                emp.setRoleName(rs.getString("role_name"));
                return emp;
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
