package search;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import seng202.team5.Search;
import seng202.team5.service.RouteService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RouteSourceAirportSearchScenario {

    private RouteService routeService;
    private Search search;
    private ArrayList<Object> data;
    private ResultSet searchResult;

    //Scenario: route source airport search

    @Before("add an occurence of the route source airport to the database")
    public void addRouteToDataBase() {
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
