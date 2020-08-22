package seng202.team5.data;

import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.FlightService;
import seng202.team5.service.RouteService;

public class AddData extends ModifyData {

    private AirlineService airlineService = new AirlineService();
    private AirportService airportService = new AirportService();
    private FlightService flightService = new FlightService();
    private RouteService routeService = new RouteService();

    @Override
    public void addAirline(String name, String alias, String iata, String icao,
                          String callsign, String country, String active) {
        if (alias.equals("")) {
            name = null;
        }
        if (iata.equals("")) {
            iata = null;
        }
        if (icao.equals("")) {
            icao = null;
        }
        if (callsign.equals("")) {
            callsign = null;
        }
        if (country.equals("")) {
            country = null;
        }

        int id = airlineService.saveAirline(name, alias, iata, icao, callsign, country, active);

        if (id != -1) {
            System.out.println("Airline added with id " + id);
        }
        else {
            System.out.println("Failed to add airline.");
        }
    }

    @Override
    public void addAirport(String name, String city, String country, String iata, String icao, double latitude,
                          double longitude, int altitude, int timezone, String dst, String tz) {
        if (iata.equals("")) {
            iata = null;
        }
        if (icao.equals("")) {
            icao = null;
        }

        int id = airportService.saveAirport(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);

        if (id != -1) {
            System.out.println("Airport added with id " + id);
        }
        else {
            System.out.println("Failed to add airport.");
        }
    }

    @Override
    public void addFlightEntry(int flightID, String airline, String airport, int altitude, double latitude, double longitude) {
        int id = flightService.saveFlight(flightID, airline, airport, altitude, latitude, longitude);

        if (id != -1) {
            System.out.println("Flight entry added with id " + id + " and flight id " + flightID);
        }
        else {
            System.out.println("Failed to add flight entry.");
        }
    }

    @Override
    public void addRoute(String airline, String source_airport, String dest_airport,
                        String codeshare, int stops, String equipment) {
        if (codeshare.equals("")) {
            codeshare = null;
        }

        int id = routeService.saveRoute(airline, source_airport, dest_airport, codeshare, stops, equipment);

        if (id != -1) {
            System.out.println("Route added with id " + id);
        }
        else {
            System.out.println("Failed to add route.");
        }
    }

}
