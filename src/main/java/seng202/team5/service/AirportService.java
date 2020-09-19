package seng202.team5.service;

import seng202.team5.accessor.AirportAccessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * AirportService
 *
 * Contains the functions save, update, delete, getData, and validity check functions for airports that interact with Accessors.
 * Implements the Service interface.
 */
public class AirportService implements Service {

    private final AirportAccessor accessor;

    /**
     * Constructor for AirportService.
     * Creates an AirportAccessor and sets the valid DSTs.
     */
    public AirportService() {
        accessor = new AirportAccessor();
    }

    /**
     * Checks the validity of input parameters and then passes them into the save method of the AirportAccessor as an ArrayList.
     *
     * @param name The name of the airport, cannot be null.
     * @param city The city of the airport, cannot be null.
     * @param country The country of the airport, cannot be null.
     * @param iata The 3-letter IATA code of the airport, must be unique, may be null if not known/assigned.
     * @param icao The 4-letter ICAO code of the airport, must be unique, may be null if not known/assigned.
     * @param latitude The latitude of the airport, a double. Negative is South, positive is North, cannot be null.
     * @param longitude The longitude of the airport, a double. Negative is West, positive is East, cannot be null.
     * @param altitude The altitude of the airport in feet, an integer. Cannot be null.
     * @param timezone The hours offset from UTC, a float, cannot be null.
     * @param dst The daylight savings time of the airport, one of E (Europe), A (US/Canada), S (South America), O (Australia), Z (New Zealand), N (None) or U (Unknown), cannot be null.
     * @param tz Timezone in "tz" (Olson) format, i.e. Country/Region. Cannot be null.
     * @return int result The airport_id of the airport that was just created by the AirportAccessor.
     */
    public int save(String name, String city, String country, String iata, String icao, double latitude,
                    double longitude, int altitude, float timezone, String dst, String tz) {
        // Checks that the IATA code is valid and that if the IATA code is not null, it does not already exist in the database
        // If this is not true, returns an error code of -1
        if (!iataIsValid(iata)) {
            return -1;
        }
        // Checks that the ICAO code is valid and that if the ICAO code is not null, it does not already exist in the database
        // If this is not true, returns an error code of -1
        if (!icaoIsValid(icao)) {
            return -1;
        }

        // Adds the parameters into an List to pass into the save method of the AirportAccessor
        List<Object> elements = Arrays.asList(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);

        return accessor.save(elements);
    }

    /**
     * Checks the validity of input parameters and then passes them into the update method of the AirportAccessor.
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
     * @return int result The airport_id of the airport that was just updated by the AirportAccessor.
     */
    public int update(int id, String new_name, String new_city, String new_country, String new_iata,
                      String new_icao, Double new_latitude, Double new_longitude, Integer new_altitude,
                      Float new_timezone, String new_dst, String new_tz) throws SQLException {
        ResultSet currentContent = getData(id);
        // Check for whether there exists an entry for this airport id in the database
        if (!currentContent.next()) {
            return 0; // 0 rows were updated
        }

        // Checks that the IATA code is valid (which includes null), if it isn't returns an error code of -1
        String currIATA = currentContent.getString(5);
        String currICAO = currentContent.getString(6);
        if (!currIATA.equals(new_iata)) {
            if (!iataIsValid(new_iata)) {
                return -1;
            }
        }
        // Checks that the ICAO code is valid (which includes null), if it isn't returns an error code of -1
        if (!currICAO.equals(new_icao)) {
            if (!icaoIsValid(new_icao)) {
                return -1;
            }
        }
        // Passes the parameters into the update method of the AirportAccessor
        return accessor.update(id, new_name, new_city, new_country, new_iata, new_icao, new_latitude,
                                    new_longitude, new_altitude, new_timezone, new_dst, new_tz);
    }

    /**
     * Checks if the airport to be deleted exists, and if it does then passes the airport_id into the delete method of AirportAccessor.
     *
     * @param id The airport_id of the airport to be deleted.
     * @return boolean result True if the delete operation is successful, False otherwise.
     */
    public boolean delete(int id) {
        if (!accessor.dataExists(id)) {
            System.out.println("Could not delete airport, does not exist.");
            return false;
        }

        return accessor.delete(id);
    }

    /**
     * Retrieves the airport with specified id.
     *
     * @param id int value of an id.
     * @return ResultSet of an airport.
     */
    public ResultSet getData(int id) {
        return accessor.getData(id);
    }

    /**
     * Retrieves the aiport with specified IATA/ICAO.
     *
     * @param code
     * @return ResultSet of an airport.
     */
    public ResultSet getData(String code) {
        return accessor.getData(code);
    }

    /**
     * Retrieves all airports with the given parameters.
     *
     * @param name string containing the airport name.
     * @param city string containing a city.
     * @param country string containing a country.
     * @return ResultSet of airports.
     */
    public ResultSet getData(String name, String city, String country) {
        return accessor.getData(name, city, country);
    }

    /**
     * Checks if an airport exists with the given IATA/ICAO code.
     *
     * @param code An airport IATA/ICAO code.
     * @return boolean True if the airport exists, false otherwise.
     */
    public boolean dataExists(String code) {
        return accessor.dataExists(code);
    }

    /**
     * Checks that a given airport IATA code is valid.
     * IATA code must either be null or not exist in the database to be valid.
     *
     * @param iata An airport IATA code.
     * @return boolean True if the IATA code is valid, False otherwise.
     */
    public boolean iataIsValid(String iata) {
        return (iata == null || !dataExists(iata));
    }

    /**
     * Checks that a given airport ICAO code is valid.
     * ICAO code must either be null or not exist in the database to be valid.
     *
     * @param icao An airport ICAO code.
     * @return boolean True if the ICAO code is valid, False otherwise.
     */
    public boolean icaoIsValid(String icao) {
        return (icao == null || !dataExists(icao));
    }

    /**
     * Calls the getMaxID method of the AirportAccessor to get the maximum airport_id contained in the database.
     *
     * @return int The maximum airport_id contained in the database.
     */
    public int getMaxID() {
        return accessor.getMaxID();
    }
}
