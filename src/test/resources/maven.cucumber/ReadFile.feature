
@ReadFile

Feature: Reading files

  # Reading Airline Data

  Scenario: Reading a valid airline from a file
    Given a file "src/test/java/seng202/team5/data/testfiles/normal_airline.txt" with valid airline data
    When reading valid airline data from a file
    Then an airline is added with id 1

  Scenario: Reading an invalid airline from a file with less than 7 entries
    Given a file "src/test/java/seng202/team5/data/testfiles/airline_too_few_entries.txt" with airline data with too few entries, i.e. less than 7 entries
    When reading airline data with less than 7 entries from a file
    Then the airline data is rejected with an error code -2

  Scenario: Reading an invalid airline from a file "file" with more than 8 entries
    Given a file "src/test/java/seng202/team5/data/testfiles/airline_too_many_entries.txt" with airline data with too many entries, i.e. more than 8 entries
    When reading airline data with more than 8 entries from a file
    Then the airline data is rejected with an error code -3

  Scenario: Reading multiple airlines from a file
    Given a file "src/test/java/seng202/team5/data/testfiles/abnormal_airlines_multiple.txt" with multiple airlines
    When reading multiple instances of airline data from a file
    Then each valid airline is added to the database with an incrementing id


  # Reading Airport Data

  Scenario: Reading a valid airport from a file
    Given a file "src/test/java/seng202/team5/data/testfiles/normal_airport_with_id.txt" with valid airport data
    When reading valid airport data from a file
    Then an airport is added with id 1

  Scenario: Reading an invalid airport from a file with less than 11 entries
    Given a file "src/test/java/seng202/team5/data/testfiles/airport_too_few_entries.txt" with airport data with too few entries, i.e. less than 11 entries
    When reading airport data with less than 11 entries from a file
    Then the airport data is rejected with an error code -2

  Scenario: Reading an invalid airport from a file with more than 12 entries
    Given a file "src/test/java/seng202/team5/data/testfiles/airport_too_many_entries.txt" with airport data with too many entries, i.e. more than 12 entries
    When reading airport data with more than 12 entries from a file
    Then the airport data is rejected with an error code -3

  Scenario: Reading multiple valid airports from a file
    Given a file "src/test/java/seng202/team5/data/testfiles/normal_airports_multiple.txt" with multiple airports
    When reading multiple instances of airport data from a file
    Then each valid airport is added to the database with an incrementing id


  # Reading Flight Data

  Scenario: Reading a valid flight entry from a file
    Given a file "src/test/java/seng202/team5/data/testfiles/normal_flight_entry.txt" with a single valid flight entry
    When reading valid flight data from a file
    Then a flight entry is added with id 1 and flight id 1

  Scenario: Reading a valid flight from a file
    Given a file "src/test/java/seng202/team5/data/testfiles/normal_flight.txt" with multiple valid flight entries
    When reading multiple instances of valid flight data from a file
    Then the flight entries are all added with flight id 1 and an incrementing unique id

  Scenario: Reading a flight entry from a file with the wrong number of entries, i.e. less or more than 5
    Given a file "src/test/java/seng202/team5/data/testfiles/flight_entry_too_few_entries.txt" with a single flight entry with the wrong number of entries, i.e. less or more than 5
    When reading flight data with the wrong number of entries from a file
    Then the flight data is rejected, and two error codes of -1 are returned

  Scenario: Reading an abnormal flight from a file
    Given a file "src/test/java/seng202/team5/data/testfiles/abnormal_flight.txt" with multiple flight data entries, some of them invalid
    When reading multiple instances of flight data with some being invalid from a file
    Then an invalid flight entry is reached all previous ones are deleted, and two error codes of -1 are returned


  # Reading Route Data

  Scenario: Reading a valid route from a file
    Given a file "src/test/java/seng202/team5/data/testfiles/normal_route_9_entries.txt" with valid route data
    When reading valid route data from a file
    Then a route is added with id 1

  Scenario: Reading route data from a file with less than 6 entries
    Given a file "src/test/java/seng202/team5/data/testfiles/route_too_few_entries.txt" with route data with too few entries, i.e. less than 6 entries
    When reading route data with less than 6 entries from a file
    Then the route data is rejected, and an error code of -2 is returned

  Scenario: Reading route data from a file with more than 6 entries but less than 9 entries
    Given a file "src/test/java/seng202/team5/data/testfiles/route_too_many_entries_less_than_9.txt" with route data with the wrong number of entries, i.e. more than 6 entries but has less than 9 entries
    When reading route data with more than 6 but less than 9 entries from a file
    Then the route data is rejected, and an error code of -3 is returned

  Scenario: Reading route data from a file with more than 9 entries
    Given a file "src/test/java/seng202/team5/data/testfiles/route_too_many_entries.txt" with route data with too many entries, i.e. more than 9 entries
    When reading route data with more than 9 entries from a file
    Then the route data is rejected, and an error code of -4 is returned

  Scenario: Reading multiple routes from a file
    Given a file "src/test/java/seng202/team5/data/testfiles/abnormal_routes_multiple.txt" with multiple routes
    When reading multiple instances of route data from a file
    Then each valid route is added to the database with an incrementing id