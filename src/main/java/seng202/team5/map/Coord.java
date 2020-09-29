package seng202.team5.map;

import java.util.List;

/**
 * Coord is an object that represents single point on the world by its latitude and longitude
 * @author Nathan Smithies
 */
public final class Coord {
    /**
     * The latitude of this point
     */
    public final double latitude;
    /**
     * The longitude of this point
     */
    public final double longitude;

    /**
     * Constructor for Coord
     * Normalises inputs such that there is exactly one Coord representation for every point on Earth
     *
     * @param latitude The latitude in degrees, will be mapped to between [-90,90]
     * @param longitude The longitude in degrees, will be mapped to between (-180,180]
     */
    public Coord(double latitude, double longitude) {
        // Map latitude to (-180,180]
        latitude = ((latitude % 360) + 360) % 360;
        if (latitude > 180) latitude -= 360;

        // Flip longitude if abs(latitude) > 90
        if (latitude < -90) {
            latitude += 180;
            longitude += 180;
        } else if (latitude > 90) {
            latitude -= 180;
            longitude += 180;
        } else if (Math.abs(latitude) == 90) {
            // If at the north/south pole then longitude doesn't affect coordinate location
            longitude = 0;
        }

        // Map longitude to (-180,180]
        longitude = ((longitude % 360) + 360) % 360;
        if (longitude > 180) longitude -= 360;

        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Coord{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (obj instanceof Coord) {
            Coord other = (Coord)obj;
            return latitude == other.latitude && longitude == other.longitude;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return List.of(latitude, longitude).hashCode();
    }
}
