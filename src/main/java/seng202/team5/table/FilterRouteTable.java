package seng202.team5.table;

import java.util.ArrayList;
public class FilterRouteTable extends ConcreteFilterTable {

    private String airportDep;
    private String airportDes;
    private String direct;
    private String equip;
    private boolean remove;

    public FilterRouteTable(ArrayList data) {
        super(data);
    }

    @Override
    public void FilterTable() {
        ArrayList current;
        currentPos = 0;
        remove = false;

        while (hasMore()) {
            current = elements.get(currentPos);
            String currentDep = (String) current.get(3);
            String currentDes = (String) current.get(5);
            int stops = (int) current.get(8);
            String currentEquip = (String) current.get(9);

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
            }
        }
    }

    public void containsAirportDep(String currentDep) {
        if (currentDep != airportDep) {
            remove = true;
        }
    }

    public void containsAirportDes(String currentDes) {
        if (currentDes != airportDes) {
            remove = true;
        }
    }

    public void isDirect(int stops) {
        if (direct == "direct") {
            remove = stops > 0 ? true : false;
        } else if (direct == "not direct") {
            remove = stops > 0 ? false : true;
        }
    }

    public void containsEquip(String currentEquip) {
        if (currentEquip != equip) {
            remove = true;
        }
    }

    public void setAirportDep(String newAirport) {
        airportDep = newAirport;
    }

    public void setAirportDes(String newAirport) {
        airportDes = newAirport;
    }

    public void setDirect(String newDirect) {
        direct = newDirect;
    }

    public void setEquip(String newEquip) {
        equip = newEquip;
    }
}
