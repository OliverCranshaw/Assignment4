package data;

import org.junit.Test;
import seng202.team5.data.AirportData;
import static org.junit.Assert.*;

public class AirportDataTest {


    @Test
    public void testValidStringConstructor() {
        String airport = "airport";
        String city = "chch";
        String country = "NZ";
        String iata = "CHC";
        String icao = "CHCH";
        String latitude = "54.4";
        String longitude = "323.2";
        String altitude = "554";
        String timezone = "53";
        String dst = "A";
        String tz = "tzTime";

        AirportData test = new AirportData(airport, city, country, iata, icao, latitude,
                longitude, altitude, timezone, dst, tz);


        assertEquals((Double) 323.2, test.getLongitude());
        assertEquals((Integer) 554, test.getAltitude());
    }


    @Test
    public void testConvertBlanksToNull() {
        String airport = "\\N";
        String city = "chch";
        String country = "-";
        String iata = "";
        String icao = "CHCH";
        String latitude = "54.4";
        String longitude = "323.2";
        String altitude = "554";
        String timezone = "53";
        String dst = "N/A";
        String tz = "tzTime";

        AirportData test = new AirportData(airport, city, country, iata, icao, latitude,
                longitude, altitude, timezone, dst, tz);

        test.convertBlanksToNull();

        assertNull(test.getCountry());
        assertNull(test.getAirportName());
        assertNull(test.getIata());
        assertNull(test.getDst());
    }



    @Test
    public void testCheckValidValues() {
        String airport = "airport";
        String city = "chch";
        String country = "NZ";
        String iata = "CHC";
        String icao = "CHCH";
        String latitude = "54.4";
        String longitude = "323.2";
        String altitude = "554";
        String timezone = "53";
        String dst = "S";
        String tz = "tzTime";

        AirportData test = new AirportData(airport, city, country, iata, icao, latitude,
                longitude, altitude, timezone, dst, tz);
        int validityValue = test.checkValues();
        assertEquals(1, validityValue);
    }



    @Test
    public void testCheckInvalidValues() {
        String airport = "airport";
        String city = "chch";
        String country = "NZ";
        String iata = "CHC";
        String icao = "CHCH";
        String latitude = "54.4";
        String longitude = "323.2";
        String altitude = "554";
        String timezone = "53";
        String dst = "Y";
        String tz = "tzTime";

        AirportData test = new AirportData(airport, city, country, iata, icao, latitude,
                longitude, altitude, timezone, dst, tz);
        int validityValue = test.checkValues();
        assertEquals(-11, validityValue);

    }

}