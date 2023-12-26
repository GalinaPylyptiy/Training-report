package com.epam.trainingReport.mapper;

import com.epam.trainingReport.action.ActionType;
import com.epam.trainingReport.dto.TrainingReportRequest;
import com.epam.trainingReport.model.TrainingRecord;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;


class TrainingRecordMappingTest {

    @Test
    void toModel() {
        TrainingReportRequest request = new TrainingReportRequest();
        request.setUsername("testUser");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setActive(true);
        request.setDate(LocalDateTime.of(2023, 10, 25, 8, 0));
        request.setDuration(Duration.ofMinutes(60));
        request.setActionType(ActionType.ADD);

        TrainingRecordMapping trainingRecordMapping = new TrainingRecordMapping();

        TrainingRecord result = trainingRecordMapping.toModel(request);

        assertEquals("testUser", result.getUsername());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals(true, result.getActive());
        assertEquals(LocalDateTime.of(2023, 10, 25, 8, 0), result.getDateAndTime());
        assertEquals(60, result.getDuration().toMinutes());
    }
}
