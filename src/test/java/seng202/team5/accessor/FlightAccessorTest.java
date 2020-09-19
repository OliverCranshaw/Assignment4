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

public class FlightAccessorTest extends BaseDatabaseTest {
    private FlightAccessor flightAccessor;

    private final List<Object> testData = List.of(1, "ITA", "Location", 1, 2.0, 3.0);

    @Before
    public void setUp() {
        super.setUp();
        flightAccessor = new FlightAccessor();
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
            int key = flightAccessor.save(new ArrayList<>(data));

            // Check operation did not fail
            Assert.assertTrue(key != -1);

            // Query flight data with flight_id=key
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT * FROM FLIGHT_DATA WHERE id = ?");
            stmt.setObject(1, key);
            ResultSet resultSet = stmt.executeQuery();

            // Check that there is at least one result
            Assert.assertTrue("Failed to fetch id=" + key, resultSet.next());

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

        // Adds an flight
        PreparedStatement stmt = dbHandler.prepareStatement(
                "INSERT INTO FLIGHT_DATA(flight_id, location_type, location, altitude, latitude, longitude) "
                        + "VALUES (?, ?, ?, ?, ?, ?)");

        // Iterates through the List and adds the values to the insert statement
        for (int i = 0; i<testData.size(); i++) {
            stmt.setObject(i + 1, testData.get(i));
        }

        Assert.assertEquals(1, stmt.executeUpdate());
        ResultSet keys = stmt.getGeneratedKeys();
        keys.next();
        int key = keys.getInt(1);


        List<Object> newData = List.of(1, "ITB", "Location2", 2, 3.0, 4.0);

        Assert.assertEquals(0, flightAccessor.update(
                10,
                (String)newData.get(1),
                (String)newData.get(2),
                (int)newData.get(3),
                (double)newData.get(4),
                (double)newData.get(5)
        ));


        Assert.assertEquals(1, flightAccessor.update(
                1,
                (String)newData.get(1),
                (String)newData.get(2),
                (int)newData.get(3),
                (double)newData.get(4),
                (double)newData.get(5)
        ));


        // Query flight data with flight_id=key
        PreparedStatement stmt2 = dbHandler.prepareStatement(
                "SELECT * FROM FLIGHT_DATA WHERE id = ?");
        stmt2.setObject(1, key);
        ResultSet resultSet = stmt2.executeQuery();
        // Check that there is at least one result
        Assert.assertTrue("Failed to fetch id=" + 1, resultSet.next());

        // Check the result contents
        for (int i = 0; i<newData.size(); i++) {
            Assert.assertEquals(newData.get(i), resultSet.getObject(2 + i));
        }

        // Check there are no more than 1 result
        Assert.assertFalse(resultSet.next());
    }


    @Test
    public void testDeleteFlight() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();

