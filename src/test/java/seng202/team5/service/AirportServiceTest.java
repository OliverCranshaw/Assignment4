package seng202.team5.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import seng202.team5.database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AirportServiceTest extends BaseDatabaseTest {

    private AirportService airportService;

    private final List<Object> testData = List.of("AirportName", "CityName", "CountryName", "IAT", "ICAO", 4.5, 6.2, 424242, 535353f, "E", "Time/Zone");
    private final List<Object> testData2 = List.of("AirportName2", "CityName2", "CountryName2", "ATA", "CAOO", 4.5, 6.2, 424242, 535353f, "E", "Timezone");
    private final List<Object> testData3 = List.of("AirportName3", "CityName3", "CountryName3", "FED", "SFBE", 4.5, 6.2, 424242, 535353f, "E", "Timezone");

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

        ResultSet resultSet = airportService.getData("IAT");

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
        Assert.assertEquals("Time/Zone", resultSet.getString("tz_database_timezone"));

        resultSet = airportService.getData("ICAO");

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
        Assert.assertEquals("Time/Zone", resultSet.getString("tz_database_timezone"));

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
    public void testUpdateRoutes() throws SQLException {
        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();

        // SQLite query used to populate the database with the route
        String routeQuery = "INSERT INTO ROUTE_DATA(airline, airline_id, source_airport, source_airport_id, " +
                "destination_airport, destination_airport_id, codeshare, stops, equipment) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate the database with the airport
        String airportQuery = "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                + "longitude, altitude, timezone, dst, tz_database_timezone) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate the database with the airline
        String airlineQuery = "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, "
                + "callsign, country, active) VALUES (?, ?, ?, ?, ?, ?, ?)";


        // Creating a statement that is then given airport data,  and then executed, inserting it into the database
        PreparedStatement stmt2 = dbHandler.prepareStatement(airportQuery);
        List<Object> tmp2 = Arrays.asList("Heathrow", "London", "England", "ABC", "FJLJ", 89, 123.2, 5000, 43, "JFI", "TZ");
        ArrayList<Object> testAirport1 = new ArrayList<>(tmp2);
        for (int i=1; i < 12; i++) {
            stmt2.setObject(i, testAirport1.get(i-1));
        }
        stmt2.executeUpdate();

        // Creating a statement that is then given airport data,  and then executed, inserting it into the database
        PreparedStatement stmt3 = dbHandler.prepareStatement(airportQuery);
        List<Object> tmp3 = Arrays.asList("ChCh", "Christchurch", "New Zealand", "BCD", "NZNZ", 70, 231, 6000, 42, "JFP", "TZF");
        ArrayList<Object> testAirport2 = new ArrayList<>(tmp3);
        for (int i=1; i < 12; i++) {
            stmt3.setObject(i, testAirport2.get(i-1));
        }
        stmt3.executeUpdate();

        // Creating a statement that is then given airline data, and then executed, inserting it into the database
        PreparedStatement stmt = dbHandler.prepareStatement(airlineQuery);
        ArrayList<String> testAirline = new ArrayList<>(Arrays.asList("testName", "testAlias", "AI", "AIR", "testCallsign", "Argentina", "Y"));
        for (int i=1; i < 8; i++) {
            stmt.setObject(i, testAirline.get(i-1));
        }
        stmt.executeUpdate();

        // Creating a statement that is then given route data, and then executed, inserting it into the database
        PreparedStatement stmt4 = dbHandler.prepareStatement(routeQuery);
        ArrayList<Object> testRoute = new ArrayList<>(Arrays.asList("AIR", 1, "ABC", 1, "NZNZ", 2, "Y", 1, "CR4"));
        for (int i=1; i < 10; i++) {
            stmt4.setObject(i, testRoute.get(i-1));
        }
        stmt4.executeUpdate();

        // Update the airports with a new IATA/ICAO code
        Assert.assertEquals(1, airportService.update(1, "Heathrow", "London", "England", "ABB", "FJLJ", 89d, 123.2, 5000, 43f, "JFI", "TZ"));
        Assert.assertEquals(1, airportService.update(2, "ChCh", "Christchurch", "New Zealand", "BCD", "ABCD", 70d, 231d, 6000, 42f, "JFP", "TZF"));

        // Query to return the route from the database
        routeQuery = "SELECT * FROM ROUTE_DATA WHERE route_id = ?";

        // Creating a statement that will retrieve that route data from the database, and then executing it
        PreparedStatement stmtRoute = dbHandler.prepareStatement(routeQuery);
        stmtRoute.setInt(1, 1);
        ResultSet result = stmtRoute.executeQuery();


        // Check that the route has the new airline ICAO code
        Assert.assertEquals("ABB", result.getString("source_airport"));
        Assert.assertEquals("ABCD", result.getString("destination_airport"));
    }


    @Test
    public void testUpdateFlightEntries() throws SQLException {
        // Initializing a connection with the database
        Connection dbHandler = DBConnection.getConnection();

        // SQLite query used to populate the database with the airport
        String airportQuery = "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                + "longitude, altitude, timezone, dst, tz_database_timezone) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate database with a flight
        String flightStmt = "INSERT INTO FLIGHT_DATA(flight_id, location_type, location, altitude, latitude, longitude) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        // SQLite query used to retrieve a flight entry from the database
        String flightQuery = "SELECT * FROM FLIGHT_DATA WHERE id = ?";

        // Creating a statement that is then given airport data,  and then executed, inserting it into the database
        PreparedStatement stmt = dbHandler.prepareStatement(airportQuery);
        List<Object> tmp = Arrays.asList("Heathrow", "London", "England", "ABC", "FJLJ", 89, 123.2, 5000, 43, "JFI", "TZ");
        ArrayList<Object> testAirport = new ArrayList<>(tmp);
        for (int i=1; i < 12; i++) {
            stmt.setObject(i, testAirport.get(i-1));
        }
        stmt.executeUpdate();

        // Creating a statement that is the given flight data, and then executed, inserting it into the database
        PreparedStatement stmtFlight = dbHandler.prepareStatement(flightStmt);
        List<Object> tmpFlightList = Arrays.asList(1, "APT", "ABC", 120, 320.54, 123.125);
        ArrayList<Object> testFlightArrayList = new ArrayList<>(tmpFlightList);
        for (int i=1; i < 7; i++) {
            stmtFlight.setObject(i, testFlightArrayList.get(i-1));
        }
        stmtFlight.executeUpdate();

        Assert.assertEquals(1, airportService.update(1, "Heathrow", "London", "England", "ABB", "FJLJ", 89d, 123.2, 5000, 43f, "JFI", "TZ"));

        // Creating a statement that will retrieve that flight data from the database, and then executing it
        PreparedStatement stmtRoute = dbHandler.prepareStatement(flightQuery);
        stmtRoute.setInt(1, 1);
        ResultSet result = stmtRoute.executeQuery();

        Assert.assertEquals("ABB", result.getString("location"));
    }


    @Test
    public void testGetIncRouteCount() throws SQLException {

        Connection dbHandler = DBConnection.getConnection();
        RouteService routeService = new RouteService();
        AirlineService airlineService = new AirlineService();

        // Testing empty case
        Hashtable<Integer, Integer> result = airportService.getIncRouteCount();
        Assert.assertEquals(0, result.size());



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


        Hashtable<Integer, Integer> incomingRoutesCount = airportService.getIncRouteCount();
        assertEquals(0, incomingRoutesCount.size());




        // Adding an airline for the routes to use
        airlineService.save("Air New Zealand", "ANZ", "NZ", "ANZ", "AIRNEWZEALAND", "New Zealand", "Y");
        // Adding routes between the two airports
        routeService.save("NZ", "IAT", "CHC", "Y", 3, "GPS");
        routeService.save("NZ", "IAT", "CHC", "N", 0, "GPS CK2");


        Hashtable<Integer, Integer> incomingRoutesCount2 = airportService.getIncRouteCount();
        assertEquals(2, (int) incomingRoutesCount2.get(2));
        Assert.assertFalse(incomingRoutesCount2.containsKey(1));
    }


    @Test
    public void testGetOutRouteCount() throws SQLException {


        Connection dbHandler = DBConnection.getConnection();
        RouteService routeService = new RouteService();
        AirlineService airlineService = new AirlineService();

        // Testing empty case
        Hashtable<Integer, Integer> result = airportService.getOutRouteCount();
        Assert.assertEquals(0, result.size());


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


        Hashtable<Integer, Integer> outgoingRoutesCount = airportService.getOutRouteCount();
        assertEquals(0, outgoingRoutesCount.size());


        // Adding an airline for the routes to use
        airlineService.save("Air New Zealand", "ANZ", "NZ", "ANZ", "AIRNEWZEALAND", "New Zealand", "Y");
        // Adding routes between the two airports
        routeService.save("NZ", "IAT", "CHC", "Y", 3, "GPS");
        routeService.save("NZ", "IAT", "CHC", "N", 0, "GPS CK2");


        Hashtable<Integer, Integer> outgoingRoutesCount2 = airportService.getOutRouteCount();
        Assert.assertFalse(outgoingRoutesCount2.containsKey(2));
        assertEquals(2, (int) outgoingRoutesCount2.get(1));
    }


    @Test
    public void testGetAirportNames() throws SQLException {

        // Establishing connection with database
        Connection dbHandler = DBConnection.getConnection();

        // Query to populate database
        String query = "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                + "longitude, altitude, timezone, dst, tz_database_timezone) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        // Adds airports
        PreparedStatement stmt = dbHandler.prepareStatement(query);
        PreparedStatement stmt2 = dbHandler.prepareStatement(query);
        PreparedStatement stmt3 = dbHandler.prepareStatement(query);

        // Iterates through the List and adds the values to the insert statement
        for (int i = 0; i<testData.size(); i++) {
            stmt.setObject(i + 1, testData.get(i));
            stmt2.setObject(i + 1, testData2.get(i));
            stmt3.setObject(i + 1, testData3.get(i));
            if (i == 3) {
                stmt3.setObject(i + 1, null);
            }
        }
        stmt.executeUpdate();
        stmt2.executeUpdate();
        stmt3.executeUpdate();


        // Testing empty case
        Hashtable<String, String> result = airportService.getAirportNames(new ArrayList<>());
        Assert.assertEquals(0, result.size());


        // Testing case with valid and invalid codes passed, as well as when the airport doesn't have an iata
        ArrayList<String> testList1 = new ArrayList<>();
        testList1.add((String) testData.get(3));
        testList1.add((String) testData2.get(4));
        testList1.add((String) testData3.get(4));
        String nonExistantIata = "ENF";
        testList1.add(nonExistantIata);
        Hashtable<String, String> result2 = airportService.getAirportNames(testList1);
        Assert.assertTrue(result2.containsKey((String) testData.get(3)));
        Assert.assertTrue(result2.containsKey((String) testData2.get(3)));
        Assert.assertTrue(result2.containsKey((String) testData3.get(4)));
        Assert.assertEquals(result2.get((String) testData.get(3)), (String) testData.get(0));
        Assert.assertEquals(result2.get((String) testData2.get(3)), (String) testData2.get(0));
        Assert.assertEquals(result2.get((String) testData3.get(4)), (String) testData3.get(0));
    }
}
