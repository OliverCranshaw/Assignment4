package seng202.team5.accessor;

import seng202.team5.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * RouteAccessor
 *
 * Contains the functions save, update, delete, getData, and dataExists for routes that directly interact with the database.
 * Implements the Accessor interface.
 *
 * @author Inga Tokarenko
 * @author Billie Johnson
 */
public class RouteAccessor implements Accessor {

    private Connection dbHandler;

    /**
     * Constructor for RouteAccessor.
     * Gets the connection to the database.
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
     */
    public RouteAccessor() {
        dbHandler = DBConnection.getConnection();
    }

    /**
     * Creates a route in the database with the given data.
     * Requires the airline IATA or ICAO code, airline_id, source airport IATA or ICAO code, source airport_id,
     * destination airport IATA or ICAO code, destination airport_id, codeshare, number of stops, and equipment.
     *
     * @param data An ArrayList containing the data to be inserted into an entry in the database.
     * @return int result The route_id of the route that was just created.
     *
     * @author Inga Tokarenko
     */
    public int save(ArrayList data) {
        int result;

        try {
            // The SQL insert statement
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO ROUTE_DATA(airline, airline_id, source_airport, source_airport_id, "
                                            + "destination_airport, destination_airport_id, codeshare, stops, equipment) "
                                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            // Iterates through the ArrayList and adds the values to the insert statement
            for (int i=1; i < 10; i++) {
                stmt.setObject(i, data.get(i-1));
            }
            // Executes the insert operation, sets the result to the route_id of the new route
            result = stmt.executeUpdate();
        } catch (SQLException e) {
            // If any of the above fails, sets result to the error code -1 and prints an error message
            result = -1;
            System.out.println("Failed to save new route data");
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * Updates a given route with new data.
     * Not every field must be updated.
     *
     * @param id The route_id of the given route you want to update.
     * @param new_airline The new 2-letter IATA or 3-letter ICAO code of the airline, may be null if not to be updated.
     * @param new_airline_id The airline_id of the airline with the given IATA or ICAO code.
     * @param new_source_airport The new 3-letter IATA or 4-letter ICAO code of the source airport, may be null if not to be updated.
     * @param new_source_airport_id The airport_id of the source airline with the given IATA or ICAO code.
     * @param new_dest_airport The new 3-letter IATA or 4-letter ICAO code of the destination airport, may be null if not to be updated.
     * @param new_dest_airport_id The airport_id of the destination airline with the given IATA or ICAO code.
     * @param new_codeshare The new codeshare of the route, "Y" or "N", may be null if not to be updated.
     * @param new_stops The new number of stops for the route, an integer, may be -1 if not to be updated.
     * @param new_equipment The new equipment for the route, may be null if not to be updated.
     * @return int result The route_id of the route that was just updated.
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
     */
    public int update(int id, String new_airline, int new_airline_id, String new_source_airport, int new_source_airport_id,
                      String new_dest_airport, int new_dest_airport_id, String new_codeshare, int new_stops, String new_equipment) {
        int result;
        ArrayList<Object> elements = new ArrayList<>();
        String search = "UPDATE ROUTE_DATA SET ";

        // Checks one by one if any of the parameters are null
        // If the parameter isn't null, then it is added to the query and the value is added to an ArrayList
        try {
            if (new_airline != null) {
                search = search + "airline = ?, airline_id = ?, ";
                elements.add(new_airline);
                elements.add(new_airline_id);
            }
            if (new_source_airport != null) {
                search = search + "source_airport = ?, source_airport_id = ?, ";
                elements.add(new_source_airport);
                elements.add(new_source_airport_id);
            }
            if (new_dest_airport != null) {
                search = search + "destination_airport = ?, destination_airport_id = ?, ";
                elements.add(new_dest_airport);
                elements.add(new_dest_airport_id);
            }
            if (new_codeshare != null) {
                search = search + "codeshare = ?, ";
                elements.add(new_codeshare);
            }
            if (new_stops != -1) {
                search = search + "stops = ?, ";
                elements.add(new_stops);
            }
            if (new_equipment != null) {
                search = search + "equipment = ? ";
                elements.add(new_equipment);
            }

            // Checks if there are any elements in the ArrayList
            // If there are not, the result is set to an error code of -2
            if (elements.size() == 0) {
                result = -2;
            } else {
                // Checks if the query ends with a comma (happens if equipment is not to be updated)
                // If it does, removes it
                // Adds the WHERE clause to the query, which is route_id
                if (search.endsWith(", ")) {
                    search = search.substring(0, search.length() - 2) + " WHERE route_id = ?";
                } else {
                    search = search + "WHERE route_id = ?";
                }
                elements.add(id);

                PreparedStatement stmt = dbHandler.prepareStatement(search);
                // Iterates through the ArrayList and adds each value to the query
                int index = 1;
                for (Object element: elements) {
                    stmt.setObject(index, element);
                    index++;
                }
                // Executes the update and sets result to the airport_id of the airport just updated
                result = stmt.executeUpdate();
            }
        } catch (Exception e) {
            // If any of the above fails, sets result to the error code -1 and prints out an error message
            result = -1;
            System.out.println("Unable to update route data with id " + id);
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * Deletes a given route from the database.
     *
     * @param id The route_id of the route to be deleted.
     * @return boolean result True if the delete operation is successful, False otherwise.
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
     */
    public boolean delete(int id) {
        boolean result = false;

        try {
            // The SQL delete statement
            PreparedStatement stmt = dbHandler.prepareStatement("DELETE FROM ROUTE_DATA WHERE route_id = ?");
            stmt.setInt(1, id); // Adds the route_id to the delete statement
            // Executes the delete operation, returns True if successful
            result = stmt.execute();
        } catch (Exception e) {
            // If any of the above fails, prints out an error message
            System.out.println("Unable to delete route data with id " + id);
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     *
     *
     * @param id
     * @return ResultSet result
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
     */
    public ResultSet getData(int id) {
        ResultSet result = null;
        try {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT * FROM ROUTE_DATA WHERE route_id = ?");
            stmt.setObject(1, id);

            result = stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Failed to retrieve route with id " + id);
            System.out.println(e.getMessage());
        }

        return result;
    }

<<<<<<< src/main/java/seng202/team5/accessor/RouteAccessor.java
    /**
     *
     *
     * @param source_airpoty The 3-letter IATA or 3-letter ICAO code of the destination airport.
     * @param dest_airport The 3-letter IATA or 3-letter ICAO code of the destination airport.
     * @param stops The number of stops on this route, 0 if it is direct. An integer.
     * @param equipment 3-letter codes for plane type(s) typically used on this flight, separated by spaces.
     * @return ResultSet result
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
     */
    public ResultSet getData(String source_airport, String dest_airport, int stops, String equipment) {
        ResultSet result = null;
        String query = "SELECT * FROM ROUTE_DATA";
        ArrayList<Object> elements = new ArrayList<>();

        try {
            if (source_airport != null) {
                query = query + " WHERE source_airport = ?";
                elements.add(source_airport);
            }
            if (dest_airport != null) {
                if (source_airport != null) {
                    query = query + " and destination_airport = ?";
                } else {
                    query = query + " WHERE destination_airport = ?";
                }
                elements.add(dest_airport);
            }
            if (stops != -1) {
                if (source_airport != null || dest_airport != null) {
                    query = query + " and stops = ?";
                } else {
                    query = query + " WHERE stops = ?";
                }
                elements.add(stops);
            }
            if (equipment != null) {
                if (source_airport != null || dest_airport != null || stops != -1) {
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
            System.out.println("Failed to retrieve route data");
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * Checks if a route with a given route_id exists.
     *
     * @param id An integer route_id.
     * @return boolean result True if a route exists with the given route_id, False otherwise.
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
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
            result = (int) data == 0 ? false : true;
        } catch (Exception e) {
            // If any of the above fails, prints out an error message
            System.out.println("Unable to retrieve route data with id " + id);
            System.out.println(e.getMessage());
        }

        return result;
    }
}
