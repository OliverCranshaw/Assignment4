package seng202.team5.data;

import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.FlightService;
import seng202.team5.service.RouteService;

public class ConcreteUpdateData extends UpdateData {

    private AirlineService airlineService = new AirlineService();
    private AirportService airportService = new AirportService();
    private FlightService flightService = new FlightService();
    private RouteService routeService = new RouteService();

    @Override
    public void updateAirline(int id, String new_name, String new_alias, String new_iata, String new_icao,
                              String new_callsign, String new_country, String new_active) {

        airlineService.updateAirline(id, new_name, new_alias, new_iata, new_icao, new_callsign, new_country, new_active);
    }

    @Override
    public void updateAirport(int id, String new_name, String new_city, String new_country, String new_iata,
                              String new_icao, double new_latitude, double new_longitude, int new_altitude,
                              int new_timezone, String new_dst, String new_tz) {
        airportService.updateAirport(id, new_name, new_city, new_country, new_iata, new_icao, new_latitude, new_longitude, new_altitude, new_timezone, new_dst, new_tz);
    }

    @Override
    public void updateFlightEntry(int id, String new_airline, String new_airport, int new_altitude,
                                  double new_latitude, double new_longitude) {
        flightService.updateFlight(id, new_airline, new_airport, new_altitude, new_latitude, new_longitude);
    }

    @Override
    public void updateRoute(int id, String new_airline, String new_source_airport, String new_dest_airport,
                            String new_codeshare, int new_stops, String new_equipment) {
        routeService.updateRoute(id, new_airline, new_source_airport, new_dest_airport, new_codeshare, new_stops, new_equipment);
    }
}
