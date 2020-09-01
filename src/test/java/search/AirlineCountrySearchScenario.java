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

public class AirlineCountrySearchScenario {

    private AirlineService airlineService;
    private Search search;
    private ArrayList<Object> data;
    private ResultSet searchResult;


    //Scenario: airline country search

    @Before("add an occurence of the airline country to the database")
    public void addAirlineToDataBase() {
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

}
