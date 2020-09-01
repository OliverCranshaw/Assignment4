# new feature
# Tags: optional

Feature: Testing Search


  Scenario: TestingAirlineNameSearch
    Given the airline name "AirNZ" is in the database
    When user searches for the airline name "AirNZ"
    Then the results from the search will include all airlines with the airline name "AirNZ"

#  Scenario: TestingAirlineCountrySearch
#    Given the airline country "New Zealand" is in the database
#    When user searches for the airline country "New Zealand"
#    Then the results from the search will include all airlines with the airline country "New Zealand"
#
#  Scenario: TestingAirlineCallsignSearch
#    Given the airline callsign "NZ123" is in the database
#    When user searches for the airline callsign "NZ123"
#    Then the results from the search will include all airlines with the airline callsign "NZ123"


#  Scenario: TestingAirportNameSearch
#    Given the airport name "Christchurch Airport" is in the database
#    When user searches for the airport name "Christchurch Airport"
#    Then the results from the search will include all airports with the airport name "Christchurch Airport"
#
#  Scenario: TestingAirportCitySearch
#    Given the airport city "Christchurch" is in the database
#    When user searches for the airport city "Christchurch"
#    Then the results from the search will include all airports with the airport city "Christchurch"
#
#  Scenario: TestingAirportCountrySearch
#    Given the airport country "New Zealand" is in the database
#    When user searches for the airport country "New Zealand"
#    Then the results from the search will include all airports with the airport country "New Zealand"
#
#
#  Scenario: TestingFlightAirlineSearch
#    Given the flight airline "Air NZ" is in the database
#    When user searches for the flight airline "Air NZ"
#    Then the results from the search will include all flights with the flight airline "Air NZ"
#
#  Scenario: TestingFlightAirportSearch
#    Given the flight airport "Christchurch Airport" is in the database
#    When user searches for the flight airport "Christchurch Airport"
#    Then the results from the search will include all flights with the flight airport "Christchurch Airport"
#
#
#  Scenario: TestingRouteSourceAirportSearch
#    Given the route source airport "Christchurch Airport" is in the database
#    When user searches for the route source airport "Christchurch Airport"
#    Then the results from the search will include all routes with the route source airport "Christchurch Airport"
#
#  Scenario: TestingRouteDestinationAirportSearch
#    Given the route destination airport "Auckland Airport" is in the database
#    When user searches for the route destination airport "Auckland Airport"
#    Then the results from the search will include all routes with the route destination airport "Auckland Airport"
#
#  Scenario: TestingRouteNumberStopsSearch
#    Given the route number stops "2" is in the database
#    When user searches for the route number stops "2"
#    Then the results from the search will include all routes with the route number stops "2"
#
#  Scenario: TestingRouteEquipmentSearch
#    Given the route equipment "GPS" is in the database
#    When user searches for the route equipment "GPS"
#    Then the results from the search will include all routes with the route equipment "GPS"
