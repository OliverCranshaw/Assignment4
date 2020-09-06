package scenario;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.After;
import org.junit.Assert;
import seng202.team5.accessor.AirlineAccessor;
import seng202.team5.accessor.AirportAccessor;
import seng202.team5.data.*;
import seng202.team5.database.DBConnection;
import seng202.team5.database.DBInitializer;
import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.FlightService;
import seng202.team5.service.RouteService;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class AddDataScenario {

    private int id;
    private int flight_id;
    private AirlineData airlineData;
    private AirportData airportData;
    private FlightData flightData;
    private RouteData routeData;
    private AirlineService airlineService;
    private AirportService airportService;
    private FlightService flightService;
    private RouteService routeService;
    private ConcreteAddData concreteAddData;

    @Before
    public void setup() {
        concreteAddData = new ConcreteAddData();
        airlineService = new AirlineService();
        airportService = new AirportService();
        flightService = new FlightService();
        routeService = new RouteService();
    }


    // Adding Airlines

    //  Adding a valid airline

    @Given("^valid airline parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\)$")
    public void validAirlineParameters(String name, String alias, String iata, String icao, String callsign, String country, String active) {
        airlineData = new AirlineData(name, alias, iata, icao, callsign, country, active);
        Assert.assertEquals(1, airlineData.checkValues());
    }

    @When("^adding an airline with parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\)$")
    public void addValidAirline(String name, String alias, String iata, String icao, String callsign, String country, String active) {
        id = concreteAddData.addAirline(name, alias, iata, icao, callsign, country, active);
    }

    @Then("the airline is added with id 1")
    public void airlineAdded(int id) {
        Assert.assertEquals(airlineService.getMaxID(), id);
    }

    // Adding an airline with an invalid name

    @Given("^valid airline parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid name \"([^\"]*)\"$")
    public void airlineInvalidName(String alias, String iata, String icao, String callsign, String country, String active, String name) {
        airlineData = new AirlineData(name, alias, iata, icao, callsign, country, active);
        Assert.assertEquals(-2, airlineData.checkValues());
    }

    @When("^adding an airline with parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) and invalid name \"([^\"]*)\"$")
    public void addAirlineInvalidName(String alias, String iata, String icao, String callsign, String country, String active, String name) {
        id = concreteAddData.addAirline(name, alias, iata, icao, callsign, country, active);
    }

    @Then("the airline parameters are rejected, and an error code -2 is returned")
    public void airlineRejectedInvalidName(int id) {
        Assert.assertEquals(-2, id);
    }

    // Adding an airline with an invalid IATA code

    @Given("^valid airline parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid iata code \"([^\"]*)\"$")
    public void airlineInvalidIATA(String name, String alias, String icao, String callsign, String country, String active, String iata) {
        airlineData = new AirlineData(name, alias, iata, icao, callsign, country, active);
        Assert.assertEquals(-3, airlineData.checkValues());
    }

    @When("^adding an airline with parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) and invalid iata code \"([^\"]*)\"$")
    public void addAirlineInvalidIATA(String name, String alias, String icao, String callsign, String country, String active, String iata) {
        id = concreteAddData.addAirline(name, alias, iata, icao, callsign, country, active);
    }

    @Then("the airline parameters are rejected, and an error code -3 is returned")
    public void airlineRejectedInvalidIATA(int id) {
        Assert.assertEquals(-3, id);
    }

    // Adding an airline with an invalid ICAO code

    @Given("^valid airline parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid icao code \"([^\"]*)\"$")
    public void airlineInvalidICAO(String name, String alias, String iata, String callsign, String country, String active, String icao) {
        airlineData = new AirlineData(name, alias, iata, icao, callsign, country, active);
        Assert.assertEquals(-4, airlineData.checkValues());
    }

    @When("^adding an airline with parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) and invalid icao code \"([^\"]*)\"$")
    public void addAirlineInvalidICAO(String name, String alias, String iata, String callsign, String country, String active, String icao) {
        id = concreteAddData.addAirline(name, alias, iata, callsign, country, active, icao);
    }

    @Then("the airline parameters are rejected, and an error code -4 is returned")
    public void airlineRejectedInvalidICAO(int id) {
        Assert.assertEquals(-4, id);
    }

    // Adding an airline with an invalid active

    @Given("^valid airline parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid active \"([^\"]*)\"$")
    public void airlineInvalidActive(String name, String alias, String iata, String icao, String callsign, String country, String active) {
        airlineData = new AirlineData(name, alias, iata, icao, callsign, country, active);
        Assert.assertEquals(-5, airlineData.checkValues());
    }

    @When("^adding an airline with parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) and invalid active \"([^\"]*)\"$")
    public void addAirlineInvalidActive(String name, String alias, String iata, String icao, String callsign, String country, String active) {
        id = concreteAddData.addAirline(name, alias, iata, icao, callsign, country, active);
    }

    @Then("the airline parameters are rejected, and an error code -5 is returned")
    public void airlineRejectedInvalidActive(int id) {
        Assert.assertEquals(-5, id);
    }


    // Adding Airports

    // Adding a valid airport

    @Given("^valid airport parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\)$")
    public void validAirportParameters(String name, String city, String country, String iata, String icao, String latitude, String longitude, String altitude, String timezone, String dst, String tz) {
        airportData = new AirportData(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        Assert.assertEquals(1, airportData.checkValues());
    }

    @When("^adding an airport with parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\)$")
    public void addValidAirport(String name, String city, String country, String iata, String icao, String latitude, String longitude, String altitude, String timezone, String dst, String tz) {
        id = concreteAddData.addAirport(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
    }

    @Then("the airport is added with id 1")
    public void airportAdded(int id) {
        Assert.assertEquals(airportService.getMaxID(), id);
    }

    // Adding an airport with an invalid name

    @Given("^valid airport parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid name \"([^\"]*)\"$")
    public void airportInvalidName(String city, String country, String iata, String icao, String latitude, String longitude, String altitude, String timezone, String dst, String tz, String name) {
        airportData = new AirportData(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        Assert.assertEquals(-2, airportData.checkValues());
    }

    @When("^adding an airport with parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) and invalid name \"([^\"]*)\"$")
    public void addAirportInvalidName(String city, String country, String iata, String icao, String latitude, String longitude, String altitude, String timezone, String dst, String tz, String name) {
        id = concreteAddData.addAirport(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
    }

    @Then("the airport parameters are rejected, and an error code -2 is returned")
    public void airportRejectedInvalidName(int id) {
        Assert.assertEquals(-2, id);
    }

    // Adding an airport with an invalid city

    @Given("^valid airport parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid city \"([^\"]*)\"$")
    public void airportInvalidCity(String name, String country, String iata, String icao, String latitude, String longitude, String altitude, String timezone, String dst, String tz, String city) {
        airportData = new AirportData(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        Assert.assertEquals(-3, airportData.checkValues());
    }

    @When("^adding an airport with parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) and invalid city \"([^\"]*)\"$")
    public void addAirportInvalidCity(String name, String country, String iata, String icao, String latitude, String longitude, String altitude, String timezone, String dst, String tz, String city) {
        id = concreteAddData.addAirport(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
    }

    @Then("the airport parameters are rejected, and an error code -3 is returned")
    public void airportRejectedInvalidCity(int id) {
        Assert.assertEquals(-3, id);
    }

    // Adding an airport with an invalid country

    @Given("^valid airport parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid country \"([^\"]*)\"$")
    public void airportInvalidCountry(String name, String city, String iata, String icao, String latitude, String longitude, String altitude, String timezone, String dst, String tz, String country) {
        airportData = new AirportData(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        Assert.assertEquals(-4, airportData.checkValues());
    }

    @When("^adding an airport with parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) and invalid country \"([^\"]*)\"$")
    public void addAirportInvalidCountry(String name, String city, String iata, String icao, String latitude, String longitude, String altitude, String timezone, String dst, String tz, String country) {
        id = concreteAddData.addAirport(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
    }

    @Then("the airport parameters are rejected, and an error code -4 is returned")
    public void airportRejectedInvalidCountry(int id) {
        Assert.assertEquals(-4, id);
    }

    // Adding an airport with an invalid IATA code

    @Given("^valid airport parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid iata code \"([^\"]*)\"$")
    public void airportInvalidIATA(String name, String city, String country, String icao, String latitude, String longitude, String altitude, String timezone, String dst, String tz, String iata) {
        airportData = new AirportData(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        Assert.assertEquals(-5, airportData.checkValues());
    }

    @When("^adding an airport with parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) and invalid iata code \"([^\"]*)\"$")
    public void addAirportInvalidIATA(String name, String city, String country, String icao, String latitude, String longitude, String altitude, String timezone, String dst, String tz, String iata) {
        id = concreteAddData.addAirport(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
    }

    @Then("the airport parameters are rejected, and an error code -5 is returned")
    public void airportRejectedInvalidIATA(int id) {
        Assert.assertEquals(-5, id);
    }

    // Adding an airport with an invalid ICAO code

    @Given("^valid airport parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid icao code \"([^\"]*)\"$")
    public void airportInvalidICAO(String name, String city, String country, String iata, String latitude, String longitude, String altitude, String timezone, String dst, String tz, String icao) {
        airportData = new AirportData(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        Assert.assertEquals(-6, airportData.checkValues());
    }

    @When("^adding an airport with parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) and invalid icao code \"([^\"]*)\"$")
    public void addAirportInvalidICAO(String name, String city, String country, String iata, String latitude, String longitude, String altitude, String timezone, String dst, String tz, String icao) {
        id = concreteAddData.addAirport(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
    }

    @Then("the airport parameters are rejected, and an error code -6 is returned")
    public void airportRejectedInvalidICAO(int id) {
        Assert.assertEquals(-6, id);
    }

    // Adding an airport with an invalid latitude

    @Given("^valid airport parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid latitude \"([^\"]*)\"$")
    public void airportInvalidLatitude(String name, String city, String country, String iata, String icao, String longitude, String altitude, String timezone, String dst, String tz, String latitude) {
        airportData = new AirportData(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        Assert.assertEquals(-7, airportData.checkValues());
    }

    @When("^adding an airport with parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) and invalid latitude \"([^\"]*)\"$")
    public void addAirportInvalidLatitude(String name, String city, String country, String iata, String icao, String longitude, String altitude, String timezone, String dst, String tz, String latitude) {
        id = concreteAddData.addAirport(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
    }

    @Then("the airport parameters are rejected, and an error code -7 is returned")
    public void airportRejectedInvalidLatitude(int id) {
        Assert.assertEquals(-7, id);
    }

    // Adding an airport with an invalid longitude

    @Given("^valid airport parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid longitude \"([^\"]*)\"$")
    public void airportInvalidLongitude(String name, String city, String country, String iata, String icao, String latitude, String altitude, String timezone, String dst, String tz, String longitude) {
        airportData = new AirportData(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        Assert.assertEquals(-8, airportData.checkValues());
    }

    @When("^adding an airport with parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) and invalid longitude \"([^\"]*)\"$")
    public void addAirportInvalidLongitude(String name, String city, String country, String iata, String icao, String latitude, String altitude, String timezone, String dst, String tz, String longitude) {
        id = concreteAddData.addAirport(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
    }

    @Then("the airport parameters are rejected, and an error code -8 is returned")
    public void airportRejectedInvalidLongitude(int id) {
        Assert.assertEquals(-8, id);
    }

    // Adding an airport with an invalid altitude

    @Given("^valid airport parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid altitude \"([^\"]*)\"$")
    public void airportInvalidAltitude(String name, String city, String country, String iata, String icao, String latitude, String longitude, String timezone, String dst, String tz, String altitude) {
        airportData = new AirportData(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        Assert.assertEquals(-9, airportData.checkValues());
    }

    @When("^adding an airport with parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) and invalid altitude \"([^\"]*)\"$")
    public void addAirportInvalidAltitude(String name, String city, String country, String iata, String icao, String latitude, String longitude, String timezone, String dst, String tz, String altitude) {
        id = concreteAddData.addAirport(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
    }

    @Then("the airport parameters are rejected, and an error code -9 is returned")
    public void airportRejectedInvalidAltitude(int id) {
        Assert.assertEquals(-9, id);
    }

    // Adding an airport with an invalid timezone

    @Given("^valid airport parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid timezone \"([^\"]*)\"$")
    public void airportInvalidTimezone(String name, String city, String country, String iata, String icao, String latitude, String longitude, String altitude, String dst, String tz, String timezone) {
        airportData = new AirportData(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        Assert.assertEquals(-10, airportData.checkValues());
    }

    @When("^adding an airport with parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) and invalid timezone \"([^\"]*)\"$")
    public void addAirportInvalidTimezone(String name, String city, String country, String iata, String icao, String latitude, String longitude, String altitude, String dst, String tz, String timezone) {
        id = concreteAddData.addAirport(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
    }

    @Then("the airport parameters are rejected, and an error code -10 is returned")
    public void airportRejectedInvalidTimezone(int id) {
        Assert.assertEquals(-10, id);
    }

    // Adding an airport with an invalid DST

    @Given("^valid airport parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid dst \"([^\"]*)\"$")
    public void airportInvalidDST(String name, String city, String country, String iata, String icao, String latitude, String longitude, String altitude, String timezone, String tz, String dst) {
        airportData = new AirportData(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        Assert.assertEquals(-11, airportData.checkValues());
    }

    @When("^adding an airport with parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) and invalid dst \"([^\"]*)\"$")
    public void addAirportInvalidDST(String name, String city, String country, String iata, String icao, String latitude, String longitude, String altitude, String timezone, String tz, String dst) {
        id = concreteAddData.addAirport(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
    }

    @Then("the airport parameters are rejected, and an error code -11 is returned")
    public void airportRejectedInvalidDST(int id) {
        Assert.assertEquals(-11, id);
    }

    // Adding an airport with an invalid TZ

    @Given("^valid airport parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid tz \"([^\"]*)\"$")
    public void airportInvalidTZ(String name, String city, String country, String iata, String icao, String latitude, String longitude, String altitude, String timezone, String dst, String tz) {
        airportData = new AirportData(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        Assert.assertEquals(-12, airportData.checkValues());
    }

    @When("^adding an airport with parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) and invalid tz \"([^\"]*)\"$")
    public void addAirportInvalidTZ(String name, String city, String country, String iata, String icao, String latitude, String longitude, String altitude, String timezone, String dst, String tz) {
        id = concreteAddData.addAirport(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
    }

    @Then("the airport parameters are rejected, and an error code -12 is returned")
    public void airportRejectedInvalidTZ(int id) {
        Assert.assertEquals(-12, id);
    }


    // Adding Flight Entries

    // Adding a valid flight entry

    @Given("^valid flight entry parameters \\(([^\"]*), \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\)$")
    public void validFlightEntryParameters(int flightID, String location_type, String location, String altitude, String latitude, String longitude) {
        flightData = new FlightData(flightID, location_type, location, altitude, latitude, longitude);
        flight_id = flightID;
        Assert.assertEquals(1, flightData.checkValues());
    }

    @When("^adding a flight entry with parameters \\(([^\"]*), \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\)$")
    public void addValidFlightEntry(int flightID, String location_type, String location, String altitude, String latitude, String longitude) {
        id = concreteAddData.addFlightEntry(flightID, location_type, location, altitude, latitude, longitude);
    }

    @Then("^the flight entry is added with flight id 1 and unique id 1$")
    public void flightEntryAdded() {
        ArrayList<Integer> expected_ids = new ArrayList<>(Arrays.asList(flightService.getNextFlightID() - 1, flightService.getMaxID()));
        ArrayList<Integer> ids = new ArrayList<>(Arrays.asList(flight_id, id));
        Assert.assertEquals(expected_ids, ids);
    }

    // Adding a flight entry with an invalid flight ID

    @Given("^valid flight entry parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid flight id ([^\"]*)$")
    public void flightEntryInvalidFlightID(String location_type, String location, String altitude, String latitude, String longitude, int flightID) {
        flightData = new FlightData(flightID, location_type, location, altitude, latitude, longitude);
        Assert.assertEquals(-2, flightData.checkValues());
    }

    @When("^adding a flight entry with parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid flight id ([^\"]*)$")
    public void addFlightEntryInvalidFlightID(String location_type, String location, String altitude, String latitude, String longitude, int flightID) {
        id = concreteAddData.addFlightEntry(flightID, location_type, location, altitude, latitude, longitude);
    }

    @Then("the flight parameters are rejected, and an error code -2 is returned")
    public void flightEntryRejectedInvalidFlightID(int id) {
        Assert.assertEquals(-2, id);
    }

    // Adding a flight entry with an invalid location type

    @Given("^valid flight entry parameters \\(([^\"]*), \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid location type \"([^\"]*)\"$")
    public void flightEntryInvalidLocationType(int flightID, String location, String altitude, String latitude, String longitude, String location_type) {
        flightData = new FlightData(flightID, location_type, location, altitude, latitude, longitude);
        Assert.assertEquals(-3, flightData.checkValues());
    }

    @When("^adding a flight entry with parameters \\(([^\"]*), \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid location type \"([^\"]*)\"$")
    public void addFlightEntryInvalidLocationType(int flightID, String location, String altitude, String latitude, String longitude, String location_type) {
        id = concreteAddData.addFlightEntry(flightID, location_type, location, altitude, latitude, longitude);
    }

    @Then("the flight parameters are rejected, and an error code -3 is returned")
    public void flightEntryRejectedInvalidLocationType(int id) {
        Assert.assertEquals(-3, id);
    }

    // Adding a flight entry with an invalid location

    @Given("^valid flight entry parameters \\(([^\"]*), \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid location \"([^\"]*)\"$")
    public void flightEntryInvalidLocation(int flightID, String location_type, String altitude, String latitude, String longitude, String location) {
        flightData = new FlightData(flightID, location_type, location, altitude, latitude, longitude);
        Assert.assertEquals(-4, flightData.checkValues());
    }

    @When("^adding a flight entry with parameters \\(([^\"]*), \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid location \"([^\"]*)\"$")
    public void addFlightEntryInvalidLocation(int flightID, String location_type, String altitude, String latitude, String longitude, String location) {
        id = concreteAddData.addFlightEntry(flightID, location_type, location, altitude, latitude, longitude);
    }

    @Then("the flight parameters are rejected, and an error code -4 is returned")
    public void flightEntryRejectedInvalidLocation(int id) {
        Assert.assertEquals(-4, id);
    }

    // Adding a flight entry with an invalid altitude

    @Given("^valid flight entry parameters \\(([^\"]*), \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid altitude \"([^\"]*)\"$")
    public void flightEntryInvalidAltitude(int flightID, String location_type, String location, String latitude, String longitude, String altitude) {
        flightData = new FlightData(flightID, location_type, location, altitude, latitude, longitude);
        Assert.assertEquals(-5, flightData.checkValues());
    }

    @When("^adding a flight entry with parameters \\(([^\"]*), \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid altitude \"([^\"]*)\"$")
    public void addFlightEntryInvalidAltitude(int flightID, String location_type, String location, String latitude, String longitude, String altitude) {
        id = concreteAddData.addFlightEntry(flightID, location_type, location, altitude, latitude, longitude);
    }

    @Then("the flight parameters are rejected, and an error code -5 is returned")
    public void flightEntryRejectedInvalidAltitude(int id) {
        Assert.assertEquals(-5, id);
    }

    // Adding a flight entry with an invalid latitude

    @Given("^valid flight entry parameters \\(([^\"]*), \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid latitude \"([^\"]*)\"$")
    public void flightEntryInvalidLatitude(int flightID, String location_type, String location, String altitude, String longitude, String latitude) {
        flightData = new FlightData(flightID, location_type, location, altitude, latitude, longitude);
        Assert.assertEquals(-6, flightData.checkValues());
    }

    @When("^adding a flight entry with parameters \\(([^\"]*), \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid latitude \"([^\"]*)\"$")
    public void addFlightEntryInvalidLatitude(int flightID, String location_type, String location, String altitude, String longitude, String latitude) {
        id = concreteAddData.addFlightEntry(flightID, location_type, location, altitude, latitude, longitude);
    }

    @Then("the flight parameters are rejected, and an error code -6 is returned")
    public void flightEntryRejectedInvalidLatitude(int id) {
        Assert.assertEquals(-6, id);
    }

    // Adding a flight entry with an invalid longitude

    @Given("^valid flight entry parameters \\(([^\"]*), \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid longitude \"([^\"]*)\"$")
    public void flightEntryInvalidLongitude(int flightID, String location_type, String location, String altitude, String latitude, String longitude) {
        flightData = new FlightData(flightID, location_type, location, altitude, latitude, longitude);
        Assert.assertEquals(-7, flightData.checkValues());
    }

    @When("^adding a flight entry with parameters \\(([^\"]*), \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid longitude \"([^\"]*)\"$")
    public void addFlightEntryInvalidLongitude(int flightID, String location_type, String location, String altitude, String latitude, String longitude) {
        id = concreteAddData.addFlightEntry(flightID, location_type, location, altitude, latitude, longitude);
    }

    @Then("the flight parameters are rejected, and an error code -7 is returned")
    public void flightEntryRejectedInvalidLongitude(int id) {
        Assert.assertEquals(-7, id);
    }


    // Adding Routes

    // Adding a valid route

    @Given("^valid route parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\)$")
    public void validRouteParameters(String airline, String source_airport, String dest_airport, String codeshare, String stops, String equipment) {
        concreteAddData.addAirport("Mount Hagen", "Mount Hagen", "Papua New Guinea", "HGU", "AYMH", "-5.826789", "144.295861", "5388", "10", "U", "Pacific/Port_Moresby");

        routeData = new RouteData(airline, source_airport, dest_airport, codeshare, stops, equipment);
        Assert.assertEquals(1, routeData.checkValues());
    }

    @When("^adding a route with parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\)$")
    public void addValidRoute(String airline, String source_airport, String dest_airport, String codeshare, String stops, String equipment) {
        id = concreteAddData.addRoute(airline, source_airport, dest_airport, codeshare, stops, equipment);
    }

    @Then("the route is added with id 1")
    public void routeAdded(int id) {
        Assert.assertEquals(routeService.getMaxID(), id);
    }

    // Adding a route with an invalid airline code

    @Given("^valid route parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid airline code \"([^\"]*)\"$")
    public void routeInvalidAirline(String source_airport, String dest_airport, String codeshare, String stops, String equipment, String airline) {
        routeData = new RouteData(airline, source_airport, dest_airport, codeshare, stops, equipment);
        Assert.assertEquals(-2, routeData.checkValues());
    }

    @When("^adding a route with parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) and invalid airline code \"([^\"]*)\"$")
    public void addRouteInvalidAirline(String source_airport, String dest_airport, String codeshare, String stops, String equipment, String airline) {
        id = concreteAddData.addRoute(airline, source_airport, dest_airport, codeshare, stops, equipment);
    }

    @Then("the route parameters are rejected, and an error code -2 is returned")
    public void routeRejectedInvalidAirline(int id) {
        Assert.assertEquals(-2, id);
    }

    // Adding a route with an invalid source airport code

    @Given("^valid route parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid source airport code \"([^\"]*)\"$")
    public void routeInvalidSourceAirport(String airline, String dest_airport, String codeshare, String stops, String equipment, String source_airport) {
        routeData = new RouteData(airline, source_airport, dest_airport, codeshare, stops, equipment);
        Assert.assertEquals(-3, routeData.checkValues());
    }

    @When("^adding a route with parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) and invalid source airport code \"([^\"]*)\"$")
    public void addRouteInvalidSourceAirport(String airline, String dest_airport, String codeshare, String stops, String equipment, String source_airport) {
        id = concreteAddData.addRoute(airline, source_airport, dest_airport, codeshare, stops, equipment);
    }

    @Then("the route parameters are rejected, and an error code -3 is returned")
    public void routeRejectedInvalidSourceAirport(int id) {
        Assert.assertEquals(-3, id);
    }

    // Adding a route with an invalid destination airport code

    @Given("^valid route parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid destination airport code \"([^\"]*)\"$")
    public void routeInvalidDestinationAirport(String airline, String source_airport, String codeshare, String stops, String equipment, String dest_airport) {
        routeData = new RouteData(airline, source_airport, dest_airport, codeshare, stops, equipment);
        Assert.assertEquals(-4, routeData.checkValues());
    }

    @When("^adding a route with parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) and invalid destination airport code \"([^\"]*)\"$")
    public void addRouteInvalidDestinationAirport(String airline, String source_airport, String codeshare, String stops, String equipment, String dest_airport) {
        id = concreteAddData.addRoute(airline, source_airport, dest_airport, codeshare, stops, equipment);
    }

    @Then("the route parameters are rejected, and an error code -4 is returned")
    public void routeRejectedInvalidDestinationAirport(int id) {
        Assert.assertEquals(-4, id);
    }

    // Adding a route with an invalid codeshare

    @Given("^valid route parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for an invalid codeshare \"([^\"]*)\"$")
    public void routeInvalidCodeshare(String airline, String source_airport, String dest_airport, String stops, String equipment, String codeshare) {
        routeData = new RouteData(airline, source_airport, dest_airport, codeshare, stops, equipment);
        Assert.assertEquals(-5, routeData.checkValues());
    }

    @When("^adding a route with parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) and invalid codeshare \"([^\"]*)\"$")
    public void addRouteInvalidCodeshare(String airline, String source_airport, String dest_airport, String stops, String equipment, String codeshare) {
        id = concreteAddData.addRoute(airline, source_airport, dest_airport, codeshare, stops, equipment);
    }

    @Then("the route parameters are rejected, and an error code -5 is returned")
    public void routeRejectedInvalidCodeshare(int id) {
        Assert.assertEquals(-5, id);
    }

    // Adding a route with invalid stops

    @Given("^valid route parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for invalid stops \"([^\"]*)\"$")
    public void routeInvalidStops(String airline, String source_airport, String dest_airport, String codeshare, String equipment, String stops) {
        routeData = new RouteData(airline, source_airport, dest_airport, codeshare, stops, equipment);
        Assert.assertEquals(-6, routeData.checkValues());
    }

    @When("^adding a route with parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) and invalid stops \"([^\"]*)\"$")
    public void addRouteInvalidStops(String airline, String source_airport, String dest_airport, String codeshare, String equipment, String stops) {
        id = concreteAddData.addRoute(airline, source_airport, dest_airport, codeshare, stops, equipment);
    }

    @Then("the route parameters are rejected, and an error code -6 is returned")
    public void routeRejectedInvalidStops(int id) {
        Assert.assertEquals(-6, id);
    }

    // Adding a route with invalid equipment

    @Given("^valid route parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) except for invalid equipment \"([^\"]*)\"$")
    public void routeInvalidEquipment(String airline, String source_airport, String dest_airport, String codeshare, String stops, String equipment) {
        routeData = new RouteData(airline, source_airport, dest_airport, codeshare, stops, equipment);
        Assert.assertEquals(-7, routeData.checkValues());
    }

    @When("^adding a route with parameters \\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\) and invalid equipment \"([^\"]*)\"$")
    public void addRouteInvalidEquipment(String airline, String source_airport, String dest_airport, String codeshare, String stops, String equipment) {
        id = concreteAddData.addRoute(airline, source_airport, dest_airport, codeshare, stops, equipment);
    }

    @Then("the route parameters are rejected, and an error code -7 is returned")
    public void routeRejectedInvalidEquipment(int id) {
        Assert.assertEquals(-7, id);
    }

}
