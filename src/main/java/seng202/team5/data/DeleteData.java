package seng202.team5.data;

/**
 * DeleteData
 *
 * Contains abstract methods for deleting airlines, airports, flight entries, flights, and routes.
 *
 * @author Billie Johnson
 */
public abstract class DeleteData {

    /**
     * Passes the airline_id into the deleteAirline method of AirlineService.
     *
     * @param id The airline_id of the airline to be deleted.
     * @return boolean True if the delete operation is successful, False otherwise.
     */
    public abstract Boolean deleteAirline(int id);

    /**
     * Passes the airport_id into the deleteAirport method of AirportService.
     *
     * @param id The airport_id of the airport to be deleted.
     * @return boolean True if the delete operation is successful, False otherwise.
     */
    public abstract Boolean deleteAirport(int id);

    /**
     * Passes the unique id of the flight entry into the deleteEntry method of FlightService.
     *
     * @param id The unique id of the flight entry to be deleted.
     * @return boolean True if the delete operation is successful, False otherwise.
     */
    public abstract Boolean deleteFlightEntry(int id);

    /**
     * Passes the flight_id of the flight entries to be deleted into the deleteFlight method of FlightService.
     *
     * @param flight_id The flight_id of the flight entries to be deleted.
     * @return boolean True if the delete operation is successful, False otherwise.
     */
    public abstract Boolean deleteFlight(int flight_id);

    /**
     * Passes the route_id into the deleteRoute method of RouteService.
     *
     * @param id The route_id of the route to be deleted.
     * @return boolean True if the delete operation is successful, False otherwise.
     */
    public abstract Boolean deleteRoute(int id);

}
