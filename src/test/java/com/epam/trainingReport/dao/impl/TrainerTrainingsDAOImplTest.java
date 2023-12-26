package com.epam.trainingReport.dao.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.trainingReport.model.TrainingRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TrainerTrainingsDAOImplTest {

    @InjectMocks
    private TrainerTrainingsDAOImpl trainerTrainingsDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addTrainingRecord_shouldAddTrainingRecordToList() {
        TrainingRecord newTrainingRecord = createTrainingRecord(LocalDateTime.now(), Duration.ofMinutes(45));

        trainerTrainingsDAO.add(newTrainingRecord);

        assertEquals(1, trainerTrainingsDAO.getTrainerTrainingsByUsername("John.Doe").size());
        assertEquals(newTrainingRecord, trainerTrainingsDAO.getTrainerTrainingsByUsername("John.Doe").get(0));
    }

    @Test
    void deleteTrainingRecord_shouldRemoveTrainingRecordFromList() {
        TrainingRecord newTrainingRecord = createTrainingRecord(LocalDateTime.now(), Duration.ofMinutes(45));
        trainerTrainingsDAO.add(newTrainingRecord);
        TrainingRecord trainingRecordToDelete = trainerTrainingsDAO.getTrainerTrainingsByUsername("John.Doe").get(0);

        assertEquals(newTrainingRecord, trainingRecordToDelete);

        trainerTrainingsDAO.delete(trainingRecordToDelete);

        assertEquals(0, trainerTrainingsDAO.getTrainerTrainingsByUsername("John.Doe").size());
        assertFalse(trainerTrainingsDAO.getTrainerTrainingsByUsername("John.Doe").contains(trainingRecordToDelete));
    }

    @Test
    void getTrainerTrainingsByUsername_shouldReturnListOfTrainings() {
        List<TrainingRecord> trainings = trainerTrainingsDAO.getTrainerTrainingsByUsername("Kate.Smith");
        assertEquals(0, trainings.size());
    }


    private TrainingRecord createTrainingRecord(LocalDateTime dateAndTime, Duration duration) {
        TrainingRecord record = new TrainingRecord();
        record.setUsername("John.Doe");
        record.setActive(true);
        record.setDateAndTime(dateAndTime);
        record.setDuration(duration);
        return record;
    }

}
