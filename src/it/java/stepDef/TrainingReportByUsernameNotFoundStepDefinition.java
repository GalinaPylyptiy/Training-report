package stepDef;

import com.epam.trainingReport.model.TrainingRecord;
import com.epam.trainingReport.model.TrainingReport;
import com.epam.trainingReport.service.TrainingReportService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.Duration;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TrainingReportByUsernameNotFoundStepDefinition {

    @Autowired
    private TrainingReportService reportService;
    private TrainingReport trainingReport;
    private TrainingRecord trainingRecord;


    @Given("the new training record came from broker")
    public void theNewTrainingRecordWithUsernameCameFromMessageBroker() {
        trainingRecord = createMockTrainingRecord();
    }

    @When("the system is looking for the training report by the username with came with training record")
    public void whenSystemChecksTrainingReportExists() {
        trainingReport = reportService.findByUsername(trainingRecord.getUsername());
    }

    @Then("the system returns the empty training report because the existing training report for username is not found")
    public void thenSystemReturnsEmptyTrainingReport() {
        assertNotNull(trainingReport);
        assertTrue(trainingReport.getYears().isEmpty());
    }

    private TrainingRecord createMockTrainingRecord() {
        TrainingRecord mockTrainingRecord = new TrainingRecord();
        mockTrainingRecord.setUsername("Bill.Smith");
        mockTrainingRecord.setFirstName("Bill");
        mockTrainingRecord.setLastName("Smith");
        mockTrainingRecord.setActive(true);
        LocalDateTime dateTime = LocalDateTime.of(2023, 12, 15, 11, 10);
        mockTrainingRecord.setDateAndTime(dateTime);
        mockTrainingRecord.setDuration(Duration.ofMinutes(90));
        return mockTrainingRecord;
    }
}
