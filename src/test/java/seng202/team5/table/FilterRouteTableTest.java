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

        // SQLite query used to populate database with the required data to run the routeService saveRoute() method
        String airportQuery = "INSERT INTO AIRPORT_DATA(airport_name, city, country, iata, icao, latitude, "
                + "longitude, altitude, timezone, dst, tz_database_timezone) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // SQLite query used to populate the database with the required data to run the RouteService saveRoute() method
        String airlineQuery = "INSERT INTO AIRLINE_DATA(airline_name, alias, iata, icao, "
                + "callsign, country, active) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Creating a query to retrieve all data from the database
        String retrieveQuery = "SELECT * FROM ROUTE_DATA";

        // Creating the statements to populate the database
        PreparedStatement testAirline = dbHandler.prepareStatement(airlineQuery);
        ArrayList<String> airline1 = new ArrayList<>(Arrays.asList("Airline", "", "airline1", "", "", "Country", "Y"));
        ArrayList<String> airline2 = new ArrayList<>(Arrays.asList("Airline", "", "airline2", "", "", "Country", "Y"));
        ArrayList<String> airline3 = new ArrayList<>(Arrays.asList("Airline", "", "airline3", "", "", "Country", "Y"));

        PreparedStatement testAirport = dbHandler.prepareStatement(airportQuery);
        ArrayList<Object> airport1 = new ArrayList<>(Arrays.asList("Airport", "City", "Country", "srcAirport1", "", 89, 123.2, 5000, 43, "JFI", "TZ"));
        ArrayList<Object> airport2 = new ArrayList<>(Arrays.asList("Airport", "City", "Country", "dstAirport1", "", 89, 123.2, 5000, 43, "JFI", "TZ"));
        ArrayList<Object> airport3 = new ArrayList<>(Arrays.asList("Airport", "City", "Country", "srcAirport2", "", 89, 123.2, 5000, 43, "JFI", "TZ"));
        ArrayList<Object> airport4 = new ArrayList<>(Arrays.asList("Airport", "City", "Country", "dstAirport2", "", 89, 123.2, 5000, 43, "JFI", "TZ"));
        ArrayList<Object> airport5 = new ArrayList<>(Arrays.asList("Airport", "City", "Country", "srcAirport3", "", 89, 123.2, 5000, 43, "JFI", "TZ"));
        ArrayList<Object> airport6 = new ArrayList<>(Arrays.asList("Airport", "City", "Country", "dstAirport3", "", 89, 123.2, 5000, 43, "JFI", "TZ"));

        PreparedStatement testData1 = dbHandler.prepareStatement(query);
        ArrayList<Object> testDataList1 = new ArrayList<>(Arrays.asList(1, "airline1", 1, "srcAirport1", 1, "dstAirport1", 2, "Y", 6, "GPS"));

        PreparedStatement testData2 = dbHandler.prepareStatement(query);
        ArrayList<Object> testDataList2 = new ArrayList<>(Arrays.asList(2, "airline2", 2, "srcAirport2", 3, "dstAirport2", 4, "Y", 7, "GPS"));

        PreparedStatement testData3 = dbHandler.prepareStatement(query);
        ArrayList<Object> testDataList3 = new ArrayList<>(Arrays.asList(3, "airline3", 3, "srcAirport3", 5, "dstAirport3", 6, "Y", 0, "GPS ADL"));

        // Filling in the blanks for the database populating statements
        for (int i=1; i < 8; i++) {
            testAirline.setObject(i, airline1.get(i-1));
        }
        testAirline.executeUpdate();
        for (int i=1; i < 8; i++) {
            testAirline.setObject(i, airline2.get(i-1));
        }
        testAirline.executeUpdate();
        for (int i=1; i < 8; i++) {
            testAirline.setObject(i, airline3.get(i-1));
        }
        testAirline.executeUpdate();

        for (int i=1; i < 12; i++) {
            testAirport.setObject(i, airport1.get(i-1));
        }
        testAirport.executeUpdate();
        for (int i=1; i < 12; i++) {
            testAirport.setObject(i, airport2.get(i-1));
        }
        testAirport.executeUpdate();
        for (int i=1; i < 12; i++) {
            testAirport.setObject(i, airport3.get(i-1));
        }
        testAirport.executeUpdate();
        for (int i=1; i < 12; i++) {
            testAirport.setObject(i, airport4.get(i-1));
        }
        testAirport.executeUpdate();
        for (int i=1; i < 12; i++) {
            testAirport.setObject(i, airport5.get(i-1));
        }
        testAirport.executeUpdate();
        for (int i=1; i < 12; i++) {
            testAirport.setObject(i, airport6.get(i-1));
        }
        testAirport.executeUpdate();

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

