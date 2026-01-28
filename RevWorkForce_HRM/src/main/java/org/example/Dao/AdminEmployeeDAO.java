package org.example.Dao;

import org.example.Config.DBconnection;
import org.example.models.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AdminEmployeeDAO {

    private static final Logger log = LoggerFactory.getLogger(AdminEmployeeDAO.class);

    private static final String FIND_ALL_EMPLOYEES_SQL =
            "SELECT employee_id, first_name, last_name, email, phone, address, " +
                    "dob, joining_date, salary, status, department_id, designation_id, " +
                    "manager_id, role_id " +
                    "FROM employee";

    public List<Employee> findAllEmployees() {

        List<Employee> employees = new ArrayList<>();

        try (Connection conn = DBconnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(FIND_ALL_EMPLOYEES_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
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
                emp.setManagerId((Integer) rs.getObject("manager_id"));
                emp.setRoleId((Integer) rs.getObject("role_id"));

                employees.add(emp);
            }

            log.info("Admin fetched {} employees", employees.size());
            return employees;

        } catch (SQLException e) {
            log.error("Error fetching all employees (admin)", e);
            throw new RuntimeException(e);
        }
    }

    // to add employee
    // DAO
    public int addEmployeeAndReturnId(Employee emp) {

        String sql =
                "INSERT INTO employee (" +
                        "first_name, last_name, email, phone, address, dob, joining_date, " +
                        "salary, status, department_id, designation_id, manager_id, role_id" +
                        ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBconnection.getInstance();
             PreparedStatement ps =
                     conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, emp.getFirstName());
            ps.setString(2, emp.getLastName());
            ps.setString(3, emp.getEmail());
            ps.setString(4, emp.getPhone());
            ps.setString(5, emp.getAddress());
            ps.setDate(6, emp.getDob() != null ? java.sql.Date.valueOf(emp.getDob()) : null);
            ps.setDate(7, emp.getJoiningDate() != null ? java.sql.Date.valueOf(emp.getJoiningDate()) : null);
            ps.setBigDecimal(8, emp.getSalary());
            ps.setString(9, emp.getStatus() != null ? emp.getStatus() : "ACTIVE");
            ps.setObject(10, emp.getDepartmentId());
            ps.setObject(11, emp.getDesignationId());
            ps.setObject(12, emp.getManagerId());
            ps.setInt(13, emp.getRoleId());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                log.warn("Employee insert failed. email={}", emp.getEmail());
                return -1;
            }

            // for getting auto generated password
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int employeeId = rs.getInt(1);
                    log.info("Employee added. employeeId={}, email={}",
                            employeeId, emp.getEmail());
                    return employeeId;
                }
            }

            log.error("Employee inserted but employee_id not generated");
            return -1;

        } catch (SQLException e) {
            log.error("Error adding employee. email={}", emp.getEmail(), e);
            throw new RuntimeException(e);
        }
    }
}