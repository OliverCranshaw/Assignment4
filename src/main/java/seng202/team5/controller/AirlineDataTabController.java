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
import seng202.team5.data.*;
import seng202.team5.map.Bounds;
import seng202.team5.map.Coord;
import seng202.team5.map.MapView;
import seng202.team5.model.AirlineModel;
import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.RouteService;
import seng202.team5.table.AirlineTable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;

public class AirlineDataTabController implements Initializable {

    @FXML
    public TableView rawAirlineTable;

    @FXML
    private TableColumn<AirlineModel, String> airlineNameCol;

    @FXML
    private TableColumn<AirlineModel, String> airlineAliasCol;

    @FXML
    private TableColumn<AirlineModel, String> airlineCountryCol;

    @FXML
    private TableColumn<AirlineModel, String> airlineActiveCol;

    @FXML
    private TextField countryAirlineField;

    @FXML
    private Button airlineApplyFilterButton;

    @FXML
    private Label lblAirlineID;

    @FXML
    private TextField airlineID;

    @FXML
    private Label lblAirlineName;

    @FXML
    private TextField airlineName;

    @FXML
    private Label lblAirlineAlias;

    @FXML
    private TextField airlineAlias;

    @FXML
    private Label lblAirlineIATA;

    @FXML
    private TextField airlineIATA;

    @FXML
    private Label lblAirlineICAO;

    @FXML
    private TextField airlineICAO;

    @FXML
    private Label lblAirlineCallsign;

    @FXML
    private TextField airlineCallsign;

    @FXML
    private Label lblAirlineCountry;

    @FXML
    private TextField airlineCountry;

    @FXML
    private Label lblAirlineActive;

    @FXML
    private TextField airlineActive;

    @FXML
    private ComboBox airlineActiveDropdown;

    @FXML
    private Button modifyAirlineBtn;

    @FXML
    private Button airlineSaveBtn;

    @FXML
    private Button airlineCancelBtn;

    @FXML
    private Button airlineDeleteBtn;

    @FXML
    private MapView airlineMapView;

    private AirlineTable airlineTable;

    private ObservableList<AirlineModel> airlineModels;

    private List<Integer> airlinePaths = new ArrayList<>();

    private DataExporter dataExporter;
    private AirlineService airlineService;
    private AirportService airportService;
    private RouteService routeService;

    private MainMenuController mainMenuController = new MainMenuController();


