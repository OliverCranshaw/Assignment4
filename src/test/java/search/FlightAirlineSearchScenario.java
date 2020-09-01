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

public class FlightAirlineSearchScenario {

    private FlightService flightService;
    private Search search;
    private ArrayList<Object> data;
    private ResultSet searchResult;

    //Scenario: flight airline search

    @Before("add an occurence of the flight airline to the database")
    public void addFlightToDataBase() {
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
}
