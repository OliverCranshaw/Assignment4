package seng202.team5.table;

import java.util.ArrayList;

/**
 * FilterAirportTable
 *
 * Contains the functions filterTable, checks for if the data in the row, and setters.
 * Extends the ConcreteFilterTable abstract class.
 */
public class FilterAirportTable extends ConcreteFilterTable {

    private ArrayList<String> countries;

    /**
     * Constructor for FilterAirportTable.
     * Sets the elements.
     *
     * @param data The ArrayList that is passed through with information from the table.
     */
    public FilterAirportTable(ArrayList<ArrayList<Object>> data) {
        super(data);
    }

    /**
     * Overrides the the parent class.
     * Goes through each element and checks weather it matches any criteria that was set at the beginning.
     * If an element does not match the criteria it is removed from the elements array and the currentPos is reset back to zero.
     */
    @Override
    public void filterTable() {
        ArrayList<Object> current;
        currentPos = 0;

        while (hasMore()) {
            current = elements.get(currentPos);
            String country = (String) current.get(3);


            if (countries != null) {
                if (!countries.contains(country)) {
                    elements.remove((currentPos));
                    currentPos = 0;
                } else {
                    currentPos++;
                }
            } else {
                currentPos++;
            }

        }
    }

    /**
     * Sets the countries to newCountries.
     *
     * @param newCountries array of countries.
     */
    public void setCountries(ArrayList<String> newCountries) {
        countries = newCountries;
    }

    /**
     * Gets elements and returns it.
     *
     * @return ArrayList of the elements.
     */
    public ArrayList<ArrayList<Object>> getElements() {
        return elements;
    }
}
