package seng202.team5.table;

import seng202.team5.database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * FlightTable
 *
 * A class
 */
public class FlightTable extends DataTable {

    public FlightTable(ResultSet newOrgData) {
        super(newOrgData);
    }

    @Override
    public void createTable() throws SQLException {
        ArrayList<ArrayList<Object>> list = createUnreducedTable();
        List<List<Integer>> currAdded = new ArrayList<>();
        for (ArrayList<Object> flightSeg : list) {
            int altitude = (int) flightSeg.get(4);
            int ID = (int) flightSeg.get(0);
            int flightID = (int) flightSeg.get(1);
            if (altitude == 0) {
                boolean isIn = false;
                for (List<Integer> flightInf : currAdded) {
                    if (flightInf.get(1) == flightID) {
                        isIn = true;
                        if (ID < flightInf.get(0)) {
                            currAdded.remove(flightInf);
                            currAdded.add(Arrays.asList(ID, flightID));
                        }
                    }
                }
                if (!isIn) {
                    currAdded.add(Arrays.asList(ID, flightID));
                }
            }
        }
        for (ArrayList<Object> flightSeg : list) {
            boolean flightUsed = false;
            int ID = (int) flightSeg.get(0);
            for (List<Integer> flightInf : currAdded) {
                if (flightInf.get(0) == ID) {
                    flightUsed = true;
                    break;
                }
            }

            if (!flightUsed) {
                list.remove(flightSeg);
            }
        }

        filteredData = list;


    }



    public ArrayList<ArrayList<Object>> createUnreducedTable() throws SQLException {
        List<Integer> currentFlights = new ArrayList<>();
        ResultSetMetaData md = orgData.getMetaData();
        int columns = md.getColumnCount();
        ArrayList<ArrayList<Object>> list = new ArrayList<ArrayList<Object>>();
        while(orgData.next()) {
            ArrayList<Object> row = new ArrayList<>(columns);
            for (int i=1; i<=columns; ++i) {
                row.add(orgData.getObject(i));
            }
            list.add(row);
        }
        return list;
    }


    public static void main(String[] args) throws SQLException {
        String query = "SELECT * FROM FLIGHT_DATA";
        Connection dbHandler = DBConnection.getConnection();
        PreparedStatement stmt = dbHandler.prepareStatement(query);
        ResultSet result = stmt.executeQuery();
        FlightTable flights = new FlightTable(result);
        flights.createTable();
        System.out.println(flights.getData());



    }
}
