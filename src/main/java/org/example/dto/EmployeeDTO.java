package org.example.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeDTO {

    private int employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;

    private LocalDate dob;
    private LocalDate joiningDate;

    private BigDecimal salary;
    private String status;

    private Integer departmentId;
    private Integer designationId;
    private Integer managerId;
    private Integer roleId;

    private String departmentName;
    private String designationName;

    // ===== getters & setters =====

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public LocalDate getJoiningDate() { return joiningDate; }
    public void setJoiningDate(LocalDate joiningDate) { this.joiningDate = joiningDate; }

    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getDepartmentId() { return departmentId; }
    public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; }

    public Integer getDesignationId() { return designationId; }
    public void setDesignationId(Integer designationId) { this.designationId = designationId; }

    public Integer getManagerId() { return managerId; }
    public void setManagerId(Integer managerId) { this.managerId = managerId; }

    public Integer getRoleId() { return roleId; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public String getDesignationName() { return designationName; }
    public void setDesignationName(String designationName) { this.designationName = designationName; }
}
