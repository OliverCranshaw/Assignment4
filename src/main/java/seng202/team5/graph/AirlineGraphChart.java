package seng202.team5.graph;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

/**
 * AirlineGraphChart
 *
 * Builds graph/chart depending on the selection.
 * Contains the functions buildChart, airlineCountryChart.
 * Implements the GraphChartBuilder interface.
 */
public class AirlineGraphChart implements GraphChartBuilder {

    private ArrayList<ArrayList<Object>> data;
    private String selection;

    /**
     * Constructor for AirlineGraphChart.
     * Sets the value of the current data.
     */
    public AirlineGraphChart(ArrayList<ArrayList<Object>> data) {
        this.data = data;
    }

    /**
     * Calls the selected chart and returns it.
     *
     * @return ObservableList that contains all the data for a chart or null if there was nothing selected.
     */
    public ObservableList<PieChart.Data> buildChart() {
        switch (selection) {
            case "AirlineCountry":
                return airlineCountryChart();
        }
        return null;
    }

    /**
     * Builds and creates an observable list with number of airlines per country.
     *
     * @return ObservableList that contains all the data for a chart.
     */
    public ObservableList<PieChart.Data> airlineCountryChart() {
        Hashtable<String, Integer> countryCounts = new Hashtable<String, Integer>();
        for (ArrayList<Object> airline : data) {
            String country = (String) airline.get(6);
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

        return sortChartList(pieChartData);
    }

    /**
     * Sorts and iterates through pie chart data and returns it.
     *
     * @param pieChartData ObservableList that contains all the pie chart data.
     * @return ObservableList that contains all the data for a chart.
     */
    public ObservableList<PieChart.Data> sortChartList(ObservableList<PieChart.Data> pieChartData) {
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

    /**
     * Sets the value of the selection.
     *
     * @param selection String value that is the selection.
     */
    public void setSelection(String selection) {
        this.selection = selection;
    }
}
