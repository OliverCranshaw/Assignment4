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

public class RouteNumberStopsSearchScenario {

    private RouteService routeService;
    private Search search;
    private ArrayList<Object> data;
    private ResultSet searchResult;

    //Scenario: route number stops search

    @Before("add an occurence of the route number stops to the database")
    public void addRouteToDataBase() {
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
}
