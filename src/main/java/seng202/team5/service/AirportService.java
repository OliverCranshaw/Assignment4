package seng202.team5.service;

import seng202.team5.accessor.AirportAccessor;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AirportService implements Service {

    private final AirportAccessor accessor;
    private final List<String> validDSTs;

    public AirportService() {
        accessor = new AirportAccessor();
        validDSTs = Arrays.asList("E", "A", "S", "O", "Z", "N", "U");
    }

    public int saveAirport(String name, String city, String country, String iata, String icao, double latitude,
                           double longitude, int altitude, int timezone, String dst, String tz) {
        if (!iataIsValid(iata) || (accessor.dataExists(iata) && iata != null)) {
            return -1;
        }
        if (!icaoIsValid(icao) || (accessor.dataExists(iata) && iata != null)) {
            return -1;
        }
        if (dst != null) {
            if (!dstIsValid(dst)) {
                return -1;
            }
        }

        List<Object> tmp = Arrays.asList(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        ArrayList<Object> elements = new ArrayList<>();
        elements.addAll(tmp);

        return accessor.save(elements);
    }

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
        return accessor.update(id, new_name, new_city, new_country, new_iata, new_icao, new_latitude,
                                    new_longitude, new_altitude, new_timezone, new_dst, new_tz);
    }

    public boolean deleteAirport(int id) {
        if (!accessor.dataExists(id)) {
            System.out.println("Could not delete airport, does not exist.");
            return false;
        }
        return accessor.delete(id);
    }

    public ResultSet getAirport(int id) {
        return accessor.getData(id);
    }

    public ResultSet getAirports(String name, String city, String country) {
        return accessor.getData(name, city, country);
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
