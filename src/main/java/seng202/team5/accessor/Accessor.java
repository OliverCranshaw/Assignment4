package seng202.team5.accessor;

import java.sql.ResultSet;
import java.util.List;

public interface Accessor {

    int save(List<Object> data);

    boolean delete(int id);

    ResultSet getData(int id);

}
