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
        this.id = id;
        this.flightId = flightID;
        this.airline = airline;
        this.airport = airport;
        this.altitude = altitude;
        this.latitude = latitude;
        this.longitude = longitude;
    }




    @Override
    public int checkValues() {
        if (this.id == null) {
            return -1;
        } else if (this.flightId == null) {
            return -2;
        } else if (this.airline == null || this.airline.length() != 4) {
            return -3;
        } else if (this.airport == null || this.airport.length() != 3) {
            return -4;
        } else if (this.altitude == null) {
            return -5;
        } else if (this.latitude == null) {
            return -6;
        } else if (this.longitude == null) {
            return -7;
        } else {
            return 1;
        }
    }

    @Override
    public void convertBlanksToNull() {

    }
}
