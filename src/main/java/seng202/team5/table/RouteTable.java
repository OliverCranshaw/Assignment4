package seng202.team5.table;

import java.sql.ResultSet;

public class RouteTable extends DataTable {

    public RouteTable(ResultSet newOrgData) {
        super(newOrgData);
    }

    public void FilterTable(String srcAirport, String dstAirport, Integer stops, String equipment) {
        FilterRouteTable filter = new FilterRouteTable(filteredData);
        filter.setAirportDep(srcAirport);
        filter.setAirportDes(dstAirport);
        String direct = (stops == 0) ? "direct" : "not direct";
        filter.setDirect(direct);
        filter.setEquip(equipment);
    }
}
