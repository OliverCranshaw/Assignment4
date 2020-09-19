package seng202.team5.accessor;

import java.sql.ResultSet;
import java.util.List;

public interface Accessor {

    /**
     * Creates an data in the database with the given data.
     *
     * @param data An List containing the data to be inserted into an entry in the database.
     * @return int result The data id of the data that was just created.
     */
    int save(List<Object> data);

    /**
     * Deletes a given data entre from the database.
     *
     * @param id The data id of the data to be deleted.
     * @return boolean result True if the delete operation is successful, False otherwise.
     */
    boolean delete(int id);

    /**
     * Retrieves the data with the provided id.
     *
     * @param id int id of a data.
     * @return ResultSet of the route data.
     */
    ResultSet getData(int id);
}
