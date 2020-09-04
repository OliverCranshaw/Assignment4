package seng202.team5.accessor;

import seng202.team5.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * AirportAccessor
 *
 * Contains the functions save, update, delete, getData, and dataExists for airports that directly interact with the database.
 * Implements the Accessor interface.
 *
 * @author Inga Tokarenko
 * @author Billie Johnson
 */
public class AirportAccessor implements Accessor {

    private Connection dbHandler;

    /**
     * Constructor for AirportAccessor.
     * Gets the connection to the database.
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
     */
    public AirportAccessor() {
        dbHandler = DBConnection.getConnection();
    }

    /**
     * Creates an airport in the database with the given data.
     * Requires airport_name, city, country, IATA code, ICAO code, latitude, longitude, altitude,
     * timezone, dst, and tz timezone parameters, contained in an ArrayList.
     *
     * @param data An List containing the data to be inserted into an entry in the database.
     * @return int result The airport_id of the airport that was just created.
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
     */
    public int save(List<Object> data) {
        int result;

        try {
            // The SQL insert statement
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                                                + "longitude, altitude, timezone, dst, tz_database_timezone) "
                                                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            // Iterates through the List and adds the values to the insert statement
            for (int i=1; i < 12; i++) {
                stmt.setObject(i, data.get(i-1));
            }
            // Executes the insert operation, sets the result to the airport_id of the new airport
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            result = keys.getInt(1);
        } catch (SQLException e) {
            // If any of the above fails, sets result to the error code -1 and prints an error message
            result = -1;
            System.out.println("Failed to save new airport data");
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * Updates a given airport with new data.
     * Not every field must be updated.
     *
     * @param id The airport_id of the given airport you want to update.
     * @param new_name The new name of the airport, may be null if not to be updated.
     * @param new_city The new city of the airport, may be null if not to be updated.
     * @param new_country The new country of the airport, may be null if not to be updated.
     * @param new_iata The new 3-letter IATA code of the airport, may be null if not to be updated.
     * @param new_icao The new 4-letter ICAO code of the airport, may be null if not to be updated.
     * @param new_latitude The new latitude of the airport, a double. Negative is South and positive is North. May be null if not to be updated.
     * @param new_longitude The new longitude of the airport, a double. Negative is West and positive is East. May be null if not to be updated.
     * @param new_altitude The new altitude of the airport in feet, an integer. May be null if not to be updated.
     * @param new_timezone The new timezone of the airport, hours offset from UTC. A float, may be null if not to be updated.
     * @param new_dst The new dst of the airport, one of E (Europe), A (US/Canada), S (South America), O (Australia), Z (New Zealand), N (None) or U (Unknown). May be null if not to be updated.
     * @param new_tz The new tz_database_timezone of the airport, timezone in "tz" (Olson) format. May be null if not to be updated.
     * @return int result The airport_id of the airport that was just updated.
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
     */
    public int update(int id, String new_name, String new_city, String new_country, String new_iata, String new_icao,
                      double new_latitude, double new_longitude, int new_altitude, float new_timezone, String new_dst, String new_tz) {
        int result;
        ArrayList<Object> elements = new ArrayList<>();
        String search = "UPDATE AIRPORT_DATA SET "; // The start of the SQL update statement

        // Checks one by one if any of the parameters are null
        // If the parameter isn't null, then it is added to the query and the value is added to an ArrayList
        try {
            if (new_name != null) {
                search = search + "airport_name = ?, ";
                elements.add(new_name);
            }
            if (new_city != null) {
                search = search + "city = ?, ";
                elements.add(new_city);
            }
            if (new_country != null) {
                search = search + "country = ?, ";
                elements.add(new_country);
            }
            if (new_iata != null) {
                search = search + "iata = ?, ";
                elements.add(new_iata);
            }
            if (new_icao != null) {
                search = search + "icao = ?, ";
                elements.add(new_icao);
            }
            if (new_latitude != -1) {
                search = search + "latitude = ?, ";
                elements.add(new_latitude);
            }
            if (new_longitude != -1) {
                search = search + "longitude = ?, ";
                elements.add(new_longitude);
            }
            if (new_altitude != -1) {
                search = search + "altitude = ?, ";
                elements.add(new_altitude);
            }
            if (new_timezone != -1) {
                search = search + "timezone = ?, ";
                elements.add(new_timezone);
            }
            if (new_dst != null) {
                search = search + "dst = ?, ";
                elements.add(new_dst);
            }
            if (new_tz != null) {
                search = search + "tz_database_timezone = ? ";
                elements.add(new_tz);
            }

            // Checks if there are any elements in the ArrayList
            // If there are not, the result is set to an error code of -2
            if (elements.size() == 0) {
                result = -2;
            } else {
                // Checks if the query ends with a comma (happens if tz_database_timezone is not to be updated)
                // If it does, removes it
                // Adds the WHERE clause to the query, which is airport_id
                if (search.endsWith(", ")) {
                    search = search.substring(0, search.length() - 2) + " WHERE airport_id = ?";
                } else {
                    search = search + "WHERE airport_id = ?";
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
            System.out.println("Unable to update airport data with id " + id);
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * Deletes a given airport from the database.
     *
     * @param id The airport_id of the airport to be deleted.
     * @return boolean result True if the delete operation is successful, False otherwise.
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
     */
    public boolean delete(int id) {
        boolean result = false;

        try {
            // The SQL delete statement
            PreparedStatement stmt = dbHandler.prepareStatement("DELETE FROM AIRPORT_DATA WHERE airport_id = ?");
            stmt.setInt(1, id); // Adds the airport_id to the delete statement
            // Executes the delete operation, returns True if successful
            result = stmt.execute();
        } catch (Exception e) {
            // If any of the above fails, prints out an error message
            System.out.println("Unable to delete airport data with id " + id);
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
                    "SELECT * FROM AIRPORT_DATA WHERE airport_id = ?");
            stmt.setObject(1, id);

            result = stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Failed to retrieve airport data with id " + id);
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     *
     *
     * @param name
     * @param city
     * @param country
     * @return ResultSet result
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
     */
    public ResultSet getData(String name, String city, String country) {
        ResultSet result = null;

        List<String> queryTerms = new ArrayList<>();
        List<String> elements = new ArrayList<>();

        try {
            if (name != null) {
                queryTerms.add("airport_name = ?");
                elements.add(name);
            }

            if (city != null) {
                queryTerms.add("city = ?");
                elements.add(city);
            }

            if (country != null) {
                queryTerms.add("country = ?");
                elements.add(country);
            }

            String query = "SELECT * FROM AIRPORT_DATA";
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
            System.out.println("Failed to retrieve airport data");
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * Gets the airport_id of an airport with a given IATA or ICAO code if one exists.
     *
     * @param code A 2-letter IATA or 3-letter ICAO code.
     * @return int result The airport_id of the airport with the given IATA or ICAO code if one exists.
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
     */
    public int getAirportId(String code) {
        int result;

        try {
            // The SQL search query - finds the airport_id of an airport with the given IATA or ICAO code if one exists
            PreparedStatement stmt = dbHandler.prepareStatement("SELECT airport_id FROM AIRPORT_DATA WHERE iata = ? OR icao = ?");
            // Adds the given code to the search query
            stmt.setObject(1, code);
            stmt.setObject(2, code);
            // Executes the search query, sets result to the first entry in the ResultSet (there will at most be one entry)
            result = stmt.executeQuery().getInt(1);
        } catch (SQLException e) {
            // If any of the above fails, sets result to the error code -1 and prints an error message
            result = -1;
            System.out.println("Unable to retrieve airport data with IATA or ICAO code " + code);
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * Gets the airport iata and icao of an airline with a given name.
     *
     * @param name The name of the airline.
     * @return iata result The iata of the airport with the name.
     *
     * @author Inga Tokarenko
     * @author Billie Johnson
     */
    public ArrayList getAirportIataIcao(String name) {
        ArrayList<String> result = new ArrayList<>();

        try {
            // The SQL search query - finds the iata and icao of an airport with the given name
            PreparedStatement stmt = dbHandler.prepareStatement("SELECT iata, icao FROM AIRPORT_DATA WHERE airport_name = ?");
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
     * Checks if an airport with a given airport_id exists.
     *
     * @param id An integer airport_id.
     * @return boolean result True if an airport exists with the given airport_id, False otherwise.
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
     */
    public boolean dataExists(int id) {
        boolean result = false;

        try {
            // The SQL search query - finds the number of airports with a given airport_id (can only be 0 or 1 as airport_id is unique)
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT COUNT(airport_id) FROM AIRPORT_DATA WHERE airport_id = ?");
            // Adds the given airport_id into the search query
            stmt.setInt(1, id);

            Object data = stmt.executeQuery().getObject(1);
            result = (int) data == 0 ? false : true;
        } catch (Exception e) {
            // If any of the above fails, prints out an error message
            System.out.println("Unable to retrieve airport data with id " + id);
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * Checks if an airport with a given IATA or ICAO code exists.
     *
     * @param code A 3-letter IATA or 4-letter ICAO code.
     * @return boolean result True if an airport with the given code exists, False otherwise.
     *
     * @author Inga Tokarenko 
     * @author Billie Johnson
     */
    public boolean dataExists(String code) {
        boolean result = false;

        try {
            // The SQL search query - finds the number of airports with a given IATA or ICAO code
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT COUNT(airport_id) FROM AIRPORT_DATA WHERE iata = ? or icao = ?");
            // Adds the given code into the search query
            stmt.setObject(1, code);
            stmt.setObject(2, code);

            Object data = stmt.executeQuery().getObject(1);
            result = (int) data == 0 ? false : true;
        } catch (Exception e) {
            // If any of the above fails, prints out an error message
            System.out.println("Unable to retrieve airport data with IATA or ICAO code " + code);
            System.out.println(e.getMessage());
        }

        return result;
    }
}
