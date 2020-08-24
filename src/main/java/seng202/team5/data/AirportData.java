package seng202.team5.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AirportData implements Data {

    private String airportName;
    private String city;
    private String country;
    private String iata;
    private String icao;
    private Double latitude;
    private Double longitude;
    private Integer altitude;
    private Float timezone;
    private String dst;
    private String tzDatabaseTimezone;


    public String getAirportName() {
        return airportName;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getIata() {
        return iata;
    }

    public String getIcao() {
        return icao;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Integer getAltitude() {
        return altitude;
    }

    public Float getTimezone() {
        return timezone;
    }

    public String getDst() {
        return dst;
    }

    public String getTzDatabaseTimezone() {
        return tzDatabaseTimezone;
    }



    public AirportData(String name, String city, String country, String iata, String icao, Double latitude, Double longitude,
                       Integer altitude, Float timezone, String dst, String tzDatabaseTimezone) {
        this.airportName = name;
        this.city = city;
        this.country = country;
        this.iata = iata;
        this.icao = icao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.timezone = timezone;
        this.dst = dst;
        this.tzDatabaseTimezone = tzDatabaseTimezone;
    }

    public AirportData(String airportName, String city, String country, String iata, String icao, String latitude,
                       String longitude, String altitude, String timezone, String dst, String tzDatabaseTimezone) {

        this.airportName = airportName;
        this.city = city;
        this.country = country;
        this.iata = iata;
        this.icao = icao;
        this.dst = dst;
        this.tzDatabaseTimezone = tzDatabaseTimezone;

        // Parsing Latitude to a double
        try {
            this.latitude = Double.parseDouble(latitude);
        } catch(NumberFormatException e){
            System.out.println("Airport Data (latitude): " + e);
        }
        // Parsing Longitude to a double
        try {
            this.longitude = Double.parseDouble(longitude);
        } catch(NumberFormatException e) {
            System.out.println("Airport Data (longitude): " + e);
        }
        // Parsing Altitude to a integer
        try {
            this.altitude = Integer.parseInt(altitude);
        } catch(NumberFormatException e) {
            System.out.println("Airport Data (altitude): " + e);
        }
        // Parsing Timezone to a float
        try {
            this.timezone = Float.parseFloat(timezone);
        } catch(NumberFormatException e) {
            System.out.println("Airport Data (timezone): " + e);
        }


    }

    @Override
    public int checkValues() {
        List<String> dstValues = Arrays.asList("E", "A", "S", "O", "Z", "N", "U");
        if (this.airportName == null) {
            return -2;
        } else if (this.city == null) {
            return -3;
        } else if (this.country == null) {
            return -4;
        } else if (!((this.iata == null) || ( this.iata.length() == 3))) {
            return -5;
        } else if (!((this.icao == null) || (this.icao.length() == 4))) {
            return -6;
        } else if (this.latitude == null) {
            return -7;
        } else if (this.longitude == null) {
            return -8;
        } else if (this.altitude == null) {
            return -9;
        } else if (this.timezone == null) {
            return -10;
        } else if (this.dst == null || !(dstValues.contains(this.dst)) ) {
            return -11;
        } else if (this.tzDatabaseTimezone == null) {
            return -12;
        } else {
            return 1;
        }
    }


    @Override
    public void convertBlanksToNull() {
        List<String> nullRepr = Arrays.asList("", "-", "\\N", "N/A");
        if (nullRepr.contains(this.airportName)) {
            this.airportName = null;
        }
        if (nullRepr.contains(this.city)) {
            this.city = null;
        }
        if (nullRepr.contains(this.country)) {
            this.country = null;
        }
        if (nullRepr.contains(this.iata)) {
            this.iata = null;
        }
        if (nullRepr.contains(this.icao)) {
            this.icao = null;
        }
        if (nullRepr.contains(this.dst)) {
            this.dst = null;
        }
        if (nullRepr.contains(this.tzDatabaseTimezone)) {
            this.tzDatabaseTimezone = null;
        }
    }


}
