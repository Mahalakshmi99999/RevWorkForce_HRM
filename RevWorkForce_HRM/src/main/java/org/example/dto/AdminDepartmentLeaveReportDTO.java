package org.example.dto;

import java.time.LocalDate;

public class AdminDepartmentLeaveReportDTO {

    private String departmentName;

    private int employeeId;
    private String employeeName;

    private String leaveType;

    private LocalDate startDate;
    private LocalDate endDate;
    private int workingDays;
    private int totalLeaves;
    private int approvedLeaves;
    private int rejectedLeaves;
    private int pendingLeaves;

    private String status;

    // Getters and Setters

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

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

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(int workingDays) {
        this.workingDays = workingDays;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalLeaves() {
        return totalLeaves;
    }

    public void setTotalLeaves(int totalLeaves) {
        this.totalLeaves = totalLeaves;
    }

    public int getApprovedLeaves() {
        return approvedLeaves;
    }

    public void setApprovedLeaves(int approvedLeaves) {
        this.approvedLeaves = approvedLeaves;
    }

    public int getRejectedLeaves() {
        return rejectedLeaves;
    }

    public void setRejectedLeaves(int rejectedLeaves) {
        this.rejectedLeaves = rejectedLeaves;
    }

    public int getPendingLeaves() {
        return pendingLeaves;
    }

    public void setPendingLeaves(int pendingLeaves) {
        this.pendingLeaves = pendingLeaves;
    }

}
