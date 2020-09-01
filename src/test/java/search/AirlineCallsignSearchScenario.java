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

public class AirlineCallsignSearchScenario {

    private AirlineService airlineService;
    private Search search;
    private ArrayList<Object> data;
    private ResultSet searchResult;

    //Scenario: airline callsign search

    @Before("add an occurence of the airline callsign to the database")
    public void addAirlineToDataBase() {
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

}
