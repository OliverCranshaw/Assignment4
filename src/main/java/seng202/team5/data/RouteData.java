package seng202.team5.data;

import java.util.Arrays;
import java.util.List;

public class RouteData implements Data {

    private String airline;
    private String sourceAirport;
    private String destinationAirport;
    private String codeShare;
    private Integer stops;
    private String equipment;

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


    public RouteData(String airline,String sourceAirport, String destinationAirport, String codeShare, Integer stops,
                     String equipment) {
        this.airline = airline;
        this.sourceAirport = sourceAirport;
        this.destinationAirport = destinationAirport;
        this.codeShare = codeShare;
        this.stops = stops;
        this.equipment = equipment;
    }

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
            System.out.println("Route Data (stops): " + e);
        }
    }



    @Override
    public int checkValues() {
        if (this.airline == null || (this.airline.length() != 2 && this.airline.length() != 3)) {
            return -2;
        } else if (this.sourceAirport == null || (this.sourceAirport.length() != 3 && this.sourceAirport.length() != 4)) {
            return -3;
        } else if (this.destinationAirport == null || (this.destinationAirport.length() != 3 && this.destinationAirport.length() != 4)) {
            return -4;
        } else if (!(this.codeShare == null || this.codeShare.equals("Y"))) {
            return -5;
        } else if (this.stops == null) {
            return -6;
        } else if (this.equipment == null) {
            return -7;
        } else {
            return 1;
        }
    }

    @Override
    public void convertBlanksToNull() {
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
