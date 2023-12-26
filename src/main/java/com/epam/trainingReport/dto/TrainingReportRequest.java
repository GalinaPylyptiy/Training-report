package com.epam.trainingReport.dto;

import com.epam.trainingReport.action.ActionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

public class TrainingReportRequest implements Serializable {

    @NotEmpty(message = "Username should not be empty or null")
    @JsonProperty(value = "trainerUsername")
    private String username;

    @NotEmpty(message = "First name should not be empty or null")
    @JsonProperty(value = "trainerFirstName")
    private String firstName;

    @NotBlank(message = "Last name should not be empty or null")
    @JsonProperty(value = "trainerLastName")
    private String lastName;

    @JsonProperty(value = "isActive")
    private Boolean isActive;

    @JsonProperty(value = "trainingDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Future(message = "Training date should be in the future")
    private LocalDateTime date;

    @JsonProperty(value = "trainingDuration")
    @NotNull(message = "Training duration should not be null")
    private Duration duration;

    @JsonProperty(value = "actionType")
    private ActionType actionType;

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
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    @Override
    public String toString() {
        return "TrainingReportRequest{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isActive=" + isActive +
                ", date=" + date +
                ", duration=" + duration +
                ", actionType=" + actionType +
                '}';
    }
}
