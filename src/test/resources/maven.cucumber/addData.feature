
Feature: Adding data

  # Adding Airlines

  Given valid airline parameters ("name", "alias", "iata", "icao", "callsign", "country", "active")
  When adding an airline with parameters ("name", "alias", "iata", "icao", "callsign", "country", "active")
  Then an airline is added with id 1

  Given valid airline parameters ("alias", "iata", "icao", "callsign", "country", "active") except for an invalid name "name"
  When adding an airline with parameters ("alias", "iata", "icao", "callsign", "country", "active") and invalid name "name"
  Then the parameters are rejected, and an error code -2 is returned

  Given valid airline parameters ("name", "alias", "icao", "callsign", "country", "active") except for an invalid iata code "iata"
  When adding an airline with parameters ("name", "alias", "icao", "callsign", "country", "active") and invalid iata code "iata"
  Then the parameters are rejected, and an error code -3 is returned

  Given valid airline parameters ("name", "alias", "iata", "callsign", "country", "active") except for an invalid icao code "icao"
  When adding an airline with parameters ("name", "alias", "iata", "callsign", "country", "active") and invalid icao code "icao"
  Then the parameters are rejected, and an error code -4 is returned

  Given valid airline parameters ("name", "alias", "iata", "icao", "callsign", "country") except for an invalid active "active"
  When adding an airline with parameters ("name", "alias", "iata", "icao", "callsign", "country") and invalid active "active"
  Then the parameters are rejected, and an error code -5 is returned


  # Adding Airports

  Given valid airport parameters ("name", "city", "country", "iata", "icao", "latitude", "longitude", "altitude", "timezone", "dst", "tz")
  When adding an airport with parameters ("name", "city", "country", "iata", "icao", "latitude", "longitude", "altitude", "timezone", "dst", "tz")
  Then an airport is added with id 1

  Given valid airport parameters ("city", "country", "iata", "icao", "latitude", "longitude", "altitude", "timezone", "dst", "tz") except for an invalid name "name"
  When adding an airport with parameters ("city", "country", "iata", "icao", "latitude", "longitude", "altitude", "timezone", "dst", "tz") and invalid name "name"
  Then the parameters are rejected, and an error code -2 is returned

  Given valid airport parameters ("name", "country", "iata", "icao", "latitude", "longitude", "altitude", "timezone", "dst", "tz") except for an invalid city "city"
  When adding an airport with parameters ("name", "country", "iata", "icao", "latitude", "longitude", "altitude", "timezone", "dst", "tz") and invalid city "city"
  Then the parameters are rejected, and an error code -3 is returned

  Given valid airport parameters ("name", "city", "iata", "icao", "latitude", "longitude", "altitude", "timezone", "dst", "tz") except for an invalid country "country"
  When adding an airport with parameters ("name", "city", "iata", "icao", "latitude", "longitude", "altitude", "timezone", "dst", "tz") and invalid country "country"
  Then the parameters are rejected, and an error code -4 is returned

  Given valid airport parameters ("name", "city", "country", "icao", "latitude", "longitude", "altitude", "timezone", "dst", "tz") except for an invalid iata "iata"
  When adding an airport with parameters ("name", "city", "country", "icao", "latitude", "longitude", "altitude", "timezone", "dst", "tz") and invalid iata "iata"
  Then the parameters are rejected, and an error code -5 is returned

  Given valid airport parameters ("name", "city", "country", "iata", "latitude", "longitude", "altitude", "timezone", "dst", "tz") except for an invalid icao "icao"
  When adding an airport with parameters ("name", "city", "country", "iata", "latitude", "longitude", "altitude", "timezone", "dst", "tz") and invalid icao "icao"
  Then the parameters are rejected, and an error code -6 is returned

  Given valid airport parameters ("name", "city", "country", "iata", "icao", "longitude", "altitude", "timezone", "dst", "tz") except for an invalid latitude "latitude"
  When adding an airport with parameters ("name", "city", "country", "iata", "icao", "longitude", "altitude", "timezone", "dst", "tz") and invalid latitude "latitude"
  Then the parameters are rejected, and an error code -7 is returned

  Given valid airport parameters ("name", "city", "country", "iata", "icao", "latitude", "altitude", "timezone", "dst", "tz") except for an invalid longitude "longitude"
  When adding an airport with parameters ("name", "city", "country", "iata", "icao", "latitude", "altitude", "timezone", "dst", "tz") and invalid longitude "longitude"
  Then the parameters are rejected, and an error code -8 is returned

  Given valid airport parameters ("name", "city", "country", "iata", "icao", "latitude", "longitude", "timezone", "dst", "tz") except for an invalid altitude "altitude"
  When adding an airport with parameters ("name", "city", "country", "iata", "icao", "latitude", "longitude", "timezone", "dst", "tz") and invalid altitude "altitude"
  Then the parameters are rejected, and an error code -9 is returned

  Given valid airport parameters ("name", "city", "country", "iata", "icao", "latitude", "longitude", "altitude", "dst", "tz") except for an invalid timezone "timezone"
  When adding an airport with parameters ("name", "city", "country", "iata", "icao", "latitude", "longitude", "altitude", "dst", "tz") and invalid timezone "timezone"
  Then the parameters are rejected, and an error code -10 is returned

  Given valid airport parameters ("name", "city", "country", "iata", "icao", "latitude", "longitude", "altitude", "timezone", "tz") except for an invalid dst "dst"
  When adding an airport with parameters ("name", "city", "country", "iata", "icao", "latitude", "longitude", "altitude", "timezone", "tz") and invalid dst "dst"
  Then the parameters are rejected, and an error code -11 is returned

  Given valid airport parameters ("name", "city", "country", "iata", "icao", "latitude", "longitude", "altitude", "timezone", "dst") except for an invalid tz "tz"
  When adding an airport with parameters ("name", "city", "country", "iata", "icao", "latitude", "longitude", "altitude", "timezone", "dst") and invalid tz "tz"
  Then the parameters are rejected, and an error code -12 is returned


  # Adding Flight Entries

  Given valid flight entry parameters (flightID, "location_type", "location", "altitude", "latitude", "longitude")
  And the location "location" exists in the airport table if the location_type "location_type" is "APT"
  When adding a flight entry with parameters (flightID, "location_type", "location", "altitude", "latitude", "longitude")
  Then a flight entry is added with flight id 1 and unique id 1

  Given valid flight entry parameters ("location_type", "location", "altitude", "latitude", "longitude") except for an invalid flight id flightID
  When adding a flight entry with parameters ("location_type", "location", "altitude", "latitude", "longitude") and invalid flight id flightID
  Then the parameters are rejected, and an error code -2 is returned

  Given valid flight entry parameters (flightID, "location", "altitude", "latitude", "longitude") except for an invalid location type "location_type"
  When adding a flight entry with parameters (flightID, "location", "altitude", "latitude", "longitude") and invalid location type "location_type"
  Then the parameters are rejected, and an error code -3 is returned

  Given valid flight entry parameters (flightID, "location_type", "altitude", "latitude", "longitude") except for an invalid location "location"
  When adding a flight entry with parameters (flightID, "location_type", "altitude", "latitude", "longitude") and invalid location "location"
  Then the parameters are rejected, and an error code -4 is returned

  Given valid flight entry parameters (flightID, "location_type", "location", "latitude", "longitude") except for an invalid altitude "altitude"
  And the location "location" exists in the airport table if the location_type "location_type" is "APT"
  When adding a flight entry with parameters (flightID, "location_type", "location", "latitude", "longitude") and invalid altitude "altitude"
  Then the parameters are rejected, and an error code -5 is returned

  Given valid flight entry parameters (flightID, "location_type", "location", "altitude", "longitude") except for an invalid latitude "latitude"
  And the location "location" exists in the airport table if the location_type "location_type" is "APT"
  When adding a flight entry with parameters (flightID, "location_type", "location", "altitude", "longitude") and invalid latitude "latitude"
  Then the parameters are rejected, and an error code -6 is returned

  Given valid flight entry parameters (flightID, "location_type", "location", "altitude", "latitude") except for an invalid longitude "longitude"
  And the location "location" exists in the airport table if the location_type "location_type" is "APT"
  When adding a flight entry with parameters (flightID, "location_type", "location", "altitude", "latitude") and invalid longitude "longitude"
  Then the parameters are rejected, and an error code -7 is returned


  # Adding Routes

  Given valid route parameters ("airline", "source_airport", "destination_airport", "codeshare", "stops", "equipment")
  And the airline code "airline" exists in the airline table
  And the source airport code "source_airport" exists in the airport table
  And the destination airport code "destination_airport" exists in the airport table
  When adding a route with parameters ("airline", "source_airport", "destination_airport", "codeshare", "stops", "equipment")
  Then a route is added with id 1

  Given valid route parameters ("source_airport", "destination_airport", "codeshare", "stops", "equipment") except for an invalid airline code "airline"
  When adding a route with parameters ("source_airport", "destination_airport", "codeshare", "stops", "equipment") and invalid airline code "airline"
  Then the parameters are rejected, and an error code -2 is returned

  Given valid route parameters ("airline", "destination_airport", "codeshare", "stops", "equipment") except for an invalid source airport code "source_airport"
  And the airline code "airline" exists in the airline table
  When adding a route with parameters ("airline", "destination_airport", "codeshare", "stops", "equipment") and invalid source airport code "source_airport"
  Then the parameters are rejected, and an error code -3 is returned

  Given valid route parameters ("airline", "source_airport", "codeshare", "stops", "equipment") except for an invalid destination airport code "destination_airport"
  And the airline code "airline" exists in the airline table
  And the source airport code "source_airport" exists in the airport table
  When adding a route with parameters ("airline", "source_airport", "codeshare", "stops", "equipment") and invalid destination airport code "destination_airport"
  Then the parameters are rejected, and an error code -4 is returned

  Given valid route parameters ("airline", "source_airport", "destination_airport", "stops", "equipment") except for an invalid codeshare "codeshare"
  And the airline code "airline" exists in the airline table
  And the source airport code "source_airport" exists in the airport table
  And the destination airport code "destination_airport" exists in the airport table
  When adding a route with parameters ("airline", "source_airport", "destination_airport", "stops", "equipment") and invalid codeshare "codeshare"
  Then the parameters are rejected, and an error code -5 is returned

  Given valid route parameters ("airline", "source_airport", "destination_airport", "codeshare", "equipment") except for invalid stops "stops"
  And the airline code "airline" exists in the airline table
  And the source airport code "source_airport" exists in the airport table
  And the destination airport code "destination_airport" exists in the airport table
  When adding a route with parameters ("airline", "source_airport", "destination_airport", "codeshare", "equipment") and invalid stops "stops"
  Then the parameters are rejected, and an error code -6 is returned

  Given valid route parameters ("airline", "source_airport", "destination_airport", "codeshare", "stops") except for invalid equipment "equipment"
  And the airline code "airline" exists in the airline table
  And the source airport code "source_airport" exists in the airport table
  And the destination airport code "destination_airport" exists in the airport table
  When adding a route with parameters ("airline", "source_airport", "destination_airport", "codeshare", "stops") and invalid equipment "equipment"
  Then the parameters are rejected, and an error code -7 is returned