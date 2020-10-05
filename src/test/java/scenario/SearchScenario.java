package scenario;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import seng202.team5.database.DBConnection;
import seng202.team5.database.DBInitializer;
import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.FlightService;
import seng202.team5.service.RouteService;
import seng202.team5.table.Search;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchScenario {

    private AirlineService airlineService;
    private AirportService airportService;
    private FlightService flightService;
    private RouteService routeService;
    private Search search;
    private ArrayList<Object> data;
    private ResultSet searchResult;

    @Before("@Search")
    public void setUp() {

        String filename = "test.db";
        File dbFile = new File(filename);

        DBInitializer.createNewDatabase(filename);

        DBConnection.setDatabaseFile(dbFile);

        airlineService = new AirlineService();
        airportService = new AirportService();
        flightService = new FlightService();
        routeService = new RouteService();

        search = new Search();
        data = new ArrayList<>();

        airlineService.save("AirNZ", null, "AB", "ABC", "NZ123", "New Zealand", "Y");
        airportService.save("Christchurch Airport", "Christchurch", "New Zealand", "ABC", "ABCD", 0,
                0, 0, 0, "E", "Pacific/Auckland");
        airportService.save("Auckland Airport", "Auckland", "New Zealand", "DBS", "DBSA", 0,
                0, 0, 0, "E", "Pacific/Auckland");
        flightService.save(1, "FIX", "NZCH", 0, 0, 0);
        routeService.save("AB", "ABC", "DBS", "Y", 2, "GPS");
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

    //Scenario: Search Airline by name

    @Given("the airline name {string} is in the database")
    public void theAirlineNameIsInTheDatabase(String airlineName) throws SQLException {
        ResultSet result = airlineService.getData(airlineName, null, null);
        Assert.assertTrue(result.next());
    }

    @When("user searches for the airline name {string}")
    public void userSearchesForTheAirlineName(String airlineName) throws SQLException {
        data.add(airlineName);
        data.add(null);
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchAirline();
        Assert.assertTrue(searchResult.next());
    }

    @Then("the results from the search will include all airlines with the airline name {string}")
    public void theResultsFromTheSearchWillIncludeAllAirlinesWithTheAirlineName(String airlineName) throws SQLException {
        while (searchResult.next()) {
            Assert.assertEquals(airlineName, searchResult.getString(1));
        }
    }

    //Scenario: Search Airline by callsign

    @Given("the airline callsign {string} is in the database")
    public void theAirlineCallsignIsInTheDatabase(String airlineCallsign) throws SQLException {
        ResultSet result = airlineService.getData(null, null, airlineCallsign);
        Assert.assertTrue(result.next());
    }

    @When("user searches for the airline callsign {string}")
    public void userSearchesForTheAirlineCallsign(String airlineCallsign) throws SQLException {
        data.add(null);
        data.add(null);
        data.add(airlineCallsign);
        search.setSearchData(data);
        searchResult = search.searchAirline();
        Assert.assertTrue(searchResult.next());
    }

    @Then("the results from the search will include all airlines with the airline callsign {string}")
    public void theResultsFromTheSearchWillIncludeAllAirlinesWithTheAirlineCallsign(String airlineCallsign) throws SQLException {
        while (searchResult.next()) {
            Assert.assertEquals(airlineCallsign, searchResult.getString(6));
        }
    }

    //Scenario: Search Airline by country

    @Given("the airline country {string} is in the database")
    public void theAirlineCountryIsInTheDatabase(String airlineCountry) throws SQLException {
        ResultSet result = airlineService.getData(null, airlineCountry, null);
        Assert.assertTrue(result.next());
    }

    @When("user searches for the airline country {string}")
    public void userSearchesForTheAirlineCountry(String airlineCountry) throws SQLException {
        data.add(null);
        data.add(airlineCountry);
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchAirline();
        Assert.assertTrue(searchResult.next());
    }

    @Then("the results from the search will include all airlines with the airline country {string}")
    public void theResultsFromTheSearchWillIncludeAllAirlinesWithTheAirlineCountry(String airlineCountry) throws SQLException {
        while (searchResult.next()) {
            Assert.assertEquals(airlineCountry, searchResult.getString(7));
        }
    }

    //Scenario: Search Airport by name

    @Given("the airport name {string} is in the database")
    public void theAirportNameIsInTheDatabase(String airportName) throws SQLException {
        ResultSet result = airportService.getData(airportName, null, null);
        Assert.assertTrue(result.next());
    }

    @When("user searches for the airport name {string}")
    public void userSearchesForTheAirportName(String airportName) throws SQLException {
        data.add(airportName);
        data.add(null);
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchAirport();
        Assert.assertTrue(searchResult.next());
    }

    @Then("the results from the search will include all airports with the airport name {string}")
    public void theResultsFromTheSearchWillIncludeAllAirportsWithTheAirportName(String airportName) throws SQLException {
        while (searchResult.next()) {
            Assert.assertEquals(airportName, searchResult.getString(2));
        }
    }

    //Scenario: Search Airport by city

    @Given("the airport city {string} is in the database")
    public void theAirportCityIsInTheDatabase(String airportCity) throws SQLException {
        ResultSet result = airportService.getData(null, airportCity, null);
        Assert.assertTrue(result.next());
    }

    @When("user searches for the airport city {string}")
    public void userSearchesForTheAirportCity(String airportCity) throws SQLException {
        data.add(null);
        data.add(airportCity);
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchAirport();
        Assert.assertTrue(searchResult.next());
    }

    @Then("the results from the search will include all airports with the airport city {string}")
    public void theResultsFromTheSearchWillIncludeAllAirportsWithTheAirportCity(String airportCity) throws SQLException {
        while (searchResult.next()) {
            Assert.assertEquals(airportCity, searchResult.getString(3));
        }
    }

    //Scenario: Search Airport by country

    @Given("the airport country {string} is in the database")
    public void theAirportCountryIsInTheDatabase(String airportCountry) throws SQLException {
        ResultSet result = airportService.getData(null, null, airportCountry);
        Assert.assertTrue(result.next());
    }

    @When("user searches for the airport country {string}")
    public void userSearchesForTheAirportCountry(String airportCountry) throws SQLException {
        data.add(null);
        data.add(null);
        data.add(airportCountry);
        search.setSearchData(data);
        searchResult = search.searchAirport();
        Assert.assertTrue(searchResult.next());
    }

    @Then("the results from the search will include all airports with the airport country {string}")
    public void theResultsFromTheSearchWillIncludeAllAirportsWithTheAirportCountry(String airportCountry) throws SQLException {
        while (searchResult.next()) {
            Assert.assertEquals(airportCountry, searchResult.getString(4));
        }
    }

    //Scenario: Search Flight by location type

    @Given("the flight location type {string} is in the database")
    public void theFlightLocationTypeIsInTheDatabase(String flightLocationType) throws SQLException {
        ResultSet result = flightService.getData(flightLocationType, null);
        Assert.assertTrue(result.next());
    }

    @When("user searches for the flight location type {string}")
    public void userSearchesForTheFlightLocationType(String flightLocationType) throws SQLException {
        data.add(flightLocationType);
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchFlight();
        Assert.assertTrue(searchResult.next());
    }

    @Then("the results from the search will include all flights with the flight location type {string}")
    public void theResultsFromTheSearchWillIncludeAllAirportsWithTheFlightLocationType(String flightLocationType) throws SQLException {
        while (searchResult.next()) {
            Assert.assertEquals(flightLocationType, searchResult.getString(3));
        }
    }

    //Scenario: Search Flight by location

    @Given("the flight location {string} is in the database")
    public void theFlightLocationIsInTheDatabase(String flightLocation) throws SQLException {
        ResultSet result = flightService.getData(null, flightLocation);
        Assert.assertTrue(result.next());
    }

    @When("user searches for the flight location {string}")
    public void userSearchesForTheFlightLocation(String flightLocation) throws SQLException {
        data.add(null);
        data.add(flightLocation);
        search.setSearchData(data);
        searchResult = search.searchFlight();
        Assert.assertTrue(searchResult.next());
    }

    @Then("the results from the search will include all flights with the flight location {string}")
    public void theResultsFromTheSearchWillIncludeAllAirportsWithTheFlightLocation(String flightLocation) throws SQLException {
        while (searchResult.next()) {
            Assert.assertEquals(flightLocation, searchResult.getString(4));
        }
    }

    //Scenario: Search Route by source airport

    @Given("the route source airport {string} is in the database")
    public void theRouteSourceAirportIsInTheDatabase(String sourceAirport) throws SQLException {
        ResultSet result = routeService.getData(sourceAirport, null, -1, null);
        Assert.assertTrue(result.next());
    }

    @When("user searches for the route source airport {string}")
    public void userSearchesForTheRouteSourceAirport(String sourceAirport) throws SQLException {
        data.add(sourceAirport);
        data.add(null);
        data.add(-1);
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchRoute();
        Assert.assertTrue(searchResult.next());
    }

    @Then("the results from the search will include all routes with the route source airport {string}")
    public void theResultsFromTheSearchWillIncludeAllRoutesWithTheRouteSourceAirport(String sourceAirport) throws SQLException {
        while (searchResult.next()) {
            ResultSet data = airportService.getData(sourceAirport, null, null);
            String airportIataIciao = data.getString(5) == null ? data.getString(6) : data.getString(5);
            Assert.assertEquals(airportIataIciao, searchResult.getString(4));
        }
    }

    //Scenario: Search Route by destination airport

    @Given("the route destination airport {string} is in the database")
    public void theRouteDestinationAirportIsInTheDatabase(String destinationAirport) throws SQLException {
        ResultSet result = routeService.getData(null, destinationAirport, -1, null);
        Assert.assertTrue(result.next());
    }

    @When("user searches for the route destination airport {string}")
    public void userSearchesForTheRouteDestinationAirport(String destinationAirport) throws SQLException {
        data.add(null);
        data.add(destinationAirport);
        data.add(-1);
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchRoute();
        Assert.assertTrue(searchResult.next());
    }

    @Then("the results from the search will include all routes with the route destination airport {string}")
    public void theResultsFromTheSearchWillIncludeAllRoutesWithTheRouteDestinationAirport(String destinationAirport) throws SQLException {
        while (searchResult.next()) {
            ResultSet data = airportService.getData(destinationAirport, null, null);
            String airportIataIciao = data.getString(5) == null ? data.getString(6) : data.getString(5);
            Assert.assertEquals(airportIataIciao, searchResult.getString(6));
        }
    }

    //Scenario: Search Route by number stops

    @Given("the route number stops {int} is in the database")
    public void theRouteNumberStopsIsInTheDatabase(int numberStops) throws SQLException {
        ResultSet result = routeService.getData(null, null, numberStops, null);
        Assert.assertTrue(result.next());
    }

    @When("user searches for the route number stops {int}")
    public void userSearchesForTheRouteNumberStops(int numberStops) throws SQLException {
        data.add(null);
        data.add(null);
        data.add(numberStops);
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchRoute();
        Assert.assertTrue(searchResult.next());
    }

    @Then("the results from the search will include all routes with the route number stops {int}")
    public void theResultsFromTheSearchWillIncludeAllRoutesWithTheRouteNumberStops(int numberStops) throws SQLException {
        while (searchResult.next()) {
            Assert.assertEquals(numberStops, searchResult.getInt(9));
        }
    }

    //Scenario: Search Route by equipment

    @Given("the route equipment {string} is in the database")
    public void theRouteEquipmentIsInTheDatabase(String equipment) throws SQLException {
        ResultSet result = routeService.getData(null, null, -1, equipment);
        Assert.assertTrue(result.next());
    }

    @When("user searches for the route equipment {string}")
    public void userSearchesForTheRouteEquipment(String equipment) throws SQLException {
        data.add(null);
        data.add(null);
        data.add(-1);
        data.add(equipment);
        search.setSearchData(data);
        searchResult = search.searchRoute();
        Assert.assertTrue(searchResult.next());
    }

    @Then("the results from the search will include all routes with the route equipment {string}")
    public void theResultsFromTheSearchWillIncludeAllRoutesWithTheRouteEquipment(String equipment) throws SQLException {
        while (searchResult.next()) {
            Assert.assertEquals(equipment, searchResult.getString(10));
        }
    }

    //Scenario: Search Airline by name when no record in database has this name.
    @Given("the airline name {string} is not in the database")
    public void theAirlineNameIsNotInTheDatabase(String airlineName) throws SQLException {
        ResultSet result = airlineService.getData(airlineName, null, null);
        Assert.assertFalse(result.next());
    }

    @When("user searches for the airline name {string} which isn't present in the database")
    public void userSearchesForTheAirlineNameWhichIsnTPresentInTheDatabase(String airlineName) {
        data.add(airlineName);
        data.add(null);
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchAirline();
    }

    //Scenario: Search Airline by callsign when no record in database has this callsign.
    @Given("the airline callsign {string} is not in the database")
    public void theAirlineCallsignIsNotInTheDatabase(String airlineCallsign) throws SQLException {
        ResultSet result = airlineService.getData(null, null, airlineCallsign);
        Assert.assertFalse(result.next());

    }

    @When("user searches for the airline callsign {string} which isn't present in the database")
    public void userSearchesForTheAirlineCallsignWhichIsnTPresentInTheDatabase(String airlineCallsign) {
        data.add(null);
        data.add(null);
        data.add(airlineCallsign);
        search.setSearchData(data);
        searchResult = search.searchAirline();
    }

    //Scenario: Search Airline by country when no record in database has this country.
    @Given("the airline country {string} is not in the database")
    public void theAirlineCountryIsNotInTheDatabase(String airlineCountry) throws SQLException {
        ResultSet result = airlineService.getData(null, airlineCountry, null);
        Assert.assertFalse(result.next());
    }

    @When("user searches for the airline country {string} which isn't present in the database")
    public void userSearchesForTheAirlineCountryWhichIsnTPresentInTheDatabase(String airlineCountry) {
        data.add(null);
        data.add(airlineCountry);
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchAirline();
    }

    //Scenario: Search Airport by name when no record in database has this name.
    @Given("the airport name {string} is not in the database")
    public void theAirportNameIsNotInTheDatabase(String airportName) throws SQLException {
        ResultSet result = airportService.getData(airportName, null, null);
        Assert.assertFalse(result.next());
    }

    @When("user searches for the airport name {string} which isn't present in the database")
    public void userSearchesForTheAirportNameWhichIsnTPresentInTheDatabase(String airportName) {
        data.add(airportName);
        data.add(null);
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchAirport();
    }

    //Scenario: Search Airport by city when no record in database has this city.
    @Given("the airport city {string} is not in the database")
    public void theAirportCityIsNotInTheDatabase(String airportCity) throws SQLException {
        ResultSet result = airportService.getData(null, airportCity, null);
        Assert.assertFalse(result.next());
    }

    @When("user searches for the airport city {string} which isn't present in the database")
    public void userSearchesForTheAirportCityWhichIsnTPresentInTheDatabase(String airportCity) {
        data.add(null);
        data.add(airportCity);
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchAirport();
    }

    //Scenario: Search Airport by country when no record in database has this country.
    @Given("the airport country {string} is not in the database")
    public void theAirportCountryIsNotInTheDatabase(String airportCountry) throws SQLException {
        ResultSet result = airportService.getData(null, null, airportCountry);
        Assert.assertFalse(result.next());
    }

    @When("user searches for the airport country {string} which isn't present in the database")
    public void userSearchesForTheAirportCountryWhichIsnTPresentInTheDatabase(String airportCountry) {
        data.add(null);
        data.add(null);
        data.add(airportCountry);
        search.setSearchData(data);
        searchResult = search.searchAirport();
    }

    //Scenario: Search Flight by location type when no record in database has this airline.
    @Given("the flight location type {string} is not in the database")
    public void theFlightLocationTypeIsNotInTheDatabase(String flightLocationType) throws SQLException {
        ResultSet result = flightService.getData(flightLocationType, null);
        Assert.assertFalse(result.next());
    }

    @When("user searches for the flight location type {string} which isn't present in the database")
    public void userSearchesForTheFlightLocationTypeWhichIsnTPresentInTheDatabase(String flightLocationType) {
        data.add(flightLocationType);
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchFlight();
    }

    //Scenario: Search Flight by location when no record in database has this airport.
    @Given("the flight location {string} is not in the database")
    public void theFlightLocationIsNotInTheDatabase(String flightLocation) throws SQLException {
        ResultSet result = flightService.getData(null, flightLocation);
        Assert.assertFalse(result.next());
    }

    @When("user searches for the flight location {string} which isn't present in the database")
    public void userSearchesForTheFlightLocationWhichIsnTPresentInTheDatabase(String flightLocation) {
        data.add(null);
        data.add(flightLocation);
        search.setSearchData(data);
        searchResult = search.searchFlight();
    }

    //Scenario: Search Route by source airport when no record in database has this source airport.
    @Given("the route source airport {string} is not in the database")
    public void theRouteSourceAirportIsNotInTheDatabase(String sourceAirport) throws SQLException {
        ResultSet result = routeService.getData(sourceAirport, null, -1, null);
        Assert.assertFalse(result.next());
    }

    @When("user searches for the route source airport {string} which isn't present in the database")
    public void userSearchesForTheRouteSourceAirportWhichIsnTPresentInTheDatabase(String sourceAirport) {
        data.add(sourceAirport);
        data.add(null);
        data.add(-1);
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchRoute();
    }

    //Scenario: Search Route by destination airport when no record in database has this destination airport.
    @Given("the route destination airport {string} is not in the database")
    public void theRouteDestinationAirportIsNotInTheDatabase(String destinationAirport) throws SQLException {
        ResultSet result = routeService.getData(null, destinationAirport, -1, null);
        Assert.assertFalse(result.next());
    }

    @When("user searches for the route destination airport {string} which isn't present in the database")
    public void userSearchesForTheRouteDestinationAirportWhichIsnTPresentInTheDatabase(String destinationAirport) {
        data.add(null);
        data.add(destinationAirport);
        data.add(-1);
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchRoute();
    }

    //Scenario: Search Route by number of stops when no record in database has this number of stops.
    @Given("the route number stops {int} is not in the database")
    public void theRouteNumberStopsIsNotInTheDatabase(int numberStops) throws SQLException {
        ResultSet result = routeService.getData(null, null, numberStops, null);
        Assert.assertFalse(result.next());
    }

    @When("user searches for the route number stops {int} which isn't present in the database")
    public void userSearchesForTheRouteNumberStopsWhichIsnTPresentInTheDatabase(int numberStops) {
        data.add(null);
        data.add(null);
        data.add(numberStops);
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchRoute();
    }

    //Scenario: Search Route by equipment when no record in database has this equipment.
    @Given("the route equipment {string} is not in the database")
    public void theRouteEquipmentIsNotInTheDatabase(String equipment) throws SQLException {
        ResultSet result = routeService.getData(null, null, -1, equipment);
        Assert.assertFalse(result.next());
    }

    @When("user searches for the route equipment {string} which isn't present in the database")
    public void userSearchesForTheRouteEquipmentWhichIsnTPresentInTheDatabase(String equipment) {
        data.add(null);
        data.add(null);
        data.add(-1);
        data.add(equipment);
        search.setSearchData(data);
        searchResult = search.searchRoute();
    }

    //Is used for all searches with data not present.
    @Then("the result from the search will be empty")
    public void theResultFromTheSearchWillBeEmpty() throws SQLException {
        Assert.assertFalse(searchResult.next());
    }

    @Then("the result from the search will be null")
    public void theResultFromTheSearchWillBeNull() {
        Assert.assertNull(searchResult);
    }
}
