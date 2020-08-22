package seng202.team5.data;

public abstract class UpdateData {

    public abstract void updateAirline(int id, String new_name, String new_alias, String new_iata, String new_icao,
                                       String new_callsign, String new_country, String new_active);

    public abstract void updateAirport(int id, String new_name, String new_city, String new_country, String new_iata,
                                       String new_icao, double new_latitude, double new_longitude, int new_altitude,
                                       int new_timezone, String new_dst, String new_tz);

    public abstract void updateFlightEntry(int id, String new_airline, String new_airport, int new_altitude,
                                           double new_latitude, double new_longitude);

    public abstract void updateRoute(int id, String new_airline, String new_source_airport, String new_dest_airport,
                                     String new_codeshare, int new_stops, String new_equipment);

}
