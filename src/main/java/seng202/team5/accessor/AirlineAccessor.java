package seng202.team5.accessor;

import seng202.team5.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

/**
 * AirlineAccessor
 *
 * Contains the functions save, update, delete, getData, and dataExists for airlines that directly interact with the database.
 * Implements the Accessor interface.
 */
public class AirlineAccessor implements Accessor {

    private final Connection dbHandler;

    static Hashtable<String, Integer> cachedIds = new Hashtable<>();

    /**
     * Constructor for AirlineAccessor.
     * Gets the connection to the database.
     */
    public AirlineAccessor() {
        dbHandler = DBConnection.getConnection();
    }

    /**
     * Creates an airline in the database with the given data.
     * Requires airline_name, alias, IATA code, ICAO code, callsign, country, and active parameters, contained in an ArrayList.
     *
     * @param data An List containing the data to be inserted into an entry in the database.
     * @return int result The airline_id of the airline that was just created, -1 if SQL exception occurs.
     */
    public int save(List<Object> data) {
        int result;

        try {
            // The SQL insert statement
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, callsign, country, active) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?)");
            // Iterates through the List and adds the values to the insert statement
            for (int i=1; i < 8; i++) {
                stmt.setObject(i, data.get(i-1));
            }
            // Executes the insert operation, sets the result to the airline_id of the new airline
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            result = keys.getInt(1);
        } catch (SQLException e) {
            // If any of the above fails, sets result to the error code -1 and prints an error message
            result = -1;
            System.err.println("Failed to save new airline data");
            System.err.println(e.getMessage());
        }

        return result;
    }

    /**
     * Updates a given airline with new data.
     * Not every field must be updated.
     *
     * @param id The airline_id of the given airline you want to update.
     * @param newName The new name of the airline, may be null if not to be updated.
     * @param newAlias The new alias of the airline, may be null if not to be updated.
     * @param newIATA The new 2-letter IATA code of the airline, may be null if not to be updated.
     * @param newICAO The new 3-letter ICAO code of the airline, may be null if not to be updated.
     * @param newCallsign The new callsign of the airline, may be null if not to be updated.
     * @param newCountry The new country of the airline, may be null if not to be updated.
     * @param newActive The new active of the airline, "Y" or "N", may be null if not to be updated.
     * @return int result The number of rows edited or -1 for error.
     *
     * @throws SQLException Caused by ResultSet interactions.
     */
    public int update(int id, String newName, String newAlias, String newIATA, String newICAO,
                      String newCallsign, String newCountry, String newActive) throws SQLException {
        int result = -1;

        List<Object> element = Arrays.asList(newName, newAlias, newIATA, newICAO, newCallsign, newCountry, newActive);
        ArrayList<Object> elements = new ArrayList<>(element);
        try {
            // The SQL update statement
            String search = "UPDATE AIRLINE_DATA SET airline_name = ?, alias = ?, iata = ?, icao = ?, callsign = ?, " +
                    "country = ?, active = ? WHERE airline_id = ?";
            PreparedStatement stmt = dbHandler.prepareStatement(search);
            // Adds the parameters to the SQL statement
            for (int i = 0; i < elements.size(); i++) {
                stmt.setObject(i+1, elements.get(i));
            }
            stmt.setObject(elements.size()+1, id);
            // Executes the update operation
            result = stmt.executeUpdate();
        } catch (SQLException e) {
            // If any of the above fails, prints out an error message
            System.err.println("Unable to update airline data with id " + id);
            System.err.println(e.getMessage());
        }

        return result;
    }

    /**
     * Deletes a given airline from the database.
     *
     * @param id The airline_id of the airline to be deleted.
     * @return boolean result True if the delete operation is successful, False otherwise.
     */
    public boolean delete(int id) {
        boolean result = false;

        try {
            // The SQL delete statement
            PreparedStatement stmt = dbHandler.prepareStatement("DELETE FROM AIRLINE_DATA WHERE airline_id = ?");
            stmt.setInt(1, id); // Adds the airline_id to the delete statement
            // Executes the delete operation, returns True if successful
            result = stmt.executeUpdate() != 0;
        } catch (SQLException e) {
            // If any of the above fails, prints out an error message
            System.err.println("Unable to delete airline data with id " + id);
            System.err.println(e.getMessage());
        }

        return result;
    }

    /**
     * Retrieves the airline with the provided id.
     *
     * @param id int id of a airline.
     * @return ResultSet of the route data, null if SQL exception occurs.
     */
    public ResultSet getData(int id) {
        ResultSet result = null;

        try {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT * FROM AIRLINE_DATA WHERE airline_id = ?");
            stmt.setObject(1, id);

            result = stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Failed to retrieve airline with id " + id);
            System.err.println(e.getMessage());
        }

        return result;
    }

    /**
     * Returns all airlines with the given code.
     *
     * @param code IATA or ICAO code of airline.
     * @return ResultSet containing relevant airline data, null if SQL exception occurs.
     */
    public ResultSet getData(String code) {
        ResultSet result = null;
        String query;

        try {
            if (code.length() == 2) {
                query = "SELECT * FROM AIRLINE_DATA WHERE iata = ?";
            } else {
                query = "SELECT * FROM AIRLINE_DATA WHERE icao = ?";
            }
            PreparedStatement stmt = dbHandler.prepareStatement(query);
            stmt.setObject(1, code);

            result = stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Failed to retrieve airline data");
            System.err.println(e.getMessage());
        }

        return result;
    }

    /**
     * Retrieves all the airlines with provided data.
     *
     * @param name String name of an ariline, can be null.
     * @param country String country of an airline, can be null.
     * @param callsign String callsign of an airline, can be null.
     * @return ResultSet of all the airline, null if SQL exception occurs.
     */
    public ResultSet getData(String name, String country, String callsign) {
        ResultSet result = null;

        String query = "SELECT * FROM AIRLINE_DATA";
        List<String> elements = new ArrayList<>();

        try {
            if (name != null) {
                query = query + " WHERE airline_name = ? ";
                elements.add(name);
            }
            if (country != null) {
                if (name != null) {
                    query = query + " and country = ? ";
                } else {
                    query = query + " WHERE country = ? ";
                }
                elements.add(country);
            }
            if (callsign != null) {
                if (name != null || country != null) {
                    query = query + " and callsign = ? ";
                } else {
                    query = query + " WHERE callsign = ? ";
                }
                elements.add(callsign);
            }

            PreparedStatement stmt = dbHandler.prepareStatement(query);
            int index = 1;
            for (String element: elements) {
                stmt.setObject(index, element);
                index++;
            }

            result = stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Failed to retrieve airline data");
            System.err.println(e.getMessage());
        }

        return result;
    }

    /**
     * Helper function that finds the related airline
     * id with the provided code.
     *
     * @param code String either IATA or ICAO.
     * @return int airline id.
     */
    public int lookupCode(String code) {
        int result;
        if (cachedIds.containsKey(code)) {
            return cachedIds.get(code);
        } else {
            result = getAirlineId(code);
            cachedIds.put(code, result);
            return result;
        }
    }

    /**
     * Gets the airline_id of an airline with a given IATA or ICAO code if one exists.
     *
     * @param code A 2-letter IATA or 3-letter ICAO code.
     * @return int result The airline_id of the airline with the given IATA or ICAO code if one exists, -1 if SQL exception occurs.
     */
    public int getAirlineId(String code) {
        int result;
        String query;

        try {
            if (code.length() == 2) {
                query = "SELECT airline_id FROM AIRLINE_DATA WHERE iata = ?";
            } else {
                query = "SELECT airline_id FROM AIRLINE_DATA WHERE icao = ?";
            }
            // The SQL search query - finds the airline_id of an airline with the given IATA or ICAO code if one exists
            PreparedStatement stmt = dbHandler.prepareStatement(query);
            // Adds the given code to the search query
            stmt.setObject(1, code);
            // Executes the search query, sets result to the first entry in the ResultSet (there will at most be one entry)
            result = stmt.executeQuery().getInt(1);
        } catch (SQLException e) {
            // If any of the above fails, sets result to the error code -1 and prints an error message
            result = -1;
            System.err.println("Unable to retrieve airline data with IATA or ICAO code " + code);
            System.err.println(e.getMessage());
        }

        return result;
    }


    /**
     * Checks if an airline with a given airline_id exists.
     *
     * @param id An integer airline_id.
     * @return boolean result True if an airline exists with the given airline_id, False otherwise.
     */
    public boolean dataExists(int id) {
        boolean result = false;

        try {
            // The SQL search query - finds the number of airlines with a given airline_id (can only be 0 or 1 as airline_id is unique)
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT COUNT(airline_id) FROM AIRLINE_DATA WHERE airline_id = ?");
            // Adds the given airline_id into the search query
            stmt.setObject(1, id);

            Object data = stmt.executeQuery().getObject(1);
            result = (int) data != 0;
        } catch (Exception e) {
            // If any of the above fails, prints out an error message
            System.err.println("Unable to retrieve airline data with id " + id);
            System.err.println(e.getMessage());
        }
        return result;
    }

    /**
     * Checks if an airline with a given IATA or ICAO code exists.
     *
     * @param code A 2-letter IATA or 3-letter ICAO code.
     * @return boolean result True if an airline with the given code exists, False otherwise.
     */
    public boolean dataExists(String code) {
        boolean result = false;

        try {
            // The SQL search query - finds the number of airlines with a given IATA or ICAO code
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT COUNT(airline_id) FROM AIRLINE_DATA WHERE iata = ? OR icao = ?");
            // Adds the given code into the search query
            stmt.setObject(1, code);
            stmt.setObject(2, code);

            Object data = stmt.executeQuery().getObject(1);
            result = (int) data != 0;
        } catch (Exception e) {
            // If any of the above fails, prints out an error message
            System.err.println("Unable to retrieve airline data with IATA or ICAO code " + code);
            System.err.println(e.getMessage());
        }
        return result;
    }

    /**
     * Gets the maximum airline_id contained in the database.
     *
     * @return int id The maximum airline_id in the database, 0 if SQL exception occurs.
     */
    public int getMaxID() {
        int id = 0;

        try {
            // The SQL search query - finds the maximum airline_id in the database
            PreparedStatement stmt = dbHandler.prepareStatement("SELECT MAX(airline_id) FROM AIRLINE_DATA");
            // Executes the search query, sets result to the first entry in the ResultSet (there will at most be one entry)
            ResultSet result = stmt.executeQuery();
            id = result.getInt(1);

        } catch (SQLException e) {
            // If any of the above fails, prints an error message
            System.err.println("Unable to get maximum airline id.");
            System.err.println(e.getMessage());
        }

        return id;
    }


    /**
     * Given an arraylist of airline codes (IATA or ICAO), returns a result set containing
     * IATA, ICAO and airline Name for the airline codes that were provided.
     *
     * @param airlineCodes ArrayList String - airlineCodes (IATA or ICAO).
     * @return ResultSet - Containting ICAO, IATA and airlineName info, null if SQL exception occurs.
     *
     * @throws SQLException Cause by ResultSet interactions.
     */
    public ResultSet getAirlineNames(ArrayList<String> airlineCodes) throws SQLException {
        String query = "SELECT iata, icao, airline_name FROM airline_data ";
        ResultSet result = null;

        try {
            if (airlineCodes.size() > 0) {
                query = query + "WHERE";
                String iataString = " iata IN (";
                String icaoString = " or icao IN (";

                for (int i = 0; i < airlineCodes.size(); i++) {
                    iataString = iataString + "\"" + airlineCodes.get(i) + "\"";
                    icaoString = icaoString + "\"" + airlineCodes.get(i) + "\"";
                    if (i != airlineCodes.size() - 1) {
                        iataString = iataString + ", ";
                        icaoString = icaoString + ", ";
                    }
                }
                iataString = iataString + ")";
                icaoString = icaoString + ")";
                query = query + iataString + icaoString;
                PreparedStatement stmt = dbHandler.prepareStatement(query);

                result = stmt.executeQuery();
            }
        } catch (SQLException e) {
            // If any of the above fails, prints an error message
            System.err.println("Unable to get airline names with code.");
            System.err.println(e.getMessage());
        }

        return result;
    }
}
