package seng202.team5.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import seng202.team5.App;
import seng202.team5.data.ConcreteDeleteData;
import seng202.team5.data.ConcreteUpdateData;
import seng202.team5.data.DataExporter;
import seng202.team5.map.AirportCompare;
import seng202.team5.map.Bounds;
import seng202.team5.map.Coord;
import seng202.team5.map.MapView;
import seng202.team5.model.AirportModel;
import seng202.team5.service.AirportService;
import seng202.team5.table.AirportTable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static java.lang.Math.abs;

public class AirportDataTabController implements Initializable {

    @FXML
    private TextField airportCountryField;

    @FXML
    private TableView<AirportModel> airportTableView;

    @FXML
    private TableColumn<AirportModel, String> airportNameCol;

    @FXML
    private TableColumn<AirportModel, String> airportCityCol;

    @FXML
    private TableColumn<AirportModel, String> airportCountryCol;

    @FXML
    private Label lblAirportID;

    @FXML
    private TextField airportID;

    @FXML
    private Label lblAirportName;

    @FXML
    private TextField airportName;

    @FXML
    private Label lblAirportCity;

    @FXML
    private TextField airportCity;

    @FXML
    private Label lblAirportCountry;

    @FXML
    private TextField airportCountry;

    @FXML
    private Label lblAirportIATA;

    @FXML
    private TextField airportIATA;

    @FXML
    private Label lblAirportICAO;

    @FXML
    private TextField airportICAO;

    @FXML
    private Label lblAirportLatitude;

    @FXML
    private TextField airportLatitude;

    @FXML
    private Label lblAirportLongitude;

    @FXML
    private TextField airportLongitude;

    @FXML
    private Label lblAirportAltitude;

    @FXML
    private TextField airportAltitude;

    @FXML
    private Label lblAirportTimezone;

    @FXML
    private TextField airportTimezone;

    @FXML
    private Label lblAirportDST;

    @FXML
    private TextField airportDST;

    @FXML
    private Label lblAirportTZ;

    @FXML
    private TextField airportTZ;

    @FXML
    private Button modifyAirportBtn;

    @FXML
    private Button airportSaveBtn;

    @FXML
    private Button airportCancelBtn;

    @FXML
    private Label airportInvalidFormatLbl;

    @FXML
    private Button airportDeleteBtn;

    @FXML
    private TableColumn<AirportModel, Integer> airportIncRoutesCol;

    @FXML
    private TableColumn<AirportModel, Integer> airportOutRoutesCol;

    @FXML
    private MapView airportMapView;

    @FXML
    private Button calculateDistanceButton;

    @FXML
    private Label distanceLabel;

    private AirportTable airportTable;

    private String prevAirportName = "";

    private AirportCompare rawAirportCompare = new AirportCompare();

    private int airportMarkerId;

    private int airportPathId;

    private int airportPrevMarkerId;

    private ObservableList<AirportModel> airportModels;

    private AirportService airportService;
    private DataExporter dataExporter;

    private MainMenuController mainMenuController = new MainMenuController();