    /**
     * Initializer for AirlineDataTabController
     * Sets up all tables, buttons, listeners, services, etc
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Setting all map sub scenes to a zoomed out map view.

        airlineMapView.addLoadListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                // new page has loaded, process:
                airlineMapView.setCentre(0, 0);
                airlineMapView.setZoom(2);
            }
        });

        // Setting the cell value factories for the airline table
        airlineNameCol.setCellValueFactory(new PropertyValueFactory<>("AirlineName"));
        airlineAliasCol.setCellValueFactory(new PropertyValueFactory<>("AirlineAlias"));
        airlineCountryCol.setCellValueFactory(new PropertyValueFactory<>("AirlineCountry"));
        airlineActiveCol.setCellValueFactory(new PropertyValueFactory<>("AirlineActive"));
        // Setting the airline dropdown menu items
        airlineActiveDropdown.getItems().removeAll(airlineActiveDropdown.getItems());
        airlineActiveDropdown.getItems().addAll("", "Yes", "No");

        // Disabling the modify button
        modifyAirlineBtn.setDisable(true);

        // Adding Listeners to airline table so that the selected Items can be displayed in the single record viewer
        rawAirlineTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                AirlineModel selected = (AirlineModel) newSelection;
                try {
                    setAirlineSingleRecord(selected);
                    setAirlineElementsEditable(false);
                    modifyAirlineBtn.setDisable(false);
                    airlineSaveBtn.setVisible(false);
                    airlineCancelBtn.setVisible(false);
                    airlineDeleteBtn.setVisible(false);
                    setAirlineElementsEditable(false);
                    setAirlineUpdateColour(null);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });


        // Initializing the required services and tables for the airline tab
        dataExporter = new DataExporter();
        airlineService = new AirlineService();
        airportService = new AirportService();
        routeService = new RouteService();
        airlineTable = new AirlineTable(airlineService.getData(null, null, null));

        // Creating and populate all the tables with data
        try {
            airlineTable.createTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        populateAirlineTable(rawAirlineTable, airlineTable.getData());


        // Setting all modify buttons to invisible
        airlineSaveBtn.setVisible(false);
        airlineCancelBtn.setVisible(false);
        airlineDeleteBtn.setVisible(false);

        // Lists of elements and label elements which need visibility toggled
        List<TextField> elements = Arrays.asList(airlineID, airlineName, airlineAlias, airlineIATA, airlineICAO, airlineCallsign, airlineCountry, airlineActive);

        List<Label> lblElements = Arrays.asList(lblAirlineID, lblAirlineName, lblAirlineAlias,
                lblAirlineIATA, lblAirlineICAO, lblAirlineCallsign, lblAirlineCountry, lblAirlineActive);

        ArrayList<TextField> elementsVisible = new ArrayList<>(elements);
        ArrayList<Label> lblElementsVisible = new ArrayList<>(lblElements);

        // Emptying and hiding fields
        mainMenuController.setFieldsEmpty(elementsVisible);
        mainMenuController.setLabelsEmpty(lblElementsVisible, false);

    }

    /**
     * setAirlineSingleRecord
     *
     * Sets the airline single record labels to contain the relevant entries
     * @param airlineModel - Airline Model used ot populate labels
     * @throws SQLException occurs when any interactions with the ResultSet fail
     */
    private void setAirlineSingleRecord(AirlineModel airlineModel) throws SQLException {
        List<TextField> elements = Arrays.asList(airlineID, airlineName, airlineAlias, airlineIATA, airlineICAO, airlineCallsign, airlineCountry, airlineActive);
        ArrayList<TextField> elementsVisible = new ArrayList<>(elements);
        List<Label> lblElements = Arrays.asList(lblAirlineID, lblAirlineName, lblAirlineAlias, lblAirlineIATA, lblAirlineICAO, lblAirlineCallsign, lblAirlineCountry, lblAirlineActive);
        ArrayList<Label> lblElementsVisible = new ArrayList<>(lblElements);
        if (airlineModel == null) {
            mainMenuController.setFieldsEmpty(elementsVisible);
            mainMenuController.setLabelsEmpty(lblElementsVisible, false);
        } else {
            ResultSet airlineData = airlineService.getData(airlineModel.getId());
            mainMenuController.setLabels(airlineData, elementsVisible);
            mainMenuController.setLabelsEmpty(lblElementsVisible, true);

            AirlineData airline = new AirlineData(airlineService.getData(airlineModel.getId()));

            // Remove pre-existing paths
            for (int pathID : airlinePaths) {
                airlineMapView.removePath(pathID);
            }
            airlinePaths.clear();


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
            ResultSet routeSet = routeService.getData(airline.getIATA());
            while (routeSet.next()) {
                Coord source = airportCache.computeIfAbsent(routeSet.getInt(5), getAirportCoordinates);
                Coord destination = airportCache.computeIfAbsent(routeSet.getInt(7), getAirportCoordinates);

                if (source != null && destination != null) {
                    airlinePaths.add(airlineMapView.addPath(List.of(source, destination)));
                }
            }

            // Sets the correct map bounds, if there are any routes
            List<Coord> airportCoordinates = List.copyOf(airportCache.values());
            if (airportCoordinates.size() >= 2) {
                airlineMapView.fitBounds(Bounds.fromCoordinateList(airportCoordinates), 5.0);
            }
        }
    }

