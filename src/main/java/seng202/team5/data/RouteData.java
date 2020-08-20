package seng202.team5.data;

public class RouteData implements Data {

    private Integer routeId;
    private String airline;
    private Integer airlineId;
    private String sourceAirport;
    private Integer sourceAirportId;
    private String destinationAirport;
    private Integer destinationAirportId;
    private String codeShare;
    private Integer stops;
    private String equipment;

    public Integer getRouteId() {
        return routeId;
    }

    public String getAirline() {
        return airline;
    }

    public Integer getAirlineId() {
        return airlineId;
    }

    public String getSourceAirport() {
        return sourceAirport;
    }

    public Integer getSourceAirportId() {
        return sourceAirportId;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public Integer getDestinationAirportId() {
        return destinationAirportId;
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


    public RouteData(Integer routeId, String airline, Integer airlineId, String sourceAirport, Integer sourceAirportId,
    String destinationAirport, Integer destinationAirportId, String codeShare, Integer stops, String equipment) {
        this.routeId = routeId;
        this.airline = airline;
        this.airlineId = airlineId;
        this.sourceAirport = sourceAirport;
        this.sourceAirportId = sourceAirportId;
        this.destinationAirport = destinationAirport;
        this.destinationAirportId = destinationAirportId;
        this.codeShare = codeShare;
        this.stops = stops;
        this.equipment = equipment;
    }



    @Override
    public int checkValues() {
        if (this.routeId == null) {
            return -1;
        } else if (this.airline == null || this.airline.length() != 2) {
            return -2;
        } else if (this.airlineId == null) {
            return -3;
        } else if (this.sourceAirport == null || (this.sourceAirport.length() != 3 && this.sourceAirport.length() != 4)) {
            return -4;
        } else if (this.sourceAirportId == null) {
            return -5;
        } else if (this.destinationAirport == null || (this.destinationAirport.length() != 3 && this.destinationAirport.length() != 4)) {
            return -6;
        } else if (this.destinationAirportId == null) {
            return -7;
        } else if (!(this.codeShare == null || this.codeShare.equals("Y"))) {
            return -8;
        } else if (this.stops == null) {
            return -9;
        } else if (this.equipment == null || this.equipment.length() != 3) {
            return -10;
        } else {
            return 1;
        }
    }

    @Override
    public void convertBlanksToNull() {
        if (this.codeShare == null) {
            //TODO determine codeShare null representation.
        }
    }
}
