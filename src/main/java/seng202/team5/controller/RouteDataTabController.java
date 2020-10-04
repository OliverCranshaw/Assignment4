package seng202.team5.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import seng202.team5.App;
import seng202.team5.accessor.AirlineAccessor;
import seng202.team5.data.AirportData;
import seng202.team5.data.ConcreteDeleteData;
import seng202.team5.data.ConcreteUpdateData;
import seng202.team5.data.DataExporter;
import seng202.team5.graph.RouteGraphChart;
import seng202.team5.map.Bounds;
import seng202.team5.map.Coord;
import seng202.team5.map.MapView;
import seng202.team5.model.RouteModel;
import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.FlightService;
import seng202.team5.service.RouteService;
import seng202.team5.table.RouteTable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static java.lang.Math.abs;

public class RouteDataTabController implements Initializable {

    @FXML
    private TextField routeSourceAirportField;

    @FXML
    private TextField routeDestAirportField;

    @FXML
    private TextField routeEquipmentField;

    @FXML
    private TableView routeTableView;

    @FXML
    private TableColumn routeAirlineCol;

    @FXML
    private TableColumn routeSrcAirportCol;

    @FXML
    private TableColumn routeDestAirportCol;

    @FXML
    private TableColumn routeStopsCol;

    @FXML
    private TableColumn routeEquipmentCol;

    @FXML
    private Button routeApplyFilter;

    @FXML
    private ComboBox routeStopsComboBox;

    @FXML
    private Label lblRouteID;

    @FXML
    private TextField routeID;

    @FXML
    private Label lblRouteAirline;

    @FXML
    private TextField routeAirline;

    @FXML
    private Label lblRouteAirlineID;

    @FXML
    private TextField routeAirlineID;

    @FXML
    private Label lblRouteDepAirport;

    @FXML
    private TextField routeDepAirport;

    @FXML
    private Label lblRouteDepAirportID;

    @FXML
    private TextField routeDepAirportID;

    @FXML
    private Label lblRouteDesAirport;

    @FXML
    private TextField routeDesAirport;

    @FXML
    private Label lblRouteDesAirportID;

    @FXML
    private TextField routeDesAirportID;

    @FXML
    private Label lblRouteCodeshare;

    @FXML
    private TextField routeCodeshare;

    @FXML
    private Label lblRouteStops;

    @FXML
    private TextField routeStops;

    @FXML
    private Label lblRouteEquip;

    @FXML
    private TextField routeEquip;

    @FXML
    private Button modifyRouteBtn;

    @FXML
    private Button routeSaveBtn;

    @FXML
    private Button routeCancelBtn;

    @FXML
    private Button routeDeleteBtn;

    @FXML
    private MapView routeMapView;

    @FXML
    private Label routeInvalidFormatLbl;

    private RouteTable routeTable;

    private ObservableList<RouteModel> routeModels;

    private int routePath = -1;

    private DataExporter dataExporter;
    private AirportService airportService;
    private AirlineService airlineService;
    private RouteService routeService;

    private MainMenuController mainMenuController = new MainMenuController();


