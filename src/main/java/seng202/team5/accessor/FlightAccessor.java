package seng202.team5.accessor;

import seng202.team5.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * FlightAccessor
 *
 * Contains the functions save, update, delete, getData, and dataExists for flights that directly interact with the database.
 * Implements the Accessor interface.
 *
 * @author Inga Tokarenko
 * @author Billie Johnson
 */
public class FlightAccessor implements Accessor{

    private Connection dbHandler;

    /**
     * Constructor for FlightAccessor.
     * Gets the connection to the database.
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
     */
    public FlightAccessor() {
        dbHandler = DBConnection.getConnection();
    }

    /**
     * Creates a flight entry in the database with the given data.
     * Requires the flight_id, airline IATA or ICAO code, airport IATA or ICAO code, altitude, latitude, and longitude.
     *
     * @param data An ArrayList containing the data to be inserted into an entry in the database.
     * @return int result The unique id of the flight entry that was just created.
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
     */
    public int save(ArrayList data) {
        int result;

        try {
            // The SQL insert statement
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO FLIGHT_DATA(flight_id, airline, airport, altitude, latitude, longitude) "
                                                + "VALUES (?, ?, ?, ?, ?, ?)");
            // Iterates through the ArrayList and adds the values to the insert statement
            for (int i=1; i < 7; i++) {
                stmt.setObject(i, data.get(i-1));
            }
            // Executes the insert operation, sets the result to the unique id of the new flight entry
            result = stmt.executeUpdate();
        } catch (SQLException e) {
            // If any of the above fails, sets result to the error code -1 and prints an error message
            result = -1;
            System.out.println("Failed to save new flight data.");
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * Updates a given flight entry with new data.
     * Not every field must be updated.
     *
     * @param id The unique id of the given flight entry you want to update.
     * @param new_airline The new 2-letter airline IATA or 3-letter airline ICAO code of the flight entry, may be null if not to be updated.
     * @param new_airport The new 3-letter airport IATA or 4-letter airport ICAO code of the flight entry, may be null if not to be updated.
     * @param new_altitude The new altitude of the flight entry in feet, an integer. May be null if not to be updated.
     * @param new_latitude The new latitude of the flight entry, a double. Negative is South and positive is North. May be null if not to be updated.
     * @param new_longitude The new longitude of the flight entry, a double. Negative is West and positive is East. May be null if not to be updated.
     * @return int result The unique id of the flight entry that was just updated.
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
     */
    public int update(int id, String new_airline, String new_airport, int new_altitude,
                      double new_latitude, double new_longitude) {
        int result;
        int flight_id;
        ArrayList<Object> elements = new ArrayList<>();
        String search = "UPDATE FLIGHT_DATA SET "; // The start of the SQL update statement

        try {
            // Retrieves the flight_id of the flight entry to be updated
            PreparedStatement flight_stmt = dbHandler.prepareStatement("SELECT flight_id FROM FLIGHT_DATA WHERE id = ?");
            flight_stmt.setObject(1, id);
            ResultSet flights = flight_stmt.executeQuery();
            flight_id = flights.getInt(1);

            // Checks one by one if any of the parameters are null
            // If the parameter isn't null, then it is added to the query and the value is added to an ArrayList
            try {
                if (new_airline != null) {
                    search = search + "airline = ?, ";
                    elements.add(new_airline);
                }
                if (new_airport != null) {
                    search = search + "airport = ?, ";
                    elements.add(new_airport);
                }
                if (new_altitude != -1) {
                    search = search + "altitude = ?, ";
                    elements.add(new_altitude);
                }
                if (new_latitude != -1) {
                    search = search + "latitude = ?, ";
                    elements.add(new_latitude);
                }
                if (new_longitude != -1) {
                    search = search + "longitude = ? ";
                    elements.add(new_longitude);
                }

                // Checks if there are any elements in the ArrayList
                // If there are not, the result is set to an error code of -2
                if (elements.size() == 0) {
                    result = -2;
                } else {
                    // Checks if the query ends with a comma (happens if longitude is not to be updated)
                    // If it does, removes it
                    // Adds the WHERE clause to the query, which is the unique id of the flight entry
                    if (search.endsWith(", ")) {
                        search = search.substring(0, search.length() - 2) + " WHERE id = ?";
                    } else {
                        search = search + "WHERE id = ?";
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
                String str = "Unable to update flight data with id " + id + " and flight id " + flight_id;
                System.out.println(str);
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            // If any of the above fails, sets result to the error code -1 and prints out an error message
            result = -1;
            System.out.println("Unable to get flight id of data with id " + id);
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * Deletes a given flight from the database.
     *
     * @param id The flight_id of the airline to be deleted.
     * @return boolean result True if the delete operation is successful, False otherwise.
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
     */
    public boolean deleteFlight(int id) {
        boolean result = false;

        try {
            // The SQL delete statement
            PreparedStatement stmt = dbHandler.prepareStatement("DELETE FROM FLIGHT_DATA WHERE flight_id = ?");
            stmt.setInt(1, id); // Adds the flight_id to the delete statement
            // Executes the delete operation, returns True if successful
            result = stmt.execute();
        } catch (Exception e) {
            // If any of the above fails, prints out an error message
            System.out.println("Unable to delete flight data with flight id " + id);
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * Deletes a given flight entry from the database.
     *
     * @param id The unique id of the flight entry to be deleted.
     * @return boolean result True if the delete operation is successful, False otherwise.
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
     */
    public boolean delete(int id) {
        boolean result = false;

        try {
            // The SQL delete statement
            PreparedStatement stmt = dbHandler.prepareStatement("DELETE FROM FLIGHT_DATA WHERE id = ?");
            stmt.setInt(1, id); // Adds the unique id of the flight entry to the delete statement
            // Executes the delete operation, returns True if successful
            result = stmt.execute();
        } catch (Exception e) {
            // If any of the above fails, prints out an error message
            System.out.println("Unable to delete flight data with id " + id);
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
                    "SELECT * FROM FLIGHT_DATA WHERE flight_id = ?");
            stmt.setObject(1, id);

            result = stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Failed to retrieve flight with id " + id);
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     *
     *
     * @param airline
     * @param airport
     * @return ResultSet result
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
     */
    public ResultSet getData(ArrayList<String> airline, ArrayList<String> airport) {
        boolean check = true;
        String addString = "";
        ResultSet result = null;
        String query = "SELECT * FROM FLIGHT_DATA";
        ArrayList<String> elements = new ArrayList<>();

        try {
            if (airline != null) {

                query = query + " WHERE ";

                for (String value:airline) {
                    if (value != null) {
                        addString = elements.size() == 0 ? " airline = ? " : " or airline = ? ";
                        query = query + addString;
                        elements.add(value);
                    }
                }
            }
            if (airport != null) {
                if (airline != null) {
                    query = query + " and ";
                } else {
                    query = query + " WHERE ";
                }

                for (String value:airport) {
                    if (value != null) {
                        addString = check ? " airport = ? " : " or airport = ? ";
                        query = query + addString;
                        elements.add(value);
                        check = false;
                    }
                }
            }

            PreparedStatement stmt = dbHandler.prepareStatement(query);
            int index = 1;
            for (String element: elements) {
                stmt.setObject(index, element);
                index++;
            }

            result = stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Failed to retrieve flight data");
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * Checks if any flight entries with a given flight_id exist.
     *
     * @param id An integer flight_id.
     * @return boolean result True if any flight entries exist with the given flight_id, False otherwise.
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
     */
    public boolean flightExists(int id) {
        boolean result = false;

        try {
            // The SQL search query - finds the number of flight entries with a given flight_id
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT COUNT(id) FROM FLIGHT_DATA WHERE flight_id = ?");
            // Adds the given flight_id into the search query
            stmt.setInt(1, id);

            Object data = stmt.executeQuery().getObject(1);
            result = (int) data == 0 ? false : true;
        } catch (Exception e) {
            // If any of the above fails, prints out an error message
            System.out.println("Unable to retrieve flight data with flight id " + id);
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * Checks if a flight entry with a given id exists.
     *
     * @param id An integer, a unique id of a flight entry.
     * @return boolean result True if a flight entry exists with the given id, False otherwise.
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
     */
    public boolean dataExists(int id) {
        boolean result = false;

        try {
            // The SQL search query - finds the number of flight entries with a given id (can only be 0 or 1 as the id is unique)
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT COUNT(id) FROM FLIGHT_DATA WHERE id = ?");
            // Adds the given unique id into the search query
            stmt.setInt(1, id);

            Object data = stmt.executeQuery().getObject(1);
            result = (int) data == 0 ? false : true;
        } catch (Exception e) {
            // If any of the above fails, prints out an error message
            System.out.println("Unable to retrieve flight data with id " + id);
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * Gets the maximum flight_id contained in the database.
     *
     * @return int id The maximum flight_id in the database.
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
     */
    public int getMaxID() {
        int id = 0;

        try {
            // The SQL search query - finds the maximum flight_id in the database
            PreparedStatement stmt = dbHandler.prepareStatement("SELECT MAX(flight_id) FROM FLIGHT_DATA");
            // Executes the search query, sets result to the first entry in the ResultSet (there will at most be one entry)
            ResultSet result = stmt.executeQuery();
            id = result.getInt(1);

        } catch (SQLException e) {
            // If any of the above fails, prints an error message
            System.out.println("Unable to get maximum flight id.");
            System.out.println(e.getMessage());
        }

        return id;
    }
}
