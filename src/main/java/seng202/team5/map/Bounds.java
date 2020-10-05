package seng202.team5.map;

import java.util.ArrayList;
import java.util.List;

/**
 * Bounds is an object that represents a rectangular region between two points on the world map.
 * It starts from the "southwest" point then covers all the space going northeast to the "northeast" point.
 */
public class Bounds {
    public final Coord southwest;
    public final Coord northeast;

    /**
     * Creates the smallest bounds object that contains the given coordinates.
     *
     * @param coordinates A list of at least 2 coordinates.
     * @return Bound that contains all the input coordinates.
     */
    public static Bounds fromCoordinateList(List<Coord> coordinates) {
        if (coordinates.size() < 2) {
            throw new RuntimeException("Coordinate list with too few coordinates");
        }

        double northern = Double.NEGATIVE_INFINITY;
        double southern = Double.POSITIVE_INFINITY;

        List<Double> longitudes = new ArrayList<>();
        for (Coord coord : coordinates) {
            northern = Math.max(northern, coord.latitude);
            southern = Math.min(southern, coord.latitude);

            longitudes.add(coord.longitude);
        }

        longitudes.sort(Double::compareTo); // Ordered from westernmost to easternmost
        longitudes.add(longitudes.get(0));

        // Finds the largest longitude gap and sets the eastern and western to exclude that longitude range
        double eastern = 0.0;
        double western = 0.0;
        double maximumGap = Double.NEGATIVE_INFINITY;
        for (int i = 0; i<longitudes.size() - 1; i++) {
            double longitudeA = longitudes.get(i) % 360;
            double longitudeB = longitudes.get(i + 1) % 360;

            double gap = (longitudeB - longitudeA + 720) % 360;
            if (gap > maximumGap) {
                eastern = longitudeA;
                western = longitudeB;

                maximumGap = gap;
            }
        }

        return new Bounds(southern, western, northern, eastern);
    }

    /**
     * The bounds constructor.
     *
     * @param southwest The southwestern most point within this bounds.
     * @param northeast The northeastern most point within this bounds.
     */
    public Bounds(Coord southwest, Coord northeast) {
        this.southwest = southwest;
        this.northeast = northeast;
    }

    /**
     * The bounds constructor.
     *
     * @param southern Southern most latitude of this bounds.
     * @param western Western most longitude of this bounds.
     * @param northern Northern most latitude of this bounds.
     * @param eastern Eastern most longitude of this bounds.
     */
    public Bounds(double southern, double western, double northern, double eastern) {
        this(new Coord(southern, western), new Coord(northern, eastern));
    }

    @Override
    public String toString() {
        return "Bounds{" +
                "southwest=" + southwest +
                ", northeast=" + northeast +
                '}';
    }
}
