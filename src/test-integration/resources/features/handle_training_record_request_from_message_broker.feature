Feature: Handling Training Report Request with success and with failure

  Scenario: Handling Training Report with Success
    Given a new Training Report message and transaction ID is received form the message broker
    When the Training Report Message Consumer processes the message
    Then the TrainingReport data should be recalculated and updated in database

  Scenario: Handling Training Report with Failure
    Given a new Training Report message with invalid data and transaction ID is received form the message broker
    When the Training Report Message Consumer fails to validate data
    Then the TrainingReportRequest failed to process and the TrainingReport was not updated