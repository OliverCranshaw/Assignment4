package seng202.team5.data;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import seng202.team5.database.DBConnection;
import seng202.team5.database.DBInitializer;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class AddDataTest {

    private ConcreteAddData concreteAddData;
    private ReadFile readFile;
    private File airlines = new File("src/test/java/seng202/team5/data/testfiles/airlines.txt");
    private File airports = new File("src/test/java/seng202/team5/data/testfiles/airports.txt");
    private int id;
    private static Connection con;

    @Before
    public void setup() {
        String filename = "test.db";
        File dbFile = new File(filename);

        DBInitializer.createNewDatabase(filename);

        DBConnection.setDatabaseFile(dbFile);

        concreteAddData = new ConcreteAddData();
        readFile = new ReadFile();
    }

    @After
    public void teardown() {
        try {
            File dbFile = new File("test.db");
            con = DBConnection.getConnection();
            con.close();

            boolean result = dbFile.delete();

            if (result) {
                System.out.println("DB deleted.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void addAirlineTest() {
        id = concreteAddData.addAirline("Abelag Aviation", "\\N", "W9", "AAB", "ABG", "Belgium", "N");
        assertEquals(1, id);

        id = concreteAddData.addAirline("Airfix Aviation", "Airfix", "", "FIX", "AIRFIX", "Finland", "Y");
        assertEquals(2, id);
    }


    @Test
    public void addAirlineFailTest() {
        // Invalid name
        id = concreteAddData.addAirline("", "Airfix", "", "FIX", "AIRFIX", "Finland", "Y");
        assertEquals(-2, id);

        // Invalid IATA code
        id = concreteAddData.addAirline("Abelag Aviation", "\\N", "W9A", "AAB", "ABG", "Belgium", "N");
        assertEquals(-3, id);

        // Invalid ICAO code
        id = concreteAddData.addAirline("Airfix Aviation", "Airfix", "", "F", "AIRFIX", "Finland", "Y");
        assertEquals(-4, id);

        // Invalid active
        id = concreteAddData.addAirline("Abelag Aviation", "\\N", "W9", "AAB", "ABG", "Belgium", "");
        assertEquals(-5, id);

        // Invalid active
        id = concreteAddData.addAirline("Abelag Aviation", "\\N", "W9", "AAB", "ABG", "Belgium", "A");
        assertEquals(-5, id);

        // Empty, invalid name
        id = concreteAddData.addAirline("", "", "", "", "", "", "");
        assertEquals(-2, id);

        // Invalid at the AirlineService checks (IATA/ICAO)
        id = concreteAddData.addAirline("Airfix Aviation", "", "", "", "", "", "N");
        assertEquals(-1, id);
    }


    @Test
    public void addAirportTest() {
        id = concreteAddData.addAirport("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "NZCH",
        "-43.489358", "172.532225", "123", "12", "Z", "Pacific/Auckland");
        assertEquals(1, id);

        id = concreteAddData.addAirport("Changi Intl", "Singapore", "Singapore", "", "WSSS", "1.350189",
        "103.994433", "22", "8", "N", "Asia/Singapore");
        assertEquals(2, id);

        id = concreteAddData.addAirport("Tolmachevo", "Novosibirsk", "Russia", "OVB", "", "55.012622",
                 "82.650656", "365", "7", "A", "Asia/Omsk");
        assertEquals(3, id);
    }


    @Test
    public void addAirportFailTest() {
        // Invalid name
        id = concreteAddData.addAirport("", "Christchurch", "New Zealand", "CHC", "NZCH",
                "-43.489358", "172.532225", "123", "12", "Z", "Pacific/Auckland");
        assertEquals(-2, id);

        // Invalid city
        id = concreteAddData.addAirport("Christchurch Intl", "", "New Zealand", "CHC", "NZCH",
                "-43.489358", "172.532225", "123", "12", "Z", "Pacific/Auckland");
        assertEquals(-3, id);

        // Invalid country
        id = concreteAddData.addAirport("Christchurch Intl", "Christchurch", "", "CHC", "NZCH",
                "-43.489358", "172.532225", "123", "12", "Z", "Pacific/Auckland");
        assertEquals(-4, id);

        // Invalid IATA code
        id = concreteAddData.addAirport("Changi Intl", "Singapore", "Singapore", "CH", "WSSS", "1.350189",
                "103.994433", "22", "8", "N", "Asia/Singapore");
        assertEquals(-5, id);

        // Invalid ICAO code
        id = concreteAddData.addAirport("Tolmachevo", "Novosibirsk", "Russia", "OVB", "TLMCV", "55.012622",
                "82.650656", "365", "7", "A", "Asia/Omsk");
        assertEquals(-6, id);

        // Invalid latitude
        id = concreteAddData.addAirport("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "NZCH",
                "", "172.532225", "123", "12", "Z", "Pacific/Auckland");
        assertEquals(-7, id);

        // Invalid longitude
        id = concreteAddData.addAirport("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "NZCH",
                "-43.489358", "", "123", "12", "Z", "Pacific/Auckland");
        assertEquals(-8, id);

        // Invalid altitude
        id = concreteAddData.addAirport("Christchurch Intl", "Christchurch", "New Zealand", "", "NZCH",
                "-43.489358", "172.532225", "", "12", "Z", "Pacific/Auckland");
        assertEquals(-9, id);

        // Invalid timezone
        id = concreteAddData.addAirport("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "",
                "-43.489358", "172.532225", "123", "", "Z", "Pacific/Auckland");
        assertEquals(-10, id);

        // Invalid DST
        id = concreteAddData.addAirport("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "NZCH",
                "-43.489358", "172.532225", "123", "12", "", "Pacific/Auckland");
        assertEquals(-11, id);

        // Invalid DST
        id = concreteAddData.addAirport("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "NZCH",
                "-43.489358", "172.532225", "123", "12", "B", "Pacific/Auckland");
        assertEquals(-11, id);

        // Invalid TZ format timezone
        id = concreteAddData.addAirport("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "NZCH",
                "-43.489358", "172.532225", "123", "12", "Z", "");
        assertEquals(-12, id);

        // Empty, invalid name
        id = concreteAddData.addAirport("", "", "", "", "", "", "", "", "", "", "");
        assertEquals(-2, id);

        // Invalid at the AirportService checks (IATA/ICAO)
        id = concreteAddData.addAirport("Balandino", "Chelyabinsk", "Russia", "", "", "55.305836",
                "61.503333", "769", "6","E", "Asia/Yekaterinburg");
        assertEquals(-1, id);
    }


    @Test
    public void addFlightEntryTest() {
        // Adds the airports necessary to add the flight entries
        readFile.readAirportData(airports);

        id = concreteAddData.addFlightEntry(1, "APT", "NZCH", "0", "-43.4866", "172.534");
        assertEquals(1, id);

        id = concreteAddData.addFlightEntry(1, "FIX", "CHC", "0", "43.4866", "-172.534");
        assertEquals(2, id);

        id = concreteAddData.addFlightEntry(1, "VOR", "CH", "0", "43.4866", "-172.534");
        assertEquals(3, id);
    }


    @Test
    public void addFlightEntryFailTest() {
        // Invalid flightID
        id = concreteAddData.addFlightEntry(-1, "APT", "NZCH", "0", "-43.4866", "172.534");
        assertEquals(-2, id);

        // Invalid location type
        id = concreteAddData.addFlightEntry(1, "", "NZCH", "0", "-43.4866", "172.534");
        assertEquals(-3, id);

        // Invalid location type
        id = concreteAddData.addFlightEntry(1, "ART", "NZCH", "0", "-43.4866", "172.534");
        assertEquals(-3, id);

        // Invalid location
        id = concreteAddData.addFlightEntry(1, "VOR", "", "0", "-43.4866", "172.534");
        assertEquals(-4, id);

        // Invalid location
        id = concreteAddData.addFlightEntry(1, "FIX", "", "0", "-43.4866", "172.534");
        assertEquals(-4, id);

        // Invalid altitude
        id = concreteAddData.addFlightEntry(1, "FIX", "NZC", "", "-43.4866", "172.534");
        assertEquals(-5, id);

        // Invalid latitude
        id = concreteAddData.addFlightEntry(1, "FIX", "NZCH", "0", "", "172.534");
        assertEquals(-6, id);

        // Invalid longitude
        id = concreteAddData.addFlightEntry(1, "VOR", "NZCH", "0", "-43.4866", "");
        assertEquals(-7, id);

        // Empty, invalid flightID
        id = concreteAddData.addFlightEntry(-1, "", "", "", "", "");
        assertEquals(-2, id);
    }


    @Test
    public void addRouteTest() {
        // Adds the airlines and airports necessary to add the routes
        readFile.readAirlineData(airlines);
        readFile.readAirportData(airports);

        id = concreteAddData.addRoute("2B", "ASF", "KZN", "", "0", "CR2");
        assertEquals(1, id);

        id = concreteAddData.addRoute("APT", "WSSS", "KZN", "Y", "1", "CR2 TN3");
        assertEquals(2, id);

        id = concreteAddData.addRoute("APT", "WSSS", "NZCH", "Y", "0", "CR2 TN3 KT4");
        assertEquals(3, id);
    }


    @Test
    public void addRouteFailTest() {
        // Invalid airline IATA/ICAO code
        id = concreteAddData.addRoute("", "ASF", "KZN", "", "0", "CR2");
        assertEquals(-2, id);

        // Invalid airline IATA/ICAO code
        id = concreteAddData.addRoute("2", "ASF", "KZN", "", "0", "CR2");
        assertEquals(-2, id);

        // Invalid source airport IATA/ICAO code
        id = concreteAddData.addRoute("2B", "", "KZN", "", "0", "CR2");
        assertEquals(-3, id);

        // Invalid source airport IATA/ICAO code
        id = concreteAddData.addRoute("2B", "ASFHT", "KZN", "", "0", "CR2");
        assertEquals(-3, id);

        // Invalid destination airport IATA/ICAO code
        id = concreteAddData.addRoute("2B", "ASF", "", "", "0", "CR2");
        assertEquals(-4, id);

        // Invalid destination airport IATA/ICAO code
        id = concreteAddData.addRoute("2B", "ASF", "KZ", "", "0", "CR2");
        assertEquals(-4, id);

        // Invalid codeshare
        id = concreteAddData.addRoute("APT", "WSSS", "KZN", "N", "1", "CR2 TN3");
        assertEquals(-5, id);

        // Invalid stops
        id = concreteAddData.addRoute("APT", "WSSS", "KZN", "Y", "-1", "CR2 TN3");
        assertEquals(-6, id);

        // Invalid stops
        id = concreteAddData.addRoute("APT", "WSSS", "KZN", "Y", "", "CR2 TN3");
        assertEquals(-6, id);

        // Invalid equipment
        id = concreteAddData.addRoute("APT", "WSSS", "KZN", "Y", "1", "");
        assertEquals(-7, id);

        // Empty, invalid airline IATA/ICAO code
        id = concreteAddData.addRoute("", "", "", "", "", "");
        assertEquals(-2, id);
    }
}
