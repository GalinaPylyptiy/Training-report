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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HandleTrainingRecordMessageFailureStepDefinition {

    @Autowired
    private TrainingReportService trainingReportService;

    @Autowired
    private JmsTemplate jmsTemplate;

    private TrainingReportRequest mockReportRequest;

    private TrainingReport notProcessedReport;

    private static final String QUEUE_NAME = "training-report";

    @Given("a new Training Report message with invalid data and transaction ID is received form the message broker")
    public void given_new_training_report_message_with_invalid_data() throws InterruptedException {

        mockReportRequest = trainingReportRequest();

        jmsTemplate.convertAndSend(QUEUE_NAME, mockReportRequest, this::setHeaders);

        Thread.sleep(1000);
    }

    @When("the Training Report Message Consumer fails to validate data")
    public void consumer_fails_to_processes_message()  {

         notProcessedReport = trainingReportService.findByUsername(mockReportRequest.getUsername());
    }

    @Then("the TrainingReportRequest failed to process and the TrainingReport was not updated")
    public void thenServiceShouldBeUpdated() {

        assertNotNull(notProcessedReport);
        assertNull(notProcessedReport.getUsername());
        assertTrue(notProcessedReport.getYears().isEmpty());
    }

    private TrainingReportRequest trainingReportRequest() {
        TrainingReportRequest trainingReportRequest = new TrainingReportRequest();
        trainingReportRequest.setUsername("");
        trainingReportRequest.setFirstName("Lily");
        trainingReportRequest.setLastName("Brown");
        trainingReportRequest.setActive(true);
        LocalDateTime dateTime = LocalDateTime.now().plusDays(2);
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
