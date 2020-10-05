package scenario;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import seng202.team5.data.*;
import seng202.team5.database.DBConnection;
import seng202.team5.database.DBInitializer;
import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.FlightService;
import seng202.team5.service.RouteService;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateDataScenario {

    private ConcreteUpdateData concreteUpdateData;
    private AirlineService airlineService;
    private AirportService airportService;
    private FlightService flightService;
    private RouteService routeService;
    private AirlineData airlineData;
    private AirportData airportData;
    private FlightData flightData;
    private RouteData routeData;

    @Before("@Update")
    public void setup() {
        String filename = "test.db";
        File dbFile = new File(filename);

        DBInitializer.createNewDatabase(filename);

        DBConnection.setDatabaseFile(dbFile);

        concreteUpdateData = new ConcreteUpdateData();
        airlineService = new AirlineService();
        airportService = new AirportService();
        flightService = new FlightService();
        routeService = new RouteService();

        airlineService.save("AirNZ", null, "AB", "ABC", "NZ123", "New Zealand", "Y");
        airportService.save("Christchurch Airport", "Christchurch", "New Zealand", "ABC", "ABCD", 0,
                0, 0, 0, "E", "Pacific/Auckland");
        airportService.save("Auckland Airport", "Auckland", "New Zealand", "DBS", "DBSA", 0,
                0, 0, 0, "E", "Pacific/Auckland");
        flightService.save(1, "FIX", "NZCH", 0, 0, 0);
        routeService.save("AB", "ABC", "DBS", "Y", 2, "GPS");
    }

    @After
    public void teardown() {
        try {
            File dbFile = new File("test.db");
            Connection con = DBConnection.getConnection();
            con.close();

            boolean result = dbFile.delete();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    // Updating airline data

    // Updating an airline with valid parameters

    @Given("valid airline parameters {string}, {string}, {string}, {string}, {string}, {string}, {string} and an existing airline with id {int}")
    public void validAirlineParametersNullAndAnExistingAirlineWithId(String name, String alias, String iata, String icao, String callsign, String country, String active, int id) throws SQLException {
        airlineData = new AirlineData(name, alias, iata, icao, callsign, country, active);
        airlineData.convertBlanksToNull();
        Assert.assertEquals(1, airlineData.checkValues());

        Assert.assertTrue(airlineService.getData(id).next());
    }

    @When("updating an airline with id {int} with valid parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}")
    public void updatingAnAirlineWithIdWithValidParameters(int id, String name, String alias, String iata, String icao, String callsign, String country, String active) throws SQLException {
        int res = concreteUpdateData.updateAirline(id, name, alias, iata, icao, callsign, country, active);

        Assert.assertEquals(id, res);
    }

    @Then("the airline with id {int} is updated with valid parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}")
    public void theAirlineWithIdIsUpdatedWithValidParameters(int id, String name, String alias, String iata, String icao, String callsign, String country, String active) throws SQLException {
        ResultSet airline = airlineService.getData(id);

        Assert.assertEquals(name, airline.getString("airline_name"));
        Assert.assertEquals(alias, airline.getString("alias"));
        Assert.assertEquals(iata, airline.getString("iata"));
        Assert.assertEquals(icao, airline.getString("icao"));
        Assert.assertEquals(callsign, airline.getString("callsign"));
        Assert.assertEquals(country, airline.getString("country"));
        Assert.assertEquals(active, airline.getString("active"));
    }

    // Updating an airline with invalid parameters

    @Given("an invalid name {string} and an existing airline with id {int}")
    public void anInvalidNameAndAnExistingAirlineWithId(String name, int id) throws SQLException {
        airlineData = new AirlineData(name, null, "AB", "ABC", "NZ123", "New Zealand", "Y");
        airlineData.convertBlanksToNull();
        Assert.assertEquals(-2, airlineData.checkValues());

        Assert.assertTrue(airlineService.getData(id).next());
    }

    @When("updating an airline with id {int} with an invalid name {string}")
    public void updatingAnAirlineWithIdWithAnInvalidName(int id, String name) throws SQLException {
        int res = concreteUpdateData.updateAirline(id, name, null, "AB", "ABC", "NZ123", "New Zealand", "Y");

        Assert.assertEquals(-2, res);
    }

    @Then("the airline with id {int} is not updated")
    public void theAirlineIsNotUpdated(int id) throws SQLException {
        ResultSet airline = airlineService.getData(id);

        Assert.assertEquals("AirNZ", airline.getString("airline_name"));
        Assert.assertEquals("AB", airline.getString("iata"));
        Assert.assertEquals("ABC", airline.getString("icao"));
        Assert.assertEquals("Y", airline.getString("active"));
    }

    @Given("an invalid iata code {string} and an existing airline with id {int}")
    public void anInvalidIataCodeAndAnExistingAirlineWithId(String iata, int id) throws SQLException {
        airlineData = new AirlineData("AirNZ", null, iata, "ABC", "NZ123", "New Zealand", "Y");
        airlineData.convertBlanksToNull();
        Assert.assertEquals(-3, airlineData.checkValues());

        Assert.assertTrue(airlineService.getData(id).next());
    }

    @When("updating an airline with id {int} with an invalid iata code {string}")
    public void updatingAnAirlineWithIdWithAnInvalidIataCode(int id, String iata) throws SQLException {
        int res = concreteUpdateData.updateAirline(id, "AirNZ", null, iata, "ABC", "NZ123", "New Zealand", "Y");

        Assert.assertEquals(-3, res);
    }

    @Given("an invalid icao code {string} and an existing airline with id {int}")
    public void anInvalidIcaoCodeAndAnExistingAirlineWithId(String icao, int id) throws SQLException {
        airlineData = new AirlineData("AirNZ", null, "AB", icao, "NZ123", "New Zealand", "Y");
        airlineData.convertBlanksToNull();
        Assert.assertEquals(-4, airlineData.checkValues());

        Assert.assertTrue(airlineService.getData(id).next());
    }

    @When("updating an airline with id {int} with an invalid icao code {string}")
    public void updatingAnAirlineWithIdWithAnInvalidIcaoCode(int id, String icao) throws SQLException {
        int res = concreteUpdateData.updateAirline(id, "AirNZ", null, "AB", icao, "NZ123", "New Zealand", "Y");

        Assert.assertEquals(-4, res);
    }

    @Given("an invalid active {string} and an existing airline with id {int}")
    public void anInvalidActiveAndAnExistingAirlineWithId(String active, int id) throws SQLException {
        airlineData = new AirlineData("AirNZ", null, "AB", "ABC", "NZ123", "New Zealand", active);
        airlineData.convertBlanksToNull();
        Assert.assertEquals(-5, airlineData.checkValues());

        Assert.assertTrue(airlineService.getData(id).next());
    }

    @When("updating an airline with id {int} with an invalid active {string}")
    public void updatingAnAirlineWithIdWithAnInvalidActive(int id, String active) throws SQLException {
        int res = concreteUpdateData.updateAirline(id, "AirNZ", null, "AB", "ABC", "NZ123", "New Zealand", active);

        Assert.assertEquals(-5, res);
    }


    // Updating airport data

    // Updating an airport with valid parameters

    @Given("valid airport parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string} and an existing aiport with id {int}")
    public void validAirportParametersAndAnExistingAirportWithId(String name, String city, String country, String iata, String icao, String latitude, String longitude, String altitude, String timezone, String dst, String tz, int id) throws SQLException{
        airportData = new AirportData(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        airportData.convertBlanksToNull();
        Assert.assertEquals(1, airportData.checkValues());

        Assert.assertTrue(airportService.getData(id).next());
    }

    @When("updating an airport with id {int} with parameters {string}, {string}, {string}, {string}, {string}, {double}, {double}, {int}, {float}, {string}, {string}")
    public void updatingAnAirportWithIdWithParameters(int id, String name, String city, String country, String iata, String icao, double latitude, double longitude, int altitude, float timezone, String dst, String tz) throws SQLException {
        int res = concreteUpdateData.updateAirport(id, name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);

        Assert.assertEquals(id, res);
    }

    @Then("the airport with id {int} is updated with valid parameters {string}, {string}, {string}, {string}, {string}, {double}, {double}, {int}, {float}, {string}, {string}")
    public void theAirportWithIdIsUpdatedWithValidParameters(int id, String name, String city, String country, String iata, String icao, double latitude, double longitude, int altitude, float timezone, String dst, String tz) throws SQLException {
        ResultSet airport = airportService.getData(id);

        Assert.assertEquals(name, airport.getString("airport_name"));
        Assert.assertEquals(city, airport.getString("city"));
        Assert.assertEquals(country, airport.getString("country"));
        Assert.assertEquals(iata, airport.getString("iata"));
        Assert.assertEquals(icao, airport.getString("icao"));
        Assert.assertEquals(latitude, airport.getDouble("latitude"), 0.01);
        Assert.assertEquals(longitude, airport.getDouble("longitude"), 0.01);
        Assert.assertEquals(altitude, airport.getInt("altitude"));
        Assert.assertEquals(timezone, airport.getFloat("timezone"), 0.01);
        Assert.assertEquals(dst, airport.getString("dst"));
        Assert.assertEquals(tz, airport.getString("tz_database_timezone"));
    }

    // Updating an airport with invalid parameters

    @Given("an invalid name {string} and an existing airport with id {int}")
    public void anInvalidNameAndAnExistingAirportWithId(String name, int id) throws SQLException {
        airportData = new AirportData(name, "Christchurch", "New Zealand", "ABC", "ABCD", "0", "0", "0", "0", "E", "Pacific/Auckland");
        airportData.convertBlanksToNull();
        Assert.assertEquals(-2, airportData.checkValues());

        Assert.assertTrue(airportService.getData(id).next());
    }

    @When("updating an airport with id {int} with an invalid name {string}")
    public void updatingAnAirportWithIdWithAnInvalidName(int id, String name) throws SQLException {
        int res = concreteUpdateData.updateAirport(id, name, "Christchurch", "New Zealand", "ABC", "ABCD", 0d, 0d, 0, 0f, "E", "Pacific/Auckland");

        Assert.assertEquals(-2, res);
    }

    @Then("the airport with id {int} is not updated")
    public void theAirportWithIdIsNotUpdated(int id) throws SQLException {
        ResultSet airport = airportService.getData(id);

        Assert.assertEquals("Christchurch Airport", airport.getString("airport_name"));
        Assert.assertEquals("Christchurch", airport.getString("city"));
        Assert.assertEquals("New Zealand", airport.getString("country"));
        Assert.assertEquals("ABC", airport.getString("iata"));
        Assert.assertEquals("ABCD", airport.getString("icao"));
        Assert.assertEquals("E", airport.getString("dst"));
        Assert.assertEquals("Pacific/Auckland", airport.getString("tz_database_timezone"));
    }

    @Given("an invalid city {string} and an existing airport with id {int}")
    public void anInvalidCityAndAnExistingAirportWithId(String city, int id) throws SQLException {
        airportData = new AirportData("Christchurch Airport", city, "New Zealand", "ABC", "ABCD", "0", "0", "0", "0", "E", "Pacific/Auckland");
        airportData.convertBlanksToNull();
        Assert.assertEquals(-3, airportData.checkValues());

        Assert.assertTrue(airportService.getData(id).next());
    }

    @When("updating an airport with id {int} with an invalid city {string}")
    public void updatingAnAirportWithIdWithAnInvalidCity(int id, String city) throws SQLException {
        int res = concreteUpdateData.updateAirport(id, "Christchurch Airport", city, "New Zealand", "ABC", "ABCD", 0d, 0d, 0, 0f, "E", "Pacific/Auckland");

        Assert.assertEquals(-3, res);
    }

    @Given("an invalid country {string} and an existing airport with id {int}")
    public void anInvalidCountryAndAnExistingAirportWithId(String country, int id) throws SQLException {
        airportData = new AirportData("Christchurch Airport", "Christchurch", country, "ABC", "ABCD", "0", "0", "0", "0", "E", "Pacific/Auckland");
        airportData.convertBlanksToNull();
        Assert.assertEquals(-4, airportData.checkValues());

        Assert.assertTrue(airportService.getData(id).next());
    }

    @When("updating an airport with id {int} with an invalid country {string}")
    public void updatingAnAirportWithIdWithAnInvalidCountry(int id, String country) throws SQLException {
        int res = concreteUpdateData.updateAirport(id, "Christchurch Airport", "Christchurch", country, "ABC", "ABCD", 0d, 0d, 0, 0f, "E", "Pacific/Auckland");

        Assert.assertEquals(-4, res);
    }

    @Given("an invalid iata code {string} and an existing airport with id {int}")
    public void anInvalidIataCodeAndAnExistingAirportWithId(String iata, int id) throws SQLException {
        airportData = new AirportData("Christchurch Airport", "Christchurch", "New Zealand", iata, "ABCD", "0", "0", "0", "0", "E", "Pacific/Auckland");
        airportData.convertBlanksToNull();
        Assert.assertEquals(-5, airportData.checkValues());

        Assert.assertTrue(airportService.getData(id).next());
    }

    @When("updating an airport with id {int} with an invalid iata code {string}")
    public void updatingAnAirportWithIdWithAnInvalidIataCode(int id, String iata) throws SQLException {
        int res = concreteUpdateData.updateAirport(id, "Christchurch Airport", "Christchurch", "New Zealand", iata, "ABCD", 0d, 0d, 0, 0f, "E", "Pacific/Auckland");

        Assert.assertEquals(-5, res);
    }

    @Given("an invalid icao code {string} and an existing airport with id {int}")
    public void anInvalidIcaoCodeAndAnExistingAirportWithId(String icao, int id) throws SQLException {
        airportData = new AirportData("Christchurch Airport", "Christchurch", "New Zealand", "ABC", icao, "0", "0", "0", "0", "E", "Pacific/Auckland");
        airportData.convertBlanksToNull();
        Assert.assertEquals(-6, airportData.checkValues());

        Assert.assertTrue(airportService.getData(id).next());
    }

    @When("updating an airport with id {int} with an invalid icao code {string}")
    public void updatingAnAirportWithIdWithAnInvalidIcaoCode(int id, String icao) throws SQLException {
        int res = concreteUpdateData.updateAirport(id, "Christchurch Airport", "Christchurch", "New Zealand", "ABC", icao, 0d, 0d, 0, 0f, "E", "Pacific/Auckland");

        Assert.assertEquals(-6, res);
    }

    @Given("an invalid dst {string} and an existing airport with id {int}")
    public void anInvalidDstAndAnExistingAirportWithId(String dst, int id) throws SQLException {
        airportData = new AirportData("Christchurch Airport", "Christchurch", "New Zealand", "ABC", "ABCD", "0", "0", "0", "0", dst, "Pacific/Auckland");
        airportData.convertBlanksToNull();
        Assert.assertEquals(-11, airportData.checkValues());

        Assert.assertTrue(airportService.getData(id).next());
    }

    @When("updating an airport with id {int} with an invalid dst {string}")
    public void updatingAnAirportWithIdWithAnInvalidDst(int id, String dst) throws SQLException {
        int res = concreteUpdateData.updateAirport(id, "Christchurch Airport", "Christchurch", "New Zealand", "ABC", "ABCD", 0d, 0d, 0, 0f, dst, "Pacific/Auckland");

        Assert.assertEquals(-11, res);
    }

    @Given("an invalid tz {string} and an existing airport with id {int}")
    public void anInvalidTzAndAnExistingAirportWithId(String tz, int id) throws SQLException {
        airportData = new AirportData("Christchurch Airport", "Christchurch", "New Zealand", "ABC", "ABCD", "0", "0", "0", "0", "E", tz);
        airportData.convertBlanksToNull();
        Assert.assertEquals(-12, airportData.checkValues());

        Assert.assertTrue(airportService.getData(id).next());
    }

    @When("updating an airport with id {int} with an invalid tz {string}")
    public void updatingAnAirportWithIdWithAnInvalidTz(int id, String tz) throws SQLException {
        int res = concreteUpdateData.updateAirport(id, "Christchurch Airport", "Christchurch", "New Zealand", "ABC", "ABCD", 0d, 0d, 0, 0f, "E", tz);

        Assert.assertEquals(-12, res);
    }


    // Updating flight data

    // Updating a flight entry with valid parameters

    @Given("valid flight entry parameters {string}, {string}, {string}, {string}, {string} and an existing flight entry with id {int}")
    public void validFlightEntryParametersAndAnExistingFlightEntryWithId(String locationType, String location, String altitude, String latitude, String longitude, int id) throws SQLException {
        flightData = new FlightData(flightService.getNextFlightID(), locationType, location, altitude, latitude, longitude);
        flightData.convertBlanksToNull();
        Assert.assertEquals(1, flightData.checkValues());

        Assert.assertTrue(flightService.getData(id).next());
    }

    @When("updating a flight entry with id {int} with parameters {string}, {string}, {int}, {double}, {double}")
    public void updatingAFlightEntryWithIdWithParameters(int id, String locationType, String location, int altitude, double latitude, double longitude) {
        int res = concreteUpdateData.updateFlightEntry(id, locationType, location, altitude, latitude, longitude);

        Assert.assertEquals(id, res);
    }

    @Then("the flight entry with id {int} is updated with valid parameters {string}, {string}, {int}, {double}, {double}")
    public void theFlightEntryWithIdIsUpdatedWithValidParameters(int id, String locationType, String location, int altitude, double latitude, double longitude) throws SQLException {
        ResultSet flightEntry = flightService.getData(id);

        Assert.assertEquals(locationType, flightEntry.getString("location_type"));
        Assert.assertEquals(location, flightEntry.getString("location"));
        Assert.assertEquals(altitude, flightEntry.getInt("altitude"));
        Assert.assertEquals(latitude, flightEntry.getDouble("latitude"), 0.01);
        Assert.assertEquals(longitude, flightEntry.getDouble("longitude"), 0.01);
    }

    // Updating a flight entry with invalid parameters

    @Given("an invalid location type {string} and an existing flight entry with id {int}")
    public void anInvalidLocationTypeAndAnExistingFlightEntryWithId(String locationType, int id) throws SQLException {
        flightData = new FlightData(flightService.getNextFlightID(), locationType, "NZCH", "0", "0", "0");
        flightData.convertBlanksToNull();
        Assert.assertEquals(-3, flightData.checkValues());

        Assert.assertTrue(flightService.getData(id).next());
    }

    @When("updating a flight entry with id {int} with invalid location type {string}")
    public void updatingAFlightEntryWithIdWithInvalidLocationType(int id, String locationType) {
        int res = concreteUpdateData.updateFlightEntry(id, locationType, "NZCH", 0, 0d, 0d);

        Assert.assertEquals(-3, res);
    }

    @Then("the flight entry with id {int} is not updated")
    public void theFlightEntryWithIdIsNotUpdated(int id) throws SQLException {
        ResultSet flightEntry = flightService.getData(id);

        Assert.assertEquals("FIX", flightEntry.getString("location_type"));
        Assert.assertEquals("NZCH", flightEntry.getString("location"));
    }

    @Given("an invalid location {string} and an existing flight entry with id {int}")
    public void anInvalidLocationAndAnExistingFlightEntryWithId(String location, int id) throws SQLException {
        flightData = new FlightData(flightService.getNextFlightID(), "FIX", location, "0", "0", "0");
        flightData.convertBlanksToNull();
        Assert.assertEquals(-4, flightData.checkValues());

        Assert.assertTrue(flightService.getData(id).next());
    }

    @When("updating a flight entry with id {int} with invalid location {string}")
    public void updatingAFlightEntryWithIdWithInvalidLocation(int id, String location) {
        int res = concreteUpdateData.updateFlightEntry(id, "FIX", location, 0, 0d, 0d);

        Assert.assertEquals(-4, res);
    }


    // Updating route data

    // Updating a route with valid parameters

    @Given("valid route parameters {string}, {string}, {string}, {string}, {string}, {string} and an existing route with id {int}")
    public void validRouteParametersAndAnExistingRouteWithId(String airline, String sourceAirport, String destAirport, String codeshare, String stops, String equipment, int id) throws SQLException {
        routeData = new RouteData(airline, sourceAirport, destAirport, codeshare, stops, equipment);
        routeData.convertBlanksToNull();
        Assert.assertEquals(1, routeData.checkValues());

        Assert.assertTrue(routeService.getData(id).next());
    }

    @When("updating a route with id {int} with parameters {string}, {string}, {string}, {string}, {int}, {string}")
    public void updatingARouteWithIdWithParameters(int id, String airline, String sourceAirport, String destAirport, String codeshare, int stops, String equipment) throws SQLException {
        int res = concreteUpdateData.updateRoute(id, airline, sourceAirport, destAirport, codeshare, stops, equipment);

        Assert.assertEquals(id, res);
    }

    @Then("the route with id {int} is updated with valid parameters {string}, {string}, {string}, {string}, {int}, {string}")
    public void theRouteWithIdIsUpdatedWithValidParameters(int id, String airline, String sourceAirport, String destAirport, String codeshare, int stops, String equipment) throws SQLException {
        ResultSet route = routeService.getData(id);

        if (codeshare.equals("")) {
            codeshare = null;
        }

        Assert.assertEquals(airline, route.getString("airline"));
        Assert.assertEquals(sourceAirport, route.getString("source_airport"));
        Assert.assertEquals(destAirport, route.getString("destination_airport"));
        Assert.assertEquals(codeshare, route.getString("codeshare"));
        Assert.assertEquals(stops, route.getInt("stops"));
        Assert.assertEquals(equipment, route.getString("equipment"));
    }

    // Updating a route with invalid parameters

    @Given("an invalid airline code {string} and an existing route with id {int}")
    public void anInvalidAirlineCodeAndAnExistingRouteWithId(String airline, int id) throws SQLException {
        routeData = new RouteData(airline, "ABC", "DBS", "Y", "2", "GPS");
        routeData.convertBlanksToNull();
        Assert.assertEquals(-2, routeData.checkValues());

        Assert.assertTrue(routeService.getData(id).next());
    }

    @When("updating a route with id {int} with invalid airline code {string}")
    public void updatingARouteWithIdWithInvalidAirlineCode(int id, String airline) throws SQLException {
        int res = concreteUpdateData.updateRoute(id, airline, "ABC", "DBS", "Y", 2, "GPS");

        Assert.assertEquals(-2, res);
    }

    @Then("the route with id {int} is not updated")
    public void theRouteWithIdIsNotUpdated(int id) throws SQLException {
        ResultSet route = routeService.getData(id);

        Assert.assertEquals("AB", route.getString("airline"));
        Assert.assertEquals("ABC", route.getString("source_airport"));
        Assert.assertEquals("DBS", route.getString("destination_airport"));
        Assert.assertEquals("Y", route.getString("codeshare"));
        Assert.assertEquals(2, route.getInt("stops"));
        Assert.assertEquals("GPS", route.getString("equipment"));
    }

    @Given("an invalid source airport code {string} and an existing route with id {int}")
    public void anInvalidSourceAirportCodeAndAnExistingRouteWithId(String sourceAirport, int id) throws SQLException {
        routeData = new RouteData("AB", sourceAirport, "DBS", "Y", "2", "GPS");
        routeData.convertBlanksToNull();
        Assert.assertEquals(-3, routeData.checkValues());

        Assert.assertTrue(routeService.getData(id).next());
    }

    @When("updating a route with id {int} with invalid source airport code {string}")
    public void updatingARouteWithIdWithInvalidSourceAirportCode(int id, String sourceAirport) throws SQLException{
        int res = concreteUpdateData.updateRoute(id, "AB", sourceAirport, "DBS", "Y", 2, "GPS");

        Assert.assertEquals(-3, res);
    }

    @Given("an invalid destination airport code {string} and an existing route with id {int}")
    public void anInvalidDestinationAirportCodeAndAnExistingRouteWithId(String destAirport, int id) throws SQLException {
        routeData = new RouteData("AB", "ABC", destAirport, "Y", "2", "GPS");
        routeData.convertBlanksToNull();
        Assert.assertEquals(-4, routeData.checkValues());

        Assert.assertTrue(routeService.getData(id).next());
    }

    @When("updating a route with id {int} with invalid destination airport code {string}")
    public void updatingARouteWithIdWithInvalidDestinationAirportCode(int id, String destAirport) throws SQLException {
        int res = concreteUpdateData.updateRoute(id, "AB", "ABC", destAirport, "Y", 2, "GPS");

        Assert.assertEquals(-4, res);
    }

    @Given("an invalid codeshare {string} and an existing route with id {int}")
    public void anInvalidCodeshareAndAnExistingRouteWithId(String codeshare, int id) throws SQLException {
        routeData = new RouteData("AB", "ABC", "DBS", codeshare, "2", "GPS");
        routeData.convertBlanksToNull();
        Assert.assertEquals(-5, routeData.checkValues());

        Assert.assertTrue(routeService.getData(id).next());
    }

    @When("updating a route with id {int} with invalid codeshare {string}")
    public void updatingARouteWithIdWithInvalidCodeshare(int id, String codeshare) throws SQLException {
        int res = concreteUpdateData.updateRoute(id, "AB", "ABC", "DBS", codeshare, 2, "GPS");

        Assert.assertEquals(-5, res);
    }

    @Given("invalid stops {string} and an existing route with id {int}")
    public void invalidStopsAndAnExistingRouteWithId(String stops, int id) throws SQLException {
        routeData = new RouteData("AB", "ABC", "DBS", "Y", stops, "GPS");
        routeData.convertBlanksToNull();
        Assert.assertEquals(-6, routeData.checkValues());

        Assert.assertTrue(routeService.getData(id).next());
    }

    @When("updating a route with id {int} with invalid stops {int}")
    public void updatingARouteWithIdWithInvalidStops(int id, int stops) throws SQLException {
        int res = concreteUpdateData.updateRoute(id, "AB", "ABC", "DBS", "Y", stops, "GPS");

        Assert.assertEquals(-6, res);
    }

    @Given("invalid equipment {string} and an existing route with id {int}")
    public void invalidEquipmentAndAnExistingRouteWithId(String equipment, int id) throws SQLException {
        routeData = new RouteData("AB", "ABC", "DBS", "Y", "2", equipment);
        routeData.convertBlanksToNull();
        Assert.assertEquals(-7, routeData.checkValues());

        Assert.assertTrue(routeService.getData(id).next());
    }

    @When("updating a route with id {int} with invalid equipment {string}")
    public void updatingARouteWithIdWithInvalidEquipment(int id, String equipment) throws SQLException {
        int res = concreteUpdateData.updateRoute(id, "AB", "ABC", "DBS", "Y", 2, equipment);

        Assert.assertEquals(-7, res);
    }
}
