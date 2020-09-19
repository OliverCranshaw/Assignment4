package seng202.team5.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.team5.database.DBConnection;
import seng202.team5.database.DBInitializer;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ReadFileTest {

    private DBConnection dbConnection;
    private DBInitializer dbInitializer;
    private ReadFile readFile;
    private ArrayList<Integer> expected;
    private File airlineFile;
    private File airportFile;
    private File flightFile;
    private File routeFile;
    private File airlines = new File("src/test/java/data/testfiles/airlines.txt");
    private File airports = new File("src/test/java/data/testfiles/airports.txt");
    private static Connection con;

    @Before
    public void setup() {
        String filename = "test.db";
        File dbFile = new File(filename);

        dbInitializer = new DBInitializer();

        DBInitializer.createNewDatabase(filename);

        dbConnection = new DBConnection();

        DBConnection.setDatabaseFile(dbFile);

        readFile = new ReadFile();
    }

    @After
    public void teardown() {
        try {
            File dbFile = new File("test.db");
            con = DBConnection.getConnection();
            con.close();

            dbFile.delete();

            System.out.println("DB deleted.");
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
        String blank_line = ",,,";

        ArrayList<String> expected_airline_entries = new ArrayList<>(Arrays.asList("1", "Private flight", "\\N", "-", "N/A", "", "", "Y"));
        ArrayList<String> expected_airport_entries = new ArrayList<>(Arrays.asList("9451", "Port Authority", "New York", "United States", "", "\\N", "40.756667", "-73.991111", "33", "-5", "A", "America/New_York"));
        ArrayList<String> expected_route_entries = new ArrayList<>(Arrays.asList("3H", "\\N", "YKG", "5481", "YVP", "154", "", "0", "DH8 DHT"));
        ArrayList<String> expected_blank_entries = new ArrayList<>(Arrays.asList("", "", "", ""));

        assertEquals(expected_airline_entries, readFile.getEntries(airline_line));
        assertEquals(expected_airport_entries, readFile.getEntries(airport_line));
        assertEquals(expected_route_entries, readFile.getEntries(route_line));
        assertEquals(expected_blank_entries, readFile.getEntries(blank_line));
    }


    @Test
    public void readAirlineTest() {
        airlineFile = new File("src/test/java/data/testfiles/normal_airline_with_id.txt");
        assertEquals(1, readFile.readAirlineData(airlineFile).get(0));

        airlineFile = new File("src/test/java/data/testfiles/normal_airline.txt");
        assertEquals(2, readFile.readAirlineData(airlineFile).get(0));

        airlineFile = new File("src/test/java/data/testfiles/abnormal_airline_with_id.txt");
        assertEquals(3, readFile.readAirlineData(airlineFile).get(0));
    }


    @Test
    public void readAirlineFailTest() {
        airlineFile = new File("src/test/java/data/testfiles/airline_too_few_entries.txt");
        assertEquals(-2, readFile.readAirlineData(airlineFile).get(0));

        airlineFile = new File("src/test/java/data/testfiles/airline_too_many_entries.txt");
        assertEquals(-3, readFile.readAirlineData(airlineFile).get(0));
    }


    @Test
    public void readAirlinesTest() {
        airlineFile = new File("src/test/java/data/testfiles/normal_airlines_multiple.txt");
        assertEquals(5, readFile.readAirlineData(airlineFile).get(0));
    }


    @Test
    public void readAirlinesFailTest() {
        airlineFile = new File("src/test/java/data/testfiles/abnormal_airlines_multiple.txt");
        assertEquals(3, readFile.readAirlineData(airlineFile).get(0));
    }


    @Test
    public void readAirportTest() {
        airportFile = new File("src/test/java/data/testfiles/normal_airport_with_id.txt");
        assertEquals(1, readFile.readAirportData(airportFile).get(0));

        airportFile = new File("src/test/java/data/testfiles/normal_airport.txt");
        assertEquals(2, readFile.readAirportData(airportFile).get(0));

        airportFile = new File("src/test/java/data/testfiles/abnormal_airport_with_id.txt");
        assertEquals(3, readFile.readAirportData(airportFile).get(0));
    }


    @Test
    public void readAirportFailTest() {
        airportFile = new File("src/test/java/data/testfiles/airport_too_few_entries.txt");
        assertEquals(-2, readFile.readAirportData(airportFile).get(0));

        airportFile = new File("src/test/java/data/testfiles/airport_too_many_entries.txt");
        assertEquals(-3, readFile.readAirportData(airportFile).get(0));
    }


    @Test
    public void readAirportsTest() {
        airportFile = new File("src/test/java/data/testfiles/normal_airports_multiple.txt");
        assertEquals(5, readFile.readAirportData(airportFile).get(0));
    }


    @Test
    public void readAirportsFailTest() {
        airportFile = new File("src/test/java/data/testfiles/abnormal_airports_multiple.txt");
        assertEquals(3, readFile.readAirportData(airportFile).get(0));
    }


    @Test
    public void readFlightEntryTest() {
        readFile.readAirlineData(airlines);
        readFile.readAirportData(airports);

        flightFile = new File("src/test/java/data/testfiles/normal_flight_entry.txt");
        expected = new ArrayList<>(Arrays.asList(1, 1));
        assertEquals(expected, readFile.readFlightData(flightFile).get(0));
    }


    @Test
    public void readFlightEntryFailTest() {
        flightFile = new File("src/test/java/data/testfiles/flight_entry_too_few_entries.txt");
        expected = new ArrayList<>(Arrays.asList(-1, -1));
        assertEquals(expected, readFile.readFlightData(flightFile).get(0));

        flightFile = new File("src/test/java/data/testfiles/flight_entry_too_many_entries.txt");
        assertEquals(expected, readFile.readFlightData(flightFile).get(0));
    }


    @Test
    public void readFlightTest() {
        readFile.readAirlineData(airlines);
        readFile.readAirportData(airports);

        flightFile = new File("src/test/java/data/testfiles/normal_flight.txt");
        expected = new ArrayList<>(Arrays.asList(1, 5));
        assertEquals(expected, readFile.readFlightData(flightFile).get(0));
    }


    @Test
    public void readFlightFailTest() {
        readFile.readAirlineData(airlines);
        readFile.readAirportData(airports);

        flightFile = new File("src/test/java/data/testfiles/abnormal_flight.txt");
        expected = new ArrayList<>(Arrays.asList(-1, -1));
        assertEquals(expected, readFile.readFlightData(flightFile).get(0));
    }


    @Test
    public void readRouteTest() {
        readFile.readAirlineData(airlines);
        readFile.readAirportData(airports);

        routeFile = new File("src/test/java/data/testfiles/normal_route_9_entries.txt");
        assertEquals(1, readFile.readRouteData(routeFile).get(0));

        routeFile = new File("src/test/java/data/testfiles/normal_route_6_entries.txt");
        assertEquals(2, readFile.readRouteData(routeFile).get(0));
    }


    @Test
    public void readRouteFailTest() {
        routeFile = new File("src/test/java/data/testfiles/route_too_few_entries.txt");
        assertEquals(-2, readFile.readRouteData(routeFile).get(0));

        routeFile = new File("src/test/java/data/testfiles/route_too_many_entries_less_than_9.txt");
        assertEquals(-3, readFile.readRouteData(routeFile).get(0));

        routeFile = new File("src/test/java/data/testfiles/route_too_many_entries.txt");
        assertEquals(-4, readFile.readRouteData(routeFile).get(0));
    }


    @Test
    public void readRoutesTest() {
        readFile.readAirlineData(airlines);
        readFile.readAirportData(airports);

        routeFile = new File("src/test/java/data/testfiles/normal_routes_multiple.txt");
        assertEquals(5, readFile.readRouteData(routeFile).get(0));
    }


    @Test
    public void readRoutesFailTest() {
        readFile.readAirlineData(airlines);
        readFile.readAirportData(airports);

        routeFile = new File("src/test/java/data/testfiles/abnormal_routes_multiple.txt");
        assertEquals(3, readFile.readRouteData(routeFile).get(0));
    }
}
