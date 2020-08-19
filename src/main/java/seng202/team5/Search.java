package seng202.team5;

import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.FlightService;
import seng202.team5.service.RouteService;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Search {

    private static Search searchInstance;
    private ArrayList<Object> searchData;


    private AirlineService airlineService;
    private AirportService airportService;
    private FlightService flightService;
    private RouteService routeService;

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

    public void setSearchData(ArrayList<Object> data) {
        searchData = data;
    }

    public void searchAirline() {

        String name = searchData.get(0) == null ? null : searchData.get(0).toString();
        String country = searchData.get(1) == null ? null : searchData.get(1).toString();
        String callsign = searchData.get(2) == null ? null : searchData.get(2).toString();

        airlineService.getAirlines(name, country, callsign);

    }

    public void searchAirport() {

        String name = searchData.get(0) == null ? null : searchData.get(0).toString();
        String city = searchData.get(1) == null ? null : searchData.get(1).toString();
        String country = searchData.get(2) == null ? null : searchData.get(2).toString();

        airportService.getAirports(name, city, country);

    }

    public void searchFlight() {

        String airline = searchData.get(0) == null ? null : searchData.get(0).toString();
        String airport = searchData.get(1) == null ? null : searchData.get(1).toString();

        flightService.getFlights(airline, airport);

    }

    public void searchRoute() {

        String sourceAirport = searchData.get(0) == null ? null : searchData.get(0).toString();
        String destAirport = searchData.get(1) == null ? null : searchData.get(1).toString();
        int numStops = (int) searchData.get(2);
        String equipment = searchData.get(3) == null ? null : searchData.get(3).toString();

        routeService.getRoutes(sourceAirport, destAirport, numStops, equipment);

    }

}
