package data;

import junit.framework.Test;
import junit.framework.TestSuite;
import seng202.team5.data.ConcreteUpdateData;
import seng202.team5.data.UpdateData;
import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import service.BaseDatabaseTest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UpdateDataTest extends BaseDatabaseTest {
    private UpdateData updateData;

    public UpdateDataTest(String testName) { super(testName); }

    public static Test suite() { return new TestSuite(UpdateDataTest.class); }

    @Override
    protected void setUp() {
        super.setUp();
        updateData = new ConcreteUpdateData();
    }

    public void testUpdateAirline() throws SQLException {
        AirlineService airlineService = new AirlineService();
        assert airlineService.saveAirline("Airline Name", "Airline Alias", "IA", "ICA", "Callsigny", "Here", "Y") != -1;
        int id = airlineService.getMaxID(); // Hmmm

        List<String> newFields = List.of("New name", "New alias", "AI", "CIA", "Callsigner", "There", "N");
        int result = updateData.updateAirline(id, newFields.get(0), newFields.get(1), newFields.get(2), newFields.get(3), newFields.get(4), newFields.get(5), newFields.get(6));
        assertTrue(result >= 0);

        ResultSet resultSet = airlineService.getAirline(id);
        assert resultSet.next();
        for (int i = 0; i<newFields.size(); i++) {
            assertEquals(newFields.get(i), resultSet.getObject(i + 2));
        }
    }

    public void testUpdateAirlineFailed() {
        assertEquals(-2, updateData.updateAirline(1234, null, "Alias", "IA", "ICA", "Call", "Here", "Y"));
        assertEquals(-2, updateData.updateAirline(1234, "", "Alias", "IA", "ICA", "Call", "Here", "Y"));

        assertEquals(-3, updateData.updateAirline(1234, "Name", "Alias", "I", "ICA", "Call", "Here", "Y"));
        assertEquals(-3, updateData.updateAirline(1234, "Name", "Alias", "ABC", "ICA", "Call", "Here", "Y"));

        assertEquals(-4, updateData.updateAirline(1234, "Name", "Alias", "IA","I", "Call", "Here", "Y"));
        assertEquals(-4, updateData.updateAirline(1234, "Name", "Alias", "IA","AI", "Call", "Here", "Y"));

        assertEquals(-5, updateData.updateAirline(1234, "Name", "Alias", "IA", "ICA", "Call", "Here", null));
        assertEquals(-5, updateData.updateAirline(1234, "Name", "Alias", "IA","ICA", "Call", "Here", ""));
        assertEquals(-5, updateData.updateAirline(1234, "Name", "Alias", "IA","ICA", "Call", "Here", "YA"));
        assertEquals(-5, updateData.updateAirline(1234, "Name", "Alias", "IA","ICA", "Call", "Here", "F"));
    }

    public void testUpdateAirport() throws SQLException {
        AirportService airportService = new AirportService();
        assert airportService.saveAirport("Airport Name", "England", "Here", "IAT", "ICAO", 1.0, 2.0,15, 7, "E", "Timey zone") != -1;
        int id = airportService.getMaxID(); // Hmmm

        List<Object> newFields = List.of("New name", "New England", "There", "ATI", "AOCI", 3.0, 4.0, 17, 21.0f, "A", "Sometime");
        int result = updateData.updateAirport(
                1,
                (String)newFields.get(0),
                (String)newFields.get(1),
                (String)newFields.get(2),
                (String)newFields.get(3),
                (String)newFields.get(4),
                (double)newFields.get(5),
                (double)newFields.get(6),
                (int)newFields.get(7),
                (float)newFields.get(8),
                (String)newFields.get(9),
                (String)newFields.get(10)
        );
        assertTrue(result >= 0);

        ResultSet resultSet = airportService.getAirport(id);
        assert resultSet.next();
        for (int i = 0; i<newFields.size(); i++) {
            Object item = newFields.get(i);
            if (item instanceof Float) {
                item = (double)((float)item);
            }
            assertEquals(item, resultSet.getObject(i + 2));
        }
    }

    public void testUpdateAirportFailed() {
        assertEquals(-2, updateData.updateAirport(1234, "", "England", "Here", "IAT", "ICAO", 1.0, 2.0,15, 7, "E", "Timey zone"));
        assertEquals(-2, updateData.updateAirport(1234, null, "England", "Here", "IAT", "ICAO", 1.0, 2.0,15, 7, "E", "Timey zone"));

        assertEquals(-3, updateData.updateAirport(1234, "Airport Name", "", "Here", "IAT", "ICAO", 1.0, 2.0,15, 7, "E", "Timey zone"));
        assertEquals(-3, updateData.updateAirport(1234, "Airport Name", null, "Here", "IAT", "ICAO", 1.0, 2.0,15, 7, "E", "Timey zone"));

        assertEquals(-4, updateData.updateAirport(1234, "Airport Name", "England", "", "IAT", "ICAO", 1.0, 2.0,15, 7, "E", "Timey zone"));
        assertEquals(-4, updateData.updateAirport(1234, "Airport Name", "England", null, "IAT", "ICAO", 1.0, 2.0,15, 7, "E", "Timey zone"));

        assertEquals(-5, updateData.updateAirport(1234, "Airport Name", "England", "Here", "I", "ICAO", 1.0, 2.0,15, 7, "E", "Timey zone"));
        assertEquals(-5, updateData.updateAirport(1234, "Airport Name", "England", "Here", "IA", "ICAO", 1.0, 2.0,15, 7, "E", "Timey zone"));
        assertEquals(-5, updateData.updateAirport(1234, "Airport Name", "England", "Here", "IATA", "ICAO", 1.0, 2.0,15, 7, "E", "Timey zone"));

        assertEquals(-6, updateData.updateAirport(1234, "Airport Name", "England", "Here", "IAT", "I", 1.0, 2.0,15, 7, "E", "Timey zone"));
        assertEquals(-6, updateData.updateAirport(1234, "Airport Name", "England", "Here", "IAT", "IC", 1.0, 2.0,15, 7, "E", "Timey zone"));
        assertEquals(-6, updateData.updateAirport(1234, "Airport Name", "England", "Here", "IAT", "ICA", 1.0, 2.0,15, 7, "E", "Timey zone"));
        assertEquals(-6, updateData.updateAirport(1234, "Airport Name", "England", "Here", "IAT", "ICAOO", 1.0, 2.0,15, 7, "E", "Timey zone"));

        assertEquals(-11, updateData.updateAirport(1234, "Airport Name", "England", "Here", "IAT", "ICAO", 1.0, 2.0,15, 7, "Y", "Timey zone"));
        assertEquals(-11, updateData.updateAirport(1234, "Airport Name", "England", "Here", "IAT", "ICAO", 1.0, 2.0,15, 7, "EA", "Timey zone"));

        assertEquals(-12, updateData.updateAirport(1234, "Airport Name", "England", "Here", "IAT", "ICAO", 1.0, 2.0,15, 7, "E", ""));
        assertEquals(-12, updateData.updateAirport(1234, "Airport Name", "England", "Here", "IAT", "ICAO", 1.0, 2.0,15, 7, "E", null));
    }

    public void testUpdateFlightEntryFailed() {
        assertEquals(-2, updateData.updateFlightEntry(-1, "ab", "abc", 1, 2, 3));

        assertEquals(-3, updateData.updateFlightEntry(1, null, "abc", 1, 2, 3));
        assertEquals(-3, updateData.updateFlightEntry(1, "", "abc", 1, 2, 3));
        assertEquals(-3, updateData.updateFlightEntry(1, "a", "abc", 1, 2, 3));
        assertEquals(-3, updateData.updateFlightEntry(1, "abcd", "abc", 1, 2, 3));

        assertEquals(-4, updateData.updateFlightEntry(1, "ab", null, 1, 2, 3));
        assertEquals(-4, updateData.updateFlightEntry(1, "ab", "", 1, 2, 3));
        assertEquals(-4, updateData.updateFlightEntry(1, "ab", "a", 1, 2, 3));
        assertEquals(-4, updateData.updateFlightEntry(1, "ab", "ab", 1, 2, 3));
        assertEquals(-4, updateData.updateFlightEntry(1, "ab", "abcde", 1, 2, 3));
    }

    public void testUpdateRouteFailed() {
        assertEquals(-2, updateData.updateRoute(1, null, "abc", "def", "Y", 1, "a"));
        assertEquals(-2, updateData.updateRoute(1, "", "abc", "def", "Y", 1, "a"));
        assertEquals(-2, updateData.updateRoute(1, "a", "abc", "def", "Y", 1, "a"));
        assertEquals(-2, updateData.updateRoute(1, "abcd", "abc", "def", "Y", 1, "a"));

        assertEquals(-3, updateData.updateRoute(1, "ab", null, "def", "Y", 1, "a"));
        assertEquals(-3, updateData.updateRoute(1, "ab", "", "def", "Y", 1, "a"));
        assertEquals(-3, updateData.updateRoute(1, "ab", "a", "def", "Y", 1, "a"));
        assertEquals(-3, updateData.updateRoute(1, "ab", "ab", "def", "Y", 1, "a"));
        assertEquals(-3, updateData.updateRoute(1, "ab", "abcde", "def", "Y", 1, "a"));

        assertEquals(-4, updateData.updateRoute(1, "ab", "def",null, "Y", 1, "a"));
        assertEquals(-4, updateData.updateRoute(1, "ab", "def","", "Y", 1, "a"));
        assertEquals(-4, updateData.updateRoute(1, "ab", "def","a",  "Y", 1, "a"));
        assertEquals(-4, updateData.updateRoute(1, "ab", "def","ab",  "Y", 1, "a"));
        assertEquals(-4, updateData.updateRoute(1, "ab", "def","abcde",  "Y", 1, "a"));

        assertEquals(-5, updateData.updateRoute(1, "ab", "abc", "def", "N", 1, "a"));

        assertEquals(-6, updateData.updateRoute(1, "ab", "abc", "def", "Y", -1, "a"));

        assertEquals(-7, updateData.updateRoute(1, "ab", "abc", "def", "Y", 1, null));
        assertEquals(-7, updateData.updateRoute(1, "ab", "abc", "def", "Y", 1, ""));
    }
}
