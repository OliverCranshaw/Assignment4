package seng202.team5.service;

import seng202.team5.accessor.AirlineAccessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

/**
 * AirlineService
 *
 * Contains the functions save, update, delete, getData, and validity check functions for airlines that interact with Accessors.
 * Implements the Service interface.
 */
public class AirlineService implements Service {

    private final AirlineAccessor accessor;
    private final RouteService routeService;

    /**
     * Constructor for AirlineService.
     * Creates an AirlineAccessor.
     */
    public AirlineService() {
        accessor = new AirlineAccessor();
        routeService = new RouteService();
    }

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
     * @return int result The airline_id of the airline that was just created by the AirlineAccessor, -1 if checks fail or fails to save.
     */
    public int save(String name, String alias, String iata, String icao, String callsign, String country, String active) {
        // Checks that an airline has a unique code
        if (iata == null && icao == null) {
            return -1;
        }
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
     * @param newName The new name of the airline, may be null if not to be updated.
     * @param newAlias The new alias of the airline, may be null if not to be updated.
     * @param newIATA The new 2-letter IATA code of the airline, may be null if not to be updated.
     * @param newICAO The new 3-letter ICAO code of the airline, may be null if not to be updated.
     * @param newCallsign The new callsign of the airline, may be null if not to be updated.
     * @param newCountry The new country of the airline, may be null if not to be updated.
     * @param newActive The new active of the airline, "Y" or "N", may be null if not to be updated.
     * @return int result The airline_id of the airline that was just updated by the AirlineAccessor, -1 if checks fail.
     *
     * @throws SQLException Caused by ResultSet interactions.
     */
    public int update(int id, String newName, String newAlias, String newIATA, String newICAO,
                      String newCallsign, String newCountry, String newActive) throws SQLException {
        // Check if IATA and ICAO are both null
        if (newIATA == null && newICAO == null) {
            return -1;
        }

        String currIATA = null;
        String currICAO = null;

        ResultSet currAirline = getData(id);
        if (currAirline.next()) {
            // Gets the current IATA and ICAO before the update
            currIATA = getData(id).getString("iata");
            currICAO = getData(id).getString("icao");
        }

        // Checks that the IATA code is valid (which includes null), if it isn't returns an error code of -1
        if (currIATA != null && !currIATA.equals(newIATA)) {
            if (!iataIsValid(newIATA)) {
                return -1;
            }
        }

        // Checks that the ICAO code is valid (which includes null), if it isn't returns an error code of -1
        if (currICAO != null && !currICAO.equals(newICAO)) {
            if (!icaoIsValid(newICAO)) {
                return -1;
            }
        }

        // Passes the parameters into the update method of the AirlineAccessor
        int value = accessor.update(id, newName, newAlias, newIATA, newICAO, newCallsign, newCountry, newActive);

        // Also updates any flight entries or routes that used the previous IATA/ICAO code
        // Checks that the new code is different from the old code
        if (currIATA != null && newIATA != null && !newIATA.equals(currIATA)) {
            updateRoutes(newIATA, currIATA);
        }
        if (currICAO != null && newICAO != null && !newICAO.equals(currICAO)) {
            updateRoutes(newICAO, currICAO);
        }
        if (newIATA == null && currIATA != null) {
            updateRoutes(newICAO, currIATA);
        }
        if (newICAO == null && currICAO != null) {
            updateRoutes(newIATA, currICAO);
        }

        return value;
    }

    /**
     * Checks if the airline to be deleted exists, and if it does then passes the airline_id into the delete method of AirlineAccessor.
     *
     * @param id The airline_id of the airline to be deleted.
     * @return boolean result True if the delete operation is successful, False otherwise.
     */
    public boolean delete(int id) {
        if (!accessor.dataExists(id)) {
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
    public boolean iataIsValid(String iata) {
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

    /**
     * Updates any routes that contain the old airline IATA/ICAO code with the new code.
     *
     * @param newCode String, new airport IATA/ICAO code.
     * @param oldCode String, old airport IATA/ICAO code.
     *
     * @throws SQLException Caused by the ResultSet interactions.
     */
    public void updateRoutes(String newCode, String oldCode) throws SQLException {
        ResultSet result;

        // Checks if any routes contain the old code and updates them if it does
        if ((result = routeService.getData(oldCode)) != null) {
            while (result.next()) {
                routeService.update(result.getInt("route_id"), newCode, result.getString("source_airport"),
                        result.getString("destination_airport"), result.getString("codeshare"),
                        result.getInt("stops"), result.getString("equipment"));
            }
        }
    }

    /**
     * Finds all Airline names for the given airline codes (IATA or ICAO) and returns it as a hashtable mapping the
     * airline code to the name of the airline.
     *
     * @param airlineCodes ArrayList of Strings - airlineCodes (IATA or ICAO).
     * @return Hashtable of String to String - airlineCode to airlineName, empty is an SQL exception occurs.
     */
    public Hashtable<String, String> getAirlineNames(ArrayList<String> airlineCodes) {
        Hashtable<String, String> result = new Hashtable<>();

        try {
            ResultSet data = accessor.getAirlineNames(airlineCodes);
            if (data != null) {
                while (data.next()) {
                    String iata = data.getString(1);
                    String icao = data.getString(2);
                    String name = data.getString(3);
                    if (iata != null) {
                        result.put(iata, name);
                    } else {
                        result.put(icao, name);
                    }
                }
            } else {
                return result;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return result;
    }
}

