package service;


import junit.framework.Test;
import junit.framework.TestSuite;
import seng202.team5.accessor.RouteAccessor;
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
        ResultSet stuff = routeService.getRoutes(null, null, 0, null); // delete later comment for a push
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

        // SQLite query used to retrieve the route data from the database
        String routeQuery = "SELECT * FROM ROUTE_DATA WHERE airline = ? and " +
                "source_airport = ? and destination_airport = ? and codeshare = ? and stops = ? and equipment = ?";

        // SQLite query used to populate database with the required data to run the routeService saveRoute() method
        String airportQuery = "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                + "longitude, altitude, timezone, dst, tz_database_timezone) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate the database with the required data to run the RouteService saveRoute() method
        String airLineQuery = "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, "
                + "callsign, country, active) VALUES (?, ?, ?, ?, ?, ?, ?)";


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

        // SQLite query used to retrieve the route data from the database
        String routeQuery = "SELECT * FROM ROUTE_DATA WHERE airline = ? and " +
                "source_airport = ? and destination_airport = ? and codeshare = ? and stops = ? and equipment = ?";


        // SQLite query used to populate database with the required data to run the routeService saveRoute() method
        String airportQuery = "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                + "longitude, altitude, timezone, dst, tz_database_timezone) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate the database with the required data to run the RouteService saveRoute() method
        String airLineQuery = "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, "
                + "callsign, country, active) VALUES (?, ?, ?, ?, ?, ?, ?)";


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
        // Setting up variables for a test Route
        String airline = "ITA";
        String sourceAirport = "ATA";
        String destinationAirport = "AUS";
        String codeShare = "Y";
        int stops = 6;
        String equipment = "AJF";

        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();


        // SQLite query used to populate database with the required data to run the routeService saveRoute() method
        String airportQuery = "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                + "longitude, altitude, timezone, dst, tz_database_timezone) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate the database with the required data to run the RouteService saveRoute() method
        String airLineQuery = "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, "
                + "callsign, country, active) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate the database with the route data required to run the routeUpdate on
        String routeQuery = "INSERT INTO ROUTE_DATA(airline, airline_id, source_airport, source_airport_id, " +
                "destination_airport, destination_airport_id, codeshare, stops, equipment) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to retrieve the route data from the database
        String routeIdQuery = "SELECT route_id FROM ROUTE_DATA WHERE airline = ? and " +
                "source_airport = ? and destination_airport = ? and codeshare = ? and stops = ? and equipment = ?";

        // SQLite query used to retrieve airline_id in order to be passed into a piece of route data
        String airlineIdQuery = "SELECT airline_id FROM AIRLINE_DATA WHERE iata = ? and airline_name = ? and alias = ?";

        // SQLite query used to retrieve airport_id in order to be passed into a piece of route data
        String airportIdQuery = "SELECT airport_id FROM AIRPORT_DATA WHERE airport_name = ? and city = ?";

        // SQLite query used to retrieve all data for the route that has been updated, from the database
        String routeFinalQuery = "SELECT * FROM ROUTE_DATA";

        // Creating a statement that is then given airport data,  and then executed, inserting it into the database
        // This will be used as the source airport
        PreparedStatement stmtSrcAirport = dbHandler.prepareStatement(airportQuery);
        List<Object> tmpSrcAirport = Arrays.asList("Heathrow", "London", "England", "ATA", "FJLJ", 89, 123.2, 5000, 43, "JFI", "TZ");
        ArrayList<Object> testSrcAirport = new ArrayList<>(tmpSrcAirport);
        for (int i=1; i < 12; i++) {
            stmtSrcAirport.setObject(i, testSrcAirport.get(i-1));
        }
        stmtSrcAirport.executeUpdate();

        // Creating a statement that is then given airport data,  and then executed, inserting it into the database
        // This will be used as the destination airport
        PreparedStatement stmtDstAirport = dbHandler.prepareStatement(airportQuery);
        List<Object> tmpDstAirport = Arrays.asList("ChCh", "Christchurch", "New Zealand", "AUS", "NZNZ", 70, 231, 6000, 42, "JFP", "TZF");
        ArrayList<Object> testDstAirport = new ArrayList<>(tmpDstAirport);
        for (int i=1; i < 12; i++) {
            stmtDstAirport.setObject(i, testDstAirport.get(i-1));
        }
        stmtDstAirport.executeUpdate();


        // Creating a statement that is then given airport data,  and then executed, inserting it into the database
        // This will be used as the replacement airport (the airport used in the update).
        PreparedStatement stmtAirportAlt = dbHandler.prepareStatement(airportQuery);
        List<Object> tmpAirportAlt = Arrays.asList("Haneda", "Tokyo", "Japan", "JPN", "JPNN", 70, 231, 6000, 42, "JFP", "TZF");
        ArrayList<Object> testAirportAlt = new ArrayList<>(tmpAirportAlt);
        for (int i=1; i < 12; i++) {
            stmtAirportAlt.setObject(i, testAirportAlt.get(i-1));
        }
        stmtAirportAlt.executeUpdate();


        // Creating a statement that is then given airline data, and then executed, inserting it into the database
        // This will be airline for the route data
        PreparedStatement stmtAirline = dbHandler.prepareStatement(airLineQuery);
        List<String> tmpAirline = Arrays.asList("testName", "testAlias", airline, "iopd", "testCallsign", "Argentina", "Y");
        ArrayList<String> testAirline = new ArrayList<>(tmpAirline);
        for (int i=1; i < 8; i++) {
            stmtAirline.setObject(i, testAirline.get(i-1));
        }
        stmtAirline.executeUpdate();


        // Creating a statement that will retrieve the airlineId from the database
        PreparedStatement stmtAirlineID = dbHandler.prepareStatement(airlineIdQuery);
        List<String> tmpAirlineID = Arrays.asList("ITA", "testName", "testAlias");
        ArrayList<String> airlineIdList = new ArrayList<>(tmpAirlineID);
        for (int i=1; i < 4; i++) {
            stmtAirlineID.setObject(i, airlineIdList.get(i-1));
        }
        ResultSet result = stmtAirlineID.executeQuery();
        int testAirlineId = result.getInt(1);


        // Creating a statement that will retrieve the srcAirportId from the database
        PreparedStatement stmtSrcAirportId = dbHandler.prepareStatement(airportIdQuery);
        List<String> tmpSrcAirportId = Arrays.asList("Heathrow", "London");
        ArrayList<String> srcAirportIdList = new ArrayList<>(tmpSrcAirportId);
        for (int i=1; i < 3; i++) {
            stmtSrcAirportId.setObject(i, srcAirportIdList.get(i-1));
        }
        ResultSet resultSrcAirport = stmtSrcAirportId.executeQuery();
        int srcAirportId = resultSrcAirport.getInt(1);


        // Creating a statement that will retrieve the dstAirportId from the database
        PreparedStatement stmtDstAirportId = dbHandler.prepareStatement(airportIdQuery);
        List<String> tmpDstAirportId = Arrays.asList("ChCh", "Christchurch");
        ArrayList<String> dstAirportIdList = new ArrayList<>(tmpDstAirportId);
        for (int i=1; i < 3; i++) {
            stmtDstAirportId.setObject(i, dstAirportIdList.get(i-1));
        }
        ResultSet resultDstAirport = stmtDstAirportId.executeQuery();
        int dstAirportId = resultDstAirport.getInt(1);


        // Creating a statement that will retrieve the altAirlineId from the database
        PreparedStatement stmtAltAirportId = dbHandler.prepareStatement(airportIdQuery);
        List<String> tmpAltAirportId = Arrays.asList("Haneda", "Tokyo");
        ArrayList<String> altAirportIdList = new ArrayList<>(tmpAltAirportId);
        for (int i=1; i < 3; i++) {
            stmtAltAirportId.setObject(i, altAirportIdList.get(i-1));
        }
        ResultSet resultAltAirport = stmtAltAirportId.executeQuery();
        int altAirportId = resultAltAirport.getInt(1);



        // Creating a statement that is then given routeData, and then executed, inserting it into the database
        // This is the route that will be updated using the updateRoute method
        PreparedStatement stmtRoute = dbHandler.prepareStatement(routeQuery);
        List<Object> tmpRoute = Arrays.asList(airline, testAirlineId, sourceAirport, testSrcAirport, destinationAirport, dstAirportId, codeShare, stops, equipment);
        ArrayList<Object> testRouteList = new ArrayList<>(tmpRoute);
        for (int i=1; i < 10; i++) {
            stmtRoute.setObject(i, testRouteList.get(i-1));
        }
        stmtRoute.executeUpdate();


        // Creating a statement that will get the routeId to be used in the updateRoute statement
        PreparedStatement stmtRouteId = dbHandler.prepareStatement(routeIdQuery);
        List<Object> tmpRouteId = Arrays.asList(airline, sourceAirport, destinationAirport, codeShare, stops, equipment);
        ArrayList<Object> testRouteIdList = new ArrayList<>(tmpRouteId);
        for (int i =1; i < 7; i++) {
            stmtRouteId.setObject(i, testRouteIdList.get(i-1));
        }
        ResultSet resultRouteId = stmtRouteId.executeQuery();
        int testRouteId = resultRouteId.getInt(1);


        // Running the updateRoute method that is being tested, replacing the destination airport
        int res = routeService.updateRoute(testRouteId, airline, sourceAirport, "JPN", codeShare, stops, equipment);


        // Creating a statement to retrieve all data of the route.
        Statement stmtRouteFinal = dbHandler.createStatement();
        ResultSet resultRouteFinal = stmtRouteFinal.executeQuery(routeFinalQuery);

        // Checking all of the route data is the same, bar the destination airport information
        assertEquals(testRouteId, resultRouteFinal.getInt(1));
        assertEquals(airline, resultRouteFinal.getString(2));
        assertEquals(testAirlineId, resultRouteFinal.getInt(3));
        assertEquals(sourceAirport, resultRouteFinal.getString(4));
        assertEquals(srcAirportId, resultRouteFinal.getInt(5));
        assertEquals("JPN", resultRouteFinal.getString(6));
        assertEquals(altAirportId, resultRouteFinal.getInt(7));
        assertEquals(codeShare, resultRouteFinal.getString(8));
        assertEquals(stops, resultRouteFinal.getInt(9));
        assertEquals(equipment, resultRouteFinal.getString(10));

        // Closing database connection
        dbHandler.close();

    }



    public void testInvalidUpdate() throws SQLException {
        // Setting up variables for a test Route
        String airline = "ITA";
        String sourceAirport = "ATA";
        String destinationAirport = "AUS";
        String codeShare = "Y";
        int stops = 6;
        String equipment = "AJF";

        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();


        // SQLite query used to populate database with the required data to run the routeService saveRoute() method
        String airportQuery = "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                + "longitude, altitude, timezone, dst, tz_database_timezone) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate the database with the required data to run the RouteService saveRoute() method
        String airLineQuery = "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, "
                + "callsign, country, active) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate the database with the route data required to run the routeUpdate on
        String routeQuery = "INSERT INTO ROUTE_DATA(airline, airline_id, source_airport, source_airport_id, " +
                "destination_airport, destination_airport_id, codeshare, stops, equipment) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to retrieve the route data from the database
        String routeIdQuery = "SELECT route_id FROM ROUTE_DATA WHERE airline = ? and " +
                "source_airport = ? and destination_airport = ? and codeshare = ? and stops = ? and equipment = ?";

        // SQLite query used to retrieve airline_id in order to be passed into a piece of route data
        String airlineIdQuery = "SELECT airline_id FROM AIRLINE_DATA WHERE iata = ? and airline_name = ? and alias = ?";

        // SQLite query used to retrieve airport_id in order to be passed into a piece of route data
        String airportIdQuery = "SELECT airport_id FROM AIRPORT_DATA WHERE airport_name = ? and city = ?";



        // Creating a statement that is then given airport data,  and then executed, inserting it into the database
        // This will be used as the source airport
        PreparedStatement stmtSrcAirport = dbHandler.prepareStatement(airportQuery);
        List<Object> tmpSrcAirport = Arrays.asList("Heathrow", "London", "England", "ATA", "FJLJ", 89, 123.2, 5000, 43, "JFI", "TZ");
        ArrayList<Object> testSrcAirport = new ArrayList<>(tmpSrcAirport);
        for (int i=1; i < 12; i++) {
            stmtSrcAirport.setObject(i, testSrcAirport.get(i-1));
        }
        stmtSrcAirport.executeUpdate();


        // Creating a statement that is then given airport data,  and then executed, inserting it into the database
        // This will be used as the destination airport
        PreparedStatement stmtDstAirport = dbHandler.prepareStatement(airportQuery);
        List<Object> tmpDstAirport = Arrays.asList("ChCh", "Christchurch", "New Zealand", "AUS", "NZNZ", 70, 231, 6000, 42, "JFP", "TZF");
        ArrayList<Object> testDstAirport = new ArrayList<>(tmpDstAirport);
        for (int i=1; i < 12; i++) {
            stmtDstAirport.setObject(i, testDstAirport.get(i-1));
        }
        stmtDstAirport.executeUpdate();


        // Creating a statement that is then given airline data, and then executed, inserting it into the database
        // This will be airline for the route data
        PreparedStatement stmtAirline = dbHandler.prepareStatement(airLineQuery);
        List<String> tmpAirline = Arrays.asList("testName", "testAlias", airline, "iopd", "testCallsign", "Argentina", "Y");
        ArrayList<String> testAirline = new ArrayList<>(tmpAirline);
        for (int i=1; i < 8; i++) {
            stmtAirline.setObject(i, testAirline.get(i-1));
        }
        stmtAirline.executeUpdate();


        // Creating a statement that will retrieve the airlineId from the database
        PreparedStatement stmtAirlineID = dbHandler.prepareStatement(airlineIdQuery);
        List<String> tmpAirlineID = Arrays.asList("ITA", "testName", "testAlias");
        ArrayList<String> airlineIdList = new ArrayList<>(tmpAirlineID);
        for (int i=1; i < 4; i++) {
            stmtAirlineID.setObject(i, airlineIdList.get(i-1));
        }
        ResultSet result = stmtAirlineID.executeQuery();
        int testAirlineId = result.getInt(1);


        // Creating a statement that will retrieve the srcAirportId from the database
        PreparedStatement stmtSrcAirportId = dbHandler.prepareStatement(airportIdQuery);
        List<String> tmpSrcAirportId = Arrays.asList("Heathrow", "London");
        ArrayList<String> srcAirportIdList = new ArrayList<>(tmpSrcAirportId);
        for (int i=1; i < 3; i++) {
            stmtSrcAirportId.setObject(i, srcAirportIdList.get(i-1));
        }
        ResultSet resultSrcAirport = stmtSrcAirportId.executeQuery();
        int srcAirportId = resultSrcAirport.getInt(1);


        // Creating a statement that will retrieve the dstAirportId from the database
        PreparedStatement stmtDstAirportId = dbHandler.prepareStatement(airportIdQuery);
        List<String> tmpDstAirportId = Arrays.asList("ChCh", "Christchurch");
        ArrayList<String> dstAirportIdList = new ArrayList<>(tmpDstAirportId);
        for (int i=1; i < 3; i++) {
            stmtDstAirportId.setObject(i, dstAirportIdList.get(i-1));
        }
        ResultSet resultDstAirport = stmtDstAirportId.executeQuery();
        int dstAirportId = resultDstAirport.getInt(1);


        // Creating a statement that is then given routeData, and then executed, inserting it into the database
        // This is the route that will be updated using the updateRoute method
        PreparedStatement stmtRoute = dbHandler.prepareStatement(routeQuery);
        List<Object> tmpRoute = Arrays.asList(airline, testAirlineId, sourceAirport, testSrcAirport, destinationAirport, dstAirportId, codeShare, stops, equipment);
        ArrayList<Object> testRouteList = new ArrayList<>(tmpRoute);
        for (int i=1; i < 10; i++) {
            stmtRoute.setObject(i, testRouteList.get(i-1));
        }
        stmtRoute.executeUpdate();


        // Creating a statement that will get the routeId to be used in the updateRoute statement
        PreparedStatement stmtRouteId = dbHandler.prepareStatement(routeIdQuery);
        List<Object> tmpRouteId = Arrays.asList(airline, sourceAirport, destinationAirport, codeShare, stops, equipment);
        ArrayList<Object> testRouteIdList = new ArrayList<>(tmpRouteId);
        for (int i =1; i < 7; i++) {
            stmtRouteId.setObject(i, testRouteIdList.get(i-1));
        }
        ResultSet resultRouteId = stmtRouteId.executeQuery();
        int testRouteId = resultRouteId.getInt(1);


        // Running the updateRoute method, using a destination airport that isn't in the database
        int res = routeService.updateRoute(testRouteId, airline, sourceAirport, "JON", codeShare, stops, equipment);
        // Running the updateRoute method, using a codeShare that is invalid
        int res2 = routeService.updateRoute(testRouteId, airline, sourceAirport, destinationAirport, "E", stops, equipment);
        // Running the updateRoute method, using a routeId that doesn't exist
        int res3 = routeService.updateRoute(4, airline, sourceAirport, destinationAirport, codeShare, stops, equipment);


        assertEquals(-1, res);
        assertEquals(-1, res2);
        assertEquals(0, res3);

    }


    public void testDeleteRoute() throws SQLException {
        // Setting up variables for a test Route
        String airline = "ITA";
        String sourceAirport = "ATA";
        String destinationAirport = "AUS";
        String codeShare = "Y";
        int stops = 6;
        String equipment = "AJF";

        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();


        // SQLite query used to populate database with the required data to run the routeService saveRoute() method
        String airportQuery = "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                + "longitude, altitude, timezone, dst, tz_database_timezone) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate the database with the required data to run the RouteService saveRoute() method
        String airLineQuery = "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, "
                + "callsign, country, active) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate the database with the route data required to run the routeUpdate on
        String routeQuery = "INSERT INTO ROUTE_DATA(airline, airline_id, source_airport, source_airport_id, " +
                "destination_airport, destination_airport_id, codeshare, stops, equipment) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to retrieve the route data from the database
        String routeIdQuery = "SELECT route_id FROM ROUTE_DATA WHERE airline = ? and " +
                "source_airport = ? and destination_airport = ? and codeshare = ? and stops = ? and equipment = ?";

        // SQLite query used to retrieve airline_id in order to be passed into a piece of route data
        String airlineIdQuery = "SELECT airline_id FROM AIRLINE_DATA WHERE iata = ? and airline_name = ? and alias = ?";

        // SQLite query used to retrieve airport_id in order to be passed into a piece of route data
        String airportIdQuery = "SELECT airport_id FROM AIRPORT_DATA WHERE airport_name = ? and city = ?";

        // SQLite query used to count the number of routes with a specific id
        String routeCountQuery = "SELECT COUNT(route_id) FROM ROUTE_DATA WHERE route_id = ?";




        // Creating a statement that is then given airport data,  and then executed, inserting it into the database
        // This will be used as the source airport
        PreparedStatement stmtSrcAirport = dbHandler.prepareStatement(airportQuery);
        List<Object> tmpSrcAirport = Arrays.asList("Heathrow", "London", "England", "ATA", "FJLJ", 89, 123.2, 5000, 43, "JFI", "TZ");
        ArrayList<Object> testSrcAirport = new ArrayList<>(tmpSrcAirport);
        for (int i=1; i < 12; i++) {
            stmtSrcAirport.setObject(i, testSrcAirport.get(i-1));
        }
        stmtSrcAirport.executeUpdate();

        // Creating a statement that is then given airport data,  and then executed, inserting it into the database
        // This will be used as the destination airport
        PreparedStatement stmtDstAirport = dbHandler.prepareStatement(airportQuery);
        List<Object> tmpDstAirport = Arrays.asList("ChCh", "Christchurch", "New Zealand", "AUS", "NZNZ", 70, 231, 6000, 42, "JFP", "TZF");
        ArrayList<Object> testDstAirport = new ArrayList<>(tmpDstAirport);
        for (int i=1; i < 12; i++) {
            stmtDstAirport.setObject(i, testDstAirport.get(i-1));
        }
        stmtDstAirport.executeUpdate();



        // Creating a statement that is then given airline data, and then executed, inserting it into the database
        // This will be airline for the route data
        PreparedStatement stmtAirline = dbHandler.prepareStatement(airLineQuery);
        List<String> tmpAirline = Arrays.asList("testName", "testAlias", airline, "iopd", "testCallsign", "Argentina", "Y");
        ArrayList<String> testAirline = new ArrayList<>(tmpAirline);
        for (int i=1; i < 8; i++) {
            stmtAirline.setObject(i, testAirline.get(i-1));
        }
        stmtAirline.executeUpdate();


        // Creating a statement that will retrieve the airlineId from the database
        PreparedStatement stmtAirlineID = dbHandler.prepareStatement(airlineIdQuery);
        List<String> tmpAirlineID = Arrays.asList("ITA", "testName", "testAlias");
        ArrayList<String> airlineIdList = new ArrayList<>(tmpAirlineID);
        for (int i=1; i < 4; i++) {
            stmtAirlineID.setObject(i, airlineIdList.get(i-1));
        }
        ResultSet result = stmtAirlineID.executeQuery();
        int testAirlineId = result.getInt(1);


        // Creating a statement that will retrieve the srcAirportId from the database
        PreparedStatement stmtSrcAirportId = dbHandler.prepareStatement(airportIdQuery);
        List<String> tmpSrcAirportId = Arrays.asList("Heathrow", "London");
        ArrayList<String> srcAirportIdList = new ArrayList<>(tmpSrcAirportId);
        for (int i=1; i < 3; i++) {
            stmtSrcAirportId.setObject(i, srcAirportIdList.get(i-1));
        }
        ResultSet resultSrcAirport = stmtSrcAirportId.executeQuery();
        int srcAirportId = resultSrcAirport.getInt(1);


        // Creating a statement that will retrieve the dstAirportId from the database
        PreparedStatement stmtDstAirportId = dbHandler.prepareStatement(airportIdQuery);
        List<String> tmpDstAirportId = Arrays.asList("ChCh", "Christchurch");
        ArrayList<String> dstAirportIdList = new ArrayList<>(tmpDstAirportId);
        for (int i=1; i < 3; i++) {
            stmtDstAirportId.setObject(i, dstAirportIdList.get(i-1));
        }
        ResultSet resultDstAirport = stmtDstAirportId.executeQuery();
        int dstAirportId = resultDstAirport.getInt(1);


        // Creating a statement that is then given routeData, and then executed, inserting it into the database
        // This is the route that will be deleted using the routeService's deleteRoute() method
        PreparedStatement stmtRoute = dbHandler.prepareStatement(routeQuery);
        List<Object> tmpRoute = Arrays.asList(airline, testAirlineId, sourceAirport, testSrcAirport, destinationAirport, dstAirportId, codeShare, stops, equipment);
        ArrayList<Object> testRouteList = new ArrayList<>(tmpRoute);
        for (int i=1; i < 10; i++) {
            stmtRoute.setObject(i, testRouteList.get(i-1));
        }
        stmtRoute.executeUpdate();

        // Creating a statement that will get the routeId to be used in the deleteRoute statement
        PreparedStatement stmtRouteId = dbHandler.prepareStatement(routeIdQuery);
        List<Object> tmpRouteId = Arrays.asList(airline, sourceAirport, destinationAirport, codeShare, stops, equipment);
        ArrayList<Object> testRouteIdList = new ArrayList<>(tmpRouteId);
        for (int i =1; i < 7; i++) {
            stmtRouteId.setObject(i, testRouteIdList.get(i-1));
        }
        ResultSet resultRouteId = stmtRouteId.executeQuery();
        int testRouteId = resultRouteId.getInt(1);

        // running the routeService deleteRoute method (the method being tested)
        boolean res = routeService.deleteRoute(testRouteId);


        // Creating a statement to get the count of routes, in order to check if the route has been deleted
        PreparedStatement stmtRouteCount = dbHandler.prepareStatement(routeCountQuery);
        stmtRouteCount.setInt(1, testRouteId);
        ResultSet routeCountResult = stmtRouteCount.executeQuery();
        int count = routeCountResult.getInt(1);
        assertEquals(0, count);

    }


    public void testInvalidDeleteRoute() throws SQLException {

        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();

        // Attempting to delete a route that doesnt exist
        boolean res = routeService.deleteRoute(4);

        assertFalse(res);

    }


    public void testGetRoute() throws SQLException {
        // Setting up variables for a test Route
        String airline = "ITA";
        String sourceAirport = "ATA";
        String destinationAirport = "AUS";
        String codeShare = "Y";
        int stops = 6;
        String equipment = "AJF";

        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();


        // SQLite query used to populate database with the required data to run the routeService saveRoute() method
        String airportQuery = "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                + "longitude, altitude, timezone, dst, tz_database_timezone) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate the database with the required data to run the RouteService saveRoute() method
        String airLineQuery = "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, "
                + "callsign, country, active) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate the database with the route data required to run the routeUpdate on
        String routeQuery = "INSERT INTO ROUTE_DATA(airline, airline_id, source_airport, source_airport_id, " +
                "destination_airport, destination_airport_id, codeshare, stops, equipment) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to retrieve the route data from the database
        String routeDataQuery = "SELECT * FROM ROUTE_DATA WHERE route_id = ?";

        // SQLite query used to retrieve airline_id in order to be passed into a piece of route data
        String airlineIdQuery = "SELECT airline_id FROM AIRLINE_DATA WHERE iata = ? and airline_name = ? and alias = ?";

        // SQLite query used to retrieve airport_id in order to be passed into a piece of route data
        String airportIdQuery = "SELECT airport_id FROM AIRPORT_DATA WHERE airport_name = ? and city = ?";

        // SQLite query used to retrieve the route data from the database
        String routeIdQuery = "SELECT route_id FROM ROUTE_DATA WHERE airline = ? and " +
                "source_airport = ? and destination_airport = ? and codeshare = ? and stops = ? and equipment = ?";



        // Creating a statement that is then given airport data,  and then executed, inserting it into the database
        // This will be used as the source airport
        PreparedStatement stmtSrcAirport = dbHandler.prepareStatement(airportQuery);
        List<Object> tmpSrcAirport = Arrays.asList("Heathrow", "London", "England", "ATA", "FJLJ", 89, 123.2, 5000, 43, "JFI", "TZ");
        ArrayList<Object> testSrcAirport = new ArrayList<>(tmpSrcAirport);
        for (int i=1; i < 12; i++) {
            stmtSrcAirport.setObject(i, testSrcAirport.get(i-1));
        }
        stmtSrcAirport.executeUpdate();

        // Creating a statement that is then given airport data,  and then executed, inserting it into the database
        // This will be used as the destination airport
        PreparedStatement stmtDstAirport = dbHandler.prepareStatement(airportQuery);
        List<Object> tmpDstAirport = Arrays.asList("ChCh", "Christchurch", "New Zealand", "AUS", "NZNZ", 70, 231, 6000, 42, "JFP", "TZF");
        ArrayList<Object> testDstAirport = new ArrayList<>(tmpDstAirport);
        for (int i=1; i < 12; i++) {
            stmtDstAirport.setObject(i, testDstAirport.get(i-1));
        }
        stmtDstAirport.executeUpdate();

        // Creating a statement that is then given airline data, and then executed, inserting it into the database
        // This will be airline for the route data
        PreparedStatement stmtAirline = dbHandler.prepareStatement(airLineQuery);
        List<String> tmpAirline = Arrays.asList("testName", "testAlias", airline, "iopd", "testCallsign", "Argentina", "Y");
        ArrayList<String> testAirline = new ArrayList<>(tmpAirline);
        for (int i=1; i < 8; i++) {
            stmtAirline.setObject(i, testAirline.get(i-1));
        }
        stmtAirline.executeUpdate();

        // Creating a statement that will retrieve the airlineId from the database
        PreparedStatement stmtAirlineID = dbHandler.prepareStatement(airlineIdQuery);
        List<String> tmpAirlineID = Arrays.asList("ITA", "testName", "testAlias");
        ArrayList<String> airlineIdList = new ArrayList<>(tmpAirlineID);
        for (int i=1; i < 4; i++) {
            stmtAirlineID.setObject(i, airlineIdList.get(i-1));
        }
        ResultSet result = stmtAirlineID.executeQuery();
        int testAirlineId = result.getInt(1);

        // Creating a statement that will retrieve the dstAirportId from the database
        PreparedStatement stmtDstAirportId = dbHandler.prepareStatement(airportIdQuery);
        List<String> tmpDstAirportId = Arrays.asList("ChCh", "Christchurch");
        ArrayList<String> dstAirportIdList = new ArrayList<>(tmpDstAirportId);
        for (int i=1; i < 3; i++) {
            stmtDstAirportId.setObject(i, dstAirportIdList.get(i-1));
        }
        ResultSet resultDstAirport = stmtDstAirportId.executeQuery();
        int dstAirportId = resultDstAirport.getInt(1);

        // Creating a statement that is then given routeData, and then executed, inserting it into the database
        // This is the route that will be deleted using the routeService's deleteRoute() method
        PreparedStatement stmtRoute = dbHandler.prepareStatement(routeQuery);
        List<Object> tmpRoute = Arrays.asList(airline, testAirlineId, sourceAirport, testSrcAirport, destinationAirport, dstAirportId, codeShare, stops, equipment);
        ArrayList<Object> testRouteList = new ArrayList<>(tmpRoute);
        for (int i=1; i < 10; i++) {
            stmtRoute.setObject(i, testRouteList.get(i-1));
        }
        stmtRoute.executeUpdate();

        PreparedStatement stmtRouteId = dbHandler.prepareStatement(routeIdQuery);
        List<Object> tmpRouteIdList = Arrays.asList(airline, sourceAirport, destinationAirport, codeShare, stops, equipment);
        ArrayList<Object> testRouteIdList = new ArrayList<>(tmpRouteIdList);
        for (int i=1; i < 7; i++) {
            stmtRouteId.setObject(i, testRouteIdList.get(i-1));
        }
        ResultSet routeIdSet = stmtRouteId.executeQuery();
        int actualId = routeIdSet.getInt(1);

        // Retrieving the route from the database
        ResultSet routeRetrieved = routeService.getRoute(testAirlineId);

        // Preparing a statement to retrieving to retrieve the expected route from the database
        PreparedStatement stmtExpectedRoute = dbHandler.prepareStatement(routeDataQuery);
        stmtExpectedRoute.setObject(1, testAirlineId);
        ResultSet routeExpected = stmtExpectedRoute.executeQuery();

        // Checking equality between actual and expected
        for (int i = 1; i < 11; i++) {
            assertEquals(routeRetrieved.getObject(i), routeExpected.getObject((i)));
        }

        // Checking the getRoute returns an empty resultSet when an id not in use given
        assertFalse(routeService.getRoute(53423412).next());


    }



    public void testGetRoutes() throws SQLException {
        // Setting up variables for a test Route
        String airline = "ITA";
        String sourceAirport = "ATA";
        String destinationAirport = "AUS";
        String codeShare = "Y";
        int stops = 6;
        String equipment = "AJF";

        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();


        // SQLite query used to populate database with the required data to run the routeService saveRoute() method
        String airportQuery = "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                + "longitude, altitude, timezone, dst, tz_database_timezone) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate the database with the required data to run the RouteService saveRoute() method
        String airLineQuery = "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, "
                + "callsign, country, active) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate the database with the route data required to run the routeUpdate on
        String routeQuery = "INSERT INTO ROUTE_DATA(airline, airline_id, source_airport, source_airport_id, " +
                "destination_airport, destination_airport_id, codeshare, stops, equipment) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to retrieve the route data from the database
        String routeDataQuery = "SELECT source_airport, destination_airport, stops, equipment FROM ROUTE_DATA WHERE route_id = ?";

        // SQLite query used to retrieve all the route data from the database for a certain id
        String routeAllDataQuery = "SELECT * FROM ROUTE_DATA WHERE route_id = ?";

        // SQLite query used to retrieve airline_id in order to be passed into a piece of route data
        String airlineIdQuery = "SELECT airline_id FROM AIRLINE_DATA WHERE iata = ? and airline_name = ? and alias = ?";

        // SQLite query used to retrieve airport_id in order to be passed into a piece of route data
        String airportIdQuery = "SELECT airport_id FROM AIRPORT_DATA WHERE airport_name = ? and city = ?";

        // SQLite query used to retrieve the route data from the database
        String routeIdQuery = "SELECT route_id FROM ROUTE_DATA WHERE airline = ? and " +
                "source_airport = ? and destination_airport = ? and codeshare = ? and stops = ? and equipment = ?";



        // Creating a statement that is then given airport data,  and then executed, inserting it into the database
        // This will be used as the source airport
        PreparedStatement stmtSrcAirport = dbHandler.prepareStatement(airportQuery);
        List<Object> tmpSrcAirport = Arrays.asList("Heathrow", "London", "England", "ATA", "FJLJ", 89, 123.2, 5000, 43, "JFI", "TZ");
        ArrayList<Object> testSrcAirport = new ArrayList<>(tmpSrcAirport);
        for (int i=1; i < 12; i++) {
            stmtSrcAirport.setObject(i, testSrcAirport.get(i-1));
        }
        stmtSrcAirport.executeUpdate();

        // Creating a statement that is then given airport data,  and then executed, inserting it into the database
        // This will be used as the destination airport
        PreparedStatement stmtDstAirport = dbHandler.prepareStatement(airportQuery);
        List<Object> tmpDstAirport = Arrays.asList("ChCh", "Christchurch", "New Zealand", "AUS", "NZNZ", 70, 231, 6000, 42, "JFP", "TZF");
        ArrayList<Object> testDstAirport = new ArrayList<>(tmpDstAirport);
        for (int i=1; i < 12; i++) {
            stmtDstAirport.setObject(i, testDstAirport.get(i-1));
        }
        stmtDstAirport.executeUpdate();

        // Creating a statement that is then given airline data, and then executed, inserting it into the database
        // This will be airline for the route data
        PreparedStatement stmtAirline = dbHandler.prepareStatement(airLineQuery);
        List<String> tmpAirline = Arrays.asList("testName", "testAlias", airline, "iopd", "testCallsign", "Argentina", "Y");
        ArrayList<String> testAirline = new ArrayList<>(tmpAirline);
        for (int i=1; i < 8; i++) {
            stmtAirline.setObject(i, testAirline.get(i-1));
        }
        stmtAirline.executeUpdate();

        // Creating a statement that will retrieve the airlineId from the database
        PreparedStatement stmtAirlineID = dbHandler.prepareStatement(airlineIdQuery);
        List<String> tmpAirlineID = Arrays.asList("ITA", "testName", "testAlias");
        ArrayList<String> airlineIdList = new ArrayList<>(tmpAirlineID);
        for (int i=1; i < 4; i++) {
            stmtAirlineID.setObject(i, airlineIdList.get(i-1));
        }
        ResultSet result = stmtAirlineID.executeQuery();
        int testAirlineId = result.getInt(1);

        // Creating a statement that will retrieve the dstAirportId from the database
        PreparedStatement stmtDstAirportId = dbHandler.prepareStatement(airportIdQuery);
        List<String> tmpDstAirportId = Arrays.asList("ChCh", "Christchurch");
        ArrayList<String> dstAirportIdList = new ArrayList<>(tmpDstAirportId);
        for (int i=1; i < 3; i++) {
            stmtDstAirportId.setObject(i, dstAirportIdList.get(i-1));
        }
        ResultSet resultDstAirport = stmtDstAirportId.executeQuery();
        int dstAirportId = resultDstAirport.getInt(1);

        // Creating a statement that is then given routeData, and then executed, inserting it into the database
        // This is the route that will be deleted using the routeService's deleteRoute() method
        PreparedStatement stmtRoute = dbHandler.prepareStatement(routeQuery);
        List<Object> tmpRoute = Arrays.asList(airline, testAirlineId, sourceAirport, testSrcAirport, destinationAirport, dstAirportId, codeShare, stops, equipment);
        ArrayList<Object> testRouteList = new ArrayList<>(tmpRoute);
        for (int i=1; i < 10; i++) {
            stmtRoute.setObject(i, testRouteList.get(i-1));
        }
        stmtRoute.executeUpdate();

        // Creating a statement that will retrieve the routeID of the route we just inserted into the database
        PreparedStatement stmtRouteId = dbHandler.prepareStatement(routeIdQuery);
        List<Object> tmpRouteIdList = Arrays.asList(airline, sourceAirport, destinationAirport, codeShare, stops, equipment);
        ArrayList<Object> testRouteIdList = new ArrayList<>(tmpRouteIdList);
        for (int i=1; i < 7; i++) {
            stmtRouteId.setObject(i, testRouteIdList.get(i-1));
        }
        ResultSet routeIdSet = stmtRouteId.executeQuery();
        int actualId = routeIdSet.getInt(1);

        // Retrieving the routes that match the actual result
        ResultSet actualResult = routeService.getRoutes("Heathrow", "ChCh", stops, equipment);

        // Getting the expected result from the database
        PreparedStatement expectedResultStmt = dbHandler.prepareStatement(routeAllDataQuery);
        expectedResultStmt.setObject(1, actualId);
        ResultSet expectedResult = expectedResultStmt.executeQuery();

        // Comparing the actual and expected results
        for (int i = 1; i < 11; i++) {
            assertEquals(expectedResult.getObject(i), actualResult.getObject(i));
        }

        // Checking the getRoutes method can deal with invalid airports
        ResultSet invalidRoutes = routeService.getRoutes("lesnfslk", "elfsknef", 434, "ENS");
        assertFalse(invalidRoutes.next());

    }


    public void testCodeShareIsValid() {
        // Testing if codeShareIsValid behaves as expected
        assertTrue(routeService.codeshareIsValid(null));
        assertTrue(routeService.codeshareIsValid("Y"));
        assertFalse(routeService.codeshareIsValid("N"));
    }


    public void testEquipmentIsValid() {
        // Testing if equipmentIsValid behaves as expected
        assertTrue(routeService.equipmentIsValid("NFE"));
        assertTrue(routeService.equipmentIsValid("NFE ENF"));
        assertTrue(routeService.equipmentIsValid("NFE NES LAP"));
        assertFalse(routeService.equipmentIsValid(""));
        assertFalse(routeService.equipmentIsValid(null));
        assertFalse(routeService.equipmentIsValid("FSFE"));
        assertFalse(routeService.equipmentIsValid("JEN ELNS"));

    }

    public void testGetMaxId() throws SQLException {
        // Checking max id is as expected
        assertEquals(0, routeService.getMaxID());

        // Setting up variables for a test Route
        String airline = "ITA";
        String sourceAirport = "ATA";
        String destinationAirport = "AUS";
        String codeShare = "Y";
        int stops = 6;
        String equipment = "AJF";

        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();


        // SQLite query used to populate database with the required data to run the routeService saveRoute() method
        String airportQuery = "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                + "longitude, altitude, timezone, dst, tz_database_timezone) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate the database with the required data to run the RouteService saveRoute() method
        String airLineQuery = "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, "
                + "callsign, country, active) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate the database with the route data required to run the routeUpdate on
        String routeQuery = "INSERT INTO ROUTE_DATA(airline, airline_id, source_airport, source_airport_id, " +
                "destination_airport, destination_airport_id, codeshare, stops, equipment) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to retrieve the route data from the database
        String routeDataQuery = "SELECT source_airport, destination_airport, stops, equipment FROM ROUTE_DATA WHERE route_id = ?";

        // SQLite query used to retrieve all the route data from the database for a certain id
        String routeAllDataQuery = "SELECT * FROM ROUTE_DATA WHERE route_id = ?";

        // SQLite query used to retrieve airline_id in order to be passed into a piece of route data
        String airlineIdQuery = "SELECT airline_id FROM AIRLINE_DATA WHERE iata = ? and airline_name = ? and alias = ?";

        // SQLite query used to retrieve airport_id in order to be passed into a piece of route data
        String airportIdQuery = "SELECT airport_id FROM AIRPORT_DATA WHERE airport_name = ? and city = ?";

        // SQLite query used to retrieve the route data from the database
        String routeIdQuery = "SELECT route_id FROM ROUTE_DATA WHERE airline = ? and " +
                "source_airport = ? and destination_airport = ? and codeshare = ? and stops = ? and equipment = ?";



        // Creating a statement that is then given airport data,  and then executed, inserting it into the database
        // This will be used as the source airport
        PreparedStatement stmtSrcAirport = dbHandler.prepareStatement(airportQuery);
        List<Object> tmpSrcAirport = Arrays.asList("Heathrow", "London", "England", "ATA", "FJLJ", 89, 123.2, 5000, 43, "JFI", "TZ");
        ArrayList<Object> testSrcAirport = new ArrayList<>(tmpSrcAirport);
        for (int i=1; i < 12; i++) {
            stmtSrcAirport.setObject(i, testSrcAirport.get(i-1));
        }
        stmtSrcAirport.executeUpdate();

        // Creating a statement that is then given airport data,  and then executed, inserting it into the database
        // This will be used as the destination airport
        PreparedStatement stmtDstAirport = dbHandler.prepareStatement(airportQuery);
        List<Object> tmpDstAirport = Arrays.asList("ChCh", "Christchurch", "New Zealand", "AUS", "NZNZ", 70, 231, 6000, 42, "JFP", "TZF");
        ArrayList<Object> testDstAirport = new ArrayList<>(tmpDstAirport);
        for (int i=1; i < 12; i++) {
            stmtDstAirport.setObject(i, testDstAirport.get(i-1));
        }
        stmtDstAirport.executeUpdate();

        // Creating a statement that is then given airline data, and then executed, inserting it into the database
        // This will be airline for the route data
        PreparedStatement stmtAirline = dbHandler.prepareStatement(airLineQuery);
        List<String> tmpAirline = Arrays.asList("testName", "testAlias", airline, "iopd", "testCallsign", "Argentina", "Y");
        ArrayList<String> testAirline = new ArrayList<>(tmpAirline);
        for (int i=1; i < 8; i++) {
            stmtAirline.setObject(i, testAirline.get(i-1));
        }
        stmtAirline.executeUpdate();

        // Creating a statement that will retrieve the airlineId from the database
        PreparedStatement stmtAirlineID = dbHandler.prepareStatement(airlineIdQuery);
        List<String> tmpAirlineID = Arrays.asList("ITA", "testName", "testAlias");
        ArrayList<String> airlineIdList = new ArrayList<>(tmpAirlineID);
        for (int i=1; i < 4; i++) {
            stmtAirlineID.setObject(i, airlineIdList.get(i-1));
        }
        ResultSet result = stmtAirlineID.executeQuery();
        int testAirlineId = result.getInt(1);

        // Creating a statement that will retrieve the dstAirportId from the database
        PreparedStatement stmtDstAirportId = dbHandler.prepareStatement(airportIdQuery);
        List<String> tmpDstAirportId = Arrays.asList("ChCh", "Christchurch");
        ArrayList<String> dstAirportIdList = new ArrayList<>(tmpDstAirportId);
        for (int i=1; i < 3; i++) {
            stmtDstAirportId.setObject(i, dstAirportIdList.get(i-1));
        }
        ResultSet resultDstAirport = stmtDstAirportId.executeQuery();
        int dstAirportId = resultDstAirport.getInt(1);

        // Creating a statement that is then given routeData, and then executed, inserting it into the database
        // This is the route that will be deleted using the routeService's deleteRoute() method
        PreparedStatement stmtRoute = dbHandler.prepareStatement(routeQuery);
        List<Object> tmpRoute = Arrays.asList(airline, testAirlineId, sourceAirport, testSrcAirport, destinationAirport, dstAirportId, codeShare, stops, equipment);
        ArrayList<Object> testRouteList = new ArrayList<>(tmpRoute);
        for (int i=1; i < 10; i++) {
            stmtRoute.setObject(i, testRouteList.get(i-1));
        }
        stmtRoute.executeUpdate();

        // Checking max id as expected
        assertEquals(1, routeService.getMaxID());

    }


}
