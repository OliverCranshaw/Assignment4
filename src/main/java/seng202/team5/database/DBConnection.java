package seng202.team5.database;

import org.sqlite.javax.SQLiteConnectionPoolDataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.String.format;

/**
 * DBConnection
 *
 * Creates and establishes connection with the database.
 */
public class DBConnection {

    private static String url = "jdbc:sqlite:%s";
    private static File dbFile =  new File("flightdata.db");
    private static Connection conn;

    /**
     * Sets the file of the database.
     *
     * @param file File file that was chosen to contain the database.
     */
    public static void setDatabaseFile(File file) {
        dbFile = file;
    }

    // refactored so that the same connection - if already opened - will be returned whenever it is requested. Otherwise, if
    // nonexistent (null) or in case it has been previously closed - a new one will be opened and returned.
    public static Connection getConnection() {
        try {
            if( conn == null || conn.isClosed() ){
                conn = DriverManager.getConnection(format(url, dbFile.getAbsolutePath()));
                SQLiteConnectionPoolDataSource dataSource = new SQLiteConnectionPoolDataSource();
                dataSource.setUrl(url);
                org.sqlite.SQLiteConfig config = new org.sqlite.SQLiteConfig();
                config.enforceForeignKeys(true);
                config.enableLoadExtension(true);
                dataSource.setConfig(config);
                conn.createStatement().execute("PRAGMA foreign_keys = ON");
            }
        } catch (SQLException e) {
            // Whenever using PrintStream statements to log errors, the 'err' property should be used instead of the default
            // 'out'. Nevertheless, some sort of logger library (such as log4j) would be a more appropriate  solution for logs
            // than PrintStream statements.
            System.err.println("Failed to create the database connection.");
        }
        return conn;
    }

}
