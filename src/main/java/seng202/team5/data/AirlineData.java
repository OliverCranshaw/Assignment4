package seng202.team5.data;

import java.util.ArrayList;

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

    public String getCountry() {
        return country;
    }

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


    public ArrayList<String> getValues() {
        ArrayList<String> toReturn = new ArrayList<>();
        toReturn.add(this.name);
        toReturn.add(this.alias);
        toReturn.add(this.iata);
        toReturn.add(this.icao);
        toReturn.add(this.callsign);
        toReturn.add(this.country);
        toReturn.add(this.active);
        return toReturn;
    }


    /**
     * Checks that the variables of the Airline data are of appropriate form.
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
     * Converts the null variables of the AirlineData class (if any) to their
     * appropriate null representation for the database.
     */
    @Override
    public void convertBlanksToNull() {
        if (this.name.equals("")) {
            this.name = null;
        }
        if (this.alias.equals("") || this.alias.equals("\\N")) {
            this.alias = null;
        }
        if (this.iata.equals("") || this.iata.equals("\\N")) {
            this.iata = null;
        }
        if (this.icao.equals("") || this.icao.equals("N/A")) {
            this.icao = null;
        }
        if (this.callsign.equals("")) {
            this.callsign = null;
        }
        if (this.country.equals("")) {
            this.country = null;
        }
        if (this.active.equals("")) {
            this.active = null;
        }
    }




}
