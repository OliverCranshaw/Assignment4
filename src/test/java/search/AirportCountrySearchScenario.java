package search;

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

public class AirportCountrySearchScenario {

    private AirportService airportService;
    private Search search;
    private ArrayList<Object> data;
    private ResultSet searchResult;

    //Scenario: Airport country search

    @Before("add an occurence of the airport country to the database")
    public void addAirportToDataBase() {
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
}
