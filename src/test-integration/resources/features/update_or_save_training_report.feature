Feature: Update the training report for the trainer if the report exists or create a new one if the report is not found

  Scenario: Update the existing training report with the data came from TrainingRecord
    Given  the new TrainingRecord data came from the broker with existing report for given username
    When the system found the TrainingReport for given username and updated the existing TrainingReport
    Then the data in the TrainingReport is changed and saved into the database

  Scenario: Add the training report with the data came from TrainingRecord when the TrainingReport was not found by username from TrainingRecord
    Given  the new Training Record data came from the broker with not existing report for given username
    When the system did not find the TrainingReport for the given username and created the new TrainingReport
    Then the data in the TrainingReport is added and saved into the database


