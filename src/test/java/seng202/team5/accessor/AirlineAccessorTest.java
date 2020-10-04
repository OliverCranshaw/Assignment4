package seng202.team5.accessor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import seng202.team5.database.DBConnection;
import seng202.team5.service.BaseDatabaseTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirlineAccessorTest extends BaseDatabaseTest {
    private AirlineAccessor airlineAccessor;

    private final List<String> testData = List.of("AirlineName", "AliasName", "IT", "ICA", "CallsignStuff", "CountryName", "Y");
    private final List<String> testData2 = List.of("AirlineName2", "AliasName2", "IF", "IGE", "CallsignStuff2", "CountryName2", "Y");
    private final List<String> testData3 = List.of("AirlineName3", "AliasName3", "", "GEE", "CallsignStuff3", "CountryName3", "Y");



    @Before
    public void setUp() {
        super.setUp();
        airlineAccessor = new AirlineAccessor();
    }


    @Test
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
            Assert.assertTrue(key != -1);

            // Query airline data with airport_id=key
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT * FROM AIRLINE_DATA WHERE airline_id = ?");
            stmt.setObject(1, key);
            ResultSet resultSet = stmt.executeQuery();

            // Check that there is at least one result
            Assert.assertTrue("Failed to fetch airline_id=" + key, resultSet.next());

            // Check the result contents
            for (int i = 0; i<data.size(); i++) {
                Assert.assertEquals(data.get(i), resultSet.getObject(2 + i));
            }

            // Check there are no more than 1 result
            Assert.assertFalse(resultSet.next());
        }
    }


    @Test
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

        Assert.assertEquals(1, stmt.executeUpdate());
        ResultSet keys = stmt.getGeneratedKeys();
        keys.next();
        int key = keys.getInt(1);


        Assert.assertEquals(0, airlineAccessor.update(10, "Namey", "Aliasy", "Ia", "Ica", "Callsigny", "Countryy", "Y"));

        List<String> newData = List.of("Namey", "Aliasy", "Ia", "Ica", "Callsigny", "Countryy", "Y");

        Assert.assertEquals(1, airlineAccessor.update(1, newData.get(0), newData.get(1), newData.get(2), newData.get(3), newData.get(4), newData.get(5), newData.get(6)));


        // Query airline data with airport_id=key
        PreparedStatement stmt2 = dbHandler.prepareStatement(
                "SELECT * FROM AIRLINE_DATA WHERE airline_id = ?");
        stmt2.setObject(1, key);
        ResultSet resultSet = stmt2.executeQuery();
        // Check that there is at least one result
        Assert.assertTrue("Failed to fetch airline_id=" + 1, resultSet.next());

        // Check the result contents
        for (int i = 0; i<newData.size(); i++) {
            Assert.assertEquals(newData.get(i), resultSet.getObject(2 + i));
        }

        // Check there are no more than 1 result
        Assert.assertFalse(resultSet.next());
    }


    @Test
    public void testDelete() throws SQLException {
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

        // Ensure delete fails it doesn't exist
        Assert.assertFalse(airlineAccessor.delete(-1));

        // Performs the delete
        Assert.assertTrue(airlineAccessor.delete(id));

        PreparedStatement stmt2 = dbHandler.prepareStatement(
                "SELECT * FROM AIRLINE_DATA");
        ResultSet resultSet = stmt2.executeQuery();

        // Check that we don't find anything
        Assert.assertFalse(resultSet.next());

        dbHandler.close();
    }


    @Test
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

            Assert.assertEquals(1, stmt.executeUpdate());
        }

        ResultSet resultSet = airlineAccessor.getData(null, null, null);
        for (int i = 0; i<2; i++) {
            Assert.assertTrue(resultSet.next());
            // Check the result contents
            for (int j = 0; j<testData.size(); j++) {
                Assert.assertEquals(testData.get(j), resultSet.getObject(2 + j));
            }
        }
        Assert.assertFalse(resultSet.next());
    }


    @Test
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
            Assert.assertEquals(1, changes);

            // Gets the airline ID
            ResultSet rs = stmt.getGeneratedKeys();
            Assert.assertTrue(rs.next());
            int key = rs.getInt(1);
            keys.add(key);
        }

        for (int i = 0; i<3; i++) {
            int key = keys.get(i);
            ResultSet resultSet = airlineAccessor.getData(key);
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

        Assert.assertFalse(airlineAccessor.getData(10000).next());
    }


    @Test
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
                        Assert.assertTrue(combination, resultSet.next());
                        for (int i = 0; i<testData.size(); i++) {
                            Assert.assertEquals(combination, testData.get(i), resultSet.getObject(2 + i));
                        }
                    }
                    Assert.assertFalse(combination, resultSet.next());
                }
            }
        }

        ResultSet resultSet = airlineAccessor.getData("IT");

        Assert.assertEquals("AirlineName", resultSet.getString("airline_name"));
        Assert.assertEquals("AliasName", resultSet.getString("alias"));
        Assert.assertEquals("IT", resultSet.getString("iata"));
        Assert.assertEquals("ICA", resultSet.getString("icao"));
        Assert.assertEquals("CallsignStuff", resultSet.getString("callsign"));
        Assert.assertEquals("CountryName", resultSet.getString("country"));
        Assert.assertEquals("Y", resultSet.getString("active"));

        resultSet = airlineAccessor.getData("ICA");

        Assert.assertEquals("AirlineName", resultSet.getString("airline_name"));
        Assert.assertEquals("AliasName", resultSet.getString("alias"));
        Assert.assertEquals("IT", resultSet.getString("iata"));
        Assert.assertEquals("ICA", resultSet.getString("icao"));
        Assert.assertEquals("CallsignStuff", resultSet.getString("callsign"));
        Assert.assertEquals("CountryName", resultSet.getString("country"));
        Assert.assertEquals("Y", resultSet.getString("active"));

        dbHandler.close();
    }


    @Test
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
            Assert.assertEquals(1, changes);

            // Gets the airline ID
            ResultSet rs = stmt.getGeneratedKeys();
            Assert.assertTrue(rs.next());
            int key = rs.getInt(1);
            keys.add(key);
        }

        for (int i = 0; i<3; i++) {
            int key = keys.get(i);

            String iataCode = i + testData.get(2).substring(1);
            Assert.assertEquals(key, airlineAccessor.getAirlineId(iataCode));

            String icaoCode = i + testData.get(3).substring(1);
            Assert.assertEquals(key, airlineAccessor.getAirlineId(icaoCode));
        }

        Assert.assertEquals(-1, airlineAccessor.getAirlineId("Something else"));
    }


    @Test
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
        Assert.assertEquals(1, stmt.executeUpdate());

        ResultSet rs = stmt.getGeneratedKeys();
        Assert.assertTrue(rs.next());
        int key = rs.getInt(1);


        Assert.assertTrue(airlineAccessor.dataExists(key));
        Assert.assertFalse(airlineAccessor.dataExists(key + 1));
    }


    @Test
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
        Assert.assertEquals(1, stmt.executeUpdate());


        Assert.assertTrue(airlineAccessor.dataExists(testData.get(2)));
        Assert.assertTrue(airlineAccessor.dataExists(testData.get(3)));
        Assert.assertFalse(airlineAccessor.dataExists("Shouldn't exist in DB"));
    }


    @Test
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
            Assert.assertEquals(1, changes);

            // Gets the airline ID
            ResultSet rs = stmt.getGeneratedKeys();
            Assert.assertTrue(rs.next());
            int key = rs.getInt(1);
            maxKey = Math.max(maxKey, key);

            Assert.assertEquals(maxKey, airlineAccessor.getMaxID());
        }
    }


    @Test
    public void testGetAirlineNames() throws SQLException {
        // Establishing connection with database
        Connection dbHandler = DBConnection.getConnection();

        // SQLITE query to insert airline data into database
        String query = "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, callsign, country, active) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";


        PreparedStatement stmt = dbHandler.prepareStatement(query);
        PreparedStatement stmt2 = dbHandler.prepareStatement(query);
        PreparedStatement stmt3 = dbHandler.prepareStatement(query);



        // Iterates through the List and adds the values to the insert statement
        for (int i = 0; i<testData.size(); i++) {
            stmt.setObject(i + 1, testData.get(i));
            stmt2.setObject(i + 1, testData2.get(i));
            stmt3.setObject(i + 1, testData3.get(i));
            if (i == 2) {
                stmt3.setObject(i + 1, null);
            }
        }

        stmt.executeUpdate();
        stmt2.executeUpdate();
        stmt3.executeUpdate();

        ResultSet test1 = airlineAccessor.getAirlineNames(new ArrayList<>());
        Assert.assertNull(test1);

        ArrayList<String> testList1 = new ArrayList<>();
        testList1.add((String) testData.get(2));
        testList1.add((String) testData2.get(3));
        testList1.add((String) testData3.get(3));
        String nonExistentIata = "FN";
        testList1.add(nonExistentIata);
        ResultSet test2 = airlineAccessor.getAirlineNames(testList1);
        while (test2.next()) {
            String iata = test2.getString(1);
            String icao = test2.getString(2);
            String name = test2.getString(3);
            if (iata == null) {
                Assert.assertEquals(name, testData3.get(0));
            } else if (iata.equals(testData.get(2))) {
                Assert.assertEquals(icao, testData.get(3));
                Assert.assertEquals(name, testData.get(0));
            } else if (iata.equals(testData2.get(2))) {
                Assert.assertEquals(icao, testData2.get(3));
                Assert.assertEquals(name, testData2.get(0));
            }
            Assert.assertNotEquals(iata, nonExistentIata);
        }

    }


}
