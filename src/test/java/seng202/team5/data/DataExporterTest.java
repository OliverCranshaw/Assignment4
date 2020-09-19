package seng202.team5.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.team5.accessor.AirlineAccessor;
import seng202.team5.accessor.AirportAccessor;
import seng202.team5.accessor.FlightAccessor;
import seng202.team5.accessor.RouteAccessor;
import seng202.team5.database.DBConnection;
import seng202.team5.database.DBInitializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DataExporterTest {

    private DataExporter dataExporter;
    private ReadFile readFile;
    private FileReader fileReader;
    private BufferedReader bufferedReader;
    private AirlineAccessor airlineAccessor;
    private AirportAccessor airportAccessor;
    private FlightAccessor flightAccessor;
    private RouteAccessor routeAccessor;
    private ResultSet resultSet;
    private String line;
    private String expectedLine;

    @Before
    public void setup() {
        String filename = "test.db";
        File dbFile = new File(filename);

        DBInitializer.createNewDatabase(filename);

        DBConnection.setDatabaseFile(dbFile);

        dataExporter = new DataExporter();
        readFile = new ReadFile();
        airlineAccessor = new AirlineAccessor();
        airportAccessor = new AirportAccessor();
        flightAccessor = new FlightAccessor();
        routeAccessor = new RouteAccessor();
    }

    @After
    public void teardown() {
        try {
            File dbFile = new File("test.db");
            Connection con = DBConnection.getConnection();
            con.close();

            boolean result = dbFile.delete();

            if (result) {
                System.out.println("DB deleted.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void exportAirlinesTest() {
        File airlineFile = new File("src/test/java/seng202/team5/data/testfiles/airlines.txt");
        readFile.readAirlineData(airlineFile);

        dataExporter.exportAirlines(new File ("src/test/java/seng202/team5/data/airlines.csv"));

        airlineFile = new File("src/test/java/seng202/team5/data/airlines.csv");

        assertTrue(airlineFile.exists());

        try {
            if (airlineFile.exists()) {
                fileReader = new FileReader(airlineFile);
                bufferedReader = new BufferedReader(fileReader);
                resultSet = airlineAccessor.getData(null, null, null);

                while ((line = bufferedReader.readLine()) != null && resultSet.next()) {
                    expectedLine = String.format("%d,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"",
                            resultSet.getInt("airline_id"), resultSet.getString("airline_name"),
                            resultSet.getString("alias"), resultSet.getString("iata"),
                            resultSet.getString("icao"), resultSet.getString("callsign"),
                            resultSet.getString("country"), resultSet.getString("active"));
                    expectedLine = expectedLine.replaceAll("null", "");

                    assertEquals(expectedLine, line);
                }
                bufferedReader.close();

                if (airlineFile.delete()) {
                    System.out.println("File deleted.");
                }
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void exportAirportsTest() {
        File airportFile = new File("src/test/java/seng202/team5/data/testfiles/airports.txt");
        readFile.readAirportData(airportFile);

        dataExporter.exportAirports(new File("src/test/java/seng202/team5/data/airports.csv"));

        airportFile = new File("src/test/java/seng202/team5/data/airports.csv");

        assertTrue(airportFile.exists());

        try {
            if (airportFile.exists()) {
                fileReader = new FileReader(airportFile);
                bufferedReader = new BufferedReader(fileReader);
                resultSet = airportAccessor.getData(null, null, null);

                while ((line = bufferedReader.readLine()) != null && resultSet.next()) {
                    expectedLine = String.format("%d,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",%f,%f,%d,%.1f,\"%s\",\"%s\"",
                            resultSet.getInt("airport_id"), resultSet.getString("airport_name"),
                            resultSet.getString("city"), resultSet.getString("country"),
                            resultSet.getString("iata"), resultSet.getString("icao"),
                            resultSet.getDouble("latitude"), resultSet.getDouble("longitude"),
                            resultSet.getInt("altitude"), resultSet.getFloat("timezone"),
                            resultSet.getString("dst"), resultSet.getString("tz_database_timezone"));
                    expectedLine = expectedLine.replaceAll("null", "");

                    assertEquals(expectedLine, line);
                }
                bufferedReader.close();

                if (airportFile.delete()) {
                    System.out.println("File deleted.");
                }
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void exportFlightTest() {
        File airlineFile = new File("src/test/java/seng202/team5/data/testfiles/airlines.txt");
        readFile.readAirlineData(airlineFile);
        File airportFile = new File("src/test/java/seng202/team5/data/testfiles/airports.txt");
        readFile.readAirportData(airportFile);

        File flightFile = new File("src/test/java/seng202/team5/data/testfiles/normal_flight.txt");
        readFile.readFlightData(flightFile);
        flightFile = new File("src/test/java/seng202/team5/data/testfiles/normal_flight_entry.txt");
        readFile.readFlightData(flightFile);

        dataExporter.exportFlight(1, new File ("src/test/java/seng202/team5/data/flight-NZCH-WSSS.csv"));

        flightFile = new File("src/test/java/seng202/team5/data/flight-NZCH-WSSS.csv");

        assertTrue(flightFile.exists());

        try {
            if (flightFile.exists()) {
                fileReader = new FileReader(flightFile);
                bufferedReader = new BufferedReader(fileReader);
                resultSet = flightAccessor.getData(1);

                while ((line = bufferedReader.readLine()) != null && resultSet.next()) {
                    expectedLine = String.format("%d,%d,%s,%s,%d,%f,%f",
                            resultSet.getInt("id"), resultSet.getInt("flight_id"),
                            resultSet.getString("location_type"), resultSet.getString("location"),
                            resultSet.getInt("altitude"), resultSet.getDouble("latitude"),
                            resultSet.getDouble("longitude"));

                    assertEquals(expectedLine, line);
                }
                bufferedReader.close();

                if (flightFile.delete()) {
                    System.out.println("File deleted.");
                }
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void exportFlightsTest() {
        File airlineFile = new File("src/test/java/seng202/team5/data/testfiles/airlines.txt");
        readFile.readAirlineData(airlineFile);
        File airportFile = new File("src/test/java/seng202/team5/data/testfiles/airports.txt");
        readFile.readAirportData(airportFile);

        File flightFile = new File("src/test/java/seng202/team5/data/testfiles/normal_flight.txt");
        readFile.readFlightData(flightFile);
        flightFile = new File("src/test/java/seng202/team5/data/testfiles/normal_flight_entry.txt");
        readFile.readFlightData(flightFile);

        dataExporter.exportFlights(new File("src/test/java/seng202/team5/data/flights.csv"));

        flightFile = new File("src/test/java/seng202/team5/data/flights.csv");

        assertTrue(flightFile.exists());

        try {
            if (flightFile.exists()) {
                fileReader = new FileReader(flightFile);
                bufferedReader = new BufferedReader(fileReader);
                resultSet = flightAccessor.getData(null, null);

                while ((line = bufferedReader.readLine()) != null && resultSet.next()) {
                    expectedLine = String.format("%d,%d,%s,%s,%d,%f,%f",
                            resultSet.getInt("id"), resultSet.getInt("flight_id"),
                            resultSet.getString("location_type"), resultSet.getString("location"),
                            resultSet.getInt("altitude"), resultSet.getDouble("latitude"),
                            resultSet.getDouble("longitude"));

                    assertEquals(expectedLine, line);
                }
                bufferedReader.close();

                if (flightFile.delete()) {
                    System.out.println("File deleted.");
                }
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void exportRoutesTest() {
        File airlineFile = new File("src/test/java/seng202/team5/data/testfiles/airlines.txt");
        readFile.readAirlineData(airlineFile);
        File airportFile = new File("src/test/java/seng202/team5/data/testfiles/airports.txt");
        readFile.readAirportData(airportFile);

        File routeFile = new File("src/test/java/seng202/team5/data/testfiles/normal_routes_multiple.txt");
        readFile.readRouteData(routeFile);

        dataExporter.exportRoutes(new File("src/test/java/seng202/team5/data/routes.csv"));

        routeFile = new File("src/test/java/seng202/team5/data/routes.csv");

        assertTrue(routeFile.exists());

        try {
            if (routeFile.exists()) {
                fileReader = new FileReader(routeFile);
                bufferedReader = new BufferedReader(fileReader);
                resultSet = routeAccessor.getData(null, null, -1, null);

                while ((line = bufferedReader.readLine()) != null && resultSet.next()) {
                    expectedLine = String.format("%d,%s,%d,%s,%d,%s,%d,%s,%d,%s",
                            resultSet.getInt("route_id"), resultSet.getString("airline"),
                            resultSet.getInt("airline_id"), resultSet.getString("source_airport"),
                            resultSet.getInt("source_airport_id"), resultSet.getString("destination_airport"),
                            resultSet.getInt("destination_airport_id"), resultSet.getString("codeshare"),
                            resultSet.getInt("stops"), resultSet.getString("equipment"));
                    expectedLine = expectedLine.replaceAll("null", "");

                    assertEquals(expectedLine, line);
                }
                bufferedReader.close();

                if (routeFile.delete()) {
                    System.out.println("File deleted.");
                }
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
