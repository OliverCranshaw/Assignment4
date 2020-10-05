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
import java.util.ArrayList;


/**
 * AddRouteMenuController
 *
 * Controller class for the single add route interface.
 */
public class AddRouteMenuController {

    private final AirlineService airlineService;
    private final AirportService airportService;

    /**
     * Constructor for AddRouteMenuController.
     * Initializes the airline and airport service.
     */
    public AddRouteMenuController() {
        airlineService = new AirlineService();
        airportService = new AirportService();
    }

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


    /**
     * Handler for the pressing of the add route button.
     * Does error checking and displays an error message if the given values
     * are invalid. Otherwise saves the given data to the database.
     *
     * @param event Add route button pressed.
     */
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
                // Attempting to get data on the airline of the route
                ResultSet resultSet = airlineService.getData(airlineName, null, null);
                AirlineData airline = new AirlineData(resultSet);
                airlineCode = (airline.getIATA() == null) ? airline.getICAO() : airline.getIATA();
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
                // Attempting to get data on the source airport of the route
                ResultSet resultSet = airportService.getData(sourceName, null, null);
                AirportData srcAirport = new AirportData(resultSet);
                sourceCode = (srcAirport.getIATA() == null) ? srcAirport.getICAO() : srcAirport.getIATA();
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
                // Attempting to get data on the destination airport of the route
                ResultSet resultSet = airportService.getData(destName, null, null);
                AirportData dstAirport = new AirportData(resultSet);
                destCode = (dstAirport.getIATA() == null) ? dstAirport.getICAO() : dstAirport.getIATA();
            }
        } catch (SQLException e) {
            errorMessage.setText("Failed to find corresponding Destination Airport");
            errorMessage.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 12));
            errorMessage.setFill(Color.RED);
            errorMessage.setVisible(true);
            addRouteDstAirportText.setStyle("-fx-border-color: #ff0000;");
            return;
        }

        // Parsing the equipment of the route
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
        // Attempting to save given data to the database
        int outcome = concreteAddData.addRoute(airlineCode, sourceCode, destCode, codeShare, stopsField.getText(), equipSpaceSeparated);
        setDefaults();
        // If an error occurred in the saving of data, this statement handles the error messaging.
        if (outcome < 0) {
            // Setting the error message to be visible and setting its style
            errorMessage.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 12));
            errorMessage.setFill(Color.RED);
            errorMessage.setVisible(true);
            if (outcome == -1) {
                // IATA and/or ICAO conflict or null error
                addRouteAirlineText.setStyle("-fx-border-color: #ff0000;");
                addRouteSrcAirportText.setStyle("-fx-border-color: #ff0000;");
                addRouteDstAirportText.setStyle("-fx-border-color: #ff0000;");
                errorMessage.setText("Please ensure the input source and/or destination exist within the database");
            } else if (outcome == -2) {
                // Airline code formatting error
                addRouteAirlineText.setStyle("-fx-border-color: #ff0000;");
                errorMessage.setText("Please ensure a valid airline code has been entered");
            } else if (outcome == -3) {
                // Source airport code formatting error
                addRouteSrcAirportText.setStyle("-fx-border-color: #ff0000;");
                errorMessage.setText("Please ensure a valid source airport code has been entered");
            } else if (outcome == -4) {
                // Destination airport code formatting error
                addRouteDstAirportText.setStyle("-fx-border-color: #ff0000;");
                errorMessage.setText("Please ensure a valid destination airport code has been entered");
            } else if (outcome == -5) {
                // Code share formatting error
                codeshareField.setStyle("-fx-border-color: #ff0000;");
                errorMessage.setText("Please ensure a valid codeshare has been entered");
            } else if (outcome == -6) {
                // Stops formatting error
                stopsField.setStyle("-fx-border-color: #ff0000;");
                errorMessage.setText("Please ensure stops is a positive value");
            } else if (outcome == -7) {
                // Equipment formatting error
                equipmentField.setStyle("-fx-border-color: #ff0000;");
                errorMessage.setText("Please ensure the equipment field has valid input");
            }
        } else {
            // Successful save case
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

    /**
     * Sets the error message and error highlights to default values.
     */
    public void setDefaults() {
        addRouteAirlineText.setStyle("-fx-border-color: #000000;");
        addRouteSrcAirportText.setStyle("-fx-border-color: #000000;");
        addRouteDstAirportText.setStyle("-fx-border-color: #000000;");
        codeshareField.setStyle("-fx-border-color: #000000;");
        stopsField.setStyle("-fx-border-color: #000000;");
        equipmentField.setStyle("-fx-border-color: #000000;");
        errorMessage.setVisible(false);
    }

    /**
     * Closes the add route window.
     *
     * @param event Cancel button pressed
     */
    @FXML
    public void onCancelPressed(ActionEvent event) {
        Window window = ((Node)event.getSource()).getScene().getWindow();
        window.hide();
    }
}
