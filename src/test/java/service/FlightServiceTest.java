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
import seng202.team5.service.FlightService;

public class FlightServiceTest extends TestCase {
    private static final String dbFile = "test.db";


    private FlightService flightService;

    public FlightServiceTest(String testName) { super(testName); }

    public static Test suite() { return new TestSuite(AirportServiceTest.class); }


    protected void setUp() {
        DBInitializer.createNewDatabase(dbFile);
        DBTableInitializer.initializeTables(dbFile);

        DBConnection.setDatabaseFile(new File(dbFile));
        flightService = new FlightService();
    }

    protected void tearDown() throws SQLException {
        Connection conn = DBConnection.getConnection();
        conn.close();

        new File(dbFile).delete();
    }

    public void testInitialState() throws SQLException {
        ResultSet stuff = flightService.getFlights(null, null);
        assertFalse(stuff.next());
    }
}
