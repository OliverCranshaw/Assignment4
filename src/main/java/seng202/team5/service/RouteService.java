package seng202.team5.service;

import seng202.team5.accessor.AirlineAccessor;
import seng202.team5.accessor.AirportAccessor;
import seng202.team5.accessor.RouteAccessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * RouteService
 *
 * Contains the functions save, update, delete, getData, and validity check functions for routes that interact with Accessors.
 * Implements the Service interface.
 */
public class RouteService implements Service {

    private final RouteAccessor accessor;
    private final AirlineAccessor airlineAccessor;
    private final AirportAccessor airportAccessor;

    /**
     * Constructor for RouteService.
     * Creates a RouteAccessor, AirlineAccessor, and AirportAccessor.
     */
    public RouteService() {
        accessor = new RouteAccessor();
        airlineAccessor = new AirlineAccessor();
        airportAccessor = new AirportAccessor();
    }

    /**
     * Checks the validity of input parameters and then passes them into the save method of the RouteAccessor as an ArrayList.
     *
     * @param airline The 2-letter IATA code or 3-letter ICAO code of the airline, cannot be null.
     * @param source_airport The 3-letter IATA code or 4-letter ICAO code of the source airport, cannot be null.
     * @param dest_airport The 3-letter IATA code or 4-letter ICAO code of the destination airport, cannot be null.
     * @param codeshare "Y" if the flight is operated by a different airline, otherwise "N". Cannot be null.
     * @param stops The number of stops for the flight, 0 if it is direct. An integer, cannot be null.
     * @param equipment 3-letter codes for plane types(s) commonly used for this flight, separated by spaces. Cannot be null.
     * @return int result The route_id of the route that was just created by the RouteAccessor.
     */
    public int save(String airline, String source_airport, String dest_airport, String codeshare, int stops, String equipment) {
        // Checks that an airline with the given IATA or ICAO code exists, if one doesn't, returns an error code of -1
        if (!airlineAccessor.dataExists(airline)) {
            return -1;
        }
        // Checks that an airport with the given IATA or ICAO code exists, if one doesn't, returns an error code of -1
        if (!airportAccessor.dataExists(source_airport)) {
            return -1;
        }
        // Checks that an airport with the given IATA or ICAO code exists, if one doesn't, returns an error code of -1
        if (!airportAccessor.dataExists(dest_airport)) {
            return -1;
        }

        int airline_id = airlineAccessor.getAirlineId(airline);
        int source_airport_id = airportAccessor.getAirportId(source_airport);
        int dest_airport_id = airportAccessor.getAirportId(dest_airport);

        // Adds the parameters into an List to pass into the save method of the RouteAccessor
        List<Object> elements = Arrays.asList(airline, airline_id, source_airport, source_airport_id, dest_airport,
                dest_airport_id, codeshare, stops, equipment);

        return accessor.save(elements);
    }

