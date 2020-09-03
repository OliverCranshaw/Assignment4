package seng202.team5.table;

import java.util.ArrayList;

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
            String country = (String) current.get(3);

            if (!countries.contains(country)) {
                elements.remove((currentPos));
                currentPos = 0;
            }
        }
    }

    public void setCountries(ArrayList newCountries) {
        countries = newCountries;
    }
}
