package seng202.team5.table;

import java.util.ArrayList;
import java.util.Set;

public class FilterAirlineTable extends ConcreteFilterTable {

    private ArrayList countries;
    private String active;
    private boolean remove;

    public FilterAirlineTable(ArrayList data) {
        super(data);
    }

    @Override
    public void FilterTable() {
        ArrayList current;
        currentPos = 0;
        remove = false;

        while (hasMore()) {
            current = elements.get(currentPos);
            String country = (String) current.get(6);
            String currentActive = (String) current.get(7);

            if (countries != null) {
                containsCountry(country);
            }
            if (active != null) {
                containsActive(currentActive);
            }
            if (remove) {
                elements.remove((currentPos));
                currentPos = 0;
                remove = false;
            }
        }
    }

    public void containsCountry(String country) {
        if (!countries.contains(country)) {
            remove = true;
        }
    }

    public void containsActive(String currentActive) {
        if (currentActive != active) {
            remove = true;
        }
    }

    public void setCountries(ArrayList newCountries) {
        countries = newCountries;
    }

    public void setActive(String newActive) {
        active = newActive;
    }
}
