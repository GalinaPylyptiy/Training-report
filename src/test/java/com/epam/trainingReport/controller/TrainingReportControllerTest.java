package com.epam.trainingReport.controller;

import com.epam.trainingReport.action.TrainerTrainingsActionHandler;
import com.epam.trainingReport.dto.TrainingReportRequest;
import com.epam.trainingReport.dto.TrainingReportResponse;
import com.epam.trainingReport.mapper.TrainingRecordMapping;
import com.epam.trainingReport.mapper.TrainingReportMapping;
import com.epam.trainingReport.model.TrainingRecord;
import com.epam.trainingReport.model.TrainingReport;
import com.epam.trainingReport.service.TrainingReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingReportControllerTest {

    @Mock
    private TrainingReportService trainingReportService;

    @Mock
    private TrainerTrainingsActionHandler mockActionHandler;

    @Mock
    private TrainingRecordMapping mockTrainingRecordMapping;

    @Mock
    private TrainingReportMapping mockTrainingReportMapping;

    @InjectMocks
    private TrainingReportController trainingReportController;

    @Test
    void handleTraining() {
        TrainingReportRequest request = new TrainingReportRequest();
        TrainingRecord trainingRecord = new TrainingRecord();
        when(mockTrainingRecordMapping.toModel(request)).thenReturn(trainingRecord);

        trainingReportController.handleTraining(request);

        verify(mockTrainingRecordMapping, times(1)).toModel(request);
        verify(mockActionHandler, times(1)).handleRequestAction(request.getActionType(), trainingRecord);
    }

    @Test
    void getSummaryRecord(){
        String username = "Username";
        TrainingReport trainingReport = new TrainingReport();
        trainingReport.setUsername(username);
        TrainingReportResponse trainingReportResponse = new TrainingReportResponse();

        when(trainingReportService.findByUsername(username)).thenReturn(trainingReport);
        when(mockTrainingReportMapping.toDto(trainingReport)).thenReturn(trainingReportResponse);

        trainingReportController.getSummaryRecord(trainingReport.getUsername());

        verify(mockTrainingReportMapping, times(1)).toDto(trainingReport);
        verify(trainingReportService, times(1)).findByUsername(trainingReport.getUsername());

    }

}