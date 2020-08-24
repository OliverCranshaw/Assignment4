package seng202.team5.data;

import java.util.Arrays;
import java.util.List;

public class FlightData implements Data {


    private Integer flightId;
    private String airline;
    private String airport;
    private Integer altitude;
    private Double latitude;
    private Double longitude;



    public Integer getFlightId() { return flightId; }

    public String getAirline() { return airline; }

    public String getAirport() { return airport; }

    public Integer getAltitude() { return altitude; }

    public Double getLatitude() { return latitude; }

    public Double getLongitude() { return longitude; }



    public FlightData(Integer flightID, String airline, String airport, Integer altitude,
                      Double latitude, Double longitude) {
        this.flightId = flightID;
        this.airline = airline;
        this.airport = airport;
        this.altitude = altitude;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public FlightData(String flightId, String airline, String airport, String altitude,
                      String latitude, String longitude) {
        this.airline = airline;
        this.airport = airport;

        // Parsing flight id to integer
        try {
            this.flightId = Integer.parseInt(flightId);
        } catch(NumberFormatException e) {
            System.out.println("Flight Data (flightID): " + e);
        }

        // Parsing altitude to integer
        try {
            this.altitude = Integer.parseInt(altitude);
        } catch(NumberFormatException e) {
            System.out.println("Flight Data (altitude): " + e);
        }

        // Parsing latitude to double
        try {
            this.latitude = Double.parseDouble(latitude);
        } catch(NumberFormatException e) {
            System.out.println("Flight Data (latitude): " + e);
        }

        // Parsing longitude to double
        try {
            this.longitude = Double.parseDouble(longitude);
        } catch(NumberFormatException e) {
            System.out.println("Flight Data (longitude): " + e);
        }
    }




    @Override
    public int checkValues() {
        if (this.flightId == null) {
            return -2;
        } else if (this.airline == null || (this.airline.length() != 2 && this.airline.length() != 3)) {
            return -3;
        } else if (this.airport == null || (this.airport.length() != 3 && this.airline.length() != 4)) {
            return -4;
        } else if (this.altitude == null) {
            return -5;
        } else if (this.latitude == null) {
            return -6;
        } else if (this.longitude == null) {
            return -7;
        } else {
            return 1;
        }
    }

    @Override
    public void convertBlanksToNull() {
        List<String> nullRepr = Arrays.asList("", "-", "\\N", "N/A");
        if (nullRepr.contains(this.airline)) {
            this.airline = null;
        }
        if (nullRepr.contains(this.airport)) {
            this.airport = null;
        }
    }
}