    /**
     * Initializer for AirportDataTabController
     * Sets up all tables, buttons, listeners, services, etc
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Setting all map sub scenes to a zoomed out map view.
        airportMapView.addLoadListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                // new page has loaded, process:
                airportMapView.setCentre(0, 0);
                airportMapView.setZoom(2);
            }
        });

        // Setting the cell value factories for the airport table
        airportNameCol.setCellValueFactory(new PropertyValueFactory<>("AirportName"));
        airportCityCol.setCellValueFactory(new PropertyValueFactory<>("AirportCity"));
        airportCountryCol.setCellValueFactory(new PropertyValueFactory<>("AirportCountry"));
        airportIncRoutesCol.setCellValueFactory(new PropertyValueFactory<>("NoIncRoutes"));
        airportOutRoutesCol.setCellValueFactory(new PropertyValueFactory<>("NoOutRoutes"));

        // Disabling the modify button
        modifyAirportBtn.setDisable(true);

        // Adding Listeners to airport table so that the selected Items can be displayed in the single record viewer
        airportTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {

                AirportModel selected = (AirportModel) newSelection;
                try {
                    setAirportSingleRecord(selected);
                    setAirportElementsEditable(false);
                    modifyAirportBtn.setDisable(false);
                    airportInvalidFormatLbl.setVisible(false);
                    airportSaveBtn.setVisible(false);
                    airportCancelBtn.setVisible(false);
                    airportDeleteBtn.setVisible(false);
                    setAirportElementsEditable(false);
                    setAirportUpdateColour(null);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });


        // Initializing the required services and tables for the airport tab
        dataExporter = new DataExporter();
        airportService = new AirportService();
        airportTable = new AirportTable(airportService.getData(null, null, null));


        // Creating and populate the table with data
        try {
            airportTable.createTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        populateAirportTable(airportTableView, airportTable.getData());


        // Setting all modify buttons to invisible
        airportSaveBtn.setVisible(false);
        airportCancelBtn.setVisible(false);
        airportDeleteBtn.setVisible(false);

        // Lists of elements and label elements which need visibility toggled
        List<TextField> elements = Arrays.asList(airportID, airportName, airportCity, airportCountry, airportIATA, airportICAO, airportLatitude, airportLongitude,
                airportAltitude, airportTimezone, airportDST, airportTZ);

        List<Label> lblElements = Arrays.asList(lblAirportID, lblAirportName, lblAirportCity, lblAirportCountry, lblAirportIATA, lblAirportICAO, lblAirportLatitude, lblAirportLongitude,
                lblAirportAltitude, lblAirportTimezone, lblAirportDST, lblAirportTZ);

        ArrayList<TextField> elementsVisible = new ArrayList<>(elements);
        ArrayList<Label> lblElementsVisible = new ArrayList<>(lblElements);

        // Emptying and hiding fields
        mainMenuController.setFieldsEmpty(elementsVisible);
        mainMenuController.setLabelsEmpty(lblElementsVisible, false);
    }


    /**
     * setAirportSingleRecord
     *
     * Sets the airport single record labels to contain relevant entries
     * @param airportModel - airport Model used to populate labels
     * @throws SQLException occurs when any interactions with the ResultSet fail
     */
    private void setAirportSingleRecord(AirportModel airportModel) throws SQLException {
        List<TextField> elements = Arrays.asList(airportID, airportName, airportCity, airportCountry, airportIATA, airportICAO, airportLatitude, airportLongitude,
                airportAltitude, airportTimezone, airportDST, airportTZ);
        ArrayList<TextField> elementsVisible = new ArrayList<>(elements);
        List<Label> lblElements = Arrays.asList(lblAirportID, lblAirportName, lblAirportCity, lblAirportCountry, lblAirportIATA, lblAirportICAO, lblAirportLatitude, lblAirportLongitude,
                lblAirportAltitude, lblAirportTimezone, lblAirportDST, lblAirportTZ);
        ArrayList<Label> lblElementsVisible = new ArrayList<>(lblElements);

        if (airportModel == null) {
            mainMenuController.setFieldsEmpty(elementsVisible);
            mainMenuController.setLabelsEmpty(lblElementsVisible, false);
        } else {
            ResultSet airportData = airportService.getData(airportModel.getId());
            mainMenuController.setLabels(airportData, elementsVisible);
            mainMenuController.setLabelsEmpty(lblElementsVisible, true);
            Coord airportCoord = new Coord(Double.parseDouble(airportLatitude.getText()), Double.parseDouble(airportLongitude.getText()));

            try {
                airportMapView.removeMarker(airportMarkerId);
                airportMapView.removeMarker(airportPrevMarkerId);
                airportMapView.removePath(airportPathId);
            } catch (Exception e) {
            }

            if (prevAirportName.equals("")) {
                calculateDistanceButton.setText("Calculate Distance From Previous Airport");
            } else {
                calculateDistanceButton.setDisable(false);
                calculateDistanceButton.setText("Calculate Distance From Previous Airport (" + prevAirportName + ")");
            }

            prevAirportName = airportName.getText();
            distanceLabel.setText("");
            rawAirportCompare.setLocations(airportCoord);
            airportMarkerId = airportMapView.addMarker(airportCoord, airportName.getText(), null);
            airportMapView.setCentre(airportCoord);
            airportMapView.setZoom(11);
        }
    }

