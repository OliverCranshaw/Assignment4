package scenario;

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

    //Scenario: airline callsign search

    @Before("add an occurence of the airline callsign to the database")
    public void addAirlineToDataBase1() {
        airlineService.saveAirline(null, null, "AB", "ABC", "NZ123", null, "Y");
    }

    @Given("^the airline callsign \"([^\"]*)\" is in the database$")
    public void theAirlineCallsignIsInTheDatabase(String airlineCallsign) throws SQLException {
        ResultSet result = airlineService.getAirlines(null, null, "NZ123");
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
            Assert.assertEquals(airlineCallsign, searchResult.getString(5));
        }
    }

    //Scenario: airline country search

    @Before("add an occurence of the airline country to the database")
    public void addAirlineToDataBase2() {
        airlineService.saveAirline(null, null, "AB", "ABC", null, "New Zealand", "Y");
    }

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
            Assert.assertEquals(airlineCountry, searchResult.getString(6));
        }
    }

    //Scenario: airline name search

    @Before("add an occurence of the airline name to the database")
    public void addAirlineToDataBase() {
        airlineService.saveAirline("AirNZ", null, "AB", "ABC", null, null, "Y");
    }

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

    //Scenario: Airport city search

    @Before("add an occurence of the airport city to the database")
    public void addAirportToDataBase3() {
        airportService.saveAirport(null, "Christchurch", null, "AB", "ABC", 0,
                0, 0,0, "E", null);
    }

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
            Assert.assertEquals(airportCity, searchResult.getString(2));
        }
    }

    //Scenario: Airport country search

    @Before("add an occurence of the airport country to the database")
    public void addAirportToDataBase4() {
        airportService.saveAirport(null, null, "New Zealand", "AB", "ABC", 0,
                0, 0,0, "E", null);
    }

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
            Assert.assertEquals(airportCountry, searchResult.getString(3));
        }
    }

    //Scenario: airport name search

    @Before("add an occurence of the airport name to the database")
    public void addAirportToDataBase5() {
        airportService.saveAirport("Christchurch Airport", null, null, "AB", "ABC", 0,
                0, 0, 0, "E", null);
    }

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
            Assert.assertEquals(airportName, searchResult.getString(1));
        }
    }

    //Scenario: flight airline search

    @Before("add an occurence of the flight airline to the database")
    public void addFlightToDataBase6() {
        flightService.saveFlight("Air NZ", null, 0, 0, 0);
    }

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
            Assert.assertEquals(flightAirline, searchResult.getString(2));
        }
    }

    //Scenario: flight airport search

    @Before("add an occurence of the flight airport to the database")
    public void addFlightToDataBase7() {
        flightService.saveFlight(null, "Christchurch Airport", 0, 0, 0);
    }

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
            Assert.assertEquals(flightAirport, searchResult.getString(3));
        }
    }

    //Scenario: route destination airport search

    @Before("add an occurence of the route destination airport to the database")
    public void addRouteToDataBase8() {
        routeService.saveRoute(null, null, "Auckland Airport", "Y", 1, null);
    }

    @Given("^the route destination airport \"([^\"]*)\" is in the database$")
    public void theRouteDestinationAirportIsInTheDatabase(String destinationAirport) throws SQLException {
        ResultSet result = routeService.getRoutes(null, destinationAirport, 1, null);
        Assert.assertTrue(result.next());
    }

    @When("^user searches for the route destination airport \"([^\"]*)\"$")
    public void userSearchesForTheRouteDestinationAirport(String destinationAirport) throws SQLException {
        data.add(null);
        data.add(destinationAirport);
        data.add(null);
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchRoute();
        Assert.assertTrue(searchResult.next());
    }

    @Then("^the results from the search will include all routes with the route destination airport \"([^\"]*)\"$")
    public void theResultsFromTheSearchWillIncludeAllRoutesWithTheRouteDestinationAirport(String destinationAirport) throws SQLException {
        while (searchResult.next()) {
            Assert.assertEquals(destinationAirport, searchResult.getString(5));
        }
    }

    //Scenario: route equipment search

    @Before("add an occurence of the route equipment to the database")
    public void addRouteToDataBase9() {
        routeService.saveRoute(null, null, null, "Y", 1, "GPS");
    }

    @Given("^the route equipment \"([^\"]*)\" is in the database$")
    public void theRouteEquipmentIsInTheDatabase(String equipment) throws SQLException {
        ResultSet result = routeService.getRoutes(null, null, 1, equipment);
        Assert.assertTrue(result.next());
    }

    @When("^user searches for the route equipment \"([^\"]*)\"$")
    public void userSearchesForTheRouteEquipment(String equipment) throws SQLException {
        data.add(null);
        data.add(null);
        data.add(null);
        data.add(equipment);
        search.setSearchData(data);
        searchResult = search.searchRoute();
        Assert.assertTrue(searchResult.next());
    }

    @Then("^the results from the search will include all routes with the route equipment \"([^\"]*)\"$")
    public void theResultsFromTheSearchWillIncludeAllRoutesWithTheRouteEquipment(String equipment) throws SQLException {
        while (searchResult.next()) {
            Assert.assertEquals(equipment, searchResult.getString(9));
        }
    }

    //Scenario: route number stops search

    @Before("add an occurence of the route number stops to the database")
    public void addRouteToDataBase10() {
        routeService.saveRoute(null, null, null, "Y", 2, null);
    }

    @Given("^the route number stops \"([^\"]*)\" is in the database$")
    public void theRouteNumberStopsIsInTheDatabase(String numberStops) throws SQLException {
        ResultSet result = routeService.getRoutes(null, null, Integer.parseInt(numberStops), null);
        Assert.assertTrue(result.next());
    }

    @When("^user searches for the route number stops \"([^\"]*)\"$")
    public void userSearchesForTheRouteNumberStops(String numberStops) throws SQLException {
        data.add(null);
        data.add(null);
        data.add(Integer.parseInt(numberStops));
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchRoute();
        Assert.assertTrue(searchResult.next());
    }

    @Then("^the results from the search will include all routes with the route number stops \"([^\"]*)\"$")
    public void theResultsFromTheSearchWillIncludeAllRoutesWithTheRouteNumberStops(String numberStops) throws SQLException {
        while (searchResult.next()) {
            Assert.assertEquals(Integer.parseInt(numberStops), searchResult.getInt(8));
        }
    }

    //Scenario: route source airport search

    @Before("add an occurence of the route source airport to the database")
    public void addRouteToDataBase11() {
        routeService.saveRoute(null, "Christchurch Airport", null, "Y", 1, null);
    }

    @Given("^the route source airport \"([^\"]*)\" is in the database$")
    public void theRouteSourceAirportIsInTheDatabase(String sourceAirport) throws SQLException {
        ResultSet result = routeService.getRoutes(sourceAirport, null, 1, null);
        Assert.assertTrue(result.next());
    }

    @When("^user searches for the route source airport \"([^\"]*)\"$")
    public void userSearchesForTheRouteSourceAirport(String sourceAirport) throws SQLException {
        data.add(sourceAirport);
        data.add(null);
        data.add(null);
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchRoute();
        Assert.assertTrue(searchResult.next());
    }

    @Then("^the results from the search will include all routes with the route source airport \"([^\"]*)\"$")
    public void theResultsFromTheSearchWillIncludeAllRoutesWithTheRouteSourceAirport(String sourceAirport) throws SQLException {
        while (searchResult.next()) {
            Assert.assertEquals(sourceAirport, searchResult.getString(3));
        }
    }
}
