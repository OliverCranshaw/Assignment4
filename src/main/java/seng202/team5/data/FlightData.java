package seng202.team5.data;

import seng202.team5.accessor.AirportAccessor;

import java.util.Arrays;
import java.util.List;


/**
 * FlightData
 *
 * A class used to represent Flight data for use in the modify data factory patterns.
 * Overrides checkValues() and convertBlanksToNull() from Data interface.
 */
public class FlightData implements Data {

    // Variables of FlightData
    private Integer flightId;
    private String location_type;
    private String location;
    private Integer altitude;
    private Double latitude;
    private Double longitude;
    private AirportAccessor airportAccessor;


    // Getters for all variables of FlightData
    public Integer getFlightId() { return flightId; }

    public String getLocationType() { return location_type; }

    public String getLocation() { return location; }

    public Integer getAltitude() { return altitude; }

    public Double getLatitude() { return latitude; }

    public Double getLongitude() { return longitude; }


    /**
     * Constructor for FlightData that takes in all parameters as their native types (types that
     * are used within this class).
     *
     * @param flightId The integer flight_id of the new flight entry, cannot be null.
     * @param location_type The location type of the flight entry location, one of "APT", "VOR", or "FIX", cannot be null.
     * @param location The location of the flight entry, cannot be null.
     * @param altitude The altitude of the plane at the time of the flight entry, in feet. A string, cannot be null.
     * @param latitude The latitude of the plane at the time of the flight entry, a string. Negative is South, positive is North, cannot be null.
     * @param longitude The longitude of the plane at the time of the flight entry, a string. Negative is West, positive is East, cannot be null.
     */
    public FlightData(Integer flightId, String location_type, String location, Integer altitude,
                      Double latitude, Double longitude) {
        this.flightId = flightId;
        this.location_type = location_type;
        this.location = location;
        this.altitude = altitude;
        this.latitude = latitude;
        this.longitude = longitude;

        airportAccessor = new AirportAccessor();
    }


    /**
     * Constructor for flightId that attempts to parse variables to their native type (type used in this class) from Strings
     *
     * @param flightId The integer flight_id of the new flight entry, cannot be null.
     * @param location_type The location type of the flight entry location, one of "APT", "VOR", or "FIX", cannot be null.
     * @param location The location of the flight entry, cannot be null.
     * @param altitude The altitude of the plane at the time of the flight entry, in feet. A string, cannot be null.
     * @param latitude The latitude of the plane at the time of the flight entry, a string. Negative is South, positive is North, cannot be null.
     * @param longitude The longitude of the plane at the time of the flight entry, a string. Negative is West, positive is East, cannot be null.
     */
    public FlightData(Integer flightId, String location_type, String location, String altitude,
                      String latitude, String longitude) {
        this.location_type = location_type;
        this.location = location;
        this.flightId = flightId;

        airportAccessor = new AirportAccessor();

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
    @Override
    public int checkValues() {
        if (this.flightId == null || this.flightId < 0) {
            // Ensures flightId cannot by negative or null
            return -2;
        } else if (this.location_type == null || !Arrays.asList("APT", "VOR", "FIX").contains(this.location_type)) {
            // Ensures location_type cannot be null and is one of "APT", "VOR", or "FIX"
            return -3;
        } else if (this.location == null || (this.location_type.equals("APT") && !airportAccessor.dataExists(this.location))) {
            // Ensures that the airport exists if the location type is APT, or otherwise just isn't null
            return -4;
        } else if (this.altitude == null) {
            // Ensures altitude is not null
            return -5;
        } else if (this.latitude == null) {
            // Ensures latitude is not null
            return -6;
        } else if (this.longitude == null) {
            // Ensures longitude is not null
            return -7;
        } else {
            // Returns valid check indicator
            return 1;
        }
    }


    /**
     * Checks every variable of FlightData against a list of possible null representations potentially used
     * in files, or returned by a gui empty field. If a null representation is found, replaces that value with null,
     */
    @Override
    public void convertBlanksToNull() {
        // List of null representations
        List<String> nullRepr = Arrays.asList("", "-", "\\N", "N/A");

        if (nullRepr.contains(this.location_type)) {
            this.location_type = null;
        }
        if (nullRepr.contains(this.location)) {
            this.location = null;
        }
    }
}
