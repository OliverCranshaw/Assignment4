package seng202.team5.data;

public abstract class AddData {

    public abstract int addAirline(String name, String alias, String iata, String icao, String callsign, String country, String active);

    public abstract int addAirport(String name, String city, String country, String iata, String icao, double latitude,
                                    double longitude, int altitude, int timezone, String dst, String tz);

    public abstract int addFlightEntry(int flightID, String airline, String airport, int altitude, double latitude, double longitude);

    public abstract int addRoute(String airline, String source_airport, String dest_airport, String codeshare, int stops, String equipment);

}
