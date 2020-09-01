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

public class AirportCitySearchScenario {

    private AirportService airportService;
    private Search search;
    private ArrayList<Object> data;
    private ResultSet searchResult;

    //Scenario: Airport city search

    @Before("add an occurence of the airport city to the database")
    public void addAirportToDataBase() {
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
}
