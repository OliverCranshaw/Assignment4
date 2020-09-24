package seng202.team5.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import seng202.team5.App;
import seng202.team5.data.ConcreteDeleteData;
import seng202.team5.data.ConcreteUpdateData;
import seng202.team5.model.*;
import seng202.team5.table.Search;
import seng202.team5.data.DataExporter;
import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.FlightService;
import seng202.team5.service.RouteService;
import seng202.team5.table.AirlineTable;
import seng202.team5.table.AirportTable;
import seng202.team5.table.FlightTable;
import seng202.team5.table.RouteTable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

import static java.lang.Math.abs;

public class MainMenuController implements Initializable {


    @FXML
    private TabPane mainTabs;

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
    private ComboBox airlineActiveDropdown;

    @FXML
    private Button airlineApplyFilterButton;

    @FXML
    private TextField routeSourceAirportField;

    @FXML
    private TextField routeDestAirportField;

    @FXML
    private TextField routeEquipmentField;

    @FXML
    private TextField airportCountryField;

    @FXML
    private TableView airportTableView;

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
    private TableColumn airportNameCol;

    @FXML
    private TableColumn airportCityCol;

    @FXML
    private TableColumn airportCountryCol;

    @FXML
    private Button airportApplyFilter;

    @FXML
    private Button routeApplyFilter;

    @FXML
    private ComboBox routeStopsComboBox;


    @FXML
    private TableColumn flightIdCol;

    @FXML
    private TableColumn flightSrcLocationCol;

    @FXML
    private TableColumn flightSrcAirportCol;

    @FXML
    private TableColumn flightDstLocationCol;

    @FXML
    private TableColumn flightDstAirportCol;

    @FXML
    private TableView flightTableView;

    @FXML
    private TableView searchTableView;

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
    private TableColumn flightDbID;

    @FXML
    private TableColumn flightID;

    @FXML
    private TableColumn flightLocationType;

    @FXML
    private TableColumn flightLocation;

    @FXML
    private TableColumn flightAltitude;

    @FXML
    private TableColumn flightLatitude;

    @FXML
    private TableColumn flightLongitude;

    @FXML
    private TableView flightSingleRecordTableView;

    @FXML
    private Button modifyAirportBtn;

    @FXML
    private Button modifyAirlineBtn;

    @FXML
    private Button modifyRouteBtn;

    @FXML
    private Button airportSaveBtn;

    @FXML
    private Button airportCancelBtn;

    @FXML
    private Button airlineSaveBtn;

    @FXML
    private Button airlineCancelBtn;

    @FXML
    private Button routeSaveBtn;

    @FXML
    private Button routeCancelBtn;

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
    private Label airportInvalidFormatLbl;

    @FXML
    private Button airportDeleteBtn;

    @FXML
    private Button airlineDeleteBtn;

    @FXML
    private Button routeDeleteBtn;

    @FXML
    private Button flightDeleteBtn;

    @FXML
    private TableColumn airportIncRoutesCol;

    @FXML
    private TableColumn airportOutRoutesCol;

    private DataExporter dataExporter;
    private AirlineService airlineService;
    private AirportService airportService;
    private RouteService routeService;
    private FlightService flightService;
    private AirlineTable airlineTable;
    private AirportTable airportTable;
    private RouteTable routeTable;
    private FlightTable flightTable;


    private ObservableList<AirlineModel> airlineModels;
    private ObservableList<AirportModel> airportModels;
    private ObservableList<RouteModel> routeModels;
    private ObservableList<FlightModel> flightModels;
    private ObservableList<FlightEntryModel> flightEntries;
    private ObservableList<FlightEntryModel> flightEntriesSearch;


