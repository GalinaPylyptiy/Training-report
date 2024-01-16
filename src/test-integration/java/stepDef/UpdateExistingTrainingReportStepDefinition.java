package stepDef;

import com.epam.trainingReport.action.ActionType;
import com.epam.trainingReport.dto.TrainingReportRequest;
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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class UpdateExistingTrainingReportStepDefinition {

    @Autowired
    private TrainingReportService reportService;

    @Autowired
    private JmsTemplate jmsTemplate;

    private TrainingReport updatedTrainingReport;

    private TrainingReportRequest trainingReportRequest;

    private  TrainingReport existingTrainingReport;

    private static final String QUEUE_NAME = "training-report";


    @Given("the new TrainingRecord data came from the broker with existing report for given username")
    public void the_training_report_was_found_by_username_from_training_record_class_which_came_from_the_broker() throws InterruptedException {

        trainingReportRequest = trainingReportRequest();

        existingTrainingReport = reportService.findByUsername(trainingReportRequest.getUsername());

        jmsTemplate.convertAndSend(QUEUE_NAME, trainingReportRequest, this::setHeaders);

        Thread.sleep(1000);
    }

    @When("the system found the TrainingReport for given username and updated the existing TrainingReport")
    public void the_existing_training_report_is_updating() {

        updatedTrainingReport = reportService.findByUsername(trainingReportRequest.getUsername());
    }

    @Then("the data in the TrainingReport is changed and saved into the database")
    public void the_data_in_the_training_report_is_changed_and_saved_into_the_database() {

        assertNotNull(updatedTrainingReport);
        assertEquals(trainingReportRequest.getUsername(), updatedTrainingReport.getUsername());
        assertNotEquals(existingTrainingReport, updatedTrainingReport);
    }

    private TrainingReportRequest trainingReportRequest() {
        TrainingReportRequest trainingReportRequest = new TrainingReportRequest();
        trainingReportRequest.setUsername("Dan.Brown");
        trainingReportRequest.setFirstName("Dan");
        trainingReportRequest.setLastName("Brown");
        trainingReportRequest.setActive(true);
        LocalDateTime dateTime = LocalDateTime.now().plusDays(3);
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
