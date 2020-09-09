package seng202.team5.data;

import java.util.Arrays;
import java.util.List;


/**
 * RouteData
 *
 * A class used to represent Route data for use in the modify data factory patterns.
 * Overrides checkValues() and convertBlanksToNull() from Data interface.
 *
 * @author Jack Ryan
 */
public class RouteData implements Data {

    // Variables of RouteData
    private String airline;
    private String sourceAirport;
    private String destinationAirport;
    private String codeShare;
    private Integer stops;
    private String equipment;

    // Getters for all variables of RouteData
    public String getAirline() {
        return airline;
    }

    public String getSourceAirport() {
        return sourceAirport;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public String getCodeShare() {
        return codeShare;
    }

    public Integer getStops() {
        return stops;
    }

    public String getEquipment() {
        return equipment;
    }


    /**
     * Constructor for RouteData that takes in all parameters as their native types (types that
     * are used within this class).
     *
     * @param airline The 2-letter IATA code or 3-letter ICAO code of the airline, cannot be null.
     * @param sourceAirport The 3-letter IATA code or 4-letter ICAO code of the source airport, cannot be null.
     * @param destinationAirport The 3-letter IATA code or 4-letter ICAO code of the destination airport, cannot be null.
     * @param codeShare "Y" if the flight is operated by a different airline, otherwise "N". Cannot be null.
     * @param stops The number of stops for the flight, 0 if it is direct. A string, cannot be null.
     * @param equipment 3-letter codes for plane types(s) commonly used for this flight, separated by spaces. Cannot be null.
     */
    public RouteData(String airline,String sourceAirport, String destinationAirport, String codeShare, Integer stops,
                     String equipment) {
        this.airline = airline;
        this.sourceAirport = sourceAirport;
        this.destinationAirport = destinationAirport;
        this.codeShare = codeShare;
        this.stops = stops;
        this.equipment = equipment;
    }


    /**
     * Constructor for RouteData that takes in all parameters as strings and attempts to parse certain variables to
     * either integers.
     *
     * @param airline The 2-letter IATA code or 3-letter ICAO code of the airline, cannot be null.
     * @param sourceAirport The 3-letter IATA code or 4-letter ICAO code of the source airport, cannot be null.
     * @param destinationAirport The 3-letter IATA code or 4-letter ICAO code of the destination airport, cannot be null.
     * @param codeShare "Y" if the flight is operated by a different airline, otherwise "N". Cannot be null.
     * @param stops The number of stops for the flight, 0 if it is direct. A string, cannot be null.
     * @param equipment 3-letter codes for plane types(s) commonly used for this flight, separated by spaces. Cannot be null.
     */
    public RouteData(String airline,String sourceAirport, String destinationAirport,String codeShare, String stops,
                     String equipment) {
        this.airline = airline;
        this.sourceAirport = sourceAirport;
        this.destinationAirport = destinationAirport;
        this.codeShare = codeShare;
        this.equipment = equipment;

        // Parsing stops to integer
        try {
            this.stops = Integer.parseInt(stops);
        } catch(NumberFormatException e) {
            // Error message to console
            System.out.println("Route Data (stops): " + e);
        }
    }


    /**
     * Checks that the values of the variables of RouteData are in appropriate forms,
     * Checking for null variables, size of variables and ensuring variables are within a valid domain.
     *
     * @return int 1 if the check passes, otherwise a negative value that corresponds to a certain variable.
     */
    @Override
    public int checkValues() {
        if (this.airline == null || (this.airline.length() != 2 && this.airline.length() != 3)) {
            // Ensures the airline is not null and is either an appropriate iata or icao length (2,3)
            return -2;
        } else if (this.sourceAirport == null || (this.sourceAirport.length() != 3 && this.sourceAirport.length() != 4)) {
            // Ensures the sourceAirport is not null and is either an appropriate iata or icao length (3,4)
            return -3;
        } else if (this.destinationAirport == null || (this.destinationAirport.length() != 3 && this.destinationAirport.length() != 4)) {
            // Ensures the destinationAirport is not null and is either an appropriate iata or icao length (3,4)
            return -4;
        } else if (this.codeShare == null || !this.codeShare.equals("Y")) {
            // Ensures the codeShare is not null and is within an appropriate domain
            return -5;
        } else if (this.stops == null || this.stops < 0) {
            // Ensures the stops is not null
            return -6;
        } else if (this.equipment == null) {
            // Ensures the equipment is not null
            return -7;
        } else {
            // Returns a valid check indicator
            return 1;
        }
    }


    /**
     * Checks every variable of RouteData against a list of possible null representations potentially used
     * in files, or returned by a gui empty field. If a null representation is found, replaces that value with null,
     */
    @Override
    public void convertBlanksToNull() {
        // List of possible null representations
        List<String> nullRepr = Arrays.asList("", "-", "\\N", "N/A");

        if (nullRepr.contains(this.airline)) {
            this.airline = null;
        }
        if (nullRepr.contains(this.sourceAirport)) {
            this.sourceAirport = null;
        }
        if (nullRepr.contains(this.destinationAirport)) {
            this.destinationAirport = null;
        }
        if (nullRepr.contains(this.codeShare)) {
            this.codeShare = null;
        }
        if (nullRepr.contains(this.equipment)) {
            this.equipment = null;
        }
    }
}
