package data;

import org.junit.Test;
import seng202.team5.data.FlightData;
import static org.junit.Assert.*;

public class FlightDataTest {



    @Test
    public void testValidStringConstructor() {
        String flightId = "545";
        String airline = "Lufthansa";
        String airport = "aflsf";
        String altitude = "4343";
        String latitude = "4223.2";
        String longitude = "434.6";

        FlightData test = new FlightData(flightId, airline, airport, altitude, latitude, longitude);

        assertEquals((Double) 4223.2, test.getLatitude());
        assertEquals((Integer) 4343, test.getAltitude());

    }


    @Test
    public void testConvertBlanksToNull() {
        String flightId = "545";
        String airline = "\\N";
        String airport = "-";
        String altitude = "4343";
        String latitude = "4223.2";
        String longitude = "434.6";

        FlightData test = new FlightData(flightId, airline, airport, altitude, latitude, longitude);

        test.convertBlanksToNull();

        assertNull(test.getAirline());
        assertNull(test.getAirport());

    }


    @Test
    public void testCheckValidValues() {
        String flightId = "545";
        String airline = "FJFF";
        String airport = "FFF";
        String altitude = "4343";
        String latitude = "4223.2";
        String longitude = "434.6";

        FlightData test = new FlightData(flightId, airline, airport, altitude, latitude, longitude);

        int validityValue = test.checkValues();
        assertEquals(1, validityValue);

    }


    @Test
    public void testCheckInvalidValues() {
        String flightId = "545";
        String airline = "alfl";
        String airport = "Fkfa";
        String altitude = "4343";
        String latitude = "4223.2";
        String longitude = "434.6";

        FlightData test = new FlightData(flightId, airline, airport, altitude, latitude, longitude);

        int validityValue = test.checkValues();
        assertEquals(-4, validityValue);

    }


}
