package seng202.team5.table;

import java.util.ArrayList;

/**
 * FilterRouteTable
 *
 * Contains the functions filterTable, checks for if the data in the row, and setters.
 * Extends the ConcreteFilterTable abstract class.
 *
 * @author Inga Tokarenko
 */
public class FilterRouteTable extends ConcreteFilterTable {

    private String airportDep;
    private String airportDes;
    private String direct;
    private ArrayList<String> equip;
    private boolean remove;

    /**
     * Constructor for FilterRouteTable.
     * Sets the elements.
     *
     * @param data The ArrayList that is passed through with information from the table.
     *
     * @author Inga Tokarenko
     */
    public FilterRouteTable(ArrayList<ArrayList<Object>> data) {
        super(data);
    }

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
            String currentDep = (String) current.get(3);
            String currentDes = (String) current.get(5);
            int stops = (int) current.get(8);
            ArrayList<String> currentEquip = (ArrayList<String>) current.get(9);

            if (airportDep != null) {
                containsAirportDep(currentDep);
            }
            if (airportDes != null) {
                containsAirportDes(currentDes);
            }
            if (direct != null) {
                isDirect(stops);
            }
            if (equip != null) {
                containsEquip(currentEquip);
            }
            if (remove) {
                elements.remove((currentPos));
                currentPos = 0;
                remove = false;
            } else {
                currentPos++;
            }
        }
    }

    /**
     * Checks weather the current airportDep equals to the set airportDep.
     * Sets the remove to true if the airports don't match.
     *
     * @param currentDep the airportDep from the current element.
     *
     * @author Inga Tokarenko
     */
    public void containsAirportDep(String currentDep) {
        if (!currentDep.equals(airportDep)) {
            remove = true;
        }
    }

    /**
     * Checks weather the current airportDes equals to the set airportDes.
     * Sets the remove to true if the airports don't match.
     *
     * @param currentDes the airportDes from the current element.
     *
     * @author Inga Tokarenko
     */
    public void containsAirportDes(String currentDes) {
        System.out.println(currentDes);
        if (!currentDes.equals(airportDes)) {
            remove = true;
        }
    }

    /**
     * Checks weather the selection is direct is direct or not direct.
     * Sets the remove to true depending on the number of stops.
     *
     * @param stops the number of stops from the current element.
     *
     * @author Inga Tokarenko
     */
    public void isDirect(int stops) {
        if (direct == "direct") {
            if (stops > 0) {
                remove = true;
            }
        } else if (direct == "not direct") {
            if (stops <= 0) {
                remove = true;
            }
        }
    }

    /**
     * Checks weather the current equipment equals to the set equipment.
     * Sets the remove to true if the equipments don't match.
     *
     * @param currentEquip the equipment from the current element.
     *
     * @author Inga Tokarenko
     */
    public void containsEquip(ArrayList<String> currentEquip) {
        for (Object value : equip) {
            if (!currentEquip.contains(value)) {
                remove = true;
            }
        }
    }

    /**
     * Sets the airportDep to newAirport.
     *
     * @param newAirport the airport iata/icao.
     *
     * @author Inga Tokarenko
     */
    public void setAirportDep(String newAirport) {
        airportDep = newAirport;
    }

    /**
     * Sets the airportDes to newAirport.
     *
     * @param newAirport the airport iata/icao.
     *
     * @author Inga Tokarenko
     */
    public void setAirportDes(String newAirport) {
        airportDes = newAirport;
    }

    /**
     * Sets the direct to newDirect.
     *
     * @param newDirect String that contains either direct or not direct string.
     *
     * @author Inga Tokarenko
     */
    public void setDirect(String newDirect) {
        direct = newDirect;
    }

    /**
     * Sets the equip to newEquip.
     *
     * @param newEquip the equipment.
     *
     * @author Inga Tokarenko
     */
    public void setEquip(ArrayList<String> newEquip) { equip = newEquip;
    }
}
