package seng202.team5.table;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;


/**
 * DataTable class
 * Used as a skeleton class for all of the different data tables
 *
 * @author Jack Ryan
 */
public abstract class DataTable {

    // Storing the original data table (usually all data for a certain table),
    // as well as the filtered data (the data intended to be diplayed).
    protected final ResultSet orgData;
    protected static ArrayList<ArrayList<Object>> originalDataArrayList;
    protected static ArrayList<ArrayList<Object>> filteredData;


    /**
     * Constructor for dataTable
     *
     * @param newOrgData - The full range of possible data to be displayed, as a resultSet
     */
    public DataTable(ResultSet newOrgData) {
        orgData = newOrgData;
    }


    /**
     * A method used to transform the original data of any type of original data
     * into a array list of arraylists which is the stored in the filtered data
     * @throws SQLException
     */
    public void createTable() throws SQLException {
        // Retrieves all of the meta data of the original data resultSet

        System.out.print(orgData);
        if (orgData != null) {
            ResultSetMetaData md = orgData.getMetaData();
            // Gets the number of columns (ie the number of variables)
            int columns = md.getColumnCount();
            // Initializing an arraylist of arraylists to store the extracted data in
            ArrayList<ArrayList<Object>> list = new ArrayList<ArrayList<Object>>();
            // Iterates through the result set
            while(orgData.next()) {
                // An arraylist of each instance of the data type
                ArrayList<Object> row = new ArrayList<>(columns);
                // Iterates through the data, storing it in the arraylist
                for (int i=1; i<=columns; ++i) {
                    row.add(orgData.getObject(i));
                }
                // Adds the extracted data to the overall arraylist of data
                list.add(row);
            }
            // Sets the filtered data to the new Arraylist of arraylists
            filteredData = new ArrayList<>(list);
            originalDataArrayList = new ArrayList<>(list);
        } else {
            filteredData = new ArrayList<ArrayList<Object>>();
            originalDataArrayList = new ArrayList<ArrayList<Object>>();
        }

    }


    /**
     * Getter for the filtered data
     * @return ArrayList<ArrayList<Object>>
     */
    public ArrayList<ArrayList<Object>> getData() {
        return filteredData;
    }

}
