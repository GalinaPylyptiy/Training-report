package com.epam.trainingReport.service.impl;

import com.epam.trainingReport.dao.TrainerTrainingsDAO;
import com.epam.trainingReport.model.TrainingRecord;
import com.epam.trainingReport.service.TrainerTrainingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TrainerTrainingsServiceImpl implements TrainerTrainingsService {

    private final TrainerTrainingsDAO trainerTrainingsDAO;
    private Logger logger = LoggerFactory.getLogger(TrainerTrainingsServiceImpl.class);


    public TrainerTrainingsServiceImpl(TrainerTrainingsDAO trainerTrainingsDAO) {
        this.trainerTrainingsDAO = trainerTrainingsDAO;
    }
//
//    @Override
//    public DurationReport getSummaryRecord(String trainerUsername) {
//        try{
//            logger.info("Calculating monthly summary training duration for trainer "+ trainerUsername);
//            return calculatorService.calculateMonthlySummary(getTrainingList(trainerUsername));
//        }catch (Exception ex){
//            logger.error("Error calculating monthly summary training duration for trainer " + trainerUsername);
//            throw new MonthlySummaryCalculatingException("Error calculating monthly summary training duration for trainer "
//                    + trainerUsername+ ": "+ ex.getMessage());
//        }
//    }

    @Override
    public void add(TrainingRecord trainingRecord) {
        try {
            logger.info("Add new Training to the training list for trainer " + trainingRecord.getUsername()+ " for "+ trainingRecord.getDateAndTime());
            trainerTrainingsDAO.add(trainingRecord);
        } catch (Exception ex) {
            logger.error("Could not add new training for trainer " + trainingRecord.getUsername(), ex.getMessage());
            throw new RuntimeException("Could not add new training for trainer " + trainingRecord.getUsername());
        }
    }

    @Override
    public void delete(TrainingRecord trainingRecord) {
        try{
            logger.info("Delete training from  training list for trainer " + trainingRecord.getUsername()+ " for "+ trainingRecord.getDateAndTime());
            trainerTrainingsDAO.delete(trainingRecord);
        }catch (Exception ex) {
            logger.error("Could not delete training for trainer " + trainingRecord.getUsername(), ex.getMessage());
            throw new RuntimeException("Could not delete training for trainer " + trainingRecord.getUsername());
        }
    }

    private List<TrainingRecord> getTrainingList(String trainerUsername){
        logger.info("Getting training list for trainer " + trainerUsername);
        return trainerTrainingsDAO.getTrainerTrainingsByUsername(trainerUsername);
    }

}