    /**
     * onAddAirlinePressed
     *
     * Starts the add airline window
     * @param event user has clicked on the add airline button
     * @throws IOException occurs when there are any errors with JavaFX
     */
    @FXML
    public void onAddAirlinePressed(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(App.class.getResource("add_airline.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Add Airline");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
        stage.show();
    }


    /**
     * onUploadAirlineDataPressed
     *
     * Starts the upload airline window
     * @param event user has clicked on the upload airline button
     * @throws IOException occurs when there are any errors with JavaFX
     */
    @FXML
    public void onUploadAirlineDataPressed(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(App.class.getResource("upload_airlines.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Upload Airline Data");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.show();
    }


    /**
     * onDowloadAirlineDataPressed
     *
     * Starts the download airline window
     * @param event user has clicked on the download button in the airline tab
     */
    @FXML
    public void onDownloadAirlineDataPressed(ActionEvent event) {
        File file = mainMenuController.selectFolder(event);

        if (file != null) {
            dataExporter.exportAirlines(file, airlineTable.getData());
        }
    }

    /**
     * populateAirlineTable
     *
     * Populates the given airline tableView with the given data
     * @param tableView - TableView
     * @param data - ArrayList of ArrayList of Object
     */
    private void populateAirlineTable(TableView tableView, ArrayList<ArrayList<Object>> data) {

        ArrayList<AirlineModel> list = new ArrayList<>();
        for (ArrayList<Object> datum : data) {
            Integer id = (Integer) datum.get(0);
            String name = (String) datum.get(1);
            String alias = (String) datum.get(2);
            String country = (String) datum.get(6);
            String active = (String) datum.get(7);
            list.add(new AirlineModel(name, alias, country, active, id));
        }
        airlineModels = FXCollections.observableArrayList(list);
        tableView.setItems(airlineModels);
    }

    /**
     * onAirlineApplyFilterButton
     *
     * Applies the selected filters to the airline table
     */
    @FXML
    public void onAirlineApplyFilterButton() {
        airlineTable.FilterTable(null, null);
        String countryText = countryAirlineField.getText();
        String activeText = (String) airlineActiveDropdown.getValue();
        if (activeText == null || activeText.equals("")) {
            activeText = null;
        } else {
            activeText = (activeText.equals("Yes")) ? "Y" : "N";
        }
        ArrayList<String> newList = mainMenuController.convertCSStringToArrayList(countryText);
        airlineTable.FilterTable(newList, activeText);

        populateAirlineTable(rawAirlineTable, airlineTable.getData());

    }

    /**
     * updateAirlineTable
     *
     * Updates the airline table with data from the database
     * @throws SQLException occurs when any interactions with the ResultSet returned by the getData function fail
     */
    public void updateAirlineTable() throws SQLException {
        airlineTable = new AirlineTable(airlineService.getData(null, null, null));
        airlineTable.createTable();
        populateAirlineTable(rawAirlineTable, airlineTable.getData());
    }

    /**
     * onAirlineRefreshButton
     *
     * Updates the airline table from a button press
     * @throws SQLException occurs when any interactions with the ResultSet returned by the getData function in updateAirlineTable fail
     */
    @FXML
    public void onAirlineRefreshButton() throws  SQLException {
        updateAirlineTable();
    }

    /**
     * onModifyAirlineBtnPressed
     *
     * Starts the modify airline mode
     * @throws SQLException occurs when any interactions with the ResultSet fail in the setAirlineSingleRecord function
     */
    public void onModifyAirlineBtnPressed() throws SQLException {
        if (rawAirlineTable.getSelectionModel().getSelectedItem() != null) {
            setAirlineUpdateColour(null);
            setAirlineElementsEditable(true);
            airlineSaveBtn.setVisible(true);
            airlineDeleteBtn.setVisible(true);
            airlineCancelBtn.setVisible(true);
        } else {
            setAirlineSingleRecord(null);
        }
    }

    /**
     * setAirlineElementsEditable
     *
     * Sets the airline TextFields to be editable or not (depending on given bool)
     * @param bool - boolean
     */
    public void setAirlineElementsEditable(Boolean bool) {
        List<TextField> elements = Arrays.asList(airlineName, airlineName, airlineAlias, airlineIATA, airlineICAO, airlineCallsign, airlineCountry,
                airlineActive);
        ArrayList<TextField> elementsVisible = new ArrayList<>(elements);
        mainMenuController.setElementsEditable(elementsVisible, bool);
    }

    /**
     * onAirlineSaveBtnPressed
     *
     * Saves the airline if the given data if in the right form,
     * other wise handles errors and prompts user on where they are
     * @throws SQLException occurs when any interactions with the ResultSet fail in the updateAirlineTable function
     */
    @FXML
    public void onAirlineSaveBtnPressed() throws SQLException {
        setAirlineUpdateColour(null);
        int id = Integer.parseInt(airlineID.getText());

        List<String> elementList2 = Arrays.asList(airlineName.getText(), airlineAlias.getText(), airlineIATA.getText(), airlineICAO.getText(),
                airlineCallsign.getText(), airlineCountry.getText(), airlineActive.getText());
        ArrayList<String> newElements = new ArrayList<>(elementList2);

        ConcreteUpdateData updater = new ConcreteUpdateData();
        int result = updater.updateAirline(id, newElements.get(0), newElements.get(1), newElements.get(2), newElements.get(3), newElements.get(4),
                newElements.get(5), newElements.get(6));
        if (result > 0) {
            updateAirlineTable();
            setAirlineElementsEditable(false);
            airlineSaveBtn.setVisible(false);
            airlineDeleteBtn.setVisible(false);
            airlineCancelBtn.setVisible(false);
        } else {
            switch (result) {
                case -1:
                    setAirlineUpdateColour(2);
                    setAirlineUpdateColour(3);
                    break;
                case -2:
                    setAirlineUpdateColour(0);
                    break;
                case -3:
                    setAirlineUpdateColour(2);
                    break;
                case -4:
                    setAirlineUpdateColour(3);
                    break;
                case -5:
                    setAirlineUpdateColour(6);
                    break;
            }
        }

    }

    /**
     * setAirlineUpdateColour
     *
     * Updates the colours of the airline TextFields by the given index
     * @param index
     */
    public void setAirlineUpdateColour(Integer index) {
        List<TextField> elements = Arrays.asList(airlineName, airlineAlias, airlineIATA, airlineICAO, airlineCallsign, airlineCountry,
                airlineActive);
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
     * onAirlineCancelBtnPressed
     *
     * Cancels current airline modify
     * @throws SQLException occurs when any interactions with the ResultSet fail in the setAirlineSingleRecord function
     */
    @FXML
    public void onAirlineCancelBtnPressed() throws SQLException {
        setAirlineElementsEditable(false);
        setAirlineSingleRecord((AirlineModel)rawAirlineTable.getSelectionModel().getSelectedItem());
        airlineCancelBtn.setVisible(false);
        airlineSaveBtn.setVisible(false);
        airlineDeleteBtn.setVisible(false);
        setAirlineUpdateColour(null);
    }

    /**
     * onAirlineDeleteBtnPressed
     *
     * Deletes airline that is currently being modified
     */
    public void onAirlineDeleteBtnPressed() {
        ConcreteDeleteData deleter = new ConcreteDeleteData();
        int id = Integer.parseInt(airlineID.getText());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Airline");
        alert.setContentText("Delete Airline with ID: " + id + "?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(type -> {
            if (type == ButtonType.YES) {
                deleter.deleteAirline(id);
                airlineDeleteBtn.setVisible(false);
                airlineSaveBtn.setVisible(false);
                airlineCancelBtn.setVisible(false);
                try {
                    setAirlineSingleRecord(null);
                    updateAirlineTable();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        });

    }

    /**
     * onGraphAirlineCountryBtnPressed
     *
     * Handles the pressing of the Airline Country Graph Button.
     * Creates a Pie Chart showing the number of airlines per country from the filtered data from the airline table.
     * @throws Exception Caused by ResultSet interactions.
     */
    public void onGraphAirlineCountryBtnPressed() throws Exception {
        PieChartController controller = new PieChartController();
        List<Object> metaData = List.of("AirlineCountry", "Airlines per Country");
        controller.inflateChart(airlineTable.getData(), metaData);
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