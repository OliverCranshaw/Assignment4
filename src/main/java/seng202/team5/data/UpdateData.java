package seng202.team5.data;

/**
 * UpdateData
 *
 * Contains abstract methods for updating airlines, airports, flight entries, and routes.
 *
 * @author Billie Johnson
 */
public abstract class UpdateData {

    /**
     * Ensures validity of parameters, passes them into the updateAirline method of AirlineService.
     *
     * @param id The airline_id of the given airline you want to update.
     * @param new_name The new name of the airline, may be "" if not to be updated.
     * @param new_alias The new alias of the airline, may be "" if not to be updated.
     * @param new_iata The new 2-letter IATA code of the airline, may be "" if not to be updated.
     * @param new_icao The new 3-letter ICAO code of the airline, may be "" if not to be updated.
     * @param new_callsign The new callsign of the airline, may be "" if not to be updated.
     * @param new_country The new country of the airline, may be "" if not to be updated.
     * @param new_active The new active of the airline, "Y" or "N", may be "" if not to be updated.
     * @return int The airline_id of the airline that was just updated by the AirlineService.
     */
    public abstract int updateAirline(int id, String new_name, String new_alias, String new_iata, String new_icao,
                                       String new_callsign, String new_country, String new_active);

    /**
     * Ensures validity of parameters, passes them into the updateAirport method of AirportService.
     *
     * @param id The airport_id of the given airport you want to update.
     * @param new_name The new name of the airport, may be "" if not to be updated.
     * @param new_city The new city of the airport, may be "" if not to be updated.
     * @param new_country The new country of the airport, may be "" if not to be updated.
     * @param new_iata The new 3-letter IATA code of the airport, may be "" if not to be updated.
     * @param new_icao The new 4-letter ICAO code of the airport, may be "" if not to be updated.
     * @param new_latitude The new latitude of the airport, a string. Negative is South and positive is North. May be "" if not to be updated.
     * @param new_longitude The new longitude of the airport, a string. Negative is West and positive is East. May be "" if not to be updated.
     * @param new_altitude The new altitude of the airport in feet, a string. May be "" if not to be updated.
     * @param new_timezone The new timezone of the airport, hours offset from UTC. A string, may be "" if not to be updated.
     * @param new_dst The new dst of the airport, one of E (Europe), A (US/Canada), S (South America), O (Australia), Z (New Zealand), N (None) or U (Unknown). May be "" if not to be updated.
     * @param new_tz The new tz_database_timezone of the airport, timezone in "tz" (Olson) format. May be "" if not to be updated.
     * @return int The airport_id of the airport that was just updated by the AirportService.
     */
    public abstract int updateAirport(int id, String new_name, String new_city, String new_country, String new_iata,
                                       String new_icao, double new_latitude, double new_longitude, int new_altitude,
                                       float new_timezone, String new_dst, String new_tz);

    /**
     * Ensures validity of parameters, passes them into the updateFlight method of FlightService.
     *
     * @param id The unique id of the given flight entry you want to update.
     * @param new_airline The new 2-letter IATA or 3-letter ICAO code of the airline, may be "" if not to be updated.
     * @param new_airport The new 3-letter IATA or 4-letter ICAO code of the airport, may be "" if not to be updated.
     * @param new_altitude The new altitude of the flight entry in feet, a string. May be "" if not to be updated.
     * @param new_latitude The new latitude of the flight entry, a string. Negative is South and positive is North. May be "" if not to be updated.
     * @param new_longitude The new longitude of the flight entry, a string. Negative is West and positive is East. May be "" if not to be updated.
     * @return int The unique id of the flight entry that was just updated by the FlightService.
     */
    public abstract int updateFlightEntry(int id, String new_airline, String new_airport, int new_altitude,
                                           double new_latitude, double new_longitude);

    /**
     * Ensures validity of parameters, passes them into the updateRoute method of RouteService.
     *
     * @param id The route_id of the given route you want to update.
     * @param new_airline The new 2-letter IATA or 3-letter ICAO code of the airline, may be "" if not to be updated.
     * @param new_source_airport The new 3-letter IATA or 4-letter ICAO code of the source airport, may be "" if not to be updated.
     * @param new_dest_airport The new 3-letter IATA or 4-letter ICAO code of the destination airport, may be "" if not to be updated.
     * @param new_codeshare The new codeshare of the route, "Y" or "N", may be "" if not to be updated.
     * @param new_stops The new number of stops for the route, a string, may be "" if not to be updated.
     * @param new_equipment The new equipment for the route, may be "" if not to be updated.
     * @return int The route_id of the route that was just updated by the RouteService.
     */
    public abstract int updateRoute(int id, String new_airline, String new_source_airport, String new_dest_airport,
                                     String new_codeshare, int new_stops, String new_equipment);

}
