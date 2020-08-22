package seng202.team5.data;

public abstract class ModifyData {

    public abstract int addAirline(String name, String alias, String iata, String icao, String callsign, String country, String active);

    public abstract int addAirport(String name, String city, String country, String iata, String icao, double latitude,
                                    double longitude, int altitude, int timezone, String dst, String tz);

    public abstract void addFlightEntry(int flightID, String airline, String airport, int altitude, double latitude, double longitude);

    public abstract void addRoute(String airline, String source_airport, String dest_airport, String codeshare, int stops, String equipment);

    public abstract void updateAirline(int id, String new_name, String new_alias, String new_iata, String new_icao,
                                       String new_callsign, String new_country, String new_active);

    public abstract void updateAirport(int id, String new_name, String new_city, String new_country, String new_iata,
                                       String new_icao, double new_latitude, double new_longitude, int new_altitude,
                                       int new_timezone, String new_dst, String new_tz);

    public abstract void updateFlightEntry(int id, String new_airline, String new_airport, int new_altitude,
                                           double new_latitude, double new_longitude);

    public abstract void updateRoute(int id, String new_airline, String new_source_airport, String new_dest_airport,
                                     String new_codeshare, int new_stops, String new_equipment);

    public abstract void deleteAirline(int id);

    public abstract void deleteAirport(int id);

    public abstract void deleteFlightEntry(int id);

    public abstract void deleteFlight(int flight_id);

    public abstract void deleteRoute(int id);

}
