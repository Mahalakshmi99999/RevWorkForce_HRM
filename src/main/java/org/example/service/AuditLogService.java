package org.example.service;

import org.example.Dao.AuditLogDAO;
import org.example.models.AuditLog;

import java.util.List;

public class AuditLogService {

    private final AuditLogDAO dao = new AuditLogDAO();


    public List<AuditLog> getAllLogs() {
        return dao.fetchAllLogs();
    }


    public void logAction(
            int employeeId,
            String action,
            String remarks
    ) {
        dao.logAction(employeeId, action, remarks);
    }
}
