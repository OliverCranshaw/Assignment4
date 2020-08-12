package seng202.team5.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.String.format;

public class DBConnection {

    private static String url = "jdbc:sqlite:%s";
    private static File dbFile =  new File("flightdata.db");
    private static Connection conn;

    public static Connection getConnection() {
        try {
            conn = DriverManager.getConnection(format(url, dbFile.getAbsolutePath()));
        } catch (SQLException e) {
            System.out.println("Failed to create the database connnection.");
        }
        return conn;
    }

}
