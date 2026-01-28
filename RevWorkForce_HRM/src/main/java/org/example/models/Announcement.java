package org.example.models;

import java.time.LocalDate;

public class Announcement {
        private int announcementId;
        private String title;
        private String message;
        private int postedBy;
        private LocalDate postedDate;
        private Integer holidayId;

        public int getAnnouncementId() {
            return announcementId;
        }
        public void setAnnouncementId(int announcementId) {
            this.announcementId = announcementId;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
        public int getPostedBy() {
            return postedBy;
        }
        public void setPostedBy(int postedBy) {
            this.postedBy = postedBy;
        }
        public LocalDate getPostedDate() {
            return postedDate;
        }
        public void setPostedDate(LocalDate postedDate) {
            this.postedDate = postedDate;
        }
        public Integer getHolidayId() {
            return holidayId;
        }
        public void setHolidayId(Integer holidayId) {
            this.holidayId = holidayId;
        }
    }


