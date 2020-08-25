package seng202.team5.service;

import seng202.team5.accessor.AirlineAccessor;
import seng202.team5.accessor.AirportAccessor;
import seng202.team5.accessor.RouteAccessor;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * RouteService
 *
 * Contains the functions save, update, delete, getRoute(s), and validity check functions for routes that interact with Accessors.
 * Implements the Service interface.
 *
 * @author Inga Tokarenko
 * @author Billie Johnson
 */
public class RouteService implements Service {

    private final RouteAccessor accessor;
    private final AirlineAccessor airlineAccessor;
    private final AirportAccessor airportAccessor;

    /**
     * Constructor for RouteService.
     * Creates a RouteAccessor, AirlineAccessor, and AirportAccessor.
     *
     * @author Inga Tokarenko
     */
    public RouteService() {
        accessor = new RouteAccessor();
        airlineAccessor = new AirlineAccessor();
        airportAccessor = new AirportAccessor();
    }

    /**
     * Checks the validity of input parameters and then passes them into the save method of the RouteAccessor as an ArrayList.
     *
     * @param airline The 2-letter IATA code or 3-letter ICAO code of the airline, cannot be null.
     * @param source_airport The 3-letter IATA code or 4-letter ICAO code of the source airport, cannot be null.
     * @param dest_airport The 3-letter IATA code or 4-letter ICAO code of the destination airport, cannot be null.
     * @param codeshare "Y" if the flight is operated by a different airline, otherwise "N". Cannot be null.
     * @param stops The number of stops for the flight, 0 if it is direct. An integer, cannot be null.
     * @param equipment 3-letter codes for plane types(s) commonly used for this flight, separated by spaces. Cannot be null.
     * @return int result The route_id of the route that was just created by the RouteAccessor.
     *
     * @author Inga Tokarenko
     */
    public int saveRoute(String airline, String source_airport, String dest_airport, String codeshare, int stops, String equipment) {
        // Checks that an airline with the given IATA or ICAO code exists, if one doesn't, returns an error code of -1
        if (!airlineAccessor.dataExists(airline)) {
            return -1;
        }
        // Checks that an airport with the given IATA or ICAO code exists, if one doesn't, returns an error code of -1
        if (!airportAccessor.dataExists(source_airport)) {
            return -1;
        }
        // Checks that an airport with the given IATA or ICAO code exists, if one doesn't, returns an error code of -1
        if (!airportAccessor.dataExists(dest_airport)) {
            return -1;
        }
        // Checks that the codeshare is valid, if it isn't returns an error code of -1
        if (!codeshareIsValid(codeshare)) {
            return -1;
        }
        // Checks that the equipment is valid, if it isn't returns an error code of -1
        if (!equipmentIsValid(equipment)) {
            return -1;
        }

        // Adds the parameters into an ArrayList to pass into the save method of the RouteAccessor
        List<Object> tmp = Arrays.asList(airline, source_airport, dest_airport, codeshare, stops, equipment);
        ArrayList<Object> elements = new ArrayList<>();
        elements.addAll(tmp);

        return accessor.save(elements);
    }

