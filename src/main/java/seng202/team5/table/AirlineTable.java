package seng202.team5.table;

import seng202.team5.accessor.AirlineAccessor;
import seng202.team5.database.DBConnection;
import seng202.team5.service.AirlineService;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class AirlineTable extends DataTable {



    public AirlineTable(ResultSet newOrgData) {
        super(newOrgData);
    }


    public void FilterTable(ArrayList<String> countries, String active) {

    }

    public static void main(String args[]) throws SQLException {
        String name = "AirportName";
        String alias = "AliasName";
        String country = "CountryName";
        String iata = "FT";
        String icao = "FAF";
        String callsign = "CallsignStuff";
        String active = "Y";
        AirlineService airlineService = new AirlineService();
        int res = airlineService.saveAirline(name, alias, iata, icao, callsign, country, active);
        System.out.println(res);
        ResultSet result = null;
        Connection dbHandler = DBConnection.getConnection();;
        String query = "SELECT * FROM AIRLINE_DATA";
        PreparedStatement stmt = dbHandler.prepareStatement(query);
        result = stmt.executeQuery();
        AirlineTable tab = new AirlineTable(result);
        tab.createTable();
        System.out.println(filteredData);
    }


}
