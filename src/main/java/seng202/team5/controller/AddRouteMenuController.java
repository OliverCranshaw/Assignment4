package seng202.team5.controller;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Window;
import seng202.team5.data.AirlineData;
import seng202.team5.data.AirportData;
import seng202.team5.data.ConcreteAddData;
import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AddRouteMenuController {
    private AirlineService airlineService = new AirlineService();
    private AirportService airportService = new AirportService();

    public AddRouteMenuController() {}

    @FXML
    private ComboBox<String> airlineField;

    @FXML
    private ComboBox<String> sourceField;

    @FXML
    private ComboBox<String> destinationField;

    @FXML
    private CheckBox codeshareField;

    @FXML
    private TextField stopsField;

    @FXML
    private TextField equipmentField;

    @FXML
    private Text errorMessage;

    @FXML
    public void initialize() {
        updateAirlines();
        updateAirports();
    }

    @FXML
    public void onAddPressed() {

        ConcreteAddData concreteAddData = new ConcreteAddData();
        String codeShare = codeshareField.isSelected() ? "Y" : null;

        String airlineName = airlineField.getValue();
        String sourceName = sourceField.getValue();
        String destName = destinationField.getValue();

        String airlineIATA;
        String sourceIATA;
        String destIATA;

        try {
            {
                ResultSet resultSet = airlineService.getAirlines(airlineName, null, null);
                resultSet.next();
                airlineIATA = new AirlineData(resultSet).getIata();
            }
            {
                ResultSet resultSet = airportService.getAirports(sourceName, null, null);
                resultSet.next();
                sourceIATA = new AirportData(resultSet).getIata();
            }
            {
                ResultSet resultSet = airportService.getAirports(destName, null, null);
                resultSet.next();
                destIATA = new AirportData(resultSet).getIata();
            }
        } catch (SQLException e) {
            System.out.println("Failed to find corresponding Airport/Airline with the given names");
            return;
        }

        //System.out.println(String.join(",", airlineIATA, sourceIATA, destIATA));

        int outcome = concreteAddData.addRoute(airlineIATA, sourceIATA, destIATA, codeShare, stopsField.getText(), equipmentField.getText());
        setDefaults();
        if (outcome < 0) {
            if (outcome == -1) {
                System.out.println("Service Error");
                errorMessage.setText("Please ensure the input source and/or destination exist within the database");
                errorMessage.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 12));
                errorMessage.setFill(Color.RED);
                errorMessage.setVisible(true);
            } else if (outcome == -2) {
                airlineField.setStyle("-fx-border-color: #ff0000;");
            } else if (outcome == -3) {
                sourceField.setStyle("-fx-border-color: #ff0000;");
            } else if (outcome == -4) {
                destinationField.setStyle("-fx-border-color: #ff0000;");
            } else if (outcome == -5) {
                codeshareField.setStyle("-fx-border-color: #ff0000;");
            } else if (outcome == -6) {
                stopsField.setStyle("-fx-border-color: #ff0000;");
            } else if (outcome == -7) {
                equipmentField.setStyle("-fx-border-color: #ff0000;");
            }
        } else {
            setDefaults();
        }
    }


    public void setDefaults() {
        airlineField.setStyle("-fx-border-color: #000000;");
        sourceField.setStyle("-fx-border-color: #000000;");
        destinationField.setStyle("-fx-border-color: #000000;");
        codeshareField.setStyle("-fx-border-color: #000000;");
        stopsField.setStyle("-fx-border-color: #000000;");
        equipmentField.setStyle("-fx-border-color: #000000;");
    }

    public void updateAirports() {
        ResultSet resultSet = airportService.getAirports(null, null, null);
        List<String> airportNames = new ArrayList<>();
        try {
            while (resultSet.next()) {
                AirportData data = new AirportData(resultSet);
                airportNames.add(data.getAirportName());
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch all airports:");
            System.out.println(e);
        }

        sourceField.setItems(FXCollections.observableList(airportNames));
        destinationField.setItems(FXCollections.observableList(airportNames));
    }

    public void updateAirlines() {
        ResultSet resultSet = airlineService.getAirlines(null, null, null);
        List<String> airlineNames = new ArrayList<>();
        try {
            while (resultSet.next()) {
                AirlineData data = new AirlineData(resultSet);
                airlineNames.add(data.getName());
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch all airlines:");
            System.out.println(e);
        }

        airlineField.setItems(FXCollections.observableList(airlineNames));
    }


    @FXML
    public void onCancelPressed(ActionEvent event) {
        Window window = ((Node)event.getSource()).getScene().getWindow();
        window.hide();
    }
}
