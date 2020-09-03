package seng202.team5.table;

import java.util.ArrayList;
public class FilterRouteTable extends ConcreteFilterTable {

    private String airportDep = null;
    private String airportDes = null;
    private int stops = -1;
    private String equip = null;

    public FilterRouteTable(ArrayList data) {
        super(data);
    }

    @Override
    public void FilterTable() {
        ArrayList current;
        currentPos = 0;

        while (hasMore()) {
            current = elements.get(currentPos);
        }
    }

    public void setAirportDep(String newAirport) {
        airportDep = newAirport;
    }

    public void setAirportDes(String newAirport) {
        airportDes = newAirport;
    }

    public void setStops(int newStops) {
        stops = newStops;
    }

    public void setEquip(String newEquip) {
        equip = newEquip;
    }
}
