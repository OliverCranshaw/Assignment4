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
import java.sql.SQLOutput;
import java.util.*;

public class AddRouteMenuController {
    private AirlineService airlineService = new AirlineService();
    private AirportService airportService = new AirportService();

    public AddRouteMenuController() {}

    @FXML
    private TextField addRouteAirlineText;

    @FXML
    private TextField addRouteSrcAirportText;

    @FXML
    private TextField addRouteDstAirportText;

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

    }

    @FXML
    public void onAddPressed(ActionEvent event) {

        ConcreteAddData concreteAddData = new ConcreteAddData();
        String codeShare = codeshareField.isSelected() ? "Y" : null;

        String airlineName = addRouteAirlineText.getText();
        String sourceName = addRouteSrcAirportText.getText();
        String destName = addRouteDstAirportText.getText();

        String airlineCode = null;
        String sourceCode = null;
        String destCode = null;
        setDefaults();
        try {
            {
                ResultSet resultSet = airlineService.getAirlines(airlineName, null, null);
                AirlineData airline = new AirlineData(resultSet);
                System.out.println(airline.getIata());
                if (airline.getName() == null) {
                    System.out.println("Please ensure the given airline is in the database");
                } else if(airline.getIata() == null && airline.getIcao() == null) {
                    System.out.println("Please ensure the selected Airline has an associated IATA or ICAO value");
                } else {
                    airlineCode = (airline.getIata() == null) ? airline.getIcao() : airline.getIata();
                }
            }
            {
                ResultSet resultSet = airportService.getAirports(sourceName, null, null);
                AirportData srcAirport = new AirportData(resultSet);
                if (srcAirport.getAirportName() == null) {
                    System.out.println("Please ensure the given source airport is in the database");
                } else if (srcAirport.getIata() == null && srcAirport.getIcao() == null) {
                    System.out.println("Please ensure the selected source airport has an associated IATA or ICAO value");
                } else {
                    sourceCode = (srcAirport.getIata() == null) ? srcAirport.getIcao() : srcAirport.getIata();
                }
            }
            {
                ResultSet resultSet = airportService.getAirports(destName, null, null);
                AirportData dstAirport = new AirportData(resultSet);
                if (dstAirport.getAirportName() == null) {
                    System.out.println("Please ensure the given destination airport is in the database");
                } else if (dstAirport.getIata() == null && dstAirport.getIcao() == null) {
                    System.out.println("Please ensure the selected destination airport has an associated IATA or ICAO value");
                } else {
                    destCode = (dstAirport.getIata() == null) ? dstAirport.getIcao() : dstAirport.getIata();
                }
            }
        } catch (SQLException e) {
            errorMessage.setText("Failed to find corresponding Airport/Airline with the given names");
            errorMessage.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 12));
            errorMessage.setFill(Color.RED);
            errorMessage.setVisible(true);
            System.out.println("Failed to find corresponding Airport/Airline with the given names");
            return;
        }


        String equipmentString = equipmentField.getText();
        String[] equipmentStringSplit = equipmentString.split(",");

        ArrayList<String> equipmentList = new ArrayList<>();
        for (String component : equipmentStringSplit) {
            equipmentList.add(component.trim());
        }

        String equipSpaceSeparated = "";
        for (String component : equipmentList) {
            equipSpaceSeparated += component;
            equipSpaceSeparated += " ";
        }
        equipSpaceSeparated = (String) equipSpaceSeparated.subSequence(0, equipSpaceSeparated.length() - 1);

        int outcome = concreteAddData.addRoute(airlineCode, sourceCode, destCode, codeShare, stopsField.getText(), equipSpaceSeparated);
        setDefaults();
        if (outcome < 0) {
            if (outcome == -1) {
                System.out.println("Service Error");
                errorMessage.setText("Please ensure the input source and/or destination exist within the database");
                errorMessage.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 12));
                errorMessage.setFill(Color.RED);
                errorMessage.setVisible(true);
            } else if (outcome == -2) {
                addRouteAirlineText.setStyle("-fx-border-color: #ff0000;");
            } else if (outcome == -3) {
                addRouteSrcAirportText.setStyle("-fx-border-color: #ff0000;");
            } else if (outcome == -4) {
                addRouteDstAirportText.setStyle("-fx-border-color: #ff0000;");
            } else if (outcome == -5) {
                codeshareField.setStyle("-fx-border-color: #ff0000;");
            } else if (outcome == -6) {
                stopsField.setStyle("-fx-border-color: #ff0000;");
            } else if (outcome == -7) {
                equipmentField.setStyle("-fx-border-color: #ff0000;");
            }
        } else {
            setDefaults();
            Window window = ((Node)event.getSource()).getScene().getWindow();
            window.hide();
        }
    }


    public void setDefaults() {
        addRouteAirlineText.setStyle("-fx-border-color: #000000;");
        addRouteSrcAirportText.setStyle("-fx-border-color: #000000;");
        addRouteDstAirportText.setStyle("-fx-border-color: #000000;");
        codeshareField.setStyle("-fx-border-color: #000000;");
        stopsField.setStyle("-fx-border-color: #000000;");
        equipmentField.setStyle("-fx-border-color: #000000;");
        errorMessage.setVisible(false);
    }

    @FXML
    public void onCancelPressed(ActionEvent event) {
        Window window = ((Node)event.getSource()).getScene().getWindow();
        window.hide();
    }
}
