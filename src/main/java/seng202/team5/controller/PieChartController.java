package seng202.team5.controller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Glow;
import javafx.stage.Stage;
import javafx.util.Duration;
import seng202.team5.graph.RouteGraphChart;

import java.util.ArrayList;
import java.util.List;

public class PieChartController extends Application {


    private ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
    private PieChart chart;
    private PieChart.Data selectedData;
    private final Integer MIN = 0;
    private final Integer MAX = 16777215;



    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(new Group());
        stage.setTitle("PieChart");
        stage.setWidth(500);
        stage.setHeight(500);
        ((Group) scene.getRoot()).getChildren().add(chart);
        stage.setScene(scene);
        stage.show();
    }


    public void inflateChart(ArrayList<ArrayList<Object>> data, List<Object> metaData) {
        RouteGraphChart routeGraph = new RouteGraphChart(data);
        this.data = routeGraph.buildGraph((String) metaData.get(0));
        if (this.data.size() > 25) {
            this.data = this.data.sorted();
        }
        chart = new PieChart(this.data);
        chart.setTitle((String) metaData.get(1));
        chart.setLegendSide(Side.BOTTOM);




        for (final PieChart.Data segment : chart.getData()) {
            applyMouseEvents(segment);
        }
    }

    public void applyMouseEvents(final PieChart.Data data) {

        final Node node = data.getNode();

        node.setOnMouseEntered(arg0 -> {
            Tooltip currTip = new Tooltip("Equipment: " + data.getName() + "\nQuantity: " + (int) data.getPieValue());
            currTip.setStyle("-fx-font: 14 arial;  -fx-font-smoothing-type: lcd;");// -fx-text-fill:black; -fx-background-color: linear-gradient(#e2ecfe, #99bcfd);");
            Tooltip.install(node, currTip);
            node.setEffect(new Glow());
            String styleString = "-fx-border-color: white; -fx-border-width: 3; -fx-border-style: dashed;";
            node.setStyle(styleString);
            currTip.setShowDelay(new Duration(0.3));
            currTip.setHideDelay(new Duration(0.1));
        });

        node.setOnMouseExited(arg0 -> {
            node.setEffect(null);
            node.setStyle("");
        });
    }


}
