package org.example.controller;

import org.example.dto.EmployeeDTO;
import org.example.service.EmployeeDirectoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EmployeeDirectoryController {

    private static final Logger log =
            LoggerFactory.getLogger(EmployeeDirectoryController.class);

    private final EmployeeDirectoryService service;

    public EmployeeDirectoryController() {
        this.service = new EmployeeDirectoryService();
    }

    public void viewEmployeeDirectory() {

        List<EmployeeDTO> employees = service.getEmployeeDirectory();

        if (employees.isEmpty()) {
            log.info("Employee directory is empty");
            return;
        }

        log.info("===== EMPLOYEE DIRECTORY =====");

        for (EmployeeDTO e : employees) {
            log.info(
                    "ID={} | {} {} | Dept={} | Designation={} | Email={} | Phone={} | Status={}",
                    e.getEmployeeId(),
                    e.getFirstName(),
                    e.getLastName(),
                    e.getDepartmentName(),
                    e.getDesignationName(),
                    e.getEmail(),
                    e.getPhone(),
                    e.getStatus()
            );
        }
    }
}
