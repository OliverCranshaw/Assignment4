package seng202.team5.accessor;

import seng202.team5.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * FlightAccessor
 *
 * Contains the functions save, update, delete, getData, and dataExists for flights that directly interact with the database.
 * Implements the Accessor interface.
 */
public class FlightAccessor implements Accessor{

    private Connection dbHandler;

    /**
     * Constructor for FlightAccessor.
     * Gets the connection to the database.
     */
    public FlightAccessor() {
        dbHandler = DBConnection.getConnection();
    }

    /**
     * Creates a flight entry in the database with the given data.
     * Requires the flightID, location type, location code, altitude, latitude, and longitude.
     *
     * @param data An List containing the data to be inserted into an entry in the database.
     * @return int result The unique id of the flight entry that was just created.
     */
    public int save(List<Object> data) {
        int result;

        try {
            // The SQL insert statement
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO FLIGHT_DATA(flight_id, location_type, location, altitude, latitude, longitude) "
                                                + "VALUES (?, ?, ?, ?, ?, ?)");
            // Iterates through the List and adds the values to the insert statement
            for (int i=1; i < 7; i++) {
                stmt.setObject(i, data.get(i-1));
            }
            // Executes the insert operation, sets the result to the unique id of the new flight entry
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            result = keys.getInt(1);
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
     * @param newLocationType The new location type of the flight entry location, one of "APT", "VOR", or "FIX", may be null if not to be updated.
     * @param newLocation The new location of the flight entry, may be null if not to be updated.
     * @param newAltitude The new altitude of the flight entry in feet, an integer. May be null if not to be updated.
     * @param newLatitude The new latitude of the flight entry, a double. Negative is South and positive is North. May be null if not to be updated.
     * @param newLongitude The new longitude of the flight entry, a double. Negative is West and positive is East. May be null if not to be updated.
     * @return int result The number of rows modified or -1 for error.
     */
    public int update(int id, String newLocationType, String newLocation, int newAltitude,
                      double newLatitude, double newLongitude) {
        int result;
        ArrayList<Object> elements = new ArrayList<>();
        String search = "UPDATE FLIGHT_DATA SET "; // The start of the SQL update statement

        try {
            // Retrieves the flight_id of the flight entry to be updated

            // Checks one by one if any of the parameters are null
            // If the parameter isn't null, then it is added to the query and the value is added to an ArrayList
            try {
                if (newLocationType != null) {
                    search = search + "location_type = ?, ";
                    elements.add(newLocationType);
                }
                if (newLocation != null) {
                    search = search + "location = ?, ";
                    elements.add(newLocation);
                }
                if (newAltitude != -1) {
                    search = search + "altitude = ?, ";
                    elements.add(newAltitude);
                }
                if (newLatitude != -1) {
                    search = search + "latitude = ?, ";
                    elements.add(newLatitude);
                }
                if (newLongitude != -1) {
                    search = search + "longitude = ? ";
                    elements.add(newLongitude);
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
                String str = "Unable to update flight data with id " + id;
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
     */
    public boolean deleteFlight(int id) {
        boolean result = false;

        try {
            // The SQL delete statement
            PreparedStatement stmt = dbHandler.prepareStatement("DELETE FROM FLIGHT_DATA WHERE flight_id = ?");
            stmt.setInt(1, id); // Adds the flight_id to the delete statement
            // Executes the delete operation, returns True if successful
            result = stmt.executeUpdate() != 0;
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
     */
    public boolean delete(int id) {
        boolean result = false;

        try {
            // The SQL delete statement
            PreparedStatement stmt = dbHandler.prepareStatement("DELETE FROM FLIGHT_DATA WHERE id = ?");
            stmt.setInt(1, id); // Adds the unique id of the flight entry to the delete statement
            // Executes the delete operation, returns True if successful
            result = stmt.executeUpdate() != 0;
        } catch (Exception e) {
            // If any of the above fails, prints out an error message
            System.out.println("Unable to delete flight data with id " + id);
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * Retrieves the flight with the provided id.
     *
     * @param id int id of a flight.
     * @return ResultSet of the route.
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
     * Retrieves all the flights with provided data.
     *
     * @param location_type String location_type of a flight, can be null.
     * @param location String location of a flight, can be null.
     * @return ResultSet of all the flights.
     */
    public ResultSet getData(String location_type, String location) {
        ResultSet result = null;
        String query = "SELECT * FROM FLIGHT_DATA";
        ArrayList<String> elements = new ArrayList<>();

        try {
            if (location_type != null) {
                query = query + " WHERE location_type = ? ";
                elements.add(location_type);
            }
            if (location != null) {
                if (location_type != null) {
                    query = query + " and location = ?";
                } else {
                    query = query + " WHERE location = ?";
                }
                elements.add(location);
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
     */
    public int getMaxFlightID() {
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

    /**
     * Gets the maximum unique id contained in the flight data table.
     *
     * @return int id The maximum unique id in the flight data table.
     */
    public int getMaxID() {
        int id = 0;

        try {
            // The SQL search query - finds the maximum unique id in the flight data table
            PreparedStatement stmt = dbHandler.prepareStatement("SELECT MAX(id) FROM FLIGHT_DATA");
            // Executes the search query, sets result to the first entry in the ResultSet (there will at most be one entry)
            ResultSet result = stmt.executeQuery();
            id = result.getInt(1);

        } catch (SQLException e) {
            // If any of the above fails, prints an error message
            System.out.println("Unable to get maximum id.");
            System.out.println(e.getMessage());
        }

        return id;
    }

    /**
     * Checks if an identical flight entry exists
     *
     * @param id An integer, a flight id.
     * @param location_type A string, a location type.
     * @param location A string, a location.
     * @param altitude An int, the altitude of the flight entry.
     * @param latitude A double, the latitude of the flight entry.
     * @param longitude A double, the longitude of the flight entry.
     * @return boolean result True if an identical flight entry exists, False otherwise.
     */
    public boolean dataExists(int id, String locationType, String location, int altitude, double latitude, double longitude) {
        boolean result = false;

        try {
            // The SQL search query - finds the number of identical flight entries
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT COUNT(id) FROM FLIGHT_DATA WHERE flight_id = ? and location_type = ? and location = ? and altitude = ? and latitude = ? and longitude = ?");
            // Adds the given parameters into the search query
            stmt.setInt(1, id);
            stmt.setString(2, locationType);
            stmt.setString(3, location);
            stmt.setInt(4, altitude);
            stmt.setDouble(5, latitude);
            stmt.setDouble(6, longitude);

            Object data = stmt.executeQuery().getObject(1);
            result = (int) data == 0 ? false : true;
        } catch (Exception e) {
            // If any of the above fails, prints out an error message
            System.out.println("Unable to retrieve flight data with id " + id);
            System.out.println(e.getMessage());
        }

        return result;
    }
}
