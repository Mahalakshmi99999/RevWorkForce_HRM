package org.example.service;

import org.example.Dao.HolidayDAO;
import org.example.models.Holiday;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HolidayService {

    private static final Logger log =
            LoggerFactory.getLogger(HolidayService.class);

    private final HolidayDAO holidayDAO = new HolidayDAO();


    public List<Holiday> getAllHolidays() {

        List<Holiday> holidays = holidayDAO.findAll();

        if (holidays.isEmpty()) {
            log.warn("No holidays found in system");
        } else {
            log.info("Returning {} holidays", holidays.size());
        }

        return holidays;
    }
}

