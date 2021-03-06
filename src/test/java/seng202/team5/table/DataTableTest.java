package seng202.team5.table;

import junit.framework.TestCase;
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

public class DataTableTest extends TestCase {

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
    public void testCreateTable() throws SQLException {

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
        ArrayList<ArrayList<Object>> expectedResult = new ArrayList<>();
        expectedResult.add(testDataList1);
        expectedResult.add(testDataList2);
        expectedResult.add(testDataList3);

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
        ArrayList<ArrayList<Object>> actualResult = testTable.getData();

        // Checking the results are as expected
        for (int i = 0; i < expectedResult.size(); i++) {
            for (int j = 0; j < expectedResult.get(i).size(); j++) {
                Assert.assertEquals(expectedResult.get(i).get(j), actualResult.get(i).get(j+1));
            }
        }
    }
}
