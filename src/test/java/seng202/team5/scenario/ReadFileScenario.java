package seng202.team5.scenario;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import seng202.team5.data.ReadFile;
import seng202.team5.database.DBConnection;
import seng202.team5.database.DBInitializer;
import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.FlightService;
import seng202.team5.service.RouteService;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class ReadFileScenario {

    private ReadFile readFile;
    private ArrayList<Integer> expected_ids;
    private Object ids;
    private int id;
    private final File airlines = new File("src/test/java/seng202/team5/data/testfiles/airlines.txt");
    private final File airports = new File("src/test/java/seng202/team5/data/testfiles/airports.txt");
    private File airlineFile;
    private File airportFile;
    private File flightFile;
    private File routeFile;
    AirlineService airlineService;
    AirportService airportService;
    FlightService flightService;
    RouteService routeService;

    @Before
    public void setup() {
        String filename = "test.db";
        File dbFile = new File(filename);

        DBInitializer.createNewDatabase(filename);

        DBConnection.setDatabaseFile(dbFile);

        readFile = new ReadFile();
        airlineService = new AirlineService();
        airportService = new AirportService();
        flightService = new FlightService();
        routeService = new RouteService();
    }

    @After
    public static void teardown() {
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


    // Reading Airline Data

    // Reading valid airline data

    @Given("a file {string} with valid airline data")
    public void validAirlineFile(String filename) {
        airlineFile = new File(filename);
    }

    @When("reading valid airline data from a file")
    public void readValidAirline() {
        id = (int)readFile.readAirlineData(airlineFile).get(0);
    }

    @Then("an airline is added with id 1")
    public void airlineAdded() {
        Assert.assertEquals(airlineService.getMaxID(), id);
    }

    // Reading invalid airline data with too few entries

    @Given("a file {string} with airline data with too few entries, i.e. less than 7 entries")
    public void invalidAirlineFileTooFewEntries(String filename) {
        airlineFile = new File(filename);
    }

    @When("reading airline data with less than 7 entries from a file")
    public void readAirlineFileTooFewEntries() {
        id = (int)readFile.readAirlineData(airlineFile).get(0);
    }

    // Reading invalid airline data with too many entries

    @Given("a file {string} with airline data with too many entries, i.e. more than 8 entries")
    public void invalidAirlineFileTooManyEntries(String filename) {
        airlineFile = new File(filename);
    }

    @When("reading airline data with more than 8 entries from a file")
    public void readAirlineFileTooManyEntries() {
        id = (int)readFile.readAirlineData(airlineFile).get(0);
    }

    @Then("the airline data is rejected with an error code {int}")
    public void rejectedAirline(int error) {
        Assert.assertEquals(error, id);
    }

    // Reading multiple airlines from a file

    @Given("a file {string} with multiple airlines")
    public void multipleAirlinesFile(String filename) {
        airlineFile = new File(filename);
    }

    @When("reading multiple instances of airline data from a file")
    public void readAirlines() {
        id = (int)readFile.readAirlineData(airlineFile).get(0);
    }

    @Then("each valid airline is added to the database with an incrementing id")
    public void validAirlinesAdded() {
        Assert.assertEquals(airlineService.getMaxID(), id);
    }


    // Reading Airport Data

    // Reading valid airport data

    @Given("a file {string} with valid airport data")
    public void validAirportFile(String filename) {
        airportFile = new File(filename);
    }

    @When("reading valid airport data from a file")
    public void readValidAirport() {
        id = (int)readFile.readAirportData(airportFile).get(0);
    }

    @Then("an airport is added with id 1")
    public void airportAdded() {
        Assert.assertEquals(airportService.getMaxID(), id);
    }

    // Reading invalid airport data with too few entries

    @Given("a file {string} with airport data with too few entries, i.e. less than 11 entries")
    public void invalidAirportFileTooFewEntries(String filename) {
        airportFile = new File(filename);
    }

    @When("reading airport data with less than 11 entries from a file")
    public void readAirportFileTooFewEntries() {
        id = (int)readFile.readAirportData(airportFile).get(0);
    }

    // Reading invalid airport data with too many entries

    @Given("a file {string} with airport data with too many entries, i.e. more than 12 entries")
    public void invalidAirportFileTooManyEntries(String filename) {
        airportFile = new File(filename);
    }

    @When("reading airport data with more than 12 entries from a file")
    public void readAirportFileTooManyEntries() {
        id = (int)readFile.readAirportData(airportFile).get(0);
    }

    @Then("the airport data is rejected with an error code {int}")
    public void rejectedAirport(int error) {
        Assert.assertEquals(error, id);
    }

    // Reading multiple airports from a file

    @Given("a file {string} with multiple airports")
    public void multipleAirportsFile(String filename) {
        airportFile = new File(filename);
    }

    @When("reading multiple instances of airport data from a file")
    public void readAirports() {
        id = (int)readFile.readAirportData(airportFile).get(0);
    }

    @Then("each valid airport is added to the database with an incrementing id")
    public void validAirportsAdded() {
        Assert.assertEquals(airportService.getMaxID(), id);
    }


    // Reading Flight Data

    // Reading valid flight entry data

    @Given("a file {string} with a single valid flight entry")
    public void validFlightEntryFile(String filename) {
        flightFile = new File(filename);
    }

    @When("reading valid flight data from a file")
    public void readValidFlightEntry() {
        ids = readFile.readFlightData(flightFile).get(0);
    }

    @Then("a flight entry is added with id 1 and flight id 1")
    public void addedFlightEntry() {
        expected_ids = new ArrayList<>(Arrays.asList(flightService.getNextFlightID() - 1, flightService.getMaxID()));
        Assert.assertEquals(expected_ids, ids);
    }

    // Reading a valid flight from a file

    @Given("a file {string} with multiple valid flight entries")
    public void validFlightFile(String filename) {
        readFile.readAirlineData(airlines);
        readFile.readAirportData(airports);

        flightFile = new File(filename);
    }

    @When("reading multiple instances of valid flight data from a file")
    public void readValidFlight() {
        ids = readFile.readFlightData(flightFile).get(0);
    }

    @Then("the flight entries are all added with flight id 1 and an incrementing unique id")
    public void addedFlight() {
        expected_ids = new ArrayList<>(Arrays.asList(flightService.getNextFlightID() - 1, flightService.getMaxID()));
        Assert.assertEquals(expected_ids, ids);
    }

    // Reading invalid flight entry data with the wrong number of entries

    @Given("a file {string} with a single flight entry with the wrong number of entries, i.e. less or more than 5")
    public void invalidFlightEntryFileWrongNumberOfEntries(String filename) {
        flightFile = new File(filename);
    }

    @When("reading flight data with the wrong number of entries from a file")
    public void readFlightEntryFileWrongNumberOfEntries() {
        ids = readFile.readFlightData(flightFile).get(0);
    }

    @Then("the flight data is rejected, and two error codes of {int} are returned")
    public void rejectedFlightEntryTooFewEntries(int error) {
        expected_ids = new ArrayList<>(Arrays.asList(error, error));
        Assert.assertEquals(expected_ids, ids);
    }

    // Reading a flight from a file with some invalid flight entries

    @Given("a file {string} with multiple flight data entries, some of them invalid")
    public void invalidFlightFile(String filename) {
        flightFile = new File(filename);
    }

    @When("reading multiple instances of flight data with some being invalid from a file")
    public void readInvalidFlightFile() {
        ids = readFile.readFlightData(flightFile).get(0);
    }

    @Then("an invalid flight entry is reached all previous ones are deleted, and two error codes of {int} are returned")
    public void rejectedFlight(int error) {
        expected_ids = new ArrayList<>(Arrays.asList(error, error));

        Assert.assertEquals(expected_ids, ids);
    }


    // Reading Route Data

    // Reading valid route data

    @Given("a file {string} with valid route data")
    public void validRouteFile(String filename) {
        routeFile = new File(filename);
        readFile.readAirlineData(airlines);
        readFile.readAirportData(airports);
    }

    @When("reading valid route data from a file")
    public void readValidRouteFile() {
        id = (int)readFile.readRouteData(routeFile).get(0);
    }

    @Then("a route is added with id 1")
    public void routeAdded() {
        Assert.assertEquals(routeService.getMaxID(), id);
    }

    // Reading invalid route data with less than 6 entries

    @Given("a file {string} with route data with too few entries, i.e. less than 6 entries")
    public void invalidRouteFileTooFewEntries(String filename) {
        routeFile = new File(filename);
    }

    @When("reading route data with less than 6 entries from a file")
    public void readRouteFileTooFewEntries() {
        id = (int)readFile.readRouteData(routeFile).get(0);
    }

    // Reading invalid route data with more than 6 entries but less than 9 entries

    @Given("a file {string} with route data with the wrong number of entries, i.e. more than 6 entries but has less than 9 entries")
    public void invalidRouteFileWrongNumberOfEntries(String filename) {
        routeFile = new File(filename);
    }

    @When("reading route data with more than 6 but less than 9 entries from a file")
    public void readRouteFileWrongNumberOfEntries() {
        id = (int)readFile.readRouteData(routeFile).get(0);
    }

    // Reading invalid route data with more than 9 entries

    @Given("a file {string} with route data with too many entries, i.e. more than 9 entries")
    public void invalidRouteFileTooManyEntries(String filename) {
        routeFile = new File(filename);
    }

    @When("reading route data with more than 9 entries from a file")
    public void readRouteFileTooManyEntries() {
        id = (int)readFile.readRouteData(routeFile).get(0);
    }

    @Then("the route data is rejected, and an error code of {int} is returned")
    public void rejectedRoute(int error) {
        Assert.assertEquals(error, id);
    }

    // Reading multiple routes from a file

    @Given("a file {string} with multiple routes")
    public void multipleRoutesFile(String filename) {
        routeFile = new File(filename);
        readFile.readAirlineData(airlines);
        readFile.readAirportData(airports);
    }

    @When("reading multiple instances of route data from a file")
    public void readRoutes() {
        id = (int)readFile.readRouteData(routeFile).get(0);
    }

    @Then("each valid route is added to the database with an incrementing id")
    public void validRoutesAdded() {
        Assert.assertEquals(routeService.getMaxID(), id);
    }

}
