package seng202.team5.graph;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import seng202.team5.accessor.RouteAccessor;
import seng202.team5.service.RouteService;

import java.sql.SQLException;
import java.util.*;

/**
 * RouteGraphChart
 *
 * A class used to setup data for route graph charts.
 * Implements from GraphChartBuilder.
 */
public class RouteGraphChart implements GraphChartBuilder {

    // ArrayList of data before it has been setup for use in graph charts.
    private ArrayList<ArrayList<Object>> data;
    // Selection string that is used to decide on the type of chart made
    private String selection;

    /**
     * Constructor for RouteGraphChart.
     *
     * @param data - ArrayList of ArrayList of Objects.
     */
    public RouteGraphChart(ArrayList<ArrayList<Object>> data) {
        this.data = data;
    }

    /**
     * Calls the appropriate graph build function depending on the selection,
     * returning the data required to build a pie chart from it.
     *
     * @return ObservableList of PieChart.Data objects.
     */
    public ObservableList<PieChart.Data> buildChart() {
        ObservableList<PieChart.Data> result = FXCollections.observableArrayList();
        if (selection == null) {
            return result;
        }
        // Switch statement to choose what type of graph is built
        switch (selection) {
            case "RouteEquipment":
                result = routeEquipmentGraph();
                break;
        }

        return result;
    }

    /**
     * Calls the appropriate graph build function depending on the selection,
     * returning the data to to build a bar chart from it.
     *
     * @return XYChart.Series (String, Number).
     */
    public XYChart.Series<String, Number> buildBarChart() {
        XYChart.Series<String, Number> result = new XYChart.Series<>();
        if (selection == null) {
            return result;
        }

        switch (selection) {
            case "RouteAirline":
                result = routeAirlineGraph();
                break;
        }
        return result;
    }


    /**
     * Builds a XYChart.Series mapping strings to Integers of Route Description (Source - Destination)
     * to the count of airlines that cover them. Returns the 10 least covered routes.
     *
     * @return XYChart.Series (String, Integer) of route descriptions to counts of airlines
     */
    public XYChart.Series<String, Number> routeAirlineGraph() {
        ArrayList<XYChart.Data<String, Integer>> tempList = new ArrayList<>();
        RouteService routeService = new RouteService();

        ArrayList<Integer> routeIds = new ArrayList<>();
        for (ArrayList<Object> route : this.data) {
            routeIds.add((Integer) route.get(0));
        }

        Hashtable<String, Integer> routeCounts = routeService.getCountAirlinesCovering(routeIds);


        for (String route : routeCounts.keySet()) {
            if (routeCounts.get(route) != 0) {
                tempList.add(new XYChart.Data<>(route, routeCounts.get(route)));
            }
        }

        return sortAirlineChart(tempList);
    }


    /**
     * Sorts the XYChart.Data in the given XYChart.Series, and returning the sorted series
     *
     * @param routeAirlineData - ArrayList of XYChart.Data (String, Integer) Routes to airline Counts
     * @return XYChart.Series (String, Number) - Sorted XYChart.Series
     */
    public XYChart.Series<String, Number> sortAirlineChart(ArrayList<XYChart.Data<String, Integer>> routeAirlineData) {

        Comparator<XYChart.Data<String, Integer>> xyChartComparator = Comparator.comparing(XYChart.Data :: getYValue);

        routeAirlineData.sort(xyChartComparator);

        XYChart.Series<String, Number> toReturn = new XYChart.Series<>();


        if (routeAirlineData.size() > 16) {
            for (int i = 0; i < 16; i++) {
                toReturn.getData().add(new XYChart.Data<String, Number>(routeAirlineData.get(i).getXValue(), (Number) routeAirlineData.get(i).getYValue()));
            }
        } else {
            for (XYChart.Data<String, Integer> stringIntegerData : routeAirlineData) {
                toReturn.getData().add(new XYChart.Data<String, Number>(stringIntegerData.getXValue(), (Number) stringIntegerData.getYValue()));
            }
        }

        return toReturn;
    }




    /**
     * Converts the ArrayList of ArrayList of Objects data to an Observable List of PieChart.Data.
     * Also condenses the data down to 16 entries, 15 of which are the 15 largest, and the 16th is the sum of the
     * rest.
     *
     * @return ObservableList of PieChart.Data objects
     */
    public ObservableList<PieChart.Data> routeEquipmentGraph() {
        Hashtable<String, Integer> equipmentCounts = new Hashtable<String, Integer>();
        // Converting the data into a hashtable of equipments to counts
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
        // Adding the results to an Observable List
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (String equip : equipmentCounts.keySet()) {
            if (equipmentCounts.get(equip) != 0) {
                pieChartData.add(new PieChart.Data(equip, equipmentCounts.get(equip)));
            }
        }

        return sortChartList(pieChartData);
    }







    /**
     * Sorts and iterates through pie chart data and returns it.
     *
     * @param pieChartData ObservableList that contains all the pie chart data.
     * @return ObservableList that contains all the data for a chart.
     */
    public ObservableList<PieChart.Data> sortChartList(ObservableList<PieChart.Data> pieChartData) {
        // Sorting the observable list by count
        ObservableList<PieChart.Data> toReturn;
        Comparator<PieChart.Data> pieChartDataComparator = Comparator.comparing(PieChart.Data::getPieValue);

        pieChartData.sort(pieChartDataComparator.reversed());

        // Trimming the size of the observable list if it is too big
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

    /**
     * Setter for selection.
     *
     * @param selection String.
     */
    public void setSelection(String selection) {
        this.selection = selection;
    }
}
