package com.epam.trainingReport.controller;

import com.epam.trainingReport.action.TrainerTrainingsActionHandler;
import com.epam.trainingReport.dto.TrainingReportRequest;
import com.epam.trainingReport.dto.TrainingReportResponse;
import com.epam.trainingReport.mapper.TrainingRecordMapping;
import com.epam.trainingReport.mapper.TrainingReportMapping;
import com.epam.trainingReport.model.TrainingRecord;
import com.epam.trainingReport.model.TrainingReport;
import com.epam.trainingReport.service.TrainingReportService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/training-reports")
public class TrainingReportController {

    private final TrainingReportService trainingReportService;
    private final TrainerTrainingsActionHandler actionHandler;
    private final TrainingRecordMapping trainingRecordMapping;
    private final TrainingReportMapping trainingReportMapping;


    public TrainingReportController(TrainingReportService trainingReportService,
                                    TrainerTrainingsActionHandler actionHandler,
                                    TrainingRecordMapping trainingRecordMapping,
                                    TrainingReportMapping trainingReportMapping) {
        this.trainingReportService = trainingReportService;
        this.actionHandler = actionHandler;
        this.trainingRecordMapping = trainingRecordMapping;
        this.trainingReportMapping = trainingReportMapping;
    }

    @PostMapping
    public void handleTraining(@Valid @RequestBody TrainingReportRequest request){
        TrainingRecord trainingRecord = trainingRecordMapping.toModel(request);
        actionHandler.handleRequestAction(request.getActionType(), trainingRecord);
    }

    @GetMapping
    public TrainingReportResponse getSummaryRecord(@RequestParam @NotEmpty String trainerUsername){
        TrainingReport trainingReport = trainingReportService.findByUsername(trainerUsername);
        return trainingReportMapping.toDto(trainingReport);
    }
}
