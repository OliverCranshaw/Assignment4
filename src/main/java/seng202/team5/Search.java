package seng202.team5;

import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.FlightService;
import seng202.team5.service.RouteService;
import seng202.team5.table.FlightTable;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private ResultSet result = null;


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
     * @return a ResultSet containing the data from the database that satisfies the search criteria.
     *
     * @author Oliver Cranshaw
     * @author Inga Tokarenko
     */
    public ResultSet searchAirline() {

        String name = searchData.get(0) == null ? null : searchData.get(0).toString();
        String country = searchData.get(1) == null ? null : searchData.get(1).toString();
        String callsign = searchData.get(2) == null ? null : searchData.get(2).toString();

        result = airlineService.getAirlines(name, country, callsign);

        return result;


    }

    /**
     * Checks that the input from the user is not null and then passes these values to a call
     * for the airportService getAirports method which returns the ResultSet of data from
     * the database.
     *
     * @return a ResultSet containing the data from the database that satisfies the search criteria.
     *
     * @author Oliver Cranshaw
     * @author Inga Tokarenko
     */
    public ResultSet searchAirport() {

        String name = searchData.get(0) == null ? null : searchData.get(0).toString();
        String city = searchData.get(1) == null ? null : searchData.get(1).toString();
        String country = searchData.get(2) == null ? null : searchData.get(2).toString();

        result = airportService.getAirports(name, city, country);

        return result;
    }

    /**
     * Checks that the input from the user is not null and then passes these values to a call
     * for the flightService getFlights method which returns the ResultSet of data from
     * the database.
     *
     * @return a ResultSet containing the data from the database that satisfies the search criteria.
     *
     * @author Oliver Cranshaw
     * @author Inga Tokarenko
     */
    public ResultSet searchFlight() {

        String location = searchData.get(0) == null ? null : searchData.get(0).toString();
        String location_type = searchData.get(1) == null ? null : searchData.get(1).toString();


        result = flightService.getFlights(location_type, location);

        return result;

    }

    /**
     * Checks that the input from the user is not null and then passes these values to a call
     * for the routeService getRoutes method which returns the ResultSet of data from
     * the database.
     *
     * @return a ResultSet containing the data from the database that satisfies the search criteria.
     *
     * @author Oliver Cranshaw
     * @author Inga Tokarenko
     */
    public ResultSet searchRoute() {

        String sourceAirport = searchData.get(0) == null ? null : searchData.get(0).toString();
        String destAirport = searchData.get(1) == null ? null : searchData.get(1).toString();
        int numStops = (int) searchData.get(2);
        String equipment = searchData.get(3) == null ? null : searchData.get(3).toString();


        result = routeService.getRoutes(sourceAirport, destAirport, numStops, equipment);
        System.out.println(result);
        return result;
    }

}
