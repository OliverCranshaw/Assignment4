package seng202.team5.data;

import org.junit.Test;
import static org.junit.Assert.*;

public class FlightDataTest {

    @Test
    public void testValidStringConstructor() {
        Integer flightID = 545;
        String locationType = "Lufthansa";
        String location = "aflsf";
        String altitude = "4343";
        String latitude = "4223.2";
        String longitude = "434.6";

        FlightData test = new FlightData(flightID, locationType, location, altitude, latitude, longitude);

        assertEquals((Double) 4223.2, test.getLatitude());
        assertEquals((Integer) 4343, test.getAltitude());
    }


    @Test
    public void testConvertBlanksToNull() {
        Integer flightID = 545;
        String locationType = "\\N";
        String location = "-";
        String altitude = "4343";
        String latitude = "4223.2";
        String longitude = "434.6";

        FlightData test = new FlightData(flightID, locationType, location, altitude, latitude, longitude);

        test.convertBlanksToNull();

        assertNull(test.getLocationType());
        assertNull(test.getLocation());
    }


    @Test
    public void testCheckValidValues() {
        Integer flightID = 545;
        String locationType = "VOR";
        String location = "FFF";
        String altitude = "4343";
        String latitude = "4223.2";
        String longitude = "434.6";

        FlightData test = new FlightData(flightID, locationType, location, altitude, latitude, longitude);

        int validityValue = test.checkValues();
        assertEquals(1, validityValue);
    }


    @Test
    public void testCheckInvalidValues() {
        Integer flightID = 545;
        String locationType = "fga";
        String location = "FkfaF";
        String altitude = "4343";
        String latitude = "4223.2";
        String longitude = "434.6";

        FlightData test = new FlightData(flightID, locationType, location, altitude, latitude, longitude);

        int validityValue = test.checkValues();
        assertEquals(-3, validityValue);
    }
}
