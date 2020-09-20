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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class AddDataScenario {

    private int id;
    //private int flightID;
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
        String filename = "test.db";
        File dbFile = new File(filename);

        DBInitializer.createNewDatabase(filename);

        DBConnection.setDatabaseFile(dbFile);

        concreteAddData = new ConcreteAddData();
        airlineService = new AirlineService();
        airportService = new AirportService();
        flightService = new FlightService();
        routeService = new RouteService();
    }

    @After
    public void teardown() {
        try {
            File dbFile = new File("test.db");
            Connection con = DBConnection.getConnection();
            con.close();

            boolean result = dbFile.delete();

            if (result) {
                System.out.println("DB deleted.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    // Adding Airlines

    //  Adding a valid airline

    @Given("airline with parameters {string}, {string}, {string}, {string}, {string}, {string}, {string} does not exist")
    public void validAirlineParameters(String name, String alias, String iata, String icao, String callsign, String country, String active) {
        airlineData = new AirlineData(name, alias, iata, icao, callsign, country, active);
        airlineData.convertBlanksToNull();
        Assert.assertEquals(1, airlineData.checkValues());
    }

    @When("adding an airline with parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}")
    public void addValidAirline(String name, String alias, String iata, String icao, String callsign, String country, String active) {
        id = concreteAddData.addAirline(name, alias, iata, icao, callsign, country, active);
        Assert.assertEquals(airlineService.getMaxID(), id);
    }

    @Then("the airline is added with iata {string} and icao {string}")
    public void airlineAdded(String iata, String icao) {
        Assert.assertTrue(airlineService.airlineExists(iata));
        Assert.assertTrue(airlineService.airlineExists(icao));
    }

    // Adding an airline with an invalid name

    @Given("valid airline parameters {string}, {string}, {string}, {string}, {string}, {string} except for an invalid name {string}")
    public void airlineInvalidName(String alias, String iata, String icao, String callsign, String country, String active, String name) {
        airlineData = new AirlineData(name, alias, iata, icao, callsign, country, active);
        airlineData.convertBlanksToNull();
        Assert.assertEquals(-2, airlineData.checkValues());
    }

    @When("adding an airline with parameters {string}, {string}, {string}, {string}, {string}, {string} and invalid name {string}")
    public void addAirlineInvalidName(String alias, String iata, String icao, String callsign, String country, String active, String name) {
        id = concreteAddData.addAirline(name, alias, iata, icao, callsign, country, active);
        Assert.assertEquals(-2, id);
    }

    // Adding an airline with an invalid IATA code

    @Given("valid airline parameters {string}, {string}, {string}, {string}, {string}, {string} except for an invalid iata code {string}")
    public void airlineInvalidIATA(String name, String alias, String icao, String callsign, String country, String active, String iata) {
        airlineData = new AirlineData(name, alias, iata, icao, callsign, country, active);
        airlineData.convertBlanksToNull();
        Assert.assertEquals(-3, airlineData.checkValues());
    }

    @When("adding an airline with parameters {string}, {string}, {string}, {string}, {string}, {string} and invalid iata code {string}")
    public void addAirlineInvalidIATA(String name, String alias, String icao, String callsign, String country, String active, String iata) {
        id = concreteAddData.addAirline(name, alias, iata, icao, callsign, country, active);
        Assert.assertEquals(-3, id);
    }

    @Then("the airline parameters are rejected, and the airline is not added with icao {string}")
    public void airlineRejectedInvalidIATA(String icao) {
        Assert.assertFalse(airlineService.airlineExists(icao));
    }

    // Adding an airline with an invalid ICAO code

    @Given("valid airline parameters {string}, {string}, {string}, {string}, {string}, {string} except for an invalid icao code {string}")
    public void airlineInvalidICAO(String name, String alias, String iata, String callsign, String country, String active, String icao) {
        airlineData = new AirlineData(name, alias, iata, icao, callsign, country, active);
        airlineData.convertBlanksToNull();
        Assert.assertEquals(-4, airlineData.checkValues());
    }

    @When("adding an airline with parameters {string}, {string}, {string}, {string}, {string}, {string} and invalid icao code {string}")
    public void addAirlineInvalidICAO(String name, String alias, String iata, String callsign, String country, String active, String icao) {
        id = concreteAddData.addAirline(name, alias, iata, callsign, country, active, icao);
        Assert.assertEquals(-4, id);
    }

    @Then("the airline parameters are rejected, and the airline is not added with iata {string}")
    public void airlineRejectedInvalidICAO(String iata) {
        Assert.assertFalse(airlineService.airlineExists(iata));
    }

    // Adding an airline with an invalid active

    @Given("valid airline parameters {string}, {string}, {string}, {string}, {string}, {string} except for an invalid active {string}")
    public void airlineInvalidActive(String name, String alias, String iata, String icao, String callsign, String country, String active) {
        airlineData = new AirlineData(name, alias, iata, icao, callsign, country, active);
        airlineData.convertBlanksToNull();
        Assert.assertEquals(-5, airlineData.checkValues());
    }

    @When("adding an airline with parameters {string}, {string}, {string}, {string}, {string}, {string} and invalid active {string}")
    public void addAirlineInvalidActive(String name, String alias, String iata, String icao, String callsign, String country, String active) {
        id = concreteAddData.addAirline(name, alias, iata, icao, callsign, country, active);
        Assert.assertEquals(-5, id);
    }

    @Then("the airline parameters are rejected, and the airline is not added with iata {string} and icao {string}")
    public void airlineRejectedInvalid(String iata, String icao) {
        Assert.assertFalse(airlineService.airlineExists(iata));
        Assert.assertFalse(airlineService.airlineExists(icao));
    }


    // Adding Airports

    // Adding a valid airport

    @Given("valid airport parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}")
    public void validAirportParameters(String name, String city, String country, String iata, String icao, String latitude, String longitude, String altitude, String timezone, String dst, String tz) {
        airportData = new AirportData(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        airportData.convertBlanksToNull();
        Assert.assertEquals(1, airportData.checkValues());
    }

    @When("adding an airport with parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}")
    public void addValidAirport(String name, String city, String country, String iata, String icao, String latitude, String longitude, String altitude, String timezone, String dst, String tz) {
        id = concreteAddData.addAirport(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        Assert.assertEquals(airportService.getMaxID(), id);
    }

    @Then("the airport is added with iata {string} and icao {string}")
    public void airportAdded(String iata, String icao) {
        Assert.assertTrue(airportService.dataExists(iata));
        Assert.assertTrue(airportService.dataExists(icao));
    }

    // Adding an airport with an invalid name

    @Given("valid airport parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string} except for an invalid name {string}")
    public void airportInvalidName(String city, String country, String iata, String icao, String latitude, String longitude, String altitude, String timezone, String dst, String tz, String name) {
        airportData = new AirportData(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        airportData.convertBlanksToNull();
        Assert.assertEquals(-2, airportData.checkValues());
    }

    @When("adding an airport with parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string} and invalid name {string}")
    public void addAirportInvalidName(String city, String country, String iata, String icao, String latitude, String longitude, String altitude, String timezone, String dst, String tz, String name) {
        id = concreteAddData.addAirport(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        Assert.assertEquals(-2, id);
    }

    // Adding an airport with an invalid city

    @Given("valid airport parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string} except for an invalid city {string}")
    public void airportInvalidCity(String name, String country, String iata, String icao, String latitude, String longitude, String altitude, String timezone, String dst, String tz, String city) {
        airportData = new AirportData(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        airportData.convertBlanksToNull();
        Assert.assertEquals(-3, airportData.checkValues());
    }

    @When("adding an airport with parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string} and invalid city {string}")
    public void addAirportInvalidCity(String name, String country, String iata, String icao, String latitude, String longitude, String altitude, String timezone, String dst, String tz, String city) {
        id = concreteAddData.addAirport(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        Assert.assertEquals(-3, id);
    }

    // Adding an airport with an invalid country

    @Given("valid airport parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string} except for an invalid country {string}")
    public void airportInvalidCountry(String name, String city, String iata, String icao, String latitude, String longitude, String altitude, String timezone, String dst, String tz, String country) {
        airportData = new AirportData(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        airportData.convertBlanksToNull();
        Assert.assertEquals(-4, airportData.checkValues());
    }

    @When("adding an airport with parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string} and invalid country {string}")
    public void addAirportInvalidCountry(String name, String city, String iata, String icao, String latitude, String longitude, String altitude, String timezone, String dst, String tz, String country) {
        id = concreteAddData.addAirport(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        Assert.assertEquals(-4, id);
    }

    // Adding an airport with an invalid IATA code

    @Given("valid airport parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string} except for an invalid iata code {string}")
    public void airportInvalidIATA(String name, String city, String country, String icao, String latitude, String longitude, String altitude, String timezone, String dst, String tz, String iata) {
        airportData = new AirportData(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        airportData.convertBlanksToNull();
        Assert.assertEquals(-5, airportData.checkValues());
    }

    @When("adding an airport with parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string} and invalid iata code {string}")
    public void addAirportInvalidIATA(String name, String city, String country, String icao, String latitude, String longitude, String altitude, String timezone, String dst, String tz, String iata) {
        id = concreteAddData.addAirport(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        Assert.assertEquals(-5, id);
    }

    @Then("the airport parameters are rejected, and the airport is not added with icao {string}")
    public void airportRejectedInvalidIATA(String icao) {
        Assert.assertFalse(airportService.dataExists(icao));
    }

    // Adding an airport with an invalid ICAO code

    @Given("valid airport parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string} except for an invalid icao code {string}")
    public void airportInvalidICAO(String name, String city, String country, String iata, String latitude, String longitude, String altitude, String timezone, String dst, String tz, String icao) {
        airportData = new AirportData(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        airportData.convertBlanksToNull();
        Assert.assertEquals(-6, airportData.checkValues());
    }

    @When("adding an airport with parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string} and invalid icao code {string}")
    public void addAirportInvalidICAO(String name, String city, String country, String iata, String latitude, String longitude, String altitude, String timezone, String dst, String tz, String icao) {
        id = concreteAddData.addAirport(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        Assert.assertEquals(-6, id);
    }

    @Then("the airport parameters are rejected, and the airport is not added with iata {string}")
    public void airportRejectedInvalidICAO(String iata) {
        Assert.assertFalse(airportService.dataExists(iata));
    }

    // Adding an airport with an invalid latitude

    @Given("valid airport parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string} except for an invalid latitude {string}")
    public void airportInvalidLatitude(String name, String city, String country, String iata, String icao, String longitude, String altitude, String timezone, String dst, String tz, String latitude) {
        airportData = new AirportData(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        airportData.convertBlanksToNull();
        Assert.assertEquals(-7, airportData.checkValues());
    }

    @When("adding an airport with parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string} and invalid latitude {string}")
    public void addAirportInvalidLatitude(String name, String city, String country, String iata, String icao, String longitude, String altitude, String timezone, String dst, String tz, String latitude) {
        id = concreteAddData.addAirport(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        Assert.assertEquals(-7, id);
    }

    // Adding an airport with an invalid longitude

    @Given("valid airport parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string} except for an invalid longitude {string}")
    public void airportInvalidLongitude(String name, String city, String country, String iata, String icao, String latitude, String altitude, String timezone, String dst, String tz, String longitude) {
        airportData = new AirportData(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        airportData.convertBlanksToNull();
        Assert.assertEquals(-8, airportData.checkValues());
    }

    @When("adding an airport with parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string} and invalid longitude {string}")
    public void addAirportInvalidLongitude(String name, String city, String country, String iata, String icao, String latitude, String altitude, String timezone, String dst, String tz, String longitude) {
        id = concreteAddData.addAirport(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        Assert.assertEquals(-8, id);
    }

    // Adding an airport with an invalid altitude

    @Given("valid airport parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string} except for an invalid altitude {string}")
    public void airportInvalidAltitude(String name, String city, String country, String iata, String icao, String latitude, String longitude, String timezone, String dst, String tz, String altitude) {
        airportData = new AirportData(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        airportData.convertBlanksToNull();
        Assert.assertEquals(-9, airportData.checkValues());
    }

    @When("adding an airport with parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string} and invalid altitude {string}")
    public void addAirportInvalidAltitude(String name, String city, String country, String iata, String icao, String latitude, String longitude, String timezone, String dst, String tz, String altitude) {
        id = concreteAddData.addAirport(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        Assert.assertEquals(-9, id);
    }

    // Adding an airport with an invalid timezone

    @Given("valid airport parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string} except for an invalid timezone {string}")
    public void airportInvalidTimezone(String name, String city, String country, String iata, String icao, String latitude, String longitude, String altitude, String dst, String tz, String timezone) {
        airportData = new AirportData(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        airportData.convertBlanksToNull();
        Assert.assertEquals(-10, airportData.checkValues());
    }

    @When("adding an airport with parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string} and invalid timezone {string}")
    public void addAirportInvalidTimezone(String name, String city, String country, String iata, String icao, String latitude, String longitude, String altitude, String dst, String tz, String timezone) {
        id = concreteAddData.addAirport(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        Assert.assertEquals(-10, id);
    }

    // Adding an airport with an invalid DST

    @Given("valid airport parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string} except for an invalid dst {string}")
    public void airportInvalidDST(String name, String city, String country, String iata, String icao, String latitude, String longitude, String altitude, String timezone, String tz, String dst) {
        airportData = new AirportData(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        airportData.convertBlanksToNull();
        Assert.assertEquals(-11, airportData.checkValues());
    }

    @When("adding an airport with parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string} and invalid dst {string}")
    public void addAirportInvalidDST(String name, String city, String country, String iata, String icao, String latitude, String longitude, String altitude, String timezone, String tz, String dst) {
        id = concreteAddData.addAirport(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        Assert.assertEquals(-11, id);
    }

    // Adding an airport with an invalid TZ

    @Given("valid airport parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string} except for an invalid tz {string}")
    public void airportInvalidTZ(String name, String city, String country, String iata, String icao, String latitude, String longitude, String altitude, String timezone, String dst, String tz) {
        airportData = new AirportData(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        airportData.convertBlanksToNull();
        Assert.assertEquals(-12, airportData.checkValues());
    }

    @When("adding an airport with parameters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string} and invalid tz {string}")
    public void addAirportInvalidTZ(String name, String city, String country, String iata, String icao, String latitude, String longitude, String altitude, String timezone, String dst, String tz) {
        id = concreteAddData.addAirport(name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);
        Assert.assertEquals(-12, id);
    }

    @Then("the airport parameters are rejected, and the airport is not added with iata {string} and icao {string}")
    public void airportRejectedInvalid(String iata, String icao) {
        Assert.assertFalse(airportService.dataExists(iata));
        Assert.assertFalse(airportService.dataExists(icao));
    }


    // Adding Flight Entries

    // Adding a valid flight entry

    @Given("valid flight entry parameters {int}, {string}, {string}, {string}, {string}, {string}")
    public void validFlightEntryParameters(int flightID, String locationType, String location, String altitude, String latitude, String longitude) {
        concreteAddData.addAirport("Christchurch Airport", "Christchurch", "New Zealand", "CHC", "NZCH", "100.0231", "-34.1271", "0", "2", "N", "Auckland/New Zealand");

        flightData = new FlightData(flightID, locationType, location, altitude, latitude, longitude);
        flightData.convertBlanksToNull();
        //flightID = flightID;
        Assert.assertEquals(1, flightData.checkValues());
    }

    @When("adding a flight entry with parameters {int}, {string}, {string}, {string}, {string}, {string}")
    public void addValidFlightEntry(int flightID, String locationType, String location, String altitude, String latitude, String longitude) {
        id = concreteAddData.addFlightEntry(flightID, locationType, location, altitude, latitude, longitude);
    }

    @Then("the flight entry is added with flight id {int} and unique id 1")
    public void flightEntryAdded(int flightID) {
        ArrayList<Integer> expectedIDs = new ArrayList<>(Arrays.asList(flightService.getNextFlightID() - 1, flightService.getMaxID()));
        ArrayList<Integer> ids = new ArrayList<>(Arrays.asList(flightID, id));
        Assert.assertEquals(expectedIDs, ids);
    }

    // Adding a flight entry with an invalid flight ID

    @Given("valid flight entry parameters {string}, {string}, {string}, {string}, {string} except for an invalid flight id {int}")
    public void flightEntryInvalidFlightID(String locationType, String location, String altitude, String latitude, String longitude, int flightID) {
        flightData = new FlightData(flightID, locationType, location, altitude, latitude, longitude);
        flightData.convertBlanksToNull();
        Assert.assertEquals(-2, flightData.checkValues());
    }

    @When("adding a flight entry with parameters {string}, {string}, {string}, {string}, {string} and invalid flight id {int}")
    public void addFlightEntryInvalidFlightID(String locationType, String location, String altitude, String latitude, String longitude, int flightID) {
        id = concreteAddData.addFlightEntry(flightID, locationType, location, altitude, latitude, longitude);
    }

    // Adding a flight entry with an invalid location type

    @Given("valid flight entry parameters {int}, {string}, {string}, {string}, {string} except for an invalid location type {string}")
    public void flightEntryInvalidLocationType(int flightID, String location, String altitude, String latitude, String longitude, String locationType) {
        flightData = new FlightData(flightID, locationType, location, altitude, latitude, longitude);
        flightData.convertBlanksToNull();
        Assert.assertEquals(-3, flightData.checkValues());
    }

    @When("adding a flight entry with parameters {int}, {string}, {string}, {string}, {string} and invalid location type {string}")
    public void addFlightEntryInvalidLocationType(int flightID, String location, String altitude, String latitude, String longitude, String locationType) {
        id = concreteAddData.addFlightEntry(flightID, locationType, location, altitude, latitude, longitude);
    }

    // Adding a flight entry with an invalid location

    @Given("valid flight entry parameters {int}, {string}, {string}, {string}, {string} except for an invalid location {string}")
    public void flightEntryInvalidLocation(int flightID, String locationType, String altitude, String latitude, String longitude, String location) {
        flightData = new FlightData(flightID, locationType, location, altitude, latitude, longitude);
        flightData.convertBlanksToNull();
        Assert.assertFalse(airportService.dataExists(location));
        Assert.assertEquals(-4, flightData.checkValues());
    }

    @When("adding a flight entry with parameters {int}, {string}, {string}, {string}, {string} and invalid location {string}")
    public void addFlightEntryInvalidLocation(int flightID, String locationType, String altitude, String latitude, String longitude, String location) {
        id = concreteAddData.addFlightEntry(flightID, locationType, location, altitude, latitude, longitude);
    }

    // Adding a flight entry with an invalid altitude

    @Given("valid flight entry parameters {int}, {string}, {string}, {string}, {string} except for an invalid altitude {string}")
    public void flightEntryInvalidAltitude(int flightID, String locationType, String location, String latitude, String longitude, String altitude) {
        concreteAddData.addAirport("Christchurch Airport", "Christchurch", "New Zealand", "CHC", "NZCH", "100.0231", "-34.1271", "0", "2", "N", "Auckland/New Zealand");

        flightData = new FlightData(flightID, locationType, location, altitude, latitude, longitude);
        flightData.convertBlanksToNull();
        Assert.assertEquals(-5, flightData.checkValues());
    }

    @When("adding a flight entry with parameters {int}, {string}, {string}, {string}, {string} and invalid altitude {string}")
    public void addFlightEntryInvalidAltitude(int flightID, String locationType, String location, String latitude, String longitude, String altitude) {
        id = concreteAddData.addFlightEntry(flightID, locationType, location, altitude, latitude, longitude);
    }

    // Adding a flight entry with an invalid latitude

    @Given("valid flight entry parameters {int}, {string}, {string}, {string}, {string} except for an invalid latitude {string}")
    public void flightEntryInvalidLatitude(int flightID, String locationType, String location, String altitude, String longitude, String latitude) {
        flightData = new FlightData(flightID, locationType, location, altitude, latitude, longitude);
        flightData.convertBlanksToNull();
        Assert.assertEquals(-6, flightData.checkValues());
    }

    @When("adding a flight entry with parameters {int}, {string}, {string}, {string}, {string} and invalid latitude {string}")
    public void addFlightEntryInvalidLatitude(int flightID, String locationType, String location, String altitude, String longitude, String latitude) {
        id = concreteAddData.addFlightEntry(flightID, locationType, location, altitude, latitude, longitude);
    }

    // Adding a flight entry with an invalid longitude

    @Given("valid flight entry parameters {int}, {string}, {string}, {string}, {string} except for an invalid longitude {string}")
    public void flightEntryInvalidLongitude(int flightID, String locationType, String location, String altitude, String latitude, String longitude) {
        flightData = new FlightData(flightID, locationType, location, altitude, latitude, longitude);
        flightData.convertBlanksToNull();
        Assert.assertEquals(-7, flightData.checkValues());
    }

    @When("adding a flight entry with parameters {int}, {string}, {string}, {string}, {string} and invalid longitude {string}")
    public void addFlightEntryInvalidLongitude(int flightID, String locationType, String location, String altitude, String latitude, String longitude) {
        id = concreteAddData.addFlightEntry(flightID, locationType, location, altitude, latitude, longitude);
    }

    @Then("the flight parameters are rejected, and an error code {int} is returned")
    public void flightEntryRejectedInvalid(int error) {
        Assert.assertEquals(error, id);
    }


    // Adding Routes

    // Adding a valid route

    @Given("valid route parameters {string}, {string}, {string}, {string}, {string}, {string}")
    public void validRouteParameters(String airline, String sourceAirport, String destAirport, String codeshare, String stops, String equipment) {
        concreteAddData.addAirport("Christchurch Airport", "Christchurch", "New Zealand", "CHC", "NZCH", "100.0231", "-34.1271", "0", "2", "N", "Auckland/New Zealand");
        concreteAddData.addAirport("Mount Hagen", "Mount Hagen", "Papua New Guinea", "HGU", "AYMH", "-5.826789", "144.295861", "5388", "10", "U", "Pacific/Port_Moresby");
        concreteAddData.addAirline("Airfix Aviation", "", "", "FIX", "AIRFIX", "Finland", "Y");

        routeData = new RouteData(airline, sourceAirport, destAirport, codeshare, stops, equipment);
        routeData.convertBlanksToNull();
        Assert.assertEquals(1, routeData.checkValues());
    }

    @When("adding a route with parameters {string}, {string}, {string}, {string}, {string}, {string}")
    public void addValidRoute(String airline, String sourceAirport, String destAirport, String codeshare, String stops, String equipment) {
        id = concreteAddData.addRoute(airline, sourceAirport, destAirport, codeshare, stops, equipment);
    }

    @Then("the route is added with id 1")
    public void routeAdded() {
        Assert.assertEquals(routeService.getMaxID(), id);
    }

    // Adding a route with an invalid airline code

    @Given("valid route parameters {string}, {string}, {string}, {string}, {string} except for an invalid airline code {string}")
    public void routeInvalidAirline(String sourceAirport, String destAirport, String codeshare, String stops, String equipment, String airline) {
        routeData = new RouteData(airline, sourceAirport, destAirport, codeshare, stops, equipment);
        routeData.convertBlanksToNull();
        Assert.assertEquals(-2, routeData.checkValues());
    }

    @When("adding a route with parameters {string}, {string}, {string}, {string}, {string} and invalid airline code {string}")
    public void addRouteInvalidAirline(String sourceAirport, String destAirport, String codeshare, String stops, String equipment, String airline) {
        id = concreteAddData.addRoute(airline, sourceAirport, destAirport, codeshare, stops, equipment);
    }

    // Adding a route with an invalid source airport code

    @Given("valid route parameters {string}, {string}, {string}, {string}, {string} except for an invalid source airport code {string}")
    public void routeInvalidSourceAirport(String airline, String destAirport, String codeshare, String stops, String equipment, String sourceAirport) {
        routeData = new RouteData(airline, sourceAirport, destAirport, codeshare, stops, equipment);
        routeData.convertBlanksToNull();
        Assert.assertEquals(-3, routeData.checkValues());
    }

    @When("adding a route with parameters {string}, {string}, {string}, {string}, {string} and invalid source airport code {string}")
    public void addRouteInvalidSourceAirport(String airline, String destAirport, String codeshare, String stops, String equipment, String sourceAirport) {
        id = concreteAddData.addRoute(airline, sourceAirport, destAirport, codeshare, stops, equipment);
    }

    // Adding a route with an invalid destination airport code

    @Given("valid route parameters {string}, {string}, {string}, {string}, {string} except for an invalid destination airport code {string}")
    public void routeInvalidDestinationAirport(String airline, String sourceAirport, String codeshare, String stops, String equipment, String destAirport) {
        routeData = new RouteData(airline, sourceAirport, destAirport, codeshare, stops, equipment);
        routeData.convertBlanksToNull();
        Assert.assertEquals(-4, routeData.checkValues());
    }

    @When("adding a route with parameters {string}, {string}, {string}, {string}, {string} and invalid destination airport code {string}")
    public void addRouteInvalidDestinationAirport(String airline, String sourceAirport, String codeshare, String stops, String equipment, String destAirport) {
        id = concreteAddData.addRoute(airline, sourceAirport, destAirport, codeshare, stops, equipment);
    }

    // Adding a route with an invalid codeshare

    @Given("valid route parameters {string}, {string}, {string}, {string}, {string} except for an invalid codeshare {string}")
    public void routeInvalidCodeshare(String airline, String sourceAirport, String destAirport, String stops, String equipment, String codeshare) {
        routeData = new RouteData(airline, sourceAirport, destAirport, codeshare, stops, equipment);
        routeData.convertBlanksToNull();
        Assert.assertEquals(-5, routeData.checkValues());
    }

    @When("adding a route with parameters {string}, {string}, {string}, {string}, {string} and invalid codeshare {string}")
    public void addRouteInvalidCodeshare(String airline, String sourceAirport, String destAirport, String stops, String equipment, String codeshare) {
        id = concreteAddData.addRoute(airline, sourceAirport, destAirport, codeshare, stops, equipment);
    }

    // Adding a route with invalid stops

    @Given("valid route parameters {string}, {string}, {string}, {string}, {string} except for invalid stops {string}")
    public void routeInvalidStops(String airline, String sourceAirport, String destAirport, String codeshare, String equipment, String stops) {
        routeData = new RouteData(airline, sourceAirport, destAirport, codeshare, stops, equipment);
        routeData.convertBlanksToNull();
        Assert.assertEquals(-6, routeData.checkValues());
    }

    @When("adding a route with parameters {string}, {string}, {string}, {string}, {string} and invalid stops {string}")
    public void addRouteInvalidStops(String airline, String sourceAirport, String destAirport, String codeshare, String equipment, String stops) {
        id = concreteAddData.addRoute(airline, sourceAirport, destAirport, codeshare, stops, equipment);
    }

    // Adding a route with invalid equipment

    @Given("valid route parameters {string}, {string}, {string}, {string}, {string} except for invalid equipment {string}")
    public void routeInvalidEquipment(String airline, String sourceAirport, String destAirport, String codeshare, String stops, String equipment) {
        routeData = new RouteData(airline, sourceAirport, destAirport, codeshare, stops, equipment);
        routeData.convertBlanksToNull();
        Assert.assertEquals(-7, routeData.checkValues());
    }

    @When("adding a route with parameters {string}, {string}, {string}, {string}, {string} and invalid equipment {string}")
    public void addRouteInvalidEquipment(String airline, String sourceAirport, String destAirport, String codeshare, String stops, String equipment) {
        id = concreteAddData.addRoute(airline, sourceAirport, destAirport, codeshare, stops, equipment);
    }

    @Then("the route parameters are rejected, and an error code {int} is returned")
    public void routeRejectedInvalid(int error) {
        Assert.assertEquals(error, id);
    }

}
