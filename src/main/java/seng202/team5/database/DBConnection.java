package seng202.team5.database;

import org.sqlite.javax.SQLiteConnectionPoolDataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.String.format;

public class DBConnection {

    private static String url = "jdbc:sqlite:%s";
    private static File dbFile =  new File("flightdata.db");
    private static Connection conn;

    public static void setDatabaseFile(File file) {
        dbFile = file;
    }

    public static Connection getConnection() {
        try {
            conn = DriverManager.getConnection(format(url, dbFile.getAbsolutePath()));

            SQLiteConnectionPoolDataSource dataSource = new SQLiteConnectionPoolDataSource();
            dataSource.setUrl(url);
            ConnectionPoolManager poolMgr = new ConnectionPoolManager(dataSource, maxConnections);

            org.sqlite.SQLiteConfig config = new org.sqlite.SQLiteConfig();
            config.enforceForeignKeys(true);
            config.enableLoadExtension(true);
            dataSource.setConfig(config);


        } catch (SQLException e) {
            System.out.println("Failed to create the database connection.");
        }
        return conn;
    }

}