    /**
     * Initializer for MainMenuController
     * Sets up all tables, buttons, listeners, services, etc
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Setting the cell value factories for the airline table
        airlineNameCol.setCellValueFactory(new PropertyValueFactory<>("AirlineName"));
        airlineAliasCol.setCellValueFactory(new PropertyValueFactory<>("AirlineAlias"));
        airlineCountryCol.setCellValueFactory(new PropertyValueFactory<>("AirlineCountry"));
        airlineActiveCol.setCellValueFactory(new PropertyValueFactory<>("AirlineActive"));
        // Setting the airline dropdown menu items
        airlineActiveDropdown.getItems().removeAll(airlineActiveDropdown.getItems());
        airlineActiveDropdown.getItems().addAll("", "Yes", "No");

        // Setting the cell value factories for the airport table
        airportNameCol.setCellValueFactory(new PropertyValueFactory<>("AirportName"));
        airportCityCol.setCellValueFactory(new PropertyValueFactory<>("AirportCity"));
        airportCountryCol.setCellValueFactory(new PropertyValueFactory<>("AirportCountry"));
        airportIncRoutesCol.setCellValueFactory(new PropertyValueFactory<>("NoIncRoutes"));
        airportOutRoutesCol.setCellValueFactory(new PropertyValueFactory<>("NoOutRoutes"));

        // Setting the cell value factories for the route table
        routeAirlineCol.setCellValueFactory(new PropertyValueFactory<>("RouteAirline"));
        routeSrcAirportCol.setCellValueFactory(new PropertyValueFactory<>("RouteSrcAirport"));
        routeDestAirportCol.setCellValueFactory(new PropertyValueFactory<>("RouteDstAirport"));
        routeStopsCol.setCellValueFactory(new PropertyValueFactory<>("RouteStops"));
        routeEquipmentCol.setCellValueFactory(new PropertyValueFactory<>("RouteEquipment"));
        // Setting the route dropdown menu items
        routeStopsComboBox.getItems().removeAll(airlineActiveDropdown.getItems());
        routeStopsComboBox.getItems().addAll("", "direct", "not direct");

        // Setting the cell value factories for the flight table
        flightIdCol.setCellValueFactory(new PropertyValueFactory<>("FlightId"));
        flightSrcLocationCol.setCellValueFactory(new PropertyValueFactory<>("SourceLocation"));
        flightSrcAirportCol.setCellValueFactory(new PropertyValueFactory<>("SourceAirport"));
        flightDstLocationCol.setCellValueFactory(new PropertyValueFactory<>("DestinationLocation"));;
        flightDstAirportCol.setCellValueFactory(new PropertyValueFactory<>("DestinationAirport"));


        // Setting the cell value factories for the flight entry table
        flightDbID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        flightID.setCellValueFactory(new PropertyValueFactory<>("FlightID"));
        flightLocationType.setCellValueFactory(new PropertyValueFactory<>("LocationType"));
        flightLocation.setCellValueFactory(new PropertyValueFactory<>("Location"));
        flightAltitude.setCellValueFactory(new PropertyValueFactory<>("Altitude"));
        flightLatitude.setCellValueFactory(new PropertyValueFactory<>("Latitude"));
        flightLongitude.setCellValueFactory(new PropertyValueFactory<>("Longitude"));

        // Setting the cell value factories for the search flight entry table
        flightDbIDS.setCellValueFactory(new PropertyValueFactory<>("ID"));
        flightIDS.setCellValueFactory(new PropertyValueFactory<>("FlightID"));
        flightLocationTypeS.setCellValueFactory(new PropertyValueFactory<>("LocationType"));
        flightLocationS.setCellValueFactory(new PropertyValueFactory<>("Location"));
        flightAltitudeS.setCellValueFactory(new PropertyValueFactory<>("Altitude"));
        flightLatitudeS.setCellValueFactory(new PropertyValueFactory<>("Latitude"));
        flightLongitudeS.setCellValueFactory(new PropertyValueFactory<>("Longitude"));

        // Disabling the modify buttons for the
        modifyAirportBtn.setDisable(true);
        modifyAirlineBtn.setDisable(true);
        modifyRouteBtn.setDisable(true);

        // Adding Listeners to all five tables so that the selected Items can be displayed in the single record viewer
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
        flightTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                FlightModel selected = (FlightModel) newSelection;
                try {
                    setFlightSingleRecord(selected);
                    flightDeleteBtn.setDisable(false);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
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
                    setAirlineUpdateColour(null);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });

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


        try {
            setFlightSingleRecord(null);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            setSearchFlightSingleRecord(null);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        dataExporter = new DataExporter();


        // Initializing the required services and tables for the main menu
        airlineService = new AirlineService();
        airportService = new AirportService();
        routeService = new RouteService();
        flightService = new FlightService();
        airlineTable = new AirlineTable(airlineService.getData(null, null, null));
        airportTable = new AirportTable(airportService.getData(null, null, null));
        routeTable = new RouteTable(routeService.getData(null, null, -1, null));
        flightTable = new FlightTable(flightService.getData(null, null));


        // Creating and populate all the tables with data
        try {
            airlineTable.createTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        populateAirlineTable(rawAirlineTable, airlineTable.getData());


        try {
            airportTable.createTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        populateAirportTable(airportTableView, airportTable.getData());

        try {
            routeTable.createTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        populateRouteTable(routeTableView, routeTable.getData());


        try {
            flightTable.createTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            populateFlightTable(flightTableView, flightTable.getData());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        setSearchTableFlights();

        // Setting all modify buttons to visible
        airportSaveBtn.setVisible(false);
        airportCancelBtn.setVisible(false);
        airportDeleteBtn.setVisible(false);
        airlineSaveBtn.setVisible(false);
        airlineCancelBtn.setVisible(false);
        airlineDeleteBtn.setVisible(false);
        routeSaveBtn.setVisible(false);
        routeCancelBtn.setVisible(false);
        routeDeleteBtn.setVisible(false);
        flightDeleteBtn.setDisable(true);

        // Lists of elements and label elements which need visibility toggled
        List<TextField> elements = Arrays.asList(routeID, routeAirline, routeAirlineID, routeDepAirport, routeDepAirportID, routeDesAirport, routeDesAirportID,
                routeCodeshare, routeStops, routeEquip, airlineID, airlineName, airlineAlias, airlineIATA, airlineICAO, airlineCallsign, airlineCountry, airlineActive,
                airportID, airportName, airportCity, airportCountry, airportIATA, airportICAO, airportLatitude, airportLongitude,
                airportAltitude, airportTimezone, airportDST, airportTZ, airportIDS, airportNameS, airportCityS, airportCountryS, airportIATAS, airportICAOS, airportLatitudeS,
                airportLongitudeS, airportAltitudeS, airportTimezoneS, airportDSTS, airportTZS, airlineIDS, airlineNameS, airlineAliasS, airlineIATAS, airlineICAOS,
                airlineCallsignS, airlineCountryS, airlineActiveS, routeIDS, routeAirlineS, routeAirlineIDS, routeDepAirportS, routeDepAirportIDS, routeDesAirportS, routeDesAirportIDS,
                routeCodeshareS, routeStopsS, routeEquipS);

        List<Label> lblElements = Arrays.asList(lblRouteID, lblRouteAirline, lblRouteAirlineID, lblRouteDepAirport, lblRouteDepAirportID,
                lblRouteDesAirport, lblRouteDesAirportID, lblRouteCodeshare, lblRouteStops, lblRouteEquip, lblAirlineID, lblAirlineName, lblAirlineAlias,
                lblAirlineIATA, lblAirlineICAO, lblAirlineCallsign, lblAirlineCountry, lblAirlineActive, lblAirportID, lblAirportName, lblAirportCity, lblAirportCountry, lblAirportIATA, lblAirportICAO, lblAirportLatitude, lblAirportLongitude,
                lblAirportAltitude, lblAirportTimezone, lblAirportDST, lblAirportTZ, lblAirportIDS, lblAirportNameS, lblAirportCityS, lblAirportCountryS, lblAirportIATAS, lblAirportICAOS,
                lblAirportLatitudeS, lblAirportLongitudeS, lblAirportAltitudeS, lblAirportTimezoneS, lblAirportDSTS, lblAirportTZS, lblAirlineIDS, lblAirlineNameS, lblAirlineAliasS,
                lblAirlineIATAS, lblAirlineICAOS, lblAirlineCallsignS, lblAirlineCountryS, lblAirlineActiveS, lblRouteIDS, lblRouteAirlineS, lblRouteAirlineIDS, lblRouteDepAirportS, lblRouteDepAirportIDS,
                lblRouteDesAirportS, lblRouteDesAirportIDS, lblRouteCodeshareS, lblRouteStopsS, lblRouteEquipS);

        ArrayList<TextField> elementsVisible = new ArrayList<TextField>(elements);
        ArrayList<Label> lblElementsVisible = new ArrayList<Label>(lblElements);

        // Emptying and hiding fields
        setFieldsEmpty(elementsVisible);
        setLabelsEmpty(lblElementsVisible, false);


    }

    /**
     * setFlightSingleRecord
     *
     * Sets the flight single record table to contain the relevant entries
     * from the given flight model
     * @param flightModel - flight model used to populate table
     * @throws SQLException
     */
    private void setFlightSingleRecord(FlightModel flightModel) throws SQLException {
        if (flightModel == null) {
            flightSingleRecordTableView.getItems().clear();
        } else {
            flightEntries = FXCollections.observableArrayList();
            Integer flightID = flightModel.getFlightId();
            ResultSet flightData = flightService.getData(flightID);
            while (flightData.next()) {
                Integer id = flightData.getInt(1);
                Integer flightId = flightData.getInt(2);
                String locationType = flightData.getString(3);
                String location = flightData.getString(4);
                Integer altitude = flightData.getInt(5);
                Double latitude = flightData.getDouble(6);
                Double longitude = flightData.getDouble(7);
                FlightEntryModel newEntry = new FlightEntryModel(id, flightId, locationType, location, altitude, latitude, longitude);
                flightEntries.add(newEntry);
            }
            flightSingleRecordTableView.setItems(flightEntries);
        }
    }

