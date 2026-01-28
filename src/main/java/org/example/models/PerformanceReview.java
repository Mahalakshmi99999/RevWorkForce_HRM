package org.example.models;

import java.time.LocalDate;

public class PerformanceReview {

    private int reviewId;
    private int employeeId;
    private int managerId;
    private int reviewYear;

    private String deliverables;
    private String accomplishments;
    private String improvements;

    private int selfRating;
    private Integer managerRating;
    private String managerFeedback;

    private String status;
    private LocalDate submittedDate;

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public int getReviewYear() {
        return reviewYear;
    }

    public void setReviewYear(int reviewYear) {
        this.reviewYear = reviewYear;
    }

    public String getDeliverables() {
        return deliverables;
    }

    public void setDeliverables(String deliverables) {
        this.deliverables = deliverables;
    }

    public String getAccomplishments() {
        return accomplishments;
    }

    public void setAccomplishments(String accomplishments) {
        this.accomplishments = accomplishments;
    }

    public String getImprovements() {
        return improvements;
    }

    public void setImprovements(String improvements) {
        this.improvements = improvements;
    }

    public int getSelfRating() {
        return selfRating;
    }

    public void setSelfRating(int selfRating) {
        this.selfRating = selfRating;
    }

    public Integer getManagerRating() {
        return managerRating;
    }

    public void setManagerRating(Integer managerRating) {
        this.managerRating = managerRating;
    }

    public String getManagerFeedback() {
        return managerFeedback;
    }

    public void setManagerFeedback(String managerFeedback) {
        this.managerFeedback = managerFeedback;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(LocalDate submittedDate) {
        this.submittedDate = submittedDate;
    }
}
