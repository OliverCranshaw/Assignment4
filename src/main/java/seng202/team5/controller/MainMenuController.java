package seng202.team5.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import seng202.team5.data.AirlineData;
import seng202.team5.data.AirportData;
import seng202.team5.data.DataExporter;
import seng202.team5.map.Bounds;
import seng202.team5.map.Coord;
import seng202.team5.map.MapView;
import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.RouteService;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;

public class MainMenuController {

    /**
     * Default stroke weight to use on MapView paths
     */
    public static final double DEFAULT_STROKE_WEIGHT = 2.0;

    /**
     * List of path symbols to display on route and flight views
     */
    public static final List<Pair<Double,String>> DEFAULT_ROUTE_SYMBOLS = List.of(new Pair<>(0.0, "FORWARD_OPEN_ARROW"), new Pair<>(0.5, "FORWARD_OPEN_ARROW"), new Pair<>(1.0, "FORWARD_OPEN_ARROW"));

    /**
     * List of path symbols to display on airline views
     */
    public static final List<Pair<Double,String>> REDUCED_ROUTE_SYMBOLS = List.of(new Pair<>(0.45, "FORWARD_OPEN_ARROW"));


    @FXML
    public TabPane mainTabs;

    private DataExporter dataExporter = new DataExporter();

    private AirlineService airlineService = new AirlineService();
    private AirportService airportService = new AirportService();
    private RouteService routeService = new RouteService();

    /**
     * setFieldsEmpty
     *
     * Sets given arraylist of TextFields to be invisible and also sets their colour to black
     * @param elementsVisible - ArrayList of TextFields
     */
    public void setFieldsEmpty(ArrayList<TextField> elementsVisible) {
        for (TextField field : elementsVisible) {
            if (field != null) {
                field.setVisible(false);
                field.setEditable(false);
                field.setStyle("-fx-border-color: #000000;");
            }
        }
    }

    /**
     * setLabelsEmpty
     *
     * Sets given labels to be visible or not depending on the given boolean
     * @param elementsVisible ArrayList of Labels
     * @param bool boolean that determines if setting element to visible or not
     */
    public void setLabelsEmpty(ArrayList<Label> elementsVisible, Boolean bool) {
        for (Label lbl : elementsVisible) {
            if (lbl != null) {
                lbl.setVisible(bool);
            }
        }
    }

    /**
     * setLabels
     *
     * Sets given elementsVisible to contain the given elementData
     * @param elementData ResultSet of data to populate TextFields with
     * @param elementsVisible ArrayList of TextFields
     * @throws SQLException occurs when any interactions with the ResultSet fail
     */
    public void setLabels(ResultSet elementData, ArrayList<TextField> elementsVisible) throws SQLException {
        for (TextField field : elementsVisible) {
            field.setVisible(true);
            field.setEditable(false);
        }
        for (int i = 0; i < elementsVisible.size(); i++) {
            elementsVisible.get(i).setText(elementData.getString(i+1));
        }
    }

    /**
     * onHelp
     *
     * Handles the requesting of help by using the HelpHandler to call startHelp
     * @param event user has clicked on the help button
     */
    public void onHelp(ActionEvent event) {
        System.out.println("Help requested: " + event);

        Node e = (Node) event.getSource();
        Scene scene = e.getScene();
        HelpHandler.startHelp(scene);
    }


    /**
     * selectFolder
     *
     * Gets input from the user on which file data should be downloaded to
     * @param event user has clicked on an download button
     * @return - File the file the user has created to download to
     */
    public File selectFolder(ActionEvent event) {
        dataExporter = new DataExporter();

        FileChooser fileChooser = new FileChooser();

        // Sets the types of files that are allowed
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Text Documents (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        extensionFilter = new FileChooser.ExtensionFilter("CSV (Comma-Separated Values) (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extensionFilter);

        return fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());
    }


    /**
     * convertCSStringToArrayList
     *
     * Converts a comma seperated string to an arraylist of strings
     * @param string
     * @return ArrayList of String
     */
    public ArrayList<String> convertCSStringToArrayList(String string) {
        String[] list = string.split(",");
        ArrayList<String> newList = convertToArrayList(list);
        for (int i = 0; i < newList.size(); i++) {
            newList.set(i, newList.get(i).trim());
            if (newList.get(i).equals("")) {
                newList.remove(i--);
            }
        }
        if (newList.isEmpty()) {
            newList = null;
        }
        return newList;
    }


    /**
     * convertToArrayList
     *
     * Converts a primitive array of strings to an arraylist
     * @param list
     * @return ArrayList of String
     */
    public ArrayList<String> convertToArrayList(String[] list) {
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, list);
        return result;
    }


    /**
     * setElementsEditable
     *
     * Sets the given TextFields to be editable or not (depending on given bool)
     * @param bool - boolean
     */
    public void setElementsEditable(ArrayList<TextField> elementsEditable, Boolean bool) {
        for (TextField field : elementsEditable) {
            field.setEditable(bool);
        }
    }

    /**
     * Adds all the routes for a given airline to the provided MapView and sets the bounds to fit
     *
     * @param mapView MapView to display the airline routes on
     * @param airlineID Airline to display
     * @return List of created path IDs
     */
    public List<Integer> showAirline(MapView mapView, int airlineID) throws SQLException {
        AirlineData airline = new AirlineData(airlineService.getData(airlineID));

        // Hopefully makes this fast, also keeps track of the bounds we need to fit to
        Map<Integer, Coord> airportCache = new HashMap<>();

        // Converts a AirportID to the coordinate of the airport
        Function<Integer, Coord> getAirportCoordinates = (airportCode) -> {
            try {
                AirportData airport = new AirportData(airportService.getData(airportCode));
                return new Coord(airport.getLatitude(), airport.getLongitude());
            } catch (SQLException ignored) {
                return null;
            }
        };

        // Find all the routes from the given airline
        List<List<Coord>> routes = new ArrayList<>();
        ResultSet routeSet = routeService.getData(airline.getIATA());
        while (routeSet.next()) {
            Coord source = airportCache.computeIfAbsent(routeSet.getInt(5), getAirportCoordinates);
            Coord destination = airportCache.computeIfAbsent(routeSet.getInt(7), getAirportCoordinates);

            if (source != null && destination != null) {
                routes.add(List.of(source, destination));
            }
        }

        List<Integer> createdPaths = new ArrayList<>();
        Random random = new Random();
        for (List<Coord> route : routes) {
            String colour = "#ff0000";
            if (routes.size() >= 30) {
                double red = random.nextDouble() * 0.4 + 0.6;
                double green = random.nextDouble() * 0.2;
                double blue = random.nextDouble() * 0.2;

                colour = String.format("rgb(%f,%f,%f)", red * 255, green * 255, blue * 255);
            }

            createdPaths.add(mapView.addPath(route, REDUCED_ROUTE_SYMBOLS, colour, 1.0));
        }



        // Sets the correct map bounds, if there are any routes
        List<Coord> airportCoordinates = List.copyOf(airportCache.values());
        if (airportCoordinates.size() >= 2) {
            mapView.fitBounds(Bounds.fromCoordinateList(airportCoordinates), 5.0);
        }

        return createdPaths;
    }
}
