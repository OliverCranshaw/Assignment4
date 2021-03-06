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
 * Contains the functions save, update, delete, getData, and getMaxFlightID functions for flights that interact with Accessors.
 * Implements the Service interface.
 */
public class FlightService implements Service {

    private final FlightAccessor accessor;
    private final AirportAccessor airportAccessor;

    /**
     * Constructor for FlightService.
     * Creates a FlightAccessor, AirlineAccessor, and AirportAccessor, and sets the valid location types.
     */
    public FlightService() {
        accessor = new FlightAccessor();
        airportAccessor = new AirportAccessor();
    }

    /**
     * Checks the validity of input parameters and then passes them into the save method of the FlightAccessor as an ArrayList.
     *
     * @param flightID The integer flight_id of the new flight entry, cannot be null.
     * @param locationType The location type of the location of the flight entry, cannot be null.
     * @param location The location of the flight entry, cannot be null.
     * @param altitude The altitude of the plane at the time of the flight entry, in feet. An integer, cannot be null.
     * @param latitude The latitude of the plane at the time of the flight entry, a double. Negative is South, positive is North, cannot be null.
     * @param longitude The longitude of the plane at the time of the flight entry, a double. Negative is West, positive is East, cannot be null.
     * @return int result The unique id of the flight entry that was just created by the FlightAccessor, -1 if checks fail or fails to save.
     */
    public int save(int flightID, String locationType, String location, int altitude, double latitude, double longitude) {
        
        // Checks if an identical flight entry already exists
        if (accessor.dataExists(flightID, locationType, location, altitude, latitude, longitude)) {
            return -1;
        }
        
        // Checks that if the location type is APT that the location exists in the airport database, if it doesn't, returns an error code of -1
        if (locationType.equals("APT")) {
            if (!airportAccessor.dataExists(location)) {
                return -1;
            }
        }

        // Adds the parameters into an List to pass into the save method of the FlightAccessor
        List<Object> elements = Arrays.asList(flightID, locationType, location, altitude, latitude, longitude);

        return accessor.save(elements);
    }

    /**
     * Checks the validity of input parameters and then passes them into the update method of the FlightAccessor.
     *
     * @param id The unique id of the given flight entry you want to update.
     * @param newLocationType The new location type of the flight entry location, one of "APT", "VOR", or "FIX", may be null if not to be updated.
     * @param newLocation The new location of the flight entry, may be null if not to be updated.
     * @param newAltitude The new altitude of the flight entry in feet, an integer. May be null if not to be updated.
     * @param newLatitude The new latitude of the flight entry, a double. Negative is South and positive is North. May be null if not to be updated.
     * @param newLongitude The new longitude of the flight entry, a double. Negative is West and positive is East. May be null if not to be updated.
     * @return int result The unique id of the flight entry that was just updated by the FlightAccessor, -1 if checks fail.
     */
    public int update(int id, String newLocationType, String newLocation, int newAltitude,
                      double newLatitude, double newLongitude) {
        // If the airline is not null, checks that an airline with the given IATA or ICAO code exists
        // If one doesn't, returns an error code of -1
        try {
            ResultSet flightEntry = getData(id);
            // Gets the location_type and location that the flight entry already has
            String locationType = flightEntry.getString("location_type");
            String location = flightEntry.getString("location");

            // If the new location type is not null and is APT, checks that the location, whether new or already existing, is in the airport database
            if (newLocationType != null) {
                if (newLocationType.equals("APT"))  {
                    if (newLocation != null && !airportAccessor.dataExists(newLocation)) {
                        return -1;
                    } else if (!airportAccessor.dataExists(location) && location.equals(newLocation)) {
                        return -1;
                    }
                }
            } // If the new location type is null, but the new location is not null, checks if the existing location type is APT, and if it is checks that the new location exists in the database
            else if (newLocation != null) {
                if (locationType.equals("APT") && !airportAccessor.dataExists(newLocation)) {
                    return -1;
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        // Passes the parameters into the update method of the FlightAccessor
        return accessor.update(id, newLocationType, newLocation, newAltitude, newLatitude, newLongitude);
    }

    /**
     * Checks if the flight to be deleted exists, and if it does then passes the flight_id into the deleteFlight method of FlightAccessor.
     *
     * @param flightID The flight_id of the flight entries to be deleted.
     * @return boolean result True if the delete operation is successful, False otherwise.
     */
    public boolean delete(int flightID) {
        if (!accessor.flightExists(flightID)) {
            return false;
        }

        return accessor.deleteFlight(flightID);
    }

    /**
     * Checks if the flight entry to be deleted exists, and if it does then passes the unique id into the delete method of FlightAccessor.
     *
     * @param id The unique id of the flight entry to be deleted.
     * @return boolean result True if the delete operation is successful, False otherwise.
     */
    public boolean deleteEntry(int id) {
        if (!accessor.dataExists(id)) {
            return false;
        }

        return accessor.delete(id);
    }

    /**
     * Retrieves the flight with specified id.
     *
     * @param id int value of an id.
     * @return ResultSet of a flight.
     */
    public ResultSet getData(int id) {
        return accessor.getData(id);
    }

    /**
     * Retrieves all flights with the given parameters.
     *
     * @param locationType string containing location type.
     * @param location string containing location.
     * @return ResultSet of flights.
     */
    public ResultSet getData(String locationType, String location) {
        return accessor.getData(locationType, location);
    }

    /**
     * Calls the getMaxFlightID method of the FlightAccessor to get the maximum flight_id contained in the database, and then adds one to it.
     *
     * @return int The next available flight_id in the database.
     */
    public int getNextFlightID() {
        return accessor.getMaxFlightID() + 1;
    }

    /**
     * Calls the getMaxID method of the FlightAccessor to get the maximum unique id contained in the flight data table.
     *
     * @return int The maximum unique id contained in the flight data table.
     */
    public int getMaxID() {
        return accessor.getMaxID();
    }
}