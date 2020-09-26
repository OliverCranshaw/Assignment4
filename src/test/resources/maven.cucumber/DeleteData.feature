
@Delete

Feature: Deleting Data

  # Deleting an airline

  Scenario: Deleting an existing airline
    Given there is an airline in the database with id 1
    When deleting the airline with id 1
    Then the airline with id 1 no longer exists

  Scenario: Deleting an airline that doesn't exist
    Given there is no airline with id 1
    When deleting the non-existing airline with id 1
    Then an airline with id 1 still does not exist

  # Deleting an airport

  Scenario: Deleting an existing airport
    Given there is an airport in the database with id 1
    When deleting the airport with id 1
    Then the airport with id 1 no longer exists

  Scenario: Deleting an airport that doesn't exist
    Given there is no airport with id 1
    When deleting the non-existing airport with id 1
    Then an airport with id 1 still does not exist

  # Deleting a flight entry

  Scenario: Deleting an existing flight entry
    Given there is a flight entry in the database with id 1
    When deleting the flight entry with id 1
    Then the flight entry with id 1 no longer exists

  Scenario: Deleting a flight entry that doesn't exist
    Given there is no flight entry with id 1
    When deleting the non-existing flight entry with id 1
    Then a flight entry with id 1 still does not exist

  # Deleting a flight

  Scenario: Deleting an existing flight
    Given there is a flight in the database with flight id 1
    When deleting the flight with flight id 1
    Then the flight with flight id 1 no longer exists

  Scenario: Deleting a flight that doesn't exist
    Given there is no flight with flight id 1
    When deleting the non-existing flight with flight id 1
    Then a flight with flight id 1 still does not exist

  # Deleting a route

  Scenario: Deleting an existing route
    Given there is a route in the database with id 1
    When deleting the route with id 1
    Then the route with id 1 no longer exists

  Scenario: Deleting a route that doesn't exist
    Given there is no route with id 1
    When deleting the non-existing route with id 1
    Then a route with id 1 still does not exist