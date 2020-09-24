package seng202.team5.map;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Bounds {
    public final Coord southwest;
    public final Coord northeast;

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

    public Bounds(Coord southwest, Coord northeast) {
        this.southwest = southwest;
        this.northeast = northeast;
    }

    public Bounds(double southern, double western, double northern, double eastern) {
        this(new Coord(southern, western), new Coord(northern, eastern));
    }
}
