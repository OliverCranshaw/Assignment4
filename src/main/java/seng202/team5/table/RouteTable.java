package seng202.team5.table;

import java.sql.ResultSet;


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
    public void FilterTable(String srcAirport, String dstAirport, Integer stops, String equipment) {
        // Creating a new instance of the FilterRouteTable
        FilterRouteTable filter = new FilterRouteTable(filteredData);
        // Setting the filter to the given source airport, destination airport, stops and equipment
        filter.setAirportDep(srcAirport);
        filter.setAirportDes(dstAirport);
        String direct = (stops == 0) ? "direct" : "not direct";
        filter.setDirect(direct);
        filter.setEquip(equipment);
        // Filtering the table, modifying the filteredData arraylist
        filter.filterTable();
    }
}
