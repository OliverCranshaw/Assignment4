package seng202.team5.data;


import org.junit.Test;
import seng202.team5.data.AirlineData;
import static org.junit.Assert.*;

public class AirlineDataTest {



    @Test
    public void testConvertBlanksToNull() {
        String testName = "airnz";
        String alias = "";
        String iata = "FJ";
        String icao = "\\N";
        String callsign = "CARGO UNIT";
        String country = "New zealand";
        String active = "Y";
        AirlineData test = new AirlineData(testName, alias, iata, icao, country, callsign, active);
        test.convertBlanksToNull();
        assertNull(test.getAlias());
        assertNull(test.getIcao());
        assertNotNull(test.getIata());
    }


    @Test
    public void testCheckValuesValid() {
        String testName = "airnz";
        String alias = null;
        String iata = "FJ";
        String icao = "FSF";
        String callsign = "CARGO UNIT";
        String country = "New zealand";
        String active = "Y";
        AirlineData test = new AirlineData(testName, alias, iata, icao, country, callsign, active);
        int validityValue = test.checkValues();
        assertEquals(1, validityValue);
    }

    @Test
    public void testCheckValuesInvalid() {
        String testName = "airnz";
        String alias = null;
        String iata = "FJ";
        String icao = "FS";
        String callsign = "CARGO UNIT";
        String country = "New zealand";
        String active = "Y";
        AirlineData test = new AirlineData(testName, alias, iata, icao, country, callsign, active);
        int validityValue = test.checkValues();
        assertEquals(-4, validityValue);
    }




}
