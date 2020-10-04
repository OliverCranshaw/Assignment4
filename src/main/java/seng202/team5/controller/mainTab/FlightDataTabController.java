package seng202.team5.controller.mainTab;

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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import seng202.team5.App;
import seng202.team5.controller.EditFlightController;
import seng202.team5.controller.HelpHandler;
import seng202.team5.controller.MainMenuController;
import seng202.team5.controller.uploadData.BaseUploadMenuController;
import seng202.team5.data.ConcreteDeleteData;
import seng202.team5.data.DataExporter;
import seng202.team5.map.Bounds;
import seng202.team5.map.Coord;
import seng202.team5.map.MapView;
import seng202.team5.map.MarkerIcon;
import seng202.team5.model.FlightEntryModel;
import seng202.team5.model.FlightModel;
import seng202.team5.service.AirportService;
import seng202.team5.service.FlightService;
import seng202.team5.table.FlightTable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class FlightDataTabController implements Initializable {

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
    private Button flightDeleteBtn;

    @FXML
    private Button flightDownloadBtn;

    @FXML
    private MapView flightMapView;

    private FlightTable flightTable;

    private ObservableList<FlightModel> flightModels;
    private ObservableList<FlightEntryModel> flightEntries;

    private int flightMapPath = -1;
    private int flightMapMarker = -1;

    private DataExporter dataExporter;
    private AirportService airportService;
    private FlightService flightService;
    private MainMenuController mainMenuController = new MainMenuController();

    /**
     * Initializer for FlightDataTabController
     * Sets up all tables, buttons, listeners, services, etc
     * @param url URL.
     * @param resourceBundle ResourceBundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Setting the cell value factories for the flight table
        flightIdCol.setCellValueFactory(new PropertyValueFactory<>("FlightId"));
        flightSrcLocationCol.setCellValueFactory(new PropertyValueFactory<>("SourceLocation"));
        flightSrcAirportCol.setCellValueFactory(new PropertyValueFactory<>("SourceAirport"));
        flightDstLocationCol.setCellValueFactory(new PropertyValueFactory<>("DestinationLocation"));
        flightDstAirportCol.setCellValueFactory(new PropertyValueFactory<>("DestinationAirport"));


        // Setting the cell value factories for the flight entry table
        flightDbID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        flightID.setCellValueFactory(new PropertyValueFactory<>("FlightID"));
        flightLocationType.setCellValueFactory(new PropertyValueFactory<>("LocationType"));
        flightLocation.setCellValueFactory(new PropertyValueFactory<>("Location"));
        flightAltitude.setCellValueFactory(new PropertyValueFactory<>("Altitude"));
        flightLatitude.setCellValueFactory(new PropertyValueFactory<>("Latitude"));
        flightLongitude.setCellValueFactory(new PropertyValueFactory<>("Longitude"));


        // Adding Listeners to flight tables so that the selected Items can be displayed in the single record viewer
        flightTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                FlightModel selected = (FlightModel) newSelection;
                try {
                    setFlightSingleRecord(selected);
                    flightDeleteBtn.setDisable(false);
                    flightDownloadBtn.setDisable(false);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        flightSingleRecordTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            onFlightEntrySelected((FlightEntryModel) newSelection);
        });


        try {
            setFlightSingleRecord(null);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        dataExporter = new DataExporter();


        // Initializing the required services and tables for the flight tab
        airportService = new AirportService();
        flightService = new FlightService();
        flightTable = new FlightTable(flightService.getData(null, null));


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
        // Setting all modify buttons to invisible
        flightDeleteBtn.setDisable(true);
        flightDownloadBtn.setDisable(true);
    }

    /**
     * Sets the flight single record table to contain the relevant entries.
     * from the given flight model.
     *
     * @param flightModel - flight model used to populate table.
     *
     * @throws SQLException occurs when any interactions with the ResultSet fail.
     */
    private void setFlightSingleRecord(FlightModel flightModel) throws SQLException {
        if (flightModel == null) {
            flightSingleRecordTableView.getItems().clear();
        } else {
            flightEntries = FXCollections.observableArrayList();
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
                flightEntries.add(newEntry);

                coordinates.add(new Coord(latitude, longitude));
            }
            flightSingleRecordTableView.setItems(flightEntries);

            if (flightMapPath != -1) {
                flightMapView.removePath(flightMapPath);
                flightMapPath = -1;
            }
            if (flightMapMarker != -1) {
                flightMapView.removeMarker(flightMapMarker);
                flightMapMarker = -1;
            }
            if (coordinates.size() >= 2) {
                flightMapPath = flightMapView.addPath(coordinates, MainMenuController.DEFAULT_ROUTE_SYMBOLS, null, MainMenuController.DEFAULT_STROKE_WEIGHT);
                flightMapView.fitBounds(Bounds.fromCoordinateList(coordinates), 0.0);
            }
        }
    }

    /**
     * Called when the currently selected flight entry changes.
     */
    private void onFlightEntrySelected(FlightEntryModel flightEntryModel) {
        if (flightMapMarker != -1) {
            flightMapView.removeMarker(flightMapMarker);
            flightMapMarker = -1;
        }
        if (flightEntryModel != null) {
            Coord coord = new Coord(flightEntryModel.getLatitude(), flightEntryModel.getLongitude());
            flightMapMarker = flightMapView.addMarker(coord, null, MarkerIcon.PLANE_ICON);
        }
    }

    /**
     * Starts the upload flight window.
     *
     * @param event user has clicked on the upload flight button.
     *
     * @throws IOException occurs when there are any errors with JavaFX.
     */
    @FXML
    public void onUploadFlightPressed(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(App.class.getResource("uploadData/upload_flight.fxml"));
        Scene scene = new Scene(loader.load());

        stage.setScene(scene);
        stage.setTitle("Upload Flight");
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.show();

        BaseUploadMenuController controller = loader.getController();
        controller.onShown(scene);

    }

    /**
     * Starts the download flights window.
     *
     * @param event user has clicked on the download button in the flight tab.
     */
    @FXML
    public void onDownloadFlightsPressed(ActionEvent event) {
        File file = mainMenuController.selectFolder(event);

        if (file != null) {
            dataExporter.exportFlights(file);
        }
    }


    /**
     * Starts the download flight window.
     *
     * @param event user has clicked on the download flight button.
     */
    @FXML
    public void onDownloadFlightPressed(ActionEvent event) {
        if (flightTableView.getSelectionModel().getSelectedItem() != null) {
            File file = mainMenuController.selectFolder(event);

            FlightModel flightModel = (FlightModel)flightTableView.getSelectionModel().getSelectedItem();

            int id = flightModel.getFlightId();

            if (file != null) {
                dataExporter.exportFlight(id, file);
            }
        }
    }

    /**
     * Populates the given flight table witht the given data.
     *
     * @param tableView - TableView.
     * @param data - ArrayList of ArrayList of data.
     *
     * @throws SQLException occurs when any interactions with the ResultSet fail.
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
     * Updates the flight table with data from the database.
     *
     * @throws SQLException occurs when any interactions with the ResultSet returned by the getData function fail.
     */
    public void updateFlightTable() throws SQLException {
        flightTable = new FlightTable(flightService.getData(null, null));
        flightTable.createTable();
        populateFlightTable(flightTableView, flightTable.getData());
    }

    /**
     * Updates the flight table from a button press.
     *
     * @throws SQLException occurs when any interactions with the ResultSet returned by the getData function in updateFlightTable fail.
     */
    @FXML
    public void onFlightRefreshButton() throws  SQLException {
        updateFlightTable();
    }

    /**
     * Deletes flight that is currently being modified.
     */
    @FXML
    public void onFlightDeleteBtnPressed() {
        ConcreteDeleteData deleter = new ConcreteDeleteData();
        int flight_id = ((FlightEntryModel) flightSingleRecordTableView.getItems().get(0)).getFlightID();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Flight");
        alert.setContentText("Delete Flight with flight_ID: " + flight_id + "?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(type -> {
            if (type == ButtonType.YES) {
                deleter.deleteFlight(flight_id);
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
     * Handles the delete flight entry by starting delete flight window.
     *
     * @throws SQLException occurs when any interactions with the ResultSet fail in the updateFlightTable function.
     */
    @FXML
    public void handleFlightDeleteEntry() throws SQLException {
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
     * Handles the flight edit option by starting the flight edit window.
     *
     * @throws SQLException occurs when any interactions with the ResultSet fail in the updateFlightTable function.
     */
    @FXML
    public void handleFlightEditOption() throws SQLException {
        // Fetch the selected row
        FlightEntryModel selectedForEdit = (FlightEntryModel) flightSingleRecordTableView.getSelectionModel().getSelectedItem();
        if (selectedForEdit != null) {
            try {
                FXMLLoader loader = new FXMLLoader(App.class.getResource("edit_flight_entry.fxml"));
                Parent parent = loader.load();
                EditFlightController controller = loader.getController();
                controller.inflateUI(selectedForEdit);
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setTitle("Edit flight Entry");
                stage.setScene(new Scene(parent));
                stage.show();

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        updateFlightTable();
    }

    /**
     * Handles the requesting of help by using the HelpHandler to call startHelp.
     *
     * @param event user has clicked on the help button.
     */
    public void onHelp(ActionEvent event) {
        System.out.println("Help requested: " + event);

        Node e = (Node) event.getSource();
        Scene scene = e.getScene();
        HelpHandler.startHelp(scene);
    }
}
