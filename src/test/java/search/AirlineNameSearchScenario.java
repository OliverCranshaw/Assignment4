package search;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import seng202.team5.Search;
import seng202.team5.service.AirlineService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AirlineNameSearchScenario
{
    private AirlineService airlineService;
    private Search search;
    private ArrayList<Object> data;
    private ResultSet searchResult;

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
}
