package seng202.team5.controller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

import java.util.ArrayList;

public class PieChartController extends Application {


    private ObservableList<PieChart.Data> data = FXCollections.observableArrayList();


    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(new Group());
        stage.setTitle("PieChart");
        stage.setWidth(500);
        stage.setHeight(500);
        PieChart chart = new PieChart(data);
        ((Group) scene.getRoot()).getChildren().add(chart);
        stage.setScene(scene);
        stage.show();
    }


    public void inflateChart(ObservableList<PieChart.Data> data, ArrayList<Object> metaData) {
        data = data;
    }


}
