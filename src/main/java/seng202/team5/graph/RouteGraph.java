package seng202.team5.graph;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import seng202.team5.service.RouteService;

import java.util.*;

public class RouteGraph implements GraphBuilder {

    private ArrayList<ArrayList<Object>> data;
    private String selection;
    private RouteService routeService;

    public RouteGraph(ArrayList<ArrayList<Object>> data) {
        this.data = data;
    }

    public ObservableList<PieChart.Data> buildPieGraph(String selection) {
        this.selection = selection;
        switch (selection) {
            case "RouteEquipment":
                return routeEquipmentGraph();
            case "RouteAirline":
                return routeAirlineGraph();
        }
        return null;
    }


    public XYChart.Series buildBarGraph(String selection) {
        XYChart.Series toReturn = new XYChart.Series();
        for (ArrayList<Object> route : data) {

        }
        return toReturn;
    }


    private ObservableList<PieChart.Data> routeAirlineGraph() {
        return null;
    }

    public ObservableList<PieChart.Data> routeEquipmentGraph() {
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
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (String equip : equipmentCounts.keySet()) {
            pieChartData.add(new PieChart.Data(equip, equipmentCounts.get(equip)));
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

    public void setSelection(String selection) {
        this.selection = selection;
    }


}