    /**
     * Checks the validity of input parameters and then passes them into the update method of the RouteAccessor.
     *
     * @param id The route_id of the given route you want to update.
     * @param new_airline The new 2-letter IATA or 3-letter ICAO code of the airline, may be null if not to be updated.
     * @param new_source_airport The new 3-letter IATA or 4-letter ICAO code of the source airport, may be null if not to be updated.
     * @param new_dest_airport The new 3-letter IATA or 4-letter ICAO code of the destination airport, may be null if not to be updated.
     * @param new_codeshare The new codeshare of the route, "Y" or "N", may be null if not to be updated.
     * @param new_stops The new number of stops for the route, an integer, may be -1 if not to be updated.
     * @param new_equipment The new equipment for the route, may be null if not to be updated.
     * @return int result The route_id of the route that was just updated by the RouteAccessor.
     */
    public int update(int id, String new_airline, String new_source_airport, String new_dest_airport,
                      String new_codeshare, Integer new_stops, String new_equipment) throws SQLException {

        int new_airline_id = -1;
        int new_source_airport_id = -1;
        int new_dest_airport_id = -1;

        ResultSet data = getData(id);
        // Check for whether there exists an entry for this route id in the database
        if (!data.next()) {
            return 0; // 0 rows were updated
        }


        String currAirline = data.getString(2);
        Integer currAirlineID = data.getInt(3);
        String currSrcAirport = data.getString(4);
        Integer currSrcAirportID = data.getInt(5);
        String currDstAIrport = data.getString(6);
        Integer currDstAirportID = data.getInt(7);

        // If the airline is not null, checks that an airline with the given IATA or ICAO code exists
        // If one doesn't, returns an error code of -1
        // If one does, gets the airline_id of the airline with the given IATA or ICAO code
        if (!currAirline.equals(new_airline)) {
            if (!airlineAccessor.dataExists(new_airline)) {
                return -1;
            } else {
                new_airline_id = airlineAccessor.getAirlineId(new_airline);
            }
        } else {
            new_airline_id = currAirlineID;
        }


        // If the source airport is not null, checks that an airport with the given IATA or ICAO code exists
        // If one doesn't, returns an error code of -1
        // If one does, gets the airport_id of the airport with the given IATA or ICAO code
        if (!currSrcAirport.equals(new_source_airport)) {
            if (!airportAccessor.dataExists(new_source_airport)) {
                return -1;
            } else {
                new_source_airport_id = airportAccessor.getAirportId(new_source_airport);
            }
        } else {
            new_source_airport_id = currSrcAirportID;
        }
        // If the destination airport is not null, checks that an airport with the given IATA or ICAO code exists
        // If one doesn't, returns an error code of -1
        // If one does, gets the airport_id of the airport with the given IATA or ICAO code
        if (!currDstAIrport.equals(new_dest_airport)) {
            if (!airportAccessor.dataExists(new_dest_airport)) {
                return -1;
            } else {
                new_dest_airport_id = airportAccessor.getAirportId(new_dest_airport);
            }
        } else {
            new_dest_airport_id = currDstAirportID;
        }

        // Passes the parameters into the update method of the RouteAccessor
        return accessor.update(id, new_airline, new_airline_id, new_source_airport, new_source_airport_id, new_dest_airport,
                new_dest_airport_id, new_codeshare, new_stops, new_equipment);
    }

    /**
     * Checks if the route to be deleted exists, and if it does then passes the route_id into the delete method of RouteAccessor.
     *
     * @param id The route_id of the route to be deleted.
     * @return boolean result True if the delete operation is successful, False otherwise.
     */
    public boolean delete(int id) {
        if (!accessor.dataExists(id)) {
            System.out.println("Could not delete route, does not exist.");
            return false;
        }

        return accessor.delete(id);
    }

    /**
     * Retrieves the route with specified id.
     *
     * @param id int value of an id.
     * @return ResultSet of a route.
     */
    public ResultSet getData(int id) {
        return accessor.getData(id);
    }

    /**
     * Retrieves the route with the given airline code.
     *
     * @param airline String, airline IATA/ICAO code.
     * @return ResultSet of a route.
     */
    public ResultSet getData(String airline) {
        return accessor.getData(airline);
    }

    /**
     * Retrieves all routes with the given parameters.
     *
     * @param source_airport string containing aiport IATA/ICAO.
     * @param dest_airport string containing aiport IATA/ICAO.
     * @param stops int containing number of stops.
     * @param equipment string containing type of equipment.
     * @return ResultSet of routes.
     */
    public ResultSet getData(String source_airport, String dest_airport, int stops, String equipment) {
        ArrayList airportSourceIataIcao = null;
        ArrayList airportDestIataIcao = null;

        if (source_airport != null) {
            airportSourceIataIcao = airportAccessor.getAirportIataIcao(source_airport);
            if (airportSourceIataIcao.isEmpty()) {
                airportSourceIataIcao = new ArrayList();
                airportSourceIataIcao.add("N/A");
            }
        }
        if (dest_airport != null) {
            airportDestIataIcao = airportAccessor.getAirportIataIcao(dest_airport);
            if (airportDestIataIcao.isEmpty()) {
                airportDestIataIcao = new ArrayList();
                airportDestIataIcao.add("N/A");
            }
        }

        return accessor.getData(airportSourceIataIcao, airportDestIataIcao, stops, equipment);
    }

    /**
     * Calls the getMaxID method of the RouteAccessor to get the maximum route_id contained in the database.
     *
     * @return int The maximum route_id contained in the database.
     */
    public int getMaxID() {
        return accessor.getMaxID();
    }
}
