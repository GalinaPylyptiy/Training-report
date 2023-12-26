package com.epam.trainingReport.repository;

import com.epam.trainingReport.model.TrainingReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TrainingReportRepository extends MongoRepository<TrainingReport, String> {

    Optional <TrainingReport> findByUsername(String username);

}

