package seng202.team5.controller.graph;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import seng202.team5.graph.RouteGraphChart;

import javax.xml.catalog.Catalog;
import java.util.ArrayList;
import java.util.List;

public class BarChartController extends Application {

    ObservableList<XYChart.Series<String, Number>> data;
    private BarChart<String, Number> chart;
    private String selection;


    @Override
    public void start(Stage stage) {
        if (data.get(0).getData().size() != 0) {
            Scene scene = new Scene(new Group());
            stage.setTitle("Bar Chart");
            stage.setWidth(500);
            stage.setHeight(500);
            ((Group) scene.getRoot()).getChildren().add(chart);
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


    public void inflateChart(ArrayList<ArrayList<Object>> routeIds, List<Object> metaData) {
        RouteGraphChart routeGraphChart = new RouteGraphChart(routeIds);
        routeGraphChart.setSelection((String) metaData.get(0));
        data = FXCollections.observableArrayList();
        data.add(routeGraphChart.buildBarChart());
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Route");
        yAxis.setLabel("Airline Count");

        chart = new BarChart<String, Number>(xAxis, yAxis);
        chart.setData(data);
        chart.setTitle((String) metaData.get(1));
        chart.setLegendVisible(false);

    }




}
