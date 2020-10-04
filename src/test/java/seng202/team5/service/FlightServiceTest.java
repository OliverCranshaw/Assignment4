package seng202.team5.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import seng202.team5.database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlightServiceTest extends BaseDatabaseTest {

    private FlightService flightService;

    @Before
    public void setUp() {
        super.setUp();
        flightService = new FlightService();
    }


    @Test
    public void testInitialState() throws SQLException {
        ResultSet stuff = flightService.getData(null, null);
        Assert.assertFalse(stuff.next());
    }


    @Test
    public void testAddValidFlight() throws SQLException {

        String location_type = "VOR";
        String location = "FSL";
        int altitude = 42;
        double latitude = 4341.1;
        double longitude = 323.2;



        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();


        // SQLite query used to retrieve flight data from the database
        String flightQuery = "SELECT * FROM FLIGHT_DATA";


        // Calling the saveFlight() method
        int res = flightService.save(flightService.getNextFlightID(), location_type, location, altitude, longitude, latitude);

        Assert.assertEquals(1, res);

        // Creating a statement that will retrieve the flight data back from the db
        PreparedStatement flightStmt = dbHandler.prepareStatement(flightQuery);
        ResultSet results = flightStmt.executeQuery();

        // Ensuring the received values are the same as the initial ones
        List<Object> tmpExpectedParameters = Arrays.asList(1, 1, location_type, location, altitude, longitude, latitude);
        ArrayList<Object> expectedParameters = new ArrayList<>(tmpExpectedParameters);
        for (int i=1; i < 8; i++) {
            Assert.assertEquals(expectedParameters.get(i-1), results.getObject(i));
        }
    }


    @Test
    public void testUpdateFlightValid() throws SQLException {

        String location_type = "FFA";
        String location = "FSL";
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
        List<Object> tmpFlightList = Arrays.asList(1, location_type, location, altitude, latitude, longitude);
        ArrayList<Object> testFlightArrayList = new ArrayList<>(tmpFlightList);
        for (int i=1; i < 7; i++) {
            stmtFlight.setObject(i, testFlightArrayList.get(i-1));
        }
        stmtFlight.executeUpdate();


        // Creating a statement to retrieve the id of the created flight so that it can be passed into the updateFlight() method.
        PreparedStatement stmtFlightId = dbHandler.prepareStatement(flightIdQuery);
        ResultSet result = stmtFlightId.executeQuery();
        int id = result.getInt(1);


        int res = flightService.update(id, "FIX", location, altitude, latitude, longitude);

        Assert.assertEquals(1, res);

        // Creating a statement to retrieve the flight data so that it can be checked
        PreparedStatement stmtFlightUpdated = dbHandler.prepareStatement(flightQuery);
        stmtFlightUpdated.setObject(1, id);
        ResultSet results = stmtFlightUpdated.executeQuery();

        // Creating ArrayList of expected Parameters
        List<Object> tmpExpectedParameters = Arrays.asList(1, 1, "FIX", location, altitude, latitude, longitude);
        ArrayList<Object> expectedParameters = new ArrayList<>(tmpExpectedParameters);

        for (int i=1; i < 8; i++) {
            Assert.assertEquals(expectedParameters.get(i-1), results.getObject((i)));
        }
    }


    @Test
    public void testDeleteFlightValid() throws SQLException {

        String location_type = "FFA";
        String location = "FSL";
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


        // Creating a statement that is the given flight data, and then executed, inserting it into the database
        PreparedStatement stmtFlight = dbHandler.prepareStatement(flightStmt);
        List<Object> tmpFlightList = Arrays.asList(1, location_type, location, altitude, latitude, longitude);
        ArrayList<Object> testFlightArrayList = new ArrayList<>(tmpFlightList);
        for (int i=1; i < 7; i++) {
            stmtFlight.setObject(i, testFlightArrayList.get(i-1));
        }
        stmtFlight.executeUpdate();


        // Creating a statement to retrieve the id of the created flight so that it can be passed into the updateFlight() method.
        PreparedStatement stmtFlightData = dbHandler.prepareStatement(flightQuery);
        ResultSet resultData = stmtFlightData.executeQuery();

        int id = resultData.getInt(1);

        // Creating a statement to get the count of flight, in order to check if the flight has been deleted.
        PreparedStatement stmtFlightCount = dbHandler.prepareStatement(flightCountQuery);
        stmtFlightCount.setInt(1, id);
        int count = stmtFlightCount.executeQuery().getInt(1);
        
        Assert.assertEquals(1, count);
    }


    @Test
    public void testDeleteFlightEntryValid() throws SQLException {

        String location_type = "FFA";
        String location = "FSL";
        int altitude = 42;
        double latitude = 4341.1;
        double longitude = 323.2;


        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();

        // SQLite query used to retrieve flight data from the database
        String flightQuery = "SELECT * FROM FLIGHT_DATA";

        // SQLite query used to populate database with a flight
        String flightStmt = "INSERT INTO FLIGHT_DATA(flight_id, location_type, location, altitude, latitude, longitude) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        // SQLite query used to get the count of ids from the flight data
        String flightCountQuery = "SELECT COUNT(flight_id) FROM FLIGHT_DATA WHERE flight_id = ?";


        // Creating a statement that is the given flight data, and then executed, inserting it into the database
        PreparedStatement stmtFlight = dbHandler.prepareStatement(flightStmt);
        List<Object> tmpFlightList = Arrays.asList(1, location_type, location, altitude, latitude, longitude);
        ArrayList<Object> testFlightArrayList = new ArrayList<>(tmpFlightList);
        for (int i=1; i < 7; i++) {
            stmtFlight.setObject(i, testFlightArrayList.get(i-1));
        }
        stmtFlight.executeUpdate();


        // Creating a statement to retrieve the id of the created flight so that it can be passed into the updateFlight() method.
        PreparedStatement stmtFlightData = dbHandler.prepareStatement(flightQuery);
        ResultSet resultData = stmtFlightData.executeQuery();

        int flight_id = resultData.getInt(2);
        
        // Creating a statement to get the count of flight, in order to check if the flight has been deleted.
        PreparedStatement stmtFlightCount = dbHandler.prepareStatement(flightCountQuery);
        stmtFlightCount.setInt(1, flight_id);
        int count = stmtFlightCount.executeQuery().getInt(1);
        
        Assert.assertEquals(1, count);
    }


    @Test
    public void testDeleteFlightInvalid() throws SQLException {

        String location_type = "FFA";
        String location = "FSL";
        int altitude = 42;
        double latitude = 4341.1;
        double longitude = 323.2;


        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();


        // SQLite query used to populate database with a flight
        String flightStmt = "INSERT INTO FLIGHT_DATA(flight_id, location_type, location, altitude, latitude, longitude) "
                + "VALUES (?, ?, ?, ?, ?, ?)";


        // Creating a statement that is the given flight data, and then executed, inserting it into the database
        PreparedStatement stmtFlight = dbHandler.prepareStatement(flightStmt);
        List<Object> tmpFlightList = Arrays.asList(1, location_type, location, altitude, latitude, longitude);
        ArrayList<Object> testFlightArrayList = new ArrayList<>(tmpFlightList);
        for (int i=1; i < 7; i++) {
            stmtFlight.setObject(i, testFlightArrayList.get(i-1));
        }
        stmtFlight.executeUpdate();


        boolean res = flightService.deleteEntry(2);

        Assert.assertFalse(res);
    }


    @Test
    public void testDeleteFlightEntryInvalid() throws SQLException {

        String location_type = "FFA";
        String location = "FSL";
        int altitude = 42;
        double latitude = 4341.1;
        double longitude = 323.2;


        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();


        // SQLite query used to populate database with a flight
        String flightStmt = "INSERT INTO FLIGHT_DATA(flight_id, location_type, location, altitude, latitude, longitude) "
                + "VALUES (?, ?, ?, ?, ?, ?)";


        // Creating a statement that is the given flight data, and then executed, inserting it into the database
        PreparedStatement stmtFlight = dbHandler.prepareStatement(flightStmt);
        List<Object> tmpFlightList = Arrays.asList(1, location_type, location, altitude, latitude, longitude);
        ArrayList<Object> testFlightArrayList = new ArrayList<>(tmpFlightList);
        for (int i=1; i < 7; i++) {
            stmtFlight.setObject(i, testFlightArrayList.get(i-1));
        }
        stmtFlight.executeUpdate();


        boolean res = flightService.delete(2);

        Assert.assertFalse(res);
    }


    @Test
    public void testGetFlight() throws SQLException {

        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();


        // SQLite query used to populate database with a flight
        String flightStmt = "INSERT INTO FLIGHT_DATA(flight_id, location_type, location, altitude, latitude, longitude) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        String flightIdStmt = "SELECT * FROM FLIGHT_DATA WHERE location_type=? AND location = ?";


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
        int id = result.getInt(1);

        // Checking that all values of the retrieved flight are the same as the original one
        ResultSet flightRetrieved = flightService.getData(id);
        for (int i=1; i < 8; i++) {
            Assert.assertEquals(result.getObject(i), flightRetrieved.getObject(i));
        }

        // Checking that getFlight returns null for an non-existent id
        ResultSet nonExistentFlight = flightService.getData(123122323);
        Assert.assertFalse(nonExistentFlight.next());
    }


    @Test
    public void testGetFlights() throws SQLException {

        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();

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
        List<Object> expectedResults = Arrays.asList(2, "VOR", "SYD", 10000, 321.5, 123.2);
        ArrayList<Object> expectedResultsList = new ArrayList<>(expectedResults);
        // Getting the actual results
        ResultSet result = flightService.getData("VOR", "SYD");

        // Comparing the actual results to the expected results
        for (int i = 2; i < 8; i++) {
            Assert.assertEquals(expectedResultsList.get(i-2), result.getObject(i));
        }


        // Getting results for a airline that doesn't exist
        ResultSet resultInvalid = flightService.getData("NEFNE>NFNE", "aNESN");
        Assert.assertFalse(resultInvalid.next());
    }


    @Test
    public void testGetMaxId() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        PreparedStatement stmt = dbHandler.prepareStatement("SELECT MAX(flight_id) FROM FLIGHT_DATA");
        // Executes the search query, sets result to the first entry in the ResultSet (there will at most be one entry)
        ResultSet result = stmt.executeQuery();
        int id = result.getInt(1);
        Assert.assertEquals(id, flightService.getMaxID());
    }


    @Test
    public void testGetNextFlightID() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        PreparedStatement stmt = dbHandler.prepareStatement("SELECT MAX(flight_id) FROM FLIGHT_DATA");
        // Executes the search query, sets result to the first entry in the ResultSet (there will at most be one entry)
        ResultSet result = stmt.executeQuery();
        int id = result.getInt(1) + 1;
        Assert.assertEquals(id, flightService.getNextFlightID());
    }
}
