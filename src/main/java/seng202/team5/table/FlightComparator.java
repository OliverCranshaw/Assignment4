package seng202.team5.table;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * FlightComparator
 *
 * A class that implements the Comparator<ArrayList<Object>>
 * Is used to compare flight entries so that they can be sorted according to flightID,
 * unless flightID is the same, the sorted by ID
 */
public class FlightComparator implements Comparator<ArrayList<Object>> {

    @Override
    public int compare(ArrayList<Object> o1, ArrayList<Object> o2) {
        if ((int) o1.get(1) == (int) o2.get(1)) {
            return (int) o1.get(1) - (int) o2.get(1);
        } else {
            return (int) o1.get(0) - (int) o2.get(0);
        }

    }
}
