package service;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import seng202.team5.database.DBConnection;
import seng202.team5.database.DBTableInitializer;
import seng202.team5.service.AirlineService;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import seng202.team5.database.DBInitializer;

public class AirlineServiceTest extends BaseDatabaseTest {

    private AirlineService airlineService;

    public AirlineServiceTest(String testName) { super(testName); }

    public static Test suite() { return new TestSuite(AirlineServiceTest.class); }

    @Override
    protected void setUp() {
        super.setUp();
        airlineService = new AirlineService();
    }

    public void testInitialState() throws SQLException {
        ResultSet stuff = airlineService.getAirlines(null, null, null);
        assertFalse(stuff.next());
    }

    public void testAddAirline() throws SQLException {
        String name = "AirportName";
        String alias = "AliasName";
        String country = "CountryName";
        String iata = "ITS";
        String icao = "ICAO";
        String callsign = "CallsignStuff";
        String active = "Y";

        int res = airlineService.saveAirline(name, alias, iata, icao, callsign, country, active);
        // Check operation did not fail
        assertTrue(res != -1);

        // Query all airline data
        Statement stmt = DBConnection.getConnection().createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM AIRLINE_DATA");

        // Check that there is at least one result
        assertTrue(resultSet.next());

        // Check the result contents
        assertEquals(name, resultSet.getString(2));
        assertEquals(alias, resultSet.getString(3));
        assertEquals(iata, resultSet.getString(4));
        assertEquals(icao, resultSet.getString(5));
        assertEquals(callsign, resultSet.getString(6));
        assertEquals(country, resultSet.getString(7));
        assertEquals(active, resultSet.getString(8));

        // Check there are no more than 1 result
        assertFalse(resultSet.next());
    }
}
