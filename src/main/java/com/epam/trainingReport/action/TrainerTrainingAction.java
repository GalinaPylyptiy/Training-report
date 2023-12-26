package com.epam.trainingReport.action;

import com.epam.trainingReport.model.TrainingRecord;

@FunctionalInterface
public interface TrainerTrainingAction {

    void perform(TrainingRecord trainingRecord);
}
