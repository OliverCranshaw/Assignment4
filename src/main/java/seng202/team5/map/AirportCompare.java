package seng202.team5.map;

public class AirportCompare {

    private static Coord location1;
    private static Coord location2;

    public AirportCompare() {
        this.location1 = null;
        this.location2 = null;
    }

    public void setLocations(Coord location1) {
        if (this.location1 != null) {
            this.location2 = this.location1;
        }

        this.location1 = location1;

    }

    public Coord getLocation1() {
        return location1;
    }

    public Coord getLocation2() {
        return location2;
    }

    public double calculateDistance() {

        double coord1Long = Math.toRadians(location1.longitude);
        double coord1Lat = Math.toRadians(location1.latitude);
        double coord2Long = Math.toRadians(location2.longitude);
        double coord2Lat = Math.toRadians(location2.latitude);

        // Haversine formula
        double deltaLong = coord2Long - coord1Long;
        double deltaLat = coord2Lat - coord1Lat;
        double value1 = Math.pow(Math.sin(deltaLat / 2), 2)
                + Math.cos(coord1Lat) * Math.cos(coord2Lat)
                * Math.pow(Math.sin(deltaLong / 2),2);

        double value2 = 2 * Math.asin(Math.sqrt(value1));

        // Radius of earth in kilometers
        double radiusEarth = 6371;

        // calculate the distance
        double distance = value2 * radiusEarth;

        return Math.round(distance * 100.0) / 100.0;
    }

}
