package seng202.team5.service;

import seng202.team5.accessor.AirportAccessor;

import java.util.Arrays;
import java.util.List;

public class AirportService implements Service {

    private final AirportAccessor airportAccessor = new AirportAccessor();
    private final List<String> validDSTs = Arrays.asList("E", "A", "S", "O", "Z", "N", "U");

    public int updateAirport(int id, String new_name, String new_city, String new_country, String new_iata,
                             String new_icao, double new_latitude, double new_longitude, int new_altitude,
                             int new_timezone, String new_dst, String new_tz) {
        if (!iataIsValid(new_iata)) {
            return -1;
        }
        if (!icaoIsValid(new_icao)) {
            return -1;
        }
        if (new_dst != null) {
            if (!dstIsValid(new_dst)) {
                return -1;
            }
        }
        return airportAccessor.update(id, new_name, new_city, new_country, new_iata, new_icao, new_latitude,
                                    new_longitude, new_altitude, new_timezone, new_dst, new_tz);
    }

    public boolean deleteAirport(int id) {
        if (!airportAccessor.dataExists(id)) {
            System.out.println("Could not delete airport, does not exist.");
            return false;
        }
        return airportAccessor.delete(id);
    }

    public boolean iataIsValid(String iata) { //should we also use a regular expression to check what characters iata/icao codes contain
        return (iata == null || iata.length() == 3);
    }

    public boolean icaoIsValid(String icao) {
        return (icao == null || icao.length() == 4);
    }

    public boolean dstIsValid(String dst) {
        return (validDSTs.contains(dst));
    }

    //do we need to check if tz is valid? i.e. ???/???
}
