package com.epam.trainingReport.service.impl;

import com.epam.trainingReport.mapper.TrainingRecordMapping;
import com.epam.trainingReport.model.Months;
import com.epam.trainingReport.model.TrainingRecord;
import com.epam.trainingReport.model.TrainingReport;
import com.epam.trainingReport.model.Years;
import com.epam.trainingReport.repository.TrainingReportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingReportServiceImplTest {

    @Mock
    private TrainingReportRepository reportRepository;

    @Mock
    private TrainingRecordMapping trainingRecordMapping;

    @InjectMocks
    private TrainingReportServiceImpl reportService;

    @Test
    void findByUsername_existingUsername_shouldReturnTrainingReport() {
        // Arrange
        String existingUsername = "Paul.Smith";
        TrainingReport mockTrainingReport = createMockTrainingReport();
        when(reportRepository.findByUsername(existingUsername)).thenReturn(Optional.of(mockTrainingReport));

        TrainingReport result = reportService.findByUsername(existingUsername);

        assertNotNull(result);
        assertEquals(mockTrainingReport.getUsername(), result.getUsername());
        assertEquals(mockTrainingReport.getYears().size(), result.getYears().size());
    }

    @Test
    void findByUsername_nonExistingUsername_shouldReturnEmptyReport() {

        String nonExistingUsername = "nonExistingUser";
        when(reportRepository.findByUsername(nonExistingUsername)).thenReturn(Optional.empty());

        TrainingReport trainingReport = reportService.findByUsername(nonExistingUsername);

        assertNotNull(trainingReport);
        assertNull(trainingReport.getUsername());
        assertTrue(trainingReport.getYears().isEmpty());

    }

    @Test
    void update_existingTrainingReport_shouldUpdateAndReturnReport() {
        // Arrange
        TrainingRecord mockTrainingRecord = createMockTrainingRecord();

        TrainingReport mockTrainingReport = createMockTrainingReport();
        when(reportRepository.findByUsername(mockTrainingRecord.getUsername())).thenReturn(Optional.of(mockTrainingReport));

        reportService.update(mockTrainingRecord);
        TrainingReport result = reportService.findByUsername(mockTrainingReport.getUsername());

        assertNotNull(result);
        assertEquals(mockTrainingRecord.getUsername(), result.getUsername());
        assertEquals(1, result.getYears().size());
        assertEquals(1, result.getYears().get(0).getMonths().size());
        assertEquals(270L, result.getYears().get(0).getMonths().get(0).getSummaryDuration());
        verify(reportRepository, times(1)).save(any());

    }

    @Test
    void update_nonExistingTrainingReport_shouldCreateNewReport() {
        // Arrange
        TrainingRecord mockTrainingRecord = new TrainingRecord();
        mockTrainingRecord.setUsername("nonExistingUser");
        mockTrainingRecord.setDuration(Duration.ofMinutes(60));
        LocalDateTime dateTime = LocalDateTime.now();
        mockTrainingRecord.setDateAndTime(dateTime);

        TrainingReport mockTrainingReport = new TrainingReport();
        Months months = new Months(dateTime.getMonth().name(),60L);
        Years years = new Years();
        years.setYear(dateTime.getYear());
        years.setMonths(List.of(months));
        mockTrainingReport.setUsername("nonExistingUser");
        mockTrainingReport.setYears(List.of(years));

        when(reportRepository.findByUsername(mockTrainingRecord.getUsername())).thenReturn(Optional.empty());
        when(trainingRecordMapping.toTrainingReportWithoutYearsList(mockTrainingRecord)).thenReturn(mockTrainingReport);

        reportService.update(mockTrainingRecord);

        when(reportRepository.findByUsername(mockTrainingRecord.getUsername())).thenReturn(Optional.of(mockTrainingReport));

        TrainingReport result = reportService.findByUsername(mockTrainingRecord.getUsername());

        assertNotNull(result);
        assertEquals(mockTrainingRecord.getUsername(), result.getUsername());
        assertEquals(1, result.getYears().size());
        assertEquals(1, result.getYears().get(0).getMonths().size());
        assertEquals(60L, result.getYears().get(0).getMonths().get(0).getSummaryDuration());
        verify(reportRepository, times(1)).save(any());
    }

    private TrainingRecord createMockTrainingRecord() {
        TrainingRecord mockTrainingRecord = new TrainingRecord();
        mockTrainingRecord.setUsername("Paul.Smith");
        mockTrainingRecord.setFirstName("FirstName");
        mockTrainingRecord.setLastName("LastName");
        mockTrainingRecord.setActive(true);
        LocalDateTime dateTime = LocalDateTime.of(2023, 12, 15, 11, 10);
        mockTrainingRecord.setDateAndTime(dateTime);
        mockTrainingRecord.setDuration(Duration.ofMinutes(90));
        return mockTrainingRecord;
    }

    private TrainingReport createMockTrainingReport() {
        TrainingReport mockTrainingReport = new TrainingReport();
        mockTrainingReport.setUsername("Paul.Smith");
        mockTrainingReport.setFirstName("FirstName");
        mockTrainingReport.setLastName("LastName");
        mockTrainingReport.setActive(true);
        Months months = new Months(Month.DECEMBER.name(), 180L);
        Years years = new Years(2023, List.of(months));
        mockTrainingReport.setYears(List.of(years));
        return mockTrainingReport;
    }
}
