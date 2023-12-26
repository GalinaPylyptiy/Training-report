package com.epam.trainingReport.stepDef;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.epam.trainingReport.consumer.TrainingReportMessageConsumer;
import com.epam.trainingReport.dto.TrainingReportRequest;
import com.epam.trainingReport.model.TrainingReport;
import com.epam.trainingReport.service.TrainingReportService;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HandleTrainingRecordMessageSuccessStepDefinition {

    @Autowired
    private TrainingReportMessageConsumer messageConsumer;

    @Autowired
    private TrainingReportService trainingReportService;

    private TrainingReportRequest mockReportRequest;

    private String mockTransactionId;

    private ListAppender<ILoggingEvent> listAppender;

    @Before
    public void setUp() {
        listAppender = LogbackTestUtil.getListAppenderForLogger(TrainingReportMessageConsumer.class);
    }

    @After
    public void tearDown() {
        LogbackTestUtil.detachListAppender(TrainingReportMessageConsumer.class, listAppender);
    }

    @Given("a new Training Report message and transaction ID {string} is received form the message broker")
    public void givenNewTrainingReportMessage(String transactionId) {
        mockReportRequest = createMockTrainingReportRequest();
        mockTransactionId = transactionId;
    }

    @When("the Training Report Message Consumer processes the message")
    public void whenConsumerProcessesMessage() {
        messageConsumer.handleMessage(mockReportRequest, mockTransactionId);
    }

    @Then("the TrainingReport data should be recalculated and updated in database")
    public void thenServiceShouldBeUpdated() {
        TrainingReport updatedReport = trainingReportService.findByUsername(mockReportRequest.getUsername());
        assertThat(updatedReport).isNotNull();
        assertThat(updatedReport.getYears()).isNotEmpty();
    }

    @Then("the logs should contain the message {string}")
    public void thenLogsShouldContainMessage(String logMessage) {
        List<ILoggingEvent> logs = listAppender.list;
        assertThat(logs).extracting("formattedMessage").contains(logMessage);
    }

    private TrainingReportRequest createMockTrainingReportRequest() {
        TrainingReportRequest trainingReportRequest = new TrainingReportRequest();
        trainingReportRequest.setUsername("Kate.Smith");
        trainingReportRequest.setFirstName("Kate");
        trainingReportRequest.setLastName("Smith");
        trainingReportRequest.setActive(true);
        LocalDateTime dateTime = LocalDateTime.of(2024, 2, 15 , 11, 10);
        trainingReportRequest.setDate(dateTime);
        trainingReportRequest.setDuration(Duration.ofMinutes(90));
        return trainingReportRequest;
    }

}
