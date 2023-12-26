package com.epam.trainingReport.action;

import com.epam.trainingReport.model.TrainingRecord;
import com.epam.trainingReport.service.impl.TrainerTrainingsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class TrainerTrainingsActionHandlerTest {

    private TrainerTrainingsActionHandler actionHandler;
    private TrainerTrainingsServiceImpl trainerTrainingsServiceImpl;

    @BeforeEach
    void setUp() {
        trainerTrainingsServiceImpl = mock(TrainerTrainingsServiceImpl.class);
        actionHandler = new TrainerTrainingsActionHandler(trainerTrainingsServiceImpl);
    }

    @Test
    void handleRequestAction_AddAction() {
        ActionType actionType = ActionType.ADD;
        TrainingRecord trainingRecord = new TrainingRecord();

        actionHandler.handleRequestAction(actionType, trainingRecord);

        verify(trainerTrainingsServiceImpl, times(1)).add(trainingRecord);
        verify(trainerTrainingsServiceImpl, never()).delete(any());
    }

    @Test
    void handleRequestAction_DeleteAction() {
        ActionType actionType = ActionType.DELETE;
        TrainingRecord trainingRecord = new TrainingRecord();

        actionHandler.handleRequestAction(actionType, trainingRecord);

        verify(trainerTrainingsServiceImpl, times(1)).delete(trainingRecord);
        verify(trainerTrainingsServiceImpl, never()).add(any());
    }
}
