package service;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import seng202.team5.database.DBConnection;
import seng202.team5.database.DBTableInitializer;
import seng202.team5.service.AirlineService;

import java.io.File;
import java.sql.*;
import java.util.List;

import seng202.team5.database.DBInitializer;

public class AirlineServiceTest extends BaseDatabaseTest {

    private AirlineService airlineService;

    private final List<String> testData = List.of("AirlineName", "AliasName", "ITS", "ICAO", "CallsignStuff", "CountryName", "Y");

    public AirlineServiceTest(String testName) { super(testName); }

    public static Test suite() { return new TestSuite(AirlineServiceTest.class); }

    @Override
    protected void setUp() {
        super.setUp();
        airlineService = new AirlineService();
    }

    public void testGetAirlinesEmpty() throws SQLException {
        ResultSet resultSet = airlineService.getAirlines(null, null, null);
        assertFalse(resultSet.next());
    }

    public void testGetAirlinesSingle() throws SQLException {
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

                    ResultSet resultSet = airlineService.getAirlines(testName, testCountry, testCallsign);

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

    public void testAddAirline() throws SQLException {
        int res = airlineService.saveAirline(
                testData.get(0),
                testData.get(1),
                testData.get(2),
                testData.get(3),
                testData.get(4),
                testData.get(5),
                testData.get(6)
        );

        // Check operation did not fail
        assertTrue(res != -1);

        // Query all airline data
        Statement stmt = DBConnection.getConnection().createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM AIRLINE_DATA");

        // Check that there is at least one result
        assertTrue(resultSet.next());

        // Check the result contents
        for (int i = 0; i<testData.size(); i++) {
            assertEquals(testData.get(i), resultSet.getString(i + 2));
        }

        // Check there are no more than 1 result
        assertFalse(resultSet.next());
    }
}
