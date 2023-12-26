package com.epam.trainingReport.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class TrainingRecord {

    private String username;

    private String firstName;

    private String lastName;

    private Boolean active;

    private LocalDateTime dateAndTime;

    private Duration duration;

    public TrainingRecord() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingRecord that = (TrainingRecord) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(dateAndTime, that.dateAndTime) &&
                Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, dateAndTime, duration);
    }
}
