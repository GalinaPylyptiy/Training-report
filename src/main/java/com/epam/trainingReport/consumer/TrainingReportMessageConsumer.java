package com.epam.trainingReport.consumer;

import com.epam.trainingReport.dto.TrainingReportRequest;
import com.epam.trainingReport.mapper.TrainingRecordMapping;
import com.epam.trainingReport.model.TrainingRecord;
import com.epam.trainingReport.service.TrainingReportService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@Validated
public class TrainingReportMessageConsumer {

    private static final String QUEUE_NAME = "training-report";
    private final TrainingRecordMapping trainingRecordMapping;
    private final TrainingReportService trainingReportService;
    private final Logger logger = LoggerFactory.getLogger(TrainingReportMessageConsumer.class);

    public TrainingReportMessageConsumer(TrainingRecordMapping trainingRecordMapping, TrainingReportService trainingReportService) {
        this.trainingRecordMapping = trainingRecordMapping;
        this.trainingReportService = trainingReportService;
    }

    @JmsListener(destination = QUEUE_NAME)
    public void handleMessage(@Payload @Valid TrainingReportRequest reportRequest, @Header String transactionId) {
        handleTransactionId(transactionId);
        logger.info("Received message from activeMQ");
        TrainingRecord trainingRecord = trainingRecordMapping.toModel(reportRequest);
        trainingReportService.update(trainingRecord);
        logger.info("TrainingRecord was handled successfully");
    }

    private void handleTransactionId(String transactionId) {
        if (transactionId == null) {
            transactionId = UUID.randomUUID().toString();
            logger.warn("The request header with transaction ID is not detected. Generating the new ID: " + transactionId);
        }
        MDC.put("transactionId", transactionId);
    }
}
