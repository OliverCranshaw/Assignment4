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
import seng202.team5.Search;
import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.RouteService;
import seng202.team5.table.AirlineTable;
import seng202.team5.table.AirportTable;
import seng202.team5.table.RouteTable;

import java.io.IOException;
import java.net.URL;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

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
    private Label errorMessage;


    private AirlineService airlineService;
    private AirportService airportService;
    private RouteService routeService;
    private AirlineTable airlineTable;
    private AirportTable airportTable;
    private RouteTable routeTable;


    private ObservableList<AirlineModel> airlineModels;
    private ObservableList<AirportModel> airportModels;
    private ObservableList<RouteModel> routeModels;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        airlineNameCol.setCellValueFactory(new PropertyValueFactory<>("AirlineName"));
        airlineAliasCol.setCellValueFactory(new PropertyValueFactory<>("AirlineAlias"));
        airlineCountryCol.setCellValueFactory(new PropertyValueFactory<>("AirlineCountry"));
        airlineActiveCol.setCellValueFactory(new PropertyValueFactory<>("AirlineActive"));

        airlineActiveDropdown.getItems().removeAll(airlineActiveDropdown.getItems());
        airlineActiveDropdown.getItems().addAll("", "Yes", "No");

        airportNameCol.setCellValueFactory(new PropertyValueFactory<>("AirportName"));
        airportCityCol.setCellValueFactory(new PropertyValueFactory<>("AirportCity"));
        airportCountryCol.setCellValueFactory(new PropertyValueFactory<>("AirportCountry"));

        routeAirlineCol.setCellValueFactory(new PropertyValueFactory<>("RouteAirline"));
        routeSrcAirportCol.setCellValueFactory(new PropertyValueFactory<>("RouteSrcAirport"));
        routeDestAirportCol.setCellValueFactory(new PropertyValueFactory<>("RouteDstAirport"));
        routeStopsCol.setCellValueFactory(new PropertyValueFactory<>("RouteStops"));
        routeEquipmentCol.setCellValueFactory(new PropertyValueFactory<>("RouteEquipment"));


        airlineService = new AirlineService();
        airportService = new AirportService();
        routeService = new RouteService();

        airlineTable = new AirlineTable(airlineService.getAirlines(null, null, null));
        airportTable = new AirportTable(airportService.getAirports(null, null, null));
        routeTable = new RouteTable(routeService.getRoutes(null, null, -1, null));

        try {
            airlineTable.createTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        populateAirlineTable(airlineTable.getData());

        System.out.println();
        System.out.println(airlineTable.getData().get(0));
        System.out.println();

        try {
            airportTable.createTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        populateAirportTable(airportTable.getData());
        /*
        try {
            routeTable.createTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        */

        //populateRouteTable(routeTable.getData());

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
            } catch (NumberFormatException e) {
                errorMessage.setText("Invalid entry for number of stops. (Must be an integer)");
            }
        }



    }


    private void populateAirlineTable(ArrayList<ArrayList<Object>> data) {

        System.out.println("POPULATING>>>");
        System.out.println("POP" + data.get(0));

        ArrayList<AirlineModel> list = new ArrayList<>();
        for (ArrayList<Object> datum : data) {
            String name = (String) datum.get(1);
            String alias = (String) datum.get(2);
            String country = (String) datum.get(6);
            String active = (String) datum.get(7);
            list.add(new AirlineModel(name, alias, country, active));
        }
        airlineModels = FXCollections.observableArrayList(list);
        rawAirlineTable.setItems(airlineModels);
    }


    private void populateAirportTable(ArrayList<ArrayList<Object>> data) {
        ArrayList<AirportModel> list = new ArrayList<>();
        for (ArrayList<Object> datum : data) {
            String name = (String) datum.get(1);
            String city = (String) datum.get(2);
            String country = (String) datum.get(3);
            list.add(new AirportModel(name, city, country));
        }
        airportModels = FXCollections.observableArrayList(list);
        airportTableView.setItems(airportModels);
    }


    private void populateRouteTable(ArrayList<ArrayList<Object>> data) {
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
        routeTableView.setItems(routeModels);
    }


    @FXML
    public void onAirlineApplyFilterButton(ActionEvent actionEvent) {

        System.out.println();
        System.out.println("Here: " + airlineTable.getData().get(0));
        System.out.println();

//        airlineTable.FilterTable(null, null);
//        String countryText = countryAirlineField.getText();
//        String activeText = (String) airlineActiveDropdown.getValue();
//        if (activeText == null || activeText.equals("")) {
//            activeText = null;
//        } else {
//            activeText = (activeText == "Yes") ? "Y" : "N";
//        }
//        ArrayList<String> newList = convertCSStringToArrayList(countryText);
//        airlineTable.FilterTable(newList, activeText);

        populateAirlineTable(airlineTable.getData());

    }

    @FXML
    public void onAirportApplyFilterButton(ActionEvent actionEvent) {
        airportTable.FilterTable(null);
        String countryText = airportCountryField.getText();
        ArrayList newList = convertCSStringToArrayList(countryText);
        airportTable.FilterTable(newList);
        populateAirportTable(airportTable.getData());
    }

    @FXML
    public void onRouteApplyFilterButton(ActionEvent actionEvent) {
        routeTable.FilterTable(null, null, null, null);
        String srcAirportText = routeSourceAirportField.getText();
        String dstAirportText = routeDestAirportField.getText();
        String equipmentText = routeEquipmentField.getText();
        srcAirportText = srcAirportText.trim();
        dstAirportText = dstAirportText.trim();
        String directText = (String) routeStopsComboBox.getValue();
        if (directText == null || directText.equals("")) {
            directText = null;
        } else {
            directText = (directText.equals("direct")) ? "direct" : "not direct";
        }
        ArrayList<String> equipmentList = convertCSStringToArrayList(equipmentText);
        routeTable.FilterTable(srcAirportText, dstAirportText, directText, equipmentList);
    }

    public void onAddFlightPressed(ActionEvent actionEvent) {
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
