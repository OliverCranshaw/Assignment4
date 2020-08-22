package seng202.team5.service;

import seng202.team5.accessor.AirlineAccessor;
import seng202.team5.accessor.AirportAccessor;
import seng202.team5.accessor.FlightAccessor;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlightService implements Service {

    private final FlightAccessor accessor;
    private final AirlineAccessor airlineAccessor;
    private final AirportAccessor airportAccessor;

    public FlightService() {
        accessor = new FlightAccessor();
        airlineAccessor = new AirlineAccessor();
        airportAccessor = new AirportAccessor();
    }

    public int saveFlight(int flightID, String airline, String airport, int altitude, double latitude, double longitude) {
        if (!airlineAccessor.dataExists(airline)) {
            return -1;
        }
        if (!airportAccessor.dataExists(airport)) {
            return -1;
        }

        List<Object> tmp = Arrays.asList(flightID, airline, airport, altitude, latitude, longitude);
        ArrayList<Object> elements = new ArrayList<>();
        elements.addAll(tmp);

        return accessor.save(elements);
    }

    public int updateFlight(int id, String new_airline, String new_airport, int new_altitude,
                      double new_latitude, double new_longitude) {
        if (!airlineAccessor.dataExists(new_airline)) {
            return -1;
        }
        if (!airportAccessor.dataExists(new_airport)) {
            return -1;
        }

        return accessor.update(id, new_airline, new_airport, new_altitude, new_latitude, new_longitude);
    }

    public boolean deleteFlight(int id) {
        if (!accessor.flightExists(id)) {
            System.out.println("Could not delete flight, does not exist.");
            return false;
        }

        return accessor.deleteFlight(id);
    }

    public boolean deleteEntry(int id) {
        if (!accessor.dataExists(id)) {
            System.out.println("Could not delete flight data, does not exist.");
            return false;
        }

        return accessor.delete(id);
    }

    public ResultSet getFlight(int id) {
        return accessor.getData(id);
    }

    public ResultSet getFlights(String airline, String airport) {
        return accessor.getData(airline, airport);
    }

    public int getMaxFlightID() {
        return accessor.getMaxID();
    }
}
