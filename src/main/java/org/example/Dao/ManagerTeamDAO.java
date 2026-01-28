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

public class ManagerTeamDAO {

        private static final Logger log = LoggerFactory.getLogger(ManagerTeamDAO.class);

        private static final String FIND_TEAM_SQL =
                "SELECT employee_id, first_name, last_name, email, phone, status, department_id, designation_id " +
                        "FROM employee " +
                        "WHERE manager_id = ? AND status = 'ACTIVE'";

        public List<Employee> findTeamMembers(int managerId) {

            List<Employee> team = new ArrayList<>();

            try (Connection conn = DBconnection.getInstance();
                 PreparedStatement ps = conn.prepareStatement(FIND_TEAM_SQL)) {

                ps.setInt(1, managerId);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Employee emp = new Employee();
                    emp.setEmployeeId(rs.getInt("employee_id"));
                    emp.setFirstName(rs.getString("first_name"));
                    emp.setLastName(rs.getString("last_name"));
                    emp.setEmail(rs.getString("email"));
                    emp.setPhone(rs.getString("phone"));
                    emp.setStatus(rs.getString("status"));
                    emp.setDepartmentId((Integer) rs.getObject("department_id"));
                    emp.setDesignationId((Integer) rs.getObject("designation_id"));

                    team.add(emp);
                }

                log.info("Fetched {} team members for managerId={}",
                        team.size(), managerId);

                return team;

            } catch (SQLException e) {
                log.error("Error fetching team members for managerId={}", managerId, e);
                throw new RuntimeException(e);
            }
        }
    }


