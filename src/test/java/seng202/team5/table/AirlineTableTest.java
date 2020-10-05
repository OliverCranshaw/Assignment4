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
import java.util.List;

public class AirlineTableTest {

    private static final String dbFile = "test.db";

    @Before
    public void setUp() {
        DBInitializer.createNewDatabase(dbFile);
        DBTableInitializer.initializeTables(dbFile);

        DBConnection.setDatabaseFile(new File(dbFile));

    }

    @After
    public void tearDown() throws SQLException {
        Connection conn = DBConnection.getConnection();
        conn.close();

        new File(dbFile).delete();
    }


    @Test
    public void testFilterTable() throws SQLException {

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
        ArrayList<String> countryList = new ArrayList<>();
        countryList.add("country1");
        countryList.add("country3");
        testTable.FilterTable(countryList, "Y");

        // Creating the expectedResult ArrayList
        ArrayList<ArrayList<Object>> expectedResult = new ArrayList<>();
        List expList = Arrays.asList("airline1", "AIR1", "A1", "AR1", "AIRLINE1", "country1", "Y");
        expectedResult.add(new ArrayList<Object>(expList));

        // Creating the actual result ArrayList
        ArrayList<ArrayList<Object>> actualResult = testTable.getData();
        Assert.assertEquals(1, actualResult.size());

        // Running the assertions
        for (int j = 0; j < expectedResult.size(); j++) {
            Assert.assertEquals(expectedResult.get(0).get(j), actualResult.get(0).get(j+1));
        }

        ArrayList<String> countryList2 = new ArrayList<>();

        ArrayList<ArrayList<Object>> expectedResult2 = new ArrayList<>();
        List expList2 = Arrays.asList("airline1", "AIR1", "A1", "AR1", "AIRLINE1", "country1", "Y");
        List expList3 = Arrays.asList("airline2", "AIR2", "A2", "AR2", "AIRLINE2", "country2", "Y");
        expectedResult2.add(new ArrayList<>(expList2));
        expectedResult2.add(new ArrayList<>(expList3));

        // Testing the FilterTable works for null
        testTable.FilterTable(null, null);
        Assert.assertEquals(3, testTable.getData().size());

        // Running another Filter to check if partial null works
        testTable.FilterTable(null, "Y");
        ArrayList<ArrayList<Object>> actualResult2 = testTable.getData();
        Assert.assertEquals(2, actualResult2.size());

        for (int k = 0; k < expectedResult2.get(0).size(); k++) {
            Assert.assertEquals(expectedResult2.get(0).get(k), actualResult2.get(0).get(k+1));
            Assert.assertEquals(expectedResult2.get(1).get(k), actualResult2.get(1).get(k+1));
        }
    }
}
