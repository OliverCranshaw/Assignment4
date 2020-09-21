package seng202.team5.data;

import java.sql.SQLException;

/**
 * UpdateData
 *
 * Contains abstract methods for updating airlines, airports, flight entries, and routes.
 */
public abstract class UpdateData {

    /**
     * Ensures validity of parameters, passes them into the updateAirline method of AirlineService.
     *
     * @param id The airline_id of the given airline you want to update.
     * @param newName The new name of the airline, may be "" if not to be updated.
     * @param newAlias The new alias of the airline, may be "" if not to be updated.
     * @param newIATA The new 2-letter IATA code of the airline, may be "" if not to be updated.
     * @param new_ICAO The new 3-letter ICAO code of the airline, may be "" if not to be updated.
     * @param newCallsign The new callsign of the airline, may be "" if not to be updated.
     * @param newCountry The new country of the airline, may be "" if not to be updated.
     * @param newActive The new active of the airline, "Y" or "N", may be "" if not to be updated.
     * @return int The airline_id of the airline that was just updated by the AirlineService.
     */
    public abstract int updateAirline(int id, String newName, String newAlias, String newIATA, String new_ICAO,
                                       String newCallsign, String newCountry, String newActive) throws SQLException;

    /**
     * Ensures validity of parameters, passes them into the updateAirport method of AirportService.
     *
     * @param id The airport_id of the given airport you want to update.
     * @param newName The new name of the airport, may be "" if not to be updated.
     * @param newCity The new city of the airport, may be "" if not to be updated.
     * @param newCountry The new country of the airport, may be "" if not to be updated.
     * @param newIATA The new 3-letter IATA code of the airport, may be "" if not to be updated.
     * @param new_ICAO The new 4-letter ICAO code of the airport, may be "" if not to be updated.
     * @param newLatitude The new latitude of the airport, a string. Negative is South and positive is North. May be "" if not to be updated.
     * @param newLongitude The new longitude of the airport, a string. Negative is West and positive is East. May be "" if not to be updated.
     * @param newAltitude The new altitude of the airport in feet, a string. May be "" if not to be updated.
     * @param newTimezone The new timezone of the airport, hours offset from UTC. A string, may be "" if not to be updated.
     * @param newDST The new dst of the airport, one of E (Europe), A (US/Canada), S (South America), O (Australia), Z (New Zealand), N (None) or U (Unknown). May be "" if not to be updated.
     * @param newTZ The new tz_databaseTimezone of the airport, timezone in "tz" (Olson) format. May be "" if not to be updated.
     * @return int The airport_id of the airport that was just updated by the AirportService.
     */
    public abstract int updateAirport(int id, String newName, String newCity, String newCountry, String newIATA,
                                       String new_ICAO, Double newLatitude, Double newLongitude, Integer newAltitude,
                                       Float newTimezone, String newDST, String newTZ) throws SQLException;

    /**
     * Ensures validity of parameters, passes them into the updateFlight method of FlightService.
     *
     * @param id The unique id of the given flight entry you want to update.
     * @param newLocationType The location type of the flight entry location, one of "APT", "VOR", or "FIX". May be "" if not to be updated.
     * @param newLocation The new location of the flight entry. May be "" if not to be updated.
     * @param newAltitude The new altitude of the flight entry in feet, a string. May be "" if not to be updated.
     * @param newLatitude The new latitude of the flight entry, a string. Negative is South and positive is North. May be "" if not to be updated.
     * @param newLongitude The new longitude of the flight entry, a string. Negative is West and positive is East. May be "" if not to be updated.
     * @return int The unique id of the flight entry that was just updated by the FlightService.
     */
    public abstract int updateFlightEntry(int id, String newLocationType, String newLocation, int newAltitude,
                                           double newLatitude, double newLongitude);

    /**
     * Ensures validity of parameters, passes them into the updateRoute method of RouteService.
     *
     * @param id The route_id of the given route you want to update.
     * @param newAirline The new 2-letter IATA or 3-letter ICAO code of the airline, may be "" if not to be updated.
     * @param newSourceAirport The new 3-letter IATA or 4-letter ICAO code of the source airport, may be "" if not to be updated.
     * @param newDestAirport The new 3-letter IATA or 4-letter ICAO code of the destination airport, may be "" if not to be updated.
     * @param newCodeshare The new codeshare of the route, "Y" or "N", may be "" if not to be updated.
     * @param newStops The new number of stops for the route, a string, may be "" if not to be updated.
     * @param newEquipment The new equipment for the route, may be "" if not to be updated.
     * @return int The route_id of the route that was just updated by the RouteService.
     */
    public abstract int updateRoute(int id, String newAirline, String newSourceAirport, String newDestAirport,
                                     String newCodeshare, int newStops, String newEquipment) throws SQLException;
}
