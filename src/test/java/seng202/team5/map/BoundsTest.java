package seng202.team5.map;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class BoundsTest {
    // Maximum allowable difference
    private static final double EPSILON = 0.0000001;

    @Test
    public void testFromCoordinateListLatitude() {
        Random random = new Random(42);

        for (int i = 0; i<100; i++) {
            List<Coord> coordList = new ArrayList<>();
            double minLatitude;
            double maxLatitude;

            // Add starting point
            {
                Coord initial = new Coord(random.nextDouble()*360, random.nextDouble()*360);
                coordList.add(initial);

                minLatitude = initial.latitude;
                maxLatitude = initial.latitude;
            }

            for (int j = 0; j<30; j++) {
                // Add new point
                Coord coord = new Coord(random.nextDouble()*360, random.nextDouble()*360);
                coordList.add(coord);

                minLatitude = Math.min(minLatitude, coord.latitude);
                maxLatitude = Math.max(maxLatitude, coord.latitude);


                // Test that latitude bounds are as expected
                Bounds bounds = Bounds.fromCoordinateList(coordList);
                assertEquals(minLatitude, bounds.southwest.latitude, EPSILON);
                assertEquals(maxLatitude, bounds.northeast.latitude, EPSILON);
            }
        }
    }

    @Test
    public void testFromCoordinateListLongitude() {
        Random random = new Random(42);

        for (int i = 0; i<100; i++) {
            List<Coord> coordList = new ArrayList<>();

            Coord initial = new Coord(0, random.nextDouble()*360);
            coordList.add(initial);

            for (int j = 1; j<30; j++) {
                Coord coord = new Coord(0, initial.longitude + j * 10);
                coordList.add(coord);

                Bounds bounds = Bounds.fromCoordinateList(coordList);

                assertEquals(initial.longitude, bounds.southwest.longitude, EPSILON);
                assertEquals(coord.longitude, bounds.northeast.longitude, EPSILON);
            }
        }
    }
}
