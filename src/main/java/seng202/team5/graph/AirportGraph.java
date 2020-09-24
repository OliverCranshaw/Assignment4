package seng202.team5.graph;

import java.util.ArrayList;

public class AirportGraph implements GraphBuilder {

    private ArrayList<ArrayList<Object>> data;
    private String selection;

    public AirportGraph(ArrayList<ArrayList<Object>> data) {
        this.data = data;
    }

    public void buildGraph() {
        switch (selection) {
            case "":
                break;
        }
    }

    public void airportRouteGraph(){}

    public void airportCountryGraph(){}

    public void setSelection(String selection) {
        this.selection = selection;
    }
}
