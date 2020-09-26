package seng202.team5.controller;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import seng202.team5.graph.RouteGraph;

import java.util.ArrayList;
import java.util.List;

public class BarChartController extends Application {

    private CategoryAxis xAxis = new CategoryAxis();
    private NumberAxis yAxis = new NumberAxis();
    private BarChart<String, Number> chart;
    private XYChart.Series dataSeries;




    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(new Group());
        stage.setTitle("Bar Chart");
        stage.setWidth(500);
        stage.setHeight(500);
        ((Group) scene.getRoot()).getChildren().add(chart);
        stage.setScene(scene);
        stage.show();
    }


    public void inflateChart(ArrayList<ArrayList<Object>> data, List<Object> metaData) {
        RouteGraph routeGraph = new RouteGraph(data);
        this.dataSeries = routeGraph.buildBarGraph((String) metaData.get(0));
        this.chart = new BarChart<String, Number>(xAxis, yAxis);




    }


}
