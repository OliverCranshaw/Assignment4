package seng202.team5.accessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface Accessor {

    int save(List<Object> data);

    boolean delete(int id);

    ResultSet getData(int id);

}
