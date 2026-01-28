package org.example.controller;

import org.example.models.AuditLog;
import org.example.service.AuditLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AdminAuditLogController {

        private static final Logger log = LoggerFactory.getLogger(AdminAuditLogController.class);

        private final AuditLogService service = new AuditLogService();

        public void viewAuditLogs() {

            List<AuditLog> logs = service.getAllLogs();

            if (logs.isEmpty()) {
                log.info("No audit logs found");
                return;
            }

            log.info("===== AUDIT LOGS =====");
            for (AuditLog a : logs) {
                log.info(
                        "LogId={} | EmpId={} | Action={} | Remarks={} | Time={}",
                        a.getLogId(),
                        a.getEmployeeId(),
                        a.getAction(),
                        a.getRemarks(),
                        a.getTimestamp()
                );
            }
        }
    }


