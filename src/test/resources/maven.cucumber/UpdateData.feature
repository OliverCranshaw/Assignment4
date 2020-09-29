
@Update

Feature: Updating Data

  # Updating airline data

  Scenario: Updating an airline with valid parameters
    Given valid airline parameters "Airfix", "AirFx", "AS", "FTX", "AIRFX", "Sweden", "N" and an existing airline with id 1
    When updating an airline with id 1 with valid parameters "Airfix", "AirFx", "AS", "FTX", "AIRFX", "Sweden", "N"
    Then the airline with id 1 is updated with valid parameters "Airfix", "AirFx", "AS", "FTX", "AIRFX", "Sweden", "N"

  Scenario: Updating an airline with an invalid name
    Given an invalid name "" and an existing airline with id 1
    When updating an airline with id 1 with an invalid name ""
    Then the airline with id 1 is not updated

  Scenario: Updating an airline with an invalid IATA code
    Given an invalid iata code "A" and an existing airline with id 1
    When updating an airline with id 1 with an invalid iata code "A"
    Then the airline with id 1 is not updated

  Scenario: Updating an airline with an invalid ICAO code
    Given an invalid icao code "FI" and an existing airline with id 1
    When updating an airline with id 1 with an invalid icao code "FI"
    Then the airline with id 1 is not updated

  Scenario: Updating an airline with an invalid active
    Given an invalid active "" and an existing airline with id 1
    When updating an airline with id 1 with an invalid active ""
    Then the airline with id 1 is not updated


  # Updating airport data

  Scenario: Updating an airport with valid parameters
    Given valid airport parameters "Christchurch Intl", "Auckland", "Zealand", "CHC", "NZCH", "-43.489358", "172.532225", "123", "12", "Z", "Oceania/Auckland" and an existing aiport with id 1
    When updating an airport with id 1 with parameters "Christchurch Intl", "Auckland", "Zealand", "CHC", "NZCH", -43.489358, 172.532225, 123, 12, "Z", "Oceania/Auckland"
    Then the airport with id 1 is updated with valid parameters "Christchurch Intl", "Auckland", "Zealand", "CHC", "NZCH", -43.489358, 172.532225, 123, 12, "Z", "Oceania/Auckland"

  Scenario: Updating an airport with an invalid name
    Given an invalid name "" and an existing airport with id 1
    When updating an airport with id 1 with an invalid name ""
    Then the airport with id 1 is not updated

  Scenario: Updating an airport with an invalid city
    Given an invalid city "" and an existing airport with id 1
    When updating an airport with id 1 with an invalid city ""
    Then the airport with id 1 is not updated

  Scenario: Updating an airport with an invalid country
    Given an invalid country "" and an existing airport with id 1
    When updating an airport with id 1 with an invalid country ""
    Then the airport with id 1 is not updated

  Scenario: Updating an airport with an invalid IATA code
    Given an invalid iata code "CH" and an existing airport with id 1
    When updating an airport with id 1 with an invalid iata code "CH"
    Then the airport with id 1 is not updated

  Scenario: Updating an airport with an invalid ICAO code
    Given an invalid icao code "NZC" and an existing airport with id 1
    When updating an airport with id 1 with an invalid icao code "NZC"
    Then the airport with id 1 is not updated

  Scenario: Updating an airport with an invalid DST
    Given an invalid dst "B" and an existing airport with id 1
    When updating an airport with id 1 with an invalid dst "B"
    Then the airport with id 1 is not updated

  Scenario: Updating an airport with an invalid TZ timezone
    Given an invalid tz "" and an existing airport with id 1
    When updating an airport with id 1 with an invalid tz ""
    Then the airport with id 1 is not updated


  # Updating flight entry data

  Scenario: Updating a flight entry with valid parameters
    Given valid flight entry parameters "VOR", "CH", "0", "-43.4866", "172.534" and an existing flight entry with id 1
    When updating a flight entry with id 1 with parameters "VOR", "CH", 0, -43.4866, 172.534
    Then the flight entry with id 1 is updated with valid parameters "VOR", "CH", 0, -43.4866, 172.534

  Scenario: Updating a flight entry with an invalid location type
    Given an invalid location type "HAT" and an existing flight entry with id 1
    When updating a flight entry with id 1 with invalid location type "HAT"
    Then the flight entry with id 1 is not updated

  Scenario: Updating a flight entry with an invalid location
    Given an invalid location "" and an existing flight entry with id 1
    When updating a flight entry with id 1 with invalid location ""
    Then the flight entry with id 1 is not updated


  # Updating route data

  Scenario: Updating a route with valid parameters
    Given valid route parameters "ABC", "ABCD", "DBSA", "", "2", "CR2" and an existing route with id 1
    When updating a route with id 1 with parameters "ABC", "ABCD", "DBSA", "", 0, "CR2"
    Then the route with id 1 is updated with valid parameters "ABC", "ABCD", "DBSA", "", 0, "CR2"

  Scenario: Updating a route with an invalid airline code
    Given an invalid airline code "" and an existing route with id 1
    When updating a route with id 1 with invalid airline code ""
    Then the route with id 1 is not updated

  Scenario: Updating a route with an invalid source airport code
    Given an invalid source airport code "" and an existing route with id 1
    When updating a route with id 1 with invalid source airport code ""
    Then the route with id 1 is not updated

  Scenario: Updating a route with an invalid destination airport code
    Given an invalid destination airport code "HG" and an existing route with id 1
    When updating a route with id 1 with invalid destination airport code "HG"
    Then the route with id 1 is not updated

  Scenario: Updating a route with an invalid codeshare
    Given an invalid codeshare "N" and an existing route with id 1
    When updating a route with id 1 with invalid codeshare "N"
    Then the route with id 1 is not updated

  Scenario: Updating a route with invalid stops
    Given invalid stops "-1" and an existing route with id 1
    When updating a route with id 1 with invalid stops -1
    Then the route with id 1 is not updated

  Scenario: Updating a route with invalid equipment
    Given invalid equipment "" and an existing route with id 1
    When updating a route with id 1 with invalid equipment ""
    Then the route with id 1 is not updated