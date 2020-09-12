package seng202.team5.service;

import seng202.team5.accessor.AirlineAccessor;
import seng202.team5.accessor.AirportAccessor;
import seng202.team5.accessor.FlightAccessor;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * FlightService
 *
 * Contains the functions save, update, delete, getFlight(s), and getMaxFlightID functions for flights that interact with Accessors.
 * Implements the Service interface.
 *
 * @author Inga Tokarenko
 * @author Billie Johnson
 */
public class FlightService implements Service {

    private final FlightAccessor accessor;
    private final AirlineAccessor airlineAccessor;
    private final AirportAccessor airportAccessor;
    private final List<String> valid_location_types;

    /**
     * Constructor for FlightService.
     * Creates a FlightAccessor, AirlineAccessor, and AirportAccessor, and sets the valid location types.
     *
     * @author Inga Tokarenko
     */
    public FlightService() {
        accessor = new FlightAccessor();
        airlineAccessor = new AirlineAccessor();
        airportAccessor = new AirportAccessor();
        valid_location_types = Arrays.asList("APT", "VOR", "FIX");
    }

    /**
     * Checks the validity of input parameters and then passes them into the save method of the FlightAccessor as an ArrayList.
     *
     * @param flightID The integer flight_id of the new flight entry, cannot be null.
     * @param location_type The location type of the location of the flight entry, cannot be null.
     * @param location The location of the flight entry, cannot be null.
     * @param altitude The altitude of the plane at the time of the flight entry, in feet. An integer, cannot be null.
     * @param latitude The latitude of the plane at the time of the flight entry, a double. Negative is South, positive is North, cannot be null.
     * @param longitude The longitude of the plane at the time of the flight entry, a double. Negative is West, positive is East, cannot be null.
     * @return int result The unique id of the flight entry that was just created by the FlightAccessor.
     *
     * @author Inga Tokarenko
     */
    public int saveFlight(int flightID, String location_type, String location, int altitude, double latitude, double longitude) {
        // Checks that if the location type is APT that the location exists in the airport database, if it doesn't, returns an error code of -1
        if (!locationTypeisValid(location_type)) {
            return -1;
        }
        else if (location_type == "APT") {
            if (!airportAccessor.dataExists(location)) {
                return -1;
            }
        }

        // Adds the parameters into an List to pass into the save method of the FlightAccessor
        List<Object> elements = Arrays.asList(flightID, location_type, location, altitude, latitude, longitude);

        return accessor.save(elements);
    }

    /**
     * Checks the validity of input parameters and then passes them into the update method of the FlightAccessor.
     *
     * @param id The unique id of the given flight entry you want to update.
     * @param new_location_type The new location type of the flight entry location, one of "APT", "VOR", or "FIX", may be null if not to be updated.
     * @param new_location The new location of the flight entry, may be null if not to be updated.
     * @param new_altitude The new altitude of the flight entry in feet, an integer. May be null if not to be updated.
     * @param new_latitude The new latitude of the flight entry, a double. Negative is South and positive is North. May be null if not to be updated.
     * @param new_longitude The new longitude of the flight entry, a double. Negative is West and positive is East. May be null if not to be updated.
     * @return int result The unique id of the flight entry that was just updated by the FlightAccessor.
     *
     * @author Billie Johnson
     */
    public int updateFlight(int id, String new_location_type, String new_location, int new_altitude,
                            double new_latitude, double new_longitude) {
        // If the airline is not null, checks that an airline with the given IATA or ICAO code exists
        // If one doesn't, returns an error code of -1
        if (new_location_type != null) {
            if (locationTypeisValid(new_location_type)) {
                return -1;
            }
        }

        // Passes the parameters into the update method of the FlightAccessor
        return accessor.update(id, new_location_type, new_location, new_altitude, new_latitude, new_longitude);
    }

    /**
     * Checks if the flight to be deleted exists, and if it does then passes the flight_id into the deleteFlight method of FlightAccessor.
     *
     * @param flight_id The flight_id of the flight entries to be deleted.
     * @return boolean result True if the delete operation is successful, False otherwise.
     *
     * @author Billie Johnson
     */
    public boolean deleteFlight(int flight_id) {
        if (!accessor.flightExists(flight_id)) {
            System.out.println("Could not delete flight, does not exist.");
            return false;
        }

        return accessor.deleteFlight(flight_id);
    }

    /**
     * Checks if the flight entry to be deleted exists, and if it does then passes the unique id into the delete method of FlightAccessor.
     *
     * @param id The unique id of the flight entry to be deleted.
     * @return boolean result True if the delete operation is successful, False otherwise.
     *
     * @author Billie Johnson
     */
    public boolean deleteEntry(int id) {
        if (!accessor.dataExists(id)) {
            System.out.println("Could not delete flight data, does not exist.");
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
    public ResultSet getFlight(int id) {
        return accessor.getData(id);
    }

    /**
     *
     *
     * @param airline
     * @param airport
     * @return
     *
     * @author Inga Tokarenko
     */
    public ResultSet getFlights(String airline, String airport) {
        ArrayList airlineIataIcao = null;
        ArrayList airportIataIcao = null;

        if (airline != null) {
            airlineIataIcao = airlineAccessor.getAirlineIataIcao(airline);
        }
        if (airport != null) {
            airportIataIcao = airportAccessor.getAirportIataIcao(airport);
        }

        return accessor.getData(airlineIataIcao, airportIataIcao);
    }

    /**
     * Checks that the given location_type is one of "APT", "VOR", or "FIX" and returns True or False.
     *
     * @param location_type String to be checked if it's one of "APT", "VOR", or "FIX".
     * @return boolean True or False.
     *
     * @author Billie Johnson
     */
    public boolean locationTypeisValid(String location_type) {
        return valid_location_types.contains(location_type);
    }

    /**
     * Calls the getMaxFlightID method of the FlightAccessor to get the maximum flight_id contained in the database, and then adds one to it.
     *
     * @return int The next available flight_id in the database.
     *
     * @author Billie Johnson
     */
    public int getNextFlightID() {
        return accessor.getMaxFlightID() + 1;
    }

    /**
     * Calls the getMaxID method of the FlightAccessor to get the maximum unique id contained in the flight data table.
     *
     * @return int The maximum unique id contained in the flight data table.
     *
     * @author Billie Johnson
     */
    public int getMaxID() {
        return accessor.getMaxID();
    }
}
