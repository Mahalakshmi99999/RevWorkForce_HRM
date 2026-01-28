package org.example.Dao;

import org.example.Config.DBconnection;
import org.example.dto.AdminDepartmentLeaveReportDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdminDepartmentLeaveReportDAO {

    private static final Logger log = LoggerFactory.getLogger(AdminDepartmentLeaveReportDAO.class);

        private static final String DEPT_LEAVE_REPORT_SQL =
                """
                SELECT
                    d.department_name,
                    e.employee_id,
                    CONCAT(e.first_name, ' ', e.last_name) AS employee_name,
                    lt.leave_name,
                    la.start_date,
                    la.end_date,
                    la.working_days,
                    la.status
                FROM leave_application la
                JOIN employee e
                    ON la.employee_id = e.employee_id
                JOIN department d
                    ON e.department_id = d.department_id
                JOIN leave_type lt
                    ON la.leave_type_id = lt.leave_type_id
                ORDER BY d.department_name, la.start_date DESC
                """;

        public List<AdminDepartmentLeaveReportDTO> fetchDepartmentWiseLeaveReport() {

            List<AdminDepartmentLeaveReportDTO> reportList = new ArrayList<>();

            try (Connection conn = DBconnection.getInstance();
                 PreparedStatement ps = conn.prepareStatement(DEPT_LEAVE_REPORT_SQL);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    AdminDepartmentLeaveReportDTO dto =
                            new AdminDepartmentLeaveReportDTO();

                    dto.setDepartmentName(rs.getString("department_name"));
                    dto.setEmployeeId(rs.getInt("employee_id"));
                    dto.setEmployeeName(rs.getString("employee_name"));
                    dto.setLeaveType(rs.getString("leave_name"));
                    dto.setStartDate(rs.getDate("start_date").toLocalDate());
                    dto.setEndDate(rs.getDate("end_date").toLocalDate());
                    dto.setWorkingDays(rs.getInt("working_days"));
                    dto.setStatus(rs.getString("status"));

                    reportList.add(dto);
                }

                log.info("DAO: {} department-wise leave records fetched",
                        reportList.size());

                return reportList;

            } catch (Exception e) {
                log.error("Error fetching department-wise leave report", e);
                throw new RuntimeException(e);
            }
        }
    }


