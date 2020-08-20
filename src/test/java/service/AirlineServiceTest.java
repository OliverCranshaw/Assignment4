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

        assertTrue(res != -1);

        ResultSet resultSet = airlineService.getAirlines(null, null, null);
        assertTrue(resultSet.next());
        assertFalse(resultSet.next());
    }
}
