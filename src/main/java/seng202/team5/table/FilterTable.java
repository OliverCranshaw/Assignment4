package seng202.team5.table;

import java.util.ArrayList;

/**
 * FilterTable
 *
 * Contains the functions filterTable, getNext, hasMore.
 */
public interface FilterTable {

    /**
     * Filters the ArrayList from table.
     */
    void filterTable();

    /**
     * Gets and returns the next item in the elements list.
     * Increments the current position in the array.
     *
     * @return ArrayList that contains the data of the next element.
     */
    ArrayList getNext();

    /**
     * Checks if there are any more elements in the array.
     *
     * @return boolean that if true then there are still more elements left in the list otherwise false.
     */
    boolean hasMore();
}
