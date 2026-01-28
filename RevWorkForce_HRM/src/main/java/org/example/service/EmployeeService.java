package org.example.service;

import org.example.Dao.EmployeeDAO;
import org.example.models.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EmployeeService {

    private static final Logger log =
            LoggerFactory.getLogger(EmployeeService.class);

        private final EmployeeDAO employeeDAO = new EmployeeDAO();


        public Employee viewProfile(int employeeId) {
            return employeeDAO.findById(employeeId);
        }

        // to update profile

        public boolean updateProfile(int employeeId, String phone, String address) {

        if (phone == null || phone.isBlank() ||
                address == null || address.isBlank()) {

            log.warn("Invalid input for profile update employeeId={}", employeeId);
            return false;
        }

        return employeeDAO.updateProfile(employeeId, phone, address);
    }


}


