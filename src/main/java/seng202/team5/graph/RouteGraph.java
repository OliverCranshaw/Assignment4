package seng202.team5.graph;

import java.util.ArrayList;
import java.util.Hashtable;

public class RouteGraph implements GraphBuilder { // pie chart

    private ArrayList<ArrayList<Object>> data;
    private String selection;

    public RouteGraph(ArrayList<ArrayList<Object>> data) {
        this.data = data;
    }

    public void buildGraph() {
        switch (selection) {
            case "":
                break;
        }
    }

    public void routeEquipmentGraph() {
        Hashtable<String, Integer> equipmentCounts = new Hashtable<String, Integer>();
        for (ArrayList<Object> route : data) {
            ArrayList<String> equipment = (ArrayList<String>) route.get(9);
            for (String equip : equipment) {
                if (equipmentCounts.containsKey(equip)) {
                    equipmentCounts.put(equip, equipmentCounts.get(equip) + 1);
                } else  {
                    equipmentCounts.put(equip, 1);
                }
            }
        }


    }

    public void setSelection(String selection) {
        this.selection = selection;
    }
}
