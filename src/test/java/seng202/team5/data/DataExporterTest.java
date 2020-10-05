package seng202.team5.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.team5.database.DBConnection;
import seng202.team5.database.DBInitializer;
import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.FlightService;
import seng202.team5.service.RouteService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DataExporterTest {

    private DataExporter dataExporter;
    private ReadFile readFile;
    private FileReader fileReader;
    private BufferedReader bufferedReader;
    private AirlineService airlineService;
    private AirportService airportService;
    private FlightService flightService;
    private RouteService routeService;
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
        airlineService = new AirlineService();
        airportService = new AirportService();
        flightService = new FlightService();
        routeService = new RouteService();
    }

    @After
    public void teardown() {
        try {
            File dbFile = new File("test.db");
            Connection con = DBConnection.getConnection();
            con.close();

            boolean result = dbFile.delete();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    @Test
    public void exportAirlinesTest() {
        // Adds airlines to the database
        File airlineFile = new File("src/test/java/seng202/team5/data/testfiles/airlines.txt");
        readFile.readAirlineData(airlineFile);

        try {
            ResultSet airlines = airlineService.getData(null, null, null);
            ResultSetMetaData md = airlines.getMetaData();
            // Gets the number of columns (ie the number of variables)
            int columns = md.getColumnCount();
            // Initializing an arraylist of arraylists to store the extracted data in
            ArrayList<ArrayList<Object>> list = new ArrayList<>();
            // Iterates through the result set
            while(airlines.next()) {
                // An arraylist of each instance of the data type
                ArrayList<Object> row = new ArrayList<>(columns);
                // Iterates through the data, storing it in the arraylist
                for (int i=1; i<=columns; ++i) {
                    row.add(airlines.getObject(i));
                }
                // Adds the extracted data to the overall arraylist of data
                list.add(row);
            }

            dataExporter.exportAirlines(new File ("src/test/java/seng202/team5/data/airlines.csv"), list);

            airlineFile = new File("src/test/java/seng202/team5/data/airlines.csv");

            assertTrue(airlineFile.exists());

            if (airlineFile.exists()) {
                fileReader = new FileReader(airlineFile);
                bufferedReader = new BufferedReader(fileReader);
                resultSet = airlineService.getData(null, null, null);

                // Compares the airline data returned from the database with the lines in the file
                // For this the airline data must be converted into a string in the correct format
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
                airlineFile.delete();
            }
        } catch (SQLException | IOException e) {
            System.err.println(e.getMessage());
        }
    }


    @Test
    public void exportAirportsTest() {
        // Adds airports to the database
        File airportFile = new File("src/test/java/seng202/team5/data/testfiles/airports.txt");
        readFile.readAirportData(airportFile);

        try {
            ResultSet airports = airportService.getData(null, null, null);
            ResultSetMetaData md = airports.getMetaData();
            // Gets the number of columns (ie the number of variables)
            int columns = md.getColumnCount();
            // Initializing an arraylist of arraylists to store the extracted data in
            ArrayList<ArrayList<Object>> list = new ArrayList<>();
            // Iterates through the result set
            while(airports.next()) {
                // An arraylist of each instance of the data type
                ArrayList<Object> row = new ArrayList<>(columns);
                // Iterates through the data, storing it in the arraylist
                for (int i=1; i<=columns; ++i) {
                    row.add(airports.getObject(i));
                }
                // Adds the extracted data to the overall arraylist of data
                list.add(row);
            }

            dataExporter.exportAirports(new File("src/test/java/seng202/team5/data/airports.csv"), list);

            airportFile = new File("src/test/java/seng202/team5/data/airports.csv");

            assertTrue(airportFile.exists());

            if (airportFile.exists()) {
                fileReader = new FileReader(airportFile);
                bufferedReader = new BufferedReader(fileReader);
                resultSet = airportService.getData(null, null, null);

                // Compares the airport data returned from the database with the lines in the file
                // For this the airport data must be converted into a string in the correct format
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
                airportFile.delete();
            }
        } catch (SQLException | IOException e) {
            System.err.println(e.getMessage());
        }
    }


    @Test
    public void exportFlightTest() {
        // Adds the airports necessary to add the flights
        File airportFile = new File("src/test/java/seng202/team5/data/testfiles/airports.txt");
        readFile.readAirportData(airportFile);

        // Adds flights
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
                resultSet = flightService.getData(1);

                // Compares the flight data returned from the database with the lines in the file
                // For this the flight data must be converted into a string in the correct format
                while ((line = bufferedReader.readLine()) != null && resultSet.next()) {
                    expectedLine = String.format("%d,%d,%s,%s,%d,%f,%f",
                            resultSet.getInt("id"), resultSet.getInt("flight_id"),
                            resultSet.getString("location_type"), resultSet.getString("location"),
                            resultSet.getInt("altitude"), resultSet.getDouble("latitude"),
                            resultSet.getDouble("longitude"));

                    assertEquals(expectedLine, line);
                }
                bufferedReader.close();
                flightFile.delete();
            }
        } catch (SQLException | IOException e) {
            System.err.println(e.getMessage());
        }
    }


    @Test
    public void exportAllFlightsTest() {
        // Adds the airports necessary to add the flights
        File airportFile = new File("src/test/java/seng202/team5/data/testfiles/airports.txt");
        readFile.readAirportData(airportFile);

        // Adds flights
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
                resultSet = flightService.getData(null, null);

                // Compares the flight data returned from the database with the lines in the file
                // For this the flight data must be converted into a string in the correct format
                while ((line = bufferedReader.readLine()) != null && resultSet.next()) {
                    expectedLine = String.format("%d,%d,%s,%s,%d,%f,%f",
                            resultSet.getInt("id"), resultSet.getInt("flight_id"),
                            resultSet.getString("location_type"), resultSet.getString("location"),
                            resultSet.getInt("altitude"), resultSet.getDouble("latitude"),
                            resultSet.getDouble("longitude"));

                    assertEquals(expectedLine, line);
                }
                bufferedReader.close();
                flightFile.delete();
            }
        } catch (SQLException | IOException e) {
            System.err.println(e.getMessage());
        }
    }


    @Test
    public void exportFlightsTest() {
        // Adds the airports necessary to add the flights
        File airportFile = new File("src/test/java/seng202/team5/data/testfiles/airports.txt");
        readFile.readAirportData(airportFile);

        // Adds flights
        File flightFile = new File("src/test/java/seng202/team5/data/testfiles/normal_flight.txt");
        readFile.readFlightData(flightFile);
        flightFile = new File("src/test/java/seng202/team5/data/testfiles/normal_flight_entry.txt");
        readFile.readFlightData(flightFile);

        try {
            ResultSet flights = flightService.getData(null, null);
            ResultSetMetaData md = flights.getMetaData();
            // Gets the number of columns (ie the number of variables)
            int columns = md.getColumnCount();
            // Initializing an arraylist of arraylists to store the extracted data in
            ArrayList<ArrayList<Object>> list = new ArrayList<>();
            int entry = 1;
            // Iterates through the result set
            while(flights.next()) {
                // Only adds the row when it's the first entry of the flight as that's how it is displayed in the table
                if (entry == 1 || entry == 6) {
                    // An arraylist of each instance of the data type
                    ArrayList<Object> row = new ArrayList<>(columns);
                    // Iterates through the data, storing it in the arraylist
                    for (int i = 1; i <= columns; ++i) {
                        row.add(flights.getObject(i));
                    }
                    // Adds the extracted data to the overall arraylist of data
                    list.add(row);
                }
                entry++;
            }

            dataExporter.exportFlights(new File("src/test/java/seng202/team5/data/flights.csv"), list);

            flightFile = new File("src/test/java/seng202/team5/data/flights.csv");

            assertTrue(flightFile.exists());

            if (flightFile.exists()) {
                fileReader = new FileReader(flightFile);
                bufferedReader = new BufferedReader(fileReader);
                resultSet = flightService.getData(null, null);

                // Compares the flight data returned from the database with the lines in the file
                // For this the flight data must be converted into a string in the correct format
                while ((line = bufferedReader.readLine()) != null && resultSet.next()) {
                    expectedLine = String.format("%d,%d,%s,%s,%d,%f,%f",
                            resultSet.getInt("id"), resultSet.getInt("flight_id"),
                            resultSet.getString("location_type"), resultSet.getString("location"),
                            resultSet.getInt("altitude"), resultSet.getDouble("latitude"),
                            resultSet.getDouble("longitude"));

                    assertEquals(expectedLine, line);
                }
                bufferedReader.close();

                flightFile.delete();
            }
        } catch (SQLException | IOException e) {
            System.err.println(e.getMessage());
        }
    }


    @Test
    public void exportRoutesTest() {
        // Adds the airlines and airports necessary to add the routes
        File airlineFile = new File("src/test/java/seng202/team5/data/testfiles/airlines.txt");
        readFile.readAirlineData(airlineFile);
        File airportFile = new File("src/test/java/seng202/team5/data/testfiles/airports.txt");
        readFile.readAirportData(airportFile);

        // Adds routes
        File routeFile = new File("src/test/java/seng202/team5/data/testfiles/normal_routes_multiple.txt");
        readFile.readRouteData(routeFile);

        try {
            ResultSet routes = routeService.getData(null, null, -1, null);
            ResultSetMetaData md = routes.getMetaData();
            // Gets the number of columns (ie the number of variables)
            int columns = md.getColumnCount();
            // Initializing an arraylist of arraylists to store the extracted data in
            ArrayList<ArrayList<Object>> list = new ArrayList<>();
            // Iterates through the result set
            while(routes.next()) {
                // An arraylist of each instance of the data type
                ArrayList<Object> row = new ArrayList<>(columns);
                // Iterates through the data, storing it in the arraylist
                for (int i=1; i<=columns; ++i) {
                    row.add(routes.getObject(i));
                }
                // Adds the extracted data to the overall arraylist of data
                list.add(row);
            }

            dataExporter.exportRoutes(new File("src/test/java/seng202/team5/data/routes.csv"), list);

            routeFile = new File("src/test/java/seng202/team5/data/routes.csv");

            assertTrue(routeFile.exists());

            if (routeFile.exists()) {
                fileReader = new FileReader(routeFile);
                bufferedReader = new BufferedReader(fileReader);
                resultSet = routeService.getData(null, null, -1, null);

                // Compares the route data returned from the database with the lines in the file
                // For this the route data must be converted into a string in the correct format
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

                routeFile.delete();
            }
        } catch (SQLException | IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
