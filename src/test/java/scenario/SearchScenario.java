package scenario;

import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import seng202.team5.Search;
import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.FlightService;
import seng202.team5.service.RouteService;

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

    @Before
    public void setUp() {
        airlineService = new AirlineService();
        airportService = new AirportService();
        flightService = new FlightService();
        routeService = new RouteService();

        search = new Search();
        data = new ArrayList<>();

        airlineService.saveAirline("AirNZ", null, "AB", "ABC", "NZ123", "New Zealand", "Y");
        airportService.saveAirport("Christchurch Airport", "Christchurch", "New Zealand", "ABC", "ABCD", 0,
                0, 0, 0, "E", "Pacific/Auckland");
        airportService.saveAirport("Auckland Airport", "Auckland", "New Zealand", "DBS", "DBSA", 0,
                0, 0, 0, "E", "Pacific/Auckland");
        flightService.saveFlight(1, "AB", "ABC", 0, 0, 0);
        routeService.saveRoute("AB", "ABC", "DBS", "Y", 2, "GPS");
    }

    //Scenario: Search Airline by name

    @Given("^the airline name \"([^\"]*)\" is in the database$")
    public void theAirlineNameIsInTheDatabase(String airlineName) throws SQLException {
        ResultSet result = airlineService.getAirlines(airlineName, null, null);
        Assert.assertTrue(result.next());
    }

    @When("^user searches for the airline name \"([^\"]*)\"$")
    public void userSearchesForTheAirlineName(String airlineName) throws SQLException {
        data.add(airlineName);
        data.add(null);
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchAirline();
        Assert.assertTrue(searchResult.next());
    }

    @Then("^the results from the search will include all airlines with the airline name \"([^\"]*)\"$")
    public void theResultsFromTheSearchWillIncludeAllAirlinesWithTheAirlineName(String airlineName) throws SQLException {
        while (searchResult.next()) {
            Assert.assertEquals(airlineName, searchResult.getString(1));
        }
    }

    //Scenario: Search Airline by callsign

    @Given("^the airline callsign \"([^\"]*)\" is in the database$")
    public void theAirlineCallsignIsInTheDatabase(String airlineCallsign) throws SQLException {
        ResultSet result = airlineService.getAirlines(null, null, airlineCallsign);
        Assert.assertTrue(result.next());
    }

    @When("^user searches for the airline callsign \"([^\"]*)\"$")
    public void userSearchesForTheAirlineCallsign(String airlineCallsign) throws SQLException {
        data.add(null);
        data.add(null);
        data.add(airlineCallsign);
        search.setSearchData(data);
        searchResult = search.searchAirline();
        Assert.assertTrue(searchResult.next());
    }

    @Then("^the results from the search will include all airlines with the airline callsign \"([^\"]*)\"$")
    public void theResultsFromTheSearchWillIncludeAllAirlinesWithTheAirlineCallsign(String airlineCallsign) throws SQLException {
        while (searchResult.next()) {
            Assert.assertEquals(airlineCallsign, searchResult.getString(6));
        }
    }

    //Scenario: Search Airline by country

    @Given("^the airline country \"([^\"]*)\" is in the database$")
    public void theAirlineCountryIsInTheDatabase(String airlineCountry) throws SQLException {
        ResultSet result = airlineService.getAirlines(null, airlineCountry, null);
        Assert.assertTrue(result.next());
    }

    @When("^user searches for the airline country \"([^\"]*)\"$")
    public void userSearchesForTheAirlineCountry(String airlineCountry) throws SQLException {
        data.add(null);
        data.add(airlineCountry);
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchAirline();
        Assert.assertTrue(searchResult.next());
    }

    @Then("^the results from the search will include all airlines with the airline country \"([^\"]*)\"$")
    public void theResultsFromTheSearchWillIncludeAllAirlinesWithTheAirlineCountry(String airlineCountry) throws SQLException {
        while (searchResult.next()) {
            Assert.assertEquals(airlineCountry, searchResult.getString(7));
        }
    }

    //Scenario: Search Airport by name

    @Given("^the airport name \"([^\"]*)\" is in the database$")
    public void theAirportNameIsInTheDatabase(String airportName) throws SQLException {
        ResultSet result = airportService.getAirports(airportName, null, null);
        Assert.assertTrue(result.next());
    }

    @When("^user searches for the airport name \"([^\"]*)\"$")
    public void userSearchesForTheAirportName(String airportName) throws SQLException {
        data.add(airportName);
        data.add(null);
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchAirport();
        Assert.assertTrue(searchResult.next());
    }

    @Then("^the results from the search will include all airports with the airport name \"([^\"]*)\"$")
    public void theResultsFromTheSearchWillIncludeAllAirportsWithTheAirportName(String airportName) throws SQLException {
        while (searchResult.next()) {
            Assert.assertEquals(airportName, searchResult.getString(2));
        }
    }

    //Scenario: Search Airport by city

    @Given("^the airport city \"([^\"]*)\" is in the database$")
    public void theAirportCityIsInTheDatabase(String airportCity) throws SQLException {
        ResultSet result = airportService.getAirports(null, airportCity, null);
        Assert.assertTrue(result.next());
    }

    @When("^user searches for the airport city \"([^\"]*)\"$")
    public void userSearchesForTheAirportCity(String airportCity) throws SQLException {
        data.add(null);
        data.add(airportCity);
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchAirport();
        Assert.assertTrue(searchResult.next());
    }

    @Then("^the results from the search will include all airports with the airport city \"([^\"]*)\"$")
    public void theResultsFromTheSearchWillIncludeAllAirportsWithTheAirportCity(String airportCity) throws SQLException {
        while (searchResult.next()) {
            Assert.assertEquals(airportCity, searchResult.getString(3));
        }
    }

    //Scenario: Search Airport by country

    @Given("^the airport country \"([^\"]*)\" is in the database$")
    public void theAirportCountryIsInTheDatabase(String airportCountry) throws SQLException {
        ResultSet result = airportService.getAirports(null, null, airportCountry);
        Assert.assertTrue(result.next());
    }

    @When("^user searches for the airport country \"([^\"]*)\"$")
    public void userSearchesForTheAirportCountry(String airportCountry) throws SQLException {
        data.add(null);
        data.add(null);
        data.add(airportCountry);
        search.setSearchData(data);
        searchResult = search.searchAirport();
        Assert.assertTrue(searchResult.next());
    }

    @Then("^the results from the search will include all airports with the airport country \"([^\"]*)\"$")
    public void theResultsFromTheSearchWillIncludeAllAirportsWithTheAirportCountry(String airportCountry) throws SQLException {
        while (searchResult.next()) {
            Assert.assertEquals(airportCountry, searchResult.getString(4));
        }
    }

    //Scenario: Search Flight by airline

    @Given("^the flight airline \"([^\"]*)\" is in the database$")
    public void theFlightAirlineIsInTheDatabase(String flightAirline) throws SQLException {
        ResultSet result = flightService.getFlights(flightAirline, null);
        Assert.assertTrue(result.next());
    }

    @When("^user searches for the flight airline \"([^\"]*)\"$")
    public void userSearchesForTheFlightAirline(String flightAirline) throws SQLException {
        data.add(flightAirline);
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchFlight();
        Assert.assertTrue(searchResult.next());
    }

    @Then("^the results from the search will include all flights with the flight airline \"([^\"]*)\"$")
    public void theResultsFromTheSearchWillIncludeAllAirportsWithTheFlightAirline(String flightAirline) throws SQLException {
        while (searchResult.next()) {
            ResultSet data = airlineService.getAirlines(flightAirline, null, null);
            String airlineIataIciao = data.getString(3) == null ? data.getString(4) : data.getString(3);
            Assert.assertEquals(airlineIataIciao, searchResult.getString(3));
        }
    }

    //Scenario: Search Flight by airport

    @Given("^the flight airport \"([^\"]*)\" is in the database$")
    public void theFlightAirportIsInTheDatabase(String flightAirport) throws SQLException {
        ResultSet result = flightService.getFlights(null, flightAirport);
        Assert.assertTrue(result.next());
    }

    @When("^user searches for the flight airport \"([^\"]*)\"$")
    public void userSearchesForTheFlightAirport(String flightAirport) throws SQLException {
        data.add(null);
        data.add(flightAirport);
        search.setSearchData(data);
        searchResult = search.searchFlight();
        Assert.assertTrue(searchResult.next());
    }

    @Then("^the results from the search will include all flights with the flight airport \"([^\"]*)\"$")
    public void theResultsFromTheSearchWillIncludeAllAirportsWithTheFlightAirport(String flightAirport) throws SQLException {
        while (searchResult.next()) {
            ResultSet data = airportService.getAirports(flightAirport, null, null);
            String airportIataIciao = data.getString(5) == null ? data.getString(6) : data.getString(5);
            Assert.assertEquals(airportIataIciao, searchResult.getString(4));
        }
    }

    //Scenario: Search Route by source airport

    @Given("^the route source airport \"([^\"]*)\" is in the database$")
    public void theRouteSourceAirportIsInTheDatabase(String sourceAirport) throws SQLException {
        ResultSet result = routeService.getRoutes(sourceAirport, null, -1, null);
        Assert.assertTrue(result.next());
    }

    @When("^user searches for the route source airport \"([^\"]*)\"$")
    public void userSearchesForTheRouteSourceAirport(String sourceAirport) throws SQLException {
        data.add(sourceAirport);
        data.add(null);
        data.add(-1);
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchRoute();
        Assert.assertTrue(searchResult.next());
    }

    @Then("^the results from the search will include all routes with the route source airport \"([^\"]*)\"$")
    public void theResultsFromTheSearchWillIncludeAllRoutesWithTheRouteSourceAirport(String sourceAirport) throws SQLException {
        while (searchResult.next()) {
            ResultSet data = airportService.getAirports(sourceAirport, null, null);
            String airportIataIciao = data.getString(5) == null ? data.getString(6) : data.getString(5);
            Assert.assertEquals(airportIataIciao, searchResult.getString(4));
        }
    }

    //Scenario: Search Route by destination airport

    @Given("^the route destination airport \"([^\"]*)\" is in the database$")
    public void theRouteDestinationAirportIsInTheDatabase(String destinationAirport) throws SQLException {
        ResultSet result = routeService.getRoutes(null, destinationAirport, -1, null);
        Assert.assertTrue(result.next());
    }

    @When("^user searches for the route destination airport \"([^\"]*)\"$")
    public void userSearchesForTheRouteDestinationAirport(String destinationAirport) throws SQLException {
        data.add(null);
        data.add(destinationAirport);
        data.add(-1);
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchRoute();
        Assert.assertTrue(searchResult.next());
    }

    @Then("^the results from the search will include all routes with the route destination airport \"([^\"]*)\"$")
    public void theResultsFromTheSearchWillIncludeAllRoutesWithTheRouteDestinationAirport(String destinationAirport) throws SQLException {
        while (searchResult.next()) {
            ResultSet data = airportService.getAirports(destinationAirport, null, null);
            String airportIataIciao = data.getString(5) == null ? data.getString(6) : data.getString(5);
            Assert.assertEquals(airportIataIciao, searchResult.getString(6));
        }
    }

    //Scenario: Search Route by number stops

    @Given("^the route number stops (\\d+) is in the database$")
    public void theRouteNumberStopsIsInTheDatabase(int numberStops) throws SQLException {
        ResultSet result = routeService.getRoutes(null, null, numberStops, null);
        Assert.assertTrue(result.next());
    }

    @When("^user searches for the route number stops (\\d+)$")
    public void userSearchesForTheRouteNumberStops(int numberStops) throws SQLException {
        data.add(null);
        data.add(null);
        data.add(numberStops);
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchRoute();
        Assert.assertTrue(searchResult.next());
    }

    @Then("^the results from the search will include all routes with the route number stops (\\d+)$")
    public void theResultsFromTheSearchWillIncludeAllRoutesWithTheRouteNumberStops(int numberStops) throws SQLException {
        while (searchResult.next()) {
            Assert.assertEquals(numberStops, searchResult.getInt(9));
        }
    }

    //Scenario: Search Route by equipment

    @Given("^the route equipment \"([^\"]*)\" is in the database")
    public void theRouteEquipmentIsInTheDatabase(String equipment) throws SQLException {
        ResultSet result = routeService.getRoutes(null, null, -1, equipment);
        Assert.assertTrue(result.next());
    }

    @When("^user searches for the route equipment \"([^\"]*)\"$")
    public void userSearchesForTheRouteEquipment(String equipment) throws SQLException {
        data.add(null);
        data.add(null);
        data.add(-1);
        data.add(equipment);
        search.setSearchData(data);
        searchResult = search.searchRoute();
        Assert.assertTrue(searchResult.next());
    }

    @Then("^the results from the search will include all routes with the route equipment \"([^\"]*)\"$")
    public void theResultsFromTheSearchWillIncludeAllRoutesWithTheRouteEquipment(String equipment) throws SQLException {
        while (searchResult.next()) {
            Assert.assertEquals(equipment, searchResult.getString(10));
        }
    }
}
