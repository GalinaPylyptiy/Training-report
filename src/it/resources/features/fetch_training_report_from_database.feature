Feature: Fetching the training report for the trainer if the report exists or return the empty training report if not found

  Scenario: Find the existing training report by username
    Given the new training record with username "Dan.Brown" came from message broker
    When  the system checks if the training report for "Dan.Brown" exists
    Then the system returns the existing training report for "Dan.Brown"

  Scenario: The training report with the passed username is not found
    Given the new training record came from broker
    When the system is looking for the training report by the username with came with training record
    Then the system returns the empty training report because the existing training report for username is not found