    /**
     * setRouteSingleRecord
     *
     * Sets the route single record labels to contain the relevant entries
     * @param routeModel - Route model used to populate labels
     * @throws SQLException
     */
    private void setRouteSingleRecord(RouteModel routeModel) throws SQLException {
        List<TextField> elements = Arrays.asList(routeID, routeAirline, routeAirlineID, routeDepAirport, routeDepAirportID, routeDesAirport, routeDesAirportID,
                routeCodeshare, routeStops, routeEquip);
        ArrayList<TextField> elementsVisible = new ArrayList<TextField>(elements);
        List<Label> lblElements = Arrays.asList(lblRouteID, lblRouteAirline, lblRouteAirlineID, lblRouteDepAirport, lblRouteDepAirportID,
                lblRouteDesAirport, lblRouteDesAirportID, lblRouteCodeshare, lblRouteStops, lblRouteEquip);
        ArrayList<Label> lblElementsVisible = new ArrayList<Label>(lblElements);
        if (routeModel == null) {
            setFieldsEmpty(elementsVisible);
            setLabelsEmpty(lblElementsVisible, false);
        } else {
            ResultSet routeData = routeService.getData(routeModel.getRouteId());
            setLabels(routeData, elementsVisible);
            setLabelsEmpty(lblElementsVisible, true);
        }
    }


    /**
     * setAirlineSingleRecord
     *
     * Sets the airline single record labels to contain the relevant entries
     * @param airlineModel - Airline Model used ot populate labels
     * @throws SQLException
     */
    private void setAirlineSingleRecord(AirlineModel airlineModel) throws SQLException {
        List<TextField> elements = Arrays.asList(airlineID, airlineName, airlineAlias, airlineIATA, airlineICAO, airlineCallsign, airlineCountry, airlineActive);
        ArrayList<TextField> elementsVisible = new ArrayList<TextField>(elements);
        List<Label> lblElements = Arrays.asList(lblAirlineID, lblAirlineName, lblAirlineAlias, lblAirlineIATA, lblAirlineICAO, lblAirlineCallsign, lblAirlineCountry, lblAirlineActive);
        ArrayList<Label> lblElementsVisible = new ArrayList<>(lblElements);
        if (airlineModel == null) {
            setFieldsEmpty(elementsVisible);
            setLabelsEmpty(lblElementsVisible, false);
        } else {
            ResultSet airlineData = airlineService.getData(airlineModel.getId());
            setLabels(airlineData, elementsVisible);
            setLabelsEmpty(lblElementsVisible, true);
        }
    }

    /**
     * setAirportSingleRecord
     *
     * Sets the airport single record labels to contain relevant entries
     * @param airportModel - airport Model used to populate labels
     * @throws SQLException
     */
    private void setAirportSingleRecord(AirportModel airportModel) throws SQLException {
        List<TextField> elements = Arrays.asList(airportID, airportName, airportCity, airportCountry, airportIATA, airportICAO, airportLatitude, airportLongitude,
                airportAltitude, airportTimezone, airportDST, airportTZ);
        ArrayList<TextField> elementsVisible = new ArrayList<TextField>(elements);
        List<Label> lblElements = Arrays.asList(lblAirportID, lblAirportName, lblAirportCity, lblAirportCountry, lblAirportIATA, lblAirportICAO, lblAirportLatitude, lblAirportLongitude,
                lblAirportAltitude, lblAirportTimezone, lblAirportDST, lblAirportTZ);
        ArrayList<Label> lblElementsVisible = new ArrayList<Label>(lblElements);

        if (airportModel == null) {
            setFieldsEmpty(elementsVisible);
            setLabelsEmpty(lblElementsVisible, false);

        } else {
            ResultSet airportData = airportService.getData(airportModel.getId());
            setLabels(airportData, elementsVisible);
            setLabelsEmpty(lblElementsVisible, true);
        }
    }