    /**
     * Checks the validity of input parameters and then passes them into the update method of the RouteAccessor.
     *
     * @param id The route_id of the given route you want to update.
     * @param new_airline The new 2-letter IATA or 3-letter ICAO code of the airline, may be null if not to be updated.
     * @param new_source_airport The new 3-letter IATA or 4-letter ICAO code of the source airport, may be null if not to be updated.
     * @param new_dest_airport The new 3-letter IATA or 4-letter ICAO code of the destination airport, may be null if not to be updated.
     * @param new_codeshare The new codeshare of the route, "Y" or "N", may be null if not to be updated.
     * @param new_stops The new number of stops for the route, an integer, may be -1 if not to be updated.
     * @param new_equipment The new equipment for the route, may be null if not to be updated.
     * @return int result The route_id of the route that was just updated by the RouteAccessor.
     *
     * @author Billie Johnson
     */
    public int updateRoute(int id, String new_airline, String new_source_airport, String new_dest_airport,
                           String new_codeshare, int new_stops, String new_equipment) {
        int new_airline_id = -1;
        int new_source_airport_id = -1;
        int new_dest_airport_id = -1;

        // If the airline is not null, checks that an airline with the given IATA or ICAO code exists
        // If one doesn't, returns an error code of -1
        // If one does, gets the airline_id of the airline with the given IATA or ICAO code
        if (new_airline != null) {
            if (!airlineAccessor.dataExists(new_airline)) {
                return -1;
            } else {
                new_airline_id = airlineAccessor.getAirlineId(new_airline);
            }
        }
        // If the source airport is not null, checks that an airport with the given IATA or ICAO code exists
        // If one doesn't, returns an error code of -1
        // If one does, gets the airport_id of the airport with the given IATA or ICAO code
        if (new_source_airport != null) {
            if (!airportAccessor.dataExists(new_source_airport)) {
                return -1;
            } else {
                new_source_airport_id = airportAccessor.getAirportId(new_source_airport);
            }
        }
        // If the destination airport is not null, checks that an airport with the given IATA or ICAO code exists
        // If one doesn't, returns an error code of -1
        // If one does, gets the airport_id of the airport with the given IATA or ICAO code
        if (new_dest_airport != null) {
            if (!airportAccessor.dataExists(new_dest_airport)) {
                return -1;
            } else {
                new_dest_airport_id = airportAccessor.getAirportId(new_dest_airport);
            }
        }
        // If the codeshare is not null, checks that it is valid, if it isn't, returns an error code of -1
        if (new_codeshare != null) {
            if (!codeshareIsValid(new_codeshare)) {
                return -1;
            }
        }
        // If the equipment is not null, checks that it is valid, if it isn't, returns an error code of -1
        if (new_equipment != null) {
            if (!equipmentIsValid(new_equipment)) {
                return -1;
            }
        }

        // Passes the parameters into the update method of the RouteAccessor
        return accessor.update(id, new_airline, new_airline_id, new_source_airport, new_source_airport_id, new_dest_airport,
                new_dest_airport_id, new_codeshare, new_stops, new_equipment);
    }

    /**
     * Checks if the route to be deleted exists, and if it does then passes the route_id into the delete method of RouteAccessor.
     *
     * @param id The route_id of the route to be deleted.
     * @return boolean result True if the delete operation is successful, False otherwise.
     *
     * @author Billie Johnson
     */
    public boolean deleteRoute(int id) {
        if (!accessor.dataExists(id)) {
            System.out.println("Could not delete route, does not exist.");
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
    public ResultSet getRoute(int id) {
        return accessor.getData(id);
    }

    /**
     *
     *
     * @param airline
     * @param source_airport
     * @param dest_airport
     * @param stops
     * @param equipment
     * @return
     *
     * @author Inga Tokarenko
     */
    public ResultSet getRoutes(String airline, String source_airport, String dest_airport, int stops, String equipment) {
        return accessor.getData(airline, dest_airport, stops, equipment);
    }

    /**
     * Checks that a given codeshare is valid.
     * Codeshare must be either "Y" or "N" to be valid.
     *
     * @param codeshare
     * @return boolean True if the codeshare is valid, False otherwise.
     *
     * @author Billie Johnson
     */
    public boolean codeshareIsValid(String codeshare) {
        return (codeshare.equals("Y") || codeshare.equals("N"));
    }

    /**
     * Checks that given equipment is valid.
     * Uses regex to check that it's a series of one or more 3-letter codes separated by spaces.
     *
     * @param equipment
     * @return boolean True if the equipment is valid, False otherwise.
     *
     * @author Billie Johnson
     */
    public boolean equipmentIsValid(String equipment) {
        return (equipment.matches("[A-Z0-9]{3}(\\s{1}[A-Z0-9])*"));
    }
}
