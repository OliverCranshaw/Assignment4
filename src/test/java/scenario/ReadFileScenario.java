package scenario;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.After;
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
    private ArrayList<Integer> ids;
    private int id;
    private File airlines;
    private File airports;
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
        readFile = new ReadFile();
        airlineService = new AirlineService();
        airportService = new AirportService();
        flightService = new FlightService();
        routeService = new RouteService();

        airlines = new File("src/test/java/data/testfiles/airlines.txt");
        airports = new File("src/test/java/data/testfiles/airports.txt");
    }


    // Reading Airline Data

    // Reading valid airline data

    @Given("^a file \"([^\"]*)\" with valid airline data$")
    public void validAirlineFile(String filename) {
        airlineFile = new File(filename);
    }

    @When("reading valid airline data from a file")
    public void readValidAirline() {
        id = readFile.readAirlineData(airlineFile);
    }

    @Then("^an airline is added with id 1$")
    public void airlineAdded() {
        Assert.assertEquals(airlineService.getMaxID(), id);
    }

    // Reading invalid airline data with too few entries

    @Given("^a file \"([^\"]*)\" with airline data with too few entries, i.e. less than 7 entries$")
    public void invalidAirlineFileTooFewEntries(String filename) {
        airlineFile = new File(filename);
    }

    @When("reading airline data with less than 7 entries from a file")
    public void readAirlineFileTooFewEntries() {
        id = readFile.readAirlineData(airlineFile);
    }

    @Then("the airline data is rejected with an error code -2")
    public void rejectedAirlineTooFewEntries() {
        Assert.assertEquals(-2, id);
    }

    // Reading invalid airline data with too many entries

    @Given("^a file \"([^\"]*)\" with airline data with too many entries, i.e. more than 8 entries$")
    public void invalidAirlineFileTooManyEntries(String filename) {
        airlineFile = new File(filename);
    }

    @When("reading airline data with more than 8 entries from a file")
    public void readAirlineFileTooManyEntries() {
        id = readFile.readAirlineData(airlineFile);
    }

    @Then("the airline data is rejected with an error code -3")
    public void rejectedAirlineTooManyEntries() {
        Assert.assertEquals(-3, id);
    }

    // Reading multiple airlines from a file

    @Given("^a file \"([^\"]*)\" with multiple airlines$")
    public void multipleAirlinesFile(String filename) {
        airlineFile = new File(filename);
    }

    @When("reading multiple instances of airline data from a file")
    public void readAirlines() {
        id = readFile.readAirlineData(airlineFile);
    }

    @Then("each valid airline is added to the database with an incrementing id")
    public void validAirlinesAdded() {
        Assert.assertEquals(airlineService.getMaxID(), id);
    }


    // Reading Airport Data

    // Reading valid airport data

    @Given("^a file \"([^\"]*)\" with valid airport data$")
    public void validAirportFile(String filename) {
        airportFile = new File(filename);
    }

    @When("reading valid airport data from a file")
    public void readValidAirport() {
        id = readFile.readAirportData(airportFile);
    }

    @Then("an airport is added with id 1")
    public void airportAdded() {
        Assert.assertEquals(airportService.getMaxID(), id);
    }

    // Reading invalid airport data with too few entries

    @Given("^a file \"([^\"]*)\" with airport data with too few entries, i.e. less than 11 entries$")
    public void invalidAirportFileTooFewEntries(String filename) {
        airportFile = new File(filename);
    }

    @When("reading airport data with less than 11 entries from a file")
    public void readAirportFileTooFewEntries() {
        id = readFile.readAirportData(airportFile);
    }

    @Then("the airport data is rejected with an error code -2")
    public void rejectedAirportTooFewEntries() {
        Assert.assertEquals(-2, id);
    }

    // Reading invalid airport data with too many entries

    @Given("^a file \"([^\"]*)\" with airport data with too many entries, i.e. more than 12 entries$")
    public void invalidAirportFileTooManyEntries(String filename) {
        airportFile = new File(filename);
    }

    @When("reading airport data with more than 12 entries from a file")
    public void readAirportFileTooManyEntries() {
        id = readFile.readAirportData(airportFile);
    }

    @Then("the airport data is rejected with an error code -3")
    public void rejectedAirportTooManyEntries() {
        Assert.assertEquals(-3, id);
    }

    // Reading multiple airports from a file

    @Given("^a file \"([^\"]*)\" with multiple airports$")
    public void multipleAirportsFile(String filename) {
        airportFile = new File(filename);
    }

    @When("reading multiple instances of airport data from a file")
    public void readAirports() {
        id = readFile.readAirportData(airportFile);
    }

    @Then("each valid airport is added to the database with an incrementing id")
    public void validAirportsAdded() {
        Assert.assertEquals(airportService.getMaxID(), id);
    }


    // Reading Flight Data

    // Reading valid flight entry data

    @Given("^a file \"([^\"]*)\" with a single valid flight entry$")
    public void validFlightEntryFile(String filename) {
        flightFile = new File(filename);
    }

    @When("reading valid flight data from a file")
    public void readValidFlightEntry() {
        ids = readFile.readFlightData(flightFile);
    }

    @Then("a flight entry is added with id 1 and flight id 1")
    public void addedFlightEntry() {
        expected_ids = new ArrayList<>(Arrays.asList(flightService.getNextFlightID() - 1, flightService.getMaxID()));
        Assert.assertEquals(expected_ids, ids);
    }

    // Reading a valid flight from a file

    @Given("^a file \"([^\"]*)\" with multiple valid flight entries$")
    public void validFlightFile(String filename) {
        readFile.readAirlineData(airlines);
        readFile.readAirportData(airports);

        flightFile = new File(filename);
    }

    @When("reading multiple instances of valid flight data from a file")
    public void readValidFlight() {
        ids = readFile.readFlightData(flightFile);
    }

    @Then("the flight entries are all added with flight id 1 and an incrementing unique id")
    public void addedFlight() {
        expected_ids = new ArrayList<>(Arrays.asList(flightService.getNextFlightID() - 1, flightService.getMaxID()));
        Assert.assertEquals(expected_ids, ids);
    }

    // Reading invalid flight entry data with the wrong number of entries

    @Given("^a file \"([^\"]*)\" with a single flight entry with the wrong number of entries, i.e. less or more than 5$")
    public void invalidFlightEntryFileWrongNumberOfEntries(String filename) {
        flightFile = new File(filename);
    }

    @When("reading flight data with the wrong number of entries from a file")
    public void readFlightEntryFileWrongNumberOfEntries() {
        ids = readFile.readFlightData(flightFile);
    }

    @Then("the flight data is rejected, and two error codes of -1 are returned")
    public void rejectedFlightEntryTooFewEntries() {
        expected_ids = new ArrayList<>(Arrays.asList(-1, -1));
        Assert.assertEquals(expected_ids, ids);
    }

    // Reading a flight from a file with some invalid flight entries

    @Given("^a file \"([^\"]*)\" with multiple flight data entries, some of them invalid$")
    public void invalidFlightFile(String filename) {
        flightFile = new File(filename);
    }

    @When("reading multiple instances of flight data with some being invalid from a file")
    public void readInvalidFlightFile() {
        ids = readFile.readFlightData(flightFile);
    }

    @Then("an invalid flight entry is reached all previous ones are deleted, and two error codes of -1 are returned")
    public void rejectedFlight() {
        expected_ids = new ArrayList<>(Arrays.asList(-1, -1));

        Assert.assertEquals(expected_ids, ids);
    }


    // Reading Route Data

    // Reading valid route data

    @Given("^a file \"([^\"]*)\" with valid route data$")
    public void validRouteFile(String filename) {
        routeFile = new File(filename);
    }

    @When("reading valid route data from a file")
    public void readValidRouteFile() {
        id = readFile.readRouteData(routeFile);
    }

    @Then("a route is added with id 1")
    public void routeAdded() {
        Assert.assertEquals(routeService.getMaxID(), id);
    }

    // Reading invalid route data with less than 6 entries

    @Given("^a file \"([^\"]*)\" with route data with too few entries, i.e. less than 6 entries$")
    public void invalidRouteFileTooFewEntries(String filename) {
        routeFile = new File(filename);
    }

    @When("reading route data with less than 6 entries from a file")
    public void readRouteFileTooFewEntries() {
        id = readFile.readRouteData(routeFile);
    }

    @Then("the route data is rejected, and an error code of -2 is returned")
    public void rejectedRouteTooFewEntries() {
        Assert.assertEquals(-2, id);
    }

    // Reading invalid route data with more than 6 entries but less than 9 entries

    @Given("^a file \"([^\"]*)\" with route data with the wrong number of entries, i.e. more than 6 entries but has less than 9 entries$")
    public void invalidRouteFileWrongNumberOfEntries(String filename) {
        routeFile = new File(filename);
    }

    @When("reading route data with more than 6 but less than 9 entries from a file")
    public void readRouteFileWrongNumberOfEntries() {
        id = readFile.readRouteData(routeFile);
    }

    @Then("the route data is rejected, and an error code of -3 is returned")
    public void rejectedRouteWrongNumberOfEntries() {
        Assert.assertEquals(-3, id);
    }

    // Reading invalid route data with more than 9 entries

    @Given("^a file \"([^\"]*)\" with route data with too many entries, i.e. more than 9 entries$")
    public void invalidRouteFileTooManyEntries(String filename) {
        routeFile = new File(filename);
    }

    @When("reading route data with more than 9 entries from a file")
    public void readRouteFileTooManyEntries() {
        id = readFile.readRouteData(routeFile);
    }

    @Then("the route data is rejected, and an error code of -4 is returned")
    public void rejectedRouteTooManyEntries() {
        Assert.assertEquals(-4, id);
    }

    // Reading multiple routes from a file

    @Given("^a file \"([^\"]*)\" with multiple routes$")
    public void multipleRoutesFile(String filename) {
        routeFile = new File(filename);
    }

    @When("reading multiple instances of route data from a file")
    public void readRoutes() {
        id = readFile.readRouteData(routeFile);
    }

    @Then("each valid route is added to the database with an incrementing id")
    public void validRoutesAdded() {
        Assert.assertEquals(routeService.getMaxID(), id);
    }

}
