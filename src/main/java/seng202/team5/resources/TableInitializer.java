package seng202.team5.resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class TableInitializer {

    public static void initializeTables() {

        String directory = (System.getProperty("user.dir")).replace("\\", "/");
        String url = "jdbc:sqlite:" + directory + "/flightdata.db";

        //String airport_sql = "CREATE TABLE IF NOT EXISTS airports (\n"
                        //+ "     id integer PRIMARY KEY,\n"
                        //+ "     "
    }
}