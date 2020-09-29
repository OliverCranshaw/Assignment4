package seng202.team5.table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FlightTable
 *
 * A class that extends DataTable and is used to store flight data to be used for display
 * in the GUI, as well as providing methods that use the filtering tables to filter for a desired
 * subset of the data.
 */
public class FlightTable extends DataTable {

    /**
     * Constructor for the RouteData
     *
     * @param newOrgData ResultSEet of full set of data
     */
    public FlightTable(ResultSet newOrgData) {
        super(newOrgData);
    }

    /**
     * createTable() method overridden from DataTable.
     * This method is overridden as the data to be shown in the gui is just the first entry
     * for each flight (each flight spans multiple entries), so the process of creating the table is different.
     *
     * @throws SQLException Caused by ResultSet interactions.
     */
    @Override
    public void createTable() throws SQLException {
        // Calling a method to get an arraylist of all the flight entries
        ArrayList<ArrayList<Object>> list = createUnreducedTable();
        // Sorting the flights by a custom comparator
        list.sort(new FlightComparator());
        // Preparing a list to hold currently found flightIds
        List<Integer> foundIDs = new ArrayList<>();
        // Preparing an ArrayList to hold the result
        ArrayList<ArrayList<Object>> result = new ArrayList<>();
        // Iterating through the list of all flight entries, adding it to the result when it is the first for each flight
        // Also adding the id, location type and location of each flight destination
        int index = 0;
        Integer currFlightId = null;
        for (ArrayList<Object> flight : list) {
            int flightID = (int) flight.get(1);
            if (!foundIDs.contains(flightID) && currFlightId != null) {
                result.get(result.size() - 1).add(list.get(index-1).get(0));
                result.get(result.size() - 1).add(list.get(index-1).get(2));
                result.get(result.size() - 1).add(list.get(index-1).get(3));
                foundIDs.add(flightID);
                result.add(flight);
                currFlightId = (Integer) flight.get(1);
            } else if (!foundIDs.contains(flightID)) {
                foundIDs.add(flightID);
                result.add(flight);
                currFlightId = (Integer) flight.get(1);
            }
            index++;
        }
        // Adding the extra data for the last result
        if (result.size() != 0) {
            result.get(result.size() - 1).add(list.get(list.size() - 1).get(0));
            result.get(result.size() - 1).add(list.get(list.size() - 1).get(2));
            result.get(result.size() - 1).add(list.get(list.size() - 1).get(3));
        }
        // Setting the filteredData to the result
        filteredData = result;
    }

    /**
     * createUnreducedTable() behaves as the createTable() method of the DataTable does,
     * Converting the resultSet orgData to an ArrayList of ArrayList of Objects.
     *
     * @return ArrayList(ArrayList(Object)) - ArrayList of FlightData.
     *
     * @throws SQLException Caused by ResultSet interactions.
     */
    public ArrayList<ArrayList<Object>> createUnreducedTable() throws SQLException {
        // Retrieves all of the meta data of the original data resultSet
        if (orgData != null) {
            ResultSetMetaData md = orgData.getMetaData();
            // Gets the number of columns (ie the number of variables)
            int columns = md.getColumnCount();
            // Initializing an arraylist of arraylists to store the extracted data in
            ArrayList<ArrayList<Object>> list = new ArrayList<>();
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
            return list;
        } else {
            return new ArrayList<>();
        }
    }
}
