package com.epam.trainingReport.action;

import com.epam.trainingReport.model.TrainingRecord;
import com.epam.trainingReport.service.TrainerTrainingsService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class TrainerTrainingsActionHandler {

    private final Map<ActionType, TrainerTrainingAction> actionMap;

    public TrainerTrainingsActionHandler(@Lazy TrainerTrainingsService trainerTrainingsService) {
        actionMap = new HashMap<>();
        actionMap.put(ActionType.ADD, trainerTrainingsService::add);
        actionMap.put(ActionType.DELETE, trainerTrainingsService::delete);
    }

    public void handleRequestAction(ActionType actionType, TrainingRecord trainingRecord) {
        actionMap.get(actionType).perform(trainingRecord);
    }

}
