package service;

import junit.framework.Test;
import junit.framework.TestSuite;
import seng202.team5.database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seng202.team5.service.BaseDatabaseTest;
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

    public void testAddValidFlight() throws SQLException {

        int flight_id = 5;
        String airline = "VOR";
        String airport = "FSL";
        int altitude = 42;
        double latitude = 4341.1;
        double longitude = 323.2;



        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();



        // SQLite query used to populate the database with the required data to run the RouteService saveRoute() method
        String airLineQuery = "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, "
                + "callsign, country, active) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate database with the required data to run the routeService saveRoute() method
        String airportQuery = "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                + "longitude, altitude, timezone, dst, tz_database_timezone) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        // SQLite query used to retrieve flight data from the database
        String flightQuery = "SELECT * FROM FLIGHT_DATA";


        // Creating a statement that is then given airport data,  and then executed, inserting it into the database
        PreparedStatement stmt2 = dbHandler.prepareStatement(airportQuery);
        List<Object> tmp2 = Arrays.asList("Heathrow", "London", "England", airport, "FJLJ", 89, 123.2, 5000, 43, "JFI", "TZ");
        ArrayList<Object> testAirport1 = new ArrayList<>(tmp2);
        for (int i=1; i < 12; i++) {
            stmt2.setObject(i, testAirport1.get(i-1));
        }
        stmt2.executeUpdate();


        // Creating a statement that is then given airline data, and then executed, inserting it into the database
        PreparedStatement stmt = dbHandler.prepareStatement(airLineQuery);
        List<String> tmp = Arrays.asList("testName", "testAlias", airline, "iopd", "testCallsign", "Argentina", "Y");
        ArrayList<String> testAirline = new ArrayList<>(tmp);
        for (int i=1; i < 8; i++) {
            stmt.setObject(i, testAirline.get(i-1));
        }
        stmt.executeUpdate();

        // Calling the saveFlight() method
        int res = flightService.saveFlight(flightService.getNextFlightID(), airline, airport, altitude, longitude, latitude);

        assertEquals(1, res);

        // Creating a statement that will retrieve the flight data back from the db
        PreparedStatement flightStmt = dbHandler.prepareStatement(flightQuery);
        ResultSet results = flightStmt.executeQuery();

        // Ensuring the received values are the same as the initial ones
        List<Object> tmpExpectedParameters = Arrays.asList(1, 1, airline, airport, altitude, longitude, latitude);
        ArrayList<Object> expectedParameters = new ArrayList<>(tmpExpectedParameters);
        for (int i=1; i < 8; i++) {
            assertEquals(expectedParameters.get(i-1), results.getObject(i));
        }
    }


    public void testAddInvalidFlight() throws SQLException {

        int flight_id = 5;
        String airline = "FFA";
        String airport = "FSL";
        int altitude = 42;
        double latitude = 4341.1;
        double longitude = 323.2;



        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();



        // SQLite query used to populate the database with the required data to run the RouteService saveRoute() method
        String airLineQuery = "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, "
                + "callsign, country, active) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate database with the required data to run the routeService saveRoute() method
        String airportQuery = "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                + "longitude, altitude, timezone, dst, tz_database_timezone) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";



        // Creating a statement that is then given airport data,  and then executed, inserting it into the database
        PreparedStatement stmt2 = dbHandler.prepareStatement(airportQuery);
        List<Object> tmp2 = Arrays.asList("Heathrow", "London", "England", airport, "FJLJ", 89, 123.2, 5000, 43, "JFI", "TZ");
        ArrayList<Object> testAirport1 = new ArrayList<>(tmp2);
        for (int i=1; i < 12; i++) {
            stmt2.setObject(i, testAirport1.get(i-1));
        }
        stmt2.executeUpdate();


        // Creating a statement that is then given airline data, and then executed, inserting it into the database
        PreparedStatement stmt = dbHandler.prepareStatement(airLineQuery);
        List<String> tmp = Arrays.asList("testName", "testAlias", airline, "iopd", "testCallsign", "Argentina", "Y");
        ArrayList<String> testAirline = new ArrayList<>(tmp);
        for (int i=1; i < 8; i++) {
            stmt.setObject(i, testAirline.get(i-1));
        }
        stmt.executeUpdate();

        // Calling the saveFlight() method
        int res = flightService.saveFlight(flightService.getNextFlightID(), airline, "LAX", altitude, longitude, latitude);

        assertEquals(-1, res);


    }



    public void testUpdateFlightValid() throws SQLException {

        int flight_id = 5;
        String airline = "FFA";
        String airport = "FSL";
        int altitude = 42;
        double latitude = 4341.1;
        double longitude = 323.2;

        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();

        // SQLite query used to populate database with a flight
        String flightStmt = "INSERT INTO FLIGHT_DATA(flight_id, location_type, location, altitude, latitude, longitude) "
                + "VALUES (?, ?, ?, ?, ?, ?)";


        // SQLite query used to retrieve flight data from the database
        String flightIdQuery = "SELECT id FROM FLIGHT_DATA";

        // SQLite query used to retrieve all flight data from the database
        String flightQuery = "SELECT * FROM FLIGHT_DATA WHERE id = ?";


        // Creating a statement that is the given flight data, and then executed, inserting it into the database
        PreparedStatement stmtFlight = dbHandler.prepareStatement(flightStmt);
        List<Object> tmpFlightList = Arrays.asList(1, airline, airport, altitude, latitude, longitude);
        ArrayList<Object> testFlightArrayList = new ArrayList<>(tmpFlightList);
        for (int i=1; i < 7; i++) {
            stmtFlight.setObject(i, testFlightArrayList.get(i-1));
        }
        stmtFlight.executeUpdate();


        // Creating a statement to retrieve the id of the created flight so that it can be passed into the updateFlight() method.
        PreparedStatement stmtFlightId = dbHandler.prepareStatement(flightIdQuery);
        ResultSet result = stmtFlightId.executeQuery();
        int id = result.getInt(1);


        int res = flightService.updateFlight(id, "FIX", airport, altitude, latitude, longitude);

        assertEquals(1, res);

        // Creating a statement to retrieve the flight data so that it can be checked
        PreparedStatement stmtFlightUpdated = dbHandler.prepareStatement(flightQuery);
        stmtFlightUpdated.setObject(1, id);
        ResultSet results = stmtFlightUpdated.executeQuery();

        // Creating ArrayList of expected Parameters
        List<Object> tmpExpectedParameters = Arrays.asList(1, 1, "FIX", airport, altitude, latitude, longitude);
        ArrayList<Object> expectedParameters = new ArrayList<>(tmpExpectedParameters);

        for (int i=1; i < 8; i++) {
            assertEquals(expectedParameters.get(i-1), results.getObject((i)));
        }


    }



    public void testUpdateFlightInvalid() throws SQLException {

        int flight_id = 5;
        String airline = "FFA";
        String airport = "FSL";
        int altitude = 42;
        double latitude = 4341.1;
        double longitude = 323.2;


        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();


        // SQLite query used to populate database with a flight
        String flightStmt = "INSERT INTO FLIGHT_DATA(flight_id, location_type, location, altitude, latitude, longitude) "
                + "VALUES (?, ?, ?, ?, ?, ?)";


        // SQLite query used to retrieve flight data from the database
        String flightIdQuery = "SELECT id FROM FLIGHT_DATA";


        // Creating a statement that is the given flight data, and then executed, inserting it into the database
        PreparedStatement stmtFlight = dbHandler.prepareStatement(flightStmt);
        List<Object> tmpFlightList = Arrays.asList(1, airline, airport, altitude, latitude, longitude);
        ArrayList<Object> testFlightArrayList = new ArrayList<>(tmpFlightList);
        for (int i = 1; i < 7; i++) {
            stmtFlight.setObject(i, testFlightArrayList.get(i - 1));
        }
        stmtFlight.executeUpdate();


        // Creating a statement to retrieve the id of the created flight so that it can be passed into the updateFlight() method.
        PreparedStatement stmtFlightId = dbHandler.prepareStatement(flightIdQuery);
        ResultSet result = stmtFlightId.executeQuery();
        int id = result.getInt(1);


        int res = flightService.updateFlight(id, "APT", airport, altitude, latitude, longitude);

        assertEquals(-1, res);

    }


    public void testDeleteFlightValid() throws SQLException {

        String airline = "FFA";
        String airport = "FSL";
        int altitude = 42;
        double latitude = 4341.1;
        double longitude = 323.2;


        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();


        // SQLite query used to populate the database with the required data to run the RouteService saveRoute() method
        String airLineQuery = "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, "
                + "callsign, country, active) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate database with the required data to run the routeService saveRoute() method
        String airportQuery = "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                + "longitude, altitude, timezone, dst, tz_database_timezone) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        // SQLite query used to retrieve flight data from the database
        String flightQuery = "SELECT * FROM FLIGHT_DATA";

        // SQLite query used to populate database with a flight
        String flightStmt = "INSERT INTO FLIGHT_DATA(flight_id, location_type, location, altitude, latitude, longitude) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        // SQLite query used to get the count of ids from the flight data
        String flightCountQuery = "SELECT COUNT(id) FROM FLIGHT_DATA WHERE id = ?";

        // Creating a statement that is then given airport data,  and then executed, inserting it into the database
        PreparedStatement stmt2 = dbHandler.prepareStatement(airportQuery);
        List<Object> tmp2 = Arrays.asList("Heathrow", "London", "England", airport, "FJLJ", 89, 123.2, 5000, 43, "JFI", "TZ");
        ArrayList<Object> testAirport1 = new ArrayList<>(tmp2);
        for (int i=1; i < 12; i++) {
            stmt2.setObject(i, testAirport1.get(i-1));
        }
        stmt2.executeUpdate();


        // Creating a statement that is then given airline data, and then executed, inserting it into the database
        PreparedStatement stmt = dbHandler.prepareStatement(airLineQuery);
        List<String> tmp = Arrays.asList("testName", "testAlias", airline, "iopd", "testCallsign", "Argentina", "Y");
        ArrayList<String> testAirline = new ArrayList<>(tmp);
        for (int i=1; i < 8; i++) {
            stmt.setObject(i, testAirline.get(i-1));
        }
        stmt.executeUpdate();


        // Creating a statement that is the given flight data, and then executed, inserting it into the database
        PreparedStatement stmtFlight = dbHandler.prepareStatement(flightStmt);
        List<Object> tmpFlightList = Arrays.asList(1, airline, airport, altitude, latitude, longitude);
        ArrayList<Object> testFlightArrayList = new ArrayList<>(tmpFlightList);
        for (int i=1; i < 7; i++) {
            stmtFlight.setObject(i, testFlightArrayList.get(i-1));
        }
        stmtFlight.executeUpdate();


        // Creating a statement to retrieve the id of the created flight so that it can be passed into the updateFlight() method.
        PreparedStatement stmtFlightData = dbHandler.prepareStatement(flightQuery);
        ResultSet resultData = stmtFlightData.executeQuery();

        int id = resultData.getInt(1);

        boolean res = flightService.deleteEntry(1);

        // Creating a statement to get the count of flight, in order to check if the flight has been deleted.
        PreparedStatement stmtFlightCount = dbHandler.prepareStatement(flightCountQuery);
        stmtFlightCount.setInt(1, id);
        int count = stmtFlightCount.executeQuery().getInt(1);
        assertEquals(0, count);


    }


    public void testDeleteFlightEntryValid() throws SQLException {

        String airline = "FFA";
        String airport = "FSL";
        int altitude = 42;
        double latitude = 4341.1;
        double longitude = 323.2;


        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();


        // SQLite query used to populate the database with the required data to run the RouteService saveRoute() method
        String airLineQuery = "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, "
                + "callsign, country, active) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate database with the required data to run the routeService saveRoute() method
        String airportQuery = "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                + "longitude, altitude, timezone, dst, tz_database_timezone) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        // SQLite query used to retrieve flight data from the database
        String flightQuery = "SELECT * FROM FLIGHT_DATA";

        // SQLite query used to populate database with a flight
        String flightStmt = "INSERT INTO FLIGHT_DATA(flight_id, location_type, location, altitude, latitude, longitude) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        // SQLite query used to get the count of ids from the flight data
        String flightCountQuery = "SELECT COUNT(flight_id) FROM FLIGHT_DATA WHERE flight_id = ?";

        // Creating a statement that is then given airport data,  and then executed, inserting it into the database
        PreparedStatement stmt2 = dbHandler.prepareStatement(airportQuery);
        List<Object> tmp2 = Arrays.asList("Heathrow", "London", "England", airport, "FJLJ", 89, 123.2, 5000, 43, "JFI", "TZ");
        ArrayList<Object> testAirport1 = new ArrayList<>(tmp2);
        for (int i=1; i < 12; i++) {
            stmt2.setObject(i, testAirport1.get(i-1));
        }
        stmt2.executeUpdate();


        // Creating a statement that is then given airline data, and then executed, inserting it into the database
        PreparedStatement stmt = dbHandler.prepareStatement(airLineQuery);
        List<String> tmp = Arrays.asList("testName", "testAlias", airline, "iopd", "testCallsign", "Argentina", "Y");
        ArrayList<String> testAirline = new ArrayList<>(tmp);
        for (int i=1; i < 8; i++) {
            stmt.setObject(i, testAirline.get(i-1));
        }
        stmt.executeUpdate();


        // Creating a statement that is the given flight data, and then executed, inserting it into the database
        PreparedStatement stmtFlight = dbHandler.prepareStatement(flightStmt);
        List<Object> tmpFlightList = Arrays.asList(1, airline, airport, altitude, latitude, longitude);
        ArrayList<Object> testFlightArrayList = new ArrayList<>(tmpFlightList);
        for (int i=1; i < 7; i++) {
            stmtFlight.setObject(i, testFlightArrayList.get(i-1));
        }
        stmtFlight.executeUpdate();


        // Creating a statement to retrieve the id of the created flight so that it can be passed into the updateFlight() method.
        PreparedStatement stmtFlightData = dbHandler.prepareStatement(flightQuery);
        ResultSet resultData = stmtFlightData.executeQuery();

        int flight_id = resultData.getInt(2);

        boolean res = flightService.deleteFlight(flight_id);

        // Creating a statement to get the count of flight, in order to check if the flight has been deleted.
        PreparedStatement stmtFlightCount = dbHandler.prepareStatement(flightCountQuery);
        stmtFlightCount.setInt(1, flight_id);
        int count = stmtFlightCount.executeQuery().getInt(1);
        assertEquals(0, count);
    }

    public void testDeleteFlightInvalid() throws SQLException {

        String airline = "FFA";
        String airport = "FSL";
        int altitude = 42;
        double latitude = 4341.1;
        double longitude = 323.2;


        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();


        // SQLite query used to populate the database with the required data to run the RouteService saveRoute() method
        String airLineQuery = "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, "
                + "callsign, country, active) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate database with the required data to run the routeService saveRoute() method
        String airportQuery = "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                + "longitude, altitude, timezone, dst, tz_database_timezone) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        // SQLite query used to retrieve flight data from the database
        String flightQuery = "SELECT * FROM FLIGHT_DATA";

        // SQLite query used to populate database with a flight
        String flightStmt = "INSERT INTO FLIGHT_DATA(flight_id, location_type, location, altitude, latitude, longitude) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        // SQLite query used to get the count of ids from the flight data
        String flightCountQuery = "SELECT COUNT(id) FROM FLIGHT_DATA WHERE id = ?";

        // Creating a statement that is then given airport data,  and then executed, inserting it into the database
        PreparedStatement stmt2 = dbHandler.prepareStatement(airportQuery);
        List<Object> tmp2 = Arrays.asList("Heathrow", "London", "England", airport, "FJLJ", 89, 123.2, 5000, 43, "JFI", "TZ");
        ArrayList<Object> testAirport1 = new ArrayList<>(tmp2);
        for (int i=1; i < 12; i++) {
            stmt2.setObject(i, testAirport1.get(i-1));
        }
        stmt2.executeUpdate();


        // Creating a statement that is then given airline data, and then executed, inserting it into the database
        PreparedStatement stmt = dbHandler.prepareStatement(airLineQuery);
        List<String> tmp = Arrays.asList("testName", "testAlias", airline, "iopd", "testCallsign", "Argentina", "Y");
        ArrayList<String> testAirline = new ArrayList<>(tmp);
        for (int i=1; i < 8; i++) {
            stmt.setObject(i, testAirline.get(i-1));
        }
        stmt.executeUpdate();


        // Creating a statement that is the given flight data, and then executed, inserting it into the database
        PreparedStatement stmtFlight = dbHandler.prepareStatement(flightStmt);
        List<Object> tmpFlightList = Arrays.asList(1, airline, airport, altitude, latitude, longitude);
        ArrayList<Object> testFlightArrayList = new ArrayList<>(tmpFlightList);
        for (int i=1; i < 7; i++) {
            stmtFlight.setObject(i, testFlightArrayList.get(i-1));
        }
        stmtFlight.executeUpdate();


        boolean res = flightService.deleteEntry(2);

        assertFalse(res);
    }


    public void testDeleteFlightEntryInvalid() throws SQLException {

        String airline = "FFA";
        String airport = "FSL";
        int altitude = 42;
        double latitude = 4341.1;
        double longitude = 323.2;


        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();


        // SQLite query used to populate the database with the required data to run the RouteService saveRoute() method
        String airLineQuery = "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, "
                + "callsign, country, active) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate database with the required data to run the routeService saveRoute() method
        String airportQuery = "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                + "longitude, altitude, timezone, dst, tz_database_timezone) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        // SQLite query used to retrieve flight data from the database
        String flightQuery = "SELECT * FROM FLIGHT_DATA";

        // SQLite query used to populate database with a flight
        String flightStmt = "INSERT INTO FLIGHT_DATA(flight_id, location_type, location, altitude, latitude, longitude) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        // SQLite query used to get the count of ids from the flight data
        String flightCountQuery = "SELECT COUNT(flight_id) FROM FLIGHT_DATA WHERE flight_id = ?";

        // Creating a statement that is then given airport data,  and then executed, inserting it into the database
        PreparedStatement stmt2 = dbHandler.prepareStatement(airportQuery);
        List<Object> tmp2 = Arrays.asList("Heathrow", "London", "England", airport, "FJLJ", 89, 123.2, 5000, 43, "JFI", "TZ");
        ArrayList<Object> testAirport1 = new ArrayList<>(tmp2);
        for (int i=1; i < 12; i++) {
            stmt2.setObject(i, testAirport1.get(i-1));
        }
        stmt2.executeUpdate();


        // Creating a statement that is then given airline data, and then executed, inserting it into the database
        PreparedStatement stmt = dbHandler.prepareStatement(airLineQuery);
        List<String> tmp = Arrays.asList("testName", "testAlias", airline, "iopd", "testCallsign", "Argentina", "Y");
        ArrayList<String> testAirline = new ArrayList<>(tmp);
        for (int i=1; i < 8; i++) {
            stmt.setObject(i, testAirline.get(i-1));
        }
        stmt.executeUpdate();


        // Creating a statement that is the given flight data, and then executed, inserting it into the database
        PreparedStatement stmtFlight = dbHandler.prepareStatement(flightStmt);
        List<Object> tmpFlightList = Arrays.asList(1, airline, airport, altitude, latitude, longitude);
        ArrayList<Object> testFlightArrayList = new ArrayList<>(tmpFlightList);
        for (int i=1; i < 7; i++) {
            stmtFlight.setObject(i, testFlightArrayList.get(i-1));
        }
        stmtFlight.executeUpdate();


        boolean res = flightService.deleteFlight(2);

        assertFalse(res);
    }


    public void testGetFlight() throws SQLException {

        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();


        // SQLite query used to populate the database with the required data to run the RouteService saveRoute() method
        String airLineQuery = "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, "
                + "callsign, country, active) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate database with the required data to run the routeService saveRoute() method
        String airportQuery = "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                + "longitude, altitude, timezone, dst, tz_database_timezone) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate database with a flight
        String flightStmt = "INSERT INTO FLIGHT_DATA(flight_id, location_type, location, altitude, latitude, longitude) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        String flightIdStmt = "SELECT * FROM FLIGHT_DATA WHERE location_type=? AND location = ?";


        // Creating a statement that is then given airport data,  and then executed, inserting it into the database
        PreparedStatement stmt2 = dbHandler.prepareStatement(airportQuery);
        List<Object> tmp2 = Arrays.asList("Heathrow", "London", "England", "HEA", "FJLJ", 89, 123.2, 5000, 43, "JFI", "TZ");
        ArrayList<Object> testAirport1 = new ArrayList<>(tmp2);
        for (int i=1; i < 12; i++) {
            stmt2.setObject(i, testAirport1.get(i-1));
        }
        stmt2.executeUpdate();


        // Creating a statement that is then given airline data, and then executed, inserting it into the database
        PreparedStatement stmt = dbHandler.prepareStatement(airLineQuery);
        List<String> tmp = Arrays.asList("testName", "testAlias", "TES", "iopd", "testCallsign", "Argentina", "Y");
        ArrayList<String> testAirline = new ArrayList<>(tmp);
        for (int i=1; i < 8; i++) {
            stmt.setObject(i, testAirline.get(i-1));
        }
        stmt.executeUpdate();



        // Creating a statement that is the given flight data, and then executed, inserting it into the database
        PreparedStatement stmtFlight = dbHandler.prepareStatement(flightStmt);
        List<Object> tmpFlightList = Arrays.asList(1, "TES", "HEA", 10000, 321.5, 123.2);
        ArrayList<Object> testFlightArrayList = new ArrayList<>(tmpFlightList);
        for (int i=1; i < 7; i++) {
            stmtFlight.setObject(i, testFlightArrayList.get(i-1));
        }
        stmtFlight.executeUpdate();

        // Creating a statement that is given flight data and then executed, retrieving flight data from the database
        PreparedStatement stmtFlightId = dbHandler.prepareStatement(flightIdStmt);
        stmtFlightId.setString(1, "TES");
        stmtFlightId.setString(2, "HEA");
        ResultSet result = stmtFlightId.executeQuery();
        Integer id = result.getInt(1);

        // Checking that all values of the retrieved flight are the same as the original one
        ResultSet flightRetrieved = flightService.getFlight(id);
        for (int i=1; i < 8; i++) {
            assertEquals(result.getObject(i), flightRetrieved.getObject(i));
        }

        // Checking that getFlight returns null for an non-existent id
        ResultSet nonExistentFlight = flightService.getFlight(123122323);
        assertFalse(nonExistentFlight.next());

    }


    public void testGetFlights() throws SQLException {

        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();

        // SQLite query used to populate the database with the required data to run the RouteService saveRoute() method
        String airLineQuery = "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, "
                + "callsign, country, active) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate database with the required data to run the routeService saveRoute() method
        String airportQuery = "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                + "longitude, altitude, timezone, dst, tz_database_timezone) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate database with a flight
        String flightStmt = "INSERT INTO FLIGHT_DATA(flight_id, location_type, location, altitude, latitude, longitude) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        // Creating a statement that is the given flight data, and then executed, inserting it into the database
        PreparedStatement stmtFlight1 = dbHandler.prepareStatement(flightStmt);
        List<Object> tmpFlightList = Arrays.asList(1, "FIX", "HEA", 10000, 321.5, 123.2);
        ArrayList<Object> testFlightArrayList = new ArrayList<>(tmpFlightList);
        for (int i=1; i < 7; i++) {
            stmtFlight1.setObject(i, testFlightArrayList.get(i-1));
        }
        stmtFlight1.executeUpdate();

        // Creating a statement that is the given flight data, and then executed, inserting it into the database
        PreparedStatement stmtFlight2 = dbHandler.prepareStatement(flightStmt);
        List<Object> tmpFlightList2 = Arrays.asList(2, "VOR", "SYD", 10000, 321.5, 123.2);
        ArrayList<Object> testFlightArrayList2 = new ArrayList<>(tmpFlightList2);
        for (int i=1; i < 7; i++) {
            stmtFlight2.setObject(i, testFlightArrayList2.get(i-1));
        }
        stmtFlight2.executeUpdate();

        // Creating a list of expected Results
        List expectedResults = Arrays.asList(2, "VOR", "SYD", 10000, 321.5, 123.2);
        ArrayList<Object> expectedResultsList = new ArrayList<Object>(expectedResults);
        // Getting the actual results
        ResultSet result = flightService.getFlights("VOR", "SYD");

        // Comparing the actual results to the expected results
        for (int i = 2; i < 8; i++) {
            assertEquals(expectedResultsList.get(i-2), result.getObject(i));
        }


        // Getting results for a airline that doesn't exist
        ResultSet resultInvalid = flightService.getFlights("NEFNE>NFNE", "aNESN");
        assertFalse(resultInvalid.next());


    }


    public void testLocationTypeValid() {
        List<String> valid_location_types = Arrays.asList("APT", "VOR", "FIX");
        List<String> invalid_location_types = Arrays.asList("NES", "LEN", "ANE");
        for (String loc : valid_location_types) {
            assertTrue(flightService.locationTypeIsValid(loc));
        }
        for (String loc : invalid_location_types) {
            assertFalse(flightService.locationTypeIsValid(loc));
        }
    }

    public void testGetMaxId() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        PreparedStatement stmt = dbHandler.prepareStatement("SELECT MAX(flight_id) FROM FLIGHT_DATA");
        // Executes the search query, sets result to the first entry in the ResultSet (there will at most be one entry)
        ResultSet result = stmt.executeQuery();
        int id = result.getInt(1);
        assertEquals(id, flightService.getMaxID());
    }

    public void testGetNextFlightID() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        PreparedStatement stmt = dbHandler.prepareStatement("SELECT MAX(flight_id) FROM FLIGHT_DATA");
        // Executes the search query, sets result to the first entry in the ResultSet (there will at most be one entry)
        ResultSet result = stmt.executeQuery();
        int id = result.getInt(1) + 1;
        assertEquals(id, flightService.getNextFlightID());
    }

}
