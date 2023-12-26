package com.epam.trainingReport.model;


import java.util.Objects;

public class Months {

    private String name;

    private Long summaryDuration;

    public Months() {
    }

    public Months(String name, Long summaryDuration) {
        this.name = name;
        this.summaryDuration = summaryDuration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSummaryDuration() {
        return summaryDuration;
    }

    public void setSummaryDuration(Long summaryDuration) {
        this.summaryDuration = summaryDuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Months months = (Months) o;
        return Objects.equals(name, months.name) &&
                Objects.equals(summaryDuration, months.summaryDuration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, summaryDuration);
    }

    @Override
    public String toString() {
        return "Months{" +
                "name='" + name + '\'' +
                ", summaryDuration=" + summaryDuration +
                '}';
    }
}
