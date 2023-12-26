package com.epam.trainingReport.stepDef;

import com.epam.trainingReport.model.TrainingRecord;
import com.epam.trainingReport.model.TrainingReport;
import com.epam.trainingReport.service.TrainingReportService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AddNewTrainingReportStepDefinition {

    @Autowired
    private TrainingReportService reportService;
    private TrainingRecord trainingRecord;

    @Given("the new Training Record data came from the broker")
    public void the_training_report_was_not_found_by_username_from_training_record_class_which_came_from_the_broker() {
        trainingRecord = createTrainingRecord();
    }

    @When("the system is trying to fetch the TrainingReport by username from TrainingRecord and not found the TrainingReport and creating the new TrainingRecord")
    public void the_non_existing_training_report_is_updating() {
        reportService.update(trainingRecord);
    }

    @Then("the data in the TrainingReport is added and saved into the database")
    public void the_data_in_the_training_report_is_added_and_saved_into_the_database() {
        TrainingReport result = reportService.findByUsername(trainingRecord.getUsername());
        assertNotNull(result);
        assertEquals(trainingRecord.getUsername(), result.getUsername());
        assertFalse(result.getYears().isEmpty());
    }

    private TrainingRecord createTrainingRecord() {
        TrainingRecord mockTrainingRecord = new TrainingRecord();
        mockTrainingRecord.setUsername("New.Trainer");
        mockTrainingRecord.setFirstName("New");
        mockTrainingRecord.setLastName("Trainer");
        mockTrainingRecord.setActive(true);
        LocalDateTime dateTime = LocalDateTime.of(2023, Month.DECEMBER, 25, 11, 10);
        mockTrainingRecord.setDateAndTime(dateTime);
        mockTrainingRecord.setDuration(Duration.ofMinutes(90));
        return mockTrainingRecord;
    }
}
