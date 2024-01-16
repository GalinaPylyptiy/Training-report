package stepDef;

import com.epam.trainingReport.action.ActionType;
import com.epam.trainingReport.dto.TrainingReportRequest;
import com.epam.trainingReport.model.TrainingRecord;
import com.epam.trainingReport.model.TrainingReport;
import com.epam.trainingReport.service.TrainingReportService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddNewTrainingReportStepDefinition {

    @Autowired
    private TrainingReportService reportService;

    @Autowired
    private JmsTemplate jmsTemplate;

    private TrainingReport createdTrainingReport;

    private TrainingReportRequest trainingReportRequest;

    private static final String QUEUE_NAME = "training-report";

    @Given("the new Training Record data came from the broker with not existing report for given username")
    public void the_training_report_was_not_found_by_username_from_training_record_class_which_came_from_the_broker() throws InterruptedException {

        trainingReportRequest = trainingReportRequest();

        TrainingReport emptyTrainingReport = reportService.findByUsername(trainingReportRequest.getUsername());
        assertNotNull(emptyTrainingReport);
        assertNull(emptyTrainingReport.getUsername());
        assertTrue(emptyTrainingReport.getYears().isEmpty());

        jmsTemplate.convertAndSend(QUEUE_NAME, trainingReportRequest, this::setHeaders);

        Thread.sleep(1000);
    }

    @When("the system did not find the TrainingReport for the given username and created the new TrainingReport")
    public void the_non_existing_training_report_is_updating() {

        createdTrainingReport = reportService.findByUsername(trainingReportRequest.getUsername());
    }

    @Then("the data in the TrainingReport is added and saved into the database")
    public void the_data_in_the_training_report_is_added_and_saved_into_the_database() {

        assertNotNull(createdTrainingReport);
        assertEquals(trainingReportRequest.getUsername(), createdTrainingReport.getUsername());
        assertFalse(createdTrainingReport.getYears().isEmpty());
    }

    private TrainingReportRequest trainingReportRequest() {
        TrainingReportRequest trainingReportRequest = new TrainingReportRequest();
        trainingReportRequest.setUsername("Denis.Kozlov");
        trainingReportRequest.setFirstName("Denis");
        trainingReportRequest.setLastName("Kozlov");
        trainingReportRequest.setActive(true);
        LocalDateTime dateTime = LocalDateTime.now().plusDays(5);
        trainingReportRequest.setDate(dateTime);
        trainingReportRequest.setDuration(Duration.ofMinutes(90));
        trainingReportRequest.setActionType(ActionType.ADD);
        return trainingReportRequest;
    }

    private Message setHeaders(Message message) throws JMSException {
        String transactionIdKey = "transactionId";
        String transactionId = UUID.randomUUID().toString();
        message.setStringProperty(transactionIdKey, transactionId);
        return message;
    }

}