    /**
     * Initializer for RouteDataTabController
     * Sets up all tables, buttons, listeners, services, etc
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Setting the cell value factories for the route table
        routeAirlineCol.setCellValueFactory(new PropertyValueFactory<>("RouteAirline"));
        routeSrcAirportCol.setCellValueFactory(new PropertyValueFactory<>("RouteSrcAirport"));
        routeDestAirportCol.setCellValueFactory(new PropertyValueFactory<>("RouteDstAirport"));
        routeStopsCol.setCellValueFactory(new PropertyValueFactory<>("RouteStops"));
        routeEquipmentCol.setCellValueFactory(new PropertyValueFactory<>("RouteEquipment"));
        // Setting the route dropdown menu items
        routeStopsComboBox.getItems().removeAll(routeStopsComboBox.getItems());
        routeStopsComboBox.getItems().addAll("", "direct", "not direct");

        // Disabling the modify button
        modifyRouteBtn.setDisable(true);

        // Adding Listeners to route table so that the selected Items can be displayed in the single record viewer
        routeTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                RouteModel selected = (RouteModel) newSelection;
                try {
                    setRouteSingleRecord(selected);
                    setRouteElementsEditable(false);
                    modifyRouteBtn.setDisable(false);
                    routeSaveBtn.setVisible(false);
                    routeDeleteBtn.setVisible(false);
                    routeCancelBtn.setVisible(false);
                    setRouteElementsEditable(false);
                    routeInvalidFormatLbl.setVisible(false);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });


        // Initializing the required services and tables for the route tab
        dataExporter = new DataExporter();
        airlineService = new AirlineService();
        airportService = new AirportService();
        routeService = new RouteService();
        routeTable = new RouteTable(routeService.getData(null, null, -1, null));

        try {
            routeTable.createTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        populateRouteTable(routeTableView, routeTable.getData());

        // Setting all modify buttons to invisible
        routeSaveBtn.setVisible(false);
        routeCancelBtn.setVisible(false);
        routeDeleteBtn.setVisible(false);

        // Lists of elements and label elements which need visibility toggled
        List<TextField> elements = Arrays.asList(routeID, routeAirline, routeAirlineID, routeDepAirport, routeDepAirportID, routeDesAirport, routeDesAirportID,
                routeCodeshare, routeStops, routeEquip);

        List<Label> lblElements = Arrays.asList(lblRouteID, lblRouteAirline, lblRouteAirlineID, lblRouteDepAirport, lblRouteDepAirportID,
                lblRouteDesAirport, lblRouteDesAirportID, lblRouteCodeshare, lblRouteStops, lblRouteEquip);

        ArrayList<TextField> elementsVisible = new ArrayList<>(elements);
        ArrayList<Label> lblElementsVisible = new ArrayList<>(lblElements);

        // Emptying and hiding fields
        mainMenuController.setFieldsEmpty(elementsVisible);
        mainMenuController.setLabelsEmpty(lblElementsVisible, false);

    }

    /**
     * setRouteSingleRecord
     *
     * Sets the route single record labels to contain the relevant entries
     * @param routeModel - Route model used to populate labels
     * @throws SQLException occurs when any interactions with the ResultSet fail
     */
    private void setRouteSingleRecord(RouteModel routeModel) throws SQLException {

        List<TextField> elements = Arrays.asList(routeID, routeAirline, routeAirlineID, routeDepAirport, routeDepAirportID, routeDesAirport, routeDesAirportID,
                routeCodeshare, routeStops, routeEquip);
        ArrayList<TextField> elementsVisible = new ArrayList<>(elements);
        List<Label> lblElements = Arrays.asList(lblRouteID, lblRouteAirline, lblRouteAirlineID, lblRouteDepAirport, lblRouteDepAirportID,
                lblRouteDesAirport, lblRouteDesAirportID, lblRouteCodeshare, lblRouteStops, lblRouteEquip);
        ArrayList<Label> lblElementsVisible = new ArrayList<>(lblElements);
        if (routeModel == null) {
            mainMenuController.setFieldsEmpty(elementsVisible);
            mainMenuController.setLabelsEmpty(lblElementsVisible, false);
        } else {
            {
                ResultSet routeData = routeService.getData(routeModel.getRouteId());
                mainMenuController.setLabels(routeData, elementsVisible);
                mainMenuController.setLabelsEmpty(lblElementsVisible, true);
            }

            {
                ResultSet routeData = routeService.getData(routeModel.getRouteId());

                AirportData source = new AirportData(airportService.getData(routeData.getInt(5)));
                AirportData destination = new AirportData(airportService.getData(routeData.getInt(7)));

                List<Coord> coordinates = List.of(new Coord(source.getLatitude(), source.getLongitude()), new Coord(destination.getLatitude(), destination.getLongitude()));

                if (routePath != -1) {
                    routeMapView.removePath(routePath);
                }
                routePath = routeMapView.addPath(coordinates, MainMenuController.DEFAULT_ROUTE_SYMBOLS, null, MainMenuController.DEFAULT_STROKE_WEIGHT);
                routeMapView.fitBounds(Bounds.fromCoordinateList(coordinates), 5.0);
            }

        }
    }

