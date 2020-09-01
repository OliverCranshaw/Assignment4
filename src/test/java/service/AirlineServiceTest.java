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
import java.util.List;

import seng202.team5.database.DBInitializer;

public class AirlineServiceTest extends BaseDatabaseTest {

    private AirlineService airlineService;

    private final List<String> testData = List.of("AirlineName", "AliasName", "CountryName", "ITS", "ICAO", "CallsignStuff", "Y");

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
        int res = airlineService.saveAirline(
                testData.get(0),
                testData.get(1),
                testData.get(2),
                testData.get(3),
                testData.get(4),
                testData.get(5),
                testData.get(6)
        );

        // Check operation did not fail
        assertTrue(res != -1);

        // Query all airline data
        Statement stmt = DBConnection.getConnection().createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM AIRLINE_DATA");

        // Check that there is at least one result
        assertTrue(resultSet.next());

        // Check the result contents
        for (int i = 0; i<testData.size(); i++) {
            assertEquals(testData.get(i), resultSet.getString(i + 2));
        }

        // Check there are no more than 1 result
        assertFalse(resultSet.next());
    }
}
