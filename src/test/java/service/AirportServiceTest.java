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
import java.sql.Statement;

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
        // Check operation did not fail
        assertTrue(res != -1);

        // Query all airport data
        Statement stmt = DBConnection.getConnection().createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM AIRPORT_DATA");

        // Check that there is at least one result
        assertTrue(resultSet.next());

        // Check the result contents
        assertEquals(name, resultSet.getString(2));
        assertEquals(city, resultSet.getString(3));
        assertEquals(country, resultSet.getString(4));

        assertEquals(iata, resultSet.getString(5));
        assertEquals(icao, resultSet.getString(6));

        assertEquals(latitude, resultSet.getDouble(7));
        assertEquals(longitude, resultSet.getDouble(8));
        assertEquals(altitude, resultSet.getInt(9));

        assertEquals(timezone, resultSet.getInt(10));
        assertEquals(dst, resultSet.getString(11));
        assertEquals(tz, resultSet.getString(12));

        // Check there are no more than 1 result
        assertFalse(resultSet.next());
    }
}
