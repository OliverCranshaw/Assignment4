package seng202.team5.data;

import java.util.ArrayList;

public class AirportData implements Data {

    private String airportName;
    private String city;
    private String country;
    private String iata;
    private String icao;
    private Double latitude;
    private Double longitude;
    private Integer altitude;
    private Integer timezone;
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

    public Integer getTimezone() {
        return timezone;
    }

    public String getDst() {
        return dst;
    }

    public String getTzDatabaseTimezone() {
        return tzDatabaseTimezone;
    }



    public AirportData(String name, String city, String country, String iata, String icao, Double latitude, Double longitude,
                       Integer altitude, Integer timezone, String dst, String tzDatabaseTimezone) {
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
        // Parsing Timezone to an integer
        try {
            this.timezone = Integer.parseInt(timezone);
        } catch(NumberFormatException e) {
            System.out.println("Airport Data (timezone): " + e);
        }


    }


    public ArrayList<Object> getValues() {
        ArrayList<Object> toReturn = new ArrayList<Object>();
        toReturn.add(this.airportName);
        toReturn.add(this.city);
        toReturn.add(this.country);
        toReturn.add(this.iata);
        toReturn.add(this.icao);
        toReturn.add(this.latitude);
        toReturn.add(this.longitude);
        toReturn.add(this.altitude);
        toReturn.add(this.timezone);
        toReturn.add(this.dst);
        toReturn.add(this.tzDatabaseTimezone);
        return toReturn;
    }



    @Override
    public int checkValues() {
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
        } else if (this.dst == null) {
            return -11;
        } else if (this.tzDatabaseTimezone == null) {
            return -12;
        } else {
            return 1;
        }
    }


    @Override
    public void convertBlanksToNull() {
        if (this.airportName.equals("")) {
            this.airportName = null;
        }
        if (this.city.equals("")) {
            this.city = null;
        }
        if (this.country.equals("")) {
            this.country = null;
        }
        if (this.iata.equals("")) {
            this.iata = null;
        }
        if (this.icao.equals("")) {
            this.icao = null;
        }
        if (this.dst.equals("")) {
            this.dst = null;
        }
        if (this.tzDatabaseTimezone.equals("")) {
            this.tzDatabaseTimezone = null;
        }
    }


}
