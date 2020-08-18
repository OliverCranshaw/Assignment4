package seng202.team5.accessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface Accessor {

    int save(ArrayList data);

    boolean delete(int id);

    ResultSet getData(int id);

}
