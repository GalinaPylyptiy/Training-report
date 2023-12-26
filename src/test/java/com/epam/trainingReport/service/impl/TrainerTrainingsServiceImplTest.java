package com.epam.trainingReport.service.impl;

import com.epam.trainingReport.dao.TrainerTrainingsDAO;
import com.epam.trainingReport.exception.MonthlySummaryCalculatingException;
import com.epam.trainingReport.model.Months;
import com.epam.trainingReport.model.TrainingRecord;
import com.epam.trainingReport.model.Years;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainerTrainingsServiceImplTest {

    @Mock
    private TrainerTrainingsDAO mockTrainerTrainingsDAO;

    @InjectMocks
    private TrainerTrainingsServiceImpl trainerTrainingsService;



    @Test
    void add_Exception() {
        TrainingRecord trainingRecord = createTrainingRecord("testTrainer", LocalDateTime.now(), Duration.ofMinutes(30));

        doThrow(new RuntimeException("Test exception")).when(mockTrainerTrainingsDAO).add(trainingRecord);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> trainerTrainingsService.add(trainingRecord));
        assertEquals("Could not add new training for trainer " + trainingRecord.getUsername(), exception.getMessage());

        verify(mockTrainerTrainingsDAO, times(1)).add(trainingRecord);
    }

    @Test
    void delete_Success() {
        TrainingRecord trainingRecord = createTrainingRecord("testTrainer", LocalDateTime.now(), Duration.ofMinutes(30));

        trainerTrainingsService.delete(trainingRecord);

        verify(mockTrainerTrainingsDAO, times(1)).delete(trainingRecord);
    }

    @Test
    void delete_Exception() {
        TrainingRecord trainingRecord = createTrainingRecord("testTrainer", LocalDateTime.now(), Duration.ofMinutes(30));

        doThrow(new RuntimeException("Test exception")).when(mockTrainerTrainingsDAO).delete(trainingRecord);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> trainerTrainingsService.delete(trainingRecord));
        assertEquals("Could not delete training for trainer " + trainingRecord.getUsername(), exception.getMessage());

        verify(mockTrainerTrainingsDAO, times(1)).delete(trainingRecord);
    }

    private TrainingRecord createTrainingRecord(String username, LocalDateTime dateAndTime, Duration duration) {
        TrainingRecord record = new TrainingRecord();
        record.setUsername(username);
        record.setDateAndTime(dateAndTime);
        record.setDuration(duration);
        return record;
    }


}