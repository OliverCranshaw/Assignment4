package seng202.team5.table;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * RouteTable
 *
 * A class that extends DataTable and is used to store route data to be used for display
 * in the GUI, as well as providing methods that use the filtering tables to filter for a desired
 * subset of data
 *
 * @author Jack Ryan
 */
public class RouteTable extends DataTable {

    /**
     * Constructor for RouteTable
     *
     * @param newOrgData ResultSet of full set of data
     */
    public RouteTable(ResultSet newOrgData) {
        super(newOrgData);
    }



    @Override
    public void createTable() throws SQLException {
        // Retrieves all of the meta data of the original data resultSet
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
                if (i != 10) {
                    row.add(orgData.getObject(i));
                } else {
                    // Converts the Equipment String into an ArrayList of strings
                    String equipString = (String) orgData.getObject(i);
                    String[] equipment = equipString.split(" ");
                    ArrayList<String> equipArray = new ArrayList<>(Arrays.asList(equipment));
                    row.add(equipArray);
                }
            }
            // Adds the extracted data to the overall arraylist of data
            list.add(row);
        }
        // Sets the filtered data to the new Arraylist of arraylists
        filteredData = list;
        originalDataArrayList = list;
    }



    /**
     * filterTable(ArrayList)
     *
     * A method that calls all relevant methods and passes in all parameters required to filter the
     * filteredData by the given parameters
     *
     * @param srcAirport String - The 3-letter IATA code or 4-letter ICAO code of the source airport
     * @param dstAirport String - The 3-letter IATA code or 4-letter ICAO code of the destination airport
     * @param stops Integer - The number of stops for the flight, 0 if it is direct.
     * @param equipment String - 3-letter codes for plane types(s) commonly used for this flight, separated by spaces
     */
    public void FilterTable(String srcAirport, String dstAirport, String stops, ArrayList<String> equipment) {
        filteredData = new ArrayList<>(originalDataArrayList);
        // Creating a new instance of the FilterRouteTable
        FilterRouteTable filter = new FilterRouteTable(filteredData);
        // Setting the filter to the given source airport, destination airport, stops and equipment
        filter.setAirportDep(srcAirport);
        filter.setAirportDes(dstAirport);
        filter.setDirect(stops);
        filter.setEquip(equipment);
        // Filtering the table, modifying the filteredData arraylist
        filter.filterTable();
    }
}
