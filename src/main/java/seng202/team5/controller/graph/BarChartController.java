package seng202.team5.controller.graph;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import seng202.team5.App;
import seng202.team5.graph.RouteGraphChart;

import java.util.ArrayList;
import java.util.List;

/**
 * BarChartController
 *
 * Controller for javafx bar charts.
 * Extends Application, overriding the start method.
 */
public class BarChartController extends Application {

    ObservableList<XYChart.Series<String, Number>> data;
    private BarChart<String, Number> chart;

    @Override
    public void start(Stage stage) {
        if (data.get(0).getData().size() != 0) {
            Scene scene = new Scene(new Group());
            stage.setTitle("Bar Chart");
            ((Group) scene.getRoot()).getChildren().add(chart);
            scene.getStylesheets().add(App.class.getResource("graph_style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Insufficient data to display this graph.");
            alert.show();
        }
    }

    /**
     * Sets up the chart with the given data.
     *
     * @param routeIds ArrayList of ArrayList of Objects, ArrayList of route data.
     * @param metaData List of Object, data relating to setup of chart.
     */
    public void inflateChart(ArrayList<ArrayList<Object>> routeIds, List<Object> metaData) {
        // Setting up data for the chart
        RouteGraphChart routeGraphChart = new RouteGraphChart(routeIds);
        routeGraphChart.setSelection((String) metaData.get(0));
        data = FXCollections.observableArrayList();
        data.add(routeGraphChart.buildBarChart());
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Route");
        yAxis.setLabel("Airline Count");

        // Setting up chart
        chart = new BarChart<String, Number>(xAxis, yAxis);
        chart.setData(data);
        chart.setTitle((String) metaData.get(1));
        chart.setLegendVisible(false);
    }
}
