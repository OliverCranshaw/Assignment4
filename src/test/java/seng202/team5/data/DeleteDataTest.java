package seng202.team5.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.FlightService;
import seng202.team5.service.RouteService;
import seng202.team5.service.BaseDatabaseTest;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteDataTest extends BaseDatabaseTest {

    private DeleteData deleteData;

    @Before
    public void setUp() {
        super.setUp();
        deleteData = new ConcreteDeleteData();
    }


    @Test
    public void testDeleteAirline() throws SQLException {
        AirlineService airlineService = new AirlineService();
        assert airlineService.saveAirline("Airline Name", "Airline Alias", "IA", "ICA", "Callsigny", "Here", "Y") != -1;
        int id = airlineService.getMaxID();

        deleteData.deleteAirline(id);

        Assert.assertFalse(airlineService.getAirline(id).next());
    }


    @Test
    public void testDeleteAirport() throws SQLException {
        AirportService airportService = new AirportService();
        assert airportService.saveAirport("Airport Name", "England", "Here", "IAT", "ICAO", 1.0, 2.0,15, 7, "E", "Timey zone") != -1;
        int id = airportService.getMaxID();

        deleteData.deleteAirport(id);

        Assert.assertFalse(airportService.getAirport(id).next());
    }


    @Test
    public void testDeleteRoute() throws SQLException {
        AirlineService airlineService = new AirlineService();
        assert airlineService.saveAirline("Airline Name", "Airline Alias", "IA", "ICA", "Callsigny", "Here", "Y") != -1;

        AirportService airportService = new AirportService();
        assert airportService.saveAirport("Airport Name", "England", "Here", "IAT", "ICAO", 1.0, 2.0,15, 7, "E", "Timey zone") != -1;


        RouteService routeService = new RouteService();
        routeService.saveRoute("IA", "IAT", "IAT", "Y", 0, "ABC");
        int id = routeService.getMaxID();

        deleteData.deleteRoute(id);

        Assert.assertFalse(routeService.getRoute(id).next());
    }


    @Test
    public void testDeleteFlightEntry() throws SQLException {
        AirlineService airlineService = new AirlineService();
        assert airlineService.saveAirline("Airline Name", "Airline Alias", "IA", "ICA", "Callsigny", "Here", "Y") != -1;

        AirportService airportService = new AirportService();
        assert airportService.saveAirport("Airport Name", "England", "Here", "IAT", "ICAO", 1.0, 2.0,15, 7, "E", "Timey zone") != -1;


        FlightService flightService = new FlightService();
        assert flightService.saveFlight(1, "VOR", "IAT", 1, 2, 3) != -1;
        assert flightService.saveFlight(1, "VOR", "IAT", 4, 5, 6) != -1;
        int id = flightService.getMaxID();

        deleteData.deleteFlightEntry(id);

        ResultSet resultSet = flightService.getFlight(1);
        Assert.assertTrue(resultSet.next());
        Assert.assertFalse(resultSet.next());
    }


    @Test
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
        Assert.assertFalse(resultSet.next());
    }
}
