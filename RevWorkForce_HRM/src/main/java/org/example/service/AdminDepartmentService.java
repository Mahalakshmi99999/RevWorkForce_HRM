package org.example.service;
import org.example.Dao.AdminDepartmentDAO;
import org.example.models.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AdminDepartmentService {

        private static final Logger log = LoggerFactory.getLogger(AdminDepartmentService.class);

        private final AdminDepartmentDAO departmentDAO;

        public AdminDepartmentService() {
            this.departmentDAO = new AdminDepartmentDAO();
        }

        // Add department
        public boolean addDepartment(String departmentName) {

            boolean added = departmentDAO.addDepartment(departmentName);

            if (added) {
                log.info("Service: Department added successfully: {}", departmentName);
            } else {
                log.warn("Service: Failed to add department: {}", departmentName);
            }

            return added;
        }

        // Fetch all departments
        public List<Department> getAllDepartments() {

            List<Department> departments = departmentDAO.getAllDepartments();

            log.info("Service: {} departments returned", departments.size());
            return departments;
        }
    }


