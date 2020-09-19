package seng202.team5.service;

import seng202.team5.accessor.AirlineAccessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * AirlineService
 *
 * Contains the functions save, update, delete, getData, and validity check functions for airlines that interact with Accessors.
 * Implements the Service interface.
 */
public class AirlineService implements Service {

    private final AirlineAccessor accessor;

    /**
     * Constructor for AirlineService.
     * Creates an AirlineAccessor.
     */
    public AirlineService() { accessor = new AirlineAccessor(); }

    /**
     * Checks the validity of input parameters and then passes them into the save method of the AirlineAccessor as an ArrayList.
     *
     * @param name The name of the airline, cannot be null.
     * @param alias The alias of the airline.
     * @param iata The 2-letter IATA code of the airline, must be unique, may be null if not known/assigned.
     * @param icao The 3-letter ICAO code of the airline, must be unique, may be null if not known/assigned.
     * @param callsign The callsign of the airline.
     * @param country The country of the airline.
     * @param active "Y" if the airline is or has until recently been operational, "N" if it is defunct, cannot be null.
     * @return int result The airline_id of the airline that was just created by the AirlineAccessor.
     */
    public int save(String name, String alias, String iata, String icao, String callsign, String country, String active) {
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

        // Adds the parameters into an List to pass into the save method of the AirlineAccessor
        List<Object> elements = Arrays.asList(name, alias, iata, icao, callsign, country, active);

        return accessor.save(elements);
    }

    /**
     * Checks the validity of input parameters and then passes them into the update method of the AirlineAccessor.
     *
     * @param id The airline_id of the given airline you want to update.
     * @param new_name The new name of the airline, may be null if not to be updated.
     * @param new_alias The new alias of the airline, may be null if not to be updated.
     * @param new_iata The new 2-letter IATA code of the airline, may be null if not to be updated.
     * @param new_icao The new 3-letter ICAO code of the airline, may be null if not to be updated.
     * @param new_callsign The new callsign of the airline, may be null if not to be updated.
     * @param new_country The new country of the airline, may be null if not to be updated.
     * @param new_active The new active of the airline, "Y" or "N", may be null if not to be updated.
     * @return int result The airline_id of the airline that was just updated by the AirlineAccessor.
     */
    public int update(int id, String new_name, String new_alias, String new_iata, String new_icao,
                      String new_callsign, String new_country, String new_active) throws SQLException {
        // Checks that the IATA code is valid (which includes null), if it isn't returns an error code of -1
        AirlineService airlineService = new AirlineService();
        String currIATA = airlineService.getData(id).getString(4);
        String currICAO = airlineService.getData(id).getString(5);
        if (currIATA == null || (!currIATA.equals(new_iata))) {
            if (!iataIsValid(new_iata)) {
                return -1;
            }
        }
        // Checks that the ICAO code is valid (which includes null), if it isn't returns an error code of -1
        if (!(currICAO.equals(new_icao) || new_icao == null)) {
            if (!icaoIsValid(new_icao)) {
                return -1;
            }
        }
        // Passes the parameters into the update method of the AirlineAccessor
        return accessor.update(id, new_name, new_alias, new_iata, new_icao, new_callsign, new_country, new_active);
    }

    /**
     * Checks if the airline to be deleted exists, and if it does then passes the airline_id into the delete method of AirlineAccessor.
     *
     * @param id The airline_id of the airline to be deleted.
     * @return boolean result True if the delete operation is successful, False otherwise.
     */
    public boolean delete(int id) {
        if (!accessor.dataExists(id)) {
            System.out.println("Could not delete airline, does not exist.");
            return false;
        }

        return accessor.delete(id);
    }

    /**
     * Retrieves the airline with specified id.
     *
     * @param id int value of an id.
     * @return ResultSet of an airline.
     */
    public ResultSet getData(int id) {
        return accessor.getData(id);
    }

    /**
     * Retrieves all airlines with the given parameters.
     *
     * @param name string of the of an airline.
     * @param country string value of a country.
     * @param callign string value of a callsign.
     * @return ResultSet of airlines.
     */
    public ResultSet getData(String name, String country, String callign) {
        return accessor.getData(name, country, callign);
    }

    /**
     * Retrieves all airlines withe the given IATA or ICAO
     * @param code IATA or ICAO of an airline
     * @return ResultSet of airline.
     */
    public ResultSet getData(String code) {
        return accessor.getData(code);
    }

    /**
     * Checks if the airline with the provided IATA/ICAO exists.
     *
     * @param code IATA or ICAO of an airline.
     * @return boolean true or false.
     */
    public boolean airlineExists(String code) {
        return accessor.dataExists(code);
    }

    /**
     * Checks that a given airline IATA code is valid.
     * IATA code must either be null or not exist in the database to be valid.
     *
     * @param iata An airline IATA code.
     * @return boolean True if the IATA code is valid, False otherwise.
     */
    public boolean iataIsValid(String iata) { //should we also use a regular expression to check what characters iata/icao codes contain
        return (iata == null || !airlineExists(iata));
    }

    /**
     * Checks that a given airline ICAO code is valid.
     * ICAO code must either be null or not exist in the database to be valid.
     *
     * @param icao An airline ICAO code.
     * @return boolean True if the ICAO code is valid, False otherwise.
     */
    public boolean icaoIsValid(String icao) {
        return (icao == null || !airlineExists(icao));
    }

    /**
     * Calls the getMaxID method of the AirlineAccessor to get the maximum airline_id contained in the database.
     *
     * @return int The maximum airline_id contained in the database.
     */
    public int getMaxID() {
        return accessor.getMaxID();
    }
}

