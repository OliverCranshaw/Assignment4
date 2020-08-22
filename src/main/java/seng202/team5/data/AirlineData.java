package seng202.team5.data;

import java.util.Arrays;
import java.util.List;

public class AirlineData implements Data {

    private String name;
    private String alias;
    private String iata;
    private String icao;
    private String callsign;
    private String country;
    private String active;


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
     * Checks that the variables of the Airline data are of appropriate form and domain (if needed).
     * @return integer (negative if error, positive if success).
     */

    @Override
    public int checkValues() {
        if (this.name == null) {
            return -2;
        } else if (this.iata.length() != 2) {
            return -3;
        } else if (this.icao.length() != 3){
            return -4;
        } else if (this.active == null || !(this.active.equals("Y") || this.active.equals("N"))) {
            return -5;
        } else {
            return 1;
        }
    }

    /**
     Converts the various null representations used to the java null.
     */
    @Override
    public void convertBlanksToNull() {
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
