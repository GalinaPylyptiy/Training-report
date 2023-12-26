package com.epam.trainingReport.mapper;
import com.epam.trainingReport.dto.TrainingReportResponse;
import com.epam.trainingReport.model.Months;
import com.epam.trainingReport.model.TrainingReport;
import com.epam.trainingReport.model.Years;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class TrainingReportMappingTest {

    private TrainingReportMapping trainingReportMapping = new TrainingReportMapping();

    @Test
    public void shouldMapTrainingReportToDto(){
        TrainingReport trainingReport = trainingReport();
        TrainingReportResponse response = trainingReportMapping.toDto(trainingReport);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(trainingReport.getUsername(), response.getUsername());
        Assertions.assertTrue(response.getActive());
        Assertions.assertFalse(response.getYears().isEmpty());
    }

    private TrainingReport trainingReport(){
        TrainingReport trainingReport = new TrainingReport();
        trainingReport.setActive(true);
        trainingReport.setFirstName("Kate");
        trainingReport.setLastName("Smith");
        trainingReport.setUsername("Kate.Smith");
        Years years = new Years();
        Months months = new Months();
        months.setName("JANUARY");
        months.setSummaryDuration(210L);
        years.setMonths(List.of(months));
        years.setYear(2024);
        years.setMonths(List.of(months));
        trainingReport.setYears(List.of(years));
        return trainingReport;
    }

}