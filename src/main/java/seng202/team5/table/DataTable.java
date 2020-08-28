package seng202.team5.table;

import java.sql.ResultSet;

public abstract class DataTable {

    private final ResultSet orgData;
    private static ResultSet filteredData;

    public DataTable(ResultSet newOrgData) {
        orgData = newOrgData;
    }

}
