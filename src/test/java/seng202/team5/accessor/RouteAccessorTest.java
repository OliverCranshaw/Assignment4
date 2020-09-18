package seng202.team5.accessor;

import junit.framework.Test;
import junit.framework.TestSuite;
import seng202.team5.database.DBConnection;
import seng202.team5.service.BaseDatabaseTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteAccessorTest extends BaseDatabaseTest {
    private RouteAccessor routeAccessor;

    private final List<Object> testData = List.of("IT", 1, "IA1", 1, "IA2", 2, "N", 100, "ABC");

    public RouteAccessorTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(RouteAccessorTest.class);
    }

    @Override
    protected void setUp() {
        super.setUp();
        routeAccessor = new RouteAccessor();

        Connection dbHandler = DBConnection.getConnection();
        try {
            {
                // Add a airline to the DB
                PreparedStatement stmt = dbHandler.prepareStatement("INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, "
                        + "callsign, country, active) VALUES (?, ?, ?, ?, ?, ?, ?)");
                List<Object> airlineData = List.of("AirlineName", "AliasName", "IT", "ICA", "CallsignStuff", "CountryName", "Y");
                for (int i = 0; i < airlineData.size(); i++) {
                    stmt.setObject(i + 1, airlineData.get(i));
                }
                stmt.executeUpdate();
            }
            {
                // Add a source airport to the DB
                PreparedStatement stmt = dbHandler.prepareStatement(
                        "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                                + "longitude, altitude, timezone, dst, tz_database_timezone) "
                                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                List<Object> airportData = List.of("SrcAirportName", "CityName1", "CountryName1", "IA1", "ICA1", 4.5, 6.2, 424242, 535353f, "E", "Timezone");
                for (int i = 0; i < airportData.size(); i++) {
                    stmt.setObject(i + 1, airportData.get(i));
                }
            }
            {
                // Add a destination airport to the DB
                PreparedStatement stmt = dbHandler.prepareStatement(
                        "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                                + "longitude, altitude, timezone, dst, tz_database_timezone) "
                                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                List<Object> airportData = List.of("DestAirportName", "CityName2", "CountryName2", "IA2", "ICA2", 4.5, 6.2, 424242, 535353f, "E", "Timezone");
                for (int i = 0; i < airportData.size(); i++) {
                    stmt.setObject(i + 1, airportData.get(i));
                }
            }
        } catch (SQLException e) {
            // Really shouldn't happen
            assert false;
        }
    }

    public void testSave() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();

        List<List<Object>> testSets = new ArrayList<>();
        testSets.add(testData);
        List<Object> testData2 = new ArrayList<>(testData);
        testData2.set(6, "Y");
        testSets.add(testData2);

        for (List<Object> data : testSets) {
            int key = routeAccessor.save(data);

            // Check operation did not fail
            assertTrue(key != -1);

            // Query route data with route_id=key
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT * FROM ROUTE_DATA WHERE route_id = ?");
            stmt.setObject(1, key);
            ResultSet resultSet = stmt.executeQuery();

            // Check that there is at least one result
            assertTrue("Failed to fetch route_id=" + key, resultSet.next());

            // Check the result contents
            for (int i = 0; i < data.size(); i++) {
                assertEquals(data.get(i), resultSet.getObject(2 + i));
            }

            // Check there are no more than 1 result
            assertFalse(resultSet.next());
        }
    }

    public void testUpdate() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();

        // Adds an route
        PreparedStatement stmt = dbHandler.prepareStatement(
                "INSERT INTO ROUTE_DATA(airline, airline_id, source_airport, source_airport_id, "
                        + "destination_airport, destination_airport_id, codeshare, stops, equipment) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

        // Iterates through the List and adds the values to the insert statement
        for (int i = 0; i < testData.size(); i++) {
            stmt.setObject(i + 1, testData.get(i));
        }

        assertEquals(1, stmt.executeUpdate());
        ResultSet keys = stmt.getGeneratedKeys();
        keys.next();
        int key = keys.getInt(1);


        List<Object> newData = List.of("IT", 1, "IA2", 2, "IA1", 1, "Y", 100, "ABC");

        assertEquals(0, routeAccessor.update(
                10,
                (String) newData.get(0),
                (int) newData.get(1),
                (String) newData.get(2),
                (int) newData.get(3),
                (String) newData.get(4),
                (int) newData.get(5),
                (String) newData.get(6),
                (int) newData.get(7),
                (String) newData.get(8)
        ));


        assertEquals(1, routeAccessor.update(
                1,
                (String) newData.get(0),
                (int) newData.get(1),
                (String) newData.get(2),
                (int) newData.get(3),
                (String) newData.get(4),
                (int) newData.get(5),
                (String) newData.get(6),
                (int) newData.get(7),
                (String) newData.get(8)
        ));


        // Query route data with route_id=key
        PreparedStatement stmt2 = dbHandler.prepareStatement(
                "SELECT * FROM ROUTE_DATA WHERE route_id = ?");
        stmt2.setObject(1, key);
        ResultSet resultSet = stmt2.executeQuery();
        // Check that there is at least one result
        assertTrue("Failed to fetch route_id=" + 1, resultSet.next());

        // Check the result contents
        for (int i = 0; i < newData.size(); i++) {
            assertEquals(newData.get(i), resultSet.getObject(2 + i));
        }

        // Check there are no more than 1 result
        assertFalse(resultSet.next());
    }

    public void testDelete() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        PreparedStatement stmt = dbHandler.prepareStatement(
                "INSERT INTO ROUTE_DATA(airline, airline_id, source_airport, source_airport_id, "
                        + "destination_airport, destination_airport_id, codeshare, stops, equipment) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

        // Iterates through the List and adds the values to the insert statement
        for (int i = 0; i < testData.size(); i++) {
            stmt.setObject(i + 1, testData.get(i));
        }

        assertEquals(1, stmt.executeUpdate());

        // Gets the airline ID
        ResultSet rs = stmt.getGeneratedKeys();
        assertTrue(rs.next());
        int id = rs.getInt(1);
        rs.close();

        // Test that a failed delete fails
        assertFalse(routeAccessor.delete(100));

        // Performs the delete
        assertTrue(routeAccessor.delete(id));

        PreparedStatement stmt2 = dbHandler.prepareStatement(
                "SELECT * FROM ROUTE_DATA");
        ResultSet resultSet = stmt2.executeQuery();

        // Check that we don't find anything
        assertFalse(resultSet.next());

        dbHandler.close();
    }

    public void testGetDataByID() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        List<Integer> keys = new ArrayList<>();

        for (int i = 0; i < 3; i++) {

            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO ROUTE_DATA(airline, airline_id, source_airport, source_airport_id, "
                            + "destination_airport, destination_airport_id, codeshare, stops, equipment) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            // Iterates through the List and adds the values to the insert statement
            for (int j = 0; j < testData.size(); j++) {
                if (j == 7) {
                    stmt.setObject(j + 1, (int) testData.get(j) + i);
                } else {
                    stmt.setObject(j + 1, testData.get(j));
                }
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

        for (int i = 0; i < 3; i++) {
            int key = keys.get(i);
            ResultSet resultSet = routeAccessor.getData(key);
            assertNotNull("Key " + key + " not found", resultSet);

            // Check that there is at least one result
            assertTrue(resultSet.next());

            // Check entry
            for (int j = 0; j < testData.size(); j++) {
                if (j == 7) {
                    assertEquals((int) testData.get(j) + i, resultSet.getObject(2 + j));
                } else {
                    assertEquals(testData.get(j), resultSet.getObject(2 + j));
                }
            }

            // Check there are no more than 1 result
            assertFalse(resultSet.next());
        }

        assertFalse(routeAccessor.getData(10000).next());
    }

    public void testGetData() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        PreparedStatement stmt = dbHandler.prepareStatement(
                "INSERT INTO ROUTE_DATA(airline, airline_id, source_airport, source_airport_id, "
                        + "destination_airport, destination_airport_id, codeshare, stops, equipment) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        for (int i = 0; i < testData.size(); i++) {
            stmt.setObject(i + 1, testData.get(i));
        }
        stmt.executeUpdate();

        String src = (String) testData.get(2);
        String dest = (String) testData.get(4);
        int stops = (int) testData.get(7);
        String equipment = (String) testData.get(8);

        // Test result data is valid
        {
            ResultSet resultSet = routeAccessor.getData(null, null, -1, null);
            assertTrue(resultSet.next());

            // Check the result contents
            for (int j = 0; j < testData.size(); j++) {
                assertEquals(testData.get(j), resultSet.getObject(2 + j));
            }
        }

        // Simple valid single search test
        assertTrue(routeAccessor.getData(new ArrayList<>(List.of(src)), null, -1, null).next());
        assertTrue(routeAccessor.getData(null, new ArrayList<>(List.of(dest)), -1, null).next());
        assertTrue(routeAccessor.getData(null, null, stops, null).next());
        assertTrue(routeAccessor.getData(null, null, -1, equipment).next());

        // Simple invalid single search test
        assertFalse(routeAccessor.getData(new ArrayList<>(List.of("Not" + src)), null, -1, null).next());
        assertFalse(routeAccessor.getData(null, new ArrayList<>(List.of("Not" + dest)), -1, null).next());
        assertFalse(routeAccessor.getData(null, null, stops + 1, null).next());
        assertFalse(routeAccessor.getData(null, null, -1, "Not" + equipment).next());

        // Test valid double conditions
        assertTrue(routeAccessor.getData(new ArrayList<>(List.of(src)), new ArrayList<>(List.of(dest)), -1, null).next());
        assertTrue(routeAccessor.getData(null, new ArrayList<>(List.of(dest)), -1, null).next());
        assertTrue(routeAccessor.getData(null, null, stops, equipment).next());

        // Test invalid double conditions
        assertFalse(routeAccessor.getData(new ArrayList<>(List.of("Not" + src)), new ArrayList<>(List.of(dest)), -1, null).next());
        assertFalse(routeAccessor.getData(new ArrayList<>(List.of(src)), new ArrayList<>(List.of("Not" + dest)), -1, null).next());

        assertFalse(routeAccessor.getData(null, new ArrayList<>(List.of("Not" + dest)), stops, null).next());
        assertFalse(routeAccessor.getData(null, new ArrayList<>(List.of(dest)), stops + 1, null).next());

        assertFalse(routeAccessor.getData(null, null, stops + 1, equipment).next());
        assertFalse(routeAccessor.getData(null, null, stops, "Not" + equipment).next());

        // Test double airports
        assertTrue(routeAccessor.getData(new ArrayList<>(List.of("Not" + src, src)), null, -1, null).next());
        assertTrue(routeAccessor.getData(null, new ArrayList<>(List.of("Not" + dest, dest)), -1, null).next());

        assertFalse(routeAccessor.getData(null, new ArrayList<>(List.of("Not" + dest, "Not2" + dest)), -1, null).next());
        assertFalse(routeAccessor.getData(new ArrayList<>(List.of("Not" + src, "Not2" + src)), null, -1, null).next());

        // Test "(a or b) and c" vs "a or (b and c)"
        assertFalse(routeAccessor.getData(new ArrayList<>(List.of(src, "Not" + src)), null, stops + 1, null).next());
    }

    public void testDataExists() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        PreparedStatement stmt = dbHandler.prepareStatement(
                "INSERT INTO ROUTE_DATA(airline, airline_id, source_airport, source_airport_id, "
                        + "destination_airport, destination_airport_id, codeshare, stops, equipment) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        for (int i = 0; i < testData.size(); i++) {
            stmt.setObject(i + 1, testData.get(i));
        }
        stmt.executeUpdate();

        // Executes the insert operation
        assertEquals(1, stmt.executeUpdate());

        ResultSet rs = stmt.getGeneratedKeys();
        assertTrue(rs.next());
        int key = rs.getInt(1);

        assertTrue(routeAccessor.dataExists(key));
        assertFalse(routeAccessor.dataExists(key + 1));
    }

    public void testGetMaxID() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();

        int maxKey = -1;
        for (int i = 0; i<3; i++) {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO ROUTE_DATA(airline, airline_id, source_airport, source_airport_id, "
                            + "destination_airport, destination_airport_id, codeshare, stops, equipment) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            // Iterates through the List and adds the values to the insert statement
            for (int j = 0; j<testData.size(); j++) {
                stmt.setObject(j + 1, testData.get(j));
            }

            // Executes the insert operation, sets the result to the route_id of the new route
            int changes = stmt.executeUpdate();
            assertEquals(1, changes);

            // Gets the route ID
            ResultSet rs = stmt.getGeneratedKeys();
            assertTrue(rs.next());
            int key = rs.getInt(1);
            maxKey = Math.max(maxKey, key);

            assertEquals(maxKey, routeAccessor.getMaxID());
        }
    }
}
