package org.example.service;
import org.example.Dao.AdminDesignationDAO;
import org.example.models.Designation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AdminDesignationService {

        private static final Logger log = LoggerFactory.getLogger(AdminDesignationService.class);

        private final AdminDesignationDAO designationDAO;

        public AdminDesignationService() {
            this.designationDAO = new AdminDesignationDAO();
        }

        // Add designation
        public boolean addDesignation(String designationName, int departmentId) {

            boolean added =
                    designationDAO.addDesignation(designationName, departmentId);

            if (added) {
                log.info("Service: Designation added successfully: {}",
                        designationName);
            } else {
                log.warn("Service: Failed to add designation: {}",
                        designationName);
            }

            return added;
        }

        // Fetch all designations
        public List<Designation> getAllDesignations() {

            List<Designation> designations =
                    designationDAO.getAllDesignations();

            log.info("Service: {} designations returned", designations.size());
            return designations;
        }
    }


