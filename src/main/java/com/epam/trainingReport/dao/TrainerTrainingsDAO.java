package com.epam.trainingReport.dao;
import com.epam.trainingReport.model.TrainingRecord;

import java.util.List;
import java.util.Map;

public interface TrainerTrainingsDAO {

    List<TrainingRecord> getTrainerTrainingsByUsername(String username);
    void add(TrainingRecord trainingRecord);
    void delete(TrainingRecord trainingRecord);
}
