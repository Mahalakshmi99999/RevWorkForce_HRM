package org.example.service;

import org.example.Dao.EmployeeDirectoryDAO;
import org.example.dto.EmployeeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EmployeeDirectoryService {

    private static final Logger log =
            LoggerFactory.getLogger(EmployeeDirectoryService.class);

    private final EmployeeDirectoryDAO dao;

    public EmployeeDirectoryService() {
        this.dao = new EmployeeDirectoryDAO();
    }

    public List<EmployeeDTO> getEmployeeDirectory() {

        List<EmployeeDTO> list = dao.fetchEmployeeDirectory();

        log.info("Service: {} employees fetched for directory", list.size());
        return list;
    }
}
