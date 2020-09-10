package seng202.team5.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBInitializer
 *
 * Creates a database.
 *
 * @author Billie Johnson
 */
public class DBInitializer {

    // The table initializer, used to create the airline, airport, flight, and route tables in the database
    private static DBTableInitializer tableInitializer = new DBTableInitializer();

    /**
     * Takes a given filename and creates a new database with it given one doesn't already exist.
     *
     * @param filename The name you want the new database to have, must include .db
     */
    public static void createNewDatabase(String filename) {

        // Finds the current directory and creates a url for the database including the directory and filename
        String directory = (System.getProperty("user.dir")).replace("\\", "/");
        String url = "jdbc:sqlite:" + directory + "/" + filename;

        // Checks if the database file already exists
        File f = new File(filename);
        if (!f.exists()) {
            // If the database file doesn't exist, a connection with the url is created, and the tables are initialized
            try (Connection con = DriverManager.getConnection(url)) {
                if (con != null) {
                    DatabaseMetaData meta = con.getMetaData();
                    System.out.println("The driver name is " + meta.getDriverName());
                    System.out.println("DB created.");

                    tableInitializer.initializeTables(url);
                }
            } catch (SQLException e) {
                // If any of the above fails, prints out an error message
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Database already exists.");
        }
    }

//    public static void main(String[] args) {
//        //createNewDatabase("flightdata.db");
//    }
}