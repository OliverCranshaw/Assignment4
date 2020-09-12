# new feature
# Tags: optional

Feature: Testing Search

  #AIRLINES

#  Scenario: Search Airline by name with matching data in database
#    Given the airline name "AirNZ" is in the database
#    When user searches for the airline name "AirNZ"
#    Then the results from the search will include all airlines with the airline name "AirNZ"
#
#  Scenario: Search Airline by callsign with matching data in database
#    Given the airline callsign "NZ123" is in the database
#    When user searches for the airline callsign "NZ123"
#    Then the results from the search will include all airlines with the airline callsign "NZ123"
#
#  Scenario: Search Airline by country with matching data in database
#    Given the airline country "New Zealand" is in the database
#    When user searches for the airline country "New Zealand"
#    Then the results from the search will include all airlines with the airline country "New Zealand"
#
#  Scenario: Search Airline by name with no matching data in database
#    Given the airline name "Non Existing" is not in the database
#    When user searches for the airline name "Non Existing" which isn't present in the database
#    Then the result from the search will be empty
#
#  Scenario: Search Airline by callsign with no matching data in database
#    Given the airline callsign "Non Existing" is not in the database
#    When user searches for the airline callsign "Non Existing" which isn't present in the database
#    Then the result from the search will be empty
#
#  Scenario: Search Airline by country with no matching data in database
#    Given the airline country "Non Existing" is not in the database
#    When user searches for the airline country "Non Existing" which isn't present in the database
#    Then the result from the search will be empty
#
#
#  #AIRPORTS
#
#  Scenario: Search Airport by name with matching data in database
#    Given the airport name "Christchurch Airport" is in the database
#    When user searches for the airport name "Christchurch Airport"
#    Then the results from the search will include all airports with the airport name "Christchurch Airport"
#
#  Scenario: Search Airport by city with matching data in database
#    Given the airport city "Christchurch" is in the database
#    When user searches for the airport city "Christchurch"
#    Then the results from the search will include all airports with the airport city "Christchurch"
#
#  Scenario: Search Airport by country with matching data in database
#    Given the airport country "New Zealand" is in the database
#    When user searches for the airport country "New Zealand"
#    Then the results from the search will include all airports with the airport country "New Zealand"
#
#  Scenario: Search Airport by name with no matching data in database
#    Given the airport name "Non Existing" is not in the database
#    When user searches for the airport name "Non Existing" which isn't present in the database
#    Then the result from the search will be empty
#
#  Scenario: Search Airport by city with no matching data in database
#    Given the airport city "Non Existing" is not in the database
#    When user searches for the airport city "Non Existing" which isn't present in the database
#    Then the result from the search will be empty
#
#  Scenario: Search Airport by country with no matching data in database
#    Given the airport country "Non Existing" is not in the database
#    When user searches for the airport country "Non Existing" which isn't present in the database
#    Then the result from the search will be empty
#
#  #FLIGHTS
#
#  Scenario: Search Flight by airline with matching data in database
#    Given the flight airline "AirNZ" is in the database
#    When user searches for the flight airline "AirNZ"
#    Then the results from the search will include all flights with the flight airline "AirNZ"
#
#  Scenario: Search Flight by airport with matching data in database
#    Given the flight airport "Christchurch Airport" is in the database
#    When user searches for the flight airport "Christchurch Airport"
#    Then the results from the search will include all flights with the flight airport "Christchurch Airport"
#
#  Scenario: Search Flight by airline with no matching data in database
#    Given the flight airline "Non Existing" is not in the database
#    When user searches for the flight airline "Non Existing" which isn't present in the database
#    Then the result from the search will be null
#
#  Scenario: Search Flight by airport with no matching data in database
#    Given the flight airport "Non Existing" is not in the database
#    When user searches for the flight airport "Non Existing" which isn't present in the database
#    Then the result from the search will be null
#
#
#  #ROUTES
#
#  Scenario: TestingRouteSourceAirportSearch with no matching data in database
#    Given the route source airport "Christchurch Airport" is in the database
#    When user searches for the route source airport "Christchurch Airport"
#    Then the results from the search will include all routes with the route source airport "Christchurch Airport"
#
#  Scenario: TestingRouteDestinationAirportSearch with no matching data in database
#    Given the route destination airport "Auckland Airport" is in the database
#    When user searches for the route destination airport "Auckland Airport"
#    Then the results from the search will include all routes with the route destination airport "Auckland Airport"
#
#  Scenario: TestingRouteNumberStopsSearch with matching data in database
#    Given the route number stops 2 is in the database
#    When user searches for the route number stops 2
#    Then the results from the search will include all routes with the route number stops 2
#
#  Scenario: TestingRouteEquipmentSearch with matching data in database
#    Given the route equipment "GPS" is in the database
#    When user searches for the route equipment "GPS"
#    Then the results from the search will include all routes with the route equipment "GPS"

#  Scenario: TestingRouteSourceAirportSearch with no matching data in database
#    Given the route source airport "Non Existing" is not in the database
#    When user searches for the route source airport "Non Existing" which isn't present in the database
#    Then the result from the search will be null
#
#  Scenario: TestingRouteDestinationAirportSearch with no matching data in database
#    Given the route destination airport "Non Existing" is not in the database
#    When user searches for the route destination airport "Non Existing" which isn't present in the database
#    Then the result from the search will be null
#
#  Scenario: TestingRouteNumberStopsSearch with no matching data in database
#    Given the route number stops 99 is not in the database
#    When user searches for the route number stops 99 which isn't present in the database
#    Then the result from the search will be empty
#
#  Scenario: TestingRouteEquipmentSearch with no matching data in database
#    Given the route equipment "Non Existing" is not in the database
#    When user searches for the route equipment "Non Existing" which isn't present in the database
#    Then the result from the search will be empty
