package seng202.team5.data;

import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.FlightService;
import seng202.team5.service.RouteService;

/**
 * ConcreteAddData
 *
 * Overrides the abstract methods of the AddData class.
 * Contains the addAirline, addAirport, addFlightEntry, and addRoute methods.
 */
public class ConcreteAddData extends AddData {

    private final AirlineService airlineService;
    private final AirportService airportService;
    private final FlightService flightService;
    private final RouteService routeService;

    /**
     * Constructor for ConcreteAddData.
     * Initializes all of the necessary Services.
     */
    public ConcreteAddData() {
        airlineService = new AirlineService();
        airportService = new AirportService();
        flightService = new FlightService();
        routeService = new RouteService();
    }

    /**
     * Overrides the abstract addAirline method of AddData.
     * First creates an instance of AirlineData with the given parameters.
     * Converts any empty strings to null and checks the validity of the parameters.
     * If they are valid, passes the parameters into the saveAirline method of AirlineService.
     * Otherwise returns an error code.
     *
     * @param name The name of the airline, cannot be null.
     * @param alias The alias of the airline.
     * @param iata The 2-letter IATA code of the airline, must be unique, may be null if not known/assigned.
     * @param icao The 3-letter ICAO code of the airline, must be unique, may be null if not known/assigned.
     * @param callsign The callsign of the airline.
     * @param country The country of the airline.
     * @param active "Y" if the airline is or has until recently been operational, "N" if it is defunct, cannot be null.
     * @return int The airline_id of the new airline that has been created by AirlineService, -1 if fails to add.
     */
    @Override
    public int addAirline(String name, String alias, String iata, String icao,
                          String callsign, String country, String active) {
        // Creates a new instance of AirlineData with the given parameters
        AirlineData airlineData = new AirlineData(name, alias, iata, icao, callsign, country, active);
        // Converts any empty strings to null
        airlineData.convertBlanksToNull();
        // Checks the validity of the parameters
        int validityValue = airlineData.checkValues();
        // If they are valid, then passes them into the saveAirline method of AirlineService
        // If any of this fails or they aren't valid, an error code is returned and an error message is printed
        if (validityValue > 0) {
            int result = airlineService.save(airlineData.getName(), airlineData.getAlias(), airlineData.getIATA(),
                    airlineData.getICAO(), airlineData.getCallsign(), airlineData.getCountry(), airlineData.getActive());

            if (result != -1) {
                return airlineService.getMaxID();
            }
            else {
                return -1;
            }
        } else {
            return validityValue;
        }
    }

