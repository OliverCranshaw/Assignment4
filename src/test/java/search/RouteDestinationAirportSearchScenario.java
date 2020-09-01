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

public class RouteDestinationAirportSearchScenario {

    private RouteService routeService;
    private Search search;
    private ArrayList<Object> data;
    private ResultSet searchResult;

    //Scenario: route destination airport search

    @Before("add an occurence of the route destination airport to the database")
    public void addRouteToDataBase() {
        routeService.saveRoute(null, null, "Auckland Airport", "Y", 1, null);
    }

    @Given("^the route destination airport \"([^\"]*)\" is in the database$")
    public void theRouteDestinationAirportIsInTheDatabase(String destinationAirport) throws SQLException {
        ResultSet result = routeService.getRoutes(null, destinationAirport, 1, null);
        Assert.assertTrue(result.next());
    }

    @When("^user searches for the route destination airport \"([^\"]*)\"$")
    public void userSearchesForTheRouteDestinationAirport(String destinationAirport) throws SQLException {
        data.add(null);
        data.add(destinationAirport);
        data.add(null);
        data.add(null);
        search.setSearchData(data);
        searchResult = search.searchRoute();
        Assert.assertTrue(searchResult.next());
    }

    @Then("^the results from the search will include all routes with the route destination airport \"([^\"]*)\"$")
    public void theResultsFromTheSearchWillIncludeAllRoutesWithTheRouteDestinationAirport(String destinationAirport) throws SQLException {
        while (searchResult.next()) {
            Assert.assertEquals(destinationAirport, searchResult.getString(5));
        }
    }
}
