package seng202.team5.service;

import seng202.team5.accessor.AirlineAccessor;
import seng202.team5.accessor.AirportAccessor;
import seng202.team5.accessor.FlightAccessor;

public class FlightService implements Service {

    private final FlightAccessor flightAccessor = new FlightAccessor();
    private final AirlineAccessor airlineAccessor = new AirlineAccessor();
    private final AirportAccessor airportAccessor = new AirportAccessor();

    public int updateFlight(int id, String new_airline, String new_airport, int new_altitude,
                      double new_latitude, double new_longitude) {
        if (!airlineAccessor.dataExists(new_airline)) {
            return -1;
        }
        if (!airportAccessor.dataExists(new_airport)) {
            return -1;
        }
        return flightAccessor.update(id, new_airline, new_airport, new_altitude, new_latitude, new_longitude);
    }

    public boolean deleteFlight(int id) {
        if (!flightAccessor.flightExists(id)) {
            System.out.println("Could not delete flight, does not exist.");
            return false;
        }
        return flightAccessor.deleteFlight(id);
    }

    public boolean deleteEntry(int id) {
        if (!flightAccessor.dataExists(id)) {
            System.out.println("Could not delete flight data, does not exist.");
            return false;
        }
        return flightAccessor.delete(id);
    }
}
