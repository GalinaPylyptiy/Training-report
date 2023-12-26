Feature: Handling Training Report Request with success and with failure

  Scenario: Handling Training Report with Success
    Given a new Training Report message and transaction ID "123456789" is received form the message broker
    When the Training Report Message Consumer processes the message
    Then the TrainingReport data should be recalculated and updated in database
    And the logs should contain the message "Received message from activeMQ"
    And the logs should contain the message "TrainingRecord was handled successfully"

  Scenario: Handling Training Report with Failure
    Given a new Training Report message with empty username and transaction ID "097898967" is received form the message broker
    When the Training Report Message Consumer fails to validate data
    Then the handleMessage method throws the ConstraintViolationException exception