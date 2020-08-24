package seng202.team5.data;

import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.FlightService;
import seng202.team5.service.RouteService;

import java.util.ArrayList;

public class ConcreteAddData extends AddData {

    private AirlineService airlineService = new AirlineService();
    private AirportService airportService = new AirportService();
    private FlightService flightService = new FlightService();
    private RouteService routeService = new RouteService();

    @Override
    public int addAirline(String name, String alias, String iata, String icao,
                          String callsign, String country, String active) {


        AirlineData airlineData = new AirlineData(name, alias, iata, icao, callsign, country, active);
        airlineData.convertBlanksToNull();
        int validityValue = airlineData.checkValues();
        if (validityValue > 0) {

            int id = airlineService.saveAirline(airlineData.getName(), airlineData.getAlias(), airlineData.getIata(),
                    airlineData.getIcao(), airlineData.getCallsign(), airlineData.getCountry(), airlineData.getActive());

            if (id != -1) {
                System.out.println("Airline added with id " + id);
                return id;
            }
            else {
                System.out.println("Failed to add airline.");
                return -1;
            }
        } else {
            return validityValue;
        }



    }

    @Override
    public int addAirport(String name, String city, String country, String iata, String icao, double latitude,
                          double longitude, int altitude, float timezone, String dst, String tz) {

        AirportData airportData = new AirportData(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        airportData.convertBlanksToNull();
        int validityValue = airportData.checkValues();
        if (validityValue > 0) {
            // Valid data
            int id = airportService.saveAirport(airportData.getAirportName(), airportData.getCity(), airportData.getCountry(),
                    airportData.getIata(), airportData.getIcao(), airportData.getLatitude(), airportData.getLongitude(),
                    airportData.getAltitude(), airportData.getTimezone(), airportData.getDst(), airportData.getTzDatabaseTimezone());

            if (id != -1) {
                System.out.println("Airport added with id " + id);
                return id;
            }
            else {
                System.out.println("Failed to add airport.");
                return -1;
            }
        } else {
            return validityValue;
        }

    }

    @Override
    public int addFlightEntry(int flightID, String airline, String airport, int altitude, double latitude, double longitude) {

        FlightData flightData = new FlightData(flightID, airline, airport, altitude, latitude, longitude);
        flightData.convertBlanksToNull();
        int validityValue = flightData.checkValues();
        if (validityValue > 0) {
            int id = flightService.saveFlight(flightID, airline, airport, altitude, latitude, longitude);

            if (id != -1) {
                System.out.println("Flight entry added with id " + id + " and flight id " + flightID);
                return id;
            }
            else {
                System.out.println("Failed to add flight entry.");
                return -1;
            }
        } else {
            return validityValue;
        }


    }

    @Override
    public int addRoute(String airline, String source_airport, String dest_airport,
                        String codeshare, int stops, String equipment) {
        RouteData routeData = new RouteData(airline, source_airport, dest_airport, codeshare, stops, equipment);
        routeData.convertBlanksToNull();
        int validityValue = routeData.checkValues();
        if (validityValue > 0) {
            int id = routeService.saveRoute(airline, source_airport, dest_airport, codeshare, stops, equipment);

            if (id != -1) {
                System.out.println("Route added with id " + id);
                return id;
            }
            else {
                System.out.println("Failed to add route.");
                return -1;
            }
        } else {
            return validityValue;
        }


    }

}
