package org.example.controller;

import org.example.models.Holiday;
import org.example.service.HolidayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HolidayController {

    private static final Logger log =
            LoggerFactory.getLogger(HolidayController.class);

    private final HolidayService holidayService = new HolidayService();

    public void viewHolidayCalendar() {

        List<Holiday> holidays = holidayService.getAllHolidays();

        if (holidays.isEmpty()) {
            log.info("No holidays configured");
            return;
        }

        log.info("===== COMPANY HOLIDAY CALENDAR =====");

        for (Holiday h : holidays) {
            log.info("{} - {}", h.getHolidayDate(), h.getHolidayName());
        }
    }
}

