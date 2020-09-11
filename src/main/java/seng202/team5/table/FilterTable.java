package seng202.team5.table;

import java.util.ArrayList;

/**
 * FilterTable
 *
 * Contains the functions filterTable, getNext, hasMore.
 *
 * @author Inga Tokarenko
 */
public interface FilterTable {

    /**
     * Filters the ArrayList from table.
     *
     * @author Inga Tokarenko
     */
    void filterTable();

    /**
     * Gets and returns the next item in the elements list.
     * Increments the current position in the array.
     *
     * @return ArrayList that contains the data of the next element.
     *
     * @author Inga Tokarenko
     */
    ArrayList getNext();

    /**
     * Checks if there are any more elements in the array.
     *
     * @return boolean that if true then there are still more elements left in the list otherwise false.
     *
     * @author Inga Tokarenko
     */
    boolean hasMore();
}
