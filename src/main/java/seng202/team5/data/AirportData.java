package seng202.team5.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * AirportData
 *
 * A class used to represent Airport data for use in the modify data factory patterns.
 * Implements from Data interface.
 */
public class AirportData implements Data {

    // Variables of AirportData
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

    /**
     * Constructor for AirportData that takes in all parameters as their native types (types that
     * are used within this class).
     *
     * @param name The name of the airport, cannot be null.* @param city The city of the airport, cannot be null.
     * @param country The country of the airport, cannot be null.
     * @param iata The 3-letter IATA code of the airport, must be unique, may be null if not known/assigned.
     * @param icao The 4-letter ICAO code of the airport, must be unique, may be null if not known/assigned.
     * @param latitude The latitude of the airport, a string. Negative is South, positive is North, cannot be null.
     * @param longitude The longitude of the airport, a string. Negative is West, positive is East, cannot be null.
     * @param altitude The altitude of the airport in feet, a string. Cannot be null.
     * @param timezone The hours offset from UTC, a string, cannot be null.
     * @param dst The daylight savings time of the airport, one of E (Europe), A (US/Canada), S (South America), O (Australia), Z (New Zealand), N (None) or U (Unknown), cannot be null.
     * @param tzDatabaseTimezone Timezone in "tz" (Olson) format, i.e. Country/Region. Cannot be null.
     */
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

    /**
     * Constructor for AirportData that takes in all parameters as strings and attempts to parse certain variables
     * to either integers or doubles.
     *
     * @param name The name of the airport, cannot be null.
     * @param city The city of the airport, cannot be null.
     * @param country The country of the airport, cannot be null.
     * @param iata The 3-letter IATA code of the airport, must be unique, may be null if not known/assigned.
     * @param icao The 4-letter ICAO code of the airport, must be unique, may be null if not known/assigned.
     * @param latitude The latitude of the airport, a string. Negative is South, positive is North, cannot be null.
     * @param longitude The longitude of the airport, a string. Negative is West, positive is East, cannot be null.
     * @param altitude The altitude of the airport in feet, a string. Cannot be null.
     * @param timezone The hours offset from UTC, a string, cannot be null.
     * @param dst The daylight savings time of the airport, one of E (Europe), A (US/Canada), S (South America), O (Australia), Z (New Zealand), N (None) or U (Unknown), cannot be null.
     * @param tzDatabaseTimezone Timezone in "tz" (Olson) format, i.e. Country/Region. Cannot be null.
     */
    public AirportData(String name, String city, String country, String iata, String icao, String latitude,
                       String longitude, String altitude, String timezone, String dst, String tzDatabaseTimezone) {
        this.airportName = name;
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
            // Error message to console
            System.out.println("Airport Data (latitude): " + e);
        }
        // Parsing Longitude to a double
        try {
            this.longitude = Double.parseDouble(longitude);
        } catch(NumberFormatException e) {
            // Error message to console
            System.out.println("Airport Data (longitude): " + e);
        }
        // Parsing Altitude to a integer
        try {
            this.altitude = Integer.parseInt(altitude);
        } catch(NumberFormatException e) {
            // Error message to console
            System.out.println("Airport Data (altitude): " + e);
        }
        // Parsing Timezone to a float
        try {
            this.timezone = Float.parseFloat(timezone);
        } catch(NumberFormatException e) {
            // Error message to console
            System.out.println("Airport Data (timezone): " + e);
        }
    }

    /**
     * Convenience constructor for AirportData
     *
     * @param resultSet ResultSet object from AirportService
     * @throws SQLException Caused by ResultSet interactions
     */
    public AirportData(ResultSet resultSet) throws SQLException {
        this(
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getString(5),
                resultSet.getString(6),
                resultSet.getDouble(7),
                resultSet.getDouble(8),
                resultSet.getInt(9),
                resultSet.getFloat(10),
                resultSet.getString(11),
                resultSet.getString(12)
        );
    }

    /**
     * Checks that the values of the variables of AirportData are in appropriate form,
     * Checking for null variables, size of variables and ensuring vairables are in a valid domain.
     *
     * @return int 1 if the check passes, otherwise a negative value that corresponds to a certain variable.
     */
    public int checkValues() {
        // List of domain of valid dst values
        List<String> dstValues = Arrays.asList("E", "A", "S", "O", "Z", "N", "U");

        if (airportName == null) {
            // Ensures the name of the airport is not null
            return -2;
        } else if (city == null) {
            // Ensures the city of the airport is not null
            return -3;
        } else if (country == null) {
            // Ensures the country of the airport is not null
            return -4;
        } else if (!((iata == null) || (iata.matches("^[A-Z0-9]{3}$")))) {
            // Ensures the iata of the airport is not null and is of the correct length (3)
            return -5;
        } else if (!((icao == null) || (icao.matches("^[A-Z0-9]{4}$")))) {
            // Ensures the icao of the airport is not null and is of the correct length (4)
            return -6;
        } else if (latitude == null) {
            // Ensures the latitude of the airport is not null
            return -7;
        } else if (longitude == null) {
            // Ensures the longitude of the airport is not null
            return -8;
        } else if (altitude == null) {
            // Ensures the altitude of the airport is not null
            return -9;
        } else if (timezone == null) {
            // Ensures the timezone of the airport is not null
            return -10;
        } else if (dst == null || !(dstValues.contains(dst)) ) {
            // Ensures the dst of the airport is not null and that the dst is within the expected domain
            return -11;
        } else if (tzDatabaseTimezone == null || !(tzDatabaseTimezone.matches("^[A-Za-z]+((\\s|_)?[A-Za-z]+)+/[A-Za-z]+((\\s|_)?[A-Za-z]+)*$"))) {
            // Ensures the tzDatabaseTimezone of the airport is not null
            return -12;
        } else {
            // Returns a valid check indicator
            return 1;
        }
    }

    /**
     * Checks every variable of AirportData against a list of possible null representations potentially used
     * in files, or returned by a gui empty field. If a null representation is found, replaces that value with null,
     */
    public void convertBlanksToNull() {
        // List of possible null representations
        List<String> nullRepr = Arrays.asList("", "-", "\\N", "N/A");

        if (nullRepr.contains(airportName)) {
            airportName = null;
        }
        if (nullRepr.contains(city)) {
            city = null;
        }
        if (nullRepr.contains(country)) {
            country = null;
        }
        if (nullRepr.contains(iata)) {
            iata = null;
        }
        if (nullRepr.contains(icao)) {
            icao = null;
        }
        if (nullRepr.contains(dst)) {
            dst = null;
        }
        if (nullRepr.contains(tzDatabaseTimezone)) {
            tzDatabaseTimezone = null;
        }
    }

    // Getters for all of the variables of AirportData
    public String getAirportName() {
        return airportName;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getIATA() {
        return iata;
    }

    public String getICAO() {
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

    public String getDST() {
        return dst;
    }

    public String getTZDatabaseTimezone() {
        return tzDatabaseTimezone;
    }
}
