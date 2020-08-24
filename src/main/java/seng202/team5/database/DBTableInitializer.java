package seng202.team5.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DBTableInitializer {

    public static void initializeTables(String url) {

        String airport_sql = "CREATE TABLE IF NOT EXISTS AIRPORT_DATA (\n"
                + "     airport_id INTEGER PRIMARY KEY,\n" // merged id and airport_id together, both unique so should work
                + "     airport_name TEXT NOT NULL,\n"
                + "     city TEXT NOT NULL,\n"
                + "     country TEXT NOT NULL,\n"
                + "     iata TEXT,\n"
                + "     icao TEXT,\n"
                + "     latitude REAL NOT NULL,\n"
                + "     longitude REAL NOT NULL,\n"
                + "     altitude INTEGER NOT NULL,\n"
                + "     timezone REAL NOT NULL,\n"
                + "     dst TEXT NOT NULL,\n"
                + "     tz_database_timezone TEXT NOT NULL\n"
                + ");";

        String airline_sql = "CREATE TABLE IF NOT EXISTS AIRLINE_DATA (\n"
                + "     airline_id INTEGER PRIMARY KEY,\n"
                + "     airline_name TEXT NOT NULL,\n"
                + "     alias TEXT,\n"
                + "     iata TEXT,\n"
                + "     icao TEXT,\n"
                + "     callsign TEXT,\n"
                + "     country TEXT,\n"
                + "     active TEXT NOT NULL\n"
                + ");";

        String route_sql = "CREATE TABLE IF NOT EXISTS ROUTE_DATA (\n"
                + "     route_id INTEGER PRIMARY KEY,\n"
                + "     airline TEXT NOT NULL,\n" // iata or icao
                + "     airline_id INTEGER NOT NULL,\n"
                + "     source_airport TEXT NOT NULL,\n" // iata or icao
                + "     source_airport_id INTEGER NOT NULL,\n"
                + "     destination_airport TEXT NOT NULL,\n" // iata or icao
                + "     destination_airport_id INTEGER NOT NULL,\n"
                + "     codeshare TEXT,\n"
                + "     stops INTEGER NOT NULL,\n"
                + "     equipment TEXT NOT NULL,\n"
                + "     FOREIGN KEY (airline_id)\n"
                + "         REFERENCES AIRLINE_DATA(airline_id)\n"
                + "             ON UPDATE CASCADE\n" // set everything to cascade if the entry its referencing is updated/deleted
                + "             ON DELETE CASCADE,\n"
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
                + "     id INTEGER PRIMARY KEY,\n"
                + "     flight_id INTEGER NOT NULL,\n"
                + "     airline TEXT NOT NULL,\n" // icao of airline
                + "     airport TEXT NOT NULL,\n" // icao of airport
                + "     altitude INTEGER NOT NULL,\n"
                + "     latitude REAL NOT NULL,\n"
                + "     longitude REAL NOT NULL\n"
                + ");";

        try (Connection con = DriverManager.getConnection(url);
            Statement statement = con.createStatement()) {
            statement.execute(airline_sql); //creates the tables
            statement.execute(airport_sql);
            statement.execute(route_sql);
            statement.execute(flight_sql);

            System.out.println("Tables created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}