    /**
     * Overrides the abstract addAirport method of AddData.
     * First creates an instance of AirportData with the given parameters.
     * Converts any empty strings to null and checks the validity of the parameters.
     * If they are valid, passes the parameters into the saveAirport method of AirportService.
     * Otherwise returns an error code.
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
     * @return int The airport_id of the new airport that has been created by AirportService, -1 if fails to add.
     */
    @Override
    public int addAirport(String name, String city, String country, String iata, String icao, String latitude,
                          String longitude, String altitude, String timezone, String dst, String tz) {
        // Creates a new instance of AirportData with the given parameters
        AirportData airportData = new AirportData(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        // Converts any empty strings to null
        airportData.convertBlanksToNull();
        // Checks the validity of the parameters
        int validityValue = airportData.checkValues();
        // If they are valid, then passes them into the saveAirport method of AirportService
        // If any of this fails or they aren't valid, an error code is returned and an error message is printed
        if (validityValue > 0) {
            int result = airportService.save(airportData.getAirportName(), airportData.getCity(), airportData.getCountry(),
                    airportData.getIATA(), airportData.getICAO(), airportData.getLatitude(), airportData.getLongitude(),
                    airportData.getAltitude(), airportData.getTimezone(), airportData.getDST(), airportData.getTZDatabaseTimezone());

            if (result != -1) {
                return airportService.getMaxID();
            }
            else {
                return -1;
            }
        } else {
            return validityValue;
        }
    }

    /**
     * Overrides the abstract addFlightEntry method of AddData.
     * First creates an instance of FlightData with the given parameters.
     * Converts any empty strings to null and checks the validity of the parameters.
     * If they are valid, passes the parameters into the saveFlight method of FlightService.
     * Otherwise returns an error code.
     *
     * @param flightID The integer flight_id of the new flight entry, cannot be null.
     * @param locationType The location type of the flight entry location, one of "APT", "VOR", or "FIX", cannot be null.
     * @param location The location of the flight entry, cannot be null.
     * @param altitude The altitude of the plane at the time of the flight entry, in feet. A string, cannot be null.
     * @param latitude The latitude of the plane at the time of the flight entry, a string. Negative is South, positive is North, cannot be null.
     * @param longitude The longitude of the plane at the time of the flight entry, a string. Negative is West, positive is East, cannot be null.
     * @return int The unique id of the new flight entry that has been created by FlightService, -1 if fails to add.
     */
    @Override
    public int addFlightEntry(int flightID, String locationType, String location, String altitude, String latitude, String longitude) {
        // Creates a new instance of FlightData with the given parameters
        FlightData flightData = new FlightData(flightID, locationType, location, altitude, latitude, longitude);
        // Converts any empty strings to null
        flightData.convertBlanksToNull();
        // Checks the validity of the parameters
        int validityValue = flightData.checkValues();
        // If they are valid, then passes them into the saveFlight method of FlightService
        // If any of this fails or they aren't valid, an error code is returned and an error message is printed
        if (validityValue > 0) {
            int result = flightService.save(flightData.getFlightId(), flightData.getLocationType(), flightData.getLocation(),
                                            flightData.getAltitude(), flightData.getLatitude(), flightData.getLongitude());

            if (result != -1) {
                return flightService.getMaxID();
            }
            else {
                return -1;
            }
        } else {
            return validityValue;
        }
    }

    /**
     * Overrides the abstract addRoute method of AddData.
     * First creates an instance of RouteData with the given parameters.
     * Converts any empty strings to null and checks the validity of the parameters.
     * If they are valid, passes the parameters into the saveRoute method of RouteService.
     * Otherwise returns an error code.
     *
     * @param airline The 2-letter IATA code or 3-letter ICAO code of the airline, cannot be null.
     * @param sourceAirport The 3-letter IATA code or 4-letter ICAO code of the source airport, cannot be null.
     * @param destAirport The 3-letter IATA code or 4-letter ICAO code of the destination airport, cannot be null.
     * @param codeshare "Y" if the flight is operated by a different airline, otherwise "N". Cannot be null.
     * @param stops The number of stops for the flight, 0 if it is direct. A string, cannot be null.
     * @param equipment 3-letter codes for plane types(s) commonly used for this flight, separated by spaces. Cannot be null.
     * @return int The route_id of the new route that has been created by RouteService, -1 if fails to add.
     */
    @Override
    public int addRoute(String airline, String sourceAirport, String destAirport,
                        String codeshare, String stops, String equipment) {
        // Creates a new instance of RouteData with the given parameters
        RouteData routeData = new RouteData(airline, sourceAirport, destAirport, codeshare, stops, equipment);
        // Converts any empty strings to null
        routeData.convertBlanksToNull();
        // Checks the validity of the parameters
        int validityValue = routeData.checkValues();
        // If they are valid, then passes them into the saveRoute method of RouteService
        // If any of this fails or they aren't valid, an error code is returned and an error message is printed
        if (validityValue > 0) {
            int result = routeService.save(routeData.getAirline(), routeData.getSourceAirport(), routeData.getDestinationAirport(),
                                            routeData.getCodeshare(), routeData.getStops(), routeData.getEquipment());

            if (result >= 0) {
                return routeService.getMaxID();
            } else {
                return -1;
            }
        } else {
            return validityValue;
        }
    }
}
