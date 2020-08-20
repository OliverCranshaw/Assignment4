package seng202.team5.data;

public class FlightData implements Data {


    private Integer id;
    private Integer flightId;
    private String airline;
    private String airport;
    private Integer altitude;
    private Double latitude;
    private Double longitude;

    public FlightData(Integer id, Integer flightID, String airline, String airport, Integer altitude,
                      Double latitude, Double longitude) {
        this.flightId = flightID;
        this.airline = airline;
        this.airport = airport;
        this.altitude = altitude;
        this.latitude = latitude;
        this.longitude = longitude;
    }




    @Override
    public int checkValues() {
        //TODO
        return 0;
    }

    @Override
    public void convertBlanksToNull() {
        //TODO

    }
}
