package seng202.team5.data;

public class AirportData implements Data {

    private Integer airportId;
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



    public Integer getAirportId() {
        return airportId;
    }

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


    public AirportData(Integer airportId, String airportName, String city, String country, String iata,
                       String icao, Double latitude, Double longitude, Integer altitude, Integer timezone, String dst, String tzDatabaseTimezone) {

        this.airportId = airportId;
        this.airportName = airportName;
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


    @Override
    public int checkValues() {
        if (this.airportId == null) {
            return -1;
        } else if (this.airportName == null) {
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
        if (this.iata == null) {
            this.iata = "";
        }
        if (this.icao == null) {
            this.icao = "";
        }

    }


}
