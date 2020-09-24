package seng202.team5.graph;

import java.util.ArrayList;

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

    public void routeEquipmentGraph() {}

    public void setSelection(String selection) {
        this.selection = selection;
    }
}
