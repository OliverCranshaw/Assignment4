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

public class FilterRouteTableTest {

    private FilterRouteTable testFilter;
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
        ArrayList<Object> testDataList3 = new ArrayList<>(Arrays.asList(3, "airline3", 3, "srcAirport3", 3, "dstAirport3", 3, "Y", 0, "GPS ADL"));

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

        // Creating new instance of FilterRouteTable
        testFilter = new FilterRouteTable(testTable.getData());
    }

    @After
    public void tearDown() throws SQLException {
        System.out.println("Tear down");
        Connection conn = DBConnection.getConnection();
        conn.close();

        new File(dbFile).delete();
    }


    @Test
    public void testAirportDepDoesNotMatch() {
        testFilter.setAirportDep("srcAirport1");
        testFilter.containsAirportDep("srcAirport6");

        Assert.assertEquals(true, testFilter.getRemove());
    }


    @Test
    public void testAirportDepMatches() {
        testFilter.setAirportDep("srcAirport1");
        testFilter.containsAirportDep("srcAirport1");

        Assert.assertEquals(false, testFilter.getRemove());
    }


    @Test
    public void testAirportDesDoesNotMatch() {
        testFilter.setAirportDep("dstAirport1");
        testFilter.containsAirportDep("dstAirport6");

        Assert.assertEquals(true, testFilter.getRemove());
    }


    @Test
    public void testAirportDesMatches() {
        testFilter.setAirportDep("dstAirport1");
        testFilter.containsAirportDep("dstAirport1");

        Assert.assertEquals(false, testFilter.getRemove());
    }


    @Test
    public void testIdDirectDoesNotMatch() {
        testFilter.setDirect("direct");
        testFilter.isDirect(5);

        Assert.assertEquals(true, testFilter.getRemove());
    }


    @Test
    public void testIsDirectMatches() {
        testFilter.setDirect("direct");
        testFilter.isDirect(0);

        Assert.assertEquals(false, testFilter.getRemove());
    }


    @Test
    public void testEquipDoesNotMatch() {
        ArrayList<String> equipmentList = new ArrayList<>(Arrays.asList("GPS"));
        ArrayList<String> currentEquipment = new ArrayList<>(Arrays.asList("III"));

        testFilter.setEquip(equipmentList);
        testFilter.containsEquip(currentEquipment);

        Assert.assertEquals(true, testFilter.getRemove());
    }


    @Test
    public void testEquipMatches() {
        ArrayList<String> equipmentList = new ArrayList<>(Arrays.asList("GPS"));
        ArrayList<String> currentEquipment = new ArrayList<>(Arrays.asList("GPS"));

        testFilter.setEquip(equipmentList);
        testFilter.containsEquip(currentEquipment);

        Assert.assertEquals(false, testFilter.getRemove());
    }


    @Test
    public void testFilterButNoMatchesFound() {
        ArrayList<String> equipmentList = new ArrayList<>(Arrays.asList("III"));

        testFilter.setAirportDep("srcAirport8");
        testFilter.setAirportDes("dstAirport8");
        testFilter.setDirect(null);
        testFilter.setEquip(equipmentList);
        testFilter.filterTable();

        Assert.assertEquals(0, testFilter.getElements().size());
    }


    @Test
    public void testFilterWithDepAirport() {
        testFilter.setAirportDep("srcAirport3");
        testFilter.setAirportDes(null);
        testFilter.setDirect(null);
        testFilter.setEquip(null);
        testFilter.filterTable();

        Assert.assertEquals(1, testFilter.getElements().size());
    }


    @Test
    public void testFilterWithDesAirport() {
        testFilter.setAirportDep(null);
        testFilter.setAirportDes("dstAirport2");
        testFilter.setDirect(null);
        testFilter.setEquip(null);
        testFilter.filterTable();

        Assert.assertEquals(1, testFilter.getElements().size());
    }


    @Test
    public void testFilterWithIdDirect() {
        testFilter.setAirportDep(null);
        testFilter.setAirportDes(null);
        testFilter.setDirect("direct");
        testFilter.setEquip(null);
        testFilter.filterTable();

        Assert.assertEquals(1, testFilter.getElements().size());
    }


    @Test
    public void testFilterWithEquip() {
        ArrayList<String> equipmentList = new ArrayList<>(Arrays.asList("GPS"));

        testFilter.setAirportDep(null);
        testFilter.setAirportDes(null);
        testFilter.setDirect(null);
        testFilter.setEquip(equipmentList);
        testFilter.filterTable();

        Assert.assertEquals(3, testFilter.getElements().size());
    }


    @Test
    public void testFilterWithEverything() {
        ArrayList<String> equipmentList = new ArrayList<>(Arrays.asList("GPS"));

        testFilter.setAirportDep("srcAirport1");
        testFilter.setAirportDes("dstAirport1");
        testFilter.setDirect("not direct");
        testFilter.setEquip(equipmentList);
        testFilter.filterTable();

        Assert.assertEquals(1, testFilter.getElements().size());
    }


    @Test
    public void testFilterWithNoEntries() {
        testFilter.setAirportDep(null);
        testFilter.setAirportDes(null);
        testFilter.setDirect(null);
        testFilter.setEquip(null);
        testFilter.filterTable();

        Assert.assertEquals(3, testFilter.getElements().size());
    }
}

