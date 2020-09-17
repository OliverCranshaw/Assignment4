package accessor;

import junit.framework.Test;
import junit.framework.TestSuite;
import seng202.team5.accessor.AirlineAccessor;
import seng202.team5.database.DBConnection;
import seng202.team5.service.AirlineService;
import service.AirlineServiceTest;
import service.BaseDatabaseTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AirlineAccessorTest extends BaseDatabaseTest {
    private AirlineAccessor airlineAccessor;

    private final List<String> testData = List.of("AirlineName", "AliasName", "IT", "ICA", "CallsignStuff", "CountryName", "Y");

    public AirlineAccessorTest(String testName) { super(testName); }

    public static Test suite() { return new TestSuite(AirlineAccessorTest.class); }

    @Override
    protected void setUp() {
        super.setUp();
        airlineAccessor = new AirlineAccessor();
    }

    public void testSave() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();

        List<List<String>> testSets = new ArrayList<>();
        testSets.add(testData);
        List<String> testData2 = new ArrayList<>();
        for (String item : testData) {
            testData2.add("2" + item);
        }
        testSets.add(testData2);

        for (List<String> data : testSets) {
            int key = airlineAccessor.save(new ArrayList<>(data));

            // Check operation did not fail
            assertTrue(key != -1);

            // Query airline data with airport_id=key
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT * FROM AIRLINE_DATA WHERE airline_id = ?");
            stmt.setObject(1, key);
            ResultSet resultSet = stmt.executeQuery();

            // Check that there is at least one result
            assertTrue("Failed to fetch airline_id=" + key, resultSet.next());

            // Check the result contents
            for (int i = 0; i<data.size(); i++) {
                assertEquals(data.get(i), resultSet.getObject(2 + i));
            }

            // Check there are no more than 1 result
            assertFalse(resultSet.next());
        }
    }

    public void testUpdate() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();

        // Adds an airline
        PreparedStatement stmt = dbHandler.prepareStatement(
                "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, callsign, country, active) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)");

        // Iterates through the List and adds the values to the insert statement
        for (int i = 0; i<testData.size(); i++) {
            stmt.setObject(i + 1, testData.get(i));
        }

        assertEquals(1, stmt.executeUpdate());
        ResultSet keys = stmt.getGeneratedKeys();
        keys.next();
        int key = keys.getInt(1);


        assertEquals(0, airlineAccessor.update(10, "Namey", "Aliasy", "Ia", "Ica", "Callsigny", "Countryy", "Y"));

        List<String> newData = List.of("Namey", "Aliasy", "Ia", "Ica", "Callsigny", "Countryy", "Y");

        assertEquals(1, airlineAccessor.update(1, newData.get(0), newData.get(1), newData.get(2), newData.get(3), newData.get(4), newData.get(5), newData.get(6)));


        // Query airline data with airport_id=key
        PreparedStatement stmt2 = dbHandler.prepareStatement(
                "SELECT * FROM AIRLINE_DATA WHERE airline_id = ?");
        stmt2.setObject(1, key);
        ResultSet resultSet = stmt2.executeQuery();
        // Check that there is at least one result
        assertTrue("Failed to fetch airline_id=" + 1, resultSet.next());

        // Check the result contents
        for (int i = 0; i<newData.size(); i++) {
            assertEquals(newData.get(i), resultSet.getObject(2 + i));
        }

        // Check there are no more than 1 result
        assertFalse(resultSet.next());
    }

    public void testDelete() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        PreparedStatement stmt = dbHandler.prepareStatement(
                "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, callsign, country, active) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)");

        // Iterates through the List and adds the values to the insert statement
        for (int i = 0; i<testData.size(); i++) {
            stmt.setObject(i + 1, testData.get(i));
        }

        assertEquals(1, stmt.executeUpdate());

        // Gets the airline ID
        ResultSet rs = stmt.getGeneratedKeys();
        assertTrue(rs.next());
        int id = rs.getInt(1);
        rs.close();

        // Ensure delete fails it doesn't exist
        assertFalse(airlineAccessor.delete(-1));

        // Performs the delete
        assertTrue(airlineAccessor.delete(id));

        PreparedStatement stmt2 = dbHandler.prepareStatement(
                "SELECT * FROM AIRLINE_DATA");
        ResultSet resultSet = stmt2.executeQuery();

        // Check that we don't find anything
        assertFalse(resultSet.next());

        dbHandler.close();
    }

    public void testGetAllData() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();

        for (int i = 0; i<2; i++) {
            // Adds an airline
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, callsign, country, active) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?)");

            // Iterates through the List and adds the values to the insert statement
            for (int j = 0; j<testData.size(); j++) {
                stmt.setObject(j + 1, testData.get(j));
            }

            assertEquals(1, stmt.executeUpdate());
        }

        ResultSet resultSet = airlineAccessor.getAllData();
        for (int i = 0; i<2; i++) {
            assertTrue(resultSet.next());
            // Check the result contents
            for (int j = 0; j<testData.size(); j++) {
                assertEquals(testData.get(j), resultSet.getObject(2 + j));
            }
        }
        assertFalse(resultSet.next());
    }

    public void testGetDataByID() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        List<Integer> keys = new ArrayList<>();

        for (int i = 0; i<3; i++) {

            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, callsign, country, active) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?)");

            // Iterates through the List and adds the values to the insert statement
            stmt.setObject(1, testData.get(0) + i);
            for (int j = 1; j<testData.size(); j++) {
                stmt.setObject(j + 1, testData.get(j));
            }

            // Executes the insert operation, sets the result to the airport_id of the new airport
            int changes = stmt.executeUpdate();
            assertEquals(1, changes);

            // Gets the airline ID
            ResultSet rs = stmt.getGeneratedKeys();
            assertTrue(rs.next());
            int key = rs.getInt(1);
            keys.add(key);
        }

        for (int i = 0; i<3; i++) {
            int key = keys.get(i);
            ResultSet resultSet = airlineAccessor.getData(key);
            assertNotNull("Key " + key + " not found", resultSet);

            // Check that there is at least one result
            assertTrue(resultSet.next());

            // Check name
            assertEquals(testData.get(0) + String.valueOf(i), resultSet.getObject(2));

            // Check rest of the entry
            for (int j = 1; j<testData.size(); j++) {
                assertEquals(testData.get(j), resultSet.getObject(2 + j));
            }

            // Check there are no more than 1 result
            assertFalse(resultSet.next());
        }

        assertFalse(airlineAccessor.getData(10000).next());
    }

    public void testGetData() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        PreparedStatement stmt = dbHandler.prepareStatement(
                "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, callsign, country, active) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)");
        for (int i = 0; i<testData.size(); i++) {
            stmt.setObject(i+1, testData.get(i));
        }
        stmt.executeUpdate();

        String name = (String)testData.get(0);
        String country = (String)testData.get(5);
        String callsign = (String)testData.get(4);

        // Test all combinations
        for (String testName : new String[]{null, name, "Not"+name}) {
            boolean validName = testName == null || testName.equals(name);

            for (String testCountry : new String[]{null, country, "Not"+country}) {
                boolean validCountry = testCountry == null || testCountry.equals(country);

                for (String testCallsign : new String[]{null, callsign, "Not"+callsign}) {
                    boolean validCallsign = testCallsign == null || testCallsign.equals(callsign);

                    ResultSet resultSet = airlineAccessor.getData(testName, testCountry, testCallsign);

                    String combination = new StringBuilder()
                            .append("name=")
                            .append(testName)
                            .append(", country=")
                            .append(testCountry)
                            .append(", callsign=")
                            .append(testCallsign)
                            .toString();

                    // If filter matches the data in the database
                    if (validName && validCountry && validCallsign) {
                        assertTrue(combination, resultSet.next());
                        for (int i = 0; i<testData.size(); i++) {
                            assertEquals(combination, testData.get(i), resultSet.getObject(2 + i));
                        }
                    }
                    assertFalse(combination, resultSet.next());
                }
            }
        }

        dbHandler.close();
    }

    public void testGetAirlineId() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        List<Integer> keys = new ArrayList<>();

        for (int i = 0; i<3; i++) {

            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, callsign, country, active) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?)");

            // Iterates through the List and adds the values to the insert statement
            for (int j = 0; j<testData.size(); j++) {
                stmt.setObject(j + 1, i + testData.get(j).substring(1));
            }

            // Executes the insert operation, sets the result to the airport_id of the new airport
            int changes = stmt.executeUpdate();
            assertEquals(1, changes);

            // Gets the airline ID
            ResultSet rs = stmt.getGeneratedKeys();
            assertTrue(rs.next());
            int key = rs.getInt(1);
            keys.add(key);
        }

        for (int i = 0; i<3; i++) {
            int key = keys.get(i);

            String iataCode = i + testData.get(2).substring(1);
            assertEquals(key, airlineAccessor.getAirlineId(iataCode));

            String icaoCode = i + testData.get(3).substring(1);
            assertEquals(key, airlineAccessor.getAirlineId(icaoCode));
        }

        assertEquals(-1, airlineAccessor.getAirlineId("Something else"));
    }

    public void testGetAirlineIataIcao() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        PreparedStatement stmt = dbHandler.prepareStatement(
                "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, callsign, country, active) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)");

        // Iterates through the List and adds the values to the insert statement
        for (int i = 0; i<testData.size(); i++) {
            stmt.setObject(i + 1, testData.get(i));
        }

        // Executes the insert operation, sets the result to the airport_id of the new airport
        assertEquals(1, stmt.executeUpdate());

        ArrayList iataIcao = airlineAccessor.getAirlineIataIcao(testData.get(0));

        for (int i = 0; i<2; i++) {
            assertEquals(testData.get(i + 2), iataIcao.get(i));
        }

        assertTrue(airlineAccessor.getAirlineIataIcao("Not in thee database").isEmpty());
    }

    public void testDataExistsById() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        PreparedStatement stmt = dbHandler.prepareStatement(
                "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, callsign, country, active) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)");

        // Iterates through the List and adds the values to the insert statement
        for (int i = 0; i<testData.size(); i++) {
            stmt.setObject(i + 1, testData.get(i));
        }

        // Executes the insert operation, sets the result to the airport_id of the new airport
        assertEquals(1, stmt.executeUpdate());

        ResultSet rs = stmt.getGeneratedKeys();
        assertTrue(rs.next());
        int key = rs.getInt(1);


        assertTrue(airlineAccessor.dataExists(key));
        assertFalse(airlineAccessor.dataExists(key + 1));
    }

    public void testDataExists() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        PreparedStatement stmt = dbHandler.prepareStatement(
                "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, callsign, country, active) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)");

        // Iterates through the List and adds the values to the insert statement
        for (int i = 0; i<testData.size(); i++) {
            stmt.setObject(i + 1, testData.get(i));
        }

        // Executes the insert operation, sets the result to the airport_id of the new airport
        assertEquals(1, stmt.executeUpdate());


        assertTrue(airlineAccessor.dataExists(testData.get(2)));
        assertTrue(airlineAccessor.dataExists(testData.get(3)));
        assertFalse(airlineAccessor.dataExists("Shouldn't exist in DB"));
    }

    public void testGetMaxID() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();

        int maxKey = -1;

        for (int i = 0; i<3; i++) {

            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, callsign, country, active) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?)");

            // Iterates through the List and adds the values to the insert statement
            for (int j = 0; j<testData.size(); j++) {
                stmt.setObject(j + 1, i + testData.get(j).substring(1));
            }

            // Executes the insert operation, sets the result to the airport_id of the new airport
            int changes = stmt.executeUpdate();
            assertEquals(1, changes);

            // Gets the airline ID
            ResultSet rs = stmt.getGeneratedKeys();
            assertTrue(rs.next());
            int key = rs.getInt(1);
            maxKey = Math.max(maxKey, key);

            assertEquals(maxKey, airlineAccessor.getMaxID());
        }
    }
}
