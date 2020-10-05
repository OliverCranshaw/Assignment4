package seng202.team5.data;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RouteDataTest {

    @Test
    public void testValidStringConstructor() {
        String airline = "323";
        String sourceAirport = "4444";
        String destinationAirport = "333";
        String codeshare = "Y";
        String stops = "4";
        String equipment = "HJA";

        RouteData test = new RouteData(airline, sourceAirport, destinationAirport, codeshare, stops, equipment);

        assertEquals((Integer) 4, test.getStops());
    }


    @Test
    public void testConvertBlanksToNull() {
        String airline = "323";
        String sourceAirport = "";
        String destinationAirport = "333";
        String codeshare = "Y";
        String stops = "4";
        String equipment = "\\N";

        RouteData test = new RouteData(airline, sourceAirport, destinationAirport, codeshare, stops, equipment);
        test.convertBlanksToNull();

        assertNull(test.getEquipment());
        assertNull(test.getSourceAirport());
    }


    @Test
    public void testCheckValidValues() {
        String airline = "323";
        String sourceAirport = "FFF";
        String destinationAirport = "333";
        String codeshare = "Y";
        String stops = "4";
        String equipment = "FFS";

        RouteData test = new RouteData(airline, sourceAirport, destinationAirport, codeshare, stops, equipment);

        int validityValue = test.checkValues();

        assertEquals(1, validityValue);
    }


    @Test
    public void testCheckInvalidValues() {
        String airline = "323";
        String sourceAirport = "FFO";
        String destinationAirport = "333";
        String codeshare = "F";
        String stops = "4";
        String equipment = "FSA";

        RouteData test = new RouteData(airline, sourceAirport, destinationAirport, codeshare, stops, equipment);

        int validityValue = test.checkValues();

        assertEquals(-5, validityValue);
    }
}
