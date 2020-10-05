package seng202.team5.table;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import seng202.team5.database.DBConnection;
import seng202.team5.database.DBInitializer;
import seng202.team5.database.DBTableInitializer;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class FilterAirlineTableTest {

    private FilterAirlineTable testFilter;
    private static final String dbFile = "test.db";

    @Before
    public void createTable() throws SQLException {
        DBInitializer.createNewDatabase(dbFile);
        DBTableInitializer.initializeTables(dbFile);

        DBConnection.setDatabaseFile(new File(dbFile));

        // Getting a connection to the test database
        Connection dbHandler = DBConnection.getConnection();

        // Creating a query to populate the database with test data
        String query = "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, callsign, country, active) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Creating a query to retrieve all data from the database
        String retrieveQuery = "SELECT * FROM AIRLINE_DATA";


        // Creating the statements to populate the database
        PreparedStatement testData1 = dbHandler.prepareStatement(query);
        ArrayList<Object> testDataList1 = new ArrayList<>(Arrays.asList("airline1", "AIR1", "A1", "AR1", "AIRLINE1", "country1", "Y"));

        PreparedStatement testData2 = dbHandler.prepareStatement(query);
        ArrayList<Object> testDataList2 = new ArrayList<>(Arrays.asList("airline2", "AIR2", "A2", "AR2", "AIRLINE2", "country2", "Y"));

        PreparedStatement testData3 = dbHandler.prepareStatement(query);
        ArrayList<Object> testDataList3 = new ArrayList<>(Arrays.asList("airline3", "AIR3", "A3", "AR3", "AIRLINE3", "country3", "N"));

        // Filling in the blanks for the database populating statements
        for (int i = 1; i < 8; i++) {
            testData1.setObject(i, testDataList1.get(i-1));
            testData2.setObject(i, testDataList2.get(i-1));
            testData3.setObject(i, testDataList3.get(i-1));
        }

        // Creating a arraylist to store the expected data in
        ArrayList<ArrayList<Object>> originalInput = new ArrayList<>();
        originalInput.add(testDataList1);
        originalInput.add(testDataList2);
        originalInput.add(testDataList3);

        // Populating the database
        testData1.executeUpdate();
        testData2.executeUpdate();
        testData3.executeUpdate();

        // Retrieving the data from the database to compare to the expected result
        PreparedStatement retrieve = dbHandler.prepareStatement(retrieveQuery);
        ResultSet result = retrieve.executeQuery();


        // Creating the table to store the actual table result in
        AirlineTable testTable = new AirlineTable(result);
        testTable.createTable();

        // Creating new instance of FilterAirlineTable
        testFilter = new FilterAirlineTable(testTable.getData());
    }

    @After
    public void tearDown() throws SQLException {
        Connection conn = DBConnection.getConnection();
        conn.close();

        new File(dbFile).delete();
    }


    @Test
    public void testCountryDoesNotMatch() {
        ArrayList<String> countryList = new ArrayList<>();
        countryList.add("country6");
        countryList.add("country8");

        testFilter.setCountries(countryList);
        testFilter.containsCountry("country1");

        Assert.assertEquals(true, testFilter.getRemove());
    }


    @Test
    public void testCountryMatches() {
        ArrayList<String> countryList = new ArrayList<>();
        countryList.add("country1");
        countryList.add("country2");

        testFilter.setCountries(countryList);
        testFilter.containsCountry("country1");

        Assert.assertEquals(false, testFilter.getRemove());
    }


    @Test
    public void testActiveDoesNotMatch() {
        testFilter.setActive("Y");
        testFilter.containsActive("N");

        Assert.assertEquals(true, testFilter.getRemove());
    }


    @Test
    public void testActiveMatches() {
        testFilter.setActive("Y");
        testFilter.containsActive("Y");

        Assert.assertEquals(false, testFilter.getRemove());
    }


    @Test
    public void testFilterButNoMatchesFound() {
        ArrayList<String> countryList = new ArrayList<>();
        countryList.add("country6");
        countryList.add("country7");

        testFilter.setCountries(countryList);
        testFilter.setActive(null);
        testFilter.filterTable();

        Assert.assertEquals(0, testFilter.getElements().size());
    }


    @Test
    public void testFilterWithCountries() {
        ArrayList<String> countryList = new ArrayList<>();
        countryList.add("country1");
        countryList.add("country2");

        testFilter.setCountries(countryList);
        testFilter.setActive(null);
        testFilter.filterTable();

        Assert.assertEquals(2, testFilter.getElements().size());
    }


    @Test
    public void testFilterWithActive() {
        testFilter.setCountries(null);
        testFilter.setActive("N");
        testFilter.filterTable();

        Assert.assertEquals(1, testFilter.getElements().size());
    }


    @Test
    public void testFilterWithActiveCountries() {
        ArrayList<String> countryList = new ArrayList<>();
        countryList.add("country1");
        countryList.add("country2");

        testFilter.setCountries(countryList);
        testFilter.setActive("Y");
        testFilter.filterTable();

        Assert.assertEquals(2, testFilter.getElements().size());
    }


    @Test
    public void testFilterWithNoEntries() {
        testFilter.setCountries(null);
        testFilter.setActive(null);
        testFilter.filterTable();

        Assert.assertEquals(3, testFilter.getElements().size());
    }
}
