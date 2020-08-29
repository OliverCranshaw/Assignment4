package service;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import seng202.team5.database.DBConnection;
import seng202.team5.database.DBTableInitializer;
import seng202.team5.service.AirportService;

import java.io.File;
import java.sql.*;
import java.util.List;

import seng202.team5.database.DBInitializer;

public class AirportServiceTest extends BaseDatabaseTest {

    private AirportService airportService;

    private final List<Object> testData = List.of("AirportName", "CityName", "CountryName", "IAT", "ICAO", 4.5, 6.2, 424242, 535353, "E", "Timezone");

    public AirportServiceTest(String testName) { super(testName); }

    public static Test suite() { return new TestSuite(AirportServiceTest.class); }

    @Override
    protected void setUp() {
        super.setUp();
        airportService = new AirportService();
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
                            assertEquals(combination, testData.get(i), resultSet.getObject(2 + i));
                        }
                    }
                    assertFalse(combination, resultSet.next());
                }
            }
        }

        dbHandler.close();
    }

    public void testAddAirport() throws SQLException {
        int res = airportService.saveAirport(
                (String)testData.get(0),
                (String)testData.get(1),
                (String)testData.get(2),
                (String)testData.get(3),
                (String)testData.get(4),
                (double)testData.get(5),
                (double)testData.get(6),
                (int)testData.get(7),
                (int)testData.get(8),
                (String)testData.get(9),
                (String)testData.get(10)
        );
        // Check operation did not fail
        assertTrue(res != -1);

        // Query all airport data
        Statement stmt = DBConnection.getConnection().createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM AIRPORT_DATA");

        // Check that there is at least one result
        assertTrue(resultSet.next());

        // Check the result contents
        for (int i = 0; i<testData.size(); i++) {
            assertEquals(testData.get(i), resultSet.getObject(2 + i));
        }

        // Check there are no more than 1 result
        assertFalse(resultSet.next());
    }
}
