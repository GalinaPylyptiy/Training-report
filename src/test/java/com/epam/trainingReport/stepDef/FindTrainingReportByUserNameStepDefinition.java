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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FindTrainingReportByUserNameStepDefinition {

    @Autowired
    private TrainingReportService reportService;
    private TrainingReport trainingReport;
    private TrainingRecord trainingRecord;
    private String username;


    @Given("the new training record with username {string} came from message broker")
    public void the_new_training_record_with_username_came_from_message_broker(String username) {
        trainingRecord = createMockTrainingRecord();
    }

    @When("the system checks if the training report for {string} exists")
    public void whenSystemChecksTrainingReportExists(String username) {
        trainingReport = reportService.findByUsername(trainingRecord.getUsername());

    }

    @Then("the system returns the existing training report for {string}")
    public void thenSystemReturnsExistingTrainingReport(String username) {
        assertNotNull(trainingReport);
        assertEquals(username, trainingReport.getUsername());
    }

    private TrainingRecord createMockTrainingRecord() {
        TrainingRecord mockTrainingRecord = new TrainingRecord();
        mockTrainingRecord.setUsername("Dan.Brown");
        mockTrainingRecord.setFirstName("Dan");
        mockTrainingRecord.setLastName("Brown");
        mockTrainingRecord.setActive(true);
        LocalDateTime dateTime = LocalDateTime.of(2023, 12, 15, 11, 10);
        mockTrainingRecord.setDateAndTime(dateTime);
        mockTrainingRecord.setDuration(Duration.ofMinutes(90));
        return mockTrainingRecord;
    }
}
