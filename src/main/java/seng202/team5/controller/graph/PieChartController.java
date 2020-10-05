package seng202.team5.controller.graph;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;
import javafx.util.Duration;
import seng202.team5.App;
import seng202.team5.graph.AirlineGraphChart;
import seng202.team5.graph.AirportGraphChart;
import seng202.team5.graph.RouteGraphChart;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * PieChartController
 *
 * Controller for javafx pie charts.
 * Extends Application, overriding the start method.
 */
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
        if (data.size() != 0) {
            // Case where data is present
            Scene scene = new Scene(new Group());
            switch (selection) {
                case "RouteEquipment":
                    stage.setTitle("Quantities of Routes per Equipment");
                    break;
                case "AirlineCountry":
                    stage.setTitle("Airlines per Country");
                    break;
                case "AirportRoute":
                    stage.setTitle("Routes per Airport");
                    break;
                case "AirportCountry":
                    stage.setTitle("Airports per Country");
                    break;
                default:
                    stage.setTitle("Pie Chart");
                    break;
            }
            ((Group) scene.getRoot()).getChildren().add(chart);
            scene.getStylesheets().add(App.class.getResource("graph_style.css").toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } else {
            // Case where there is no data present
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Insufficient data to display this graph.");
            alert.show();
        }
    }

    /**
     * Inflates the pie chart with the given data.
     *
     * @param data - ArrayList of ArrayList of Objects, representing an Arraylist of a data type.
     * @param metaData - List of Object, data relating to setup of chart.
     *
     * @throws SQLException Due to ResultSet Interactions.
     */
    public void inflateChart(ArrayList<ArrayList<Object>> data, List<Object> metaData) throws SQLException {

        selection = (String) metaData.get(0);
        // Choosing what type of graph to make
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
        // Creating the chart
        chart = new PieChart(this.data);
        chart.setTitle((String) metaData.get(1));
        chart.setLegendSide(Side.BOTTOM);
        chart.resize(500.0, 500.0);

        // Applying the Other options and mouse events (highlighting and showing segment info)
        for (final PieChart.Data segment : chart.getData()) {
            if (segment.getName().startsWith("Other")) {
                applyRemoveOtherOption(segment);
            } else {
                applyReturnOtherOption(segment);
            }
            applyMouseEvents(segment);
        }
    }

    /**
     * Adds the remove other option to the other segment of the pie chart.
     *
     * @param segment - PieChart.data, Other segment of pie chart.
     */
    private void applyRemoveOtherOption(PieChart.Data segment) {
        final Node node = segment.getNode();
        // Adding node event on right click
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

    /**
     * Adds the return other option to the given segment of data.
     *
     * @param segment - PieChart.data, A segment of a pie chart.
     */
    public void applyReturnOtherOption(PieChart.Data segment) {
        final Node node = segment.getNode();
        // Adding node event on right click
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

    /**
     * Method that sets up the highlighting affect on the pie chart segments,
     * as well as the popup tool tip showing the segment information.
     *
     * @param data - PieChart.data, A segment of a pie chart.
     */
    public void applyMouseEvents(final PieChart.Data data) {

        final Node node = data.getNode();
        // Adding node event on mouse entering a segment
        node.setOnMouseEntered(arg0 -> {
            Tooltip currTip;
            if (this.selection.equals("RouteEquipment")) {
                currTip = new Tooltip("Equipment: " + data.getName() + "\nQuantity: " + (int) data.getPieValue());
            } else if (this.selection.equals("AirportRoute")) {
                currTip = new Tooltip("Airport: " + data.getName() + "\nQuantity: " + (int) data.getPieValue());
            } else if (this.selection.equals("AirportCountry")) {
                currTip = new Tooltip("Country: " + data.getName() + "\nQuantity: " + (int) data.getPieValue());
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
