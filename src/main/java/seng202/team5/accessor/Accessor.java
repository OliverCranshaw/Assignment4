package seng202.team5.accessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface Accessor {

    int save(ArrayList data) throws SQLException;

    boolean delete(int id) throws SQLException;

    ResultSet getData(int id) throws SQLException;

}
