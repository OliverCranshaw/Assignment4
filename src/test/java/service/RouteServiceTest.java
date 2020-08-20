package service;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import seng202.team5.accessor.Accessor;
import seng202.team5.accessor.AirlineAccessor;
import seng202.team5.database.DBConnection;
import seng202.team5.database.DBInitializer;
import seng202.team5.database.DBTableInitializer;
import seng202.team5.service.RouteService;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteServiceTest extends BaseDatabaseTest {

    private RouteService routeService;

    public RouteServiceTest(String testName) { super(testName); }

    public static Test suite() { return new TestSuite(RouteServiceTest.class); }

    //protected Connection dbHandler;

    @Override
    protected void setUp() {
        super.setUp();
        routeService = new RouteService();
    }

    public void testInitialState() throws SQLException {
        ResultSet stuff = routeService.getRoutes(null, null, null, 0, null);
        assertFalse(stuff.next());
    }

    public void testAddRoute() throws SQLException {
        String airline = "ITA";
        String sourceAirport = "ATA";
        String destinationAirport = "AUS";
        String codeShare = "Y";
        Integer stops = 6;
        String equipment = "AJF";

        Connection dbHandler = DBConnection.getConnection();


        String airportQuery = "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                + "longitude, altitude, timezone, dst, tz_database_timezone) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement stmt2 = dbHandler.prepareStatement(airportQuery);
        List<Object> tmp2 = Arrays.asList("Heathrow", "London", "England", "ATA", "FJLJ", 89, 123.2, 5000, 43, "JFI", "TZ");
        ArrayList<Object> testAirport1 = new ArrayList<>();
        testAirport1.addAll(tmp2);
        for (int i=1; i < 12; i++) {
            stmt2.setObject(i, testAirport1.get(i-1));
        }
        stmt2.executeUpdate();


        PreparedStatement stmt3 = dbHandler.prepareStatement(airportQuery);
        List<Object> tmp3 = Arrays.asList("ChCh", "Christchurch", "New Zealand", "AUS", "NZNZ", 70, 231, 6000, 42, "JFP", "TZF");
        ArrayList<Object> testAirport2 = new ArrayList<>();
        testAirport2.addAll(tmp3);
        for (int i=1; i < 12; i++) {
            stmt3.setObject(i, testAirport2.get(i-1));
        }
        stmt3.executeUpdate();



        String airLineQuery = "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, "
                + "callsign, country, active) VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement stmt = dbHandler.prepareStatement(airLineQuery);
        List<String> tmp = Arrays.asList("testName", "testAlias", airline, "iopd", "testCallsign", "Argentina", "Y");
        ArrayList<String> testAirline = new ArrayList<>();
        testAirline.addAll(tmp);
        for (int i=1; i < 8; i++) {
            stmt.setObject(i, testAirline.get(i-1));
        }
        stmt.executeUpdate();

        dbHandler.close();

        System.out.println("Here!!!!");
        int res = routeService.saveRoute(airline, sourceAirport, destinationAirport, codeShare, stops, equipment);
        System.out.println("Here: " + res);

        /*
        NOT USED ATM JUST HERE FOR ONCE ABOVE PART WORKING 8)
        String routeQuery = "SELECT route_id FROM ROUTE_DATA WHERE airline = ITA and " +
                "source_airport = ATA and destination_airport = AUS and stops = 6";


        PreparedStatement stmtRoute = dbHandler.prepareStatement(routeQuery);
        ResultSet result = stmtRoute.executeQuery();
        System.out.println("result: " + result);


        */

    }


}
