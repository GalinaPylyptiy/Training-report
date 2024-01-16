package stepDef;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.epam.trainingReport.action.ActionType;
import com.epam.trainingReport.consumer.TrainingReportMessageConsumer;
import com.epam.trainingReport.dto.TrainingReportRequest;
import com.epam.trainingReport.model.TrainingReport;
import com.epam.trainingReport.service.TrainingReportService;
import io.cucumber.java.After;
import io.cucumber.java.Before;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HandleTrainingRecordMessageSuccessStepDefinition {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private TrainingReportService trainingReportService;

    private TrainingReportRequest mockReportRequest;

    private TrainingReport updatedReport;

    private static final String QUEUE_NAME = "training-report";

    @Given("a new Training Report message and transaction ID is received form the message broker")
    public void givenNewTrainingReportMessage() throws InterruptedException {

        mockReportRequest = trainingReportRequest();

        jmsTemplate.convertAndSend(QUEUE_NAME, mockReportRequest, this::setHeaders);

        Thread.sleep(1000);
    }

    @When("the Training Report Message Consumer processes the message")
    public void whenConsumerProcessesMessage() {
        updatedReport = trainingReportService.findByUsername(mockReportRequest.getUsername());
    }

    @Then("the TrainingReport data should be recalculated and updated in database")
    public void thenServiceShouldBeUpdated() {
        assertNotNull(updatedReport);
        assertTrue(updatedReport.getActive());
        assertFalse(updatedReport.getYears().isEmpty());
    }


    private TrainingReportRequest trainingReportRequest() {
        TrainingReportRequest trainingReportRequest = new TrainingReportRequest();
        trainingReportRequest.setUsername("Lily.Brown");
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
