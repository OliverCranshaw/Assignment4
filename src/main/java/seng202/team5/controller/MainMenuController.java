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
import seng202.team5.App;
import seng202.team5.data.ConcreteUpdateData;
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
    private Tab tableRecordsTab;

    @FXML
    private Tab generalTab;

    @FXML
    private Tab airlineDataTab;

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
    private Button modifyFlightBtn;

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
    private TableView searchFlightSingleRecordTableView;



    @FXML
    private Label airportInvalidFormatLbl;

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
    private ObservableList<FlightEntry> flightEntries;



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


        modifyAirportBtn.setDisable(true);
        modifyAirlineBtn.setDisable(true);
        modifyRouteBtn.setDisable(true);
        modifyFlightBtn.setDisable(true);

        // Adding Listeners to all four tables so that the selected Items can be displayed in the single record viewer
        airportTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                AirportModel selected = (AirportModel) newSelection;
                try {
                    setAirportSingleRecord(selected);
                    setAirportElementsEditable(false);
                    modifyAirportBtn.setDisable(false);
                    airportInvalidFormatLbl.setVisible(false);
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




        dataExporter = new DataExporter();

        airlineService = new AirlineService();
        airportService = new AirportService();
        routeService = new RouteService();
        flightService = new FlightService();

        airlineTable = new AirlineTable(airlineService.getData(null, null, null));
        airportTable = new AirportTable(airportService.getData(null, null, null));
        routeTable = new RouteTable(routeService.getData(null, null, -1, null));
        flightTable = new FlightTable(flightService.getData(null, null));

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

        airportSaveBtn.setVisible(false);
        airportCancelBtn.setVisible(false);
        airlineSaveBtn.setVisible(false);
        airlineCancelBtn.setVisible(false);
        routeSaveBtn.setVisible(false);
        routeCancelBtn.setVisible(false);

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
        setFieldsEmpty(elementsVisible);
        setLabelsEmpty(lblElementsVisible, false);

    }

    private void setFlightSingleRecord(FlightModel flightModel) throws SQLException {
        if (flightModel == null) {
            flightSingleRecordTableView.getItems().removeAll();
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
                FlightEntry newEntry = new FlightEntry(id, flightId, locationType, location, altitude, latitude, longitude);
                flightEntries.add(newEntry);
            }
            flightSingleRecordTableView.setItems(flightEntries);
        }
    }

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

    private void setSearchFlightSingleRecord(FlightModel flightModel) throws SQLException {
        if (flightModel == null) {
            searchFlightSingleRecordTableView.getItems().removeAll();
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
                FlightEntry newEntry = new FlightEntry(id, flightId, locationType, location, altitude, latitude, longitude);
                flightEntries.add(newEntry);
            }
            searchFlightSingleRecordTableView.setItems(flightEntries);
        }
    }

    private void setSearchRouteSingleRecord(RouteModel routeModel) throws SQLException {
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


    private void setFieldsEmpty(ArrayList<TextField> elementsVisible) {
        for (TextField field : elementsVisible) {
            if (field != null) {
                field.setVisible(false);
                field.setEditable(false);
                field.setStyle("-fx-border-color: #000000;");
            }
        }
    }

    private void setLabelsEmpty(ArrayList<Label> elementsVisible, Boolean bool) {
        for (Label lbl : elementsVisible) {
            if (lbl != null) {
                lbl.setVisible(bool);
            }
        }
    }

    private void setLabels(ResultSet elementData, ArrayList<TextField> elementsVisible) throws SQLException {
        for (TextField field : elementsVisible) {
            field.setVisible(true);
            field.setEditable(false);
        }
        for (int i = 0; i < elementsVisible.size(); i++) {
            elementsVisible.get(i).setText(elementData.getString(i+1));
        }
    }

    public void onHelp(ActionEvent event) {
        System.out.println("Help requested: " + event);

        Scene scene = mainTabs.getScene();
        HelpHandler.startHelp(scene);
    }

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

    @FXML
    public void onViewMorePressed() {
        mainTabs.getSelectionModel().select(tableRecordsTab);
    }

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

    @FXML
    public void onDownloadAirportDataPressed(ActionEvent event) throws IOException {
        File file = selectFolder(event);

        if (file != null) {
            dataExporter.exportAirports(file);
        }
    }

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

    @FXML
    public void onDownloadAirlineDataPressed(ActionEvent event) throws IOException {
        File file = selectFolder(event);

        if (file != null) {
            dataExporter.exportAirlines(file);
        }
    }

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

    @FXML
    public void onDownloadFlightsPressed(ActionEvent event) throws IOException {
        File file = selectFolder(event);

        if (file != null) {
            dataExporter.exportFlights(file);
        }
    }

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

    @FXML
    public void onDownloadRouteDataPressed(ActionEvent event) throws IOException {
        File file = selectFolder(event);

        if (file != null) {
            dataExporter.exportRoutes(file);
        }
    }

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

        firstSearchType.setText("Airline:");
        secondSearchType.setText("Airport:");
        thirdSearchType.setVisible(false);
        fourthSearchType.setVisible(false);

        thirdSearchEntry.setVisible(false);
        thirdSearchEntry.setDisable(true);
        fourthSearchEntry.setVisible(false);
        fourthSearchEntry.setDisable(true);

        errorMessage.setText("");

        setSearchTableFlights();



    }
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

    @FXML
    public void onSearchPressed() throws SQLException {

        ArrayList<Object> fields = new ArrayList<>();
        Search searchInstance = new Search();
        ResultSet result;


        if (flightsRadioButton.isSelected()) {


            fields.add(firstSearchEntry.getText().isBlank() ? null : firstSearchEntry.getText());
            fields.add(secondSearchEntry.getText().isBlank() ? null : secondSearchEntry.getText());


            System.out.printf("Location Type: %s, Location: %s\n%n", fields.get(0), fields.get(1));

            searchInstance.setSearchData(fields);
            result = searchInstance.searchFlight();

            if (result == null) {
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

            System.out.printf("Name: %s, City: %s, Country: %s\n%n", fields.get(0), fields.get(1), fields.get(2));

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


            System.out.printf("Name: %s, Country: %s, Callsign: %s\n%n", fields.get(0), fields.get(1), fields.get(2));

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

                System.out.printf("Source Airport: %s, Dest. Airpot: %s, Num. Stops: %s, Equipment: %s\n%n", fields.get(0), fields.get(1), fields.get(2), fields.get(3));

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

    @FXML
    public void onAirportApplyFilterButton(ActionEvent actionEvent) {
        airportTable.FilterTable(null);
        String countryText = airportCountryField.getText();
        ArrayList newList = convertCSStringToArrayList(countryText);
        airportTable.FilterTable(newList);
        populateAirportTable(airportTableView, airportTable.getData());
    }

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


    public void updateAirportTable() throws SQLException {
        airportTable = new AirportTable(airportService.getData(null, null, null));
        airportTable.createTable();
        populateAirportTable(airportTableView, airportTable.getData());
    }


    public void updateAirlineTable() throws SQLException {
        airlineTable = new AirlineTable(airlineService.getData(null, null, null));
        airlineTable.createTable();
        populateAirlineTable(rawAirlineTable, airlineTable.getData());
    }

    public void updateRouteTable() throws SQLException {
        routeTable = new RouteTable(routeService.getData(null, null, -1, null));
        routeTable.createTable();
        populateRouteTable(routeTableView, routeTable.getData());
    }

    public void updateFlightTable() throws SQLException {
        flightTable = new FlightTable(flightService.getData(null, null));
        flightTable.createTable();
        populateFlightTable(flightTableView, flightTable.getData());
    }




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


    public void setSearchTableAirports() {
        searchTableView.getItems().clear();
        searchTableView.getColumns().clear();
        TableColumn<AirportModel, String> airportNameCol = new TableColumn<>("Name");
        TableColumn<AirportModel, String> airportCityCol = new TableColumn<>("City");
        TableColumn<AirportModel, String> airportCountryCol = new TableColumn<>("Country");

        airportNameCol.setCellValueFactory(new PropertyValueFactory<>("AirportName"));
        airportCityCol.setCellValueFactory(new PropertyValueFactory<>("AirportCity"));
        airportCountryCol.setCellValueFactory(new PropertyValueFactory<>("AirportCountry"));

        searchTableView.getColumns().addAll(airportNameCol, airportCityCol, airportCountryCol);
    }

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


    @FXML
    public void onAirportRefreshButton(ActionEvent event) throws SQLException {
        updateAirportTable();
    }

    @FXML
    public void onAirlineRefreshButton(ActionEvent event) throws  SQLException {
        updateAirlineTable();
    }

    @FXML
    public void onRouteRefreshButton(ActionEvent event) throws  SQLException {
        updateRouteTable();
    }

    @FXML
    public void onFlightRefreshButton(ActionEvent event) throws  SQLException {
        updateFlightTable();
    }



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

    public ArrayList<String> convertToArrayList(String[] list) {
        ArrayList<String> result = new ArrayList<>();
        for (String component : list) {
            result.add(component);
        }
        return result;
    }

    public void onModifyAirlineBtnPressed(ActionEvent actionEvent) {
        setAirlineElementsEditable(true);
        airlineSaveBtn.setVisible(true);
        airlineCancelBtn.setVisible(true);
    }

    public void onModifyRouteBtnPressed(ActionEvent actionEvent) {
        setRouteElementsEditable(true);
        routeSaveBtn.setVisible(true);
        routeCancelBtn.setVisible(true);
    }

    public void onModifyAirportBtnPressed(ActionEvent actionEvent) {
        airportInvalidFormatLbl.setVisible(false);
        setAirportElementsEditable(true);
        airportSaveBtn.setVisible(true);
        airportCancelBtn.setVisible(true);

    }

    public void onModifyFlightBtnPressed(ActionEvent actionEvent) {

    }

    public void setAirportElementsEditable(Boolean bool) {
        List<TextField> elements = Arrays.asList(airportName, airportCity, airportCountry, airportIATA, airportICAO, airportLatitude, airportLongitude,
                airportAltitude, airportTimezone, airportDST, airportTZ);
        ArrayList<TextField> elementsEditable = new ArrayList<TextField>(elements);
        setElementsEditable(elementsEditable, bool);
    }

    public void setAirlineElementsEditable(Boolean bool) {
        List<TextField> elements = Arrays.asList(airlineName, airlineName, airlineAlias, airlineIATA, airlineICAO, airlineCallsign, airlineCountry,
                airlineActive);
        ArrayList<TextField> elementsVisible = new ArrayList<TextField>(elements);
        setElementsEditable(elementsVisible, bool);
    }


    public void setRouteElementsEditable(Boolean bool) {
        List<TextField> elements = Arrays.asList(routeAirline, routeDepAirport, routeDesAirport,
                routeCodeshare, routeStops, routeEquip);
        ArrayList<TextField> elementsVisible = new ArrayList<TextField>(elements);
        setElementsEditable(elementsVisible, bool);
    }


    public void setElementsEditable(ArrayList<TextField> elementsEditable, Boolean bool) {
        for (TextField field : elementsEditable) {
            field.setEditable(bool);
        }
    }

    @FXML
    public void onAirportSaveBtnPressed(ActionEvent event) throws SQLException {
        airportInvalidFormatLbl.setVisible(false);
        setAirportUpdateColour(null);
        Integer id = Integer.parseInt(airportID.getText());
        List<Object> elementList2 = null;
        try {
            elementList2 = Arrays.asList(airportName.getText(), airportCity.getText(), airportCountry.getText(), airportIATA.getText(), airportICAO.getText(),
                    Double.parseDouble(airportLatitude.getText()), Double.parseDouble(airportLongitude.getText()), Integer.parseInt(airportAltitude.getText()),
                    Float.parseFloat(airportTimezone.getText()), airportDST.getText(), airportTZ.getText());
        } catch (NumberFormatException e) { }
        if (elementList2 != null) {
            ArrayList<Object> newElements = new ArrayList<>(elementList2);
            ConcreteUpdateData updater = new ConcreteUpdateData();
            int result = updater.updateAirport(id, (String) newElements.get(0), (String) newElements.get(1), (String) newElements.get(2), (String) newElements.get(3),
                    (String) newElements.get(4), (Double) newElements.get(5), (Double) newElements.get(6), (newElements.get(7) == null) ? -1 : (Integer) newElements.get(7), (Float) newElements.get(8),
                    (String) newElements.get(9), (String) newElements.get(10));
            if (result > 0) {
                updateAirportTable();
            } else {
                System.out.println(result);
                setAirportUpdateColour(abs(result) - 2);
            }
        } else {
            airportInvalidFormatLbl.setVisible(true);
        }
        setAirportElementsEditable(false);
        airportSaveBtn.setVisible(false);
        airportCancelBtn.setVisible(false);
    }

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

        } else {
            System.out.println(result);
            setAirlineUpdateColour(abs(result) - 2);
        }
        setAirlineElementsEditable(false);
        airlineSaveBtn.setVisible(false);
        airlineCancelBtn.setVisible(false);
    }

    @FXML
    public void onRouteSaveBtnPressed(ActionEvent event) throws SQLException {
        setRouteUpdateColour(null);
        Integer id = Integer.parseInt(routeID.getText());
        List<Object> elementsList2 = null;
        try {
            elementsList2 = Arrays.asList(routeAirline.getText(), routeDepAirport.getText(), routeDesAirport.getText(), routeCodeshare.getText(),
                    Integer.parseInt(routeStops.getText()), routeEquip.getText());
        } catch (NumberFormatException e) { }
        if (elementsList2 != null) {
            ArrayList<Object> newElements = new ArrayList<>(elementsList2);

            ConcreteUpdateData updater = new ConcreteUpdateData();

            int result = updater.updateRoute(id, (String) newElements.get(0), (String) newElements.get(1), (String) newElements.get(2),
                    (String) newElements.get(3), (newElements.get(4) == null) ? -1 : (Integer) newElements.get(4), (String) newElements.get(5));
            System.out.println(result);
            System.out.println(newElements);
            if (result > 0) {
                updateRouteTable();
                Integer routeAirlineID1 = airlineService.getData((String) newElements.get(0)).getInt(1);
                Integer routeSrcAirportID1 = airportService.getData((String) newElements.get(1)).getInt(1);
                Integer routeDstAirportID1 = airportService.getData((String) newElements.get(2)).getInt(1);
                routeAirlineID.setText(String.valueOf(routeAirlineID1));
                routeDepAirportID.setText(String.valueOf(routeSrcAirportID1));
                routeDesAirportID.setText(String.valueOf(routeDstAirportID1));
            } else {
                System.out.println(result);
                setRouteUpdateColour(abs(result) - 2);
            }
        } else {
            System.out.println("Error formatting");
        }
        setRouteElementsEditable(false);
        routeSaveBtn.setVisible(false);
        routeCancelBtn.setVisible(false);
    }

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


    public void setAirlineUpdateColour(Integer index) {
        List<TextField> elements = Arrays.asList(airlineName, airlineName, airlineAlias, airlineIATA, airlineICAO, airlineCallsign, airlineCountry,
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


    @FXML
    public void onAirportCancelBtnPressed(ActionEvent event) throws SQLException {
        airportInvalidFormatLbl.setVisible(false);
        setAirportElementsEditable(false);
        setAirportSingleRecord((AirportModel)airportTableView.getSelectionModel().getSelectedItem());
        airportCancelBtn.setVisible(false);
        airportSaveBtn.setVisible(false);
    }

    @FXML
    public void onAirlineCancelBtnPressed(ActionEvent event) throws SQLException {
        setAirlineElementsEditable(false);
        setAirlineSingleRecord((AirlineModel)rawAirlineTable.getSelectionModel().getSelectedItem());
        airlineCancelBtn.setVisible(false);
        airlineSaveBtn.setVisible(false);
    }

    public void onRouteCancelBtnPressed(ActionEvent actionEvent) throws SQLException {
        setRouteElementsEditable(false);
        setRouteSingleRecord((RouteModel)routeTableView.getSelectionModel().getSelectedItem());
        routeCancelBtn.setVisible(false);
        routeSaveBtn.setVisible(false);
    }
}