    /**
     * setSearchFlightSingleRecord
     *
     * Sets the flight single record table for the search tab to contain relevant entries
     * @param flightModel - flight Model used to populate table
     * @throws SQLException
     */
    private void setSearchFlightSingleRecord(FlightModel flightModel) throws SQLException {
        if (flightModel == null) {
            searchFlightSingleRecordTableView.getItems().clear();
        } else {
            flightEntriesSearch = FXCollections.observableArrayList();
            Integer flightID = flightModel.getFlightId();
            ResultSet flightData = flightService.getData(flightID);
            while (flightData.next()) {
                Integer id = flightData.getInt(1);
                Integer flightId = flightData.getInt(2);
                String locationType = flightData.getString(3);
                String location = flightData.getString(4);
                Integer altitude = flightData.getInt(5);
                Double latitude = flightData.getDouble(6);
                Double longitude = flightData.getDouble(7);
                FlightEntryModel newEntry = new FlightEntryModel(id, flightId, locationType, location, altitude, latitude, longitude);
                flightEntriesSearch.add(newEntry);
            }
            searchFlightSingleRecordTableView.setItems(flightEntriesSearch);

        }

    }

    /**
     * setSearchRouteSingleRecord
     *
     * Sets the route single record labels for the search tab to contain relevant data
     * @param routeModel - route model used to populate labels
     * @throws SQLException
     */
    private void setSearchRouteSingleRecord(RouteModel routeModel) throws SQLException {
        List<TextField> elements = Arrays.asList(routeIDS, routeAirlineS, routeAirlineIDS, routeDepAirportS, routeDepAirportIDS, routeDesAirportS, routeDesAirportIDS,
                routeCodeshareS, routeStopsS, routeEquipS);
        ArrayList<TextField> elementsVisible = new ArrayList<TextField>(elements);
        List<Label> lblElements = Arrays.asList(lblRouteIDS, lblRouteAirlineS, lblRouteAirlineIDS, lblRouteDepAirportS, lblRouteDepAirportIDS,
                lblRouteDesAirportS, lblRouteDesAirportIDS, lblRouteCodeshareS, lblRouteStopsS, lblRouteEquipS);
        ArrayList<Label> lblElementsVisible = new ArrayList<Label>(lblElements);
        if (routeModel == null) {
            setFieldsEmpty(elementsVisible);
            setLabelsEmpty(lblElementsVisible, false);
        } else {
            ResultSet routeData = routeService.getData(routeModel.getRouteId());
            setLabels(routeData, elementsVisible);
            setLabelsEmpty(lblElementsVisible, true);
        }
    }

    /**
     * setSearchAirlineSingleRecord
     *
     * Sets the airline single record labels for the search tab to contain relevant data
     * @param airlineModel - airline model used to populate labels
     * @throws SQLException
     */
    private void setSearchAirlineSingleRecord(AirlineModel airlineModel) throws SQLException {
        List<TextField> elements = Arrays.asList(airlineIDS, airlineNameS, airlineAliasS, airlineIATAS, airlineICAOS, airlineCallsignS, airlineCountryS, airlineActiveS);
        ArrayList<TextField> elementsVisible = new ArrayList<TextField>(elements);
        List<Label> lblElements = Arrays.asList(lblAirlineIDS, lblAirlineNameS, lblAirlineAliasS, lblAirlineIATAS, lblAirlineICAOS, lblAirlineCallsignS, lblAirlineCountryS, lblAirlineActiveS);
        ArrayList<Label> lblElementsVisible = new ArrayList<>(lblElements);
        if (airlineModel == null) {
            setFieldsEmpty(elementsVisible);
            setLabelsEmpty(lblElementsVisible, false);
        } else {
            ResultSet airlineData = airlineService.getData(airlineModel.getId());
            setLabels(airlineData, elementsVisible);
            setLabelsEmpty(lblElementsVisible, true);
        }
    }

    /**
     * setSearchAirportSingleRecord
     *
     * Sets aiport single record labels for the search table to contain relevant data
     * @param airportModel - airport model used to populate labels
     * @throws SQLException
     */
    private void setSearchAirportSingleRecord(AirportModel airportModel) throws SQLException {
        List<TextField> elements = Arrays.asList(airportIDS, airportNameS, airportCityS, airportCountryS, airportIATAS, airportICAOS, airportLatitudeS, airportLongitudeS,
                airportAltitudeS, airportTimezoneS, airportDSTS, airportTZS);
        ArrayList<TextField> elementsVisible = new ArrayList<TextField>(elements);
        List<Label> lblElements = Arrays.asList(lblAirportIDS, lblAirportNameS, lblAirportCityS, lblAirportCountryS, lblAirportIATAS, lblAirportICAOS, lblAirportLatitudeS, lblAirportLongitudeS,
                lblAirportAltitudeS, lblAirportTimezoneS, lblAirportDSTS, lblAirportTZS);
        ArrayList<Label> lblElementsVisible = new ArrayList<Label>(lblElements);

        if (airportModel == null) {
            setFieldsEmpty(elementsVisible);
            setLabelsEmpty(lblElementsVisible, false);

        } else {
            ResultSet airportData = airportService.getData(airportModel.getId());
            setLabels(airportData, elementsVisible);
            setLabelsEmpty(lblElementsVisible, true);
        }
    }

