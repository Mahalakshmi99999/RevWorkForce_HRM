package org.example.models;

public class LoggedInUserContext {

    private static Integer employeeId;
    private static Integer managerId;
    private static String roleName;

    public static Integer getEmployeeId() {

        return employeeId;
    }

    public static void setEmployeeId(Integer employeeId) {
        LoggedInUserContext.employeeId = employeeId;
    }

    public static Integer getManagerId() {
        return managerId;
    }

    public static void setManagerId(Integer managerId) {
        LoggedInUserContext.managerId = managerId;
    }


    public static String getRoleName() {
        return roleName;
    }

    public static void setRoleName(String roleName) {
        LoggedInUserContext.roleName = roleName;
    }


    public static void clear() {
        employeeId = null;
        managerId = null;
        roleName = null;
    }
}