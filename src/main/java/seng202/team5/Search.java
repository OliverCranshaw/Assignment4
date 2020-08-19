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

        airlineService.getAirlines(searchData.get(0).toString(), searchData.get(1).toString(), searchData.get(2).toString());

    }

    public void searchAirpot() {

        airportService.getAirports(searchData.get(0).toString(), searchData.get(1).toString(), searchData.get(2).toString());

    }

    public void searchFlight() {

        flightService.getFlights(searchData.get(0).toString(), searchData.get(1).toString());

    }

    public void searchRoute() {

        routeService.getRoutes(searchData.get(0).toString(), searchData.get(1).toString(), (int) searchData.get(2), searchData.get(3).toString());

    }

}
