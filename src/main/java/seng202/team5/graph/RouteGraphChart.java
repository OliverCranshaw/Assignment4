package seng202.team5.graph;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import seng202.team5.service.AirlineService;
import seng202.team5.service.RouteService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RouteGraphChart implements GraphChartBuilder {

    private ArrayList<ArrayList<Object>> data;
    private String selection;

    public RouteGraphChart(ArrayList<ArrayList<Object>> data) {
        this.data = data;
    }

    public ObservableList<PieChart.Data> buildPieGraph() {
        switch (selection) {
            case "RouteEquipment":
                return routeEquipmentGraph();
        }
        return null;
    }


    public XYChart.Series<String, Integer> buildBarGraph() throws SQLException {
        switch (selection) {
            case "RouteAirline":
                return routeAirlineGraph();
        }
        return null;
    }


    private XYChart.Series<String, Integer> routeAirlineGraph() throws SQLException {
        XYChart.Series<String, Integer> toReturn = new XYChart.Series<String, Integer>();
        RouteService routeService = new RouteService();
        for (ArrayList<Object> route : data) {
            ArrayList<Integer> counts = routeService.getAirlinesCoveringRoute((Integer) route.get(4), (Integer) route.get(6), true);
            String srcCode = (String) route.get(3);
            String dstCode = (String) route.get(5);
            toReturn.getData().add(new XYChart.Data<String, Integer>(srcCode + " - " + dstCode, counts.size()));
        }
        toReturn.getData().sort(Comparator.comparingInt(XYChart.Data::getYValue));
        System.out.println(toReturn);
        return toReturn;

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

        ObservableList<PieChart.Data> toReturn;
        Comparator<PieChart.Data> pieChartDataComparator = Comparator.comparing(PieChart.Data::getPieValue);

        pieChartData.sort(pieChartDataComparator.reversed());

        if (pieChartData.size() > 15) {
            toReturn = FXCollections.observableArrayList(pieChartData.subList(0, 15));
            List<PieChart.Data> other = pieChartData.subList(15, pieChartData.size() - 1);
            Integer num = 0;
            double count = 0;
            for (PieChart.Data data : other) {
                count = count + data.getPieValue();
                num++;
            }
            toReturn.add(new PieChart.Data("Other (" + num.toString() + ")", count));
        } else {
            toReturn = pieChartData;
        }


        return toReturn;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }


}
