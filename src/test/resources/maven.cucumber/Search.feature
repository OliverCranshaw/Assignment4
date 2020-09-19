# new feature
# Tags: optional

Feature: Testing Search

  #AIRLINES

  Scenario: Search Airline by name with matching data in database
    Given the airline name "AirNZ" is in the database
    When user searches for the airline name "AirNZ"
    Then the results from the search will include all airlines with the airline name "AirNZ"

  Scenario: Search Airline by callsign with matching data in database
    Given the airline callsign "NZ123" is in the database
    When user searches for the airline callsign "NZ123"
    Then the results from the search will include all airlines with the airline callsign "NZ123"

  Scenario: Search Airline by country with matching data in database
    Given the airline country "New Zealand" is in the database
    When user searches for the airline country "New Zealand"
    Then the results from the search will include all airlines with the airline country "New Zealand"

  Scenario: Search Airline by name with no matching data in database
    Given the airline name "Non Existing" is not in the database
    When user searches for the airline name "Non Existing" which isn't present in the database
    Then the result from the search will be empty

  Scenario: Search Airline by callsign with no matching data in database
    Given the airline callsign "Non Existing" is not in the database
    When user searches for the airline callsign "Non Existing" which isn't present in the database
    Then the result from the search will be empty

  Scenario: Search Airline by country with no matching data in database
    Given the airline country "Non Existing" is not in the database
    When user searches for the airline country "Non Existing" which isn't present in the database
    Then the result from the search will be empty


    #AIRPORTS

  Scenario: Search Airport by name with matching data in database
    Given the airport name "Christchurch Airport" is in the database
    When user searches for the airport name "Christchurch Airport"
    Then the results from the search will include all airports with the airport name "Christchurch Airport"

  Scenario: Search Airport by city with matching data in database
    Given the airport city "Christchurch" is in the database
    When user searches for the airport city "Christchurch"
    Then the results from the search will include all airports with the airport city "Christchurch"

  Scenario: Search Airport by country with matching data in database
    Given the airport country "New Zealand" is in the database
    When user searches for the airport country "New Zealand"
    Then the results from the search will include all airports with the airport country "New Zealand"

  Scenario: Search Airport by name with no matching data in database
    Given the airport name "Non Existing" is not in the database
    When user searches for the airport name "Non Existing" which isn't present in the database
    Then the result from the search will be empty

  Scenario: Search Airport by city with no matching data in database
    Given the airport city "Non Existing" is not in the database
    When user searches for the airport city "Non Existing" which isn't present in the database
    Then the result from the search will be empty

  Scenario: Search Airport by country with no matching data in database
    Given the airport country "Non Existing" is not in the database
    When user searches for the airport country "Non Existing" which isn't present in the database
    Then the result from the search will be empty


    #FLIGHTS

  Scenario: Search Flight by location type with matching data in database
    Given the flight location type "FIX" is in the database
    When user searches for the flight location type "FIX"
    Then the results from the search will include all flights with the flight location type "FIX"

  Scenario: Search Flight by location with matching data in database
    Given the flight location "NZCH" is in the database
    When user searches for the flight location "NZCH"
    Then the results from the search will include all flights with the flight location "NZCH"

  Scenario: Search Flight by location type with no matching data in database
    Given the flight location type "VOR" is not in the database
    When user searches for the flight location type "VOR" which isn't present in the database
    Then the result from the search will be empty

  Scenario: Search Flight by location with no matching data in database
    Given the flight location "Non Existing" is not in the database
    When user searches for the flight location "Non Existing" which isn't present in the database
    Then the result from the search will be empty


  #ROUTES

  Scenario: TestingRouteSourceAirportSearch with no matching data in database
    Given the route source airport "Christchurch Airport" is in the database
    When user searches for the route source airport "Christchurch Airport"
    Then the results from the search will include all routes with the route source airport "Christchurch Airport"

  Scenario: TestingRouteDestinationAirportSearch with no matching data in database
    Given the route destination airport "Auckland Airport" is in the database
    When user searches for the route destination airport "Auckland Airport"
    Then the results from the search will include all routes with the route destination airport "Auckland Airport"

  Scenario: TestingRouteNumberStopsSearch with matching data in database
    Given the route number stops 2 is in the database
    When user searches for the route number stops 2
    Then the results from the search will include all routes with the route number stops 2

  Scenario: TestingRouteEquipmentSearch with matching data in database
    Given the route equipment "GPS" is in the database
    When user searches for the route equipment "GPS"
    Then the results from the search will include all routes with the route equipment "GPS"

  Scenario: TestingRouteSourceAirportSearch with no matching data in database
    Given the route source airport "Non Existing" is not in the database
    When user searches for the route source airport "Non Existing" which isn't present in the database
    Then the result from the search will be empty

  Scenario: TestingRouteDestinationAirportSearch with no matching data in database
    Given the route destination airport "Non Existing" is not in the database
    When user searches for the route destination airport "Non Existing" which isn't present in the database
    Then the result from the search will be empty
#
  Scenario: TestingRouteNumberStopsSearch with no matching data in database
    Given the route number stops 99 is not in the database
    When user searches for the route number stops 99 which isn't present in the database
    Then the result from the search will be empty

  Scenario: TestingRouteEquipmentSearch with no matching data in database
    Given the route equipment "Non Existing" is not in the database
    When user searches for the route equipment "Non Existing" which isn't present in the database
    Then the result from the search will be empty
