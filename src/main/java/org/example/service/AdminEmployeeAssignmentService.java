package org.example.service;
import org.example.Dao.AdminEmployeeAssignmentDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AdminEmployeeAssignmentService {
        private static final Logger log = LoggerFactory.getLogger(AdminEmployeeAssignmentService.class);

        private final AdminEmployeeAssignmentDAO assignmentDAO;

        public AdminEmployeeAssignmentService() {
            this.assignmentDAO = new AdminEmployeeAssignmentDAO();
        }

        public boolean assignDepartmentAndDesignation(
                int employeeId,
                int departmentId,
                int designationId
        ) {

            boolean updated =
                    assignmentDAO.assignDepartmentAndDesignation(
                            employeeId,
                            departmentId,
                            designationId
                    );

            if (updated) {
                log.info(
                        "Service: Department & Designation assigned. employeeId={}",
                        employeeId
                );
            } else {
                log.warn(
                        "Service: Assignment failed. employeeId={}",
                        employeeId
                );
            }

            return updated;
        }
    }


