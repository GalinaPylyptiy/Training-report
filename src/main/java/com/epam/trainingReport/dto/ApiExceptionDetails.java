package com.epam.trainingReport.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public class ApiExceptionDetails {

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "status")
    private HttpStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "dateAndTime")
    private LocalDateTime dateAndTime;

    public ApiExceptionDetails(String message, HttpStatus status, LocalDateTime dateAndTime) {
        this.message = message;
        this.status = status;
        this.dateAndTime = dateAndTime;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

}
