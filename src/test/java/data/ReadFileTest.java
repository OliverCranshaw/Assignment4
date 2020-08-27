package data;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import seng202.team5.data.ReadFile;
import seng202.team5.database.DBTableInitializer;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ReadFileTest {

    private ReadFile readFile = new ReadFile();
    private File airlineFile = new File("src/test/java/data/airlines.txt");
    private File airportFile = new File("src/test/java/data/airports.txt");
    private File flightFile = new File("src/test/java/data/flight.txt");
    private File routeFile = new File("src/test/java/data/routes.txt");
    private static Connection con;

    @BeforeClass
    public static void setup() {
        String url = "jdbc:sqlite:test.db";
        DBTableInitializer tableInitializer = new DBTableInitializer();

        try (Connection tmp = DriverManager.getConnection(url)) {
            con = tmp;
            if (con != null) {
                DatabaseMetaData meta = con.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("DB created.");
                tableInitializer.initializeTables(url);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @AfterClass
    public static void teardown() {
        File db = new File("test.db");
        try {
            if (con != null) {
                con.close();
                db.delete();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void removeQuotesTest() {
        String quotes_only = "\"\"";
        String empty = "";
        String no_quotes = "\\N";
        String quotes = "\"New Zealand\"";

        assertEquals("", readFile.removeQuotes(quotes_only));
        assertEquals("", readFile.removeQuotes(empty));
        assertEquals("\\N", readFile.removeQuotes(no_quotes));
        assertEquals("New Zealand", readFile.removeQuotes(quotes));
    }

    @Test
    public void getEntriesNormalTest() {
        String airline_line = "2,\"135 Airways\",\\N,\"\",\"GNL\",\"GENERAL\",\"United States\",\"N\"";
        String airport_line = "1,\"Goroka\",\"Goroka\",\"Papua New Guinea\",\"GKA\",\"AYGA\",-6.081689,145.391881,5282,10,\"U\",\"Pacific/Port_Moresby\"";
        String flight_line = "APT,NZCH,0,-43.4866,172.534";
        String route_line = "2B,410,AER,2965,KZN,2990,,0,CR2";

        ArrayList<String> expected_airline_entries = new ArrayList<>(Arrays.asList("2", "135 Airways", "\\N", "", "GNL", "GENERAL", "United States", "N"));
        ArrayList<String> expected_airport_entries = new ArrayList<>(Arrays.asList("1", "Goroka", "Goroka", "Papua New Guinea", "GKA", "AYGA", "-6.081689", "145.391881", "5282", "10", "U", "Pacific/Port_Moresby"));
        ArrayList<String> expected_flight_entries = new ArrayList<>(Arrays.asList("APT", "NZCH", "0", "-43.4866", "172.534"));
        ArrayList<String> expected_route_entries = new ArrayList<>(Arrays.asList("2B", "410", "AER", "2965", "KZN", "2990", "", "0", "CR2"));

        assertEquals(expected_airline_entries, readFile.getEntries(airline_line));
        assertEquals(expected_airport_entries, readFile.getEntries(airport_line));
        assertEquals(expected_flight_entries, readFile.getEntries(flight_line));
        assertEquals(expected_route_entries, readFile.getEntries(route_line));
    }

    @Test
    public void getEntriesAbnormalTest() {
        String airline_line = "1,\"Private flight\",\\N,\"-\",\"N/A\",\"\",\"\",\"Y\"";
        String airport_line = "9451,\"Port Authority\",\"New York\",\"United States\",\"\",\\N,40.756667,-73.991111,33,-5,\"A\",\"America/New_York\"";
        String route_line = "3H,\\N,YKG,5481,YVP,154,,0,DH8 DHT";

        ArrayList<String> expected_airline_entries = new ArrayList<>(Arrays.asList("1", "Private flight", "\\N", "-", "N/A", "", "", "Y"));
        ArrayList<String> expected_airport_entries = new ArrayList<>(Arrays.asList("9451", "Port Authority", "New York", "United States", "", "\\N", "40.756667", "-73.991111", "33", "-5", "A", "America/New_York"));
        ArrayList<String> expected_route_entries = new ArrayList<>(Arrays.asList("3H", "\\N", "YKG", "5481", "YVP", "154", "", "0", "DH8 DHT"));

        assertEquals(expected_airline_entries, readFile.getEntries(airline_line));
        assertEquals(expected_airport_entries, readFile.getEntries(airport_line));
        assertEquals(expected_route_entries, readFile.getEntries(route_line));
    }

    @Test
    public void readAirlineDataTest() {
        int expected = 6048;
        assertEquals(expected, readFile.readAirlineData(airlineFile));
    }

    @Test
    public void readAirportDataTest() {
        int expected = 8107;
        assertEquals(expected, readFile.readAirportData(airportFile));
    }

    @Test
    public void readFlightDataTest() {
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1, 31));
        assertEquals(expected, readFile.readFlightData(flightFile));
    }

    @Test
    public void readRouteDataTest() {
        int expected = 15045;
        assertEquals(expected, readFile.readRouteData(routeFile));
    }
}
