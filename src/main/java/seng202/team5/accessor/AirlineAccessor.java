package seng202.team5.accessor;

import seng202.team5.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * AirlineAccessor
 *
 * Contains the functions save, update, delete, getData, and dataExists for airlines that directly interact with the database.
 * Implements the Accessor interface.
 *
 * @author Inga Tokarenko
 * @author Billie Johnson
 */
public class AirlineAccessor implements Accessor {

    private Connection dbHandler;

    /**
     * Constructor for AirlineAccessor.
     * Gets the connection to the database.
     *
     * @author Inga Tokarenko
     * @author Billie Johnson 
     */
    public AirlineAccessor() {
        dbHandler = DBConnection.getConnection();
    }

    /**
     * Creates an airline in the database with the given data.
     * Requires airline_name, alias, IATA code, ICAO code, callsign, country, and active parameters, contained in an ArrayList.
     *
     * @param data An List containing the data to be inserted into an entry in the database.
     * @return int result The airline_id of the airline that was just created.
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
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
            System.out.println("Failed to save new airline data");
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * Updates a given airline with new data.
     * Not every field must be updated.
     *
     * @param id The airline_id of the given airline you want to update.
     * @param new_name The new name of the airline, may be null if not to be updated.
     * @param new_alias The new alias of the airline, may be null if not to be updated.
     * @param new_iata The new 2-letter IATA code of the airline, may be null if not to be updated.
     * @param new_icao The new 3-letter ICAO code of the airline, may be null if not to be updated.
     * @param new_callsign The new callsign of the airline, may be null if not to be updated.
     * @param new_country The new country of the airline, may be null if not to be updated.
     * @param new_active The new active of the airline, "Y" or "N", may be null if not to be updated.
     * @return int result The number of rows edited or -1 for error.
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
     */
    public int update(int id, String new_name, String new_alias, String new_iata, String new_icao,
                      String new_callsign, String new_country, String new_active) {
        int result;
        ArrayList<Object> elements = new ArrayList<>();
        String search = "UPDATE AIRLINE_DATA SET "; // The start of the SQL update statement

        // Checks one by one if any of the parameters are null
        // If the parameter isn't null, then it is added to the query and the value is added to an ArrayList
        try {
            if (new_name != null) {
                search = search + "airline_name = ?, ";
                elements.add(new_name);
            }
            if (new_alias != null) {
                search = search + "alias = ?, ";
                elements.add(new_alias);
            }
            if (new_iata != null) {
                search = search + "iata = ?, ";
                elements.add(new_iata);
            }
            if (new_icao != null) {
                search = search + "icao = ?, ";
                elements.add(new_icao);
            }
            if (new_callsign != null) {
                search = search + "callsign = ?, ";
                elements.add(new_callsign);
            }
            if (new_country != null) {
                search = search + "country = ?, ";
                elements.add(new_country);
            }
            if (new_active != null) {
                search = search + "active = ? ";
                elements.add(new_active);
            }

            // Checks if there are any elements in the ArrayList
            // If there are not, the result is set to an error code of -2
            if (elements.size() == 0) {
                result = -2;
            } else {
                // Checks if the query ends with a comma (happens if active is not to be updated)
                // If it does, removes it
                // Adds the WHERE clause to the query, which is airline_id
                if (search.endsWith(", ")) {
                    search = search.substring(0, search.length() - 2) + " WHERE airline_id = ?";
                } else {
                    search = search + "WHERE airline_id = ?";
                }
                elements.add(id);

                PreparedStatement stmt = dbHandler.prepareStatement(search);
                // Iterates through the ArrayList and adds each value to the query
                int index = 1;
                for (Object element: elements) {
                    stmt.setObject(index, element);
                    index++;
                }
                // Executes the update and sets result to the number of rows that were modified
                result = stmt.executeUpdate();
            }
        } catch (Exception e) {
            // If any of the above fails, sets result to the error code -1 and prints out an error message
            result = -1;
            System.out.println("Unable to update airline data with id " + id);
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * Deletes a given airline from the database.
     *
     * @param id The airline_id of the airline to be deleted.
     * @return boolean result True if the delete operation is successful, False otherwise.
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
     */
    public boolean delete(int id) {
        boolean result = false;

        try {
            // The SQL delete statement
            PreparedStatement stmt = dbHandler.prepareStatement("DELETE FROM AIRLINE_DATA WHERE airline_id = ?");
            stmt.setInt(1, id); // Adds the airline_id to the delete statement
            // Executes the delete operation, returns True if successful
            result = stmt.executeUpdate() != 0;
        } catch (Exception e) {
            // If any of the above fails, prints out an error message
            System.out.println("Unable to delete airline data with id " + id);
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * Selects all airlines from the database and returns them.
     *
     * @return ResultSet result Contains the airlines in the database.
     *
     * @author Billie Johnson
     */
    public ResultSet getAllData() {
        ResultSet result = null;

        try {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT * FROM AIRLINE_DATA");

            result = stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Failed to retrieve airlines.");
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
                    "SELECT * FROM AIRLINE_DATA WHERE airline_id = ?");
            stmt.setObject(1, id);

            result = stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Failed to retrieve airline with id " + id);
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     *
     *
     * @param name
     * @param country
     * @param callsign
     * @return ResultSet result
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
     */
    public ResultSet getData(String name, String country, String callsign) {
        ResultSet result = null;

        List<String> queryTerms = new ArrayList<>();
        List<String> elements = new ArrayList<>();

        try {
            if (name != null) {
                queryTerms.add("airline_name = ?");
                elements.add(name);
            }
            if (country != null) {
                queryTerms.add("country = ?");
                elements.add(country);
            }
            if (callsign != null) {
                queryTerms.add("callsign = ?");
                elements.add(callsign);
            }

            String query = "SELECT * FROM AIRLINE_DATA";
            if (queryTerms.size() != 0) {
                query += " WHERE ";
                query += String.join(" and ", queryTerms);
            }

            PreparedStatement stmt = dbHandler.prepareStatement(query);
            int index = 1;
            for (String element: elements) {
                stmt.setObject(index, element);
                index++;
            }

            result = stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Failed to retrieve airline data");
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * Gets the airline_id of an airline with a given IATA or ICAO code if one exists.
     *
     * @param code A 2-letter IATA or 3-letter ICAO code.
     * @return int result The airline_id of the airline with the given IATA or ICAO code if one exists.
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
     */
    public int getAirlineId(String code) {
        int result;

        try {
            // The SQL search query - finds the airline_id of an airline with the given IATA or ICAO code if one exists
            PreparedStatement stmt = dbHandler.prepareStatement("SELECT airline_id FROM AIRLINE_DATA WHERE iata = ? OR icao = ?");
            // Adds the given code to the search query
            stmt.setObject(1, code);
            stmt.setObject(2, code);
            // Executes the search query, sets result to the first entry in the ResultSet (there will at most be one entry)
            result = stmt.executeQuery().getInt(1);
        } catch (SQLException e) {
            // If any of the above fails, sets result to the error code -1 and prints an error message
            result = -1;
            System.out.println("Unable to retrieve airline data with IATA or ICAO code " + code);
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * Gets the airline iata and icao of an airline with a given name.
     *
     * @param name The name of the airline.
     * @return iata result The iata of the airline with the name.
     *
     * @author Inga Tokarenko
     * @author Billie Johnson
     */
    public ArrayList getAirlineIataIcao(String name) {
        ArrayList<String> result = new ArrayList<>();

        try {
            // The SQL search query - finds the iata and icao of an airline with the given name
            PreparedStatement stmt = dbHandler.prepareStatement("SELECT iata, icao FROM AIRLINE_DATA WHERE airline_name = ?");
            // Adds the given code to the search query
            stmt.setObject(1, name);
            // Executes the search query, sets result to the first entry in the ResultSet (there will at most be one entry)
            ResultSet data = stmt.executeQuery();

            while (data.next()) {
                String iata = data.getString("iata");
                String icao = data.getString("icao");

                result.add(iata);
                result.add(icao);
            }
        } catch (SQLException e) {
            // If any of the above fails, sets result to the error code -1 and prints an error message
            result = null;
            System.out.println("Unable to retrieve airline data with name " + name);
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * Checks if an airline with a given airline_id exists.
     *
     * @param id An integer airline_id.
     * @return boolean result True if an airline exists with the given airline_id, False otherwise.
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
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
            result = (int) data == 0 ? false : true;
        } catch (Exception e) {
            // If any of the above fails, prints out an error message
            System.out.println("Unable to retrieve airline data with id " + id);
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * Checks if an airline with a given IATA or ICAO code exists.
     *
     * @param code A 2-letter IATA or 3-letter ICAO code.
     * @return boolean result True if an airline with the given code exists, False otherwise.
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
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
            result = (int) data == 0 ? false : true;
        } catch (Exception e) {
            // If any of the above fails, prints out an error message
            System.out.println("Unable to retrieve airline data with IATA or ICAO code " + code);
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * Gets the maximum airline_id contained in the database.
     *
     * @return int id The maximum airline_id in the database.
     *
     * @author Billie Johnson
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
            System.out.println("Unable to get maximum airline id.");
            System.out.println(e.getMessage());
        }

        return id;
    }
}
