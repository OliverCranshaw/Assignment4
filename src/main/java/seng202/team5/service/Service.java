package seng202.team5.service;

import java.sql.ResultSet;

/**
 * Service
 *
 * Contains the functions delete, getData.
 */
public interface Service {

    /**
     * Checks if the data to be deleted exists, and if it does then passes the data id into the delete method of Accessor.
     *
     * @param id The data id of the data to be deleted.
     * @return boolean result True if the delete operation is successful, False otherwise.
     */
    boolean delete(int id);

    /**
     * Retrieves the data with specified id.
     *
     * @param id int value of an id.
     * @return ResultSet of data.
     */
    ResultSet getData(int id);
}
