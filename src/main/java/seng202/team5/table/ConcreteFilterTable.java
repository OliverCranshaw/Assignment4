package seng202.team5.table;

import java.util.ArrayList;

/**
 * ConcreteFilterTable
 *
 * Contains the functions filterTable, getNext, hasMore.
 * Implements the FilterTable interface.
 *
 * @author Inga Tokarenko
 */
public class ConcreteFilterTable implements FilterTable {

    public ArrayList<ArrayList<Object>> elements;
    public int currentPos;

    /**
     * Constructor for ConcreteFilterTable.
     * Sets the elements.
     *
     * @param data The ArrayList that is passed through with information from the table.
     *
     * @author Inga Tokarenko
     */
    public ConcreteFilterTable(ArrayList<ArrayList<Object>> data) {
        elements = data;
    }

    /**
     * Parents function that does not contain any functionality in the parent class.
     *
     * @author Inga Tokarenko
     */
    public void filterTable() {}

    /**
     * Gets and returns the next item in the elements list.
     * Increments the current position in the array.
     *
     * @return ArrayList that contains the data of the next element.
     *
     * @author Inga Tokarenko
     */
    public ArrayList<Object> getNext() {
        if (hasMore()) {
            currentPos++;
            return elements.get(currentPos);
        }
        return null;
    }

    /**
     * Checks if there are any more elements in the array.
     *
     * @return boolean that if true then there are still more elements left in the list otherwise false.
     *
     * @author Inga Tokarenko
     */
    public boolean hasMore() {
        return currentPos != elements.size();
    }
}
