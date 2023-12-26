package com.epam.trainingReport.service;

import com.epam.trainingReport.model.TrainingRecord;

public interface TrainerTrainingsService {

//    DurationReport getSummaryRecord(String username);

    void add(TrainingRecord trainingRecord);

    void delete(TrainingRecord trainingRecord);

}
