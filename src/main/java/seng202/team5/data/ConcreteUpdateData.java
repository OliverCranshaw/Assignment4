package seng202.team5.data;

import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.FlightService;
import seng202.team5.service.RouteService;

public class ConcreteUpdateData extends UpdateData {

    private AirlineService airlineService = new AirlineService();
    private AirportService airportService = new AirportService();
    private FlightService flightService = new FlightService();
    private RouteService routeService = new RouteService();

    @Override
    public int updateAirline(int id, String new_name, String new_alias, String new_iata, String new_icao,
                              String new_callsign, String new_country, String new_active) {
        AirlineData airlineData = new AirlineData(new_name, new_alias, new_iata, new_icao, new_callsign, new_country, new_active);
        airlineData.convertBlanksToNull();

        int validityValue = airlineData.checkValues();
        if (validityValue < 0) return validityValue;

        return airlineService.updateAirline(id, airlineData.getName(), airlineData.getAlias(), airlineData.getIata(), airlineData.getIcao(), airlineData.getCallsign(), airlineData.getCountry(), airlineData.getActive());
    }

    @Override
    public int updateAirport(int id, String new_name, String new_city, String new_country, String new_iata,
                              String new_icao, double new_latitude, double new_longitude, int new_altitude,
                              float new_timezone, String new_dst, String new_tz) {
        AirportData airportData = new AirportData(new_name, new_city, new_country, new_iata, new_icao, new_latitude, new_longitude, new_altitude, new_timezone, new_dst, new_tz);
        airportData.convertBlanksToNull();

        int validityValue = airportData.checkValues();
        if (validityValue < 0) return validityValue;

        return airportService.updateAirport(id, airportData.getAirportName(), airportData.getCity(), airportData.getCountry(),
                airportData.getIata(), airportData.getIcao(), airportData.getLatitude(), airportData.getLongitude(),
                airportData.getAltitude(), airportData.getTimezone(), airportData.getDst(), airportData.getTzDatabaseTimezone());
    }

    @Override
    public int updateFlightEntry(int id, String new_airline, String new_airport, int new_altitude,
                                  double new_latitude, double new_longitude) {
        FlightData flightData = new FlightData(id, new_airline, new_airport, new_altitude, new_latitude, new_longitude);
        flightData.convertBlanksToNull();
        int validityValue = flightData.checkValues();
        if (validityValue < 0)
            return validityValue;
        return flightService.updateFlight(flightData.getFlightId(), flightData.getAirline(), flightData.getAirport(), flightData.getAltitude(), flightData.getLatitude(), flightData.getLongitude());
    }

    @Override
    public int updateRoute(int id, String new_airline, String new_source_airport, String new_dest_airport,
                            String new_codeshare, int new_stops, String new_equipment) {
        RouteData routeData = new RouteData(new_airline, new_source_airport, new_dest_airport, new_codeshare, new_stops, new_equipment);
        routeData.convertBlanksToNull();
        int validityValue = routeData.checkValues();
        if (validityValue < 0)
            return validityValue;
        return routeService.updateRoute(id, routeData.getAirline(), routeData.getSourceAirport(), routeData.getDestinationAirport(), routeData.getCodeShare(), routeData.getStops(), routeData.getEquipment());
    }
}