    /**
     * onAddRoutePressed
     *
     * Starts the add route window
     * @param event user has clicked on the add airline button
     * @throws IOException occurs when there are any errors with JavaFX
     */
    @FXML
    public void onAddRoutePressed(ActionEvent event) throws IOException {
        routeInvalidFormatLbl.setVisible(false);
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(App.class.getResource("add_route.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Add Route");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
        stage.show();
    }


    /**
     * onUploadRouteDataPressed
     *
     * Starts the upload route data window
     * @param event user has clicked on the upload route button
     * @throws IOException occurs when there are any errors with JavaFX
     */
    @FXML
    public void onUploadRouteDataPressed(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(App.class.getResource("upload_routes.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Upload Route Data");
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.show();

        BaseUploadMenuController controller = loader.getController();
        controller.onShown(scene);

    }

    /**
     * onDownloadRouteDataPressed
     *
     * Starts the download route data window
     * @param event user has clicked on the download button in the route tab
     */
    @FXML
    public void onDownloadRouteDataPressed(ActionEvent event) {
        File file = mainMenuController.selectFolder(event);

        if (file != null) {
            dataExporter.exportRoutes(file, routeTable.getData());
        }
    }

    /**
     * populateRouteTable
     *
     * Populates the given route table with the given data
     * @param tableView - TableView
     * @param data - ArrayList of ArrayList of Object
     */
    private void populateRouteTable(TableView<RouteModel> tableView, ArrayList<ArrayList<Object>> data) {
        ArrayList<RouteModel> list = new ArrayList<>();
        ArrayList<String> airlineCodes = new ArrayList<>();
        ArrayList<String> srcAirportCodes = new ArrayList<>();
        ArrayList<String> dstAirportCodes = new ArrayList<>();
        for (ArrayList<Object> datum : data) {
            String airline = (String) datum.get(1);
            String srcAirport = (String) datum.get(3);
            String dstAirport = (String) datum.get(5);
            Integer stops = (Integer) datum.get(8);
            ArrayList<String> equipment = (ArrayList<String>) datum.get(9);
            StringBuffer equipmentString = new StringBuffer();
            for (int i = 0; i < equipment.size(); i++) {
                equipmentString.append(equipment.get(i));
                if (i != equipment.size() - 1) {
                    equipmentString.append(", ");
                }
            }
            Integer id = (Integer) datum.get(0);
            if (!airlineCodes.contains(airline)) {
                airlineCodes.add(airline);
            }
            if (!srcAirportCodes.contains((srcAirport))) {
                srcAirportCodes.add(srcAirport);
            }
            if (!dstAirportCodes.contains(dstAirport)) {
                dstAirportCodes.add(dstAirport);
            }
            list.add(new RouteModel(airline, srcAirport, dstAirport, stops, equipmentString.toString(), id));
        }
        Hashtable<String, String> airlineNames = airlineService.getAirlineNames(airlineCodes);
        Hashtable<String, String> srcAirportNames = airportService.getAirportNames(srcAirportCodes);
        Hashtable<String, String> dstAirportNames = airportService.getAirportNames(dstAirportCodes);
        for (RouteModel route : list) {
            if (airlineNames.containsKey(route.getRouteAirline())) {
                route.setRouteAirline(airlineNames.get(route.getRouteAirline()));
            }
            if (srcAirportNames.containsKey(route.getRouteSrcAirport())) {
                route.setRouteSrcAirport(srcAirportNames.get(route.getRouteSrcAirport()));
            }
            if (dstAirportNames.containsKey(route.getRouteDstAirport())) {
                route.setRouteDstAirport((dstAirportNames.get(route.getRouteDstAirport())));
            }
        }
        routeModels = FXCollections.observableArrayList(list);
        tableView.setItems(routeModels);
    }

    /**
     * onRouteApplyFilterButton
     *
     * Applies the selected filter to the route table
     * @throws SQLException occurs when any interactions with the ResultSet fail
     */
    @FXML
    public void onRouteApplyFilterButton() throws SQLException {
        routeTable.FilterTable(null, null, null, null);
        String srcAirportText = (routeSourceAirportField.getText().length() == 0) ? null : routeSourceAirportField.getText().trim();
        String dstAirportText = (routeDestAirportField.getText().length() == 0) ? null : routeDestAirportField.getText().trim();
        String equipmentText = routeEquipmentField.getText();
        String directText = (String) routeStopsComboBox.getValue();
        if (directText == null || directText.equals("")) {
            directText = null;
        } else {
            directText = (directText.equals("direct")) ? "direct" : "not direct";
        }
        ArrayList<String> equipmentList = mainMenuController.convertCSStringToArrayList(equipmentText);
        String srcIata;
        String dstIata;
        if (srcAirportText == null) {
            srcIata = null;
        } else {
            ResultSet srcAirport = airportService.getData(srcAirportText, null, null);
            if (srcAirport.next()) {
                srcIata = srcAirport.getString(5);
            } else {
                srcIata = "_!$";
            }
        }
        if (dstAirportText == null) {
            dstIata = null;
        } else {
            ResultSet dstAirport = airportService.getData(dstAirportText, null, null);
            if (dstAirport.next()) {
                dstIata = dstAirport.getString(5);
            } else {
                dstIata = "_N@";
            }
        }
        routeTable.FilterTable(srcIata, dstIata, directText, equipmentList);
        populateRouteTable(routeTableView, routeTable.getData());
    }

    /**
     * updateRouteTable
     *
     * Updates the route table with data from the database
     * @throws SQLException occurs when any interactions with the ResultSet returned by the getData function fail
     */
    public void updateRouteTable() throws SQLException {
        routeTable = new RouteTable(routeService.getData(null, null, -1, null));
        routeTable.createTable();
        populateRouteTable(routeTableView, routeTable.getData());
    }

    /**
     * onRouteRefreshButton
     *
     * Updates the route table from a button press
     * @throws SQLException occurs when any interactions with the ResultSet returned by the getData function in updateRouteTable fail
     */
    @FXML
    public void onRouteRefreshButton() throws  SQLException {
        routeInvalidFormatLbl.setVisible(false);
        updateRouteTable();
    }

    /**
     * onModifyRouteBtnPressed
     *
     * Starts the modify route mode
     * @throws SQLException occurs when any interactions with the ResultSet fail in the setRouteSingleRecord function
     */
    public void onModifyRouteBtnPressed() throws SQLException {
        routeInvalidFormatLbl.setVisible(false);
        if (routeTableView.getSelectionModel().getSelectedItem() != null) {
            setRouteUpdateColour(null);
            setRouteElementsEditable(true);
            routeSaveBtn.setVisible(true);
            routeCancelBtn.setVisible(true);
            routeDeleteBtn.setVisible(true);
        } else {
            setRouteSingleRecord(null);
        }
    }


    /**
     * setRouteElementsEditable
     *
     * Sets the route TextFields to be editable or not (depending on given bool)
     * @param bool - boolean
     */
    public void setRouteElementsEditable(Boolean bool) {
        List<TextField> elements = Arrays.asList(routeAirline, routeDepAirport, routeDesAirport,
                routeCodeshare, routeStops, routeEquip);
        ArrayList<TextField> elementsVisible = new ArrayList<>(elements);
        mainMenuController.setElementsEditable(elementsVisible, bool);
    }

    /**
     * onRouteSaveBtnPressed
     *
     * Saves the route if the given data if in the right form,
     * other wise handles errors and prompts user on where they are
     * @throws SQLException occurs when any interactions with the ResultSet fail in the updateRouteTable function
     */
    @FXML
    public void onRouteSaveBtnPressed() throws SQLException {
        routeInvalidFormatLbl.setVisible(false);
        setRouteUpdateColour(null);
        int id = Integer.parseInt(routeID.getText());
        List<Object> elements;
        Integer stops = null;
        try {
            stops = Integer.parseInt(routeStops.getText());

        } catch (NumberFormatException e) {
            setRouteUpdateColour(4);
            routeInvalidFormatLbl.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 12));
            routeInvalidFormatLbl.setTextFill(Color.RED);
            routeInvalidFormatLbl.setText("Invalid Stops Provided");
            routeInvalidFormatLbl.setVisible(true);
        }
        if (stops != null) {
            elements = Arrays.asList(routeAirline.getText(), routeDepAirport.getText(), routeDesAirport.getText(), routeCodeshare.getText(),
                    stops, routeEquip.getText());

            ConcreteUpdateData updater = new ConcreteUpdateData();

            int result = updater.updateRoute(id, (String) elements.get(0), (String) elements.get(1), (String) elements.get(2),
                    (String) elements.get(3), (elements.get(4) == null) ? -1 : (Integer) elements.get(4), (String) elements.get(5));
            if (result > 0) {
                updateRouteTable();
                Integer routeAirlineID1 = airlineService.getData((String) elements.get(0)).getInt(1);
                Integer routeSrcAirportID1 = airportService.getData((String) elements.get(1)).getInt(1);
                Integer routeDstAirportID1 = airportService.getData((String) elements.get(2)).getInt(1);
                routeAirlineID.setText(String.valueOf(routeAirlineID1));
                routeDepAirportID.setText(String.valueOf(routeSrcAirportID1));
                routeDesAirportID.setText(String.valueOf(routeDstAirportID1));
                setRouteElementsEditable(false);
                routeSaveBtn.setVisible(false);
                routeCancelBtn.setVisible(false);
                routeDeleteBtn.setVisible(false);
            } else if (result == - 1) {
                setRouteUpdateColour(0);
                setRouteUpdateColour(1);
                setRouteUpdateColour(2);
                routeInvalidFormatLbl.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 12));
                routeInvalidFormatLbl.setTextFill(Color.RED);
                routeInvalidFormatLbl.setText("Invalid Airline and/or Airports Provided");
                routeInvalidFormatLbl.setVisible(true);
            } else {
                setRouteUpdateColour(abs(result) - 2);
                routeInvalidFormatLbl.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 12));
                routeInvalidFormatLbl.setTextFill(Color.RED);
                routeInvalidFormatLbl.setVisible(true);
                switch (result) {
                    case -2:
                        routeInvalidFormatLbl.setText("Invalid Airline Provided");
                        break;
                    case -3:
                        routeInvalidFormatLbl.setText("Invalid Source Airport Provided");
                        break;
                    case -4:
                        routeInvalidFormatLbl.setText("Invalid Destination Airport Provided");
                        break;
                    case -5:
                        routeInvalidFormatLbl.setText("Invalid Codeshare Provided");
                        break;
                    case -6:
                        routeInvalidFormatLbl.setText("Invalid Stops Provided");
                        break;
                    case -7:
                        routeInvalidFormatLbl.setText("Invalid Equipment Provided");
                        break;
                }
            }

        }

    }


    /**
     * setRouteUpdateColour
     *
     * Updates the colours of the airline TextFields by the given index
     * @param index
     */
    public void setRouteUpdateColour(Integer index) {
        List<TextField> elements = Arrays.asList(routeAirline, routeDepAirport, routeDesAirport,
                routeCodeshare, routeStops, routeEquip);
        ArrayList<TextField> elementsVisible = new ArrayList<>(elements);
        if (index == null) {
            for (TextField field : elements) {
                field.setStyle("-fx-border-color: #000000;");
            }
        } else {
            elements.get(index).setStyle("-fx-border-color: #ff0000;");
        }
    }


    /**
     * onRouteCancelBtnPressed
     *
     * Cancels current route modify
     * @throws SQLException occurs when any interactions with the ResultSet fail in the setRouteSingleRecord function
     */
    @FXML
    public void onRouteCancelBtnPressed() throws SQLException {
        routeInvalidFormatLbl.setVisible(false);
        setRouteElementsEditable(false);
        setRouteSingleRecord((RouteModel)routeTableView.getSelectionModel().getSelectedItem());
        routeCancelBtn.setVisible(false);
        routeSaveBtn.setVisible(false);
        routeDeleteBtn.setVisible(false);
        setRouteUpdateColour(null);
    }


    /**
     * onRouteDeleteBtnPressed
     *
     * Deletes route that is currently being modified
     */
    @FXML
    public void onRouteDeleteBtnPressed() {
        routeInvalidFormatLbl.setVisible(false);
        ConcreteDeleteData deleter = new ConcreteDeleteData();
        int id = Integer.parseInt(routeID.getText());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Route");
        alert.setContentText("Delete Route with ID: " + id + "?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(type -> {
            if (type == ButtonType.YES) {
                deleter.deleteRoute(id);
                routeDeleteBtn.setVisible(false);
                routeSaveBtn.setVisible(false);
                routeCancelBtn.setVisible(false);
                try {
                    setRouteSingleRecord(null);
                    updateRouteTable();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    /**
     * onGraphRouteBtnPressed
     *
     * Calls required functions to create a pieChart for graph - route data,
     * showing equipment usage percentages
     * @throws Exception Caused by ResultSet interactions.
     */
    @FXML
    public void onGraphRouteBtnPressed() throws Exception {

        PieChartController controller = new PieChartController();
        List<Object> metaData = List.of("RouteEquipment", "Quantities of Equipment used on Routes (Top 16)");
        controller.inflateChart(routeTable.getData(), metaData);
        controller.start(new Stage(StageStyle.DECORATED));
    }


    /**
     * Calls required functions to create a barchart for graph - airline data,
     * showing the least commonly covered routes.
     */
    @FXML
    public void onGraphRouteAirlineBtnPressed() {
        BarChartController controller = new BarChartController();
        List<Object> metaData = List.of("RouteAirline", "Routes Covered by the fewest number of airlines");
        controller.inflateChart(routeTable.getData(), metaData);
        controller.start(new Stage(StageStyle.DECORATED));
    }



    /**
     * showOtherAirlines
     *
     * Shows the other airlines that cover the given route
     *
     * @throws SQLException occurs when any interactions with the ResultSet fail
     */
    public void handleShowOtherAirlines() throws SQLException {
        RouteModel route = (RouteModel) routeTableView.getSelectionModel().getSelectedItem();
        String srcIata = route.getRouteSrcAirport();
        String dstIata = route.getRouteDstAirport();
        System.out.println(srcIata + " " + dstIata);
        ResultSet srcData = airportService.getData(srcIata);
        ResultSet dstData = airportService.getData(dstIata);
        ArrayList<Integer> airlines = routeService.getAirlinesCoveringRoute(srcData.getInt(1), dstData.getInt(1), true);
        String toShow = "";
        ArrayList<Integer> airlinesCleaned = new ArrayList<>();
        for (Integer airline: airlines) {
            if (!airlinesCleaned.contains(airline)) {
                airlinesCleaned.add(airline);
            }
        }
        int i = 0;
        for (Integer airlineID : airlinesCleaned) {
            ResultSet data = airlineService.getData(airlineID);
            String name = data.getString(2);
            toShow = toShow + name;
            if (data.getString(8).equals("N")) {
                toShow = toShow + " - Inactive";
            } else {
                toShow = toShow + " - Active";
            }
            if (i++ == 0) {
                toShow = toShow + " (Selected) ";
            }

            toShow = toShow  + "\n";
        }
        System.out.println(airlines);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Airlines that cover the " + srcIata + " - " + dstIata + " Route:");
        alert.setContentText(toShow);
        alert.showAndWait();

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

}
