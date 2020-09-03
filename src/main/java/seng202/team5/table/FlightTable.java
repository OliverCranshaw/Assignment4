package seng202.team5.table;

import seng202.team5.database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlightTable extends DataTable {

    public FlightTable(ResultSet newOrgData) {
        super(newOrgData);
    }

    @Override
    public void createTable() throws SQLException {
        List<Integer> addedFlights = new ArrayList<>();
        ResultSetMetaData md = orgData.getMetaData();
        int columns = md.getColumnCount();
        ArrayList<ArrayList<Object>> list = new ArrayList<ArrayList<Object>>();
        while(orgData.next()) {

            ArrayList<Object> row = new ArrayList<>(columns);
            for (int i=1; i<=columns; ++i) {
                row.add(orgData.getObject(i));
            }



        }
        filteredData = list;
    }


    public void FilterTable() {

    }

    public static void main(String[] args) throws SQLException {
        ResultSet result = null;
        Connection dbHandler = DBConnection.getConnection();
        String query = "SELECT * FROM FLIGHT_DATA";
        PreparedStatement stmt = dbHandler.prepareStatement(query);
        result = stmt.executeQuery();
        FlightTable tab = new FlightTable(result);
        tab.createTable();
        System.out.println(filteredData);

    }


}
