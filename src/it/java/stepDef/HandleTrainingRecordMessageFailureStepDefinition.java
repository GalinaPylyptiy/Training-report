package stepDef;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.epam.trainingReport.consumer.TrainingReportMessageConsumer;
import com.epam.trainingReport.dto.TrainingReportRequest;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HandleTrainingRecordMessageFailureStepDefinition {

    @Autowired
    private TrainingReportMessageConsumer messageConsumer;

    private TrainingReportRequest mockReportRequest;

    private String mockTransactionId;

    private ListAppender<ILoggingEvent> listAppender;

    private ConstraintViolationException constraintViolationException;

    @Before
    public void setUp() {
        listAppender = LogbackTestUtil.getListAppenderForLogger(TrainingReportMessageConsumer.class);
    }

    @After
    public void tearDown() {
        LogbackTestUtil.detachListAppender(TrainingReportMessageConsumer.class, listAppender);
    }

    @Given("a new Training Report message with empty username and transaction ID {string} is received form the message broker")
    public void given_new_training_report_message_with_invalid_data(String transactionId) {
        mockReportRequest = createMockTrainingReportRequest();
        mockTransactionId = transactionId;
    }

    @When("the Training Report Message Consumer fails to validate data")
    public void consumer_fails_to_processes_message() {
        try{
            messageConsumer.handleMessage(mockReportRequest, mockTransactionId);
        }catch (ConstraintViolationException ex){
            constraintViolationException = ex;
        }
    }

    @Then("the handleMessage method throws the ConstraintViolationException exception")
    public void thenServiceShouldBeUpdated() {
        assertThrows(ConstraintViolationException.class,
                ()-> messageConsumer.handleMessage(mockReportRequest, mockTransactionId));
        assertNotNull(constraintViolationException);
        assertFalse(constraintViolationException.getConstraintViolations().isEmpty());
    }

    private TrainingReportRequest createMockTrainingReportRequest() {
        TrainingReportRequest trainingReportRequest = new TrainingReportRequest();
        trainingReportRequest.setUsername("");
        trainingReportRequest.setFirstName("Kate");
        trainingReportRequest.setLastName("Smith");
        trainingReportRequest.setActive(true);
        LocalDateTime dateTime = LocalDateTime.of(2024, 2, 15 , 11, 10);
        trainingReportRequest.setDate(dateTime);
        trainingReportRequest.setDuration(Duration.ofMinutes(90));
        return trainingReportRequest;
    }
}
