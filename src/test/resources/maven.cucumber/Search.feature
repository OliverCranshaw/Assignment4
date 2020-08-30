# new feature
# Tags: optional

Feature: Testing Search

  Scenario: TestingAirlineNameSearch

    Given the airline name "AirNZ" is in the database
    When user searches for the airline name "AirNZ"
    Then the results from the search will include all airlines with the airline name "AirNZ"


  Scenario: TestingAirlineCountrySearch
    Given the airline country __ is in the database
    When user searches for the airline country __
    Then the results from the search will include all airlines with the airline country __

  Scenario: TestingAirlineCallsignSearch
    Given the airline callsign __ is in the database
    When user searches for the airline callsign __
    Then the results from the search will include all airlines with the airline callsign __


  Scenario: TestingAirportNameSearch
    Given the airport name __ is in the database
    When user searches for the airport name __
    Then the results from the search will include all airports with the airport name __

  Scenario: TestingAirportCitySearch
    Given the airport city __ is in the database
    When user searches for the airport city __
    Then the results from the search will include all airports with the airport city __

  Scenario: TestingAirportCountrySearch
    Given the airport country __ is in the database
    When user searches for the airport country __
    Then the results from the search will include all airports with the airport country __


  Scenario: TestingFlightAirlineSearch
    Given the flight airline __ is in the database
    When user searches for the flight airline __
    Then the results from the search will include all flights with the flight airline __

  Scenario: TestingFlightAirportSearch
    Given the flight airport __ is in the database
    When user searches for the flight airport __
    Then the results from the search will include all flights with the flight airport __


  Scenario: TestingRouteSourceAirportSearch
    Given the route source airport __ is in the database
    When user searches for the route source airport __
    Then the results from the search will include all routes with the route source airport __

  Scenario: TestingRouteDestinationAirportSearch
    Given the route destination airport __ is in the database
    When user searches for the route destination airport __
    Then the results from the search will include all routes with the route destination airport __

  Scenario: TestingRouteNumberStopsSearch
    Given the route number stops __ is in the database
    When user searches for the route number stops __
    Then the results from the search will include all routes with the route number stops __

  Scenario: TestingRouteEquipmentSearch
    Given the route equipment __ is in the database
    When user searches for the route equipment __
    Then the results from the search will include all routes with the route equipment __


#  Scenario:
#    Given
#    When
#    Then