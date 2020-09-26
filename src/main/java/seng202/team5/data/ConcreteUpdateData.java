package seng202.team5.data;

import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.FlightService;
import seng202.team5.service.RouteService;

import java.sql.SQLException;

/**
 * ConcreteUpdateData
 *
 * Overrides the updateAirline(), updateAirport(), updateFlightEntry(), updateFlight(), updateRoute() methods
 * of the UpdateData abstract class.
 */
public class ConcreteUpdateData extends UpdateData {

    private final AirlineService airlineService;
    private final AirportService airportService;
    private final FlightService flightService;
    private final RouteService routeService;

    /**
     * Constructor for ConcreteUpdateData.
     * Initializes all of the necessary Services.
     */
    public ConcreteUpdateData() {
        airlineService = new AirlineService();
        airportService = new AirportService();
        flightService = new FlightService();
        routeService = new RouteService();
    }

    /**
     * Implements the updateAirline method of UpdateData
     *
     * Creates an instance of AirlineData from the arguments
     * Converts empty strings to null and checks whether the arguments are valid
     * If they are valid pass them to AirlineService.updateAirline
     *
     * @param id The airline_id of the given airline you want to update.
     * @param newName The new name of the airline, may be "" if not to be updated.
     * @param newAlias The new alias of the airline, may be "" if not to be updated.
     * @param newIATA The new 2-letter IATA code of the airline, may be "" if not to be updated.
     * @param newICAO The new 3-letter ICAO code of the airline, may be "" if not to be updated.
     * @param newCallsign The new callsign of the airline, may be "" if not to be updated.
     * @param newCountry The new country of the airline, may be "" if not to be updated.
     * @param newActive The new active of the airline, "Y" or "N", may be "" if not to be updated.
     * @return An error code representing the success/failure of the operation.
     */
    @Override
    public int updateAirline(int id, String newName, String newAlias, String newIATA, String newICAO,
                              String newCallsign, String newCountry, String newActive) throws SQLException {
        AirlineData airlineData = new AirlineData(newName, newAlias, newIATA, newICAO, newCallsign, newCountry, newActive);
        airlineData.convertBlanksToNull();

        int validityValue = airlineData.checkValues();

        if (validityValue < 0) {
            return validityValue;
        } else {
            return airlineService.update(id, airlineData.getName(), airlineData.getAlias(), airlineData.getIATA(), airlineData.getICAO(), airlineData.getCallsign(), airlineData.getCountry(), airlineData.getActive());
        }

    }

    /**
     * Implements the updateAirport method of UpdateData
     *
     * Creates an instance of AirportData from the arguments
     * Converts empty strings to null and checks whether the arguments are valid
     * If they are valid pass them to AirportService.updateAirport
     *
     * @param id The airport_id of the given airport you want to update.
     * @param newName The new name of the airport, may be "" if not to be updated.
     * @param newCity The new city of the airport, may be "" if not to be updated.
     * @param newCountry The new country of the airport, may be "" if not to be updated.
     * @param newIATA The new 3-letter IATA code of the airport, may be "" if not to be updated.
     * @param newICAO The new 4-letter ICAO code of the airport, may be "" if not to be updated.
     * @param newLatitude The new latitude of the airport, a string. Negative is South and positive is North. May be "" if not to be updated.
     * @param newLongitude The new longitude of the airport, a string. Negative is West and positive is East. May be "" if not to be updated.
     * @param newAltitude The new altitude of the airport in feet, a string. May be "" if not to be updated.
     * @param newTimezone The new timezone of the airport, hours offset from UTC. A string, may be "" if not to be updated.
     * @param newDST The new dst of the airport, one of E (Europe), A (US/Canada), S (South America), O (Australia), Z (New Zealand), N (None) or U (Unknown). May be "" if not to be updated.
     * @param newTZ The new tz_databaseTimezone of the airport, timezone in "tz" (Olson) format. May be "" if not to be updated.
     * @return An error code representing the success/failure of the operation.
     */
    @Override
    public int updateAirport(int id, String newName, String newCity, String newCountry, String newIATA,
                              String newICAO, Double newLatitude, Double newLongitude, Integer newAltitude,
                              Float newTimezone, String newDST, String newTZ) throws SQLException {
        AirportData airportData = new AirportData(newName, newCity, newCountry, newIATA, newICAO, newLatitude, newLongitude,
                newAltitude, newTimezone, newDST, newTZ);
        airportData.convertBlanksToNull();
        int validityValue = airportData.checkValues();

        if (validityValue < 0) {
            return validityValue;
        } else {
            return airportService.update(id, airportData.getAirportName(), airportData.getCity(), airportData.getCountry(),
                    airportData.getIATA(), airportData.getICAO(), airportData.getLatitude(), airportData.getLongitude(),
                    airportData.getAltitude(), airportData.getTimezone(), airportData.getDST(), airportData.getTZDatabaseTimezone());
        }
    }

    /**
     * Implements the updateFlightEntry method of UpdateData
     *
     * Creates an instance of FlightData from the arguments
     * Converts empty strings to null and checks whether the arguments are valid
     * If they are valid pass them to FlightService.updateFlight
     *
     * @param id The unique id of the given flight entry you want to update.
     * @param newLocation The location of a flight, may be "" if not to be updated.
     * @param newLocationType The location type of a flight, may be "" if not to be updated.
     * @param newAltitude The new altitude of the flight entry in feet, a string. May be "" if not to be updated.
     * @param newLatitude The new latitude of the flight entry, a string. Negative is South and positive is North. May be "" if not to be updated.
     * @param newLongitude The new longitude of the flight entry, a string. Negative is West and positive is East. May be "" if not to be updated.
     * @return An error code representing the success/failure of the operation.
     */
    @Override
    public int updateFlightEntry(int id, String newLocationType, String newLocation, int newAltitude,
                                  double newLatitude, double newLongitude) {
        FlightData flightData = new FlightData(id, newLocationType, newLocation, newAltitude, newLatitude, newLongitude);
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
     * @param newAirline The new 2-letter IATA or 3-letter ICAO code of the airline, may be "" if not to be updated.
     * @param newSourceAirport The new 3-letter IATA or 4-letter ICAO code of the source airport, may be "" if not to be updated.
     * @param newDestAirport The new 3-letter IATA or 4-letter ICAO code of the destination airport, may be "" if not to be updated.
     * @param newCodeshare The new codeshare of the route, "Y" or "N", may be "" if not to be updated.
     * @param newStops The new number of stops for the route, a string, may be "" if not to be updated.
     * @param newEquipment The new equipment for the route, may be "" if not to be updated.
     * @return An error code representing the success/failure of the operation.
     */
    @Override
    public int updateRoute(int id, String newAirline, String newSourceAirport, String newDestAirport,
                            String newCodeshare, int newStops, String newEquipment) throws SQLException {
        RouteData routeData = new RouteData(newAirline, newSourceAirport, newDestAirport, newCodeshare, newStops, newEquipment);
        routeData.convertBlanksToNull();
        int validityValue = routeData.checkValues();
        if (validityValue < 0)
            return validityValue;
        return routeService.update(id, routeData.getAirline(), routeData.getSourceAirport(), routeData.getDestinationAirport(), routeData.getCodeshare(), routeData.getStops(), routeData.getEquipment());
    }
}
