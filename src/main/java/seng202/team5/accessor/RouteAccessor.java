package seng202.team5.accessor;

import seng202.team5.database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * RouteAccessor
 *
 * Contains the functions save, update, delete, getData, and dataExists for routes that directly interact with the database.
 * Implements the Accessor interface.
 */
public class RouteAccessor implements Accessor {

    private final Connection dbHandler;

    /**
     * Constructor for RouteAccessor.
     * Gets the connection to the database.
     */
    public RouteAccessor() {
        dbHandler = DBConnection.getConnection();
    }

    /**
     * Creates a route in the database with the given data.
     * Requires the airline IATA or ICAO code, airline_id, source airport IATA or ICAO code, source airport_id,
     * destination airport IATA or ICAO code, destination airport_id, codeshare, number of stops, and equipment.
     *
     * @param data An List containing the data to be inserted into an entry in the database.
     * @return int result The route_id of the route that was just created, -1 if SQL exception occurs.
     */
    public int save(List<Object> data) {
        int result;

        try {
            // The SQL insert statement
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO ROUTE_DATA(airline, airline_id, source_airport, source_airport_id, "
                                            + "destination_airport, destination_airport_id, codeshare, stops, equipment) "
                                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            // Iterates through the List and adds the values to the insert statement
            for (int i=1; i < 10; i++) {
                stmt.setObject(i, data.get(i-1));
            }
            // Executes the insert operation, sets the result to the route_id of the new route
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            result = keys.getInt(1);
        } catch (SQLException e) {
            // If any of the above fails, sets result to the error code -1 and prints an error message
            result = -1;
            System.err.println("Failed to save new route data");
            System.err.println(e.getMessage());
        }

        return result;
    }

    /**
     * Updates a given route with new data.
     * Not every field must be updated.
     *
     * @param id The route_id of the given route you want to update.
     * @param newAirline The new 2-letter IATA or 3-letter ICAO code of the airline, may be null if not to be updated.
     * @param newAirlineID The airline_id of the airline with the given IATA or ICAO code.
     * @param newSourceAirport The new 3-letter IATA or 4-letter ICAO code of the source airport, may be null if not to be updated.
     * @param newSourceAirportID The airport_id of the source airline with the given IATA or ICAO code.
     * @param newDestAirport The new 3-letter IATA or 4-letter ICAO code of the destination airport, may be null if not to be updated.
     * @param newDestAirportID The airport_id of the destination airline with the given IATA or ICAO code.
     * @param newCodeshare The new codeshare of the route, "Y" or "N", may be null if not to be updated.
     * @param newStops The new number of stops for the route, an integer, may be -1 if not to be updated.
     * @param newEquipment The new equipment for the route, may be null if not to be updated.
     * @return int result The number of rows modified or -1 for error
     */
    public int update(int id, String newAirline, Integer newAirlineID, String newSourceAirport, Integer newSourceAirportID,
                      String newDestAirport, Integer newDestAirportID, String newCodeshare, Integer newStops, String newEquipment) {
        int result;

        List<Object> element = Arrays.asList(newAirline, newAirlineID, newSourceAirport, newSourceAirportID, newDestAirport,
                newDestAirportID, newCodeshare, newStops, newEquipment);
        ArrayList<Object> elements = new ArrayList<>(element);
        // The SQL update statement
        String search = "UPDATE ROUTE_DATA SET airline = ?, airline_id = ?, source_airport = ?, source_airport_id = ?, " +
                "destination_airport =? , destination_airport_id = ?, codeshare = ?, stops = ?, equipment = ? WHERE route_id = ?";

        try {
            PreparedStatement stmt = dbHandler.prepareStatement(search);
            // Adds the parameters to the SQL statement
            for (int i = 0; i < elements.size(); i++) {
                stmt.setObject(i+1, elements.get(i));
            }
            stmt.setObject(elements.size()+1, id);
            // Executes the update operation
            result = stmt.executeUpdate();
        } catch (SQLException e) {
            // If any of the above fails, sets result to the error code -1 and prints out an error message
            result = -1;
            System.err.println("Unable to update route data with id " + id);
            System.err.println(e.getMessage());
        }

        return result;
    }

    /**
     * Deletes a given route from the database.
     *
     * @param id The route_id of the route to be deleted.
     * @return boolean result True if the delete operation is successful, False otherwise.
     */
    public boolean delete(int id) {
        boolean result = false;

        try {
            // The SQL delete statement
            PreparedStatement stmt = dbHandler.prepareStatement("DELETE FROM ROUTE_DATA WHERE route_id = ?");
            stmt.setInt(1, id); // Adds the route_id to the delete statement
            // Executes the delete operation, returns True if successful
            result = stmt.executeUpdate() != 0;
        } catch (Exception e) {
            // If any of the above fails, prints out an error message
            System.err.println("Unable to delete route data with id " + id);
            System.err.println(e.getMessage());
        }

        return result;
    }

    /**
     * Retrieves the route with the provided id.
     *
     * @param id int id of a route.
     * @return ResultSet of the route, null if SQL exception occurs.
     */
    public ResultSet getData(int id) {
        ResultSet result = null;

        try {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT * FROM ROUTE_DATA WHERE route_id = ?");
            stmt.setObject(1, id);

            result = stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Failed to retrieve route with id " + id);
            System.err.println(e.getMessage());
        }

        return result;
    }

    /**
     * Retrieves the route with the provided airline code.
     *
     * @param airline String, airline IATA/ICAO code.
     * @return ResultSet of the route, null if SQL exception occurs.
     */
    public ResultSet getData(String airline) {
        ResultSet result = null;

        try {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT * FROM ROUTE_DATA WHERE airline = ?");
            stmt.setObject(1, airline);

            result = stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Failed to retrieve route");
            System.err.println(e.getMessage());
        }

        return result;
    }

    /**
     * Retrieves all the routes with provided data.
     *
     * @param sourceAirport The 3-letter IATA or 3-letter ICAO code of the destination airport.
     * @param destAirport The 3-letter IATA or 3-letter ICAO code of the destination airport.
     * @param stops The number of stops on this route, 0 if it is direct. An integer.
     * @param equipment 3-letter codes for plane type(s) typically used on this flight, separated by spaces.
     * @return ResultSet of all the routes, null if SQL exception occurs.
     */
    public ResultSet getData(ArrayList<String> sourceAirport, ArrayList<String> destAirport, int stops, String equipment) {
        ResultSet result = null;

        boolean check = true;
        String addString;
        String query = "SELECT * FROM ROUTE_DATA";
        ArrayList<Object> elements = new ArrayList<>();

        try {
            if (sourceAirport != null) {
                query = query + " WHERE ";

                for (String value:sourceAirport) {
                    if (value != null) {
                        addString = elements.size() == 0 ? " ( source_airport = ? " : " or source_airport = ? ";
                        query = query.concat(addString);
                        elements.add(value);
                    }
                }
                query = query + " ) ";
            }
            if (destAirport != null) {
                if (sourceAirport != null) {
                    query = query + " and ";
                } else {
                    query = query + " WHERE ";
                }

                for (String value:destAirport) {
                    if (value != null) {
                        addString = check ? " ( destination_airport = ? " : " or destination_airport = ? ";
                        query = query.concat(addString);
                        elements.add(value);
                        check = false;
                    }
                }
                query = query + " ) ";
            }
            if (stops != -1) {
                if (sourceAirport != null || destAirport != null) {
                    query = query + " and stops = ?";
                } else {
                    query = query + " WHERE stops = ?";
                }
                elements.add(stops);
            }
            if (equipment != null) {
                if (sourceAirport != null || destAirport != null || stops != -1) {
                    query = query + " and equipment = ?";
                } else {
                    query = query + " WHERE equipment = ?";
                }
                elements.add(equipment);
            }

            PreparedStatement stmt = dbHandler.prepareStatement(query);

            int index = 1;
            for (Object element: elements) {
                stmt.setObject(index, element);
                index++;
            }

            result = stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Failed to retrieve route data");
            System.err.println(e.getMessage());
        }

        return result;
    }

    /**
     * Checks if a route with a given route_id exists.
     *
     * @param id An integer route_id.
     * @return boolean result True if a route exists with the given route_id, False otherwise.
     */
    public boolean dataExists(int id) {
        boolean result = false;

        try {
            // The SQL search query - finds the number of routes with a given route_id (can only be 0 or 1 as route_id is unique)
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT COUNT(route_id) FROM ROUTE_DATA WHERE route_id = ?");
            // Adds the given route_id into the search query
            stmt.setInt(1, id);

            Object data = stmt.executeQuery().getObject(1);
            result = (int) data != 0;
        } catch (Exception e) {
            // If any of the above fails, prints out an error message
            System.err.println("Unable to retrieve route data with id " + id);
            System.err.println(e.getMessage());
        }

        return result;
    }


    /**
     * Gets the maximum route_id contained in the database.
     *
     * @return int id The maximum route_id in the database, 0 if SQL exception occurs.
     */
    public int getMaxID() {
        int id = 0;

        try {
            // The SQL search query - finds the maximum route_id in the database
            PreparedStatement stmt = dbHandler.prepareStatement("SELECT MAX(route_id) FROM ROUTE_DATA");
            // Executes the search query, sets result to the first entry in the ResultSet (there will at most be one entry)
            ResultSet result = stmt.executeQuery();
            id = result.getInt(1);

        } catch (SQLException e) {
            // If any of the above fails, prints an error message
            System.err.println("Unable to get maximum id.");
            System.err.println(e.getMessage());
        }

        return id;
    }


    /**
     * Gets the ids of all airlines that cover the given route id.
     *
     * @param srcId - Id of a source airport.
     * @param dstId - Id or a destination airport.
     * @return ArrayList of Integer - Id's of airlines that cover the given route, empty ArrayList if SQL exception occurs.
     */
    public ArrayList<Integer> getAirlinesCovering(Integer srcId, Integer dstId) {
        ArrayList<Integer> result = new ArrayList<>();
        try {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT airline_id FROM ROUTE_DATA WHERE source_airport_id = ? and destination_airport_id = ?");
            stmt.setObject(1, srcId);
            stmt.setObject(2, dstId);

            ResultSet data = stmt.executeQuery();

            while (data.next()) {
                Integer id = data.getInt(1);
                result.add(id);
            }
        } catch (SQLException e) {
            // If any of the above fails, prints an error message
            System.err.println("Unable to retrieve airlines covering route with srcID: " + srcId + ", dstID: " + dstId);
            System.err.println(e.getMessage());
        }
        return result;
    }


    /**
     * Returns the counts of the number of airlines covering each route.
     *
     * @param routeIds ArrayList of route ids.
     * @return ResultSet - containing source_airport, destination airport and the count of airlines that cover that route
     * null if SQL exception occurs.
     */
    public ResultSet getCountAirlinesCovering(ArrayList<Integer> routeIds) {
        ResultSet result = null;

        try {
            String query = "SELECT distinct airline_id, source_airport, destination_airport, count(*), route_id FROM ROUTE_DATA WHERE route_id IN (";
            for (int i = 0; i < routeIds.size(); i++) {
                query = query + routeIds.get(i);
                if (i != routeIds.size() - 1) {
                    query = query + ", ";
                }
            }
            query = query + ")";
            query = query + " GROUP BY source_airport_id, destination_airport_id";
            PreparedStatement stmt = dbHandler.prepareStatement(query);

            result = stmt.executeQuery();
        } catch (SQLException e) {
            // If any of the above fails, prints an error message
            System.err.println("Failed to retrieve the airlines route coverage.");
            System.err.println(e.getMessage());
        }

        return result;
    }
}
