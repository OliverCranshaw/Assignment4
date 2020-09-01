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

public class RouteEquipmentSearchScenario {

    private RouteService routeService;
    private Search search;
    private ArrayList<Object> data;
    private ResultSet searchResult;

    //Scenario: route equipment search

    @Before("add an occurence of the route equipment to the database")
    public void addRouteToDataBase() {
        routeService.saveRoute(null, null, null, "Y", 1, "GPS");
    }

    @Given("^the route equipment \"([^\"]*)\" is in the database$")
    public void theRouteEquipmentIsInTheDatabase(String equipment) throws SQLException {
        ResultSet result = routeService.getRoutes(null, null, 1, equipment);
        Assert.assertTrue(result.next());
    }

    @When("^user searches for the route equipment \"([^\"]*)\"$")
    public void userSearchesForTheRouteEquipment(String equipment) throws SQLException {
        data.add(null);
        data.add(null);
        data.add(null);
        data.add(equipment);
        search.setSearchData(data);
        searchResult = search.searchRoute();
        Assert.assertTrue(searchResult.next());
    }

    @Then("^the results from the search will include all routes with the route equipment \"([^\"]*)\"$")
    public void theResultsFromTheSearchWillIncludeAllRoutesWithTheRouteEquipment(String equipment) throws SQLException {
        while (searchResult.next()) {
            Assert.assertEquals(equipment, searchResult.getString(9));
        }
    }
}
