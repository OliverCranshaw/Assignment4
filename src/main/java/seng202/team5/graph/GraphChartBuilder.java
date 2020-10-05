package seng202.team5.graph;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.sql.SQLException;

/**
 * GraphChartBuilder
 *
 * An interface used to build graphs and charts.
 */
public interface GraphChartBuilder {

    /**
     * Calls the appropriate graph build function depending on the selection,
     * returning the data required to build a pie chart from it.
     *
     * @return ObservableList of PieChart.Data objects.
     *
     * @throws SQLException Caused by ResultSet interactions.
     */
    ObservableList<PieChart.Data> buildChart() throws SQLException;

    /**
     * Sorts and iterates through pie chart data and returns it.
     *
     * @param pieChartData ObservableList that contains all the pie chart data.
     * @return ObservableList that contains all the data for a chart.
     */
    ObservableList<PieChart.Data> sortChartList(ObservableList<PieChart.Data> pieChartData);
}
