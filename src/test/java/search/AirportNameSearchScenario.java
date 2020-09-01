package search;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import seng202.team5.Search;
import seng202.team5.service.AirportService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AirportNameSearchScenario {

    private AirportService airportService;
    private Search search;
    private ArrayList<Object> data;
    private ResultSet searchResult;

    //Scenario: airport name search

    @Before("add an occurence of the airport name to the database")
    public void addAirportToDataBase() {
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
}
