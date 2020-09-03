package seng202.team5.table;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

public abstract class DataTable {

    protected final ResultSet orgData;
    protected static ArrayList<ArrayList> filteredData;

    public DataTable(ResultSet newOrgData) {
        orgData = newOrgData;
    }

    public void createTable() throws SQLException {
        ResultSetMetaData md = orgData.getMetaData();
        int columns = md.getColumnCount();
        ArrayList<ArrayList> list = new ArrayList<>();
        while(orgData.next()) {
            ArrayList<Object> row = new ArrayList<>(columns);
            for (int i=1; i<=columns; ++i) {
                row.add(orgData.getObject(i));
            }
            list.add(row);
        }
        filteredData = list;
    }

}
