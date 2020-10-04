package seng202.team5.data;

import java.util.Arrays;
import java.util.List;

/**
 * FlightData
 *
 * A class used to represent Flight data for use in the modify data factory patterns.
 * Implements Data interface.
 */
public class FlightData implements Data {

    // Variables of FlightData
    private final Integer flightID;
    private String locationType;
    private String location;
    private Integer altitude;
    private Double latitude;
    private Double longitude;

    // Getters for all variables of FlightData
    public Integer getFlightId() { return flightID; }

    public String getLocationType() { return locationType; }

    public String getLocation() { return location; }

    public Integer getAltitude() { return altitude; }

    public Double getLatitude() { return latitude; }

    public Double getLongitude() { return longitude; }

    /**
     * Constructor for FlightData that takes in all parameters as their native types (types that
     * are used within this class).
     *
     * @param flightID The integer flight_id of the new flight entry, cannot be null.
     * @param locationType The location type of the flight entry location, one of "APT", "VOR", or "FIX", cannot be null.
     * @param location The location of the flight entry, cannot be null.
     * @param altitude The altitude of the plane at the time of the flight entry, in feet. A string, cannot be null.
     * @param latitude The latitude of the plane at the time of the flight entry, a string. Negative is South, positive is North, cannot be null.
     * @param longitude The longitude of the plane at the time of the flight entry, a string. Negative is West, positive is East, cannot be null.
     */
    public FlightData(Integer flightID, String locationType, String location, Integer altitude,
                      Double latitude, Double longitude) {
        this.flightID = flightID;
        this.locationType = locationType;
        this.location = location;
        this.altitude = altitude;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    /**
     * Constructor for flightID that attempts to parse variables to their native type (type used in this class) from Strings
     *
     * @param flightID The integer flight_id of the new flight entry, cannot be null.
     * @param locationType The location type of the flight entry location, one of "APT", "VOR", or "FIX", cannot be null.
     * @param location The location of the flight entry, cannot be null.
     * @param altitude The altitude of the plane at the time of the flight entry, in feet. A string, cannot be null.
     * @param latitude The latitude of the plane at the time of the flight entry, a string. Negative is South, positive is North, cannot be null.
     * @param longitude The longitude of the plane at the time of the flight entry, a string. Negative is West, positive is East, cannot be null.
     */
    public FlightData(Integer flightID, String locationType, String location, String altitude,
                      String latitude, String longitude) {
        this.locationType = locationType;
        this.location = location;
        this.flightID = flightID;

        // Parsing altitude to integer
        try {
            this.altitude = Integer.parseInt(altitude);
        } catch(NumberFormatException e) {
            // Error message to console
            System.out.println("Flight Data (altitude): " + e);
        }

        // Parsing latitude to double
        try {
            this.latitude = Double.parseDouble(latitude);
        } catch(NumberFormatException e) {
            // Error message to console
            System.out.println("Flight Data (latitude): " + e);
        }

        // Parsing longitude to double
        try {
            this.longitude = Double.parseDouble(longitude);
        } catch(NumberFormatException e) {
            // Error message to console
            System.out.println("Flight Data (longitude): " + e);
        }
    }

    /**
     * Checks that the values of the variables of FlightData are in appropriate form,
     * Checking for null variables, size of variables and ensuring vairables are in a valid domain.
     *
     * @return int 1 if the check passes, otherwise a negative value that corresponds to a certain variable.
     */
    public int checkValues() {
        if (flightID == null || flightID < 0) {
            // Ensures flightID cannot by negative or null
            return -2;
        } else if (locationType == null || !Arrays.asList("APT", "VOR", "FIX").contains(locationType)) {
            // Ensures locationType cannot be null and is one of "APT", "VOR", or "FIX"
            return -3;
        } else if (location == null || !(location.matches("^[A-Z]+$"))) {
            // Ensures that the airport exists if the location type is APT, or otherwise just isn't null
            return -4;
        } else if (altitude == null) {
            // Ensures altitude is not null
            return -5;
        } else if (latitude == null) {
            // Ensures latitude is not null
            return -6;
        } else if (longitude == null) {
            // Ensures longitude is not null
            return -7;
        } else {
            // Returns valid check indicator
            return 1;
        }
    }

    /**
     * Checks every variable of FlightData against a list of possible null representations potentially used
     * in files, or returned by a gui empty field. If a null representation is found, replaces that value with null.
     */
    public void convertBlanksToNull() {
        // List of null representations
        List<String> nullRepr = Arrays.asList("", "-", "\\N", "N/A");

        if (nullRepr.contains(locationType)) {
            locationType = null;
        }
        if (nullRepr.contains(location)) {
            location = null;
        }
    }
}
