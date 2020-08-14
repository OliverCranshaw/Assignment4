package seng202.team5.service;

import seng202.team5.accessor.AirlineAccessor;
import seng202.team5.accessor.AirportAccessor;
import seng202.team5.accessor.RouteAccessor;

public class RouteService implements Service {

    private final RouteAccessor routeAccessor = new RouteAccessor();
    private final AirportAccessor airportAccessor = new AirportAccessor();
    private final AirlineAccessor airlineAccessor = new AirlineAccessor();

    public int updateRoute(int id, String new_airline, String new_source_airport, String new_dest_airport,
                      String new_codeshare, int new_stops, String new_equipment) {
        if (!airlineAccessor.dataExists(new_airline)) {
            return -1;
        }
        if (!airportAccessor.dataExists(new_source_airport)) {
            return -1;
        }
        if (!airportAccessor.dataExists(new_dest_airport)) {
            return -1;
        }
        if (!codeshareIsValid(new_codeshare)) {
            return -1;
        }
        if (!equipmentIsValid(new_equipment)) {
            return -1;
        }
        return routeAccessor.update(id, new_airline, new_source_airport, new_dest_airport,
                                new_codeshare, new_stops, new_equipment);
    }

    public boolean deleteRoute(int id) {
        if (!routeAccessor.dataExists(id)) {
            System.out.println("Could not delete route, does not exist.");
            return false;
        }
        return routeAccessor.delete(id);
    }

    public boolean codeshareIsValid(String codeshare) {
        return (codeshare.equals("Y") || codeshare.equals(""));
    }

    public boolean equipmentIsValid(String equipment) {
        return (equipment.matches("[A-Z0-9]{3}(\\s{1}[A-Z0-9])*"));
    }
}
