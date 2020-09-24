package seng202.team5.graph;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

public class AirportGraph implements GraphBuilder {

    private ArrayList<ArrayList<Object>> data;
    private String selection;

    public AirportGraph(ArrayList<ArrayList<Object>> data) {
        this.data = data;
    }

    public ObservableList<PieChart.Data> buildGraph() {
        switch (selection) {
            case "":
                return airportRouteGraph();
        }
        return null;
    }

    public ObservableList<PieChart.Data> airportRouteGraph(){
        Hashtable<String, Integer> countryCounts = new Hashtable<String, Integer>();
        for (ArrayList<Object> airport : data) {
            String country = (String) airport.get(3);
            if (countryCounts.containsKey(country)) {
                countryCounts.put(country, countryCounts.get(country) + 1);
            } else {
                countryCounts.put(country, 1);
            }
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (String country : countryCounts.keySet()) {
            pieChartData.add(new PieChart.Data(country, countryCounts.get(country)));
        }

        Comparator<PieChart.Data> pieChartDataComparator = Comparator.comparing(PieChart.Data::getPieValue);

        pieChartData.sort(pieChartDataComparator.reversed());
        ObservableList<PieChart.Data> toReturn = FXCollections.observableArrayList(pieChartData.subList(0, 15));
        List<PieChart.Data> other = pieChartData.subList(15, pieChartData.size() - 1);
        Integer num = 0;
        double count = 0;
        for (PieChart.Data data : other) {
            count = count + data.getPieValue();
            num++;
        }
        toReturn.add(new PieChart.Data("Other (" + num.toString() + ")", count));
        return toReturn;
    }




    public void airportCountryGraph(){}

    public void setSelection(String selection) {
        this.selection = selection;
    }
}
