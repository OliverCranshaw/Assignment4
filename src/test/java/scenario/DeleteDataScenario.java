package scenario;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.After;
import org.junit.Assert;
import io.cucumber.java.Before;
import seng202.team5.data.ConcreteDeleteData;
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

public class DeleteDataScenario {

    private ConcreteDeleteData concreteDeleteData;
    private ReadFile readFile;
    private AirlineService airlineService;
    private AirportService airportService;
    private FlightService flightService;
    private RouteService routeService;

    @Before("@Delete")
    public void setup() {
        String filename = "test.db";
        File dbFile = new File(filename);

        DBInitializer.createNewDatabase(filename);

        DBConnection.setDatabaseFile(dbFile);

        concreteDeleteData = new ConcreteDeleteData();
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

            if (result) {
                System.out.println("DB deleted.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    // Deleting airlines

    // Deleting an airline that exists

    @Given("there is an airline in the database with id {int}")
    public void thereIsAnAirlineInTheDatabase(int id) throws SQLException {
        airlineService.save("AirNZ", null, "AB", "ABC", "NZ123", "New Zealand", "Y");

        Assert.assertTrue(airlineService.getData(id).next());
    }

    @When("deleting the airline with id {int}")
    public void deletingTheAirlineWithId(int id) {
        boolean result = concreteDeleteData.deleteAirline(id);

        Assert.assertTrue(result);
    }

    @Then("the airline with id {int} no longer exists")
    public void theAirlineWithIdNoLongerExists(int id) throws SQLException {
        Assert.assertFalse(airlineService.getData(id).next());
    }

    // Deleting an airline that does not exist

    @Given("there is no airline with id {int}")
    public void thereIsNoAirlineWithId(int id) throws SQLException {
        Assert.assertFalse(airlineService.getData(id).next());
    }

    @When("deleting the non-existing airline with id {int}")
    public void deletingTheNonExistingAirlineWithId(int id) {
        boolean result = concreteDeleteData.deleteAirline(id);

        Assert.assertFalse(result);
    }

    @Then("an airline with id {int} still does not exist")
    public void anAirlineWithIdStillDoesNotExist(int id) throws SQLException {
        Assert.assertFalse(airlineService.getData(id).next());
    }


    // Deleting airports

    // Deleting an airport that exists

    @Given("there is an airport in the database with id {int}")
    public void thereIsAnAirportInTheDatabaseWithId(int id) throws SQLException {
        airportService.save("Christchurch Airport", "Christchurch", "New Zealand", "ABC",
                        "ABCD", 0, 0, 0, 0, "E", "Pacific/Auckland");

        Assert.assertTrue(airportService.getData(id).next());
    }

    @When("deleting the airport with id {int}")
    public void deletingTheAirportWithId(int id) {
        boolean result = concreteDeleteData.deleteAirport(id);

        Assert.assertTrue(result);
    }

    @Then("the airport with id {int} no longer exists")
    public void theAirportWithIdNoLongerExists(int id) throws SQLException {
        Assert.assertFalse(airportService.getData(id).next());
    }

    // Deleting an airport that does not exist

    @Given("there is no airport with id {int}")
    public void thereIsNoAirportWithId(int id) throws SQLException {
        Assert.assertFalse(airportService.getData(id).next());
    }

    @When("deleting the non-existing airport with id {int}")
    public void deletingTheNonExistingAirportWithId(int id) {
        boolean result = concreteDeleteData.deleteAirport(id);

        Assert.assertFalse(result);
    }

    @Then("an airport with id {int} still does not exist")
    public void anAirportWithIdStillDoesNotExist(int id) throws SQLException {
        Assert.assertFalse(airportService.getData(id).next());
    }


    // Deleting a flight entry

    // Deleting a flight entry that exists

    @Given("there is a flight entry in the database with id {int}")
    public void thereIsAFlightEntryInTheDatabaseWithId(int id) throws SQLException {
        flightService.save(1, "FIX", "NZ", 0, 0, 0);

        Assert.assertTrue(flightService.getData(id).next());
    }

    @When("deleting the flight entry with id {int}")
    public void deletingTheFlightEntryWithId(int id) {
        boolean result = concreteDeleteData.deleteFlightEntry(id);

        Assert.assertTrue(result);
    }

    @Then("the flight entry with id {int} no longer exists")
    public void theFlightEntryWithIdNoLongerExists(int id) throws SQLException {
        Assert.assertFalse(flightService.getData(id).next());
    }

    // Deleting a flight entry that does not exist

    @Given("there is no flight entry with id {int}")
    public void thereIsNoFlightEntryWithId(int id) throws SQLException {
        Assert.assertFalse(flightService.getData(id).next());
    }

    @When("deleting the non-existing flight entry with id {int}")
    public void deletingTheNonExistingFlightEntryWithId(int id) {
        boolean result = concreteDeleteData.deleteFlightEntry(id);

        Assert.assertFalse(result);
    }

    @Then("a flight entry with id {int} still does not exist")
    public void aFlightEntryWithIdStillDoesNotExist(int id) throws SQLException {
        Assert.assertFalse(flightService.getData(id).next());
    }


    // Deleting a flight

    // Deleting a flight that exists

    @Given("there is a flight in the database with flight id {int}")
    public void thereIsAFlightInTheDatabaseWithFlightId(int id) throws SQLException {
        // Adds the airports to the database that may be needed to create the flight
        readFile.readAirportData(new File("src/test/java/seng202/team5/data/testfiles/airports.txt"));
        readFile.readFlightData(new File("src/test/java/seng202/team5/data/testfiles/normal_flight.txt"));

        Assert.assertTrue(flightService.getData(id).next());
    }

    @When("deleting the flight with flight id {int}")
    public void deletingTheFlightWithFlightId(int id) {
        boolean result = concreteDeleteData.deleteFlight(id);

        Assert.assertTrue(result);
    }

    @Then("the flight with flight id {int} no longer exists")
    public void theFlightWithFlightIdNoLongerExists(int id) throws SQLException {
        Assert.assertFalse(flightService.getData(id).next());
    }

    // Deleting a flight that does not exist

    @Given("there is no flight with flight id {int}")
    public void thereIsNoFlightWithFlightId(int id) throws SQLException {
        Assert.assertFalse(flightService.getData(id).next());
    }

    @When("deleting the non-existing flight with flight id {int}")
    public void deletingTheNonExistingFlightWithFlightId(int id) {
        boolean result = concreteDeleteData.deleteFlight(id);

        Assert.assertFalse(result);
    }

    @Then("a flight with flight id {int} still does not exist")
    public void aFlightWithFlightIdStillDoesNotExist(int id) throws SQLException {
        Assert.assertFalse(flightService.getData(id).next());
    }


    // Deleting a route

    // Deleting a route that exists

    @Given("there is a route in the database with id {int}")
    public void thereIsARouteInTheDatabaseWithId(int id) throws SQLException {
        // Adds the airline and airports needed to be able to create the route
        airlineService.save("AirNZ", null, "AB", "ABC", "NZ123", "New Zealand", "Y");
        airportService.save("Christchurch Airport", "Christchurch", "New Zealand", "ABC", "ABCD", 0,
                0, 0, 0, "E", "Pacific/Auckland");
        airportService.save("Auckland Airport", "Auckland", "New Zealand", "DBS", "DBSA", 0,
                0, 0, 0, "E", "Pacific/Auckland");

        routeService.save("AB", "ABC", "DBS", "Y", 2, "GPS");

        Assert.assertTrue(routeService.getData(id).next());
    }

    @When("deleting the route with id {int}")
    public void deletingTheRouteWithId(int id) {
        boolean result = concreteDeleteData.deleteRoute(id);

        Assert.assertTrue(result);
    }

    @Then("the route with id {int} no longer exists")
    public void theRouteWithIdNoLongerExists(int id) throws SQLException {
        Assert.assertFalse(routeService.getData(id).next());
    }

    // Deleting a route that does not exist

    @Given("there is no route with id {int}")
    public void thereIsNoRouteWithId(int id) throws SQLException {
        Assert.assertFalse(routeService.getData(id).next());
    }

    @When("deleting the non-existing route with id {int}")
    public void deletingTheNonExistingRouteWithId(int id) {
        boolean result = concreteDeleteData.deleteRoute(id);

        Assert.assertFalse(result);
    }

    @Then("a route with id {int} still does not exist")
    public void aRouteWithIdStillDoesNotExist(int id) throws SQLException {
        Assert.assertFalse(routeService.getData(id).next());
    }
}
