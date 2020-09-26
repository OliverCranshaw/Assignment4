package seng202.team5.map;

public class Distance {

    public static double distance(Coord coord1, Coord coord2) {

        double coord1Long = Math.toRadians(coord1.longitude);
        double coord1Lat = Math.toRadians(coord1.latitude);
        double coord2Long = Math.toRadians(coord2.longitude);
        double coord2Lat = Math.toRadians(coord2.latitude);

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

        return distance;
    }

}
