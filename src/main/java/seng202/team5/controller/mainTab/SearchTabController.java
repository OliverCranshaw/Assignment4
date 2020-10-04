package seng202.team5.controller.mainTab;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team5.controller.HelpHandler;
import seng202.team5.controller.MainMenuController;
import seng202.team5.data.AirportData;
import seng202.team5.data.DataExporter;
import seng202.team5.map.*;
import seng202.team5.model.*;
import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.FlightService;
import seng202.team5.service.RouteService;
import seng202.team5.table.*;

import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SearchTabController implements Initializable {

    private AirlineService airlineService;
    private AirportService airportService;
    private RouteService routeService;
    private FlightService flightService;

    @FXML
    private RadioButton flightsRadioButton;

    @FXML
    private RadioButton airportsRadioButton;

    @FXML
    private RadioButton airlinesRadioButton;

    @FXML
    private RadioButton routesRadioButton;

    @FXML
    private Label firstSearchType;

    @FXML
    private Label secondSearchType;

    @FXML
    private Label thirdSearchType;

    @FXML
    private Label fourthSearchType;

    @FXML
    private TextField firstSearchEntry;

    @FXML
    private TextField secondSearchEntry;

    @FXML
    private TextField thirdSearchEntry;

    @FXML
    private TextField fourthSearchEntry;

    @FXML
    private Label errorMessage;

    @FXML
    private Label lblAirportIDS;

    @FXML
    private TextField airportIDS;

    @FXML
    private Label lblAirportNameS;

    @FXML
    private TextField airportNameS;

    @FXML
    private Label lblAirportCityS;

    @FXML
    private TextField airportCityS;

    @FXML
    private Label lblAirportCountryS;

    @FXML
    private TextField airportCountryS;

    @FXML
    private Label lblAirportIATAS;

    @FXML
    private TextField airportIATAS;

    @FXML
    private Label lblAirportICAOS;

    @FXML
    private TextField airportICAOS;

    @FXML
    private Label lblAirportLatitudeS;

    @FXML
    private TextField airportLatitudeS;

    @FXML
    private Label lblAirportLongitudeS;

    @FXML
    private TextField airportLongitudeS;

    @FXML
    private Label lblAirportAltitudeS;

    @FXML
    private TextField airportAltitudeS;

    @FXML
    private Label lblAirportTimezoneS;

    @FXML
    private TextField airportTimezoneS;

    @FXML
    private Label lblAirportDSTS;

    @FXML
    private TextField airportDSTS;

    @FXML
    private Label lblAirportTZS;

    @FXML
    private TextField airportTZS;

    @FXML
    private Label lblAirlineIDS;

    @FXML
    private TextField airlineIDS;

    @FXML
    private Label lblAirlineNameS;

    @FXML
    private TextField airlineNameS;

    @FXML
    private Label lblAirlineAliasS;

    @FXML
    private TextField airlineAliasS;

    @FXML
    private Label lblAirlineIATAS;

    @FXML
    private TextField airlineIATAS;

    @FXML
    private Label lblAirlineICAOS;

    @FXML
    private TextField airlineICAOS;

    @FXML
    private Label lblAirlineCallsignS;

    @FXML
    private TextField airlineCallsignS;

    @FXML
    private Label lblAirlineCountryS;

    @FXML
    private TextField airlineCountryS;

    @FXML
    private Label lblAirlineActiveS;

    @FXML
    private TextField airlineActiveS;

    @FXML
    private Label lblRouteIDS;

    @FXML
    private TextField routeIDS;

    @FXML
    private Label lblRouteAirlineS;

    @FXML
    private TextField routeAirlineS;

    @FXML
    private Label lblRouteAirlineIDS;

    @FXML
    private TextField routeAirlineIDS;

    @FXML
    private Label lblRouteDepAirportS;

    @FXML
    private TextField routeDepAirportS;

    @FXML
    private Label lblRouteDepAirportIDS;

    @FXML
    private TextField routeDepAirportIDS;

    @FXML
    private Label lblRouteDesAirportS;

    @FXML
    private TextField routeDesAirportS;

    @FXML
    private Label lblRouteDesAirportIDS;

    @FXML
    private TextField routeDesAirportIDS;

    @FXML
    private Label lblRouteCodeshareS;

    @FXML
    private TextField routeCodeshareS;

    @FXML
    private Label lblRouteStopsS;

    @FXML
    private TextField routeStopsS;

    @FXML
    private Label lblRouteEquipS;

    @FXML
    private TextField routeEquipS;

    @FXML
    private TableColumn flightDbIDS;

    @FXML
    private TableColumn flightIDS;

    @FXML
    private TableColumn flightLocationTypeS;

    @FXML
    private TableColumn flightLocationS;

    @FXML
    private TableColumn flightAltitudeS;

    @FXML
    private TableColumn flightLatitudeS;

    @FXML
    private TableColumn flightLongitudeS;

    @FXML
    private TableView searchFlightSingleRecordTableView;

    @FXML
    private TableView searchTableView;

    @FXML
    private MapView searchMapView;

    private AirlineTable airlineSearchTable;
    private AirportTable airportSearchTable;
    private RouteTable routeSearchTable;
    private FlightTable flightSearchTable;

    private AirportCompare searchAirportCompare;

    private ObservableList<FlightModel> flightModels;
    private ObservableList<FlightEntryModel> flightEntriesSearch;
    private ObservableList<AirportModel> airportModels;
    private ObservableList<AirlineModel> airlineModels;
    private ObservableList<RouteModel> routeModels;

    private int searchAirportMarker = -1;
    private final List<Integer> searchAirlinePaths = new ArrayList<>();
    private int searchRoutePath = -1;
    private int searchFlightPath = -1;
    private int flightMapPath = -1;
    private int flightMapMarker = -1;

    private DataExporter dataExporter;

    private MainMenuController mainMenuController = new MainMenuController();

    /**
     * Initializer for SearchTabController
     * Sets up all tables, buttons, listeners, services, etc
     * @param url URL.
     * @param resourceBundle ResourceBundle.
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Setting all map sub scenes to a zoomed out map view.
        searchMapView.addLoadListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                // new page has loaded, process:
                searchMapView.setCentre(0, 0);
                searchMapView.setZoom(2);
            }
        });

        // Setup the initial helpText for the flight search
        firstSearchEntry.getProperties().put("helpText", "Flight location type to search");
        secondSearchEntry.getProperties().put("helpText", "Flight location to search");

        // Setting the cell value factories for the search flight entry table
        flightDbIDS.setCellValueFactory(new PropertyValueFactory<>("ID"));
        flightIDS.setCellValueFactory(new PropertyValueFactory<>("FlightID"));
        flightLocationTypeS.setCellValueFactory(new PropertyValueFactory<>("LocationType"));
        flightLocationS.setCellValueFactory(new PropertyValueFactory<>("Location"));
        flightAltitudeS.setCellValueFactory(new PropertyValueFactory<>("Altitude"));
        flightLatitudeS.setCellValueFactory(new PropertyValueFactory<>("Latitude"));
        flightLongitudeS.setCellValueFactory(new PropertyValueFactory<>("Longitude"));


        // Adding Listeners to search table so that the selected Items can be displayed in the single record viewer
        searchTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

            if (flightsRadioButton.isSelected()) {
                if (newSelection != null) {
                    FlightModel selected = (FlightModel) newSelection;
                    try {
                        setSearchFlightSingleRecord(selected);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }
            } else if (airlinesRadioButton.isSelected()) {
                if (newSelection != null) {
                    AirlineModel selected = (AirlineModel) newSelection;
                    try {
                        setSearchAirlineSingleRecord(selected);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }
            } else if (airportsRadioButton.isSelected()) {
                if (newSelection != null) {
                    AirportModel selected = (AirportModel) newSelection;
                    try {
                        setSearchAirportSingleRecord(selected);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }
            } else if (routesRadioButton.isSelected()) {
                if (newSelection != null) {
                    RouteModel selected = (RouteModel) newSelection;
                    try {
                        setSearchRouteSingleRecord(selected);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }
            }
        });

        searchFlightSingleRecordTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            onFlightEntrySelected((FlightEntryModel) newSelection);
        });

        try {
            setSearchFlightSingleRecord(null);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Initializing the required services and tables for the search tab
        dataExporter = new DataExporter();
        airlineService = new AirlineService();
        airportService = new AirportService();
        routeService = new RouteService();
        flightService = new FlightService();



        setSearchTableFlights();


        // Lists of elements and label elements which need visibility toggled
        List<TextField> elements = Arrays.asList(airportIDS, airportNameS, airportCityS, airportCountryS, airportIATAS, airportICAOS, airportLatitudeS,
                airportLongitudeS, airportAltitudeS, airportTimezoneS, airportDSTS, airportTZS, airlineIDS, airlineNameS, airlineAliasS, airlineIATAS, airlineICAOS,
                airlineCallsignS, airlineCountryS, airlineActiveS, routeIDS, routeAirlineS, routeAirlineIDS, routeDepAirportS, routeDepAirportIDS, routeDesAirportS, routeDesAirportIDS,
                routeCodeshareS, routeStopsS, routeEquipS);

        List<Label> lblElements = Arrays.asList(lblAirportIDS, lblAirportNameS, lblAirportCityS, lblAirportCountryS, lblAirportIATAS, lblAirportICAOS,
                lblAirportLatitudeS, lblAirportLongitudeS, lblAirportAltitudeS, lblAirportTimezoneS, lblAirportDSTS, lblAirportTZS, lblAirlineIDS, lblAirlineNameS, lblAirlineAliasS,
                lblAirlineIATAS, lblAirlineICAOS, lblAirlineCallsignS, lblAirlineCountryS, lblAirlineActiveS, lblRouteIDS, lblRouteAirlineS, lblRouteAirlineIDS, lblRouteDepAirportS, lblRouteDepAirportIDS,
                lblRouteDesAirportS, lblRouteDesAirportIDS, lblRouteCodeshareS, lblRouteStopsS, lblRouteEquipS);

        ArrayList<TextField> elementsVisible = new ArrayList<>(elements);
        ArrayList<Label> lblElementsVisible = new ArrayList<>(lblElements);

        // Emptying and hiding fields
        mainMenuController.setFieldsEmpty(elementsVisible);
        mainMenuController.setLabelsEmpty(lblElementsVisible, false);


    }

    private void clearSearchMap() {
        // Remove airport marker
        if (searchAirportMarker != -1) {
            searchMapView.removeMarker(searchAirportMarker);
            searchAirportMarker = -1;
        }

        // Remove airline paths
        for (int pathID : searchAirlinePaths) {
            searchMapView.removePath(pathID);
        }
        searchAirlinePaths.clear();

        // Remove route path
        if (searchRoutePath != -1) {
            searchMapView.removePath(searchRoutePath);
            searchRoutePath = -1;
        }

        // Remove flight path
        if (searchFlightPath != -1) {
            searchMapView.removePath(searchFlightPath);
            searchFlightPath = -1;
        }
    }


    /**
     * setSearchFlightSingleRecord
     *
     * Sets the flight single record table for the search tab to contain relevant entries
     * @param flightModel - flight Model used to populate table
     * @throws SQLException occurs when any interactions with the ResultSet fail
     */
    private void setSearchFlightSingleRecord(FlightModel flightModel) throws SQLException {
        clearSearchMap();

        if (flightModel == null) {
            searchFlightSingleRecordTableView.getItems().clear();
        } else {
            flightEntriesSearch = FXCollections.observableArrayList();
            int flightID = flightModel.getFlightId();
            ResultSet flightData = flightService.getData(flightID);

            List<Coord> coordinates = new ArrayList<>();

            while (flightData.next()) {
                Integer id = flightData.getInt(1);
                Integer flightId = flightData.getInt(2);
                String locationType = flightData.getString(3);
                String location = flightData.getString(4);
                Integer altitude = flightData.getInt(5);
                double latitude = flightData.getDouble(6);
                double longitude = flightData.getDouble(7);
                FlightEntryModel newEntry = new FlightEntryModel(id, flightId, locationType, location, altitude, latitude, longitude);
                flightEntriesSearch.add(newEntry);

                coordinates.add(new Coord(latitude, longitude));
            }
            searchFlightSingleRecordTableView.setItems(flightEntriesSearch);

            if (coordinates.size() >= 2) {
                searchFlightPath = searchMapView.addPath(coordinates, MainMenuController.DEFAULT_ROUTE_SYMBOLS, null, MainMenuController.DEFAULT_STROKE_WEIGHT);
                searchMapView.fitBounds(Bounds.fromCoordinateList(coordinates), 0.0);
            }
        }

    }

    /**
     * setSearchRouteSingleRecord
     *
     * Sets the route single record labels for the search tab to contain relevant data
     * @param routeModel - route model used to populate labels
     * @throws SQLException occurs when any interactions with the ResultSet fail
     */
    private void setSearchRouteSingleRecord(RouteModel routeModel) throws SQLException {
        List<TextField> elements = Arrays.asList(routeIDS, routeAirlineS, routeAirlineIDS, routeDepAirportS, routeDepAirportIDS, routeDesAirportS, routeDesAirportIDS,
                routeCodeshareS, routeStopsS, routeEquipS);
        ArrayList<TextField> elementsVisible = new ArrayList<>(elements);
        List<Label> lblElements = Arrays.asList(lblRouteIDS, lblRouteAirlineS, lblRouteAirlineIDS, lblRouteDepAirportS, lblRouteDepAirportIDS,
                lblRouteDesAirportS, lblRouteDesAirportIDS, lblRouteCodeshareS, lblRouteStopsS, lblRouteEquipS);
        ArrayList<Label> lblElementsVisible = new ArrayList<>(lblElements);

        clearSearchMap();

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

                searchRoutePath = searchMapView.addPath(coordinates, MainMenuController.DEFAULT_ROUTE_SYMBOLS, null, MainMenuController.DEFAULT_STROKE_WEIGHT);
                searchMapView.fitBounds(Bounds.fromCoordinateList(coordinates), 5.0);
            }
        }
    }

    /**
     * setSearchAirlineSingleRecord
     *
     * Sets the airline single record labels for the search tab to contain relevant data
     * @param airlineModel - airline model used to populate labels
     * @throws SQLException occurs when any interactions with the ResultSet fail
     */
    private void setSearchAirlineSingleRecord(AirlineModel airlineModel) throws SQLException {
        List<TextField> elements = Arrays.asList(airlineIDS, airlineNameS, airlineAliasS, airlineIATAS, airlineICAOS, airlineCallsignS, airlineCountryS, airlineActiveS);
        ArrayList<TextField> elementsVisible = new ArrayList<>(elements);
        List<Label> lblElements = Arrays.asList(lblAirlineIDS, lblAirlineNameS, lblAirlineAliasS, lblAirlineIATAS, lblAirlineICAOS, lblAirlineCallsignS, lblAirlineCountryS, lblAirlineActiveS);
        ArrayList<Label> lblElementsVisible = new ArrayList<>(lblElements);

        clearSearchMap();

        if (airlineModel == null) {
            mainMenuController.setFieldsEmpty(elementsVisible);
            mainMenuController.setLabelsEmpty(lblElementsVisible, false);
        } else {
            ResultSet airlineData = airlineService.getData(airlineModel.getId());
            mainMenuController.setLabels(airlineData, elementsVisible);
            mainMenuController.setLabelsEmpty(lblElementsVisible, true);

            searchAirlinePaths.addAll(mainMenuController.showAirline(searchMapView, airlineModel.getId()));
        }
    }

    /**
     * setSearchAirportSingleRecord
     *
     * Sets aiport single record labels for the search table to contain relevant data
     * @param airportModel - airport model used to populate labels
     * @throws SQLException occurs when any interactions with the ResultSet fail
     */
    private void setSearchAirportSingleRecord(AirportModel airportModel) throws SQLException {
        List<TextField> elements = Arrays.asList(airportIDS, airportNameS, airportCityS, airportCountryS, airportIATAS, airportICAOS, airportLatitudeS, airportLongitudeS,
                airportAltitudeS, airportTimezoneS, airportDSTS, airportTZS);
        ArrayList<TextField> elementsVisible = new ArrayList<>(elements);
        List<Label> lblElements = Arrays.asList(lblAirportIDS, lblAirportNameS, lblAirportCityS, lblAirportCountryS, lblAirportIATAS, lblAirportICAOS, lblAirportLatitudeS, lblAirportLongitudeS,
                lblAirportAltitudeS, lblAirportTimezoneS, lblAirportDSTS, lblAirportTZS);
        ArrayList<Label> lblElementsVisible = new ArrayList<>(lblElements);

        clearSearchMap();

        if (airportModel == null) {
            mainMenuController.setFieldsEmpty(elementsVisible);
            mainMenuController.setLabelsEmpty(lblElementsVisible, false);

        } else {
            ResultSet airportData = airportService.getData(airportModel.getId());
            mainMenuController.setLabels(airportData, elementsVisible);
            mainMenuController.setLabelsEmpty(lblElementsVisible, true);

            searchAirportMarker = searchMapView.addMarker(Double.parseDouble(airportLatitudeS.getText()), Double.parseDouble(airportLongitudeS.getText()), airportNameS.getText(), null);
            searchMapView.setCentre(Double.parseDouble(airportLatitudeS.getText()), Double.parseDouble(airportLongitudeS.getText()));
            searchMapView.setZoom(11);
        }
    }

    /**
     * onDownloadSearchDataPressed
     *
     * Starts the download window, allowing the user to download the data displayed in the search table.
     * @param event user has clicked on the download button in the search tab
     */
    @FXML
    public void onDownloadSearchDataPressed(ActionEvent event) {
        // User creates the file they would like to download the data to
        File file = mainMenuController.selectFolder(event);

        if (file != null) {
            if (flightsRadioButton.isSelected()) {
                // Gets the data from the flight search table and passes it into data exporter
                if (flightSearchTable != null) {
                    ArrayList<ArrayList<Object>> flightSearchData = flightSearchTable.getData();
                    dataExporter.exportFlights(file, flightSearchData);
                }
            }
            else if (airportsRadioButton.isSelected()) {
                // Gets the data from the airport search table and passes it into data exporter
                if (airportSearchTable != null) {
                    dataExporter.exportAirports(file, airportSearchTable.getData());
                }
            }
            else if (airlinesRadioButton.isSelected()) {
                // Gets the data from the airline search table and passes it into data exporter
                if (airlineSearchTable != null) {
                    dataExporter.exportAirlines(file, airlineSearchTable.getData());
                }
            }
            else if (routesRadioButton.isSelected()) {
                // Gets the data from the route search table and passes it into data exporter
                if (routeSearchTable != null) {
                    dataExporter.exportRoutes(file, routeSearchTable.getData());
                }
            }
        }
    }

    /**
     * onFlightsRadioPressed
     *
     * Changes the UI of the search tab to be in the format to handle Flight searching,
     * flight results showing in tables, and flight single record viewing.
     */
    @FXML
    public void onFlightsRadioPressed() {

        List<TextField> allSearchTextFields = Arrays.asList(airportIDS, airportNameS, airportCityS, airportCountryS, airportIATAS, airportICAOS, airportLatitudeS,
                airportLongitudeS, airportAltitudeS, airportTimezoneS, airportDSTS, airportTZS, airlineIDS, airlineNameS, airlineAliasS, airlineIATAS, airlineICAOS,
                airlineCallsignS, airlineCountryS, airlineActiveS, routeIDS, routeAirlineS, routeAirlineIDS, routeDepAirportS, routeDepAirportIDS, routeDesAirportS, routeDesAirportIDS,
                routeCodeshareS, routeStopsS, routeEquipS);

        List<Label> allSearchLabels = Arrays.asList(lblAirportIDS, lblAirportNameS, lblAirportCityS, lblAirportCountryS, lblAirportIATAS, lblAirportICAOS,
                lblAirportLatitudeS, lblAirportLongitudeS, lblAirportAltitudeS, lblAirportTimezoneS, lblAirportDSTS, lblAirportTZS, lblAirlineIDS, lblAirlineNameS, lblAirlineAliasS,
                lblAirlineIATAS, lblAirlineICAOS, lblAirlineCallsignS, lblAirlineCountryS, lblAirlineActiveS, lblRouteIDS, lblRouteAirlineS, lblRouteAirlineIDS, lblRouteDepAirportS, lblRouteDepAirportIDS,
                lblRouteDesAirportS, lblRouteDesAirportIDS, lblRouteCodeshareS, lblRouteStopsS, lblRouteEquipS);


        ArrayList<TextField> textFields = new ArrayList<>(allSearchTextFields);
        ArrayList<Label> labels = new ArrayList<>(allSearchLabels);

        mainMenuController.setFieldsEmpty(textFields);
        mainMenuController.setLabelsEmpty(labels, false);

        searchFlightSingleRecordTableView.setVisible(true);
        searchFlightSingleRecordTableView.setDisable(false);

        flightsRadioButton.setSelected(true);
        airportsRadioButton.setSelected(false);
        airlinesRadioButton.setSelected(false);
        routesRadioButton.setSelected(false);

        firstSearchEntry.setText("");
        secondSearchEntry.setText("");
        thirdSearchEntry.setText("");
        fourthSearchEntry.setText("");

        firstSearchEntry.getProperties().put("helpText", "Flight location type to search");
        secondSearchEntry.getProperties().put("helpText", "Flight location to search");

        firstSearchType.setText("Location Type:");
        secondSearchType.setText("Location:");
        thirdSearchType.setVisible(false);
        fourthSearchType.setVisible(false);

        thirdSearchEntry.setVisible(false);
        thirdSearchEntry.setDisable(true);
        fourthSearchEntry.setVisible(false);
        fourthSearchEntry.setDisable(true);

        errorMessage.setText("");

        setSearchTableFlights();



    }

    /**
     * onAirportsRadioPressed
     *
     * Changes the UI of the search tab to be in the format to handle Airport searching,
     * airport results showing in tables, and airport single record viewing.
     */
    @FXML
    public void onAirportsRadioPressed() {

        List<TextField> allSearchTextFields = Arrays.asList(airportIDS, airportNameS, airportCityS, airportCountryS, airportIATAS, airportICAOS, airportLatitudeS,
                airportLongitudeS, airportAltitudeS, airportTimezoneS, airportDSTS, airportTZS, airlineIDS, airlineNameS, airlineAliasS, airlineIATAS, airlineICAOS,
                airlineCallsignS, airlineCountryS, airlineActiveS, routeIDS, routeAirlineS, routeAirlineIDS, routeDepAirportS, routeDepAirportIDS, routeDesAirportS, routeDesAirportIDS,
                routeCodeshareS, routeStopsS, routeEquipS);

        List<Label> allSearchLabels = Arrays.asList(lblAirportIDS, lblAirportNameS, lblAirportCityS, lblAirportCountryS, lblAirportIATAS, lblAirportICAOS,
                lblAirportLatitudeS, lblAirportLongitudeS, lblAirportAltitudeS, lblAirportTimezoneS, lblAirportDSTS, lblAirportTZS, lblAirlineIDS, lblAirlineNameS, lblAirlineAliasS,
                lblAirlineIATAS, lblAirlineICAOS, lblAirlineCallsignS, lblAirlineCountryS, lblAirlineActiveS, lblRouteIDS, lblRouteAirlineS, lblRouteAirlineIDS, lblRouteDepAirportS, lblRouteDepAirportIDS,
                lblRouteDesAirportS, lblRouteDesAirportIDS, lblRouteCodeshareS, lblRouteStopsS, lblRouteEquipS);


        ArrayList<TextField> textFields = new ArrayList<>(allSearchTextFields);
        ArrayList<Label> labels = new ArrayList<>(allSearchLabels);

        mainMenuController.setFieldsEmpty(textFields);
        mainMenuController.setLabelsEmpty(labels, false);


        searchFlightSingleRecordTableView.setVisible(false);
        searchFlightSingleRecordTableView.setDisable(true);

        flightsRadioButton.setSelected(false);
        airportsRadioButton.setSelected(true);
        airlinesRadioButton.setSelected(false);
        routesRadioButton.setSelected(false);

        firstSearchEntry.setText("");
        secondSearchEntry.setText("");
        thirdSearchEntry.setText("");
        fourthSearchEntry.setText("");

        firstSearchEntry.getProperties().put("helpText", "Airport name to search");
        secondSearchEntry.getProperties().put("helpText", "Airport city to search");
        thirdSearchEntry.getProperties().put("helpText", "Airport country to search");

        firstSearchType.setText("Name:");
        secondSearchType.setText("City:");
        thirdSearchType.setVisible(true);
        thirdSearchType.setText("Country:");
        fourthSearchType.setVisible(false);

        thirdSearchEntry.setVisible(true);
        thirdSearchEntry.setDisable(false);
        fourthSearchEntry.setVisible(false);
        fourthSearchEntry.setDisable(true);

        errorMessage.setText("");

        setSearchTableAirports();

    }

    /**
     * onAirlinesRadioPressed
     *
     * Changes the UI of the search tab to be in the format to handle Airlines searching,
     * airlines results showing in tables, and airlines single record viewing.
     */
    @FXML
    public void onAirlinesRadioPressed() {

        List<TextField> allSearchTextFields = Arrays.asList(airportIDS, airportNameS, airportCityS, airportCountryS, airportIATAS, airportICAOS, airportLatitudeS,
                airportLongitudeS, airportAltitudeS, airportTimezoneS, airportDSTS, airportTZS, airlineIDS, airlineNameS, airlineAliasS, airlineIATAS, airlineICAOS,
                airlineCallsignS, airlineCountryS, airlineActiveS, routeIDS, routeAirlineS, routeAirlineIDS, routeDepAirportS, routeDepAirportIDS, routeDesAirportS, routeDesAirportIDS,
                routeCodeshareS, routeStopsS, routeEquipS);

        List<Label> allSearchLabels = Arrays.asList(lblAirportIDS, lblAirportNameS, lblAirportCityS, lblAirportCountryS, lblAirportIATAS, lblAirportICAOS,
                lblAirportLatitudeS, lblAirportLongitudeS, lblAirportAltitudeS, lblAirportTimezoneS, lblAirportDSTS, lblAirportTZS, lblAirlineIDS, lblAirlineNameS, lblAirlineAliasS,
                lblAirlineIATAS, lblAirlineICAOS, lblAirlineCallsignS, lblAirlineCountryS, lblAirlineActiveS, lblRouteIDS, lblRouteAirlineS, lblRouteAirlineIDS, lblRouteDepAirportS, lblRouteDepAirportIDS,
                lblRouteDesAirportS, lblRouteDesAirportIDS, lblRouteCodeshareS, lblRouteStopsS, lblRouteEquipS);


        ArrayList<TextField> textFields = new ArrayList<>(allSearchTextFields);
        ArrayList<Label> labels = new ArrayList<>(allSearchLabels);

        mainMenuController.setFieldsEmpty(textFields);
        mainMenuController.setLabelsEmpty(labels, false);

        searchFlightSingleRecordTableView.setVisible(false);
        searchFlightSingleRecordTableView.setDisable(true);

        flightsRadioButton.setSelected(false);
        airportsRadioButton.setSelected(false);
        airlinesRadioButton.setSelected(true);
        routesRadioButton.setSelected(false);

        firstSearchEntry.setText("");
        secondSearchEntry.setText("");
        thirdSearchEntry.setText("");
        fourthSearchEntry.setText("");

        firstSearchEntry.getProperties().put("helpText", "Airline name to search");
        secondSearchEntry.getProperties().put("helpText", "Airline country to search");
        thirdSearchEntry.getProperties().put("helpText", "Airline callsign to search");

        firstSearchType.setText("Name:");
        secondSearchType.setText("Country:");
        thirdSearchType.setVisible(true);
        thirdSearchType.setText("Callsign:");
        fourthSearchType.setVisible(false);

        thirdSearchEntry.setVisible(true);
        thirdSearchEntry.setDisable(false);
        fourthSearchEntry.setVisible(false);
        fourthSearchEntry.setDisable(true);

        errorMessage.setText("");

        setSearchTableAirlines();

    }


    /**
     * onRoutesRadioPressed
     *
     * Changes the UI of the search tab to be in the format to handle Route searching,
     * route results showing in tables, and route single record viewing.
     */
    @FXML
    public void onRoutesRadioPressed() {

        List<TextField> allSearchTextFields = Arrays.asList(airportIDS, airportNameS, airportCityS, airportCountryS, airportIATAS, airportICAOS, airportLatitudeS,
                airportLongitudeS, airportAltitudeS, airportTimezoneS, airportDSTS, airportTZS, airlineIDS, airlineNameS, airlineAliasS, airlineIATAS, airlineICAOS,
                airlineCallsignS, airlineCountryS, airlineActiveS, routeIDS, routeAirlineS, routeAirlineIDS, routeDepAirportS, routeDepAirportIDS, routeDesAirportS, routeDesAirportIDS,
                routeCodeshareS, routeStopsS, routeEquipS);

        List<Label> allSearchLabels = Arrays.asList(lblAirportIDS, lblAirportNameS, lblAirportCityS, lblAirportCountryS, lblAirportIATAS, lblAirportICAOS,
                lblAirportLatitudeS, lblAirportLongitudeS, lblAirportAltitudeS, lblAirportTimezoneS, lblAirportDSTS, lblAirportTZS, lblAirlineIDS, lblAirlineNameS, lblAirlineAliasS,
                lblAirlineIATAS, lblAirlineICAOS, lblAirlineCallsignS, lblAirlineCountryS, lblAirlineActiveS, lblRouteIDS, lblRouteAirlineS, lblRouteAirlineIDS, lblRouteDepAirportS, lblRouteDepAirportIDS,
                lblRouteDesAirportS, lblRouteDesAirportIDS, lblRouteCodeshareS, lblRouteStopsS, lblRouteEquipS);


        ArrayList<TextField> textFields = new ArrayList<>(allSearchTextFields);
        ArrayList<Label> labels = new ArrayList<>(allSearchLabels);

        mainMenuController.setFieldsEmpty(textFields);
        mainMenuController.setLabelsEmpty(labels, false);

        searchFlightSingleRecordTableView.setVisible(false);
        searchFlightSingleRecordTableView.setDisable(true);


        flightsRadioButton.setSelected(false);
        airportsRadioButton.setSelected(false);
        airlinesRadioButton.setSelected(false);
        routesRadioButton.setSelected(true);

        firstSearchEntry.setText("");
        secondSearchEntry.setText("");
        thirdSearchEntry.setText("");
        fourthSearchEntry.setText("");

        firstSearchEntry.getProperties().put("helpText", "Route source airport to search");
        secondSearchEntry.getProperties().put("helpText", "Route destination airport to search");
        thirdSearchEntry.getProperties().put("helpText", "Route stops to search");
        fourthSearchEntry.getProperties().put("helpText", "Route plane types to search");

        firstSearchType.setText("Source Airport:");
        secondSearchType.setText("Dest. Airport:");
        thirdSearchType.setVisible(true);
        thirdSearchType.setText("No. Stops:");
        fourthSearchType.setVisible(true);
        fourthSearchType.setText("Equipment:");

        thirdSearchEntry.setVisible(true);
        thirdSearchEntry.setDisable(false);
        fourthSearchEntry.setVisible(true);
        fourthSearchEntry.setDisable(false);

        errorMessage.setText("");

        setSearchTableRoutes();

    }


    /**
     * onSearchPressed
     *
     * Searches for data from the database, depending on what radio button is selected, also displaying the result
     * in the result tables, as well as setting up for the single record viewing
     * @throws SQLException occurs when any interactions with the ResultSet fail
     */
    @FXML
    public void onSearchPressed() throws SQLException {

        ArrayList<Object> fields = new ArrayList<>();
        Search searchInstance = new Search();
        ResultSet result;

        if (flightsRadioButton.isSelected()) {

            fields.add(firstSearchEntry.getText().isBlank() ? null : firstSearchEntry.getText());
            fields.add(secondSearchEntry.getText().isBlank() ? null : secondSearchEntry.getText());

            searchInstance.setSearchData(fields);
            result = searchInstance.searchFlight();

            if (result == null) {
                errorMessage.setText("Sorry but there are no results for your search.");
            } else if (!result.next()) {
                errorMessage.setText("Sorry but there are no results for your search.");
            } else {
                errorMessage.setText("");
            }

            result = searchInstance.searchFlight();
            flightSearchTable = new FlightTable(result);
            flightSearchTable.createTable();
            populateFlightTable(searchTableView, flightSearchTable.getData());

        } else if (airportsRadioButton.isSelected()) {

            fields.add(firstSearchEntry.getText().isBlank() ? null : firstSearchEntry.getText());
            fields.add(secondSearchEntry.getText().isBlank() ? null : secondSearchEntry.getText());
            fields.add(thirdSearchEntry.getText().isBlank() ? null : thirdSearchEntry.getText());

            searchInstance.setSearchData(fields);
            result = searchInstance.searchAirport();
            if (!result.next()) {
                errorMessage.setText("Sorry but there are no results for your search.");

            } else {
                errorMessage.setText("");

            }

            result = searchInstance.searchAirport();
            airportSearchTable = new AirportTable(result);
            airportSearchTable.createTable();
            populateAirportTable(searchTableView, airportSearchTable.getData());

        } else if (airlinesRadioButton.isSelected()) {

            fields.add(firstSearchEntry.getText().isBlank() ? null : firstSearchEntry.getText());
            fields.add(secondSearchEntry.getText().isBlank() ? null : secondSearchEntry.getText());
            fields.add(thirdSearchEntry.getText().isBlank() ? null : thirdSearchEntry.getText());

            searchInstance.setSearchData(fields);
            result = searchInstance.searchAirline();

            if (!result.next()) {
                errorMessage.setText("Sorry but there are no results for your search.");
            } else {
                errorMessage.setText("");
            }

            result = searchInstance.searchAirline();
            airlineSearchTable = new AirlineTable(result);
            airlineSearchTable.createTable();
            populateAirlineTable(searchTableView, airlineSearchTable.getData());

        } else if (routesRadioButton.isSelected()) {

            try {
                fields.add(firstSearchEntry.getText().isBlank() ? null : firstSearchEntry.getText());
                fields.add(secondSearchEntry.getText().isBlank() ? null : secondSearchEntry.getText());
                fields.add(thirdSearchEntry.getText().isBlank() ? -1 : Integer.parseInt(thirdSearchEntry.getText()));
                fields.add(fourthSearchEntry.getText().isBlank() ? null : fourthSearchEntry.getText());

                searchInstance.setSearchData(fields);
                result = searchInstance.searchRoute();

                if (result == null) {
                    errorMessage.setText("Sorry but there are no results for your search.");
                } else if (!result.next()) {
                    errorMessage.setText("Sorry but there are no results for your search.");
                } else {
                    errorMessage.setText("");
                }

                result = searchInstance.searchRoute();
                routeSearchTable = new RouteTable(result);
                routeSearchTable.createTable();
                populateRouteTable(searchTableView, routeSearchTable.getData());

            } catch (NumberFormatException e) {
                errorMessage.setText("Invalid entry for number of stops. (Must be an integer)");
            }
        }
    }

    /**
     * setSearchTableFlights
     *
     * Changes all the columns in the search table to match the attributes to of the Flights
     */
    public void setSearchTableFlights() {
        searchTableView.getItems().clear();
        searchTableView.getColumns().clear();
        TableColumn<FlightModel, String> flightIdCol = new TableColumn<>("Flight Id");
        flightIdCol.setMaxWidth(80);
        TableColumn<FlightModel, String> flightSrcLocationCol = new TableColumn<>("Source Location");
        flightSrcLocationCol.setMaxWidth(150);
        TableColumn<FlightModel, String> flightSrcAirportCol = new TableColumn<>("Source Airport");
        flightSrcAirportCol.setMaxWidth(140);
        TableColumn<FlightModel, String> flightDstLocationCol = new TableColumn<>("Destination Location");
        flightDstLocationCol.setMaxWidth(180);
        TableColumn<FlightModel, String> flightDstAirportCol = new TableColumn<>("Destination Airport");
        flightDstAirportCol.setMaxWidth(200);

        flightIdCol.setCellValueFactory(new PropertyValueFactory<>("FlightId"));
        flightSrcLocationCol.setCellValueFactory(new PropertyValueFactory<>("SourceLocation"));
        flightSrcAirportCol.setCellValueFactory(new PropertyValueFactory<>("SourceAirport"));
        flightDstLocationCol.setCellValueFactory(new PropertyValueFactory<>("DestinationLocation"));
        flightDstAirportCol.setCellValueFactory(new PropertyValueFactory<>("DestinationAirport"));

        searchTableView.getColumns().addAll(flightIdCol, flightSrcLocationCol, flightSrcAirportCol, flightDstLocationCol, flightDstAirportCol);

    }

    /**
     * setSearchTableAirport
     *
     * Changes all the columns in the search table to match the attributes to of the Airports
     */
    public void setSearchTableAirports() {
        searchTableView.getItems().clear();
        searchTableView.getColumns().clear();
        TableColumn<AirportModel, String> airportNameCol = new TableColumn<>("Name");
        TableColumn<AirportModel, String> airportCityCol = new TableColumn<>("City");
        TableColumn<AirportModel, String> airportCountryCol = new TableColumn<>("Country");
        TableColumn<AirportModel, Integer> airportIncRoutesCol = new TableColumn<>("No. In Routes");
        TableColumn<AirportModel, Integer> airportOutRoutesCol = new TableColumn<>("No. Out Routes");

        airportNameCol.setCellValueFactory(new PropertyValueFactory<>("AirportName"));
        airportCityCol.setCellValueFactory(new PropertyValueFactory<>("AirportCity"));
        airportCountryCol.setCellValueFactory(new PropertyValueFactory<>("AirportCountry"));
        airportIncRoutesCol.setCellValueFactory(new PropertyValueFactory<>("NoIncRoutes"));
        airportOutRoutesCol.setCellValueFactory(new PropertyValueFactory<>("NoOutRoutes"));

        searchTableView.getColumns().addAll(airportNameCol, airportCityCol, airportCountryCol, airportIncRoutesCol, airportOutRoutesCol);
    }

    /**
     * setSearchTableAirlines
     *
     * Changes all the columns in the search table to match the attributes to of the Airlines
     */
    public void setSearchTableAirlines() {
        searchTableView.getItems().clear();
        searchTableView.getColumns().clear();
        TableColumn<AirlineModel, String> airlineNameCol = new TableColumn<>("Name");
        TableColumn<AirlineModel, String> airlineAliasCol = new TableColumn<>("Alias");
        TableColumn<AirlineModel, String> airlineCountryCol = new TableColumn<>("Country");
        TableColumn<AirlineModel, String> airlineActiveCol = new TableColumn<>("Active");

        airlineNameCol.setCellValueFactory(new PropertyValueFactory<>("AirlineName"));
        airlineAliasCol.setCellValueFactory(new PropertyValueFactory<>("AirlineAlias"));
        airlineCountryCol.setCellValueFactory(new PropertyValueFactory<>("AirlineCountry"));
        airlineActiveCol.setCellValueFactory(new PropertyValueFactory<>("AirlineActive"));

        searchTableView.getColumns().addAll(airlineNameCol, airlineAliasCol, airlineCountryCol, airlineActiveCol);
    }


    /**
     * setSearchTableRoutes
     *
     * Changes all the columns in the search table to match the attributes to of the Routes
     */
    public void setSearchTableRoutes() {
        searchTableView.getItems().clear();
        searchTableView.getColumns().clear();
        TableColumn<RouteModel, String> routeAirlineCol = new TableColumn<>("Airline");
        routeAirlineCol.setMaxWidth(100);
        TableColumn<RouteModel, String> routeSrcAirportCol = new TableColumn<>("Source Airport");
        routeSrcAirportCol.setMaxWidth(120);
        TableColumn<RouteModel, String> routeDestAirportCol = new TableColumn<>("Destination Airport");
        routeDestAirportCol.setMaxWidth(180);
        TableColumn<RouteModel, String> routeStopsCol = new TableColumn<>("No. Stops");
        routeStopsCol.setMaxWidth(80);
        TableColumn<RouteModel, String> routeEquipmentCol = new TableColumn<>("Equipment");
        routeEquipmentCol.setMaxWidth(150);

        routeAirlineCol.setCellValueFactory(new PropertyValueFactory<>("RouteAirline"));
        routeSrcAirportCol.setCellValueFactory(new PropertyValueFactory<>("RouteSrcAirport"));
        routeDestAirportCol.setCellValueFactory(new PropertyValueFactory<>("RouteDstAirport"));
        routeStopsCol.setCellValueFactory(new PropertyValueFactory<>("RouteStops"));
        routeEquipmentCol.setCellValueFactory(new PropertyValueFactory<>("RouteEquipment"));

        searchTableView.getColumns().addAll(routeAirlineCol, routeSrcAirportCol, routeDestAirportCol, routeStopsCol, routeEquipmentCol);

    }

    /**
     * populateFlightTable
     *
     * Populates the given flight table witht the given data
     * @param tableView - TableView
     * @param data - ArrayList of ArrayList of data
     * @throws SQLException occurs when any interactions with the ResultSet fail
     */
    private void populateFlightTable(TableView tableView, ArrayList<ArrayList<Object>> data) throws SQLException {
        ArrayList<FlightModel> list = new ArrayList<>();
        for (ArrayList<Object> datum : data) {
            ArrayList<Integer> idRange = new ArrayList<>();
            idRange.add((Integer) datum.get(0));
            if (datum.get(0) != datum.get(7)) {
                idRange.add((Integer) datum.get(7));
            }
            Integer flightId = (Integer) datum.get(1);
            String srcLocation = (String) datum.get(2);
            String srcAirportIata = (String) datum.get(3);
            String dstLocation = (String) datum.get(8);
            String dstAirportIata = (String) datum.get(9);
            ResultSet srcAirportSet = airportService.getData(srcAirportIata);
            String srcAirport = (srcAirportSet.next()) ? srcAirportSet.getString(2) : srcAirportIata;
            ResultSet dstAirportSet = airportService.getData(dstAirportIata);
            String dstAirport = (dstAirportSet.next()) ? dstAirportSet.getString(2) : dstAirportIata;
            list.add(new FlightModel(flightId, srcLocation, srcAirport, dstLocation, dstAirport, idRange));
        }
        flightModels = FXCollections.observableArrayList(list);
        tableView.setItems(flightModels);

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
     * populateRouteTable
     *
     * Populates the given route table with the given data
     * @param tableView - TableView
     * @param data - ArrayList of ArrayList of Object
     */
    private void populateRouteTable(TableView tableView, ArrayList<ArrayList<Object>> data) {
        ArrayList<RouteModel> list = new ArrayList<>();
        ArrayList<String> airlineCodes = new ArrayList<>();
        ArrayList<String> srcAirportCodes = new ArrayList<>();
        ArrayList<String> dstAirportCodes = new ArrayList<>();
        for (ArrayList<Object> datum : data) {
            String airline = (String) datum.get(1);
            String srcAirport = (String) datum.get(3);
            String srcAirportCode = (String) datum.get(3);
            String dstAirport = (String) datum.get(5);
            String dstAirportCode = (String) datum.get(5);
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
            list.add(new RouteModel(airline, srcAirport, dstAirport, stops, equipmentString.toString(), id, srcAirportCode, dstAirportCode));
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
     * onFlightEntrySelected
     *
     * Called when the currently selected flight entry changes.
     */
    private void onFlightEntrySelected(FlightEntryModel flightEntryModel) {
        if (flightMapMarker != -1) {
            searchMapView.removeMarker(flightMapMarker);
            flightMapMarker = -1;
        }
        if (flightEntryModel != null) {
            Coord coord = new Coord(flightEntryModel.getLatitude(), flightEntryModel.getLongitude());
            flightMapMarker = searchMapView.addMarker(coord, null, MarkerIcon.PLANE_ICON);
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

}
