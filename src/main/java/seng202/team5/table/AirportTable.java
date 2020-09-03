package seng202.team5.table;

import seng202.team5.database.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class AirportTable extends DataTable {

    public AirportTable(ResultSet newOrgData) {
        super(newOrgData);
    }



    public void FilterTable(ArrayList<String> countries) {

    }

    public static void main(String args[]) throws SQLException {
        ResultSet result = null;
        Connection dbHandler = DBConnection.getConnection();;
        String query = "SELECT * FROM AIRPORT_DATA";
        PreparedStatement stmt = dbHandler.prepareStatement(query);
        result = stmt.executeQuery();
        AirportTable tab = new AirportTable(result);
        tab.createTable();
        System.out.println(filteredData);
    }
}
