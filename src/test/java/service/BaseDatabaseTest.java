package service;

import junit.framework.TestCase;
import seng202.team5.database.DBConnection;
import seng202.team5.database.DBInitializer;
import seng202.team5.database.DBTableInitializer;
import seng202.team5.service.AirportService;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

abstract class BaseDatabaseTest extends TestCase {
    private static final String dbFile = "test.db";

    public BaseDatabaseTest(String testName) { super(testName); }

    @Override
    protected void setUp() {
        System.out.println("Setup");
        DBInitializer.createNewDatabase(dbFile);
        DBTableInitializer.initializeTables(dbFile);

        DBConnection.setDatabaseFile(new File(dbFile));
    }

    @Override
    protected void tearDown() throws SQLException {
        System.out.println("Tear down");
        Connection conn = DBConnection.getConnection();
        conn.close();

        new File(dbFile).delete();
    }
}