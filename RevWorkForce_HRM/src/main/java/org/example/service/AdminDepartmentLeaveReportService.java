package org.example.service;

import org.example.Dao.AdminDepartmentLeaveReportDAO;
import org.example.dto.AdminDepartmentLeaveReportDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AdminDepartmentLeaveReportService {

        private static final Logger log = LoggerFactory.getLogger(AdminDepartmentLeaveReportService.class);

        private final AdminDepartmentLeaveReportDAO reportDAO;

        public AdminDepartmentLeaveReportService() {
            this.reportDAO = new AdminDepartmentLeaveReportDAO();
        }

        public List<AdminDepartmentLeaveReportDTO> getDepartmentWiseLeaveReport() {

            List<AdminDepartmentLeaveReportDTO> reports =
                    reportDAO.fetchDepartmentWiseLeaveReport();

            log.info("Service: {} department-wise leave records returned",
                    reports.size());

            return reports;
        }
    }


