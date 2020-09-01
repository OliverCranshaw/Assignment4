package seng202.team5.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DBTableInitializer
 *
 * Creates the database tables for airport, airline, flight, and route data.
 *
 * @author Billie Johnson
 */
public class DBTableInitializer {

    /**
     * Creates the airport, airline, flight, and route tables if they do not already exist.
     * Uses SQLite3.
     *
     * @param url The url of the database, intended to be passed in from DBInitializer
     */
    public static void initializeTables(String url) {
    // public static void initializeTables(String filename) {

    //     String directory = (System.getProperty("user.dir")).replace("\\", "/");
    //     String url = "jdbc:sqlite:" + directory + "/" + filename;

        String airport_sql = "CREATE TABLE IF NOT EXISTS AIRPORT_DATA (\n"
                + "     airport_id INTEGER PRIMARY KEY,\n" // Auto-increments as it is an integer primary key, unique
                + "     airport_name TEXT NOT NULL,\n"
                + "     city TEXT NOT NULL,\n"
                + "     country TEXT NOT NULL,\n"
                + "     iata TEXT,\n" // 3-letter code, unique, null if not known/assigned
                + "     icao TEXT,\n" // 4-letter code, unique, null if not known/assigned
                + "     latitude REAL NOT NULL,\n" // Negative is South, positive is North, double
                + "     longitude REAL NOT NULL,\n" // Negative is West, positive is East, double
                + "     altitude INTEGER NOT NULL,\n" // In feet
                + "     timezone REAL NOT NULL,\n" // Hours offset from UTC, float
                + "     dst TEXT NOT NULL,\n" // Daylight savings time, one of E (Europe), A (US/Canada), S (South America), O (Australia), Z (New Zealand), N (None) or U (Unknown)
                + "     tz_database_timezone TEXT NOT NULL\n" // Timezone in "tz" (Olson) format, i.e. Country/Region
                + ");";

        String airline_sql = "CREATE TABLE IF NOT EXISTS AIRLINE_DATA (\n"
                + "     airline_id INTEGER PRIMARY KEY,\n"
                + "     airline_name TEXT NOT NULL,\n"
                + "     alias TEXT,\n"
                + "     iata TEXT,\n" // 2-letter code, unique, null if not known/assigned
                + "     icao TEXT,\n" // 3-letter code, unique, null if not known/assigned
                + "     callsign TEXT,\n"
                + "     country TEXT,\n"
                + "     active TEXT NOT NULL\n" // "Y" if the airline is or has until recently been operational, "N" if it is defunct
                + ");";

        String route_sql = "CREATE TABLE IF NOT EXISTS ROUTE_DATA (\n"
                + "     route_id INTEGER PRIMARY KEY,\n"
                + "     airline TEXT NOT NULL,\n" // IATA/ICAO code
                + "     airline_id INTEGER NOT NULL,\n"
                + "     source_airport TEXT NOT NULL,\n" // IATA/ICAO code
                + "     source_airport_id INTEGER NOT NULL,\n"
                + "     destination_airport TEXT NOT NULL,\n" // IATA/ICAO code
                + "     destination_airport_id INTEGER NOT NULL,\n"
                + "     codeshare TEXT NOT NULL,\n" // "Y" if the flight is operated by a different airline, otherwise ""
                + "     stops INTEGER NOT NULL,\n" // Number of stops for the flight, 0 if it is direct
                + "     equipment TEXT NOT NULL,\n" // 3-letter codes for plane types(s) commonly used for this flight, separated by spaces
                + "     FOREIGN KEY (airline_id)\n"
                + "         REFERENCES AIRLINE_DATA(airline_id)\n"
                + "             ON UPDATE CASCADE\n" // Set everything to cascade if the entry it's referencing is updated/deleted
                + "             ON DELETE CASCADE,\n" // i.e. if the airline with said airline_id is deleted, the entry with said airline_id is also deleted
                + "     FOREIGN KEY (source_airport_id)\n"
                + "         REFERENCES AIRPORT_DATA(airport_id)\n"
                + "             ON UPDATE CASCADE\n"
                + "             ON DELETE CASCADE,\n"
                + "     FOREIGN KEY (destination_airport_id)\n"
                + "         REFERENCES AIRPORT_DATA(airport_id)\n"
                + "             ON UPDATE CASCADE\n"
                + "             ON DELETE CASCADE\n"
                + ");";

        String flight_sql = "CREATE TABLE IF NOT EXISTS FLIGHT_DATA (\n"
                + "     id INTEGER PRIMARY KEY,\n" // Auto-increments as it is an integer primary key, unique
                + "     flight_id INTEGER NOT NULL,\n" // Not unique, ties the different entries in a single flight together
                + "     airline TEXT NOT NULL,\n" // IATA/ICAO code
                + "     airport TEXT NOT NULL,\n" // IATA/ICAO code
                + "     altitude INTEGER NOT NULL,\n" // In feet, first and last entries of a flight must be 0
                + "     latitude REAL NOT NULL,\n" // Negative is South, positive is North, double
                + "     longitude REAL NOT NULL\n" // Negative is West, positive is East, double
                + ");";

        // Attempts to create a connection to the database with the given url, prints an error message otherwise
        try (Connection con = DriverManager.getConnection(url);
            Statement statement = con.createStatement()) {
            // Creates the tables for airlines, airports, routes, and flights
            statement.execute(airline_sql);
            statement.execute(airport_sql);
            statement.execute(route_sql);
            statement.execute(flight_sql);

            System.out.println("Tables created.");
        } catch (SQLException e) {
            // If any of the above fails, prints out an error message
            System.out.println(e.getMessage());
        }
    }

    // public static void main(String[] args) {
    //     initializeTables("flightdata.db");
    //     System.out.println("Tables created.");
    // }
}