package service;

import junit.framework.Test;
import junit.framework.TestSuite;
import seng202.team5.database.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


import seng202.team5.service.FlightService;

public class FlightServiceTest extends BaseDatabaseTest {

    private FlightService flightService;

    public FlightServiceTest(String testName) { super(testName); }

    public static Test suite() { return new TestSuite(FlightServiceTest.class); }

    @Override
    protected void setUp() {
        super.setUp();
        flightService = new FlightService();
    }

    public void testInitialState() throws SQLException {
        ResultSet stuff = flightService.getFlights(null, null);
        assertFalse(stuff.next());
    }
}
