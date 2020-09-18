package seng202.team5.service;

import seng202.team5.accessor.AirportAccessor;
import seng202.team5.accessor.FlightAccessor;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    private final AirportAccessor airportAccessor;

    /**
     * Constructor for FlightService.
     * Creates a FlightAccessor, AirlineAccessor, and AirportAccessor, and sets the valid location types.
     *
     * @author Inga Tokarenko
     */
    public FlightService() {
        accessor = new FlightAccessor();
        airportAccessor = new AirportAccessor();
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
        if (location_type.equals("APT")) {
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
        try {
            ResultSet flightEntry = getFlight(id);
            // Gets the location_type and location that the flight entry already has
            String location_type = flightEntry.getString("location_type");
            String location = flightEntry.getString("location");

            // If the new location type is not null and is APT, checks that the location, whether new or already existing, is in the airport database
            if (new_location_type != null) {
                if (new_location_type.equals("APT"))  {
                    if (new_location != null && !airportAccessor.dataExists(new_location)) {
                        return -1;
                    }
                    else if (!airportAccessor.dataExists(location)) {
                        return -1;
                    }
                }
            } // If the new location type is null, but the new location is not null, checks if the existing location type is APT, and if it is checks that the new location exists in the database
            else if (new_location != null) {
                if (location_type.equals("APT") && !airportAccessor.dataExists(new_location)) {
                    return -1;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
     * Retrieves the flight with specified id.
     *
     * @param id int value of an id.
     * @return ResultSet of a flight.
     *
     * @author Inga Tokarenko
     */
    public ResultSet getFlight(int id) {
        return accessor.getData(id);
    }

    /**
     * Retrieves all flights with the given parameters.
     *
     * @param location_type string containing location type.
     * @param location string containing location.
     * @return ResultSet of flights.
     *
     * @author Inga Tokarenko
     */
    public ResultSet getFlights(String location_type, String location) {
        return accessor.getData(location_type, location);
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