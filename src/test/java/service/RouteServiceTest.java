package service;


import junit.framework.Test;
import junit.framework.TestSuite;
import seng202.team5.database.DBConnection;
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

    @Override
    protected void setUp() {
        super.setUp();
        routeService = new RouteService();
    }

    public void testInitialState() throws SQLException {
        ResultSet stuff = routeService.getRoutes(null, null, null, 0, null);
        assertFalse(stuff.next());
    }


    public void testAddValidRoute() throws SQLException {
        // Setting up variables for a test Route
        String airline = "ITA";
        String sourceAirport = "ATA";
        String destinationAirport = "AUS";
        String codeShare = "Y";
        int stops = 6;
        String equipment = "AJF";

        // Placing variables for testRoute into an ArrayList
        List<Object> temp = Arrays.asList(airline, sourceAirport, destinationAirport, codeShare, stops, equipment);
        ArrayList<Object> parameters = new ArrayList<>(temp);

        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();


        // SQLite query used to populate database with the required data to run the routeService saveRoute() method
        String airportQuery = "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                + "longitude, altitude, timezone, dst, tz_database_timezone) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Creating a statement that is then given airport data,  and then executed, inserting it into the database
        PreparedStatement stmt2 = dbHandler.prepareStatement(airportQuery);
        List<Object> tmp2 = Arrays.asList("Heathrow", "London", "England", "ATA", "FJLJ", 89, 123.2, 5000, 43, "JFI", "TZ");
        ArrayList<Object> testAirport1 = new ArrayList<>(tmp2);
        for (int i=1; i < 12; i++) {
            stmt2.setObject(i, testAirport1.get(i-1));
        }
        stmt2.executeUpdate();

        // Creating a statement that is then given airport data,  and then executed, inserting it into the database
        PreparedStatement stmt3 = dbHandler.prepareStatement(airportQuery);
        List<Object> tmp3 = Arrays.asList("ChCh", "Christchurch", "New Zealand", "AUS", "NZNZ", 70, 231, 6000, 42, "JFP", "TZF");
        ArrayList<Object> testAirport2 = new ArrayList<>(tmp3);
        for (int i=1; i < 12; i++) {
            stmt3.setObject(i, testAirport2.get(i-1));
        }
        stmt3.executeUpdate();


        // SQLite query used to populate the database with the required data to run the RouteService saveRoute() method
        String airLineQuery = "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, "
                + "callsign, country, active) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Creating a statement that is then given airline data, and then executed, inserting it into the database
        PreparedStatement stmt = dbHandler.prepareStatement(airLineQuery);
        List<String> tmp = Arrays.asList("testName", "testAlias", airline, "iopd", "testCallsign", "Argentina", "Y");
        ArrayList<String> testAirline = new ArrayList<>(tmp);
        for (int i=1; i < 8; i++) {
            stmt.setObject(i, testAirline.get(i-1));
        }
        stmt.executeUpdate();


        // Running the saveRoute() of the routeService. This is the method being tested.
        int res = routeService.saveRoute(airline, sourceAirport, destinationAirport, codeShare, stops, equipment);

        // Ensuring the routeService returns no errors
        assertEquals(1, res);

        // SQLite query used to retrieve the route data from the database
        String routeQuery = "SELECT * FROM ROUTE_DATA WHERE airline = ? and " +
                "source_airport = ? and destination_airport = ? and codeshare = ? and stops = ? and equipment = ?";

        // Creating a statement that will retrieve that route data from the database, and the executing it
        PreparedStatement stmtRoute = dbHandler.prepareStatement(routeQuery);
        for (int i = 1; i < 7; i++) {
            stmtRoute.setObject(i, parameters.get(i-1));
        }
        ResultSet result = stmtRoute.executeQuery();

        // Asserting that the variables returned by the database are the same as the variables that were used to
        // Initially populate it.
        int index = 0;
        for (int i = 1; i <= 10; i++) {
            if (i != 1 && i != 3 && i != 5 && i != 7 && i != 9) {
                assertEquals(result.getString(i), parameters.get(index++));
            } else if (i == 9) {
                assertEquals(result.getInt(i), parameters.get(index++));
            }
        }

        // Closing connection with the database
        dbHandler.close();
    }




    public void testAddInvalidRoute() throws SQLException {
        // Setting up variables for a test Route
        String airline = "ITA";
        String sourceAirport = "ATA";
        String destinationAirport = "AUS";
        String codeShare = "P";
        int stops = 6;
        String equipment = "AJF";

        // Placing variables for testRoute into an ArrayList
        List<Object> temp = Arrays.asList(airline, sourceAirport, destinationAirport, codeShare, stops, equipment);
        ArrayList<Object> parameters = new ArrayList<>(temp);

        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();


        // SQLite query used to populate database with the required data to run the routeService saveRoute() method
        String airportQuery = "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                + "longitude, altitude, timezone, dst, tz_database_timezone) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Creating a statement that is then given airport data,  and then executed, inserting it into the database
        PreparedStatement stmt2 = dbHandler.prepareStatement(airportQuery);
        List<Object> tmp2 = Arrays.asList("Heathrow", "London", "England", "ATA", "FJLJ", 89, 123.2, 5000, 43, "JFI", "TZ");
        ArrayList<Object> testAirport1 = new ArrayList<>(tmp2);
        for (int i=1; i < 12; i++) {
            stmt2.setObject(i, testAirport1.get(i-1));
        }
        stmt2.executeUpdate();

        // Creating a statement that is then given airport data,  and then executed, inserting it into the database
        PreparedStatement stmt3 = dbHandler.prepareStatement(airportQuery);
        List<Object> tmp3 = Arrays.asList("ChCh", "Christchurch", "New Zealand", "AUS", "NZNZ", 70, 231, 6000, 42, "JFP", "TZF");
        ArrayList<Object> testAirport2 = new ArrayList<>(tmp3);
        for (int i=1; i < 12; i++) {
            stmt3.setObject(i, testAirport2.get(i-1));
        }
        stmt3.executeUpdate();


        // SQLite query used to populate the database with the required data to run the RouteService saveRoute() method
        String airLineQuery = "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, "
                + "callsign, country, active) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Creating a statement that is then given airline data, and then executed, inserting it into the database
        PreparedStatement stmt = dbHandler.prepareStatement(airLineQuery);
        List<String> tmp = Arrays.asList("testName", "testAlias", airline, "iopd", "testCallsign", "Argentina", "Y");
        ArrayList<String> testAirline = new ArrayList<>(tmp);
        for (int i=1; i < 8; i++) {
            stmt.setObject(i, testAirline.get(i-1));
        }
        stmt.executeUpdate();


        // Running the saveRoute() of the routeService. This is the method being tested.
        int res = routeService.saveRoute(airline, sourceAirport, destinationAirport, codeShare, stops, equipment);
        assertEquals(-1, res);

        // SQLite query used to retrieve the route data from the database
        String routeQuery = "SELECT * FROM ROUTE_DATA WHERE airline = ? and " +
                "source_airport = ? and destination_airport = ? and codeshare = ? and stops = ? and equipment = ?";

        // Creating a statement that will retrieve that route data from the database, and the executing it
        PreparedStatement stmtRoute = dbHandler.prepareStatement(routeQuery);
        for (int i = 1; i < 7; i++) {
            stmtRoute.setObject(i, parameters.get(i-1));
        }
        ResultSet result = stmtRoute.executeQuery();
        assertTrue(result.isClosed());


        // Closing connection with the database
        dbHandler.close();
    }


    public void testUpdateRoute() throws SQLException {



    }

}
