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

public class AirportAccessorTest extends BaseDatabaseTest {
    private AirportAccessor airportAccessor;

    private final List<Object> testData = List.of("AirportName", "CityName", "CountryName", "IAT", "ICAO", 4.5, 6.2, 424242, 535353f, "E", "Timezone");
    private final List<Object> testData2 = List.of("AirportName2", "CityName2", "CountryName2", "ATA", "CAOO", 4.5, 6.2, 424242, 535353f, "E", "Timezone");
    private final List<String> testData3 = List.of("AirlineName", "AliasName", "IT", "ICA", "CallsignStuff", "CountryName", "Y");
    private final List<Object> testData4 = List.of("IT", 1, "IAT", 1, "ATA", 2, "N", 100, "ABC");

    @Before
    public void setUp() {
        super.setUp();
        airportAccessor = new AirportAccessor();
    }


    @Test
    public void testSave() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();

        List<List<Object>> testSets = new ArrayList<>();
        testSets.add(testData);
        List<Object> testData2 = new ArrayList<>();
        for (Object item : testData) {
            if (item instanceof String) {
                testData2.add("2" + item);
            } else {
                testData2.add(item);
            }
        }
        testSets.add(testData2);

        for (List<Object> data : testSets) {
            int key = airportAccessor.save(new ArrayList<>(data));

            // Check operation did not fail
            Assert.assertTrue(key != -1);

            // Query airport data with airport_id=key
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT * FROM AIRPORT_DATA WHERE airport_id = ?");
            stmt.setObject(1, key);
            ResultSet resultSet = stmt.executeQuery();

            // Check that there is at least one result
            Assert.assertTrue("Failed to fetch airport_id=" + key, resultSet.next());

            // Check the result contents
            for (int i = 0; i<data.size(); i++) {
                Object cell = data.get(i);
                if (cell instanceof Float) {
                    cell = (double)((Float)cell);
                }
                Assert.assertEquals(cell, resultSet.getObject(2 + i));
            }

            // Check there are no more than 1 result
            Assert.assertFalse(resultSet.next());
        }
    }


    @Test
    public void testUpdate() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();

        // Adds an airport
        PreparedStatement stmt = dbHandler.prepareStatement(
                "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                        + "longitude, altitude, timezone, dst, tz_database_timezone) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        // Iterates through the List and adds the values to the insert statement
        for (int i = 0; i<testData.size(); i++) {
            stmt.setObject(i + 1, testData.get(i));
        }

        Assert.assertEquals(1, stmt.executeUpdate());
        ResultSet keys = stmt.getGeneratedKeys();
        keys.next();
        int key = keys.getInt(1);


        List<Object> newData = List.of("Namey", "Cityy", "Countryy", "Iat", "Icao", 1.0, 2.0, 3, 4.0f, "A", "B");

        Assert.assertEquals(0, airportAccessor.update(
                10,
                (String)newData.get(0),
                (String)newData.get(1),
                (String)newData.get(2),
                (String)newData.get(3),
                (String)newData.get(4),
                (double)newData.get(5),
                (double)newData.get(6),
                (int)newData.get(7),
                (float)newData.get(8),
                (String)newData.get(9),
                (String)newData.get(10)
        ));


        Assert.assertEquals(1, airportAccessor.update(
                1,
                (String)newData.get(0),
                (String)newData.get(1),
                (String)newData.get(2),
                (String)newData.get(3),
                (String)newData.get(4),
                (double)newData.get(5),
                (double)newData.get(6),
                (int)newData.get(7),
                (float)newData.get(8),
                (String)newData.get(9),
                (String)newData.get(10)
                ));


        // Query airport data with airport_id=key
        PreparedStatement stmt2 = dbHandler.prepareStatement(
                "SELECT * FROM AIRPORT_DATA WHERE airport_id = ?");
        stmt2.setObject(1, key);
        ResultSet resultSet = stmt2.executeQuery();
        // Check that there is at least one result
        Assert.assertTrue("Failed to fetch airport_id=" + 1, resultSet.next());

        // Check the result contents
        for (int i = 0; i<newData.size(); i++) {
            Object cell = newData.get(i);
            if (cell instanceof Float) {
                cell = (double)((Float)cell);
            }
            Assert.assertEquals(cell, resultSet.getObject(2 + i));
        }

        // Check there are no more than 1 result
        Assert.assertFalse(resultSet.next());
    }


    @Test
    public void testDelete() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();

        // Adds an airport
        PreparedStatement stmt = dbHandler.prepareStatement(
                "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                        + "longitude, altitude, timezone, dst, tz_database_timezone) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        for (int i = 0; i<testData.size(); i++) {
            stmt.setObject(i+1, testData.get(i));
        }
        stmt.executeUpdate();

        // Checks that nonexistent delete fails
        Assert.assertFalse(airportAccessor.delete(100));

        // Performs the delete operation
        Assert.assertTrue(airportAccessor.delete(1));

        // Checks that the airport has been deleted
        PreparedStatement stmt2 = dbHandler.prepareStatement(
                "SELECT * FROM AIRPORT_DATA");
        ResultSet resultSet = stmt2.executeQuery();
        Assert.assertFalse(resultSet.next());
    }


    @Test
    public void testGetDataByID() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        List<Integer> keys = new ArrayList<>();

        for (int i = 0; i<3; i++) {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                            + "longitude, altitude, timezone, dst, tz_database_timezone) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            // Iterates through the List and adds the values to the insert statement
            stmt.setObject(1, testData.get(0) + String.valueOf(i));
            for (int j = 1; j<testData.size(); j++) {
                stmt.setObject(j + 1, testData.get(j));
            }

            // Executes the insert operation, sets the result to the airport_id of the new airport
            int changes = stmt.executeUpdate();
            Assert.assertEquals(1, changes);

            // Gets the airport ID
            ResultSet rs = stmt.getGeneratedKeys();
            Assert.assertTrue(rs.next());
            int key = rs.getInt(1);
            keys.add(key);
        }

        for (int i = 0; i<3; i++) {
            int key = keys.get(i);
            ResultSet resultSet = airportAccessor.getData(key);
            Assert.assertNotNull("Key " + key + " not found", resultSet);

            // Check that there is at least one result
            Assert.assertTrue(resultSet.next());

            // Check name
            Assert.assertEquals(testData.get(0) + String.valueOf(i), resultSet.getObject(2));

            // Check rest of the entry
            for (int j = 1; j<testData.size(); j++) {
                Object cell = testData.get(j);
                if (cell instanceof Float) {
                    cell = (double)((Float)cell);
                }
                Assert.assertEquals(cell, resultSet.getObject(2 + j));
            }

            // Check there are no more than 1 result
            Assert.assertFalse(resultSet.next());
        }
    }


    @Test
    public void testGetData() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        PreparedStatement stmt = dbHandler.prepareStatement(
                "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                        + "longitude, altitude, timezone, dst, tz_database_timezone) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        for (int i = 0; i<testData.size(); i++) {
            stmt.setObject(i+1, testData.get(i));
        }
        stmt.executeUpdate();

        String name = (String)testData.get(0);
        String city = (String)testData.get(1);
        String country = (String)testData.get(2);

        // Test all combinations
        for (String testName : new String[]{null, name, "Not"+name}) {
            boolean validName = testName == null || testName.equals(name);

            for (String testCity : new String[]{null, city, "Not"+city}) {
                boolean validCity = testCity == null || testCity.equals(city);

                for (String testCountry : new String[]{null, country, "Not"+country}) {
                    boolean validCountry = testCountry == null || testCountry.equals(country);

                    ResultSet resultSet = airportAccessor.getData(testName, testCity, testCountry);

                    String combination = new StringBuilder()
                            .append("name=")
                            .append(testName)
                            .append(", city=")
                            .append(testCity)
                            .append(", country=")
                            .append(testCountry)
                            .toString();

                    // If filter matches the data in the database
                    if (validName && validCity && validCountry) {
                        Assert.assertTrue(combination, resultSet.next());
                        for (int i = 0; i<testData.size(); i++) {
                            Object cell = testData.get(i);
                            if (cell instanceof Float) {
                                cell = (double)((Float)cell);
                            }
                            Assert.assertEquals(combination, cell, resultSet.getObject(2 + i));
                        }
                    }
                    Assert.assertFalse(combination, resultSet.next());
                }
            }
        }

        ResultSet resultSet = airportAccessor.getData("IAT");

        Assert.assertEquals("AirportName", resultSet.getString("airport_name"));
        Assert.assertEquals("CityName", resultSet.getString("city"));
        Assert.assertEquals("CountryName", resultSet.getString("country"));
        Assert.assertEquals("IAT", resultSet.getString("iata"));
        Assert.assertEquals("ICAO", resultSet.getString("icao"));
        Assert.assertEquals(4.5, resultSet.getDouble("latitude"), 0.1);
        Assert.assertEquals(6.2, resultSet.getDouble("longitude"), 0.1);
        Assert.assertEquals(424242, resultSet.getInt("altitude"));
        Assert.assertEquals(535353f, resultSet.getFloat("timezone"), 0.1);
        Assert.assertEquals("E", resultSet.getString("dst"));
        Assert.assertEquals("Timezone", resultSet.getString("tz_database_timezone"));

        resultSet = airportAccessor.getData("ICAO");

        Assert.assertEquals("AirportName", resultSet.getString("airport_name"));
        Assert.assertEquals("CityName", resultSet.getString("city"));
        Assert.assertEquals("CountryName", resultSet.getString("country"));
        Assert.assertEquals("IAT", resultSet.getString("iata"));
        Assert.assertEquals("ICAO", resultSet.getString("icao"));
        Assert.assertEquals(4.5, resultSet.getDouble("latitude"), 0.1);
        Assert.assertEquals(6.2, resultSet.getDouble("longitude"), 0.1);
        Assert.assertEquals(424242, resultSet.getInt("altitude"));
        Assert.assertEquals(535353f, resultSet.getFloat("timezone"), 0.1);
        Assert.assertEquals("E", resultSet.getString("dst"));
        Assert.assertEquals("Timezone", resultSet.getString("tz_database_timezone"));

        dbHandler.close();
    }


    @Test
    public void testGetAirportId() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        List<Integer> keys = new ArrayList<>();

        for (int i = 0; i<3; i++) {

            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                            + "longitude, altitude, timezone, dst, tz_database_timezone) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            // Iterates through the List and adds the values to the insert statement
            for (int j = 0; j<testData.size(); j++) {
                Object object = testData.get(j);
                if (object instanceof String) {
                    object = i + ((String)object).substring(1);
                }
                stmt.setObject(j + 1, object);
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

            String iataCode = i + ((String)testData.get(3)).substring(1);
            Assert.assertEquals(key, airportAccessor.getAirportId(iataCode));

            String icaoCode = i + ((String)testData.get(4)).substring(1);
            Assert.assertEquals(key, airportAccessor.getAirportId(icaoCode));
        }

        Assert.assertEquals(-1, airportAccessor.getAirportId("Something else"));
    }


    @Test
    public void testGetAirlineIataIcao() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        PreparedStatement stmt = dbHandler.prepareStatement(
                "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                        + "longitude, altitude, timezone, dst, tz_database_timezone) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        // Iterates through the List and adds the values to the insert statement
        for (int i = 0; i<testData.size(); i++) {
            stmt.setObject(i + 1, testData.get(i));
        }

        // Executes the insert operation, sets the result to the airport_id of the new airport
        Assert.assertEquals(1, stmt.executeUpdate());

        ArrayList iataIcao = airportAccessor.getAirportIataIcao((String)testData.get(0));

        for (int i = 0; i<2; i++) {
            Assert.assertEquals(testData.get(i + 3), iataIcao.get(i));
        }

        Assert.assertTrue(airportAccessor.getAirportIataIcao("Not in thee database").isEmpty());
    }


    @Test
    public void testDataExistsById() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        PreparedStatement stmt = dbHandler.prepareStatement(
                "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                        + "longitude, altitude, timezone, dst, tz_database_timezone) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        // Iterates through the List and adds the values to the insert statement
        for (int i = 0; i<testData.size(); i++) {
            stmt.setObject(i + 1, testData.get(i));
        }

        // Executes the insert operation, sets the result to the airport_id of the new airport
        Assert.assertEquals(1, stmt.executeUpdate());

        ResultSet rs = stmt.getGeneratedKeys();
        Assert.assertTrue(rs.next());
        int key = rs.getInt(1);


        Assert.assertTrue(airportAccessor.dataExists(key));
        Assert.assertFalse(airportAccessor.dataExists(key + 1));
    }


    @Test
    public void testDataExists() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        PreparedStatement stmt = dbHandler.prepareStatement(
                "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                        + "longitude, altitude, timezone, dst, tz_database_timezone) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        // Iterates through the List and adds the values to the insert statement
        for (int i = 0; i<testData.size(); i++) {
            stmt.setObject(i + 1, testData.get(i));
        }

        // Executes the insert operation, sets the result to the airport_id of the new airport
        Assert.assertEquals(1, stmt.executeUpdate());


        Assert.assertTrue(airportAccessor.dataExists((String)testData.get(3)));
        Assert.assertTrue(airportAccessor.dataExists((String)testData.get(4)));
        Assert.assertFalse(airportAccessor.dataExists("Shouldn't exist in DB"));
    }


    @Test
    public void testGetMaxID() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();

        int maxKey = -1;

        for (int i = 0; i<3; i++) {

            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                            + "longitude, altitude, timezone, dst, tz_database_timezone) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");


            // Iterates through the List and adds the values to the insert statement
            for (int j = 0; j<testData.size(); j++) {
                stmt.setObject(j + 1, testData.get(j));
            }

            // Executes the insert operation, sets the result to the airport_id of the new airport
            int changes = stmt.executeUpdate();
            Assert.assertEquals(1, changes);

            // Gets the airline ID
            ResultSet rs = stmt.getGeneratedKeys();
            Assert.assertTrue(rs.next());
            int key = rs.getInt(1);
            maxKey = Math.max(maxKey, key);

            Assert.assertEquals(maxKey, airportAccessor.getMaxID());
        }
    }


    @Test
    public void testIncomingRoutes() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();

        {
            // Add a airline to the DB
            PreparedStatement stmt = dbHandler.prepareStatement("INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, "
                    + "callsign, country, active) VALUES (?, ?, ?, ?, ?, ?, ?)");
            for (int i = 0; i < testData3.size(); i++) {
                stmt.setObject(i + 1, testData3.get(i));
            }
            stmt.executeUpdate();
        }
        {
            // Add a source airport to the DB
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                            + "longitude, altitude, timezone, dst, tz_database_timezone) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for (int i = 0; i < testData.size(); i++) {
                stmt.setObject(i + 1, testData.get(i));
            }
            stmt.executeUpdate();
        }
        {
            // Add a destination airport to the DB
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                            + "longitude, altitude, timezone, dst, tz_database_timezone) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for (int i = 0; i < testData2.size(); i++) {
                stmt.setObject(i + 1, testData2.get(i));
            }
            stmt.executeUpdate();
        }
        {
            // Add a route to the DB
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO ROUTE_DATA(airline, airline_id, source_airport, source_airport_id, "
                            + "destination_airport, destination_airport_id, codeshare, stops, equipment) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            // Iterates through the List and adds the values to the insert statement
            for (int i = 0; i < testData4.size(); i++) {
                stmt.setObject(i + 1, testData4.get(i));
            }
            stmt.executeUpdate();
        }

        PreparedStatement stmt = dbHandler.prepareStatement("SELECT COUNT(*) FROM ROUTE_DATA WHERE destination_airport_id = ?");
        stmt.setObject(1, 2);

        int result = stmt.executeQuery().getInt(1);
        ResultSet actualOutput = airportAccessor.getIncomingRoutes();
        int airportID = actualOutput.getInt(1);
        int incomingCount = actualOutput.getInt(2);
        Assert.assertEquals(result, incomingCount);
    }


    @Test
    public void testOutgoingRoutes() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();

        {
            // Add a airline to the DB
            PreparedStatement stmt = dbHandler.prepareStatement("INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, "
                    + "callsign, country, active) VALUES (?, ?, ?, ?, ?, ?, ?)");
            for (int i = 0; i < testData3.size(); i++) {
                stmt.setObject(i + 1, testData3.get(i));
            }
            stmt.executeUpdate();
        }
        {
            // Add a source airport to the DB
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                            + "longitude, altitude, timezone, dst, tz_database_timezone) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for (int i = 0; i < testData.size(); i++) {
                stmt.setObject(i + 1, testData.get(i));
            }
            stmt.executeUpdate();
        }
        {
            // Add a destination airport to the DB
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                            + "longitude, altitude, timezone, dst, tz_database_timezone) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for (int i = 0; i < testData2.size(); i++) {
                stmt.setObject(i + 1, testData2.get(i));
            }
            stmt.executeUpdate();
        }
        {
            // Add a route to the DB
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO ROUTE_DATA(airline, airline_id, source_airport, source_airport_id, "
                            + "destination_airport, destination_airport_id, codeshare, stops, equipment) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            // Iterates through the List and adds the values to the insert statement
            for (int i = 0; i < testData4.size(); i++) {
                stmt.setObject(i + 1, testData4.get(i));
            }
            stmt.executeUpdate();
        }

        PreparedStatement stmt = dbHandler.prepareStatement("SELECT COUNT(*) FROM ROUTE_DATA WHERE source_airport_id = ?");
        stmt.setObject(1, 1);

        int result = stmt.executeQuery().getInt(1);
        ResultSet actualOutput = airportAccessor.getOutgoingRoutes();
        int airportID = actualOutput.getInt(1);
        int incomingCount = actualOutput.getInt(2);
        Assert.assertEquals(result, incomingCount);

    }
}
