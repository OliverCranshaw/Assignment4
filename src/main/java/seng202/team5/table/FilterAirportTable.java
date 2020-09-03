package seng202.team5.table;

import java.util.ArrayList;
import java.util.Set;

public class FilterAirportTable extends ConcreteFilterTable {

    private ArrayList countries;

    public FilterAirportTable(ArrayList data) {
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

    public void setCountries(ArrayList newCountries) {
        countries = newCountries;
    }
}
