package seng202.team5.resources;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBInitializer {

    public static void createNewDatabase(String filename) {

        String directory = (System.getProperty("user.dir")).replace("\\", "/");
        String url = "jdbc:sqlite:" + directory + "/" + filename;

        try (Connection con = DriverManager.getConnection(url)) {
            if (con != null) {
                DatabaseMetaData meta = con.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("DB created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        createNewDatabase("flightdata.db");
    }
}