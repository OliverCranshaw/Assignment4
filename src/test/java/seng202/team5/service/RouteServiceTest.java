package seng202.team5.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import seng202.team5.database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class RouteServiceTest extends BaseDatabaseTest {

    private RouteService routeService;

    @Before
    public void setUp() {
        super.setUp();
        routeService = new RouteService();
    }


    @Test
    public void testInitialState() throws SQLException {
        ResultSet stuff = routeService.getData(null, null, 0, null); // delete later comment for a push
        Assert.assertFalse(stuff.next());
    }


    @Test
    public void testAddValidRoute() throws SQLException {
        // Setting up variables for a test Route
        String airline = "IT";
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
        List<String> tmp = Arrays.asList("testName", "testAlias", airline, "iop", "testCallsign", "Argentina", "Y");
        ArrayList<String> testAirline = new ArrayList<>(tmp);
        for (int i=1; i < 8; i++) {
            stmt.setObject(i, testAirline.get(i-1));
        }
        stmt.executeUpdate();


        // Running the saveRoute() of the routeService. This is the method being tested.
        int res = routeService.save(airline, sourceAirport, destinationAirport, codeShare, stops, equipment);

        // Ensuring the routeService returns no errors
        Assert.assertEquals(1, res);


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
                Assert.assertEquals(result.getString(i), parameters.get(index++));
            } else if (i == 9) {
                Assert.assertEquals(result.getInt(i), parameters.get(index++));
            }
        }

        // Closing connection with the database
        dbHandler.close();
    }


    @Test
    public void testAddInvalidRoute() throws SQLException {
        // Setting up variables for a test Route
        String airline = "IT";
        String sourceAirport = "AAA";
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
        List<String> tmp = Arrays.asList("testName", "testAlias", airline, "iop", "testCallsign", "Argentina", "Y");
        ArrayList<String> testAirline = new ArrayList<>(tmp);
        for (int i=1; i < 8; i++) {
            stmt.setObject(i, testAirline.get(i-1));
        }
        stmt.executeUpdate();


        // Running the saveRoute() of the routeService. This is the method being tested.
        int res = routeService.save(airline, sourceAirport, destinationAirport, codeShare, stops, equipment);
        Assert.assertEquals(-3, res);


        // Creating a statement that will retrieve that route data from the database, and the executing it
        PreparedStatement stmtRoute = dbHandler.prepareStatement(routeQuery);
        for (int i = 1; i < 7; i++) {
            stmtRoute.setObject(i, parameters.get(i-1));
        }
        ResultSet result = stmtRoute.executeQuery();
        Assert.assertTrue(result.isClosed());


        // Closing connection with the database
        dbHandler.close();
    }


    @Test
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
        List<Object> tmpRoute = Arrays.asList(airline, testAirlineId, sourceAirport, srcAirportId, destinationAirport, dstAirportId, codeShare, stops, equipment);
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
        int res = routeService.update(testRouteId, airline, sourceAirport, "JPN", codeShare, stops, equipment);


        // Creating a statement to retrieve all data of the route.
        Statement stmtRouteFinal = dbHandler.createStatement();
        ResultSet resultRouteFinal = stmtRouteFinal.executeQuery(routeFinalQuery);


        // Checking all of the route data is the same, bar the destination airport information
        Assert.assertEquals(testRouteId, resultRouteFinal.getInt(1));
        Assert.assertEquals(airline, resultRouteFinal.getString(2));
        Assert.assertEquals(testAirlineId, resultRouteFinal.getInt(3));
        Assert.assertEquals(sourceAirport, resultRouteFinal.getString(4));
        Assert.assertEquals(srcAirportId, resultRouteFinal.getInt(5));
        Assert.assertEquals("JPN", resultRouteFinal.getString(6));
        Assert.assertEquals(altAirportId, resultRouteFinal.getInt(7));
        Assert.assertEquals(codeShare, resultRouteFinal.getString(8));
        Assert.assertEquals(stops, resultRouteFinal.getInt(9));
        Assert.assertEquals(equipment, resultRouteFinal.getString(10));

        // Closing database connection
        dbHandler.close();
    }


    @Test
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
        String airlineIdQuery = "SELECT airline_id FROM AIRLINE_DATA WHERE iata = ? or icao = ?";

        // SQLite query used to retrieve airport_id in order to be passed into a piece of route data
        String airportIdQuery = "SELECT airport_id FROM AIRPORT_DATA WHERE iata = ? or icao = ?";



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
        String tmpAirlineCode = "ITA";
        for (int i=1; i < 3; i++) {
            stmtAirlineID.setObject(i, tmpAirlineCode);
        }
        ResultSet result = stmtAirlineID.executeQuery();
        int testAirlineId = result.getInt("airline_id");


        // Creating a statement that will retrieve the srcAirportId from the database
        PreparedStatement stmtSrcAirportId = dbHandler.prepareStatement(airportIdQuery);
        String tmpSrcAirportCode = "ATA";
        for (int i=1; i < 3; i++) {
            stmtSrcAirportId.setObject(i, tmpSrcAirportCode);
        }
        ResultSet resultSrcAirport = stmtSrcAirportId.executeQuery();
        int srcAirportId = resultSrcAirport.getInt("airport_id");


        // Creating a statement that will retrieve the dstAirportId from the database
        PreparedStatement stmtDstAirportId = dbHandler.prepareStatement(airportIdQuery);
        String tmpDstAirportCode = "AUS";
        for (int i=1; i < 3; i++) {
            stmtDstAirportId.setObject(i, tmpDstAirportCode);
        }
        ResultSet resultDstAirport = stmtDstAirportId.executeQuery();
        int dstAirportId = resultDstAirport.getInt("airport_id");


        // Creating a statement that is then given routeData, and then executed, inserting it into the database
        // This is the route that will be updated using the updateRoute method
        PreparedStatement stmtRoute = dbHandler.prepareStatement(routeQuery);
        List<Object> tmpRoute = Arrays.asList(airline, testAirlineId, sourceAirport, srcAirportId, destinationAirport, dstAirportId, codeShare, stops, equipment);
        for (int i = 1; i< 10; i++) {
            stmtRoute.setObject(i, tmpRoute.get(i - 1));
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
        int res = routeService.update(testRouteId, airline, sourceAirport, "JON", codeShare, stops, equipment);
        // Running the updateRoute method, using a routeId that doesn't exist
        int res2 = routeService.update(4, airline, sourceAirport, destinationAirport, codeShare, stops, equipment);


        Assert.assertEquals(-1, res);
        Assert.assertEquals(0, res2);
    }


    @Test
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
        String airlineIdQuery = "SELECT airline_id FROM AIRLINE_DATA WHERE iata = ? or icao = ?";

        // SQLite query used to retrieve airport_id in order to be passed into a piece of route data
        String airportIdQuery = "SELECT airport_id FROM AIRPORT_DATA WHERE iata = ? or icao = ?";

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
        String tmpAirlineCode = "ITA";
        for (int i=1; i < 3; i++) {
            stmtAirlineID.setObject(i, tmpAirlineCode);
        }
        ResultSet result = stmtAirlineID.executeQuery();
        int testAirlineId = result.getInt("airline_id");


        // Creating a statement that will retrieve the srcAirportId from the database
        PreparedStatement stmtSrcAirportId = dbHandler.prepareStatement(airportIdQuery);
        String tmpSrcAirportCode = "ATA";
        for (int i=1; i < 3; i++) {
            stmtSrcAirportId.setObject(i, tmpSrcAirportCode);
        }
        ResultSet resultSrcAirport = stmtSrcAirportId.executeQuery();
        int srcAirportId = resultSrcAirport.getInt("airport_id");


        // Creating a statement that will retrieve the dstAirportId from the database
        PreparedStatement stmtDstAirportId = dbHandler.prepareStatement(airportIdQuery);
        String tmpDstAirportCode = "AUS";
        for (int i=1; i < 3; i++) {
            stmtDstAirportId.setObject(i, tmpDstAirportCode);
        }
        ResultSet resultDstAirport = stmtDstAirportId.executeQuery();
        int dstAirportId = resultDstAirport.getInt("airport_id");


        // Creating a statement that is then given routeData, and then executed, inserting it into the database
        // This is the route that will be deleted using the routeService's deleteRoute() method
        PreparedStatement stmtRoute = dbHandler.prepareStatement(routeQuery);
        List<Object> tmpRoute = Arrays.asList(airline, testAirlineId, sourceAirport, srcAirportId, destinationAirport, dstAirportId, codeShare, stops, equipment);
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
        boolean res = routeService.delete(testRouteId);
        Assert.assertTrue(res);

        // Creating a statement to get the count of routes, in order to check if the route has been deleted
        PreparedStatement stmtRouteCount = dbHandler.prepareStatement(routeCountQuery);
        stmtRouteCount.setInt(1, testRouteId);
        ResultSet routeCountResult = stmtRouteCount.executeQuery();
        int count = routeCountResult.getInt(1);

        Assert.assertEquals(0, count);
    }


    @Test
    public void testInvalidDeleteRoute() {

        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();

        // Attempting to delete a route that doesnt exist
        boolean res = routeService.delete(4);

        Assert.assertFalse(res);
    }


    @Test
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

        // Creating a statement that is then given routeData, and then executed, inserting it into the database
        // This is the route that will be deleted using the routeService's deleteRoute() method
        PreparedStatement stmtRoute = dbHandler.prepareStatement(routeQuery);
        List<Object> tmpRoute = Arrays.asList(airline, 1, sourceAirport, 1, destinationAirport, 2, codeShare, stops, equipment);
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

        Assert.assertEquals(1, actualId);

        // Retrieving the route from the database
        ResultSet routeRetrieved = routeService.getData(1);

        // Preparing a statement to retrieving to retrieve the expected route from the database
        PreparedStatement stmtExpectedRoute = dbHandler.prepareStatement(routeDataQuery);
        stmtExpectedRoute.setObject(1, 1);
        ResultSet routeExpected = stmtExpectedRoute.executeQuery();

        // Checking equality between actual and expected
        for (int i = 1; i < 11; i++) {
            Assert.assertEquals(routeRetrieved.getObject(i), routeExpected.getObject((i)));
        }

        // Checking the getRoute returns an empty resultSet when an id not in use given
        Assert.assertFalse(routeService.getData(53423412).next());
    }


    @Test
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

        // SQLite query used to retrieve all the route data from the database for a certain id
        String routeAllDataQuery = "SELECT * FROM ROUTE_DATA WHERE route_id = ?";

        // SQLite query used to retrieve airline_id in order to be passed into a piece of route data
        String airlineIdQuery = "SELECT airline_id FROM AIRLINE_DATA WHERE iata = ? or icao = ?";

        // SQLite query used to retrieve airport_id in order to be passed into a piece of route data
        String airportIdQuery = "SELECT airport_id FROM AIRPORT_DATA WHERE iata = ? or icao = ?";

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
        String tmpAirlineCode = "ITA";
        for (int i=1; i < 3; i++) {
            stmtAirlineID.setObject(i, tmpAirlineCode);
        }
        ResultSet result = stmtAirlineID.executeQuery();
        int testAirlineId = result.getInt("airline_id");


        // Creating a statement that will retrieve the srcAirportId from the database
        PreparedStatement stmtSrcAirportId = dbHandler.prepareStatement(airportIdQuery);
        String tmpSrcAirportCode = "ATA";
        for (int i=1; i < 3; i++) {
            stmtSrcAirportId.setObject(i, tmpSrcAirportCode);
        }
        ResultSet resultSrcAirport = stmtSrcAirportId.executeQuery();
        int srcAirportId = resultSrcAirport.getInt("airport_id");


        // Creating a statement that will retrieve the dstAirportId from the database
        PreparedStatement stmtDstAirportId = dbHandler.prepareStatement(airportIdQuery);
        String tmpDstAirportCode = "AUS";
        for (int i=1; i < 3; i++) {
            stmtDstAirportId.setObject(i, tmpDstAirportCode);
        }
        ResultSet resultDstAirport = stmtDstAirportId.executeQuery();
        int dstAirportId = resultDstAirport.getInt("airport_id");


        // Creating a statement that is then given routeData, and then executed, inserting it into the database
        // This is the route that will be deleted using the routeService's deleteRoute() method
        PreparedStatement stmtRoute = dbHandler.prepareStatement(routeQuery);
        List<Object> tmpRoute = Arrays.asList(airline, testAirlineId, sourceAirport, srcAirportId, destinationAirport, dstAirportId, codeShare, stops, equipment);
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
        ResultSet actualResult = routeService.getData("Heathrow", "ChCh", stops, equipment);

        // Getting the expected result from the database
        PreparedStatement expectedResultStmt = dbHandler.prepareStatement(routeAllDataQuery);
        expectedResultStmt.setObject(1, actualId);
        ResultSet expectedResult = expectedResultStmt.executeQuery();

        // Comparing the actual and expected results
        for (int i = 1; i < 11; i++) {
            Assert.assertEquals(expectedResult.getObject(i), actualResult.getObject(i));
        }

        // Checking the getRoutes method can deal with invalid airports
        ResultSet invalidRoutes = routeService.getData("lesnfslk", "elfsknef", 434, "ENS");
        Assert.assertFalse(invalidRoutes.next());
    }
    

    @Test
    public void testGetMaxId() throws SQLException {
        // Checking max id is as expected
        Assert.assertEquals(0, routeService.getMaxID());

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

        // Creating a statement that is then given routeData, and then executed, inserting it into the database
        // This is the route that will be deleted using the routeService's deleteRoute() method
        PreparedStatement stmtRoute = dbHandler.prepareStatement(routeQuery);
        List<Object> tmpRoute = Arrays.asList(airline, 1, sourceAirport, 1, destinationAirport, 2, codeShare, stops, equipment);
        ArrayList<Object> testRouteList = new ArrayList<>(tmpRoute);
        for (int i=1; i < 10; i++) {
            stmtRoute.setObject(i, testRouteList.get(i-1));
        }
        stmtRoute.executeUpdate();

        // Checking max id as expected
        Assert.assertEquals(1, routeService.getMaxID());
    }


    @Test
    public void testGetAirlinesCoveringRoute() throws SQLException {
        // Initializing connection to test database
        Connection dbHandler = DBConnection.getConnection();

        // Initializing services to populate db with test data
        AirlineService airlineService = new AirlineService();
        AirportService airportService = new AirportService();

        // Populating database with test Airlines
        List<String> airline1Data = List.of("name1", "alias1", "I1", "IC1", "callsign1", "country1", "Y");
        List<String> airline2Data = List.of("name2", "alias2", "I2", "IC2", "callsign2", "country2", "N");
        List<String> airline3Data = List.of("name3", "alias3", "I3", "IC3", "callsign3", "country3", "Y");

        airlineService.save(airline1Data.get(0), airline1Data.get(1), airline1Data.get(2), airline1Data.get(3), airline1Data.get(4), airline1Data.get(5), airline1Data.get(6));
        airlineService.save(airline2Data.get(0), airline2Data.get(1), airline2Data.get(2), airline2Data.get(3), airline2Data.get(4), airline2Data.get(5), airline2Data.get(6));
        airlineService.save(airline3Data.get(0), airline3Data.get(1), airline3Data.get(2), airline3Data.get(3), airline3Data.get(4), airline3Data.get(5), airline3Data.get(6));

        // Populating database with test airports
        List<Object> airport1Data = List.of("name1", "city1", "country1", "ia1", "ica1", 1.1, 2.1, 1, 1, "E", "A/B");
        List<Object> airport2Data = List.of("name2", "city2", "country2", "ia2", "ica2", 1.2, 2.2, 2, 2, "E", "A/B");
        List<Object> airport3Data = List.of("name3", "city3", "country3", "ia3", "ica3", 1.3, 2.3, 3, 3, "E", "A/B");

        airportService.save((String) airport1Data.get(0), (String) airport1Data.get(1), (String) airport1Data.get(2), (String) airport1Data.get(3),
                (String)airport1Data.get(4), (Double) airport1Data.get(5), (Double) airport1Data.get(6), (Integer) airport1Data.get(7),
                (Integer) airport1Data.get(8), (String) airport1Data.get(9), (String) airport1Data.get(10));
        airportService.save((String) airport2Data.get(0), (String) airport2Data.get(1), (String) airport2Data.get(2), (String) airport2Data.get(3),
                (String)airport2Data.get(4), (Double) airport2Data.get(5), (Double) airport2Data.get(6), (Integer) airport2Data.get(7),
                (Integer) airport2Data.get(8), (String) airport2Data.get(9), (String) airport2Data.get(10));
        airportService.save((String) airport3Data.get(0), (String) airport3Data.get(1), (String) airport3Data.get(2), (String) airport3Data.get(3),
                (String)airport3Data.get(4), (Double) airport3Data.get(5), (Double) airport3Data.get(6), (Integer) airport3Data.get(7),
                (Integer) airport3Data.get(8), (String) airport3Data.get(9), (String) airport3Data.get(10));

        // Populating database with test routes
        String query = "INSERT INTO ROUTE_DATA(airline, airline_id, source_airport, source_airport_id, "
                + "destination_airport, destination_airport_id, codeshare, stops, equipment) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";


        List<Object> route1Data = List.of(airline1Data.get(2), 1,  airport1Data.get(3), 1, airport2Data.get(3), 2, "Y", 1, "GPS");
        List<Object> route2Data = List.of(airline2Data.get(2), 2,  airport1Data.get(3), 1, airport2Data.get(3), 2, "N", 1, "GPS, CK3");
        List<Object> route3Data = List.of(airline2Data.get(2), 3,  airport3Data.get(3), 3, airport2Data.get(3), 2, "Y", 1, "GPS, CK3");

        PreparedStatement stmt = dbHandler.prepareStatement(query);

        for (int i = 0; i < route1Data.size(); i++) {
            stmt.setObject(i+1, route1Data.get(i));
        }
        stmt.executeUpdate();
        for (int i = 0; i < route2Data.size() - 1; i++) {
            stmt.setObject(i+1, route2Data.get(i));
        }
        stmt.executeUpdate();
        for (int i = 0; i < route3Data.size() - 1; i++) {
            stmt.setObject(i+1, route3Data.get(i));
        }
        stmt.executeUpdate();

        // Testing method behaves as expected
        ArrayList<Integer> results = routeService.getAirlinesCoveringRoute(1, 2, true);
        List<Integer> expectedResult = List.of(1, 2);
        for (int i = 0; i < results.size(); i++) {
            Assert.assertEquals(expectedResult.get(i), results.get(i));
        }

        ArrayList<Integer> result2 = routeService.getAirlinesCoveringRoute(1, 2, false);
        List<Integer> expectedResult2 = List.of(1);
        for (int i = 0; i < result2.size(); i++) {
            Assert.assertEquals(expectedResult2.get(i), result2.get(i));
        }

        ArrayList<Integer> result3 = routeService.getAirlinesCoveringRoute(3, 2, false);
        List<Integer> expectedResult3 = List.of(3);
        for (int i = 0; i < result3.size(); i++) {
            Assert.assertEquals(expectedResult3.get(i), result3.get(i));
        }
    }


    @Test
    public void testGetCountAirlinesCovering() throws SQLException {

        // Initializing connection to test database
        Connection dbHandler = DBConnection.getConnection();

        // Initializing services to populate db with test data
        AirlineService airlineService = new AirlineService();
        AirportService airportService = new AirportService();

        // Populating database with test Airlines
        List<String> airline1Data = List.of("name1", "alias1", "I1", "IC1", "callsign1", "country1", "Y");
        List<String> airline2Data = List.of("name2", "alias2", "I2", "IC2", "callsign2", "country2", "N");
        List<String> airline3Data = List.of("name3", "alias3", "I3", "IC3", "callsign3", "country3", "Y");

        airlineService.save(airline1Data.get(0), airline1Data.get(1), airline1Data.get(2), airline1Data.get(3), airline1Data.get(4), airline1Data.get(5), airline1Data.get(6));
        airlineService.save(airline2Data.get(0), airline2Data.get(1), airline2Data.get(2), airline2Data.get(3), airline2Data.get(4), airline2Data.get(5), airline2Data.get(6));
        airlineService.save(airline3Data.get(0), airline3Data.get(1), airline3Data.get(2), airline3Data.get(3), airline3Data.get(4), airline3Data.get(5), airline3Data.get(6));

        // Populating database with test airports
        List<Object> airport1Data = List.of("name1", "city1", "country1", "ia1", "ica1", 1.1, 2.1, 1, 1, "E", "A/B");
        List<Object> airport2Data = List.of("name2", "city2", "country2", "ia2", "ica2", 1.2, 2.2, 2, 2, "E", "A/B");
        List<Object> airport3Data = List.of("name3", "city3", "country3", "ia3", "ica3", 1.3, 2.3, 3, 3, "E", "A/B");

        airportService.save((String) airport1Data.get(0), (String) airport1Data.get(1), (String) airport1Data.get(2), (String) airport1Data.get(3),
                (String)airport1Data.get(4), (Double) airport1Data.get(5), (Double) airport1Data.get(6), (Integer) airport1Data.get(7),
                (Integer) airport1Data.get(8), (String) airport1Data.get(9), (String) airport1Data.get(10));
        airportService.save((String) airport2Data.get(0), (String) airport2Data.get(1), (String) airport2Data.get(2), (String) airport2Data.get(3),
                (String)airport2Data.get(4), (Double) airport2Data.get(5), (Double) airport2Data.get(6), (Integer) airport2Data.get(7),
                (Integer) airport2Data.get(8), (String) airport2Data.get(9), (String) airport2Data.get(10));
        airportService.save((String) airport3Data.get(0), (String) airport3Data.get(1), (String) airport3Data.get(2), (String) airport3Data.get(3),
                (String)airport3Data.get(4), (Double) airport3Data.get(5), (Double) airport3Data.get(6), (Integer) airport3Data.get(7),
                (Integer) airport3Data.get(8), (String) airport3Data.get(9), (String) airport3Data.get(10));


        // Populating database with test routes
        String query = "INSERT INTO ROUTE_DATA(airline, airline_id, source_airport, source_airport_id, "
                + "destination_airport, destination_airport_id, codeshare, stops, equipment) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";


        List<Object> route1Data = List.of(airline1Data.get(2), 1,  airport1Data.get(3), 1, airport2Data.get(3), 2, "Y", 1, "GPS");
        List<Object> route2Data = List.of(airline2Data.get(2), 2,  airport1Data.get(3), 1, airport2Data.get(3), 2, "N", 1, "GPS, CK3");
        List<Object> route3Data = List.of(airline2Data.get(2), 3,  airport3Data.get(3), 3, airport2Data.get(3), 2, "Y", 1, "GPS, CK3");

        PreparedStatement stmt = dbHandler.prepareStatement(query);

        for (int i = 0; i < route1Data.size(); i++) {
            stmt.setObject(i+1, route1Data.get(i));
        }
        stmt.executeUpdate();
        for (int i = 0; i < route2Data.size() - 1; i++) {
            stmt.setObject(i+1, route2Data.get(i));
        }
        stmt.executeUpdate();
        for (int i = 0; i < route3Data.size() - 1; i++) {
            stmt.setObject(i+1, route3Data.get(i));
        }
        stmt.executeUpdate();

        ArrayList<Integer> routeIds = new ArrayList<>();
        routeIds.add(1);
        routeIds.add(2);
        routeIds.add(3);

        Hashtable<String, Integer> result = routeService.getCountAirlinesCovering(routeIds);
        Integer first = result.get("ia1 - ia2");
        Integer second = result.get("ia3 - ia2");
        Assert.assertEquals(2, (int) first);
        Assert.assertEquals(1, (int) second);


        Hashtable<String, Integer> result2 = routeService.getCountAirlinesCovering(new ArrayList<Integer>());
        Assert.assertEquals(0, result2.size());
    }
}
