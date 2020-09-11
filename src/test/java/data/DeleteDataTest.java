package data;

import junit.framework.Test;
import junit.framework.TestSuite;
import seng202.team5.data.ConcreteDeleteData;
import seng202.team5.data.ConcreteUpdateData;
import seng202.team5.data.DeleteData;
import seng202.team5.data.RouteData;
import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.FlightService;
import seng202.team5.service.RouteService;
import service.BaseDatabaseTest;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteDataTest extends BaseDatabaseTest {
    private DeleteData deleteData;

    public DeleteDataTest(String testName) { super(testName); }

    public static Test suite() { return new TestSuite(DeleteDataTest.class); }

    @Override
    protected void setUp() {
        super.setUp();
        deleteData = new ConcreteDeleteData();
    }

    public void testDeleteAirline() throws SQLException {
        AirlineService airlineService = new AirlineService();
        assert airlineService.saveAirline("Airline Name", "Airline Alias", "IA", "ICA", "Callsigny", "Here", "Y") != -1;
        int id = airlineService.getMaxID();

        deleteData.deleteAirline(id);

        assertFalse(airlineService.getAirline(id).next());
    }

    public void testDeleteAirport() throws SQLException {
        AirportService airportService = new AirportService();
        assert airportService.saveAirport("Airport Name", "England", "Here", "IAT", "ICAO", 1.0, 2.0,15, 7, "E", "Timey zone") != -1;
        int id = airportService.getMaxID();

        deleteData.deleteAirport(id);

        assertFalse(airportService.getAirport(id).next());
    }

    public void testDeleteRoute() throws SQLException {
        AirlineService airlineService = new AirlineService();
        assert airlineService.saveAirline("Airline Name", "Airline Alias", "IA", "ICA", "Callsigny", "Here", "Y") != -1;

        AirportService airportService = new AirportService();
        assert airportService.saveAirport("Airport Name", "England", "Here", "IAT", "ICAO", 1.0, 2.0,15, 7, "E", "Timey zone") != -1;


        RouteService routeService = new RouteService();
        routeService.saveRoute("IA", "IAT", "IAT", "Y", 0, "ABC");
        int id = routeService.getMaxID();

        deleteData.deleteRoute(id);

        assertFalse(routeService.getRoute(id).next());
    }

    public void testDeleteFlightEntry() throws SQLException {
        AirlineService airlineService = new AirlineService();
        assert airlineService.saveAirline("Airline Name", "Airline Alias", "IA", "ICA", "Callsigny", "Here", "Y") != -1;

        AirportService airportService = new AirportService();
        assert airportService.saveAirport("Airport Name", "England", "Here", "IAT", "ICAO", 1.0, 2.0,15, 7, "E", "Timey zone") != -1;


        FlightService flightService = new FlightService();
        flightService.saveFlight(1, "IA", "IAT", 1, 2, 3);
        flightService.saveFlight(1, "IA", "IAT", 4, 5, 6);
        int id = flightService.getMaxID();

        deleteData.deleteFlightEntry(id);

        ResultSet resultSet = flightService.getFlight(1);
        assertTrue(resultSet.next());
        assertFalse(resultSet.next());
    }

    public void testDeleteFlight() throws SQLException {
        AirlineService airlineService = new AirlineService();
        assert airlineService.saveAirline("Airline Name", "Airline Alias", "IA", "ICA", "Callsigny", "Here", "Y") != -1;

        AirportService airportService = new AirportService();
        assert airportService.saveAirport("Airport Name", "England", "Here", "IAT", "ICAO", 1.0, 2.0,15, 7, "E", "Timey zone") != -1;


        FlightService flightService = new FlightService();
        flightService.saveFlight(1, "IA", "IAT", 1, 2, 3);
        flightService.saveFlight(1, "IA", "IAT", 4, 5, 6);
        int id = flightService.getMaxID();

        deleteData.deleteFlight(1);

        ResultSet resultSet = flightService.getFlight(1);
        assertFalse(resultSet.next());
    }
}
