package scenario;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import seng202.team5.data.DataExporter;
import seng202.team5.data.ReadFile;
import seng202.team5.database.DBConnection;
import seng202.team5.database.DBInitializer;
import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.FlightService;
import seng202.team5.service.RouteService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class DataExporterScenario {

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

    @Before("@DataExporter")
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

    // Exporting airline data

    // Exporting airline data with airlines in the database

    @Given("there are airlines in the database")
    public void thereAreAirlinesInTheDatabase() throws SQLException {
        File airlineFile = new File("src/test/java/seng202/team5/data/testfiles/airlines.txt");
        readFile.readAirlineData(airlineFile);

        Assert.assertTrue(airlineService.getData(null, null, null).next());
    }

    @When("exporting airline data to file {string}")
    public void exportingAirlineDataToFile(String filename) throws SQLException {
        File file = new File(filename);

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

        dataExporter.exportAirlines(file, list);

        Assert.assertTrue(file.exists());
    }

    @Then("there is a file {string} containing all the airline data")
    public void thereIsAFileContainingAllTheAirlineData(String filename) throws Exception {
        File file = new File(filename);

        if (file.exists()) {
            fileReader = new FileReader(file);
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
            file.delete();

        }
    }

    // Exporting airline data with no airlines in the database

    @Given("there are no airlines in the database")
    public void thereAreNoAirlinesInTheDatabase() throws SQLException {
        Assert.assertFalse(airlineService.getData(null, null, null).next());
    }

    @Then("there is an empty file {string}")
    public void thereIsAnEmptyFile(String filename) throws Exception {
        File file = new File(filename);

        if (file.exists()) {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                assertEquals("", line);
            }
            bufferedReader.close();
            file.delete();
        }


    }


    // Exporting airport data

    // Exporting airport data with airports in the database

    @Given("there are airports in the database")
    public void thereAreAirportsInTheDatabase() throws SQLException {
        File airportFile = new File("src/test/java/seng202/team5/data/testfiles/airports.txt");
        readFile.readAirportData(airportFile);

        Assert.assertTrue(airportService.getData(null, null, null).next());
    }

    @When("exporting airport data to file {string}")
    public void exportingAirportDataToFile(String filename) throws SQLException {
        File file = new File(filename);

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

        dataExporter.exportAirports(file, list);

        Assert.assertTrue(file.exists());
    }

    @Then("there is a file {string} containing all the airport data")
    public void thereIsAFileContainingAllTheAirportData(String filename) throws Exception {
        File file = new File(filename);

        if (file.exists()) {
            fileReader = new FileReader(file);
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
            file.delete();

        }
    }

    // Exporting airport data with no airports in the database

    @Given("there are no airports in the database")
    public void thereAreNoAirportsInTheDatabase() throws SQLException {
        Assert.assertFalse(airportService.getData(null, null, null).next());
    }


    // Exporting all flight data/Exporting flight data

    // Multiple flights in the database

    @Given("there are multiple flights in the database")
    public void thereAreMultipleFlightsInTheDatabase() {
        // Adds the airports needed for the flights to be created
        File airportFile = new File("src/test/java/seng202/team5/data/testfiles/airports.txt");
        readFile.readAirportData(airportFile);

        File flightFile = new File("src/test/java/seng202/team5/data/testfiles/normal_flight.txt");
        readFile.readFlightData(flightFile);
        flightFile = new File("src/test/java/seng202/team5/data/testfiles/normal_flight_entry.txt");
        readFile.readFlightData(flightFile);

        Assert.assertNotNull(flightService.getData(1));
        Assert.assertNotNull(flightService.getData(2));
    }

    @When("exporting all flight data to file {string}")
    public void exportingAllFlightDataToFile(String filename) {
        File file = new File(filename);
        dataExporter.exportFlights(file);

        Assert.assertTrue(file.exists());
    }

    @Then("there is a file {string} containing all the flight data")
    public void thereIsAFileContainingAllTheFlightData(String filename) throws Exception {
        File file = new File(filename);

        if (file.exists()) {
            fileReader = new FileReader(file);
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
            file.delete();

        }
    }

    // One flight in the database

    @Given("there is one flight in the database")
    public void thereIsOneFlightInTheDatabase() throws SQLException {
        // Adds the airports needed to create the flight
        File airportFile = new File("src/test/java/seng202/team5/data/testfiles/airports.txt");
        readFile.readAirportData(airportFile);

        File flightFile = new File("src/test/java/seng202/team5/data/testfiles/normal_flight.txt");
        readFile.readFlightData(flightFile);

        Assert.assertTrue(flightService.getData(1).next());
        Assert.assertFalse(flightService.getData(2).next());
    }

    // No flights in the database

    @Given("there are no flights in the database")
    public void thereAreNoFlightsInTheDatabase() throws SQLException {
        Assert.assertFalse(flightService.getData(null, null).next());
    }

    @When("exporting flight data to file {string}")
    public void exportingFlightDataToFile(String filename) {
        File file = new File(filename);
        dataExporter.exportFlight(1, file);

        Assert.assertTrue(file.exists());
    }

    @Then("there is a file {string} containing the flight data")
    public void thereIsAFileContainingTheFlightData(String filename) throws Exception {
        File file = new File(filename);

        if (file.exists()) {
            fileReader = new FileReader(file);
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
            file.delete();

        }
    }


    // Exporting route data

    // Exporting route data with routes in the database

    @Given("there are routes in the database")
    public void thereAreRoutesInTheDatabase() throws SQLException {
        // Adds the airline and airports needed to create the routes
        File airlineFile = new File("src/test/java/seng202/team5/data/testfiles/airlines.txt");
        readFile.readAirlineData(airlineFile);

        File airportFile = new File("src/test/java/seng202/team5/data/testfiles/airports.txt");
        readFile.readAirportData(airportFile);

        File routeFile = new File("src/test/java/seng202/team5/data/testfiles/normal_routes_multiple.txt");
        readFile.readRouteData(routeFile);

        Assert.assertTrue(routeService.getData(null, null, -1, null).next());
    }

    @When("exporting route data to file {string}")
    public void exportingRouteDataToFile(String filename) throws SQLException {
        File file = new File(filename);

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

        dataExporter.exportRoutes(file, list);

        Assert.assertTrue(file.exists());
    }

    @Then("there is a file {string} containing all the route data")
    public void thereIsAFileContainingAllTheRouteData(String filename) throws Exception {
        File file = new File(filename);

        if (file.exists()) {
            fileReader = new FileReader(file);
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
            file.delete();
        }
    }

    // Exporting route data with no routes in the database

    @Given("there are no routes in the database")
    public void thereAreNoRoutesInTheDatabase() throws SQLException {
        Assert.assertFalse(routeService.getData(null, null, -1, null).next());
    }
}
