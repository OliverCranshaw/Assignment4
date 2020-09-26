package seng202.team5.table;

import java.sql.*;
import java.util.ArrayList;

/**
 * AirlineTable
 *
 * A class that extends DataTable and is used to store airline data to be used for display
 * in the GUI, as well as providing methods that use th filtering tables to filter for a desired
 * subset of data
 */
public class AirlineTable extends DataTable {

    /**
     * Constructor for AirlineTable
     *
     * @param newOrgData ResultSet of full set of data
     */
    public AirlineTable(ResultSet newOrgData) {
        super(newOrgData);
    }

    /**
     * filterTable(arraylist, string)
     *
     * A method that calls all relevant methods and passes in all parameters required to filter the
     *  filteredData by the given parameters
     *
     * @param countries ArrayList(String) - ArrayList of countries to
     * @param active String - "Y" if airline currently active "N" otherwise.
     */
    public void FilterTable(ArrayList<String> countries, String active) {
        filteredData = new ArrayList<>(originalDataArrayList);
        // Creating a new instance of the filterAirlineTable
        FilterAirlineTable filter = new FilterAirlineTable(filteredData);
        // Setting the filter to the given countries and active status
        filter.setCountries(countries);
        filter.setActive(active);
        // Filtering the table, modifying the filteredData arraylist
        filter.filterTable();
    }
}
