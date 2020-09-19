package seng202.team5.data;

import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.FlightService;
import seng202.team5.service.RouteService;

/**
 * ConcreteUpdateData
 *
 * Overrides the updateAirline(), updateAirport(), updateFlightEntry(), updateFlight(), updateRoute() methods
 * of the UpdateData abstract class.
 */
public class ConcreteUpdateData extends UpdateData {

    private AirlineService airlineService = new AirlineService();
    private AirportService airportService = new AirportService();
    private FlightService flightService = new FlightService();
    private RouteService routeService = new RouteService();

    /**
     * Implements the updateAirline method of UpdateData
     *
     * Creates an instance of AirlineData from the arguments
     * Converts empty strings to null and checks whether the arguments are valid
     * If they are valid pass them to AirlineService.updateAirline
     *
     * @param id The airline_id of the given airline you want to update.
     * @param new_name The new name of the airline, may be "" if not to be updated.
     * @param new_alias The new alias of the airline, may be "" if not to be updated.
     * @param new_iata The new 2-letter IATA code of the airline, may be "" if not to be updated.
     * @param new_icao The new 3-letter ICAO code of the airline, may be "" if not to be updated.
     * @param new_callsign The new callsign of the airline, may be "" if not to be updated.
     * @param new_country The new country of the airline, may be "" if not to be updated.
     * @param new_active The new active of the airline, "Y" or "N", may be "" if not to be updated.
     * @return An error code representing the success/failure of the operation.
     */
    @Override
    public int updateAirline(int id, String new_name, String new_alias, String new_iata, String new_icao,
                              String new_callsign, String new_country, String new_active) {
        AirlineData airlineData = new AirlineData(new_name, new_alias, new_iata, new_icao, new_callsign, new_country, new_active);
        airlineData.convertBlanksToNull();

        int validityValue = airlineData.checkValues();
        if (validityValue < 0) return validityValue;

        return airlineService.update(id, airlineData.getName(), airlineData.getAlias(), airlineData.getIata(), airlineData.getIcao(), airlineData.getCallsign(), airlineData.getCountry(), airlineData.getActive());
    }

    /**
     * Implements the updateAirport method of UpdateData
     *
     * Creates an instance of AirportData from the arguments
     * Converts empty strings to null and checks whether the arguments are valid
     * If they are valid pass them to AirportService.updateAirport
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
     * @return An error code representing the success/failure of the operation.
     */
    @Override
    public int updateAirport(int id, String new_name, String new_city, String new_country, String new_iata,
                              String new_icao, double new_latitude, double new_longitude, int new_altitude,
                              float new_timezone, String new_dst, String new_tz) {
        AirportData airportData = new AirportData(new_name, new_city, new_country, new_iata, new_icao, new_latitude, new_longitude, new_altitude, new_timezone, new_dst, new_tz);
        airportData.convertBlanksToNull();

        int validityValue = airportData.checkValues();
        if (validityValue < 0) return validityValue;

        return airportService.update(id, airportData.getAirportName(), airportData.getCity(), airportData.getCountry(),
                airportData.getIata(), airportData.getIcao(), airportData.getLatitude(), airportData.getLongitude(),
                airportData.getAltitude(), airportData.getTimezone(), airportData.getDst(), airportData.getTzDatabaseTimezone());
    }

    /**
     * Implements the updateFlightEntry method of UpdateData
     *
     * Creates an instance of FlightData from the arguments
     * Converts empty strings to null and checks whether the arguments are valid
     * If they are valid pass them to FlightService.updateFlight
     *
     * @param id The unique id of the given flight entry you want to update.
     * @param new_location The location of a flight, may be "" if not to be updated.
     * @param new_location_type The location type of a flight, may be "" if not to be updated.
     * @param new_altitude The new altitude of the flight entry in feet, a string. May be "" if not to be updated.
     * @param new_latitude The new latitude of the flight entry, a string. Negative is South and positive is North. May be "" if not to be updated.
     * @param new_longitude The new longitude of the flight entry, a string. Negative is West and positive is East. May be "" if not to be updated.
     * @return An error code representing the success/failure of the operation.
     */
    @Override
    public int updateFlightEntry(int id, String new_location_type, String new_location, int new_altitude,
                                  double new_latitude, double new_longitude) {
        FlightData flightData = new FlightData(id, new_location_type, new_location, new_altitude, new_latitude, new_longitude);
        flightData.convertBlanksToNull();
        int validityValue = flightData.checkValues();
        if (validityValue < 0)
            return validityValue;
        return flightService.update(flightData.getFlightId(), flightData.getLocationType(), flightData.getLocation(), flightData.getAltitude(), flightData.getLatitude(), flightData.getLongitude());
    }

    /**
     * Implements the updateRoute method of UpdateData
     *
     * Creates an instance of RouteData from the arguments
     * Converts empty strings to null and checks whether the arguments are valid
     * If they are valid pass them to RouteService.updateRoute
     *
     * @param id The route_id of the given route you want to update.
     * @param new_airline The new 2-letter IATA or 3-letter ICAO code of the airline, may be "" if not to be updated.
     * @param new_source_airport The new 3-letter IATA or 4-letter ICAO code of the source airport, may be "" if not to be updated.
     * @param new_dest_airport The new 3-letter IATA or 4-letter ICAO code of the destination airport, may be "" if not to be updated.
     * @param new_codeshare The new codeshare of the route, "Y" or "N", may be "" if not to be updated.
     * @param new_stops The new number of stops for the route, a string, may be "" if not to be updated.
     * @param new_equipment The new equipment for the route, may be "" if not to be updated.
     * @return An error code representing the success/failure of the operation.
     */
    @Override
    public int updateRoute(int id, String new_airline, String new_source_airport, String new_dest_airport,
                            String new_codeshare, int new_stops, String new_equipment) {
        RouteData routeData = new RouteData(new_airline, new_source_airport, new_dest_airport, new_codeshare, new_stops, new_equipment);
        routeData.convertBlanksToNull();
        int validityValue = routeData.checkValues();
        if (validityValue < 0)
            return validityValue;
        return routeService.update(id, routeData.getAirline(), routeData.getSourceAirport(), routeData.getDestinationAirport(), routeData.getCodeShare(), routeData.getStops(), routeData.getEquipment());
    }
}
