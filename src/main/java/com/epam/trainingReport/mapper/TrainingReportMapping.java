package com.epam.trainingReport.mapper;

import com.epam.trainingReport.dto.TrainingReportResponse;
import com.epam.trainingReport.model.TrainingReport;
import org.springframework.stereotype.Component;

@Component
public class TrainingReportMapping {

    public TrainingReportResponse toDto(TrainingReport trainingReport){
        TrainingReportResponse trainingReportResponse = new TrainingReportResponse();
        trainingReportResponse.setFirstName(trainingReport.getFirstName());
        trainingReportResponse.setLastName(trainingReport.getLastName());
        trainingReportResponse.setUsername(trainingReport.getUsername());
        trainingReportResponse.setActive(trainingReport.getActive());
        trainingReportResponse.setYears(trainingReport.getYears());
        return trainingReportResponse;
    }
}
