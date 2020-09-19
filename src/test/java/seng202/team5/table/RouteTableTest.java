package seng202.team5.table;

import junit.framework.TestCase;
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

public class RouteTableTest extends TestCase {

    private static final String dbFile = "test.db";

    @Override
    protected void setUp() {
        System.out.println("Setup");
        DBInitializer.createNewDatabase(dbFile);
        DBTableInitializer.initializeTables(dbFile);

        DBConnection.setDatabaseFile(new File(dbFile));

    }

    @Override
    protected void tearDown() throws SQLException {
        System.out.println("Tear down");
        Connection conn = DBConnection.getConnection();
        conn.close();

        new File(dbFile).delete();
    }


    public void testFilterTable() throws SQLException {

        // Getting a connection to the test database
        Connection dbHandler = DBConnection.getConnection();

        // Creating a query to populate the database with test data
        String query = "INSERT INTO ROUTE_DATA(route_id, airline, airline_id, source_airport, source_airport_id, destination_airport, destination_airport_id, "
                + "codeshare, stops, equipment) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Creating a query to retrieve all data from the database
        String retrieveQuery = "SELECT * FROM ROUTE_DATA";

        // Creating the statements to populate the database
        PreparedStatement testData1 = dbHandler.prepareStatement(query);
        ArrayList<Object> testDataList1 = new ArrayList<>(Arrays.asList(1, "airline1", 1, "srcAirport1", 1, "dstAirport1", 1, "Y", 6, "GPS"));

        PreparedStatement testData2 = dbHandler.prepareStatement(query);
        ArrayList<Object> testDataList2 = new ArrayList<>(Arrays.asList(2, "airline2", 2, "srcAirport2", 2, "dstAirport2", 2, "Y", 7, "GPS"));

        PreparedStatement testData3 = dbHandler.prepareStatement(query);
        ArrayList<Object> testDataList3 = new ArrayList<>(Arrays.asList(3, "airline3", 3, "srcAirport3", 3, "dstAirport3", 3, "Y", 8, "GPS ADL"));

        List equipmentList3 = Arrays.asList("GPS", "ADL");
        ArrayList<String> equipmentList = new ArrayList<>(equipmentList3);

        // Filling in the blanks for the database populating statements
        for (int i = 1; i < 11; i++) {
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

        // Filtering the table
        RouteTable testTable = new RouteTable(result);
        testTable.createTable();
        testTable.FilterTable("srcAirport3", "dstAirport3", "not direct", equipmentList);

        // Creating the expected Results array list
        List expectedResult = Arrays.asList("airline3", 3, "srcAirport3", 3, "dstAirport3", 3, "Y", 8, equipmentList);
        ArrayList<Object> expectedResultList = new ArrayList<>(expectedResult);

        // Creating the actual result array list
        ArrayList<ArrayList<Object>> actualResult = testTable.getData();
        // Sanity size check
        assertEquals(1, actualResult.size());

        // Running assertion to check expected same as actual
        for (int i = 1; i < expectedResultList.size(); i++) {
            assertEquals(expectedResultList.get(i), actualResult.get(0).get(i+1));
        }

        // Re-filtering the table by null to check if clears filter properly
        testTable.FilterTable(null, null, null, null);
        assertEquals(testTable.getData().size(), 3);


    }
}
