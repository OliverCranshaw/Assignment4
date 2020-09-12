package service;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.sqlite.core.DB;
import seng202.team5.database.DBConnection;
import seng202.team5.database.DBTableInitializer;
import seng202.team5.service.AirportService;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seng202.team5.database.DBInitializer;

public class AirportServiceTest extends BaseDatabaseTest {

    private AirportService airportService;

    private final List<Object> testData = List.of("AirportName", "CityName", "CountryName", "IAT", "ICAO", 4.5, 6.2, 424242, 535353f, "E", "Timezone");

    public AirportServiceTest(String testName) { super(testName); }

    public static Test suite() { return new TestSuite(AirportServiceTest.class); }

    @Override
    protected void setUp() {
        super.setUp();
        airportService = new AirportService();
    }

    public void testUpdateAirport() throws SQLException {
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

        assertEquals(-1, airportService.updateAirport(1, "Namey", "Cityy", "Countryy", "IA", "ICAO", 1.0, 2.0, 3, 4.0f, "E", "Timezoney"));
        assertEquals(-1, airportService.updateAirport(1, "Namey", "Cityy", "Countryy", "IAT", "ICA", 1.0, 2.0, 3, 4.0f, "E", "Timezoney"));
        assertEquals(-1, airportService.updateAirport(1, "Namey", "Cityy", "Countryy", "IAT", "ICAO", 1.0, 2.0, 3, 4.0f, "Something", "Timezoney"));
        assertEquals(-1, airportService.updateAirport(10, "Namey", "Cityy", "Countryy", "IAT", "ICAO", 1.0, 2.0, 3, 4.0f, "E", "Timezoney"));
        assertEquals(1, airportService.updateAirport(1, "Namey", "Cityy", "Countryy", "IAT", "ICAO", 1.0, 2.0, 3, 4.0f, "E", "Timezoney"));
    }

    public void testGetAirportsEmpty() throws SQLException {
        ResultSet resultSet = airportService.getAirports(null, null, null);
        assertFalse(resultSet.next());
    }

    public void testGetAirportsSingle() throws SQLException {
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

                    ResultSet resultSet = airportService.getAirports(testName, testCity, testCountry);

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
                        assertTrue(combination, resultSet.next());
                        for (int i = 0; i<testData.size(); i++) {
                            Object cell = testData.get(i);
                            if (cell instanceof Float) {
                                cell = (double)((Float)cell);
                            }
                            assertEquals(combination, cell, resultSet.getObject(2 + i));
                        }
                    }
                    assertFalse(combination, resultSet.next());
                }
            }
        }

        dbHandler.close();
    }

    public void testGetAirportByID() throws SQLException {
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
            assertEquals(1, changes);

            // Gets the airport ID
            ResultSet rs = stmt.getGeneratedKeys();
            assertTrue(rs.next());
            int key = rs.getInt(1);
            keys.add(key);
        }

        for (int i = 0; i<3; i++) {
            int key = keys.get(i);
            ResultSet resultSet = airportService.getAirport(key);
            assertNotNull("Key " + key + " not found", resultSet);

            // Check that there is at least one result
            assertTrue(resultSet.next());

            // Check name
            assertEquals(testData.get(0) + String.valueOf(i), resultSet.getObject(2));

            // Check rest of the entry
            for (int j = 1; j<testData.size(); j++) {
                Object cell = testData.get(j);
                if (cell instanceof Float) {
                    cell = (double)((Float)cell);
                }
                assertEquals(cell, resultSet.getObject(2 + j));
            }

            // Check there are no more than 1 result
            assertFalse(resultSet.next());
        }
    }

    public void testSaveAirport() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();

        List<Object> testData2 = new ArrayList<>(testData);
        testData2.set(0, testData.get(0) + "2");
        testData2.set(3, "XYZ");
        testData2.set(4, "WXYZ");

        for (List<Object> entry : List.of(testData, testData2)) {
            int res = airportService.saveAirport(
                    (String)entry.get(0),
                    (String)entry.get(1),
                    (String)entry.get(2),
                    (String)entry.get(3),
                    (String)entry.get(4),
                    (double)entry.get(5),
                    (double)entry.get(6),
                    (int)entry.get(7),
                    (float)entry.get(8),
                    (String)entry.get(9),
                    (String)entry.get(10)
            );
            // Check operation did not fail
            assertTrue(res != -1);

            // Query airline data with airport_id=res
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT * FROM AIRPORT_DATA WHERE airport_id = ?");
            stmt.setObject(1, res);
            ResultSet resultSet = stmt.executeQuery();

            // Check that there is at least one result
            assertTrue("Failed to fetch airport_id=" + res, resultSet.next());

            // Check the result contents
            for (int i = 0; i<entry.size(); i++) {
                Object cell = entry.get(i);
                if (cell instanceof Float) {
                    cell = (double)((Float)cell);
                }
                assertEquals(cell, resultSet.getObject(2 + i));
            }

            // Check there are no more than 1 result
            assertFalse(resultSet.next());
        }

        dbHandler.close();
    }

    public void testDeleteAirport() throws SQLException {
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

        // Performs the delete operation
        assertTrue(airportService.deleteAirport(1));

        // Checks that the airport has been deleted
        PreparedStatement stmt2 = dbHandler.prepareStatement(
                "SELECT * FROM AIRPORT_DATA");
        ResultSet resultSet = stmt2.executeQuery();
        assertFalse(resultSet.next());
    }

    public void testIataIsValid() {
        assertTrue(airportService.iataIsValid(null));
        assertTrue(airportService.iataIsValid("IAT"));
        assertFalse(airportService.iataIsValid("IA"));
        assertFalse(airportService.iataIsValid("IATA"));
    }

    public void testIcaoIsValid() {
        assertTrue(airportService.icaoIsValid(null));
        assertTrue(airportService.icaoIsValid("IACO"));
        assertFalse(airportService.icaoIsValid("ICA"));
        assertFalse(airportService.icaoIsValid("ICAOE"));
    }

    public void testDstIsValid() {
        // All valid DSTs
        for (String dst : Arrays.asList("E", "A", "S", "O", "Z", "N", "U")) {
            assertTrue(airportService.dstIsValid(dst));
        }
        assertFalse(airportService.dstIsValid(null));

        // Invalid DSTs
        assertFalse(airportService.dstIsValid(""));
        assertFalse(airportService.dstIsValid("EE"));
    }

    public void testGetMaxID() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();

        for (int i = 0; i<3; i++) {
            // Adds an airport
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                            + "longitude, altitude, timezone, dst, tz_database_timezone) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for (int j = 0; j<testData.size(); j++) {
                stmt.setObject(j+1, testData.get(j));
            }
            stmt.executeUpdate();

            // Checks maximum ID against expected value
            assertEquals(i + 1, airportService.getMaxID());
        }
    }
}
