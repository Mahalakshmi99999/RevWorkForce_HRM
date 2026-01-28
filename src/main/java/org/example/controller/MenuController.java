
package org.example.controller;

import org.example.models.LoggedInUserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class MenuController {

    private static final Logger log =
            LoggerFactory.getLogger(MenuController.class);

    private final Scanner sc = new Scanner(System.in);

    public void showMenu() {

        String role = LoggedInUserContext.getRoleName();

        switch (role) {
            case "EMPLOYEE" -> showEmployeeMenu();
            case "MANAGER" -> showManagerMenu();
            case "ADMIN" -> showAdminMenu();
            default -> log.error("Unknown role: {}", role);
        }
    }

    private void showEmployeeMenu() {

        EmployeeController employeeController = new EmployeeController();
        HolidayController holidayController = new HolidayController();
        LeaveController leaveController = new LeaveController();
        LeaveStatusController leaveStatusController = new LeaveStatusController();
        CancelLeaveController cancelLeaveController = new CancelLeaveController();
        LeaveBalanceController leaveBalanceController = new LeaveBalanceController();
        NotificationController notificationController = new NotificationController();
        PerformanceReviewController performanceReviewController = new PerformanceReviewController();
        GoalController goalController = new GoalController();
        EmployeePerformanceFeedbackController feedbackController = new EmployeePerformanceFeedbackController();
        EmployeeDirectoryController directoryController = new EmployeeDirectoryController();

        while (true) {
            log.info("===== EMPLOYEE MENU =====");
            log.info("1. View Profile");
            log.info("2. Update Profile");
            log.info("3. Change Password");
            log.info("4. View Holiday Calendar");
            log.info("5. Apply Leave");
            log.info("6. View Leave Status");
            log.info("7. Cancel Pending Leave");
            log.info("8. View Leave Balance");
            log.info("9. View Notifications");
            log.info("10. Create Performance Review");
            log.info("11. View My Performance Reviews");
            log.info("12. Add Goal to Performance Review");
            log.info("13. View Manager Feedback");
            log.info("14. Employee Directory");
            log.info("15. Logout");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> employeeController.viewProfile();
                case 2 -> employeeController.updateProfile();
                case 3 -> employeeController.showChangePassword();

                case 4 -> holidayController.viewHolidayCalendar();
                case 5 -> leaveController.applyLeave();
                case 6 -> leaveStatusController.viewLeaveStatus();
                case 7 -> cancelLeaveController.cancelPendingLeave();
                case 8 -> leaveBalanceController.viewLeaveBalance();
                case 9 -> notificationController.viewNotifications();
                case 10 -> performanceReviewController.createSelfReview();
                case 11 -> performanceReviewController.viewMyReviews();
                case 12 -> goalController.addGoal();
                case 13 -> feedbackController.viewMyFeedback();

                case 14 -> directoryController.viewEmployeeDirectory();

                case 15 -> {
                    log.info("Employee logged out");
                    LoggedInUserContext.clear();
                    return;
                }

                default -> log.warn("Invalid choice: {}", choice);
            }
        }
    }

    private void showManagerMenu() {

        ManagerLeaveController managerLeaveController = new ManagerLeaveController();
        ManagerPerformanceReviewController managerReviewController = new ManagerPerformanceReviewController();
        ManagerTeamController teamController = new ManagerTeamController();
        ManagerTeamLeaveCalendarController calendarController = new ManagerTeamLeaveCalendarController();
        ManagerTeamPerformanceSummaryController teamPerformanceController = new ManagerTeamPerformanceSummaryController();

        while (true) {
            log.info("===== MANAGER MENU =====");
            log.info("1. View My Team");
            log.info("2. View Pending Leave Requests");
            log.info("3. Approve / Reject Leave");
            log.info("4. View Submitted Performance Reviews");
            log.info("5. Review Employee Performance");
            log.info("6. View Team Leave Calendar");
            log.info("7. View Team Performance Summary");
            log.info("8. Logout");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> teamController.viewMyTeam();
                case 2 -> managerLeaveController.viewPendingLeaves();
                case 3 -> managerLeaveController.approveOrRejectLeave();
                case 4 -> managerReviewController.viewSubmittedReviews();
                case 5 -> managerReviewController.reviewEmployeePerformance();
                case 6 -> calendarController.viewTeamLeaveCalendar();
                case 7 -> teamPerformanceController.viewTeamPerformanceSummary();

                case 8 -> {
                    log.info("Manager logged out");
                    LoggedInUserContext.clear();
                    return;
                }

                default -> log.warn("Invalid choice: {}", choice);
            }
        }
    }

    private void showAdminMenu() {

        AdminEmployeeController adminEmployeeController = new AdminEmployeeController();
        AdminAssignManagerController assignManagerController = new AdminAssignManagerController();
        AdminAssignLeaveBalanceController assignLeaveBalanceController = new AdminAssignLeaveBalanceController();
        AdminDepartmentController departmentController = new AdminDepartmentController();
        AdminDesignationController designationController = new AdminDesignationController();
        AdminEmployeeAssignmentController assignmentController = new AdminEmployeeAssignmentController();
        AdminAuditLogController auditLogController = new AdminAuditLogController();
        AdminDepartmentLeaveReportController leaveReportController = new AdminDepartmentLeaveReportController();

        while (true) {
            log.info("===== ADMIN MENU =====");
            log.info("1. View All Employees");
            log.info("2. Add Employee");
            log.info("3. Assign Manager to Employee");
            log.info("4. Assign Leave Balance to Employee");
            log.info("5. Add Department");
            log.info("6. View Departments");
            log.info("7. Add Designation");
            log.info("8. View Designations");
            log.info("9. Assign Department & Designation to Employee");
            log.info("10. View Audit Logs");
            log.info("11. View Department-wise Leave Report");
            log.info("12. Logout");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> adminEmployeeController.viewAllEmployees();
                case 2 -> adminEmployeeController.addEmployee();
                case 3 -> assignManagerController.assignManager();
                case 4 -> assignLeaveBalanceController.assignLeaveBalance();
                case 5 -> departmentController.addDepartment();
                case 6 -> departmentController.viewDepartments();
                case 7 -> designationController.addDesignation();
                case 8 -> designationController.viewDesignations();
                case 9 -> assignmentController.assignDepartmentAndDesignation();
                case 10 -> auditLogController.viewAuditLogs();
                case 11 -> leaveReportController.viewDepartmentWiseLeaveReport();

                case 12 -> {
                    log.info("Admin logged out");
                    LoggedInUserContext.clear();
                    return;
                }

                default -> log.warn("Invalid choice: {}", choice);
            }
        }
    }
}
