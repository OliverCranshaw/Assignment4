
Feature: Reading files

  Scenario: Removing quotes from a string that is only quotes, i.e. ""
    Given a string that is only quotes, ""
    When remove quotes from only quotes
    Then the string becomes an empty string

  Scenario: Removing quotes from a string that is empty
    Given a string that is empty, ""
    When remove quotes from empty string
    Then the string remains an empty string

  Scenario: Removing quotes from a string that has no quotes, i.e. \N
    Given a string without quotes, e.g. \N
    When remove quotes from a string with no quotes
    Then the string remains the same, e.g. \N

  Scenario: Removing quotes from a string that is a normal string with quotes, i.e. "New Zealand"
    Given a string with quotes, e.g. "New Zealand"
    When remove quotes from a normal string with quotes
    Then the string has quotes removed, e.g New Zealand


  Scenario: Splitting normal data
    Given normal data with no blank entries and all with quotation marks
    When splitting normal entries
    Then the data is split into separate entries, all with quotation marks removed

  Scenario: Splitting abnormal data
    Given data with some blank entries, and not all entries having quotation marks
    When splitting abnormal entries
    Then the data is split into separate entries including any blank spaces, any entries with quotation marks now have them removed

  Scenario: Splitting empty data entries
    Given data with only blank entries
    When splitting empty entries
    Then the data is split into separate empty strings

  Scenario: Splitting data with no commas and with quotation marks
    Given data with no commas but with quotation marks
    When splitting single entry with quotes
    Then the data is one string with quotation marks removed

  Scenario: Splitting data with no commas and no quotation marks
    Given data with no commas and no quotation marks
    When splitting single entry
    Then the data is one string, the same as what was input

  Scenario: Splitting empty data
    Given empty data with no commas
    When splitting empty line
    Then the data is one empty string


  Scenario: Reading normal airline data from a file with an airline id
    Given a file with airline data with 8 entries
    When reading normal airline data with an airline id
    Then the airline id is removed, the airline data is split into 7 entries, and an airline is added with id 1

  Scenario: Reading normal airline data from a file without an airline id
    Given a file with airline data with 7 entries
    When reading normal airline data without an airline id
    Then the airline data is split into the 7 entries, and an airline is added with id 1

  Scenario: Reading abnormal airline data from a file with an airline id
    Given a file with airline data with an airline id, 8 entries, and abnormal entries such as blank spaces, N/A, etc.
    When reading abnormal airline data with an airline id
    Then the airline id is removed, the airline data is split into 7 entries with quotes removed, and an airline is added with id 1

  Scenario: Reading airline data from a file with less than 7 entries
  Given a file with airline data with too few entries, i.e. less than 7 entries
  When reading airline data with less than 7 entries
  Then the airline data is rejected, and the user is told that their data has too few entries

  Scenario: Reading airline data from a file with more than 8 entries
  Given a file with airline data with too many entries, i.e. more than 8 entries
  When reading airline data with more than 8 entries
  Then the airline data is rejected, and the user is told that their data has too many entries

  Scenario: Reading multiple airlines from a file with normal data
    Given a file with multiple airlines, i.e. 5, of normal airline data, i.e. airline data with 7 or 8 entries
    When reading multiple instances of normal airline data
    Then each line is split with quotes removed, and adds each airline to the database with an incrementing id, i.e. last id is 5

  Scenario: Reading multiple airlines from a file where some lines don't have the correct number of entries
    Given a file with multiple airlines, i.e. 5, where the first and third lines have the incorrect number of entries
    When reading multiple instances of airline data with some having incorrect numbers of entries
    Then each line is split with quotes removed, and adds each airline except for the ones with the incorrect number of entries to the database with incrementing ids, i.e. last id is 3


  Scenario: Reading normal airport data from a file with an airport id
    Given a file with airport data with 12 entries
    When reading normal airport data with an airport id
    Then the airport id is removed, the airport data is split into 11 entries with quotes removed, and an airport is added with id 1

  Scenario: Reading normal airport data from a file without an airport id
    Given a file with airport data with 11 entries
    When reading normal airport data without an airport id
    Then the airport data is split into the 11 entries, with quotes removed, and an airport is added with id 1

  Scenario: Reading abnormal airport data from a file with an airport id
    Given a file with airport data with an airport id, 12 entries, and abnormal entries such as blank spaces
    When reading abnormal airport data with an airport id
    Then the airport id is removed, the airport data is split into 11 entries with quotes removed, and an airport is added with id 1

  Scenario: Reading airport data from a file with less than 11 entries
    Given a file with airport data with too few entries, i.e. less than 11 entries
    When reading airport data with too few entries
    Then the airport data is rejected, and the user is told that their data has too few entries

  Scenario: Reading airport data from a file with more than 12 entries
    Given a file with airport data with too many entries, i.e. more than 12 entries
    When reading airport data with too many entries
    Then the airport data is rejected, and the user is told that their data has too many entries

  Scenario: Reading multiple airports from a file with normal data
    Given a file with multiple airports, i.e. 5, of normal airport data, i.e. airport data with 11 or 12 entries
    When reading multiple instances of normal airport data
    Then each line is split with quotes removed, and adds each airport to the database with an incrementing id, i.e. last id is 5

  Scenario: Reading multiple airports from a file where some lines don't have the correct number of entries
    Given a file with multiple airports, i.e. 5, where the first and third lines have the incorrect number of entries
    When reading multiple instances of airport data with some having incorrect numbers of entries
    Then each line is split with quotes removed, and adds each airport except for the ones with the incorrect number of entries to the database with incrementing ids, i.e. last id is 3


  Scenario: Reading a normal flight entry data from a file
    Given a file with a single flight entry with 5 entries
    And the relevant airline and airport exist
    When reading normal flight data
    Then the flight data is split into the 5 entries, with quotes removed, and a flight entry is added with id 1 and flight id 1

  Scenario: Reading normal flight data from a file
    Given a file with multiple flight data entries, each with 5 entries
    And the airline and airport needed exist
    When reading multiple instances of normal flight data
    Then each flight entry is split into the 5 entries, with quotes removed, and the flight entries are all added with flight id 1

  Scenario: Reading a flight entry from a file with less than 5 entries
    Given a file with a single flight entry with too few entries, i.e. less than 5 entries
    When reading flight data with too few entries
    Then the flight data is rejected, and the user is told that their data has too few entries

  Scenario: Reading a flight entry from a file with more than 5 entries
    Given a file with a single flight entry with too many entries, i.e. more than 5 entries
    When reading flight data with too many entries
    Then the flight data is rejected, and the user is told that their data has too many entries

  Scenario: Reading abnormal flight data from a file
    Given a file with multiple flight data entries, some of them with the incorrect number of entries
    And the necessary airlines and airports exist
    When reading multiple instances of flight data with some having the incorrect number of entries
    Then if an entry with the incorrect number of entries is reached, the data is rejected, any of the entries added prior are deleted, and the user is told that their data is in the wrong format


  Scenario: Reading normal route data from a file with an airline id, and source and destination airport ids
    Given a file with route data with 9 entries
    And the relevant airline and airports exist
    When reading normal route data with an airline id, and source and destination airport ids
    Then the airline id, source and destination airport ids are removed, the route data is split into 6 entries with quotes removed, and a route is added with id 1

  Scenario: Reading normal route data from a file without an airline id, or source and destination airport ids
    Given a file with route data with 6 entries
    And the airline and airports needed exist
    When reading normal route data without airline or airport ids
    Then the route data is split into the 6 entries, with quotes removed, and a route is added with id 1

  Scenario: Reading abnormal route data from a file with an airline id, and source and destination airport ids
    Given a file with route data with an airline id, source and destination airport ids, 9 entries, and abnormal entries such as blank spaces
    And the airline and airports necessary exist
    When reading abnormal route data with an airline id, and source and destination airport ids
    Then the airline id, and source and destination airport ids are removed, the route data is split into 6 entries with quotes removed, and a route is added with id 1

  Scenario: Reading route data from a file with less than 6 entries
    Given a file with route data with too few entries, i.e. less than 6 entries
    When reading route data with too few entries
    Then the route data is rejected, and the user is told that their data has too few entries

  Scenario: Reading route data from a file with more than 6 entries but less than 9 entries
    Given a file with route data with the wrong number of entries, i.e. more than 6 entries but has less than 9 entries
    When reading route data with more than 6 but less than 9 entries
    Then the route data is rejected, and the user is told that their data has too many entries

  Scenario: Reading route data from a file with more than 9 entries
    Given a file with route data with too many entries, i.e. more than 9 entries
    When reading route data with too many entries, i.e. more than 9 entries
    Then the route data is rejected, and the user is told that their data has too many entries

  Scenario: Reading multiple routes from a file with normal data
    Given a file with multiple lines, i.e. 5, of normal route data, i.e. route data with 6 or 9 entries
    And the airline and airports needed to add routes exist
    When reading multiple instances of normal route data
    Then each line is split with quotes removed, and adds each route to the database with an incrementing id, i.e. last id is 5

  Scenario: Reading multiple routes from a file where some lines don't have the correct number of entries
    Given a file with multiple lines, i.e. 5, where the first and third lines have the incorrect number of entries
    And the airline and airports necessary to add routes exist
    When reading multiple instances of route data with some having the incorrect number of entries
    Then each line is split with quotes removed, and adds each route except for the ones with the incorrect number of entries to the database with incrementing ids, i.e. last id is 3