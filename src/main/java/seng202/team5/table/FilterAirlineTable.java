package seng202.team5.table;

import java.util.ArrayList;

/**
 * FilterAirlineTable
 *
 * Contains the functions filterTable, checks for if the data in the row, and setters.
 * Extends the ConcreteFilterTable abstract class.
 *
 * @author Inga Tokarenko
 */
public class FilterAirlineTable extends ConcreteFilterTable {

    private ArrayList<String> countries;
    private String active;
    private boolean remove;

    /**
     * Constructor for FilterAirlineTable.
     * Sets the elements.
     *
     * @param data The ArrayList that is passed through with information from the table.
     *
     * @author Inga Tokarenko
     */
    public FilterAirlineTable(ArrayList data) { super(data); }

    /**
     * Overrides the the parent class.
     * Goes through each element and checks weather it matches any criteria that was set at the beginning.
     * If an element does not match the criteria it is removed from the elements array and the currentPos is reset back to zero.
     *
     * @author Inga Tokarenko
     */
    @Override
    public void filterTable() {
        ArrayList<Object> current;
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

    /**
     * Checks weather the current country is in the set counties list.
     * Sets the remove to true if the country does not match any elements in the countries list.
     *
     * @param country the country from the current element.
     *
     * @author Inga Tokarenko
     */
    public void containsCountry(String country) {
        if (!countries.contains(country)) {
            remove = true;
        }
    }

    /**
     * Checks weather the current active status equals to the set active status.
     * Sets the remove to true if the active statuses don't match.
     *
     * @param currentActive the active status from the current element.
     *
     * @author Inga Tokarenko
     */
    public void containsActive(String currentActive) {
        if (currentActive != active) {
            remove = true;
        }
    }

    public void setCountries(ArrayList<String> newCountries) {
        countries = newCountries;
    }

    public void setActive(String newActive) {
        active = newActive;
    }
}
