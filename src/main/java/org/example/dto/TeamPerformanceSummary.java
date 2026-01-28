package org.example.dto;

public class TeamPerformanceSummary {

        private int employeeId;
        private String employeeName;
        private int reviewYear;

        private Integer selfRating;
        private Integer managerRating;
        private String status;


        public int getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(int employeeId) {
            this.employeeId = employeeId;
        }

        public String getEmployeeName() {
            return employeeName;
        }

        public void setEmployeeName(String employeeName) {
            this.employeeName = employeeName;
        }

        public int getReviewYear() {
            return reviewYear;
        }

        public void setReviewYear(int reviewYear) {
            this.reviewYear = reviewYear;
        }

        public Integer getSelfRating() {
            return selfRating;
        }

        public void setSelfRating(Integer selfRating) {
            this.selfRating = selfRating;
        }

        public Integer getManagerRating() {
            return managerRating;
        }

        public void setManagerRating(Integer managerRating) {
            this.managerRating = managerRating;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


