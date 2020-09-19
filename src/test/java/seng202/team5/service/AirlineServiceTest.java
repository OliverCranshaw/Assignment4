package seng202.team5.service;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;
import seng202.team5.database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AirlineServiceTest extends BaseDatabaseTest {

    private AirlineService airlineService;

    private final List<String> testData = List.of("AirlineName", "AliasName", "IT", "ICA", "CallsignStuff", "CountryName", "Y");

    @Before
    public void setUp() {
        super.setUp();
        airlineService = new AirlineService();
    }


    @Test
    public void testUpdateAirline() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();

        // Adds an airline
        PreparedStatement stmt = dbHandler.prepareStatement(
                "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, callsign, country, active) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)");

        // Iterates through the List and adds the values to the insert statement
        for (int i = 0; i<testData.size(); i++) {
            stmt.setObject(i + 1, testData.get(i));
        }

        Assert.assertEquals(1, stmt.executeUpdate());

        Assert.assertEquals(0, airlineService.update(10, "Namey", "Aliasy", "Ia", "Ica", "Callsigny", "Countryy", "Y"));
        Assert.assertEquals(1, airlineService.update(1, "Namey", "Aliasy", "Ia", "Ica", "Callsigny", "Countryy", "Y"));
    }

    @Test
    public void testGetAirlines() throws SQLException {
        {
            // Test empty database
            ResultSet resultSet = airlineService.getData(null, null, null);
            Assert.assertFalse(resultSet.next());
        }

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

                    ResultSet resultSet = airlineService.getData(testName, testCountry, testCallsign);

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
                        Assert.assertTrue(combination, resultSet.next());
                        for (int i = 0; i<testData.size(); i++) {
                            Assert.assertEquals(combination, testData.get(i), resultSet.getObject(2 + i));
                        }
                    }
                    Assert.assertFalse(combination, resultSet.next());
                }
            }
        }

        dbHandler.close();
    }


    @Test
    public void testGetAirlineByID() throws SQLException {
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
            Assert.assertEquals(1, changes);

            // Gets the airline ID
            ResultSet rs = stmt.getGeneratedKeys();
            Assert.assertTrue(rs.next());
            int key = rs.getInt(1);
            keys.add(key);
        }

        for (int i = 0; i<3; i++) {
            int key = keys.get(i);
            ResultSet resultSet = airlineService.getData(key);
            Assert.assertNotNull("Key " + key + " not found", resultSet);

            // Check that there is at least one result
            Assert.assertTrue(resultSet.next());

            // Check name
            Assert.assertEquals(testData.get(0) + String.valueOf(i), resultSet.getObject(2));

            // Check rest of the entry
            for (int j = 1; j<testData.size(); j++) {
                Assert.assertEquals(testData.get(j), resultSet.getObject(2 + j));
            }

            // Check there are no more than 1 result
            Assert.assertFalse(resultSet.next());
        }
    }


    @Test
    public void testSaveAirline() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();

        List<String> testData2 = new ArrayList<>(testData);
        testData2.set(0, testData.get(0) + "2");
        testData2.set(2, "XY");
        testData2.set(3, "XYZ");

        for (List<String> entry : List.of(testData, testData2)) {
            int res = airlineService.save(
                    entry.get(0),
                    entry.get(1),
                    entry.get(2),
                    entry.get(3),
                    entry.get(4),
                    entry.get(5),
                    entry.get(6)
            );
            // Check operation did not fail
            Assert.assertTrue(res != -1);

            // Query airline data with airport_id=res
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT * FROM AIRLINE_DATA WHERE airline_id = ?");
            stmt.setObject(1, res);
            ResultSet resultSet = stmt.executeQuery();

            // Check that there is at least one result
            Assert.assertTrue("Failed to fetch airline_id=" + res, resultSet.next());

            // Check the result contents
            for (int i = 0; i<entry.size(); i++) {
                Assert.assertEquals(entry.get(i), resultSet.getObject(2 + i));
            }

            // Check there are no more than 1 result
            Assert.assertFalse(resultSet.next());
        }

        dbHandler.close();
    }


    @Test
    public void testDeleteAirline() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        PreparedStatement stmt = dbHandler.prepareStatement(
                "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, callsign, country, active) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)");

        // Iterates through the List and adds the values to the insert statement
        for (int i = 0; i<testData.size(); i++) {
            stmt.setObject(i + 1, testData.get(i));
        }

        Assert.assertEquals(1, stmt.executeUpdate());

        // Gets the airline ID
        ResultSet rs = stmt.getGeneratedKeys();
        Assert.assertTrue(rs.next());
        int id = rs.getInt(1);
        rs.close();

        // Performs the delete
        Assert.assertTrue(airlineService.delete(id));

        PreparedStatement stmt2 = dbHandler.prepareStatement(
                "SELECT * FROM AIRLINE_DATA");
        ResultSet resultSet = stmt2.executeQuery();

        // Check that we don't find anything
        Assert.assertFalse(resultSet.next());

        dbHandler.close();
    }


    @Test
    public void testAirlineExists() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        PreparedStatement stmt = dbHandler.prepareStatement(
                "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, callsign, country, active) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)");

        // Iterates through the List and adds the values to the insert statement
        for (int i = 0; i<testData.size(); i++) {
            stmt.setObject(i + 1, testData.get(i));
        }

        Assert.assertEquals(1, stmt.executeUpdate());

        Assert.assertTrue(airlineService.airlineExists(testData.get(2)));
        Assert.assertTrue(airlineService.airlineExists(testData.get(3)));
        Assert.assertFalse(airlineService.airlineExists("Not"+testData.get(2)));
    }


    @Test
    public void testIataIsValid() {
        Assert.assertTrue(airlineService.iataIsValid(null));
        Assert.assertTrue(airlineService.iataIsValid("ab"));
    }


    @Test
    public void testIcaoIsValid() {
        Assert.assertTrue(airlineService.icaoIsValid(null));
        Assert.assertTrue(airlineService.icaoIsValid("abc"));
    }


    @Test
    public void testGetMaxID() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        for (int i = 0; i<3; i++) {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, callsign, country, active) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?)");

            // Iterates through the List and adds the values to the insert statement
            for (int j = 0; j<testData.size(); j++) {
                stmt.setObject(j + 1, testData.get(j));
            }
            Assert.assertEquals(1, stmt.executeUpdate());

            // Checks maximum ID against expected value
            Assert.assertEquals(i + 1, airlineService.getMaxID());
        }
    }
}
