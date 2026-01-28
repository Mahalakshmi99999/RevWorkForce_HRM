package org.example.models;

public class LoginCredentials {
        private int loginId;
        private int employeeId;
        private String passwordHash;
        private String securityQuestion;
        private String securityAnswer;

        public int getLoginId() {
            return loginId;
        }
        public void setLoginId(int loginId) {
            this.loginId = loginId;
        }
        public int getEmployeeId() {
            return employeeId;
        }
        public void setEmployeeId(int employeeId) {
            this.employeeId = employeeId;
        }
        public String getPasswordHash() {
            return passwordHash;
        }
        public void setPasswordHash(String passwordHash) {
            this.passwordHash = passwordHash;
        }
        public String getSecurityQuestion() {
            return securityQuestion;
        }
        public void setSecurityQuestion(String securityQuestion) {
            this.securityQuestion = securityQuestion;
        }
        public String getSecurityAnswer() {
            return securityAnswer;
        }
        public void setSecurityAnswer(String securityAnswer) {
            this.securityAnswer = securityAnswer;
        }
    }


