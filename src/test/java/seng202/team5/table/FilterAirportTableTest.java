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

public class FilterAirportTableTest {

    private FilterAirportTable testFilter;
    private static final String dbFile = "test.db";

    @Before
    public void setUp() throws SQLException {
        System.out.println("Setup");
        DBInitializer.createNewDatabase(dbFile);
        DBTableInitializer.initializeTables(dbFile);

        DBConnection.setDatabaseFile(new File(dbFile));

        // Getting a connection to the test database
        Connection dbHandler = DBConnection.getConnection();

        // Creating a query to populate the database with test data
        String query = "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz_database_timezone) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Creating a query to retrieve all data from the database
        String retrieveQuery = "SELECT * FROM AIRPORT_DATA";

        // Creating the statements to populate the database
        PreparedStatement testData1 = dbHandler.prepareStatement(query);
        ArrayList<Object> testDataList1 = new ArrayList<>(Arrays.asList("airport1", "city1", "country1", "ia1", "ica1", 33.3, 44.4, 4344, "time1", "dst1", "tz1"));

        PreparedStatement testData2 = dbHandler.prepareStatement(query);
        ArrayList<Object> testDataList2 = new ArrayList<>(Arrays.asList("airport2", "city2", "country2", "ia2", "ica2", 33.2, 44.2, 4342, "time2", "dst2", "tz2"));

        PreparedStatement testData3 = dbHandler.prepareStatement(query);
        ArrayList<Object> testDataList3 = new ArrayList<>(Arrays.asList("airport3", "city3", "country3", "ia3", "ica3", 33.1, 44.1, 4341, "time3", "dst3", "tz3"));

        // Filling in the blanks for the database populating statements
        for (int i = 1; i < 12; i++) {
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
        AirportTable testTable = new AirportTable(result);
        testTable.createTable();

        // Creating new instance of FilterAirportTable
        System.out.println(testTable.getData());
        testFilter = new FilterAirportTable(testTable.getData());
    }

    @After
    public void tearDown() throws SQLException {
        System.out.println("Tear down");
        Connection conn = DBConnection.getConnection();
        conn.close();

        new File(dbFile).delete();
    }


    @Test
    public void testFilterButNoMatchesFound() {
        ArrayList<String> countryList = new ArrayList<>();
        countryList.add("country6");
        countryList.add("country8");

        testFilter.setCountries(countryList);
        testFilter.filterTable();

        Assert.assertEquals(0, testFilter.getElements().size());
    }


    @Test
    public void testFilterMatchesFound() {
        ArrayList<String> countryList = new ArrayList<>();
        countryList.add("country1");
        countryList.add("country2");

        testFilter.setCountries(countryList);
        testFilter.filterTable();

        Assert.assertEquals(2, testFilter.getElements().size());
    }


    @Test
    public void testFilterWithNoEntries() {
        testFilter.setCountries(null);
        testFilter.filterTable();

        Assert.assertEquals(3, testFilter.getElements().size());
    }
}
