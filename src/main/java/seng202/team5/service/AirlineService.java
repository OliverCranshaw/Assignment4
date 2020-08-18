package seng202.team5.service;

import seng202.team5.accessor.AirlineAccessor;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AirlineService implements Service {

    private final AirlineAccessor accessor;

    public AirlineService() { accessor = new AirlineAccessor(); }

    public int saveAirline(String name, String alias, String iata, String icao, String callsign, String country, String active) {
        if (!iataIsValid(iata) || (accessor.dataExists(iata) && iata != null)) {
            return -1;
        }
        if (!icaoIsValid(icao) || (accessor.dataExists(icao) && icao != null)) {
            return -1;
        }
        if (active != null) {
            if (!activeIsValid(active)) {
                return -1;
            }
        }

        List<String> tmp = Arrays.asList(name, alias, iata, icao, callsign, country, active);
        ArrayList<String> elements = new ArrayList<>();
        elements.addAll(tmp);

        return accessor.save(elements);
    }

    public int updateAirline(int id, String new_name, String new_alias, String new_iata, String new_icao,
                      String new_callsign, String new_country, String new_active) {
        if (!iataIsValid(new_iata)) {
            return -1;
        }
        if (!icaoIsValid(new_icao)) {
            return -1;
        }
        if (new_active != null) {
            if (!activeIsValid(new_active)) {
                return -1;
            }
        }

        return accessor.update(id, new_name, new_alias, new_iata, new_icao, new_callsign, new_country, new_active);
    }

    public boolean deleteAirline(int id) {
        if (!accessor.dataExists(id)) {
            System.out.println("Could not delete airline, does not exist.");
            return false;
        }
        return accessor.delete(id);
    }

    public ResultSet getAirline(int id) {
        return accessor.getData(id);
    }

    public ResultSet getAirlines(String name, String country, String callign) {
        return accessor.getData(name, country, callign);
    }

    public boolean iataIsValid(String iata) { //should we also use a regular expression to check what characters iata/icao codes contain
        return (iata == null || iata.length() == 2);
    }

    public boolean icaoIsValid(String icao) {
        return (icao == null || icao.length() == 3);
    }

    public boolean activeIsValid(String active) {
        return (active.equals("Y") || active.equals("N"));
    }
}

