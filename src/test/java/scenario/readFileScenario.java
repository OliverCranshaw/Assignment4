package scenario;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.After;
import org.junit.Assert;
import seng202.team5.data.ReadFile;
import seng202.team5.database.DBTableInitializer;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class readFileScenario {

    private ReadFile readFile = new ReadFile();
    private String string;
    private String string_without_quotes;
    private ArrayList<String> splitLine;
    private ArrayList<String> expected;
    private ArrayList<Integer> expected_ids;
    private ArrayList<Integer> ids;
    private int id;
    private File airlineFile;
    private File airportFile;
    private File flightFile;
    private File routeFile;
    private static Connection con;

    @Before
    public static void setup() {
        String url = "jdbc:sqlite:test.db";
        DBTableInitializer tableInitializer = new DBTableInitializer();

        try (Connection tmp = DriverManager.getConnection(url)) {
            con = tmp;
            if (con != null) {
                DatabaseMetaData meta = con.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("DB created.");
                tableInitializer.initializeTables(url);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @After
    public static void teardown() {
        File db = new File("test.db");
        try {
            if (con != null) {
                con.close();
                db.delete();
                System.out.println("DB deleted.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // "\"\""
    // ""
    // "\\N"
    // "\"New Zealand\""
    @Given("^a string (.*) \"(.*)\"$")
    public void aString(String givenString) {
        string = givenString;
    }

    @When("^remove quotes (.*)$")
    public void removeQuotes() {
        string_without_quotes = readFile.removeQuotes(string);
    }

    @Then("^the string (.*)$")
    public void checkString(String string_without_quotes) {
        Assert.assertEquals(string.replaceAll("\"", ""), string_without_quotes);
    }



    @Given("normal data with no blank entries and all with quotation marks")
    public void normalData() {
        string = "\"Goroka\",\"Goroka\",\"Papua New Guinea\"";
    }

    @When("^splitting (.*)$")
    public void splitData() {
        splitLine = readFile.getEntries(string);
    }

    @Then("the data is split into separate entries, all with quotation marks removed")
    public void separatedNormalData() {
        expected = new ArrayList<>(Arrays.asList("Goroka", "Goroka", "Papua New Guinea"));
        Assert.assertEquals(expected, splitLine);
    }

    @Given("data with some blank entries, and not all entries having quotation marks")
    public void abnormalData() {
        string = "2,\"135 Airways\",\\N,\"\",,\"GNL\",\"GENERAL\",\"United States\",\"N\"";
    }

    @Then("the data is split into separate entries including any blank spaces, any entries with quotation marks now have them removed")
    public void separatedAbnormalData() {
        expected = new ArrayList<>(Arrays.asList("2", "135 Airways", "\\N", "", "", "GNL", "GENERAL", "United States", "N"));
        Assert.assertEquals(expected, splitLine);
    }

    @Given("data with only blank entries")
    public void blankData() {
        string = ",,,";
    }

    @Then("the data is split into separate empty strings")
    public void separatedBlankData() {
        expected = new ArrayList<>(Arrays.asList("", "", "", ""));
        Assert.assertEquals(expected, splitLine);
    }

    @Given("data with no commas but with quotation marks")
    public void singleEntryQuotes() {
        string = "\"Papua New Guinea\"";
    }

    @Then("the data is one string with quotation marks removed")
    public void singleEntry() {
        expected = new ArrayList<>(Arrays.asList("Papua New Guinea"));
        Assert.assertEquals(expected, splitLine);
    }

    @Given("data with no commas and no quotation marks")
    public void singleEntryNoQuotes() {
        string = "Papua New Guinea";
    }

    @Then("the data is one string, the same as what was input")
    public void sameSingleEntry() {
        expected = new ArrayList<>(Arrays.asList("Papua New Guinea"));
        Assert.assertEquals(expected, splitLine);
    }

    @Given("empty data with no commas")
    public void emptyData() {
        string = "";
    }

    @Then("the data is one empty string")
    public void separatedEmptyString() {
        expected = new ArrayList<>(Arrays.asList(""));
        Assert.assertEquals(expected, splitLine);
    }



    @Given("a file with airline data with 8 entries")
    public void setNormalAirlineIDFile() {
        airlineFile = new File("normal_airline_with_id.txt");
    }

    @When("^reading(.*)airline data(.*)$")
    public void readAirline(File file) {
        id = readFile.readAirlineData(file);
    }

    @Then("^(.*) a(n)* (.*) is added with id 1$")
    public void dataAdded() {
        Assert.assertEquals(1, id);
    }

    @Given("a file with airline data with 7 entries")
    public void setNormalAirlineFile() {
        airlineFile = new File("normal_airline.txt");
    }

    @Given("a file with airline data with an airline id, 8 entries, and abnormal entries such as blank spaces, N/A, etc.")
    public void setAbnormalAirlineFile() {
        airlineFile = new File("abnormal_airline_with_id.txt");
    }

    @Given("a file with airline data with too few entries, i.e. less than 7 entries")
    public void setTooFewEntriesAirlineFile() {
        airlineFile = new File("airline_too_few_entries.txt");
    }

    @Then("^the (.*) data is rejected, and the user is told that their data has too few entries$")
    public void rejectedTooFewEntries() {
        Assert.assertEquals(-2, id);
    }

    @Given("a file with airline data with too many entries, i.e. more than 8 entries")
    public void setTooManyEntriesAirlineFile() {
        airlineFile = new File("airline_too_many_entries.txt");
    }

    @Then("^the (.*?) data is rejected, and the user is told that their data (.*)$")
    public void rejectedTooManyEntries() {
        Assert.assertEquals(-3, id);
    }

    @Given("a file with multiple airlines, i.e. 5, of normal airline data, i.e. airline data with 7 or 8 entries")
    public void setNormalAirlinesFile() {
        airlineFile = new File("normal_airlines_multiple.txt");
    }

    @Then("^each line is split with quotes removed, and adds each (.*) to the database with an incrementing id, i.e. last id is 5$")
    public void normalMultipleAdded() {
        Assert.assertEquals(5, id);
    }

    @Given("a file with multiple airlines, i.e. 5, where the first and third lines have the incorrect number of entries")
    public void setAbnormalAirlinesFile() {
        airlineFile = new File("abnormal_airlines_multiple.txt");
    }

    @Then("^each line is split with quotes removed, and adds each (.*) except for the ones with the incorrect number of entries to the database with incrementing ids, i.e. last id is 3$")
    public void abnormalMultipleAdded() {
        Assert.assertEquals(3, id);
    }



    @Given("a file with airport data with 12 entries")
    public void setNormalAirportIDFile() {
        airportFile = new File("normal_airport_with_id.txt");
    }

    @When("^reading(.*)airport data(.*)$")
    public void readAirport(File file) {
        id = readFile.readAirportData(file);
    }

    @Given("a file with airport data with 11 entries")
    public void setNormalAirportFile() {
        airportFile = new File("normal_airport.txt");
    }

    @Given("a file with airport data with an airport id, 12 entries, and abnormal entries such as blank spaces")
    public void setAbnormalAirportFile() {
        airportFile = new File("abnormal_airport_with_id.txt");
    }

    @Given("a file with airport data with too few entries, i.e. less than 11 entries")
    public void setTooFewEntriesAirportFile() {
        airportFile = new File("airport_too_few_entries.txt");
    }

    @Given("a file with airport data with too many entries, i.e. more than 12 entries")
    public void setTooManyEntriesAirportFile() {
        airportFile = new File("airport_too_many_entries.txt");
    }

    @Given("a file with multiple airports, i.e. 5, of normal airport data, i.e. airport data with 11 or 12 entries")
    public void setNormalAirportsFile() {
        airportFile = new File("normal_airports_multiple.txt");
    }

    @Given("a file with multiple airports, i.e. 5, where the first and third lines have the incorrect number of entries")
    public void setAbnormalAirportsFile() {
        airportFile = new File("abnormal_airports_multiple.txt");
    }



    @Given("a file with a single flight entry with 5 entries")
    public void setNormalFlightEntryFile() {
        flightFile = new File("normal_flight_entry.txt");
    }

    @And("^the(.*)airline(s)* and airport(.*)exist$")
    public void setupAirportsAirlines() {
        airlineFile = new File("airlines.txt");
        airportFile = new File("airports.txt");

        readFile.readAirlineData(airlineFile);
        readFile.readAirportData(airportFile);
    }

    @When("^reading(.*)flight data(.*)$")
    public void readFlight(File file) {
        ids = readFile.readFlightData(file);
    }

    @Then("the flight data is split into the 5 entries, with quotes removed, and a flight entry is added with id 1 and flight id 1")
    public void addedFlightEntry() {
        expected_ids = new ArrayList<>(Arrays.asList(1, 1));
        Assert.assertEquals(expected_ids, ids);
    }

    @Given("a file with multiple flight data entries, each with 5 entries")
    public void setNormalFlightFile() {
        flightFile = new File("normal_flight.txt");
    }

    @Then("each flight entry is split into the 5 entries, with quotes removed, and the flight entries are all added with flight id 1")
    public void addedFlight() {
        expected_ids = new ArrayList<>(Arrays.asList(1, 5));
        Assert.assertEquals(expected_ids, ids);
    }

    @Given("a file with a single flight entry with too few entries, i.e. less than 5 entries")
    public void setTooFewEntriesFlightFile() {
        flightFile = new File("flight_entry_too_few_entries.txt");
    }

    @Given("a file with a single flight entry with too many entries, i.e. more than 5 entries")
    public void setTooManyEntriesFlightFile() {
        flightFile = new File("flight_entry_too_many_entries.txt");
    }

    @Given("a file with multiple flight data entries, some of them with the incorrect number of entries")
    public void setAbnormalFlightFile() {
        flightFile = new File("abnormal_flight.txt");
    }

    @Then("if an entry with the incorrect number of entries is reached, the data is rejected, any of the entries added prior are deleted, and the user is told that their data is in the wrong format")
    public void rejectedFlight() {
        expected_ids = new ArrayList<>(Arrays.asList(-1, -2));
        Assert.assertEquals(expected_ids, ids);
    }



    @Given("a file with route data with 9 entries")
    void setNormalRouteIDFile() {
        routeFile = new File("normal_route_9_entries.txt");
    }

    @When("^reading(.*)route data(.*)$")
    void readRoute(File file) {
        id = readFile.readRouteData(file);
    }

    @Given("a file with route data with 6 entries")
    void setNormalRouteFile() {
        routeFile = new File("normal_route_6_entries.txt");
    }

    @Given("a file with route data with an airline id, source and destination airport ids, 9 entries, and abnormal entries such as blank spaces")
    void setAbnormalRouteFile() {
        routeFile = new File("abnormal_route.txt");
    }

    @Given("a file with route data with too few entries, i.e. less than 6 entries")
    void setTooFewEntriesRoute() {
        routeFile = new File("route_too_few_entries.txt");
    }

    @Given("a file with route data with the wrong number of entries, i.e. more than 6 entries but has less than 9 entries")
    void setTooManyEntriesLessThan9Route() {
        routeFile = new File("route_too_many_entries_less_than_9.txt");
    }

    @Given("a file with route data with too many entries, i.e. more than 9 entries")
    void setTooManyEntriesRoute() {
        routeFile = new File("route_too_many_entries.txt");
    }

    @Given("a file with multiple lines, i.e. 5, of normal route data, i.e. route data with 6 or 9 entries")
    void setNormalRoutesFile() {
        routeFile = new File("normal_routes_multiple.txt");
    }

    @Given("a file with multiple lines, i.e. 5, where the first and third lines have the incorrect number of entries")
    void setAbnormalRoutesFile() {
        routeFile = new File("abnormal_routes_multiple.txt");
    }

}
