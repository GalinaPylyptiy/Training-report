package com.epam.trainingReport.stepDef;

import com.epam.trainingReport.model.TrainingRecord;
import com.epam.trainingReport.model.TrainingReport;
import com.epam.trainingReport.service.TrainingReportService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class UpdateExistingTrainingReportStepDefinition {

    @Autowired
    private TrainingReportService reportService;
    private TrainingReport existingTrainingReport;
    private TrainingRecord trainingRecord;
    private Logger logger = LoggerFactory.getLogger(TrainingReportByUsernameNotFoundStepDefinition.class);


    @Given("the new TrainingRecord data came from the broker")
    public void the_training_report_was_found_by_username_from_training_record_class_which_came_from_the_broker() {
        trainingRecord = createTrainingRecord();
    }

    @When("the system is trying to fetch the existing TrainingReport by username from TrainingRecord and found the TrainingReport and updating the existing TrainingRecord")
    public void the_existing_training_report_is_updating() {
        existingTrainingReport = reportService.findByUsername(trainingRecord.getUsername());
        reportService.update(trainingRecord);
    }

    @Then("the data in the TrainingReport is changed and saved into the database")
    public void the_data_in_the_training_report_is_changed_and_saved_into_the_database() {
        TrainingReport result = reportService.findByUsername(trainingRecord.getUsername());
        assertNotNull(result);
        assertEquals(trainingRecord.getUsername(), result.getUsername());
        assertNotEquals(existingTrainingReport, result);
    }

    private TrainingRecord createTrainingRecord() {
        TrainingRecord mockTrainingRecord = new TrainingRecord();
        mockTrainingRecord.setUsername("Dan.Brown");
        mockTrainingRecord.setFirstName("Dan");
        mockTrainingRecord.setLastName("Brown");
        mockTrainingRecord.setActive(true);
        LocalDateTime dateTime = LocalDateTime.of(2023, Month.DECEMBER, 25, 11, 10);
        mockTrainingRecord.setDateAndTime(dateTime);
        mockTrainingRecord.setDuration(Duration.ofMinutes(90));
        return mockTrainingRecord;
    }

}
