package com.epam.trainingReport.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "training_reports")
public class TrainingReport {

    @Id
    private String id;

    @Indexed
    private String username;

    private String firstName;

    private String lastName;

    private Boolean active;

    private List<Years> years = new ArrayList<>();

    public TrainingReport() {
    }

    public TrainingReport(String username, String firstName, String lastName, Boolean active, List<Years> years) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
        this.years = years;
    }

    public String getReportId() {
        return id;
    }

    public void setReportId(String reportId) {
        this.id = reportId;
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

    public List<Years> getYears() {
        return years;
    }

    public void setYears(List<Years> years) {
        this.years = years;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingReport that = (TrainingReport) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(username, that.username) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(active, that.active) &&
                Objects.equals(years, that.years);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, firstName, lastName, active, years);
    }
}
