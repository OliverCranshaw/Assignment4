package seng202.team5.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import seng202.team5.database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AirportServiceTest extends BaseDatabaseTest {

    private AirportService airportService;

    private final List<Object> testData = List.of("AirportName", "CityName", "CountryName", "IAT", "ICAO", 4.5, 6.2, 424242, 535353f, "E", "Time/Zone");

    @Before
    public void setUp() {
        super.setUp();
        airportService = new AirportService();
    }


    @Test
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

        assertEquals(0, airportService.update(10, "Namey", "Cityy", "Countryy", "IAC", "ICAA", 1.0, 2.0, 3, 4.0f, "E", "Sometime/Someplace"));
        assertEquals(1, airportService.update(1, "Namey", "Cityy", "Countryy", "IAC", "ICAA", 1.0, 2.0, 3, 4.0f, "E", "Sometime/Someplace"));
    }


    @Test
    public void testGetAirportsEmpty() throws SQLException {
        ResultSet resultSet = airportService.getData(null, null, null);
        Assert.assertFalse(resultSet.next());
    }


    @Test
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

                    ResultSet resultSet = airportService.getData(testName, testCity, testCountry);

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
                            assertEquals(combination, cell, resultSet.getObject(2 + i));
                        }
                    }
                    Assert.assertFalse(combination, resultSet.next());
                }
            }
        }

        dbHandler.close();
    }


    @Test
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
            Assert.assertTrue(rs.next());
            int key = rs.getInt(1);
            keys.add(key);
        }

        for (int i = 0; i<3; i++) {
            int key = keys.get(i);
            ResultSet resultSet = airportService.getData(key);
            Assert.assertNotNull("Key " + key + " not found", resultSet);

            // Check that there is at least one result
            Assert.assertTrue(resultSet.next());

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
            Assert.assertFalse(resultSet.next());
        }
    }


    @Test
    public void testSaveAirport() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();

        List<Object> testData2 = new ArrayList<>(testData);
        testData2.set(0, testData.get(0) + "2");
        testData2.set(3, "XYZ");
        testData2.set(4, "WXYZ");

        for (List<Object> entry : List.of(testData, testData2)) {
            int res = airportService.save(
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
            Assert.assertTrue(res != -1);

            // Query airline data with airport_id=res
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT * FROM AIRPORT_DATA WHERE airport_id = ?");
            stmt.setObject(1, res);
            ResultSet resultSet = stmt.executeQuery();

            // Check that there is at least one result
            Assert.assertTrue("Failed to fetch airport_id=" + res, resultSet.next());

            // Check the result contents
            for (int i = 0; i<entry.size(); i++) {
                Object cell = entry.get(i);
                if (cell instanceof Float) {
                    cell = (double)((Float)cell);
                }
                assertEquals(cell, resultSet.getObject(2 + i));
            }

            // Check there are no more than 1 result
            Assert.assertFalse(resultSet.next());
        }

        dbHandler.close();
    }


    @Test
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
        Assert.assertTrue(airportService.delete(1));

        // Checks that the airport has been deleted
        PreparedStatement stmt2 = dbHandler.prepareStatement(
                "SELECT * FROM AIRPORT_DATA");
        ResultSet resultSet = stmt2.executeQuery();
        Assert.assertFalse(resultSet.next());
    }


    @Test
    public void testIataIsValid() {
        Assert.assertTrue(airportService.iataIsValid(null));
        Assert.assertTrue(airportService.iataIsValid("IAT"));
    }


    @Test
    public void testIcaoIsValid() {
        Assert.assertTrue(airportService.icaoIsValid(null));
        Assert.assertTrue(airportService.icaoIsValid("IACO"));
    }


    @Test
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

    @Test
    public void testDeleteFlight() throws SQLException {
        Connection dbHandler = DBConnection.getConnection();
        FlightService flightService = new FlightService();

        // Adds an airport
        PreparedStatement stmt = dbHandler.prepareStatement(
                "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                        + "longitude, altitude, timezone, dst, tz_database_timezone) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        for (int j = 0; j < testData.size(); j++) {
            stmt.setObject(j + 1, testData.get(j));
        }
        stmt.executeUpdate();

        List<Object> flightData = List.of(flightService.getNextFlightID(), "APT", "IAT", 4242, 235.1621, -125.12451);
        stmt = dbHandler.prepareStatement(
                "INSERT INTO FLIGHT_DATA(flight_id, location_type, location, " +
                    "altitude, latitude, longitude) VALUES (?, ?, ?, ?, ?, ?)");
        for (int j = 0; j < flightData.size(); j++) {
            stmt.setObject(j + 1, flightData.get(j));
        }
        stmt.executeUpdate();

        flightData = List.of(flightService.getNextFlightID(), "APT", "ICAO", 4242, 235.1621, -125.12451);
        stmt = dbHandler.prepareStatement(
                "INSERT INTO FLIGHT_DATA(flight_id, location_type, location, " +
                        "altitude, latitude, longitude) VALUES (?, ?, ?, ?, ?, ?)");
        for (int j = 0; j < flightData.size(); j++) {
            stmt.setObject(j + 1, flightData.get(j));
        }
        stmt.executeUpdate();

        airportService.delete(1);

        Assert.assertFalse(airportService.dataExists("IAT"));
        Assert.assertFalse(airportService.dataExists("ICAO"));

        assertEquals(0, flightService.getMaxID());
    }


    @Test
    public void testGetIncRouteCount() throws SQLException {

        Connection dbHandler = DBConnection.getConnection();
        RouteService routeService = new RouteService();
        AirlineService airlineService = new AirlineService();

        // Adding a couple airports in order to be able to test routes between them
        PreparedStatement stmt = dbHandler.prepareStatement(
                "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                        + "longitude, altitude, timezone, dst, tz_database_timezone) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        for (int j = 0; j < testData.size(); j++) {
            stmt.setObject(j + 1, testData.get(j));
        }
        stmt.executeUpdate();

        PreparedStatement stmt2 = dbHandler.prepareStatement(
                "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                        + "longitude, altitude, timezone, dst, tz_database_timezone) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        List<Object> testData2 = List.of("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "CHCH", 43.5321, 172.6396, 20, 12, "Z", "Pacific/Auckland");
        for (int i = 0; i < testData2.size(); i++) {
            stmt2.setObject(i+1, testData2.get(i));
        }
        stmt2.executeUpdate();


        // Adding an airline for the routes to use
        airlineService.save("Air New Zealand", "ANZ", "NZ", "ANZ", "AIRNEWZEALAND", "New Zealand", "Y");
        // Adding routes between the two airports
        routeService.save("NZ", "IAT", "CHC", "Y", 3, "GPS");
        routeService.save("NZ", "IAT", "CHC", "N", 0, "GPS CK2");


        int incomingRoutesCount1 = airportService.getIncRouteCount(2);
        int incomingRoutesCount2 = airportService.getIncRouteCount(1);
        int nonExistingData = airportService.getIncRouteCount(5);
        assertEquals(2, incomingRoutesCount1);
        assertEquals(0, incomingRoutesCount2);
        assertEquals(-1, nonExistingData);
    }

    @Test
    public void testGetOutRouteCount() throws SQLException {

        Connection dbHandler = DBConnection.getConnection();
        RouteService routeService = new RouteService();
        AirlineService airlineService = new AirlineService();

        // Adding a couple airports in order to be able to test routes between them
        PreparedStatement stmt = dbHandler.prepareStatement(
                "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                        + "longitude, altitude, timezone, dst, tz_database_timezone) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        for (int j = 0; j < testData.size(); j++) {
            stmt.setObject(j + 1, testData.get(j));
        }
        stmt.executeUpdate();

        PreparedStatement stmt2 = dbHandler.prepareStatement(
                "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                        + "longitude, altitude, timezone, dst, tz_database_timezone) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        List<Object> testData2 = List.of("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "CHCH", 43.5321, 172.6396, 20, 12, "Z", "Pacific/Auckland");
        for (int i = 0; i < testData2.size(); i++) {
            stmt2.setObject(i+1, testData2.get(i));
        }
        stmt2.executeUpdate();


        // Adding an airline for the routes to use
        airlineService.save("Air New Zealand", "ANZ", "NZ", "ANZ", "AIRNEWZEALAND", "New Zealand", "Y");
        // Adding routes between the two airports
        routeService.save("NZ", "IAT", "CHC", "Y", 3, "GPS");
        routeService.save("NZ", "IAT", "CHC", "N", 0, "GPS CK2");


        int outgoingRoutesCount1 = airportService.getOutRouteCount(2);
        int outgoingRoutesCount2 = airportService.getOutRouteCount(1);
        int nonExistingData = airportService.getOutRouteCount(5);
        assertEquals(0, outgoingRoutesCount1);
        assertEquals(2, outgoingRoutesCount2);
        assertEquals(-1, nonExistingData);


    }





}
