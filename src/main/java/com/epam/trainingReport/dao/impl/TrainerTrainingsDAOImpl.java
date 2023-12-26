package com.epam.trainingReport.dao.impl;

import com.epam.trainingReport.dao.TrainerTrainingsDAO;
import com.epam.trainingReport.model.TrainingRecord;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TrainerTrainingsDAOImpl implements TrainerTrainingsDAO {

    private List<TrainingRecord> trainingRecords = new ArrayList<>();

    @PostConstruct
    private void populateListWithTestData() {
        List<TrainingRecord> trainingList = List.of(
                createTrainingRecord("Dan.Brown", false, LocalDateTime.of(2022, 1, 15, 12, 0), Duration.ofMinutes(60)),
                createTrainingRecord("Kate.Smith", true, LocalDateTime.of(2023, 2, 20, 15, 30),  Duration.ofMinutes(90)),
                createTrainingRecord("Kate.Smith", true, LocalDateTime.of(2022, 1, 5, 8, 45),  Duration.ofMinutes(30)),
                createTrainingRecord("Kate.Smith", true, LocalDateTime.of(2022, 12, 10, 9, 0), Duration.ofMinutes(45)),
                createTrainingRecord("Kate.Smith", true, LocalDateTime.of(2023, 1, 15, 12, 0), Duration.ofMinutes(60)),
                createTrainingRecord("Kate.Smith", true, LocalDateTime.of(2022, 2, 20, 15, 30), Duration.ofMinutes(70)),
                createTrainingRecord("Kate.Smith", true, LocalDateTime.of(2023, 1, 5, 8, 45), Duration.ofMinutes(60)),
                createTrainingRecord("Kate.Smith", true, LocalDateTime.of(2023, 2, 10, 9, 0), Duration.ofMinutes(50))
        );
        trainingRecords.addAll(trainingList);
    }

    private TrainingRecord createTrainingRecord(String username,Boolean status, LocalDateTime dateAndTime, Duration duration) {
        TrainingRecord record = new TrainingRecord();
        record.setUsername(username);
        record.setActive(status);
        record.setDateAndTime(dateAndTime);
        record.setDuration(duration);
        return record;
    }

    @Override
    public void add(TrainingRecord trainingRecord) {
        trainingRecords.add(trainingRecord);
    }

    @Override
    public void delete(TrainingRecord trainingRecord) {
        trainingRecords.remove(trainingRecord);
    }

    @Override
    public List<TrainingRecord> getTrainerTrainingsByUsername(String trainerUsername) {
       return trainingRecords.stream()
               .filter(trainingRecord -> trainingRecord.getUsername().equals(trainerUsername))
               .collect(Collectors.toList());
    }
}
