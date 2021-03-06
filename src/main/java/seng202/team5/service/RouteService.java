package seng202.team5.service;

import seng202.team5.accessor.AirlineAccessor;
import seng202.team5.accessor.AirportAccessor;
import seng202.team5.accessor.RouteAccessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
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
     * @param sourceAirport The 3-letter IATA code or 4-letter ICAO code of the source airport, cannot be null.
     * @param destAirport The 3-letter IATA code or 4-letter ICAO code of the destination airport, cannot be null.
     * @param codeshare "Y" if the flight is operated by a different airline, otherwise "N". Cannot be null.
     * @param stops The number of stops for the flight, 0 if it is direct. An integer, cannot be null.
     * @param equipment 3-letter codes for plane types(s) commonly used for this flight, separated by spaces. Cannot be null.
     * @return int result The route_id of the route that was just created by the RouteAccessor, -1 if checks fail or fails to save.
     */
    public int save(String airline, String sourceAirport, String destAirport, String codeshare, int stops, String equipment) {
        int airlineID;
        int sourceAirportID;
        int destAirportID;
        
        // Checks that an airline with the given IATA or ICAO code exists, if one doesn't, returns an error code of -1
        if ((airlineID = airlineAccessor.lookupCode(airline)) == -1) {
            return -2;
        }
        
        // Checks that an airport with the given IATA or ICAO code exists, if one doesn't, returns an error code of -1
        if ((sourceAirportID = airportAccessor.lookupCode(sourceAirport)) == -1) {
            return -3;
        }
        
        // Checks that an airport with the given IATA or ICAO code exists, if one doesn't, returns an error code of -1
        if ((destAirportID = airportAccessor.lookupCode(destAirport)) == -1) {
            return -4;
        }

        // Adds the parameters into an List to pass into the save method of the RouteAccessor
        List<Object> elements = Arrays.asList(airline, airlineID, sourceAirport, sourceAirportID, destAirport,
                destAirportID, codeshare, stops, equipment);

        return accessor.save(elements);
    }

    /**
     * Checks the validity of input parameters and then passes them into the update method of the RouteAccessor.
     *
     * @param id The route_id of the given route you want to update.
     * @param newAirline The new 2-letter IATA or 3-letter ICAO code of the airline, may be null if not to be updated.
     * @param newSourceAirport The new 3-letter IATA or 4-letter ICAO code of the source airport, may be null if not to be updated.
     * @param newDestAirport The new 3-letter IATA or 4-letter ICAO code of the destination airport, may be null if not to be updated.
     * @param newCodeshare The new codeshare of the route, "Y" or "N", may be null if not to be updated.
     * @param newStops The new number of stops for the route, an integer, may be -1 if not to be updated.
     * @param newEquipment The new equipment for the route, may be null if not to be updated.
     * @return int result The route_id of the route that was just updated by the RouteAccessor, -1 if checks fail.
     *
     * @throws SQLException Caused by ResultSet interactions.
     */
    public int update(int id, String newAirline, String newSourceAirport, String newDestAirport,
                      String newCodeshare, Integer newStops, String newEquipment) throws SQLException {

        int newAirlineID;
        int newSourceAirportID;
        int newDestAirportID;

        ResultSet data = getData(id);
        // Check for whether there exists an entry for this route id in the database
        if (!data.next()) {
            return 0; // 0 rows were updated
        }

        String currAirline = data.getString(2);
        int currAirlineID = data.getInt(3);
        String currSrcAirport = data.getString(4);
        int currSrcAirportID = data.getInt(5);
        String currDstAIrport = data.getString(6);
        int currDstAirportID = data.getInt(7);

        // If the airline is not null, checks that an airline with the given IATA or ICAO code exists
        // If one doesn't, returns an error code of -1
        // If one does, gets the airline_id of the airline with the given IATA or ICAO code
        if (!currAirline.equals(newAirline)) {
            if (!airlineAccessor.dataExists(newAirline)) {
                return -1;
            } else {
                newAirlineID = airlineAccessor.getAirlineId(newAirline);
            }
        } else {
            newAirlineID = currAirlineID;
        }

        // If the source airport is not null, checks that an airport with the given IATA or ICAO code exists
        // If one doesn't, returns an error code of -1
        // If one does, gets the airport_id of the airport with the given IATA or ICAO code
        if (!currSrcAirport.equals(newSourceAirport)) {
            if (!airportAccessor.dataExists(newSourceAirport)) {
                return -1;
            } else {
                newSourceAirportID = airportAccessor.getAirportId(newSourceAirport);
            }
        } else {
            newSourceAirportID = currSrcAirportID;
        }
        // If the destination airport is not null, checks that an airport with the given IATA or ICAO code exists
        // If one doesn't, returns an error code of -1
        // If one does, gets the airport_id of the airport with the given IATA or ICAO code
        if (!currDstAIrport.equals(newDestAirport)) {
            if (!airportAccessor.dataExists(newDestAirport)) {
                return -1;
            } else {
                newDestAirportID = airportAccessor.getAirportId(newDestAirport);
            }
        } else {
            newDestAirportID = currDstAirportID;
        }

        // Passes the parameters into the update method of the RouteAccessor
        return accessor.update(id, newAirline, newAirlineID, newSourceAirport, newSourceAirportID, newDestAirport,
                newDestAirportID, newCodeshare, newStops, newEquipment);
    }

    /**
     * Checks if the route to be deleted exists, and if it does then passes the route_id into the delete method of RouteAccessor.
     *
     * @param id The route_id of the route to be deleted.
     * @return boolean result True if the delete operation is successful, False otherwise.
     */
    public boolean delete(int id) {
        if (!accessor.dataExists(id)) {
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
        ArrayList<String> airportSourceIataIcao = null;
        ArrayList<String> airportDestIataIcao = null;

        if (source_airport != null) {
            airportSourceIataIcao = airportAccessor.getAirportIataIcao(source_airport);
            if (airportSourceIataIcao.isEmpty()) {
                airportSourceIataIcao.add("N/A");
            }
        }
        if (dest_airport != null) {
            airportDestIataIcao = airportAccessor.getAirportIataIcao(dest_airport);
            if (airportDestIataIcao.isEmpty()) {
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


    /**
     * Return all airline IDS that cover the given route (from src to dst). If the given boolean if false, it doesn't
     * return any inactive airlines.
     * @param srcId - Integer source airport ID.
     * @param dstId - Integer destination airport ID.
     * @param includeInactive - Boolean determines if the result should include inactive airlines.
     * @return - Arraylist of Integers of airline IDs.
     *
     * @throws SQLException Caused by ResultSet interactions.
     */
    public ArrayList<Integer> getAirlinesCoveringRoute(Integer srcId, Integer dstId, Boolean includeInactive) throws SQLException {
        ArrayList<Integer> current = accessor.getAirlinesCovering(srcId, dstId);
        ArrayList<Integer> result = new ArrayList<>();
        for (Integer airlineId : current) {
            ResultSet airlineInfo = airlineAccessor.getData(airlineId);
            if (airlineInfo.getString(8).equals("Y") || includeInactive) {
                result.add(airlineId);
            }
        }
        return result;
    }

    /**
     * Returns a hashtable of route descriptions (source code - destination code) mapped to how
     * many airlines cover that route.
     *
     * @return Hashtable (String, Integer) of route description to count of airlines, null if SQL exception occurs.
     */
    public Hashtable<String, Integer> getCountAirlinesCovering(ArrayList<Integer> routeIds) {
        ResultSet data = accessor.getCountAirlinesCovering(routeIds);
        Hashtable<String, Integer> result = new Hashtable<>();

        try {
            while (data.next()) {
                String routeDesc = data.getString(2) + " - " + data.getString(3);
                Integer count = data.getInt(4);
                result.put(routeDesc, count);
            }
            return result;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
