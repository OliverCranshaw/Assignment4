package seng202.team5.map;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class CoordTest {
    // Maximum allowable difference
    private static final double EPSILON = 0.0000001;

    @Test
    public void testConstructor() {
        // Simple no wrapping test
        {
            Coord coord = new Coord(0, 0);
            assertEquals(0, coord.latitude, EPSILON);
            assertEquals(0, coord.longitude, EPSILON);
        }

        // Test longitude ranges
        {
            Coord coord = new Coord(0, 180);
            assertEquals(0, coord.latitude, EPSILON);
            assertEquals(180, coord.longitude, EPSILON);
        }
        {
            Coord coord = new Coord(0, -180);
            assertEquals(0, coord.latitude, EPSILON);
            assertEquals(180, coord.longitude, EPSILON);
        }
        {
            Coord coord = new Coord(0, 360);
            assertEquals(0, coord.latitude, EPSILON);
            assertEquals(0, coord.longitude, EPSILON);
        }

        // Test latitude pole behaviour
        {
            Coord coord = new Coord(90, 45);
            assertEquals(90, coord.latitude, EPSILON);
            assertEquals(0, coord.longitude, EPSILON);
        }
        {
            Coord coord = new Coord(-90, 45);
            assertEquals(-90, coord.latitude, EPSILON);
            assertEquals(0, coord.longitude, EPSILON);
        }

        // Test latitude flipping
        {
            Coord coord = new Coord(180, 0);
            assertEquals(0, coord.latitude, EPSILON);
            assertEquals(180, coord.longitude, EPSILON);
        }
        {
            Coord coord = new Coord(360, 0);
            assertEquals(0, coord.latitude, EPSILON);
            assertEquals(0, coord.longitude, EPSILON);
        }

        // Test latitude and longitude get wrapped around correctly
        Random random = new Random(42);
        for (int i = 0; i<100; i++) {
            double latitude = random.nextDouble() * 360;
            double longitude = random.nextDouble() * 360;

            Coord test = new Coord(latitude, longitude);
            Coord stillTest = new Coord(latitude + 360, longitude - 360);

            assertEquals(test.latitude, stillTest.latitude, EPSILON);
            assertEquals(test.longitude, stillTest.longitude, EPSILON);
        }
    }


    @Test
    public void testEquals() {
        Random random = new Random(42);

        assertNotEquals("Hello world", new Coord(0,0));

        for (int i = 0; i<100; i++) {
            double latitude = random.nextDouble() * 360;
            double longitude = random.nextDouble() * 360;

            Coord test = new Coord(latitude, longitude);
            Coord stillTest = new Coord(latitude, longitude);

            assertEquals(test, test);
            assertEquals(test, stillTest);
        }
    }


    @Test
    public void testHashCode() {
        Random random = new Random(42);

        for (int i = 0; i<1000; i++) {
            double latitude = random.nextDouble() * 360;
            double longitude = random.nextDouble() * 360;

            assertEquals(new Coord(latitude, longitude).hashCode(), new Coord(latitude, longitude).hashCode());
        }
    }
}
