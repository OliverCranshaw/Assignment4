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
     *
     * @param latitude The latitude for this coord
     * @param longitude The longitude for this coord
     */
    public Coord(double latitude, double longitude) {
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
