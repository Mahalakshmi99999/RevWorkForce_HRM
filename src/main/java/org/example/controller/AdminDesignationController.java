package org.example.controller;
import org.example.models.Designation;
import org.example.service.AdminDesignationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;


public class AdminDesignationController {

        private static final Logger log = LoggerFactory.getLogger(AdminDesignationController.class);

        private final AdminDesignationService service =
                new AdminDesignationService();

        private final Scanner sc = new Scanner(System.in);

        public void addDesignation() {

            sc.nextLine(); // consume leftover newline

            log.info("Enter Designation Name:");
            String designationName = sc.nextLine().trim();

            if (designationName.isEmpty()) {
                log.warn("Designation name cannot be empty");
                return;
            }

            log.info("Enter Department ID:");
            int departmentId = sc.nextInt();

            boolean success =
                    service.addDesignation(designationName, departmentId);

            if (success) {
                log.info("Designation added successfully");
            } else {
                log.warn("Failed to add designation");
            }
        }


        public void viewDesignations() {

            List<Designation> designations =
                    service.getAllDesignations();

            if (designations.isEmpty()) {
                log.info("No designations found");
                return;
            }

            log.info("===== DESIGNATIONS =====");
            for (Designation d : designations) {
                log.info(
                        "ID={} | Name={} | DepartmentId={}",
                        d.getDesignationId(),
                        d.getDesignationName(),
                        d.getDepartmentId()
                );
            }
        }
    }


