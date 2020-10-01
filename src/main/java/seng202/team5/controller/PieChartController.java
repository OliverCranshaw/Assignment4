package seng202.team5.controller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Glow;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import seng202.team5.graph.AirlineGraphChart;
import seng202.team5.graph.AirportGraphChart;
import seng202.team5.graph.RouteGraphChart;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieChartController extends Application {


    private ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
    private PieChart chart;
    private String selection;
    private PieChart.Data Other = null;
    private final Integer MIN = 0;
    private final Integer MAX = 16777215;
    private ContextMenu removeContextMenu;
    private ContextMenu addContextMenu;





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


    public void inflateChart(ArrayList<ArrayList<Object>> data, List<Object> metaData) throws SQLException {

        selection = (String) metaData.get(0);

        if (selection.startsWith("Route")) {
            RouteGraphChart routeGraph = new RouteGraphChart(data);
            routeGraph.setSelection(selection);
            this.data = routeGraph.buildChart();
        } else if (selection.startsWith("Airport")) {
            AirportGraphChart airportGraph = new AirportGraphChart(data);
            airportGraph.setSelection(selection);
            this.data = airportGraph.buildChart();
        } else if (selection.startsWith("Airline")) {
            AirlineGraphChart airlineGraphChart = new AirlineGraphChart(data);
            airlineGraphChart.setSelection(selection);
            this.data = airlineGraphChart.buildChart();
        }

        chart = new PieChart(this.data);
        chart.setTitle((String) metaData.get(1));
        chart.setLegendSide(Side.BOTTOM);
        chart.resize(500.0, 500.0);


        for (final PieChart.Data segment : chart.getData()) {
            if (segment.getName().startsWith("Other")) {
                applyRemoveOtherOption(segment);
            } else {
                applyReturnOtherOption(segment);
            }
            applyMouseEvents(segment);
        }
    }

    private void applyRemoveOtherOption(PieChart.Data segment) {
        final Node node = segment.getNode();
        node.setOnContextMenuRequested(arg0 -> {
            removeContextMenu = new ContextMenu();
            removeContextMenu.hide();
            MenuItem menuItem = new MenuItem();
            menuItem.setText("Hide 'Other'");
            menuItem.setOnAction(arg1 -> {
                Other = segment;
                chart.getData().remove(segment);
            });
            removeContextMenu.getItems().add(menuItem);
            Robot robot = new Robot();
            removeContextMenu.show(node, robot.getMousePosition().getX(), robot.getMousePosition().getY());
        });
    }


    public void applyReturnOtherOption(PieChart.Data segment) {
        final Node node = segment.getNode();
        node.setOnContextMenuRequested(arg0 -> {
            if (Other != null) {
                addContextMenu = new ContextMenu();
                addContextMenu.hide();
                MenuItem menuItem = new MenuItem();
                menuItem.setText("Return 'Other'");
                menuItem.setOnAction(arg1 -> {
                    chart.getData().add(Other);
                    Other = null;
                });
                addContextMenu.getItems().add(menuItem);
                Robot robot = new Robot();
                addContextMenu.show(node, robot.getMousePosition().getX(), robot.getMousePosition().getY());
            }
        });
    }




    public void applyMouseEvents(final PieChart.Data data) {

        final Node node = data.getNode();

        node.setOnMouseEntered(arg0 -> {
            Tooltip currTip;
            if (this.selection.equals("RouteEquipment")) {
                currTip = new Tooltip("Equipment: " + data.getName() + "\nQuantity: " + (int) data.getPieValue());
            } else if (this.selection.equals("AirportRoute")) {
                currTip = new Tooltip("Airport: " + data.getName() + "\nQuantity: " + (int) data.getPieValue());
            } else if (this.selection.equals("AirportCountry")) {
                currTip = new Tooltip("Airport: " + data.getName() + "\nQuantity: " + (int) data.getPieValue());
            } else if (this.selection.equals("AirlineCountry")) {
                currTip = new Tooltip("Airline: " + data.getName() + "\nQuantity: " + (int) data.getPieValue());
            } else {
                currTip = null;
            }
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
