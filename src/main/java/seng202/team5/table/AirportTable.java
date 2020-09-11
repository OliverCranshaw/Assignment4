package seng202.team5.table;

import seng202.team5.database.DBConnection;

import java.sql.*;
import java.util.ArrayList;


/**
 * AirportTable
 *
 * A class that extends DataTable and is used to store airport data to be used for display
 * in the GUI, as well as providing methods that use the filtering tables to filter for a desired
 * subset of the data.
 *
 * @author Jack Ryan
 */
public class AirportTable extends DataTable {

    /**
     * Constructor for AirportTable
     *
     * @param newOrgData ResultSet of full set of data
     */
    public AirportTable(ResultSet newOrgData) {
        super(newOrgData);
    }

    /**
     * filterTable(ArrayList)
     *
     * A method that calls all relevant methods and passes in all parameters required to filter the
     * filteredData by the given parameters
     *
     * @param countries ArrayList(String) - Arraylist of countries to filter by
     */
    public void FilterTable(ArrayList<String> countries) {
        filteredData = new ArrayList<>(originalDataArrayList);
        // Creating a new instance of the FilterAirportTable
        FilterAirportTable filter = new FilterAirportTable(filteredData);
        // Setting the filter to the countries given
        filter.setCountries(countries);
        // Filtering the table, modifying the filteredData arraylist
        filter.filterTable();
    }


}
