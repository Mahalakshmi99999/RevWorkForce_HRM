package org.example.models;
import java.time.LocalDateTime;

public class AuditLog {
        private int logId;
        private int employeeId;
        private String action;
        private LocalDateTime timestamp;
        private String remarks;

        public int getLogId() {
            return logId;
        }
        public void setLogId(int logId) {
            this.logId = logId;
        }
        public int getEmployeeId() {
            return employeeId;
        }
        public void setEmployeeId(int employeeId) {
            this.employeeId = employeeId;
        }
        public String getAction() {
            return action;
        }
        public void setAction(String action) {
            this.action = action;
        }
        public LocalDateTime getTimestamp() {
            return timestamp;
        }
        public void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }
        public String getRemarks() {
            return remarks;
        }
        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }
    }


