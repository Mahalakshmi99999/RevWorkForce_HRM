package org.example.controller;

import org.example.dto.AdminDepartmentLeaveReportDTO;
import org.example.service.AdminDepartmentLeaveReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AdminDepartmentLeaveReportController {

        private static final Logger log = LoggerFactory.getLogger(AdminDepartmentLeaveReportController.class);

        private final AdminDepartmentLeaveReportService reportService;

        public AdminDepartmentLeaveReportController() {
            this.reportService = new AdminDepartmentLeaveReportService();
        }

        public void viewDepartmentWiseLeaveReport() {

            List<AdminDepartmentLeaveReportDTO> reports =
                    reportService.getDepartmentWiseLeaveReport();

            if (reports.isEmpty()) {
                log.info("No department-wise leave report data found");
                return;
            }

            log.info("===== DEPARTMENT-WISE LEAVE REPORT =====");

            for (AdminDepartmentLeaveReportDTO dto : reports) {
                log.info(
                        "Department={} | Total Leaves={} | Approved={} | Rejected={} | Pending={}",
                        dto.getDepartmentName(),
                        dto.getTotalLeaves(),
                        dto.getApprovedLeaves(),
                        dto.getRejectedLeaves(),
                        dto.getPendingLeaves()
                );
            }
        }
    }


