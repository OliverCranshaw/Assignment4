package seng202.team5.data;

import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.FlightService;
import seng202.team5.service.RouteService;


/**
 * ConcreteDeleteData
 *
 * Overrides the deleteAirline(), deleteAirport(), deleteFlightEntry(), deleteFlight(), deleteRoute() methods
 * of the DeleteData abstract class.
 */
public class ConcreteDeleteData extends DeleteData{
    // Initializes the necessary service classes
    private AirlineService airlineService = new AirlineService();
    private AirportService airportService = new AirportService();
    private FlightService flightService = new FlightService();
    private RouteService routeService = new RouteService();

    /**
     * Overrides the deleteAirline() abstract method.
     * passes the given id parameter into the airlineService's deleteAirline method
     * @param id The airline_id of the airline to be deleted.
     * @return Boolean true if successfully deleted, false otherwise
     */
    @Override
    public Boolean deleteAirline(int id) {
        // Call to the deleteAirline method of the airlineService
        return airlineService.delete(id);
    }


    /**
     * Overrides the deleteAirport() abstract method.
     * passes the given id parameter into the airportService's deleteAirport method
     * @param id The airport_id of the airline to be deleted.
     * @return Boolean true if successfully deleted, false otherwise
     */
    @Override
    public Boolean deleteAirport(int id) {
        // Call to the deleteAirport method of the airportService
        return airportService.delete(id);
    }


    /**
     * Overrides the deleteFlightEntry() abstract method.
     * Passes the given id parameter into the flightService's deleteEntry method
     * @param id The unique id of the flight entry to be deleted.
     * @return Boolean true if successfully deleted, false otherwise
     */
    @Override
    public Boolean deleteFlightEntry(int id) {
        // Call to the deleteEntry method of the flightService
        return flightService.deleteEntry(id);
    }


    /**
     * Overrides the deleteFlight() abstract method.
     * Passes the given flight_id parameter into flightServices's deleteEntry method
     * @param flight_id The flight_id of the flight entries to be deleted.
     * @return Boolean true if successfully deleted, false otherwise
     */
    @Override
    public Boolean deleteFlight(int flight_id) {
        // Call to the deleteFlight method of the flightService
        return flightService.delete(flight_id);
    }


    /**
     * Overrides the deleteRoute() abstract method.
     * passes the given id parameter into the routeService's deleteRoute method
     * @param id The route_id of the route to be deleted.
     * @return Boolean true if successfully deleted, false otherwise
     */
    @Override
    public Boolean deleteRoute(int id) {
        // Call to the deleteRoute method of the routeService
        return routeService.delete(id);
    }
}
