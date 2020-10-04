package seng202.team5.controller.addData;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
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
                ResultSet resultSet = airlineService.getData(airlineName, null, null);
                AirlineData airline = new AirlineData(resultSet);
                System.out.println(airline.getIATA());
                if (airline.getName() == null) {
                    System.out.println("Please ensure the given airline is in the database");
                } else if (airline.getIATA() == null && airline.getICAO() == null) {
                    System.out.println("Please ensure the selected Airline has an associated IATA or ICAO value");
                } else {
                    airlineCode = (airline.getIATA() == null) ? airline.getICAO() : airline.getIATA();
                }
            }
        } catch (SQLException e) {
            errorMessage.setText("Failed to find corresponding Airline");
            errorMessage.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 12));
            errorMessage.setFill(Color.RED);
            errorMessage.setVisible(true);
            addRouteAirlineText.setStyle("-fx-border-color: #ff0000;");
            return;
        }
        try {
            {
                ResultSet resultSet = airportService.getData(sourceName, null, null);
                AirportData srcAirport = new AirportData(resultSet);
                if (srcAirport.getAirportName() == null) {
                    System.out.println("Please ensure the given source airport is in the database");
                } else if (srcAirport.getIATA() == null && srcAirport.getICAO() == null) {
                    System.out.println("Please ensure the selected source airport has an associated IATA or ICAO value");
                } else {
                    sourceCode = (srcAirport.getIATA() == null) ? srcAirport.getICAO() : srcAirport.getIATA();
                }
            }
        } catch (SQLException e) {
            errorMessage.setText("Failed to find corresponding Source Airport");
            errorMessage.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 12));
            errorMessage.setFill(Color.RED);
            errorMessage.setVisible(true);
            addRouteSrcAirportText.setStyle("-fx-border-color: #ff0000;");
            return;
        }
        try {
            {
                ResultSet resultSet = airportService.getData(destName, null, null);
                AirportData dstAirport = new AirportData(resultSet);
                if (dstAirport.getAirportName() == null) {
                    System.out.println("Please ensure the given destination airport is in the database");
                } else if (dstAirport.getIATA() == null && dstAirport.getICAO() == null) {
                    System.out.println("Please ensure the selected destination airport has an associated IATA or ICAO value");
                } else {
                    destCode = (dstAirport.getIATA() == null) ? dstAirport.getICAO() : dstAirport.getIATA();
                }
            }
        } catch (SQLException e) {
            errorMessage.setText("Failed to find corresponding Destination Airport");
            errorMessage.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 12));
            errorMessage.setFill(Color.RED);
            errorMessage.setVisible(true);
            addRouteDstAirportText.setStyle("-fx-border-color: #ff0000;");
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
            errorMessage.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 12));
            errorMessage.setFill(Color.RED);
            errorMessage.setVisible(true);
            if (outcome == -1) {
                System.out.println("Service Error");
                addRouteAirlineText.setStyle("-fx-border-color: #ff0000;");
                addRouteSrcAirportText.setStyle("-fx-border-color: #ff0000;");
                addRouteDstAirportText.setStyle("-fx-border-color: #ff0000;");
                errorMessage.setText("Please ensure the input source and/or destination exist within the database");
            } else if (outcome == -2) {
                addRouteAirlineText.setStyle("-fx-border-color: #ff0000;");
                errorMessage.setText("Please ensure a valid airline code has been entered");
            } else if (outcome == -3) {
                addRouteSrcAirportText.setStyle("-fx-border-color: #ff0000;");
                errorMessage.setText("Please ensure a valid source airport code has been entered");
            } else if (outcome == -4) {
                addRouteDstAirportText.setStyle("-fx-border-color: #ff0000;");
                errorMessage.setText("Please ensure a valid destination airport code has been entered");
            } else if (outcome == -5) {
                codeshareField.setStyle("-fx-border-color: #ff0000;");
                errorMessage.setText("Please ensure a valid codeshare has been entered");
            } else if (outcome == -6) {
                stopsField.setStyle("-fx-border-color: #ff0000;");
                errorMessage.setText("Please ensure stops is a positive value");
            } else if (outcome == -7) {
                equipmentField.setStyle("-fx-border-color: #ff0000;");
                errorMessage.setText("Please ensure the equipment field has valid input");
            }
        } else {
            setDefaults();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setContentText("Successfully added Route");
            errorMessage.setVisible(false);
            alert.showAndWait();
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
