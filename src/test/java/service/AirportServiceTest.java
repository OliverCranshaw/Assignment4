package service;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import seng202.team5.database.DBConnection;
import seng202.team5.database.DBTableInitializer;
import seng202.team5.service.AirportService;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import seng202.team5.database.DBInitializer;

public class AirportServiceTest extends BaseDatabaseTest {

    private AirportService airportService;

    public AirportServiceTest(String testName) { super(testName); }

    public static Test suite() { return new TestSuite(AirportServiceTest.class); }

    @Override
    protected void setUp() {
        super.setUp();
        airportService = new AirportService();
    }

    public void testInitialState() throws SQLException {
        ResultSet stuff = airportService.getAirports(null, null, null);
        assertFalse(stuff.next());
    }

    public void testAddAirport() throws SQLException {
        String name = "AirportName";
        String city = "CityName";
        String country = "CountryName";
        String iata = "IAT";
        String icao = "ICAO";
        double latitude = 4.5;
        double longitude = 6.2;
        int altitude = 424242;
        int timezone = 535353;
        String dst = "E";
        String tz = "Timezone";

        int res = airportService.saveAirport(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        assertTrue(res != -1);

        ResultSet resultSet = airportService.getAirports(null, null, null);
        assertTrue(resultSet.next());
        assertFalse(resultSet.next());
    }
}
