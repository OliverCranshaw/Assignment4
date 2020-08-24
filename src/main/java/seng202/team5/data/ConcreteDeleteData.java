package seng202.team5.data;

import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.FlightService;
import seng202.team5.service.RouteService;

public class ConcreteDeleteData extends DeleteData{

    private AirlineService airlineService = new AirlineService();
    private AirportService airportService = new AirportService();
    private FlightService flightService = new FlightService();
    private RouteService routeService = new RouteService();


    @Override
    public Boolean deleteAirline(int id) {
        return airlineService.deleteAirline(id);
    }

    @Override
    public Boolean deleteAirport(int id) {
        return airportService.deleteAirport(id);
    }

    @Override
    public Boolean deleteFlightEntry(int id) {
        return flightService.deleteEntry(id);
    }

    @Override
    public Boolean deleteFlight(int flight_id) {
        return flightService.deleteFlight(flight_id);
    }

    @Override
    public Boolean deleteRoute(int id) {
        return routeService.deleteRoute(id);
    }

}
