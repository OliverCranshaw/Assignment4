package seng202.team5.data;

import org.junit.Test;
import seng202.team5.data.FlightData;
import static org.junit.Assert.*;

public class FlightDataTest {



    @Test
    public void testValidStringConstructor() {
        Integer flightId = 545;
        String location_type = "Lufthansa";
        String location = "aflsf";
        String altitude = "4343";
        String latitude = "4223.2";
        String longitude = "434.6";

        FlightData test = new FlightData(flightId, location_type, location, altitude, latitude, longitude);

        assertEquals((Double) 4223.2, test.getLatitude());
        assertEquals((Integer) 4343, test.getAltitude());

    }


    @Test
    public void testConvertBlanksToNull() {
        Integer flightId = 545;
        String location_type = "\\N";
        String location = "-";
        String altitude = "4343";
        String latitude = "4223.2";
        String longitude = "434.6";

        FlightData test = new FlightData(flightId, location_type, location, altitude, latitude, longitude);

        test.convertBlanksToNull();

        assertNull(test.getLocationType());
        assertNull(test.getLocation());

    }


    @Test
    public void testCheckValidValues() {
        Integer flightId = 545;
        String location_type = "VOR";
        String location = "FFF";
        String altitude = "4343";
        String latitude = "4223.2";
        String longitude = "434.6";

        FlightData test = new FlightData(flightId, location_type, location, altitude, latitude, longitude);

        int validityValue = test.checkValues();
        assertEquals(1, validityValue);

    }


    @Test
    public void testCheckInvalidValues() {
        Integer flightId = 545;
        String location_type = "fga";
        String location = "FkfaF";
        String altitude = "4343";
        String latitude = "4223.2";
        String longitude = "434.6";

        FlightData test = new FlightData(flightId, location_type, location, altitude, latitude, longitude);

        int validityValue = test.checkValues();
        assertEquals(-3, validityValue);

    }

}
