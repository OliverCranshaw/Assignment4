package seng202.team5.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;


/**
 * AirlineData
 *
 * A class used to represent Airline data for use in the modify data factory patterns.
 * Overrides checkValues() and convertBlanksToNull() from Data interface.
 *
 * @author Jack Ryan
 */
public class AirlineData implements Data {

    // Variables of AirlineData
    private String name;
    private String alias;
    private String iata;
    private String icao;
    private String callsign;
    private String country;
    private String active;

    // Getters for all of the variables of AirlineData
    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public String getIata() {
        return iata;
    }

    public String getIcao() {
        return icao;
    }

    public String getCallsign() {
        return callsign;
    }

    public String getCountry() { return country; }

    public String getActive() {
        return active;
    }


    /**
     * Constructor for AirlineData.
     *
     * @param name The name of the airline, cannot be null.
     * @param alias The alias of the airline.
     * @param iata The 2-letter IATA code of the airline, must be unique, may be null if not known/assigned.
     * @param icao The 3-letter ICAO code of the airline, must be unique, may be null if not known/assigned.
     * @param callsign The callsign of the airline.
     * @param country The country of the airline.
     * @param active "Y" if the airline is or has until recently been operational, "N" if it is defunct, cannot be null.
     */
    public AirlineData(String name, String alias, String iata,
                       String icao, String callsign, String country, String active) {
        this.name = name;
        this.alias = alias;
        this.iata = iata;
        this.icao = icao;
        this.callsign = callsign;
        this.country = country;
        this.active = active;
    }

    /**
     * Convenience constructor for AirlineData
     *
     * @param resultSet ResultSet object from AirlineService
     * @throws SQLException
     */
    public AirlineData(ResultSet resultSet) throws SQLException {
        this(
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getString(5),
                resultSet.getString(6),
                resultSet.getString(7),
                resultSet.getString(8)
        );
    }

    /**
     * Checks that the values of the variables of AirlineData are in appropriate form,
     * Checking for null variables, size of variables and ensuring variables in a valid domain.
     *
     * @return int is 1 if the check passes, otherwise a negative value that corresponds to a certain variable.
     */
    @Override
    public int checkValues() {
        if (this.name == null) {
            // Ensures the name of the Airline is not null
            return -2;
        } else if (this.iata != null && !(this.iata.matches("^[A-Z0-9]{2}$"))) {
            // Checks the iata is of the correct size (2)
            return -3;
        } else if (this.icao != null && !(this.icao.matches("^[A-Z0-9]{3}$"))) {
            // Checks the icao is of the correct size (3)
            return -4;
        } else if (this.active == null || !(this.active.equals("Y") || this.active.equals("N"))) {
            // Checks the active variable is not null or within a specific domain
            return -5;
        } else {
            // Returns a valid check indicator
            return 1;
        }
    }

    /**
     * Checks every variable of AirlineData against a list of possible null representations potentially used
     * in files, or returned by a gui empty field. If a null representation is found, replaces that value with null,
     */
    @Override
    public void convertBlanksToNull() {
        // List of possible null representations
        List<String> nullRepr = Arrays.asList("", "-", "\\N", "N/A");

        if (nullRepr.contains(this.name)) {
            this.name = null;
        }
        if (nullRepr.contains(this.alias)) {
            this.alias = null;
        }
        if (nullRepr.contains(this.iata)) {
            this.iata = null;
        }
        if (nullRepr.contains(this.icao)) {
            this.icao = null;
        }
        if (nullRepr.contains(this.callsign)) {
            this.callsign = null;
        }
        if (nullRepr.contains(this.country)) {
            this.country = null;
        }
        if (nullRepr.contains(this.active)) {
            this.active = null;
        }
    }

}
