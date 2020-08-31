package data;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import seng202.team5.data.ConcreteAddData;
import seng202.team5.data.ReadFile;
import seng202.team5.database.DBConnection;
import seng202.team5.database.DBInitializer;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class AddDataTest {

    private ConcreteAddData concreteAddData;
    private ReadFile readFile;
    private File airlines = new File("src/test/java/data/testfiles/airlines.txt");
    private File airports = new File("src/test/java/data/testfiles/airports.txt");
    private int outcome;
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
        // String name, String alias, String iata, String icao, String callsign, String country, String active
        outcome = concreteAddData.addAirline("Abelag Aviation", "\\N", "W9", "AAB", "ABG", "Belgium", "N");
        assertEquals(1, outcome);

        outcome = concreteAddData.addAirline("Airfix Aviation", "Airfix", "", "FIX", "AIRFIX", "Finland", "Y");
        assertEquals(1, outcome);

        outcome = concreteAddData.addAirline("Airfix Aviation", "", "", "", "", "", "N");
        assertEquals(1, outcome);
    }

    @Test
    public void addAirlineFailTest() {
        outcome = concreteAddData.addAirline("", "Airfix", "", "FIX", "AIRFIX", "Finland", "Y");
        assertEquals(-2, outcome);

        outcome = concreteAddData.addAirline("Abelag Aviation", "\\N", "W9A", "AAB", "ABG", "Belgium", "N");
        assertEquals(-3, outcome);

        outcome = concreteAddData.addAirline("Airfix Aviation", "Airfix", "", "FX", "AIRFIX", "Finland", "Y");
        assertEquals(-4, outcome);

        outcome = concreteAddData.addAirline("Abelag Aviation", "\\N", "W9", "AAB", "ABG", "Belgium", "");
        assertEquals(-5, outcome);

        outcome = concreteAddData.addAirline("Abelag Aviation", "\\N", "W9", "AAB", "ABG", "Belgium", "A");
        assertEquals(-5, outcome);

        outcome = concreteAddData.addAirline("", "", "", "", "", "", "");
        assertEquals(-2, outcome);
    }

    @Test
    public void addAirportTest() {
        // String name, String city, String country, String iata, String icao, String latitude,
        //                          String longitude, String altitude, String timezone, String dst, String tz
        outcome = concreteAddData.addAirport("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "NZCH",
        "-43.489358", "172.532225", "123", "12", "Z", "Pacific/Auckland");
        assertEquals(1, outcome);

        outcome = concreteAddData.addAirport("Changi Intl", "Singapore", "Singapore", "", "WSSS", "1.350189",
        "103.994433", "22", "8", "N", "Asia/Singapore");
        assertEquals(1, outcome);

        outcome = concreteAddData.addAirport("Tolmachevo", "Novosibirsk", "Russia", "OVB", "", "55.012622",
                 "82.650656", "365", "7", "A", "Asia/Omsk");
        assertEquals(1, outcome);

        outcome = concreteAddData.addAirport("Balandino", "Chelyabinsk", "Russia", "", "", "55.305836",
                "61.503333", "769", "6","E", "Asia/Yekaterinburg");
        assertEquals(1, outcome);
    }

    @Test
    public void addAirportFailTest() {
        outcome = concreteAddData.addAirport("", "Christchurch", "New Zealand", "CHC", "NZCH",
                "-43.489358", "172.532225", "123", "12", "Z", "Pacific/Auckland");
        assertEquals(-2, outcome);

        outcome = concreteAddData.addAirport("Christchurch Intl", "", "New Zealand", "CHC", "NZCH",
                "-43.489358", "172.532225", "123", "12", "Z", "Pacific/Auckland");
        assertEquals(-3, outcome);

        outcome = concreteAddData.addAirport("Christchurch Intl", "Christchurch", "", "CHC", "NZCH",
                "-43.489358", "172.532225", "123", "12", "Z", "Pacific/Auckland");
        assertEquals(-4, outcome);

        outcome = concreteAddData.addAirport("Changi Intl", "Singapore", "Singapore", "CH", "WSSS", "1.350189",
                "103.994433", "22", "8", "N", "Asia/Singapore");
        assertEquals(-5, outcome);

        outcome = concreteAddData.addAirport("Tolmachevo", "Novosibirsk", "Russia", "OVB", "TLMCV", "55.012622",
                "82.650656", "365", "7", "A", "Asia/Omsk");
        assertEquals(-6, outcome);

        outcome = concreteAddData.addAirport("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "NZCH",
                "", "172.532225", "123", "12", "Z", "Pacific/Auckland");
        assertEquals(-7, outcome);

        outcome = concreteAddData.addAirport("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "NZCH",
                "-43.489358", "", "123", "12", "Z", "Pacific/Auckland");
        assertEquals(-8, outcome);

        outcome = concreteAddData.addAirport("Christchurch Intl", "Christchurch", "New Zealand", "", "NZCH",
                "-43.489358", "172.532225", "", "12", "Z", "Pacific/Auckland");
        assertEquals(-9, outcome);

        outcome = concreteAddData.addAirport("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "",
                "-43.489358", "172.532225", "123", "", "Z", "Pacific/Auckland");
        assertEquals(-10, outcome);

        outcome = concreteAddData.addAirport("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "NZCH",
                "-43.489358", "172.532225", "123", "12", "", "Pacific/Auckland");
        assertEquals(-11, outcome);

        outcome = concreteAddData.addAirport("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "NZCH",
                "-43.489358", "172.532225", "123", "12", "B", "Pacific/Auckland");
        assertEquals(-11, outcome);

        outcome = concreteAddData.addAirport("Christchurch Intl", "Christchurch", "New Zealand", "CHC", "NZCH",
                "-43.489358", "172.532225", "123", "12", "Z", "");
        assertEquals(-12, outcome);

        outcome = concreteAddData.addAirport("", "", "", "", "", "", "", "", "", "", "");
        assertEquals(-2, outcome);
    }

    @Test
    public void addFlightEntryTest() {
        readFile.readAirlineData(airlines);
        readFile.readAirportData(airports);

        // int flightID, String airline, String airport, String altitude, String latitude, String longitude
        outcome = concreteAddData.addFlightEntry(1, "APT", "NZCH", "0", "-43.4866", "172.534");
        assertEquals(1, outcome);

        outcome = concreteAddData.addFlightEntry(1, "W9", "CHC", "0", "43.4866", "-172.534");
        assertEquals(1, outcome);
    }

    @Test
    public void addFlightEntryFailTest() {
        outcome = concreteAddData.addFlightEntry(-1, "APT", "NZCH", "0", "-43.4866", "172.534");
        assertEquals(-2, outcome);

        outcome = concreteAddData.addFlightEntry(1, "", "NZCH", "0", "-43.4866", "172.534");
        assertEquals(-3, outcome);

        outcome = concreteAddData.addFlightEntry(1, "APTN", "NZCH", "0", "-43.4866", "172.534");
        assertEquals(-3, outcome);

        outcome = concreteAddData.addFlightEntry(1, "APT", "", "0", "-43.4866", "172.534");
        assertEquals(-4, outcome);

        outcome = concreteAddData.addFlightEntry(1, "APT", "NZ", "0", "-43.4866", "172.534");
        assertEquals(-4, outcome);

        outcome = concreteAddData.addFlightEntry(1, "APT", "NZCH", "", "-43.4866", "172.534");
        assertEquals(-5, outcome);

        outcome = concreteAddData.addFlightEntry(1, "APT", "NZCH", "0", "", "172.534");
        assertEquals(-6, outcome);

        outcome = concreteAddData.addFlightEntry(1, "APT", "NZCH", "0", "-43.4866", "");
        assertEquals(-7, outcome);

        outcome = concreteAddData.addFlightEntry(-1, "", "", "", "", "");
        assertEquals(-2, outcome);
    }

    @Test
    public void addRouteTest() {
        readFile.readAirlineData(airlines);
        readFile.readAirportData(airports);

        // String airline, String source_airport, String dest_airport, String codeshare, String stops, String equipment
        outcome = concreteAddData.addRoute("2B", "ASF", "KZN", "", "0", "CR2");
        assertEquals(1, outcome);

        outcome = concreteAddData.addRoute("APT", "WSSS", "KZN", "Y", "1", "CR2 TN3");
        assertEquals(1, outcome);

        outcome = concreteAddData.addRoute("APT", "WSSS", "NZCH", "Y", "0", "CR2 TN3 KT4");
        assertEquals(1, outcome);
    }

    @Test
    public void addRouteFailTest() {
        outcome = concreteAddData.addRoute("", "ASF", "KZN", "", "0", "CR2");
        assertEquals(-2, outcome);

        outcome = concreteAddData.addRoute("2", "ASF", "KZN", "", "0", "CR2");
        assertEquals(-2, outcome);

        outcome = concreteAddData.addRoute("2B", "", "KZN", "", "0", "CR2");
        assertEquals(-3, outcome);

        outcome = concreteAddData.addRoute("2B", "ASFHT", "KZN", "", "0", "CR2");
        assertEquals(-3, outcome);

        outcome = concreteAddData.addRoute("2B", "ASF", "", "", "0", "CR2");
        assertEquals(-4, outcome);

        outcome = concreteAddData.addRoute("2B", "ASF", "KZ", "", "0", "CR2");
        assertEquals(-4, outcome);

        outcome = concreteAddData.addRoute("APT", "WSSS", "KZN", "N", "1", "CR2 TN3");
        assertEquals(-5, outcome);

        outcome = concreteAddData.addRoute("APT", "WSSS", "KZN", "Y", "-1", "CR2 TN3");
        assertEquals(-1, outcome);

        outcome = concreteAddData.addRoute("APT", "WSSS", "KZN", "Y", "", "CR2 TN3");
        assertEquals(-6, outcome);

        outcome = concreteAddData.addRoute("APT", "WSSS", "KZN", "Y", "1", "");
        assertEquals(-7, outcome);

        outcome = concreteAddData.addRoute("", "", "", "", "", "");
        assertEquals(-2, outcome);
    }
}
