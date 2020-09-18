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
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.team5.App;
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

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

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
    private Label airportID;

    @FXML
    private Label lblAirportName;

    @FXML
    private Label airportName;

    @FXML
    private Label lblAirportCity;

    @FXML
    private Label airportCity;

    @FXML
    private Label lblAirportCountry;

    @FXML
    private Label airportCountry;

    @FXML
    private Label lblAirportIATA;

    @FXML
    private Label airportIATA;

    @FXML
    private Label lblAirportICAO;

    @FXML
    private Label airportICAO;

    @FXML
    private Label lblAirportLatitude;

    @FXML
    private Label airportLatitude;

    @FXML
    private Label lblAirportLongitude;

    @FXML
    private Label airportLongitude;

    @FXML
    private Label lblAirportAltitude;

    @FXML
    private Label airportAltitude;

    @FXML
    private Label lblAirportTimezone;

    @FXML
    private Label airportTimezone;

    @FXML
    private Label lblAirportDST;

    @FXML
    private Label airportDST;

    @FXML
    private Label lblAirportTZ;

    @FXML
    private Label airportTZ;

    @FXML
    private Label lblAirlineID;

    @FXML
    private Label airlineID;

    @FXML
    private Label lblAirlineName;

    @FXML
    private Label airlineName;

    @FXML
    private Label lblAirlineAlias;

    @FXML
    private Label airlineAlias;

    @FXML
    private Label lblAirlineIATA;

    @FXML
    private Label airlineIATA;

    @FXML
    private Label lblAirlineICAO;

    @FXML
    private Label airlineICAO;

    @FXML
    private Label lblAirlineCallsign;

    @FXML
    private Label airlineCallsign;

    @FXML
    private Label lblAirlineCountry;

    @FXML
    private Label airlineCountry;

    @FXML
    private Label lblAirlineActive;

    @FXML
    private Label airlineActive;

    @FXML
    private Label lblRouteID;

    @FXML
    private Label routeID;

    @FXML
    private Label lblRouteAirline;

    @FXML
    private Label routeAirline;

    @FXML
    private Label lblRouteAirlineID;

    @FXML
    private Label routeAirlineID;

    @FXML
    private Label lblRouteDepAirport;

    @FXML
    private Label routeDepAirport;

    @FXML
    private Label lblRouteDepAirportID;

    @FXML
    private Label routeDepAirportID;

    @FXML
    private Label lblRouteDesAirport;

    @FXML
    private Label routeDesAirport;

    @FXML
    private Label lblRouteDesAirportID;

    @FXML
    private Label routeDesAirportID;

    @FXML
    private Label lblRouteCodeshare;

    @FXML
    private Label routeCodeshare;

    @FXML
    private Label lblRouteStops;

    @FXML
    private Label routeStops;

    @FXML
    private Label lblRouteEquip;

    @FXML
    private Label routeEquip;

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

        // Adding Listeners to all four tables so that the selected Items can be displayed in the single record viewer
        airportTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                AirportModel selected = (AirportModel) newSelection;
                System.out.println(selected.getId());
                try {
                    setAirportSingleRecord(selected);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        rawAirlineTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                AirlineModel selected = (AirlineModel) newSelection;
                System.out.println(selected.getId());
                try {
                    setAirlineSingleRecord(selected);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        flightTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                FlightModel selected = (FlightModel) newSelection;
                System.out.println(selected.getIdRange());
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
                System.out.println(selected.getRouteId());
                try {
                    setRouteSingleRecord(selected);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        try {
            setAirportSingleRecord(null);
            setAirlineSingleRecord(null);
            setRouteSingleRecord(null);
            setFlightSingleRecord(null);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }




        dataExporter = new DataExporter();

        airlineService = new AirlineService();
        airportService = new AirportService();
        routeService = new RouteService();
        flightService = new FlightService();

        airlineTable = new AirlineTable(airlineService.getAirlines(null, null, null));
        airportTable = new AirportTable(airportService.getAirports(null, null, null));
        routeTable = new RouteTable(routeService.getRoutes(null, null, -1, null));
        flightTable = new FlightTable(flightService.getFlights(null, null));

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


    }

    private void setFlightSingleRecord(FlightModel flightModel) throws SQLException {
        if (flightModel == null) {
            flightSingleRecordTableView.getItems().removeAll();
        } else {
            flightEntries = FXCollections.observableArrayList();
            ArrayList<Integer> idRange = flightModel.getIdRange();
            Integer flightID = flightModel.getFlightId();
            ResultSet flightData = flightService.getFlight(flightID);
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
        List elements = Arrays.asList(routeID, routeAirline, routeAirlineID, routeDepAirport, routeDepAirportID, routeDesAirport, routeDesAirportID,
                routeCodeshare, routeStops, routeEquip, lblRouteID, lblRouteAirline, lblRouteAirlineID, lblRouteDepAirport, lblRouteDepAirportID,
                lblRouteDesAirport, lblRouteDesAirportID, lblRouteCodeshare, lblRouteStops, lblRouteEquip);
        ArrayList<Label> elementsVisible = new ArrayList<>(elements);
        if (routeModel == null) {
            setLabelsInvis(elementsVisible);
        } else {
            ResultSet routeData = routeService.getRoute(routeModel.getRouteId());
            setLabels(routeData, elementsVisible);
        }
    }

    private void setAirlineSingleRecord(AirlineModel airlineModel) throws SQLException {
        List elements = Arrays.asList(airlineID, airlineName, airlineAlias, airlineIATA, airlineICAO, airlineCallsign, airlineCountry, airlineActive,
                lblAirlineID, lblAirlineName, lblAirlineAlias, lblAirlineIATA, lblAirlineICAO, lblAirlineCallsign, lblAirlineCountry, lblAirlineActive);
        ArrayList<Label> elementsVisible = new ArrayList<>(elements);
        if (airlineModel == null) {
            setLabelsInvis(elementsVisible);
        } else {
            ResultSet airlineData = airlineService.getAirline(airlineModel.getId());
            setLabels(airlineData, elementsVisible);
        }
    }

    private void setAirportSingleRecord(AirportModel airportModel) throws SQLException {
        List elements = Arrays.asList(airportID, airportName, airportCity, airportCountry, airportIATA, airportICAO, airportLatitude, airportLongitude,
                airportAltitude, airportTimezone, airportDST, airportTZ, lblAirportID, lblAirportName, lblAirportCity, lblAirportCountry,
                lblAirportIATA, lblAirportICAO, lblAirportLatitude, lblAirportLongitude, lblAirportAltitude, lblAirportTimezone, lblAirportDST, lblAirportTZ);
        ArrayList<Label> elementsVisible = new ArrayList<>(elements);
        if (airportModel == null) {
            setLabelsInvis(elementsVisible);
        } else {
            ResultSet airportData = airportService.getAirport(airportModel.getId());
            setLabels(airportData, elementsVisible);
        }
    }

    private void setLabelsInvis(ArrayList<Label> elementsVisible) {
        for (Label lbl : elementsVisible) {
            if (lbl != null) {
                lbl.setVisible(false);
            }
        }
    }

    private void setLabels(ResultSet elementData, ArrayList<Label> elementsVisible) throws SQLException {
        for (Label lbl : elementsVisible) {
            lbl.setVisible(true);
        }
        for (int i = 0; i < elementsVisible.size() / 2; i++) {
            elementsVisible.get(i).setText(elementData.getString(i+1));
        }
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
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(App.class.getResource("download.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Download Airports");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.show();
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
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(App.class.getResource("download.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Download Airports");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.show();
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
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(App.class.getResource("download.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Download Airports");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.show();
    }

    @FXML
    public void onUploadFlightsDataPressed(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(App.class.getResource("upload_flight.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Upload Flights");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.show();
    }


    @FXML
    public void onFlightsRadioPressed() {

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
            ResultSet srcAirportSet = airportService.getAirport(srcAirportIata);
            String srcAirport = (srcAirportSet.next()) ? srcAirportSet.getString(2) : srcAirportIata;
            ResultSet dstAirportSet = airportService.getAirport(dstAirportIata);
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
            ResultSet srcAirport = airportService.getAirports(srcAirportText, null, null);
            if (srcAirport.next()) {
                srcIata = srcAirport.getString(5);
            } else {
                srcIata = "_!$";
            }
        }
        if (dstAirportText == null) {
            dstIata = null;
        } else {
            ResultSet dstAirport = airportService.getAirports(dstAirportText, null, null);
            if (dstAirport.next()) {
                dstIata = dstAirport.getString(5);
            } else {
                dstIata = "_N@";
            }
        }
        routeTable.FilterTable(srcIata, dstIata, directText, equipmentList);
        populateRouteTable(routeTableView, routeTable.getData());
    }

    public void onAddFlightPressed(ActionEvent actionEvent) {
    }

    public void updateAirportTable() throws SQLException {
        airportTable = new AirportTable(airportService.getAirports(null, null, null));
        airportTable.createTable();
        populateAirportTable(airportTableView, airportTable.getData());
    }


    public void updateAirlineTable() throws SQLException {
        airlineTable = new AirlineTable(airlineService.getAirlines(null, null, null));
        airlineTable.createTable();
        populateAirlineTable(rawAirlineTable, airlineTable.getData());
    }

    public void updateRouteTable() throws SQLException {
        routeTable = new RouteTable(routeService.getRoutes(null, null, -1, null));
        routeTable.createTable();
        populateRouteTable(routeTableView, routeTable.getData());
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

}
