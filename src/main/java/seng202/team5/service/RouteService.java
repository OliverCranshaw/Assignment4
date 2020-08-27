package seng202.team5.service;

import seng202.team5.accessor.AirlineAccessor;
import seng202.team5.accessor.AirportAccessor;
import seng202.team5.accessor.RouteAccessor;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteService implements Service {

    private final RouteAccessor accessor;
    private final AirlineAccessor airlineAccessor;
    private final AirportAccessor airportAccessor;

    public RouteService() {
        accessor = new RouteAccessor();
        airlineAccessor = new AirlineAccessor();
        airportAccessor = new AirportAccessor();
    }

    public int saveRoute(String airline, String source_airport, String dest_airport, String codeshare, int stops, String equipment) {
        if (!airlineAccessor.dataExists(airline)) {
            System.out.println("Here1");
            return -1;
        }
        if (!airportAccessor.dataExists(source_airport)) {
            System.out.println("Here2");
            return -1;
        }
        if (!airportAccessor.dataExists(dest_airport)) {
            System.out.println("here3");
            return -1;
        }
        if (!codeshareIsValid(codeshare)) {
            System.out.println("Here4");
            return -1;
        }
        if (!equipmentIsValid(equipment)) {
            System.out.println("HEre5");
            return -1;
        }

        int airline_id = airlineAccessor.getAirlineId(airline);
        int source_airport_id = airportAccessor.getAirportId(source_airport);
        int dest_airport_id = airportAccessor.getAirportId(dest_airport);

        List<Object> tmp = Arrays.asList(airline, airline_id, source_airport, source_airport_id, dest_airport,
                dest_airport_id, codeshare, stops, equipment);
        ArrayList<Object> elements = new ArrayList<>();
        elements.addAll(tmp);

        return accessor.save(elements);
    }

    public int updateRoute(int id, String new_airline, String new_source_airport, String new_dest_airport,
                      String new_codeshare, int new_stops, String new_equipment) {
        int new_airline_id = -1;
        int new_source_airport_id = -1;
        int new_dest_airport_id = -1;

        if (!airlineAccessor.dataExists(new_airline)) {
            return -1;
        } else {
            new_airline_id = airlineAccessor.getAirlineId(new_airline);
        }
        if (!airportAccessor.dataExists(new_source_airport)) {
            return -1;
        } else {
            new_source_airport_id = airportAccessor.getAirportId(new_source_airport);
        }
        if (!airportAccessor.dataExists(new_dest_airport)) {
            return -1;
        } else {
            new_dest_airport_id = airportAccessor.getAirportId(new_dest_airport);
        }
        if (!codeshareIsValid(new_codeshare)) {
            return -1;
        }
        if (!equipmentIsValid(new_equipment)) {
            return -1;
        }

        return accessor.update(id, new_airline, new_airline_id, new_source_airport, new_source_airport_id, new_dest_airport,
                new_dest_airport_id, new_codeshare, new_stops, new_equipment);
    }

    public boolean deleteRoute(int id) {
        if (!accessor.dataExists(id)) {
            System.out.println("Could not delete route, does not exist.");
            return false;
        }

        return accessor.delete(id);
    }

    public ResultSet getRoute(int id) {
        return accessor.getData(id);
    }

    public ResultSet getRoutes(String airline, String source_airport, String dest_airport, int stops, String equipment) {
        return accessor.getData(airline, dest_airport, stops, equipment);
    }

    public boolean codeshareIsValid(String codeshare) {
        return (codeshare.equals("Y") || codeshare.equals(""));
    }

    public boolean equipmentIsValid(String equipment) {
        return (equipment.matches("[A-Z0-9]{3}(\\s{1}[A-Z0-9])*"));
    }
}
