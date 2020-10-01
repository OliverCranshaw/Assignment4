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

/**
 * AirlineGraphChart
 *
 * Builds graph/chart depending on the selection.
 * Contains the functions buildChart, airportRouteChart, airportCountryChart.
 * Implements the GraphChartBuilder interface.
 */
public class AirportGraphChart implements GraphChartBuilder {

    private ArrayList<ArrayList<Object>> data;
    private AirportService service;
    private String selection;

    /**
     * Constructor for AirlineGraphChart.
     * Sets the value of the current data.
     * Created new instance of airport service.
     */
    public AirportGraphChart(ArrayList<ArrayList<Object>> data) {
        this.data = data;
        service = new AirportService();
    }

    /**
     * Calls the appropriate graph build function depending on the selection,
     * returning the data required to build a pie chart from it.
     *
     * @return ObservableList of PieChart.Data objects.
     */
    public ObservableList<PieChart.Data> buildChart() throws SQLException {
        ObservableList<PieChart.Data> result = FXCollections.observableArrayList();

        switch (selection) {
            case "AirportRoute":
                result =  airportRouteChart();
                break;
            case "AirportCountry":
                result = airportCountryChart();
                break;
        }

        return result;
    }

    /**
     * Builds and creates an observable list with number of routes in and out per airport.
     *
     * @return ObservableList that contains all the data for a chart.
     */
    public ObservableList<PieChart.Data> airportRouteChart() throws SQLException {
        Hashtable<String, Integer> routeCounts = new Hashtable<String, Integer>();
        Hashtable<Integer, Integer> incCounts = service.getIncRouteCount();
        Hashtable<Integer, Integer> outCounts = service.getOutRouteCount();
        for (ArrayList<Object> airport : data) {
            String airportName = (String) airport.get(1);
            int airportID = (int) airport.get(0);

            int incCount = (incCounts.get(airportID) != null) ? incCounts.get(airportID) : 0;
            int outCount = (outCounts.get(airportID) != null) ? outCounts.get(airportID) : 0;

            int routeCount = incCount + outCount;

            routeCounts.put(airportName, routeCount);
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (String airport : routeCounts.keySet()) {
            pieChartData.add(new PieChart.Data(airport, routeCounts.get(airport)));
        }

        return sortChartList(pieChartData);
    }

    /**
     * Builds and creates an observable list with number of airports per country.
     *
     * @return ObservableList that contains all the data for a chart.
     */
    public ObservableList<PieChart.Data> airportCountryChart(){
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
     * Sets the value of the selection.
     *
     * @param selection String value that is the selection.
     */
    public void setSelection(String selection) {
        this.selection = selection;
    }
}