    /**
     * setFieldsEmpty
     *
     * Sets given arraylist of TextFields to be invisible and also sets their colour to black
     * @param elementsVisible - ArrayList of TextFields
     */
    private void setFieldsEmpty(ArrayList<TextField> elementsVisible) {
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
    private void setLabelsEmpty(ArrayList<Label> elementsVisible, Boolean bool) {
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
     * @throws SQLException
     */
    private void setLabels(ResultSet elementData, ArrayList<TextField> elementsVisible) throws SQLException {
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
     * @param event
     */
    public void onHelp(ActionEvent event) {
        System.out.println("Help requested: " + event);

        Scene scene = mainTabs.getScene();
        HelpHandler.startHelp(scene);
    }


    /**
     * selectFolder
     *
     * Gets input from the user on where the file should be saved
     * @param event
     * @return - File
     */
    public File selectFolder(ActionEvent event) {
        dataExporter = new DataExporter();

        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Text Documents (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        extensionFilter = new FileChooser.ExtensionFilter("CSV (Comma-Separated Values) (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

        return file;
    }

    /**
     * onAddAirportPressed
     *
     * Handles add airport button pressing by showing new window which contains
     * UI to add new airport
     * @param event
     * @throws IOException
     */
    @FXML
    public void onAddAirportPressed(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(App.class.getResource("add_airport.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Add Airport");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
        stage.show();
    }


    /**
     * onUploadAirportDataPressed
     *
     * Starts the upload airport data window
     * @param event
     * @throws IOException
     */
    @FXML
    public void onUploadAirportDataPressed(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(App.class.getResource("upload_airports.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Upload Airport Data");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.show();
    }

    /**
     * onDownloadAirportDataPressed
     *
     * Starts the download airport data window
     * @param event
     * @throws IOException
     */
    @FXML
    public void onDownloadAirportDataPressed(ActionEvent event) throws IOException {
        File file = selectFolder(event);

        if (file != null) {
            dataExporter.exportAirports(file);
        }
    }


    /**
     * onAddAirlinePressed
     *
     * Starts the add airline window
     * @param event
     * @throws IOException
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
     * Stars the upload airline window
     * @param event
     * @throws IOException
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
     * Stars the download airline window
     * @param event
     * @throws IOException
     */
    @FXML
    public void onDownloadAirlineDataPressed(ActionEvent event) throws IOException {
        File file = selectFolder(event);

        if (file != null) {
            dataExporter.exportAirlines(file);
        }
    }


    /**
     * onUploadFlightPressed
     *
     * Starts the upload flight window
     * @param event
     * @throws IOException
     */
    @FXML
    public void onUploadFlightPressed(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(App.class.getResource("upload_flight.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Upload Flight");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.show();
    }

    /**
     * onDownloadFlightsPressed
     *
     * Starts the download flights window
     * @param event
     * @throws IOException
     */
    @FXML
    public void onDownloadFlightsPressed(ActionEvent event) throws IOException {
        File file = selectFolder(event);

        if (file != null) {
            dataExporter.exportFlights(file);
        }
    }


    /**
     * onAddRoutePressed
     *
     * Starts the add route window
     * @param event
     * @throws IOException
     */
    @FXML
    public void onAddRoutePressed(ActionEvent event) throws IOException {
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
     * @param event
     * @throws IOException
     */
    @FXML
    public void onUploadRouteDataPressed(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(App.class.getResource("upload_routes.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Upload Route Data");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.show();
    }

    /**
     * onDownloadRouteDataPressed
     *
     * Starts the download route data window
     * @param event
     * @throws IOException
     */
    @FXML
    public void onDownloadRouteDataPressed(ActionEvent event) throws IOException {
        File file = selectFolder(event);

        if (file != null) {
            dataExporter.exportRoutes(file);
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


        ArrayList<TextField> textFields = new ArrayList<TextField>(allSearchTextFields);
        ArrayList<Label> labels = new ArrayList<Label>(allSearchLabels);

        setFieldsEmpty(textFields);
        setLabelsEmpty(labels, false);

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


        ArrayList<TextField> textFields = new ArrayList<TextField>(allSearchTextFields);
        ArrayList<Label> labels = new ArrayList<Label>(allSearchLabels);

        setFieldsEmpty(textFields);
        setLabelsEmpty(labels, false);


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


        ArrayList<TextField> textFields = new ArrayList<TextField>(allSearchTextFields);
        ArrayList<Label> labels = new ArrayList<Label>(allSearchLabels);

        setFieldsEmpty(textFields);
        setLabelsEmpty(labels, false);

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


        ArrayList<TextField> textFields = new ArrayList<TextField>(allSearchTextFields);
        ArrayList<Label> labels = new ArrayList<Label>(allSearchLabels);

        setFieldsEmpty(textFields);
        setLabelsEmpty(labels, false);

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
     * @throws SQLException
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
            FlightTable flightSearchTable = new FlightTable(result);
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
            AirportTable airportSearchTable = new AirportTable(result);
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
            AirlineTable airlineSearchTable = new AirlineTable(result);
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
                RouteTable routeSearchTable = new RouteTable(result);
                routeSearchTable.createTable();
                populateRouteTable(searchTableView, routeSearchTable.getData());

            } catch (NumberFormatException e) {
                errorMessage.setText("Invalid entry for number of stops. (Must be an integer)");
            }
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
     * populateAirportTable
     *
     * Populates the given airport table with the given data
     * @param tableView - TableView
     * @param data - ArrayList of ArrayList of Object
     */
    private void populateAirportTable(TableView tableView, ArrayList<ArrayList<Object>> data) {
        ArrayList<AirportModel> list = new ArrayList<>();
        for (ArrayList<Object> datum : data) {
            Integer id = (Integer) datum.get(0);
            String name = (String) datum.get(1);
            String city = (String) datum.get(2);
            String country = (String) datum.get(3);
            list.add(new AirportModel(name, city, country, id));
        }
        airportModels = FXCollections.observableArrayList(list);
        tableView.setItems(airportModels);
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
        for (ArrayList<Object> datum : data) {
            String airline = (String) datum.get(1);
            String srcAirport = (String) datum.get(3);
            String dstAirport = (String) datum.get(5);
            Integer stops = (Integer) datum.get(8);
            ArrayList<String> equipment = (ArrayList<String>) datum.get(9);
            StringBuffer equipmentString = new StringBuffer();
            for (String s: equipment) {
                equipmentString.append(s);
                equipmentString.append(", ");
            }
            Integer id = (Integer) datum.get(0);
            list.add(new RouteModel(airline, srcAirport, dstAirport, stops, equipmentString.toString(), id));
        }
        routeModels = FXCollections.observableArrayList(list);
        tableView.setItems(routeModels);
    }

    /**
     * populateFlightTable
     *
     * Populates the given flight table witht the given data
     * @param tableView - TableView
     * @param data - ArrayList of ArrayList of data
     * @throws SQLException
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
     * onAirlineApplyFilterButton
     *
     * Applies the selected filters to the airline table
     * @param actionEvent
     */
    @FXML
    public void onAirlineApplyFilterButton(ActionEvent actionEvent) {
        airlineTable.FilterTable(null, null);
        String countryText = countryAirlineField.getText();
        String activeText = (String) airlineActiveDropdown.getValue();
        if (activeText == null || activeText.equals("")) {
            activeText = null;
        } else {
            activeText = (activeText == "Yes") ? "Y" : "N";
        }
        ArrayList<String> newList = convertCSStringToArrayList(countryText);
        airlineTable.FilterTable(newList, activeText);

        populateAirlineTable(rawAirlineTable, airlineTable.getData());

    }

    /**
     * onAirportApplyFilterButton
     *
     * Applies the selected filters to the airline table
     * @param actionEvent
     */
    @FXML
    public void onAirportApplyFilterButton(ActionEvent actionEvent) {
        airportTable.FilterTable(null);
        String countryText = airportCountryField.getText();
        ArrayList newList = convertCSStringToArrayList(countryText);
        airportTable.FilterTable(newList);
        populateAirportTable(airportTableView, airportTable.getData());
    }


    /**
     * onRouteApplyFilterButton
     *
     * Applies the selected filter to the route table
     * @param actionEvent
     * @throws SQLException
     */
    @FXML
    public void onRouteApplyFilterButton(ActionEvent actionEvent) throws SQLException {
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
        ArrayList<String> equipmentList = convertCSStringToArrayList(equipmentText);
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
     * updateAirportTable
     *
     * Updates the airport table with data from the database
     * @throws SQLException
     */
    public void updateAirportTable() throws SQLException {
        airportTable = new AirportTable(airportService.getData(null, null, null));
        airportTable.createTable();
        populateAirportTable(airportTableView, airportTable.getData());
    }

    /**
     * updateAirlineTable
     *
     * Updates the airline table with data from the database
     * @throws SQLException
     */
    public void updateAirlineTable() throws SQLException {
        airlineTable = new AirlineTable(airlineService.getData(null, null, null));
        airlineTable.createTable();
        populateAirlineTable(rawAirlineTable, airlineTable.getData());
    }

    /**
     * updateRouteTable
     *
     * Updates the route table with data from the database
     * @throws SQLException
     */
    public void updateRouteTable() throws SQLException {
        routeTable = new RouteTable(routeService.getData(null, null, -1, null));
        routeTable.createTable();
        populateRouteTable(routeTableView, routeTable.getData());
    }


    /**
     * updateFlightTable
     *
     * Updates the flight table with data from the database
     * @throws SQLException
     */
    public void updateFlightTable() throws SQLException {
        flightTable = new FlightTable(flightService.getData(null, null));
        flightTable.createTable();
        populateFlightTable(flightTableView, flightTable.getData());
    }


    /**
     * setSearchTableFlights
     *
     * Changes all the columns in the search table to match the attributes to of the Flights
     */
    public void setSearchTableFlights() {
        searchTableView.getItems().clear();
        searchTableView.getColumns().clear();
        TableColumn<FlightModel, String> flightIdCol = new TableColumn<FlightModel, String>("Flight Id");
        TableColumn<FlightModel, String> flightSrcLocationCol = new TableColumn<FlightModel, String>("Source Location");
        TableColumn<FlightModel, String> flightSrcAirportCol = new TableColumn<FlightModel, String>("Source Airport");
        TableColumn<FlightModel, String> flightDstLocationCol = new TableColumn<FlightModel, String>("Destination Location");
        TableColumn<FlightModel, String> flightDstAirportCol = new TableColumn<FlightModel, String>("Destination Airport");

        flightIdCol.setCellValueFactory(new PropertyValueFactory<>("FlightId"));
        flightSrcLocationCol.setCellValueFactory(new PropertyValueFactory<>("SourceLocation"));
        flightSrcAirportCol.setCellValueFactory(new PropertyValueFactory<>("SourceAirport"));
        flightDstLocationCol.setCellValueFactory(new PropertyValueFactory<>("DestinationLocation"));;
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
        TableColumn<AirportModel, Integer> airportIncRoutesCol = new TableColumn<>("No. Incoming Routes");
        TableColumn<AirportModel, Integer> airportOutRoutesCol = new TableColumn<>("No. Outgoing Routes");

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
        TableColumn<RouteModel, String> routeSrcAirportCol = new TableColumn<>("Source Airport");
        TableColumn<RouteModel, String> routeDestAirportCol = new TableColumn<>("Destination Airport");
        TableColumn<RouteModel, String> routeStopsCol = new TableColumn<>("No. Stops");
        TableColumn<RouteModel, String> routeEquipmentCol = new TableColumn<>("Equipment");

        routeAirlineCol.setCellValueFactory(new PropertyValueFactory<>("RouteAirline"));
        routeSrcAirportCol.setCellValueFactory(new PropertyValueFactory<>("RouteSrcAirport"));
        routeDestAirportCol.setCellValueFactory(new PropertyValueFactory<>("RouteDstAirport"));
        routeStopsCol.setCellValueFactory(new PropertyValueFactory<>("RouteStops"));
        routeEquipmentCol.setCellValueFactory(new PropertyValueFactory<>("RouteEquipment"));

        searchTableView.getColumns().addAll(routeAirlineCol, routeSrcAirportCol, routeDestAirportCol, routeStopsCol, routeEquipmentCol);

    }

    /**
     * onAirportRefreshButton
     *
     * Updates the airport table from a button press
     * @param event
     * @throws SQLException
     */
    @FXML
    public void onAirportRefreshButton(ActionEvent event) throws SQLException {
        updateAirportTable();
    }

    /**
     * onAirlineRefreshButton
     *
     * Updates the airline table from a button press
     * @param event
     * @throws SQLException
     */
    @FXML
    public void onAirlineRefreshButton(ActionEvent event) throws  SQLException {
        updateAirlineTable();
    }

    /**
     * onRouteRefreshButton
     *
     * Updates the route table from a button press
     * @param event
     * @throws SQLException
     */
    @FXML
    public void onRouteRefreshButton(ActionEvent event) throws  SQLException {
        updateRouteTable();
    }


    /**
     * onFlightRefreshButton
     *
     * Updates the flight table from a button press
     * @param event
     * @throws SQLException
     */
    @FXML
    public void onFlightRefreshButton(ActionEvent event) throws  SQLException {
        updateFlightTable();
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
        for (String component : list) {
            result.add(component);
        }
        return result;
    }

    /**
     * onModifyAirlineBtnPressed
     *
     * Starts the modify airline mode
     * @param actionEvent
     * @throws SQLException
     */
    public void onModifyAirlineBtnPressed(ActionEvent actionEvent) throws SQLException {
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
     * onModifyRouteBtnPressed
     *
     * Starts the modify route mode
     * @param actionEvent
     * @throws SQLException
     */
    public void onModifyRouteBtnPressed(ActionEvent actionEvent) throws SQLException {
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
     * onModifyAirportBtnPressed
     *
     * Starts the modify airport mode
     * @param actionEvent
     * @throws SQLException
     */
    public void onModifyAirportBtnPressed(ActionEvent actionEvent) throws SQLException {
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
        ArrayList<TextField> elementsEditable = new ArrayList<TextField>(elements);
        setElementsEditable(elementsEditable, bool);
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
        ArrayList<TextField> elementsVisible = new ArrayList<TextField>(elements);
        setElementsEditable(elementsVisible, bool);
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
        ArrayList<TextField> elementsVisible = new ArrayList<TextField>(elements);
        setElementsEditable(elementsVisible, bool);
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
     * onAirportSaveBtnPressed
     *
     * Saves the airport if the given data if in the right form,
     * other wise handles errors and prompts user on where they are
     * @param event
     * @throws SQLException
     */
    @FXML
    public void onAirportSaveBtnPressed(ActionEvent event) throws SQLException {
        airportInvalidFormatLbl.setVisible(false);
        setAirportUpdateColour(null);
        Integer id = Integer.parseInt(airportID.getText());
        List<Object> elementList2 = null;
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
     * onAirlineSaveBtnPressed
     *
     * Saves the airline if the given data if in the right form,
     * other wise handles errors and prompts user on where they are
     * @param event
     * @throws SQLException
     */
    @FXML
    public void onAirlineSaveBtnPressed(ActionEvent event) throws SQLException {
        setAirlineUpdateColour(null);
        Integer id = Integer.parseInt(airlineID.getText());

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
     * onRouteSaveBtnPressed
     *
     * Saves the route if the given data if in the right form,
     * other wise handles errors and prompts user on where they are
     * @param event
     * @throws SQLException
     */
    @FXML
    public void onRouteSaveBtnPressed(ActionEvent event) throws SQLException {
        setRouteUpdateColour(null);
        Integer id = Integer.parseInt(routeID.getText());
        List<Object> elements;
        Integer stops = null;
        try {
            stops = Integer.parseInt(routeStops.getText());
        } catch (NumberFormatException e) {
            setRouteUpdateColour(4);
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
            } else {
                setRouteUpdateColour(abs(result) - 2);
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
        ArrayList<TextField> elements = new ArrayList<TextField>(element);
        if (index == null) {
            for (int i = 0; i < elements.size(); i++) {
                elements.get(i).setStyle("-fx-border-color: #000000;");
            }
        } else {
            elements.get(index).setStyle("-fx-border-color: #ff0000;");
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
        ArrayList<TextField> elementsVisible = new ArrayList<TextField>(elements);
        if (index == null) {
            for (TextField field : elements) {
                field.setStyle("-fx-border-color: #000000;");
            }
        } else {
            elements.get(index).setStyle("-fx-border-color: #ff0000;");
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
        ArrayList<TextField> elementsVisible = new ArrayList<TextField>(elements);
        if (index == null) {
            for (TextField field : elements) {
                field.setStyle("-fx-border-color: #000000;");
            }
        } else {
            elements.get(index).setStyle("-fx-border-color: #ff0000;");
        }
    }


    /**
     * onAirportCancelBtnPressed
     *
     * Cancels current airport modify
     * @param event
     * @throws SQLException
     */
    @FXML
    public void onAirportCancelBtnPressed(ActionEvent event) throws SQLException {
        airportInvalidFormatLbl.setVisible(false);
        setAirportElementsEditable(false);
        setAirportSingleRecord((AirportModel)airportTableView.getSelectionModel().getSelectedItem());
        airportCancelBtn.setVisible(false);
        airportSaveBtn.setVisible(false);
        airportDeleteBtn.setVisible(false);
        setAirportUpdateColour(null);
    }

    /**
     * onAirlineCancelBtnPressed
     *
     * Cancels current airline modify
     * @param event
     * @throws SQLException
     */
    @FXML
    public void onAirlineCancelBtnPressed(ActionEvent event) throws SQLException {
        setAirlineElementsEditable(false);
        setAirlineSingleRecord((AirlineModel)rawAirlineTable.getSelectionModel().getSelectedItem());
        airlineCancelBtn.setVisible(false);
        airlineSaveBtn.setVisible(false);
        airlineDeleteBtn.setVisible(false);
        setAirlineUpdateColour(null);
    }

    /**
     * onRouteCancelBtnPressed
     *
     * Cancels current route modify
     * @param actionEvent
     * @throws SQLException
     */
    @FXML
    public void onRouteCancelBtnPressed(ActionEvent actionEvent) throws SQLException {
        setRouteElementsEditable(false);
        setRouteSingleRecord((RouteModel)routeTableView.getSelectionModel().getSelectedItem());
        routeCancelBtn.setVisible(false);
        routeSaveBtn.setVisible(false);
        routeDeleteBtn.setVisible(false);
        setRouteUpdateColour(null);
    }


    /**
     * onAirportDeleteBtnPressed
     *
     * Deletes airport that is currently being modified
     * @param actionEvent
     * @throws SQLException
     */
    @FXML
    public void onAirportDeleteBtnPressed(ActionEvent actionEvent) throws SQLException {
        ConcreteDeleteData deleter = new ConcreteDeleteData();
        Integer id = Integer.parseInt(airportID.getText());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Airport");
        alert.setContentText("Delete Airport with ID: " + id + "?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(type -> {
            if (type == ButtonType.YES) {
                Boolean result = deleter.deleteAirport(id);
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
     * onAirlineDeleteBtnPressed
     *
     * Deletes airline that is currently being modified
     * @param actionEvent
     * @throws SQLException
     */
    public void onAirlineDeleteBtnPressed(ActionEvent actionEvent) {
        ConcreteDeleteData deleter = new ConcreteDeleteData();
        Integer id = Integer.parseInt(airlineID.getText());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Airline");
        alert.setContentText("Delete Airline with ID: " + id + "?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(type -> {
            if (type == ButtonType.YES) {
                Boolean result = deleter.deleteAirline(id);
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
     * onRouteDeleteBtnPressed
     *
     * Deletes route that is currently being modified
     * @param actionEvent
     * @throws SQLException
     */
    @FXML
    public void onRouteDeleteBtnPressed(ActionEvent actionEvent) {
        ConcreteDeleteData deleter = new ConcreteDeleteData();
        Integer id = Integer.parseInt(routeID.getText());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Route");
        alert.setContentText("Delete Route with ID: " + id + "?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(type -> {
            if (type == ButtonType.YES) {
                Boolean result = deleter.deleteRoute(id);
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
     * onFlightDeleteBtnPressed
     *
     * Deletes flight that is currently being modified
     * @param event
     * @throws SQLException
     */
    @FXML
    public void onFlightDeleteBtnPressed(ActionEvent event) {
        ConcreteDeleteData deleter = new ConcreteDeleteData();
        Integer flight_id = ((FlightEntryModel) flightSingleRecordTableView.getItems().get(0)).getFlightID();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Flight");
        alert.setContentText("Delete Flight with flight_ID: " + flight_id + "?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(type -> {
            if (type == ButtonType.YES) {
                Boolean result = deleter.deleteFlight(flight_id);
                flightDeleteBtn.setDisable(true);
                try {
                    setFlightSingleRecord(null);
                    updateFlightTable();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }


    /**
     * handleFlightDeleteEntry
     *
     * Handles the delete flight entry by starting delete flight window
     * @param actionEvent
     * @throws SQLException
     */
    @FXML
    public void handleFlightDeleteEntry(ActionEvent actionEvent) throws SQLException {
        // Fetch the selected row
        ConcreteDeleteData deleter = new ConcreteDeleteData();
        FlightEntryModel selectedForDeletion = (FlightEntryModel) flightSingleRecordTableView.getSelectionModel().getSelectedItem();
        if (selectedForDeletion != null && selectedForDeletion.getLocationType().equals("APT")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Flight Entry");
            alert.setContentText("Cannot delete a flight entry that represents an airport");
            alert.showAndWait();
        } else if (selectedForDeletion != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Flight Entry");
            alert.setContentText("Confirm Deletion of flight_entry with id: " + selectedForDeletion.getID() + ".");
            Optional<ButtonType> answer = alert.showAndWait();
            if (answer.get() == ButtonType.OK && !selectedForDeletion.getLocationType().equals("APT")) {
                // Confirmed deletion option
                deleter.deleteFlightEntry(selectedForDeletion.getID());
                updateFlightTable();
                flightSingleRecordTableView.getItems().remove(selectedForDeletion);
            }
        }
    }

    /**
     * handleFlightEditOption
     *
     * Handles the flight edit option by starting the flight edit window
     * @param actionEvent
     * @throws SQLException
     */
    @FXML
    public void handleFlightEditOption(ActionEvent actionEvent) throws SQLException {
        // Fetch the selected row
        FlightEntryModel selectedForEdit = (FlightEntryModel) flightSingleRecordTableView.getSelectionModel().getSelectedItem();
        if (selectedForEdit != null) {
            try {
                FXMLLoader loader = new FXMLLoader(App.class.getResource("edit_flight_entry.fxml"));
                Parent parent = loader.load();
                EditFlightController controller = (EditFlightController) loader.getController();
                controller.inflateUI(selectedForEdit);
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setTitle("Edit flight Entry");
                stage.setScene(new Scene(parent));
                stage.show();

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        updateAirportTable();
    }
}
