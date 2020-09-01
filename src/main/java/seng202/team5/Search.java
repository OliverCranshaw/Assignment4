package seng202.team5;

import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.FlightService;
import seng202.team5.service.RouteService;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Search
 *
 * Contains the functions searchAirline, searchAirport, searchFlight, searchRoute. Each
 * function uses its relative accessor class to search for and return data specified by the
 * users' input.
 *
 *
 * @author Oliver Cranshaw
 * @author Inga Tokarenko
 */

public class Search {

    private static Search searchInstance;
    private ArrayList<Object> searchData;


    private AirlineService airlineService;
    private AirportService airportService;
    private FlightService flightService;
    private RouteService routeService;


    /** Constructor for the Search class.
     * Creates an instance of each service class and
     * an empty ArrayList of search data.
     *
     *  @author Oliver Cranshaw
     *  @author Inga Tokarenko
     */
    public Search() {

        //searchInstance = new Search();
        searchData = new ArrayList<>();

        airlineService = new AirlineService();
        airportService = new AirportService();
        flightService = new FlightService();
        routeService = new RouteService();

    }

    public static Search getInstance() {
        return searchInstance;
    }

    public ArrayList<Object> getSearchData() {
        return searchData;
    }

    /** Setter for the search data.
     *
     * @param data A list of data input by the user to specify what they are searching for.
     *
     * @author Oliver Cranshaw
     * @author Inga Tokarenko
     */
    public void setSearchData(ArrayList<Object> data) {
        searchData = data;
    }

    /**
     * Checks that the input from the user is not null and then passes these values to a call
     * for the arlineService getAirlines method which returns the ResultSet of data from
     * the database.
     *
     *  @author Oliver Cranshaw
     *  @author Inga Tokarenko
     *  @return
     */
    public ResultSet searchAirline() {

        String name = searchData.get(0) == null ? null : searchData.get(0).toString();
        String country = searchData.get(1) == null ? null : searchData.get(1).toString();
        String callsign = searchData.get(2) == null ? null : searchData.get(2).toString();

        return airlineService.getAirlines(name, country, callsign);

    }

    /**
     * Checks that the input from the user is not null and then passes these values to a call
     * for the airportService getAirports method which returns the ResultSet of data from
     * the database.
     *
     *  @author Oliver Cranshaw
     *  @author Inga Tokarenko
     * @return
     */
    public ResultSet searchAirport() {

        String name = searchData.get(0) == null ? null : searchData.get(0).toString();
        String city = searchData.get(1) == null ? null : searchData.get(1).toString();
        String country = searchData.get(2) == null ? null : searchData.get(2).toString();

        return airportService.getAirports(name, city, country);

    }

    /**
     * Checks that the input from the user is not null and then passes these values to a call
     * for the flightService getFlights method which returns the ResultSet of data from
     * the database.
     *
     *  @author Oliver Cranshaw
     *  @author Inga Tokarenko
     * @return
     */
    public ResultSet searchFlight() {

        String airline = searchData.get(0) == null ? null : searchData.get(0).toString();
        String airport = searchData.get(1) == null ? null : searchData.get(1).toString();

        return flightService.getFlights(airline, airport);

    }

    /**
     * Checks that the input from the user is not null and then passes these values to a call
     * for the routeService getRoutes method which returns the ResultSet of data from
     * the database.
     *
     *  @author Oliver Cranshaw
     *  @author Inga Tokarenko
     * @return
     */
    public ResultSet searchRoute() {

        String sourceAirport = searchData.get(0) == null ? null : searchData.get(0).toString();
        String destAirport = searchData.get(1) == null ? null : searchData.get(1).toString();
        int numStops = (int) searchData.get(2);
        String equipment = searchData.get(3) == null ? null : searchData.get(3).toString();

        return routeService.getRoutes(sourceAirport, destAirport, numStops, equipment);

    }

}
