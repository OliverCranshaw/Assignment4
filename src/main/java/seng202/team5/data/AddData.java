package seng202.team5.data;

/**
 * AddData
 *
 * Contains abstract methods for adding airlines, airports, flight entries, and routes.
 *
 * @author Billie Johnson
 */
public abstract class AddData {

    /**
     * Ensures validity of parameters, passes them into the saveAirline method of AirlineService.
     *
     * @param name The name of the airline, cannot be null.
     * @param alias The alias of the airline.
     * @param iata The 2-letter IATA code of the airline, must be unique, may be null if not known/assigned.
     * @param icao The 3-letter ICAO code of the airline, must be unique, may be null if not known/assigned.
     * @param callsign The callsign of the airline.
     * @param country The country of the airline.
     * @param active "Y" if the airline is or has until recently been operational, "N" if it is defunct, cannot be null.
     * @return int The airline_id of the new airline that has been created by AirlineService.
     */
    public abstract int addAirline(String name, String alias, String iata, String icao, String callsign, String country, String active);

    /**
     * Ensures validity of parameters, passes them into the saveAirport method of AirportService.
     *
     * @param name The name of the airport, cannot be null.
     * @param city The city of the airport, cannot be null.
     * @param country The country of the airport, cannot be null.
     * @param iata The 3-letter IATA code of the airport, must be unique, may be null if not known/assigned.
     * @param icao The 4-letter ICAO code of the airport, must be unique, may be null if not known/assigned.
     * @param latitude The latitude of the airport, a string. Negative is South, positive is North, cannot be null.
     * @param longitude The longitude of the airport, a string. Negative is West, positive is East, cannot be null.
     * @param altitude The altitude of the airport in feet, a string. Cannot be null.
     * @param timezone The hours offset from UTC, a string, cannot be null.
     * @param dst The daylight savings time of the airport, one of E (Europe), A (US/Canada), S (South America), O (Australia), Z (New Zealand), N (None) or U (Unknown), cannot be null.
     * @param tz Timezone in "tz" (Olson) format, i.e. Country/Region. Cannot be null.
     * @return int The airport_id of the new airport that has been created by AirportService.
     */
    public abstract int addAirport(String name, String city, String country, String iata, String icao, String latitude,
                                    String longitude, String altitude, String timezone, String dst, String tz);

    /**
     * Ensures validity of parameters, passes them into the saveFlight method of FlightService.
     *
     * @param flightID The integer flight_id of the new flight entry, cannot be null.
     * @param airline The 2-letter IATA code or 3-letter ICAO code of the airline, cannot be null.
     * @param airport The 3-letter IATA code or 4-letter ICAO code of the airport, cannot be null.
     * @param altitude The altitude of the plane at the time of the flight entry, in feet. A string, cannot be null.
     * @param latitude The latitude of the plane at the time of the flight entry, a string. Negative is South, positive is North, cannot be null.
     * @param longitude The longitude of the plane at the time of the flight entry, a string. Negative is West, positive is East, cannot be null.
     * @return int The unique id of the new flight entry that has been created by FlightService.
     */
    public abstract int addFlightEntry(int flightID, String airline, String airport, String altitude, String latitude, String longitude);

    /**
     * Ensures validity of parameters, passes them into the saveRoute method of RouteService.
     *
     * @param airline The 2-letter IATA code or 3-letter ICAO code of the airline, cannot be null.
     * @param source_airport The 3-letter IATA code or 4-letter ICAO code of the source airport, cannot be null.
     * @param dest_airport The 3-letter IATA code or 4-letter ICAO code of the destination airport, cannot be null.
     * @param codeshare "Y" if the flight is operated by a different airline, otherwise "N". Cannot be null.
     * @param stops The number of stops for the flight, 0 if it is direct. A string, cannot be null.
     * @param equipment 3-letter codes for plane types(s) commonly used for this flight, separated by spaces. Cannot be null.
     * @return int The route_id of the new route that has been created by RouteService.
     */
    public abstract int addRoute(String airline, String source_airport, String dest_airport, String codeshare, String stops, String equipment);

}
