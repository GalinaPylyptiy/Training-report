package com.epam.trainingReport.consumer;

import com.epam.trainingReport.action.ActionType;
import com.epam.trainingReport.action.TrainerTrainingsActionHandler;
import com.epam.trainingReport.dto.TrainingReportRequest;
import com.epam.trainingReport.mapper.TrainingRecordMapping;
import com.epam.trainingReport.model.TrainingRecord;
import com.epam.trainingReport.service.TrainingReportService;
import jakarta.jms.JMSException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingReportMessageConsumerTest {
    @Mock
    private TrainingRecordMapping trainingRecordMapping;

    @Mock
    private TrainerTrainingsActionHandler actionHandler;

    @Mock
    private  TrainingReportService trainingReportService;

    @InjectMocks
    private TrainingReportMessageConsumer messageConsumer;

    @Test
    void handleMessage_shouldProcessMessage() {

        TrainingReportRequest reportRequest = new TrainingReportRequest();
        reportRequest.setActionType(ActionType.ADD); // Set other properties as needed
        TrainingRecord trainingRecord = new TrainingRecord();

        String transactionId = "some-transaction-id";

        when(trainingRecordMapping.toModel(reportRequest)).thenReturn(trainingRecord);

        doNothing().when(trainingReportService).update(trainingRecord);

//        doNothing().when(actionHandler).handleRequestAction(eq(reportRequest.getActionType()), any(TrainingRecord.class));

        messageConsumer.handleMessage(reportRequest, transactionId);

        verify(trainingRecordMapping, times(1)).toModel(reportRequest);
        verify(trainingReportService, times(1)).update(trainingRecord);
//        verify(actionHandler, times(1)).handleRequestAction(eq(reportRequest.getActionType()), any(TrainingRecord.class));
    }

}