    /**
     * onCalculateDistancePressed
     *
     * Sets the distance label on the gui to the calculated distance between the currently viewed
     * airport and the previously viewed airport. If no previous airport has been viewed, the distance
     * label will display an error message.
     */
    public void onCalculateDistancePressed() {

        double distance = rawAirportCompare.calculateDistance();

        distanceLabel.setText(distance + "km");

        ArrayList airportCoords = new ArrayList(Arrays.asList(rawAirportCompare.getLocation1(), rawAirportCompare.getLocation2()));

        airportMapView.fitBounds(Bounds.fromCoordinateList(airportCoords), 2);

        airportPathId = airportMapView.addPath(airportCoords);
        airportPrevMarkerId = airportMapView.addMarker(rawAirportCompare.getLocation2(), "Previous Airport", null);


    }

    /**
     * onAddAirportPressed
     *
     * Handles add airport button pressing by showing new window which contains
     * UI to add new airport
     * @param event user has clicked on the add airport button
     * @throws IOException occurs when there are any errors with JavaFX
     */
    @FXML
    public void onAddAirportPressed(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(App.class.getResource("add_airport.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Add Airport");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.show();
    }


    /**
     * onUploadAirportDataPressed
     *
     * Starts the upload airport data window
     * @param event user has clicked on the upload airport button
     * @throws IOException occurs when there are any errors with JavaFX
     */
    @FXML
    public void onUploadAirportDataPressed(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(App.class.getResource("upload_airports.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Upload Airport Data");
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.show();

        BaseUploadMenuController controller = loader.getController();
        controller.onShown(scene);
    }

    /**
     * onDownloadAirportDataPressed
     *
     * Starts the download airport data window
     * @param event user has clicked on the download button in the airport tab
     */
    @FXML
    public void onDownloadAirportDataPressed(ActionEvent event) {
        File file = mainMenuController.selectFolder(event);

        if (file != null) {
            dataExporter.exportAirports(file, airportTable.getData());
        }
    }

    /**
     * populateAirportTable
     *
     * Populates the given airport table with the given data
     * @param tableView - TableView
     * @param data - ArrayList of ArrayList of Object
     */
    private void populateAirportTable(TableView tableView, ArrayList<ArrayList<Object>> data) {
        ArrayList<AirportModel> list = new ArrayList<>();
        Hashtable<Integer, Integer> incRouteCounts = new Hashtable<>();
        Hashtable<Integer, Integer> outRouteCounts = new Hashtable<>();
        try {
            incRouteCounts = airportService.getIncRouteCount();
            outRouteCounts = airportService.getOutRouteCount();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for (ArrayList<Object> datum : data) {
            Integer id = (Integer) datum.get(0);
            String name = (String) datum.get(1);
            String city = (String) datum.get(2);
            String country = (String) datum.get(3);
            int incCounts = 0;
            int outCounts = 0;
            if (incRouteCounts.size() != 0) {
                incCounts = (incRouteCounts.get(id) != null) ? incRouteCounts.get(id) : 0;
                outCounts = (outRouteCounts.get(id) != null) ? outRouteCounts.get(id) : 0;
            }
            list.add(new AirportModel(name, city, country, id, incCounts, outCounts));
        }
        airportModels = FXCollections.observableArrayList(list);
        tableView.setItems(airportModels);
    }

    /**
     * onAirportApplyFilterButton
     *
     * Applies the selected filters to the airline table
     */
    @FXML
    public void onAirportApplyFilterButton() {
        airportTable.FilterTable(null);
        String countryText = airportCountryField.getText();
        ArrayList newList = mainMenuController.convertCSStringToArrayList(countryText);
        airportTable.FilterTable(newList);
        populateAirportTable(airportTableView, airportTable.getData());
    }

    /**
     * updateAirportTable
     *
     * Updates the airport table with data from the database
     * @throws SQLException occurs when any interactions with the ResultSet returned by the getData function fail
     */
    public void updateAirportTable() throws SQLException {
        airportTable = new AirportTable(airportService.getData(null, null, null));
        airportTable.createTable();
        populateAirportTable(airportTableView, airportTable.getData());
    }

    /**
     * onAirportRefreshButton
     *
     * Updates the airport table from a button press
     * @throws SQLException occurs when any interactions with the ResultSet returned by the getData function in updateAirportTable fail
     */
    @FXML
    public void onAirportRefreshButton() throws SQLException {
        updateAirportTable();
    }

    /**
     * onModifyAirportBtnPressed
     *
     * Starts the modify airport mode
     * @throws SQLException occurs when any interactions with the ResultSet fail in the setAirportSingleRecord function
     */
    public void onModifyAirportBtnPressed() throws SQLException {
        if (airportTableView.getSelectionModel().getSelectedItem() != null) {
            setAirportUpdateColour(null);
            airportInvalidFormatLbl.setVisible(false);
            setAirportElementsEditable(true);
            airportSaveBtn.setVisible(true);
            airportCancelBtn.setVisible(true);
            airportDeleteBtn.setVisible(true);
        } else {
            setAirportSingleRecord(null);
        }
    }

    /**
     * setAirportElementsEditable
     *
     * Sets the airport TextFields to be editable or not (depending on given bool)
     * @param bool - boolean
     */
    public void setAirportElementsEditable(Boolean bool) {
        List<TextField> elements = Arrays.asList(airportName, airportCity, airportCountry, airportIATA, airportICAO, airportLatitude, airportLongitude,
                airportAltitude, airportTimezone, airportDST, airportTZ);
        ArrayList<TextField> elementsEditable = new ArrayList<>(elements);
        mainMenuController.setElementsEditable(elementsEditable, bool);
    }


    /**
     * onAirportSaveBtnPressed
     *
     * Saves the airport if the given data if in the right form,
     * other wise handles errors and prompts user on where they are
     * @throws SQLException occurs when any interactions with the ResultSet fail in the updateAirportTable function
     */
    @FXML
    public void onAirportSaveBtnPressed() throws SQLException {
        airportInvalidFormatLbl.setVisible(false);
        setAirportUpdateColour(null);
        int id = Integer.parseInt(airportID.getText());
        List<Object> elementList2;
        Double latitude = null;
        Double longitude = null;
        Integer altitude = null;
        Float timezone = null;
        try {
            latitude = Double.parseDouble(airportLatitude.getText());
        } catch(NumberFormatException e) {
            airportLatitude.setStyle("-fx-border-color: #ff0000;");
        }
        try {
            longitude = Double.parseDouble(airportLongitude.getText());
        } catch (NumberFormatException e) {
            airportLongitude.setStyle("-fx-border-color: #ff0000;");
        }
        try {
            altitude = Integer.parseInt(airportAltitude.getText());
        } catch (NumberFormatException e) {
            airportAltitude.setStyle("-fx-border-color: #ff0000;");
        }
        try {
            timezone = Float.parseFloat(airportTimezone.getText());
        } catch (NumberFormatException e) {
            airportTimezone.setStyle("-fx-border-color: #ff0000;");
        }

        if (!(latitude == null || longitude == null || altitude == null || timezone == null)) {
            elementList2 = Arrays.asList(airportName.getText(), airportCity.getText(), airportCountry.getText(), airportIATA.getText(), airportICAO.getText(),
                    latitude, longitude, altitude, timezone, airportDST.getText(), airportTZ.getText());
            ArrayList<Object> newElements = new ArrayList<>(elementList2);
            ConcreteUpdateData updater = new ConcreteUpdateData();
            int result = updater.updateAirport(id, (String) newElements.get(0), (String) newElements.get(1), (String) newElements.get(2), (String) newElements.get(3),
                    (String) newElements.get(4), (Double) newElements.get(5), (Double) newElements.get(6), (newElements.get(7) == null) ? -1 : (Integer) newElements.get(7), (Float) newElements.get(8),
                    (String) newElements.get(9), (String) newElements.get(10));
            if (result > 0) {
                updateAirportTable();
                setAirportElementsEditable(false);
                airportSaveBtn.setVisible(false);
                airportCancelBtn.setVisible(false);
                airportDeleteBtn.setVisible(false);
            } else if (result == -1) {
                setAirportUpdateColour(3);
                setAirportUpdateColour(4);
            } else {
                setAirportUpdateColour(abs(result) - 2);
            }

        }
    }

    /**
     * setAirportUpdateColour
     *
     * Updates the colours of the airport TextFields by the given index
     * @param index
     */
    public void setAirportUpdateColour(Integer index) {
        airportInvalidFormatLbl.setVisible(false);
        List<TextField> element = Arrays.asList(airportName, airportCity, airportCountry, airportIATA, airportICAO, airportLatitude, airportLongitude,
                airportAltitude, airportTimezone, airportDST, airportTZ);
        ArrayList<TextField> elements = new ArrayList<>(element);
        if (index == null) {
            for (TextField textField : elements) {
                textField.setStyle("-fx-border-color: #000000;");
            }
        } else {
            elements.get(index).setStyle("-fx-border-color: #ff0000;");
        }
    }


    /**
     * onAirportCancelBtnPressed
     *
     * Cancels current airport modify
     * @throws SQLException occurs when any interactions with the ResultSet fail in the setAirportSingleRecord function
     */
    @FXML
    public void onAirportCancelBtnPressed() throws SQLException {
        airportInvalidFormatLbl.setVisible(false);
        setAirportElementsEditable(false);
        setAirportSingleRecord((AirportModel)airportTableView.getSelectionModel().getSelectedItem());
        airportCancelBtn.setVisible(false);
        airportSaveBtn.setVisible(false);
        airportDeleteBtn.setVisible(false);
        setAirportUpdateColour(null);
    }


    /**
     * onAirportDeleteBtnPressed
     *
     * Deletes airport that is currently being modified
     */
    @FXML
    public void onAirportDeleteBtnPressed() {
        ConcreteDeleteData deleter = new ConcreteDeleteData();
        int id = Integer.parseInt(airportID.getText());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Airport");
        alert.setContentText("Delete Airport with ID: " + id + "?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(type -> {
            if (type == ButtonType.YES) {
                deleter.deleteAirport(id);
                airportDeleteBtn.setVisible(false);
                airportSaveBtn.setVisible(false);
                airportCancelBtn.setVisible(false);
                try {
                    setAirportSingleRecord(null);
                    updateAirportTable();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        });


    }

    /**
     * onGraphAirportCountryBtnPressed
     *
     * Handles the pressing of the Airport Country Graph Button.
     * Creates a Pie Chart showing the number of airports per country from the filtered data of the table.
     * @throws Exception Caused by ResultSet interactions.
     */
    public void onGraphAirportCountryBtnPressed() throws Exception {
        PieChartController controller = new PieChartController();
        List<Object> metaData = List.of("AirportCountry", "Airports per Country");
        controller.inflateChart(airportTable.getData(), metaData);
        controller.start(new Stage(StageStyle.DECORATED));
    }

    /**
     * onGraphAirportRouteBtnPressed
     *
     * Handles the pressing of the Airport Route Graph Button.
     * Creates a Pie Chart showing the number of routes per airport from the filtered data of the airport table.
     * @throws Exception Caused by ResultSet interactions.
     */
    public void onGraphAirportRouteBtnPressed() throws Exception {
        PieChartController controller = new PieChartController();
        List<Object> metaData = List.of("AirportRoute", "Routes per Airport");
        controller.inflateChart(airportTable.getData(), metaData);
        controller.start(new Stage(StageStyle.DECORATED));
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
