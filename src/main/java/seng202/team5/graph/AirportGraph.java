package seng202.team5.graph;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import seng202.team5.service.AirportService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

public class AirportGraph implements GraphBuilder {

    private ArrayList<ArrayList<Object>> data;
    private AirportService service;
    private String selection;

    public AirportGraph(ArrayList<ArrayList<Object>> data) {
        this.data = data;
        service = new AirportService();
    }

    public ObservableList<PieChart.Data> buildChart() throws SQLException {
        switch (selection) {
            case "AirportRoute":
                return airportRouteGraph();
            case "AirportCountry":
                return airportCountryGraph();
        }
        return null;
    }

    public ObservableList<PieChart.Data> airportRouteGraph() throws SQLException {
        Hashtable<String, Integer> routeCounts = new Hashtable<String, Integer>();
        for (ArrayList<Object> airport : data) {
            String airportName = (String) airport.get(1);
            int airportID = (int) airport.get(0);
            int routeCount = service.getIncRouteCount(airportID) + service.getOutRouteCount(airportID);

            routeCounts.put(airportName, routeCount);
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (String airport : routeCounts.keySet()) {
            pieChartData.add(new PieChart.Data(airport, routeCounts.get(airport)));
        }

        return sortList(pieChartData);
    }

    public ObservableList<PieChart.Data> airportCountryGraph(){
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

        return sortList(pieChartData);
    }

    public ObservableList<PieChart.Data> sortList(ObservableList<PieChart.Data> pieChartData) {
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

    public void setSelection(String selection) {
        this.selection = selection;
    }
}
