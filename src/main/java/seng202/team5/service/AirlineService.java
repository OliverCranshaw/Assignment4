package seng202.team5.service;

import seng202.team5.accessor.AirlineAccessor;

public class AirlineService implements Service {

    private final AirlineAccessor airlineAccessor = new AirlineAccessor();

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
        return airlineAccessor.update(id, new_name, new_alias, new_iata, new_icao, new_callsign, new_country, new_active);
    }

    public boolean deleteAirline(int id) {
        if (!airlineAccessor.dataExists(id)) {
            System.out.println("Could not delete airline, does not exist.");
            return false;
        }
        return airlineAccessor.delete(id);
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

