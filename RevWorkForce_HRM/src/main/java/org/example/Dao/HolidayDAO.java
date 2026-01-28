package org.example.Dao;

import org.example.Config.DBconnection;
import org.example.models.Holiday;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HolidayDAO {

    private static final Logger log =
            LoggerFactory.getLogger(HolidayDAO.class);

    private static final String FETCH_ALL_SQL =
            "SELECT holiday_id, holiday_date, holiday_name " +
                    "FROM holiday ORDER BY holiday_date";


    public List<Holiday> findAll() {

        List<Holiday> holidays = new ArrayList<>();

        try (Connection conn = DBconnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(FETCH_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Holiday h = new Holiday();
                h.setHolidayId(rs.getInt("holiday_id"));
                h.setHolidayDate(rs.getDate("holiday_date").toLocalDate());
                h.setHolidayName(rs.getString("holiday_name"));
                holidays.add(h);
            }

            log.info("Fetched {} holidays from database", holidays.size());
            return holidays;

        } catch (SQLException e) {
            log.error("Error while fetching holidays", e);
            throw new RuntimeException(e);
        }
    }
}
