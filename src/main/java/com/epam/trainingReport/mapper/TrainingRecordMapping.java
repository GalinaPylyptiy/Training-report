package com.epam.trainingReport.mapper;

import com.epam.trainingReport.dto.TrainingReportRequest;
import com.epam.trainingReport.model.TrainingRecord;
import com.epam.trainingReport.model.TrainingReport;
import org.springframework.stereotype.Component;


@Component
public class TrainingRecordMapping {

    public TrainingRecord toModel(TrainingReportRequest request){
        TrainingRecord record = new TrainingRecord();
        record.setUsername(request.getUsername());
        record.setFirstName(request.getFirstName());
        record.setLastName(request.getLastName());
        boolean status = request.getActive();
        if(status){
            record.setActive(true);
        }else {
            record.setActive(false);
        }
        record.setDateAndTime(request.getDate());
        record.setDuration(request.getDuration());
        return record;
    }

    public TrainingReport toTrainingReportWithoutYearsList(TrainingRecord record){
        TrainingReport trainingReport = new TrainingReport();
        trainingReport.setUsername(record.getUsername());
        trainingReport.setFirstName(record.getFirstName());
        trainingReport.setLastName(record.getLastName());
        trainingReport.setActive(record.getActive());
        return trainingReport;
    }
}
