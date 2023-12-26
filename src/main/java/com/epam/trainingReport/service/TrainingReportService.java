package com.epam.trainingReport.service;

import com.epam.trainingReport.model.TrainingRecord;
import com.epam.trainingReport.model.TrainingReport;

public interface TrainingReportService {

    TrainingReport findByUsername(String username);

    void update(TrainingRecord record);

}
