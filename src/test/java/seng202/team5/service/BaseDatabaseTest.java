package seng202.team5.service;

import org.junit.After;
import org.junit.Before;
import seng202.team5.database.DBConnection;
import seng202.team5.database.DBInitializer;
import seng202.team5.database.DBTableInitializer;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDatabaseTest {

    private static final String dbFile = "test.db";

    @Before
    public void setUp() {
        System.out.println("Setup");
        DBInitializer.createNewDatabase(dbFile);
        DBTableInitializer.initializeTables(dbFile);

        DBConnection.setDatabaseFile(new File(dbFile));
    }

    @After
    public void tearDown() throws SQLException {
        System.out.println("Tear down");
        Connection conn = DBConnection.getConnection();
        conn.close();

        new File(dbFile).delete();
    }
}
