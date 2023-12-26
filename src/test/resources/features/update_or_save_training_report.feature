Feature: Update the training report for the trainer if the report exists or create a new one if the report is not found

  Scenario: Update the existing training report with the data came from TrainingRecord
    Given  the new TrainingRecord data came from the broker
    When the system is trying to fetch the existing TrainingReport by username from TrainingRecord and found the TrainingReport and updating the existing TrainingRecord
    Then the data in the TrainingReport is changed and saved into the database

  Scenario: Add the training report with the data came from TrainingRecord when the TrainingReport was not found by username from TrainingRecord
    Given  the new Training Record data came from the broker
    When the system is trying to fetch the TrainingReport by username from TrainingRecord and not found the TrainingReport and creating the new TrainingRecord
    Then the data in the TrainingReport is added and saved into the database


