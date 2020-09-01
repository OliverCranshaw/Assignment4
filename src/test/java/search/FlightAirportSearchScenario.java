package search;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import seng202.team5.Search;
import seng202.team5.service.FlightService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FlightAirportSearchScenario {

    private FlightService flightService;
    private Search search;
    private ArrayList<Object> data;
    private ResultSet searchResult;

    //Scenario: flight airport search

    @Before("add an occurence of the flight airport to the database")
    public void addFlightToDataBase() {
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
}
