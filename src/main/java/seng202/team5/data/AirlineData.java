package seng202.team5.data;

public class AirlineData implements Data {

    private Integer airlineID;
    private String name;
    private String alias;
    private String iata;
    private String icao;
    private String callsign;
    private String country;
    private String active;


    public Integer getAirlineID() {
        return airlineID;
    }

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



    public AirlineData(Integer airlineID, String name, String alias, String iata,
                       String icao, String callsign, String country, String active) {

        this.airlineID = airlineID;
        this.name = name;
        this.alias = alias;
        this.iata = iata;
        this.icao = icao;
        this.callsign = callsign;
        this.country = country;
        this.active = active;
    }

    /**
     * Checks that the variables of the Airline data are of appropriate form.
     * @return integer (negative if error, positive if success).
     */

    @Override
    public int checkValues() {
        if (this.airlineID == null) {
            return -1;
        } else if (this.name == null) {
            return -2;
        } else if (!(this.iata == null || this.iata.length() == 2)) {
            return -3;
        } else if (!(this.icao == null || this.icao.length() == 3)) {
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
        if (this.alias == null) {
            this.alias = "\\N";
        }
        if (this.iata == null) {
            this.iata = "\\N";
        }
        if (this.icao == null) {
            this.icao = "N/A";
        }
        if (this.callsign == null) {
            this.callsign = "";
        }
        if (this.country == null) {
            this.country = "";
        }
    }




}
