
@DataExporter

Feature: Exporting data

  # Exporting airline data

  Scenario: Exporting airline data with airlines in the database
    Given there are airlines in the database
    When exporting airline data to file "airlines.csv"
    Then there is a file "airlines.csv" containing all the airline data

  Scenario: Exporting airline data with no airlines in the database
    Given there are no airlines in the database
    When exporting airline data to file "airlines.csv"
    Then there is an empty file "airlines.csv"


  # Exporting airport data

  Scenario: Exporting airport data with airports in the database
    Given there are airports in the database
    When exporting airport data to file "airports.csv"
    Then there is a file "airports.csv" containing all the airport data

  Scenario: Exporting airport data with no airports in the database
    Given there are no airports in the database
    When exporting airport data to file "airports.csv"
    Then there is an empty file "airports.csv"


  # Exporting all flight data

  Scenario: Exporting all flight data with multiple flights in the database
    Given there are multiple flights in the database
    When exporting all flight data to file "flights.csv"
    Then there is a file "flights.csv" containing all the flight data

  Scenario: Exporting all flight data with one flight in the database
    Given there is one flight in the database
    When exporting all flight data to file "flights.csv"
    Then there is a file "flights.csv" containing all the flight data

  Scenario: Exporting all flight data with no flights in the database
    Given there are no flights in the database
    When exporting all flight data to file "flights.csv"
    Then there is an empty file "flights.csv"


  # Exporting flight data

  Scenario: Exporting flight data with multiple flights in the database
    Given there are multiple flights in the database
    When exporting flight data to file "flight.csv"
    Then there is a file "flight.csv" containing the flight data

  Scenario: Exporting flight data with one flight in the database
    Given there is one flight in the database
    When exporting flight data to file "flight.csv"
    Then there is a file "flight.csv" containing the flight data

  Scenario: Exporting flight data with no flights in the database
    Given there are no flights in the database
    When exporting flight data to file "flight.csv"
    Then there is an empty file "flight.csv"


  # Exporting route data

  Scenario: Exporting route data with routes in the database
    Given there are routes in the database
    When exporting route data to file "routes.csv"
    Then there is a file "routes.csv" containing all the route data

  Scenario: Exporting route data with no routes in the database
    Given there are no routes in the database
    When exporting route data to file "routes.csv"
    Then there is an empty file "routes.csv"