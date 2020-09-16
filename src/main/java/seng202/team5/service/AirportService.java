package seng202.team5.service;

import seng202.team5.accessor.AirportAccessor;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * AirportService
 *
 * Contains the functions save, update, delete, getAirport, and validity check functions for airports that interact with Accessors.
 * Implements the Service interface.
 *
 * @author Inga Tokarenko
 * @author Billie Johnson
 */
public class AirportService implements Service {

    private final AirportAccessor accessor;
    private final List<String> validDSTs;

    /**
     * Constructor for AirportService.
     * Creates an AirportAccessor and sets the valid DSTs.
     *
     * @author Inga Tokarenko
     */
    public AirportService() {
        accessor = new AirportAccessor();
        validDSTs = Arrays.asList("E", "A", "S", "O", "Z", "N", "U");
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
     *
     * @author Inga Tokarenko
     */
    public int saveAirport(String name, String city, String country, String iata, String icao, double latitude,
                           double longitude, int altitude, float timezone, String dst, String tz) {
        // Checks that the IATA code is valid and that if the IATA code is not null, it does not already exist in the database
        // If this is not true, returns an error code of -1
        if (!iataIsValid(iata) || (accessor.dataExists(iata) && iata != null)) {
            return -1;
        }
        // Checks that the ICAO code is valid and that if the ICAO code is not null, it does not already exist in the database
        // If this is not true, returns an error code of -1
        if (!icaoIsValid(icao) || (accessor.dataExists(icao) && iata != null)) {
            return -1;
        }
        // Checks that the dst is valid, if it isn't returns an error code of -1
        if (!dstIsValid(dst)) {
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
     *
     * @author Billie Johnson
     */
    public int updateAirport(int id, String new_name, String new_city, String new_country, String new_iata,
                             String new_icao, double new_latitude, double new_longitude, int new_altitude,
                             float new_timezone, String new_dst, String new_tz) {
        // Checks that the IATA code is valid (which includes null), if it isn't returns an error code of -1
        if (!iataIsValid(new_iata)) {
            return -1;
        }
        // Checks that the ICAO code is valid (which includes null), if it isn't returns an error code of -1
        if (!icaoIsValid(new_icao)) {
            return -1;
        }
        // If the dst is not null, checks that it is valid, if it isn't returns an error code of -1
        if (new_dst != null) {
            if (!dstIsValid(new_dst)) {
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
     *
     * @author Billie Johnson
     */
    public boolean deleteAirport(int id) {
        if (!accessor.dataExists(id)) {
            System.out.println("Could not delete airport, does not exist.");
            return false;
        }

        return accessor.delete(id);
    }

    /**
     *
     *
     * @param id
     * @return
     *
     * @author Inga Tokarenko
     */
    public ResultSet getAirport(int id) {
        return accessor.getData(id);
    }

    /**
     *
     *
     * @param id
     * @return
     *
     * @author Inga Tokarenko
     */
    public ResultSet getAirport(String code) {
        return accessor.getData(code);
    }

    /**
     *
     *
     * @param name
     * @param city
     * @param country
     * @return
     *
     * @author Inga Tokarenko
     */
    public ResultSet getAirports(String name, String city, String country) {
        return accessor.getData(name, city, country);
    }

    /**
     * Checks if an airport exists with the given IATA/ICAO code.
     *
     * @param code An airport IATA/ICAO code.
     * @return boolean True if the airport exists, false otherwise.
     */
    public boolean airportExists(String code) {
        return accessor.dataExists(code);
    }

    /**
     * Checks that a given airport IATA code is valid.
     * IATA code must either be null or of length 3 to be valid.
     *
     * @param iata An airport IATA code.
     * @return boolean True if the IATA code is valid, False otherwise.
     *
     * @author Billie Johnson
     */
    public boolean iataIsValid(String iata) {
        return (iata == null || iata.length() == 3);
    }

    /**
     * Checks that a given airport ICAO code is valid.
     * ICAO code must either be null or of length 4 to be valid.
     *
     * @param icao An airport ICAO code.
     * @return boolean True if the ICAO code is valid, False otherwise.
     *
     * @author Billie Johnson
     */
    public boolean icaoIsValid(String icao) {
        return (icao == null || icao.length() == 4);
    }

    /**
     * Checks that a given dst is valid.
     * dst must be one of E (Europe), A (US/Canada), S (South America), O (Australia), Z (New Zealand), N (None) or U (Unknown) to be valid.
     *
     * @param dst An airport dst, daylight savings time.
     * @return boolean True if the dst is valid, False otherwise.
     *
     * @author Billie Johnson
     */
    public boolean dstIsValid(String dst) {
        return (validDSTs.contains(dst));
    }

    /**
     * Calls the getMaxID method of the AirportAccessor to get the maximum airport_id contained in the database.
     *
     * @return int The maximum airport_id contained in the database.
     *
     * @author Billie Johnson
     */
    public int getMaxID() {
        return accessor.getMaxID();
    }
}
