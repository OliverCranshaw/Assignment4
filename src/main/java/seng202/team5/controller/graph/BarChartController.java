package seng202.team5.controller.graph;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.stage.Stage;
import seng202.team5.graph.RouteGraphChart;

import java.util.ArrayList;
import java.util.List;

public class BarChartController extends Application {

    private BarChart<String, Number> chart;
    private String selection;

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Bar Chart");
        stage.setWidth(500);
        stage.setHeight(500);
        ((Group) scene.getRoot()).getChildren().add(chart);
        stage.setScene(scene);
        stage.show();
    }

    public void inflateChart(ArrayList<ArrayList<Object>> routeIds, List<Object> metaData) {
        RouteGraphChart routeGraphChart = new RouteGraphChart(routeIds);
        routeGraphChart.setSelection((String) metaData.get(0));
        ObservableList<XYChart.Series<String, Number>> data = FXCollections.observableArrayList();
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