        // Adds an flight
        for (int i = 0; i<2; i++) {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO FLIGHT_DATA(flight_id, location_type, location, altitude, latitude, longitude) "
                            + "VALUES (?, ?, ?, ?, ?, ?)");
            for (int j = 0; j<testData.size(); j++) {
                stmt.setObject(j+1, testData.get(j));
            }
            stmt.executeUpdate();
        }

        // Checks that nonexistent delete fails
        Assert.assertFalse(flightAccessor.deleteFlight(100));

        // Does a proper delete
        Assert.assertTrue(flightAccessor.deleteFlight(1));

        // Checks that the flight has been deleted
        PreparedStatement stmt2 = dbHandler.prepareStatement(
                "SELECT * FROM FLIGHT_DATA");
        ResultSet resultSet = stmt2.executeQuery();
        Assert.assertFalse(resultSet.next());
    }


    @Test
    public void testDelete() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();

        // Adds an flight
        PreparedStatement stmt = dbHandler.prepareStatement(
                "INSERT INTO FLIGHT_DATA(flight_id, location_type, location, altitude, latitude, longitude) "
                        + "VALUES (?, ?, ?, ?, ?, ?)");
        for (int i = 0; i<testData.size(); i++) {
            stmt.setObject(i+1, testData.get(i));
        }
        stmt.executeUpdate();

        // Checks that nonexistent delete fails
        Assert.assertFalse(flightAccessor.delete(100));

        // Performs the delete operation
        Assert.assertTrue(flightAccessor.delete(1));

        // Checks that the flight has been deleted
        PreparedStatement stmt2 = dbHandler.prepareStatement(
                "SELECT * FROM FLIGHT_DATA");
        ResultSet resultSet = stmt2.executeQuery();
        Assert.assertFalse(resultSet.next());
    }


    @Test
    public void testGetDataByID() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        for (int i = 0; i<3; i++) {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO FLIGHT_DATA(flight_id, location_type, location, altitude, latitude, longitude) "
                            + "VALUES (?, ?, ?, ?, ?, ?)");

            // Iterates through the List and adds the values to the insert statement
            for (int j = 0; j<testData.size(); j++) {
                stmt.setObject(j + 1, testData.get(j));
            }

            // Executes the insert operation
            Assert.assertEquals(1, stmt.executeUpdate());
        }

        ResultSet resultSet = flightAccessor.getData(1);
        Assert.assertNotNull(resultSet);

        for (int i = 0; i<3; i++) {
            Assert.assertTrue(resultSet.next());

            // Check rest of the entry
            for (int j = 0; j<testData.size(); j++) {
                Assert.assertEquals(testData.get(j), resultSet.getObject(2 + j));
            }
        }
        // Check there are no more than 3 results
        Assert.assertFalse(resultSet.next());
    }


    @Test
    public void testGetData() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        PreparedStatement stmt = dbHandler.prepareStatement(
                "INSERT INTO FLIGHT_DATA(flight_id, location_type, location, altitude, latitude, longitude) "
                        + "VALUES (?, ?, ?, ?, ?, ?)");
        for (int i = 0; i<testData.size(); i++) {
            stmt.setObject(i+1, testData.get(i));
        }
        stmt.executeUpdate();

        String locationType = (String)testData.get(1);
        String location = (String)testData.get(2);

        // Test all combinations
        for (String testLocationType : new String[]{null, locationType, "Not"+locationType}) {
            boolean validLocationType = testLocationType == null || testLocationType.equals(locationType);

            for (String testLocation : new String[]{null, location, "Not"+location}) {
                boolean validLocation = testLocation == null || testLocation.equals(location);

                ResultSet resultSet = flightAccessor.getData(testLocationType, testLocation);

                String combination = new StringBuilder()
                        .append("name=")
                        .append(testLocationType)
                        .append(", city=")
                        .append(testLocation)
                        .toString();

                // If filter matches the data in the database
                if (validLocationType && validLocation) {
                    Assert.assertTrue(combination, resultSet.next());
                    for (int i = 0; i<testData.size(); i++) {
                        Assert.assertEquals(combination, testData.get(i), resultSet.getObject(2 + i));
                    }
                }
                Assert.assertFalse(combination, resultSet.next());
            }
        }

        dbHandler.close();
    }


    @Test
    public void testFlightExists() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        PreparedStatement stmt = dbHandler.prepareStatement(
                "INSERT INTO FLIGHT_DATA(flight_id, location_type, location, altitude, latitude, longitude) "
                        + "VALUES (?, ?, ?, ?, ?, ?)");

        // Iterates through the List and adds the values to the insert statement
        for (int i = 0; i<testData.size(); i++) {
            stmt.setObject(i + 1, testData.get(i));
        }

        // Executes the insert operation
        Assert.assertEquals(1, stmt.executeUpdate());

        Assert.assertTrue(flightAccessor.flightExists(1));
        Assert.assertFalse(flightAccessor.flightExists(0));
    }


    @Test
    public void testDataExists() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        PreparedStatement stmt = dbHandler.prepareStatement(
                "INSERT INTO FLIGHT_DATA(flight_id, location_type, location, altitude, latitude, longitude) "
                        + "VALUES (?, ?, ?, ?, ?, ?)");

        // Iterates through the List and adds the values to the insert statement
        for (int i = 0; i<testData.size(); i++) {
            stmt.setObject(i + 1, testData.get(i));
        }

        // Executes the insert operation, sets the result to the id of the new flight
        Assert.assertEquals(1, stmt.executeUpdate());

        ResultSet rs = stmt.getGeneratedKeys();
        Assert.assertTrue(rs.next());
        int key = rs.getInt(1);


        Assert.assertTrue(flightAccessor.dataExists(key));
        Assert.assertFalse(flightAccessor.dataExists(key + 1));
    }


    @Test
    public void testGetMaxFlightID() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();

        int maxKey = -1;

        for (int i = 0; i<3; i++) {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO FLIGHT_DATA(flight_id, location_type, location, altitude, latitude, longitude) "
                            + "VALUES (?, ?, ?, ?, ?, ?)");

            stmt.setObject(1, i + 1);

            // Iterates through the List and adds the values to the insert statement
            for (int j = 1; j<testData.size(); j++) {
                stmt.setObject(j + 1, testData.get(j));
            }

            // Executes the insert operation
            Assert.assertEquals(1, stmt.executeUpdate());

            Assert.assertEquals(i + 1, flightAccessor.getMaxFlightID());
        }
    }


    @Test
    public void testGetMaxID() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();

        int maxKey = -1;

        for (int i = 0; i<3; i++) {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO FLIGHT_DATA(flight_id, location_type, location, altitude, latitude, longitude) "
                            + "VALUES (?, ?, ?, ?, ?, ?)");


            // Iterates through the List and adds the values to the insert statement
            for (int j = 0; j<testData.size(); j++) {
                stmt.setObject(j + 1, testData.get(j));
            }

            // Executes the insert operation, sets the result to the flight_id of the new flight
            int changes = stmt.executeUpdate();
            Assert.assertEquals(1, changes);

            // Gets the id
            ResultSet rs = stmt.getGeneratedKeys();
            Assert.assertTrue(rs.next());
            int key = rs.getInt(1);
            maxKey = Math.max(maxKey, key);

            Assert.assertEquals(maxKey, flightAccessor.getMaxID());
        }
    }
}
