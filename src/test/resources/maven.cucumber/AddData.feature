
Feature: Adding data

  # Adding Airlines

  Scenario: Adding a valid airline
    Given valid airline parameters ("Airfix Aviation", "", "", "FIX", "AIRFIX", "Finland", "Y")
    When adding an airline with parameters ("Airfix Aviation", "", "", "FIX", "AIRFIX", "Finland", "Y")
    Then the airline is added with id 1

  Scenario: Adding an airline with an invalid name
    Given valid airline parameters ("", "", "FIX", "AIRFIX", "Finland", "Y") except for an invalid name ""
    When adding an airline with parameters ("", "", "FIX", "AIRFIX", "Finland", "Y") and invalid name ""
    Then the airline parameters are rejected, and an error code -2 is returned

  Scenario: Adding an airline with an invalid IATA code
    Given valid airline parameters ("Airfix Aviation", "", "FIX", "AIRFIX", "Finland", "Y") except for an invalid iata code "A"
    When adding an airline with parameters ("Airfix Aviation", "", "FIX", "AIRFIX", "Finland", "Y") and invalid iata code "A"
    Then the airline parameters are rejected, and an error code -3 is returned

  Scenario: Adding an airline with an invalid ICAO code
    Given valid airline parameters ("Airfix Aviation", "", "", "FIX", "AIRFIX", "Finland", "Y") except for an invalid icao code "FI"
    When adding an airline with parameters ("Airfix Aviation", "", "", "FIX", "AIRFIX", "Finland", "Y") and invalid icao code "FI"
    Then the airline parameters are rejected, and an error code -4 is returned

  Scenario: Adding an airline with an invalid active
    Given valid airline parameters ("Airfix Aviation", "", "", "FIX", "AIRFIX", "Finland") except for an invalid active ""
    When adding an airline with parameters ("Airfix Aviation", "", "", "FIX", "AIRFIX", "Finland") and invalid active ""
    Then the airline parameters are rejected, and an error code -5 is returned


  # Adding Airports

  Scenario: Adding a valid airport
    Given valid airport parameters ("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "NZCH", "-43.489358", "172.532225", "123", "12", "Z", "Pacific/Auckland")
    When adding an airport with parameters ("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "NZCH", "-43.489358", "172.532225", "123", "12", "Z", "Pacific/Auckland")
    Then the airport is added with id 1

  Scenario: Adding an airport with an invalid name
    Given valid airport parameters ("Christchurch", "New Zealand", "CHC", "NZCH", "-43.489358", "172.532225", "123", "12", "Z", "Pacific/Auckland") except for an invalid name ""
    When adding an airport with parameters ("Christchurch", "New Zealand", "CHC", "NZCH", "-43.489358", "172.532225", "123", "12", "Z", "Pacific/Auckland") and invalid name ""
    Then the airport parameters are rejected, and an error code -2 is returned

  Scenario: Adding an airport with an invalid city
    Given valid airport parameters ("Christchurch Intl", "New Zealand", "CHC", "NZCH", "-43.489358", "172.532225", "123", "12", "Z", "Pacific/Auckland") except for an invalid city ""
    When adding an airport with parameters ("Christchurch Intl", "New Zealand", "CHC", "NZCH", "-43.489358", "172.532225", "123", "12", "Z", "Pacific/Auckland") and invalid city ""
    Then the airport parameters are rejected, and an error code -3 is returned

  Scenario: Adding an airport with an invalid country
    Given valid airport parameters ("Christchurch Intl", "Christchurch", "CHC", "NZCH", "-43.489358", "172.532225", "123", "12", "Z", "Pacific/Auckland") except for an invalid country ""
    When adding an airport with parameters ("Christchurch Intl", "Christchurch", "CHC", "NZCH", "-43.489358", "172.532225", "123", "12", "Z", "Pacific/Auckland") and invalid country ""
    Then the airport parameters are rejected, and an error code -4 is returned

  Scenario: Adding an airport with an invalid IATA code
    Given valid airport parameters ("Christchurch Intl", "Christchurch", "New Zealand", "NZCH", "-43.489358", "172.532225", "123", "12", "Z", "Pacific/Auckland") except for an invalid iata "CH"
    When adding an airport with parameters ("Christchurch Intl", "Christchurch", "New Zealand", "NZCH", "-43.489358", "172.532225", "123", "12", "Z", "Pacific/Auckland") and invalid iata "CH"
    Then the airport parameters are rejected, and an error code -5 is returned

  Scenario: Adding an airport with an invalid ICAO code
    Given valid airport parameters ("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "-43.489358", "172.532225", "123", "12", "Z", "Pacific/Auckland") except for an invalid icao "NZC"
    When adding an airport with parameters ("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "-43.489358", "172.532225", "123", "12", "Z", "Pacific/Auckland") and invalid icao "NZC"
    Then the airport parameters are rejected, and an error code -6 is returned

  Scenario: Adding an airport with an invalid latitude
    Given valid airport parameters ("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "NZCH", "172.532225", "123", "12", "Z", "Pacific/Auckland") except for an invalid latitude ""
    When adding an airport with parameters ("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "NZCH", "172.532225", "123", "12", "Z", "Pacific/Auckland") and invalid latitude ""
    Then the airport parameters are rejected, and an error code -7 is returned

  Scenario: Adding an airport with an invalid longitude
    Given valid airport parameters ("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "NZCH", "-43.489358", "123", "12", "Z", "Pacific/Auckland") except for an invalid longitude ""
    When adding an airport with parameters ("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "NZCH", "-43.489358", "123", "12", "Z", "Pacific/Auckland") and invalid longitude ""
    Then the airport parameters are rejected, and an error code -8 is returned

  Scenario: Adding an airport with an invalid altitude
    Given valid airport parameters ("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "NZCH", "-43.489358", "172.532225", "12", "Z", "Pacific/Auckland") except for an invalid altitude ""
    When adding an airport with parameters ("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "NZCH", "-43.489358", "172.532225", "12", "Z", "Pacific/Auckland") and invalid altitude ""
    Then the airport parameters are rejected, and an error code -9 is returned

  Scenario: Adding an airport with an invalid timezone
    Given valid airport parameters ("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "NZCH", "-43.489358", "172.532225", "123", "Z", "Pacific/Auckland") except for an invalid timezone ""
    When adding an airport with parameters ("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "NZCH", "-43.489358", "172.532225", "123", "Z", "Pacific/Auckland") and invalid timezone ""
    Then the airport parameters are rejected, and an error code -10 is returned

  Scenario: Adding an airport with an invalid DST
    Given valid airport parameters ("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "NZCH", "-43.489358", "172.532225", "123", "12", "Pacific/Auckland") except for an invalid dst "B"
    When adding an airport with parameters ("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "NZCH", "-43.489358", "172.532225", "123", "12", "Pacific/Auckland") and invalid dst "B"
    Then the airport parameters are rejected, and an error code -11 is returned

  Scenario: Adding an airport with an invalid TZ timezone
    Given valid airport parameters ("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "NZCH", "-43.489358", "172.532225", "123", "12", "Z") except for an invalid tz ""
    When adding an airport with parameters ("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "NZCH", "-43.489358", "172.532225", "123", "12", "Z") and invalid tz ""
    Then the airport parameters are rejected, and an error code -12 is returned


  # Adding Flight Entries

  Scenario: Adding a valid flight entry
    Given valid flight entry parameters (1, "APT", "NZCH", "0", "-43.4866", "172.534")
    When adding a flight entry with parameters (1, "APT", "NZCH", "0", "-43.4866", "172.534")
    Then the flight entry is added with flight id 1 and unique id 1

  Scenario: Adding a flight entry with an invalid flightID
    Given valid flight entry parameters ("APT", "NZCH", "0", "-43.4866", "172.534") except for an invalid flight id -1
    When adding a flight entry with parameters ("APT", "NZCH", "0", "-43.4866", "172.534") and invalid flight id -1
    Then the flight parameters are rejected, and an error code -2 is returned

  Scenario: Adding a flight entry with an invalid location type
    Given valid flight entry parameters (1, "NZCH", "0", "-43.4866", "172.534") except for an invalid location type "HAT"
    When adding a flight entry with parameters (1, "NZCH", "0", "-43.4866", "172.534") and invalid location type "HAT"
    Then the flight parameters are rejected, and an error code -3 is returned

  Scenario: Adding a flight entry with an invalid location
    Given valid flight entry parameters (1, "APT", "0", "-43.4866", "172.534") except for an invalid location "CH"
    When adding a flight entry with parameters (1, "APT", "0", "-43.4866", "172.534") and invalid location "CH"
    Then the flight parameters are rejected, and an error code -4 is returned

  Scenario: Adding a flight entry with an invalid altitude
    Given valid flight entry parameters (1, "APT", "CHC", "-43.4866", "172.534") except for an invalid altitude ""
    When adding a flight entry with parameters (1, "APT", "CHC", "-43.4866", "172.534") and invalid altitude ""
    Then the flight parameters are rejected, and an error code -5 is returned

  Scenario: Adding a flight entry with an invalid latitude
    Given valid flight entry parameters (1, "APT", "CHC", "0", "172.534") except for an invalid latitude ""
    When adding a flight entry with parameters (1, "APT", "CHC", "0", "172.534") and invalid latitude ""
    Then the flight parameters are rejected, and an error code -6 is returned

  Scenario: Adding a flight entry with an invalid longitude
    Given valid flight entry parameters (1, "APT", "NZCH", "0", "-43.4866") except for an invalid longitude ""
    When adding a flight entry with parameters (1, "APT", "NZCH", "0", "-43.4866") and invalid longitude ""
    Then the flight parameters are rejected, and an error code -7 is returned


  # Adding Routes

  Scenario: Adding a valid route
    Given valid route parameters ("FIX", "NZCH", "HGU", "", "0", "CR2")
    When adding a route with parameters ("FIX", "NZCH", "HGU", "", "0", "CR2")
    Then the route is added with id 1

  Scenario: Adding a route with an invalid airline code
    Given valid route parameters ("NZCH", "HGU", "", "0", "CR2") except for an invalid airline code ""
    When adding a route with parameters ("FIX", "NZCH", "HGU", "", "0", "CR2") and invalid airline code ""
    Then the route parameters are rejected, and an error code -2 is returned

  Scenario: Adding a route with an invalid source airport code
    Given valid route parameters ("FIX", "HGU", "", "0", "CR2") except for an invalid source airport code ""
    When adding a route with parameters ("FIX", "HGU", "", "0", "CR2") and invalid source airport code ""
    Then the route parameters are rejected, and an error code -3 is returned

  Scenario: Adding a route with an invalid destination airport code
    Given valid route parameters ("FIX", "NZCH", "", "0", "CR2"") except for an invalid destination airport code "HG"
    When adding a route with parameters ("FIX", "NZCH", "", "0", "CR2") and invalid destination airport code "HG"
    Then the route parameters are rejected, and an error code -4 is returned

  Scenario: Adding a route with an invalid codeshare
    Given valid route parameters ("FIX", "NZCH", "HGU", "0", "CR2") except for an invalid codeshare "N"
    When adding a route with parameters ("FIX", "NZCH", "HGU", "0", "CR2") and invalid codeshare "N"
    Then the route parameters are rejected, and an error code -5 is returned

  Scenario: Adding a route with invalid stops
    Given valid route parameters ("FIX", "NZCH", "HGU", "", "CR2") except for invalid stops ""
    When adding a route with parameters ("FIX", "NZCH", "HGU", "", "CR2") and invalid stops ""
    Then the route parameters are rejected, and an error code -6 is returned

  Scenario: Adding a route with invalid equipment
    Given valid route parameters ("FIX", "NZCH", "HGU", "", "0") except for invalid equipment ""
    When adding a route with parameters ("FIX", "NZCH", "HGU", "", "0") and invalid equipment ""
    Then the route parameters are rejected, and an error code -7 is returned