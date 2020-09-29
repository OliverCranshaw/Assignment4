package seng202.team5.graph;

import java.util.ArrayList;

public class FlightGraphChart implements GraphChartBuilder {

    private ArrayList<ArrayList<Object>> data;
    private String selection;

    public FlightGraphChart(ArrayList<ArrayList<Object>> data) {
        this.data = data;
    }

    public void buildGraph() {
        switch (selection) {
            case "":
                break;
        }
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }
}
