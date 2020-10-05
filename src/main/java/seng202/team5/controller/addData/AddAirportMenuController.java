package seng202.team5.controller.addData;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Window;
import seng202.team5.data.ConcreteAddData;

/**
 * AddAirportMenuController
 *
 * Controller class for the single airport add interface.
 */
public class AddAirportMenuController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField countryField;

    @FXML
    private TextField iataField;

    @FXML
    private TextField icaoField;

    @FXML
    private TextField latitudeField;

    @FXML
    private TextField longitudeField;

    @FXML
    private TextField altitudeField;

    @FXML
    private TextField timezoneField;

    @FXML
    private ComboBox<String> dstField;

    @FXML
    private TextField tzField;

    @FXML
    private Text errorMessage;


    /**
     * Handler for the pressing of the add airport button.
     * Does error checking and displays an error message if the given values
     * are invalid. Otherwise saves the given data to the database.
     *
     * @param event Add airport button pressed event.
     */
    @FXML
    public void addButtonPressed(ActionEvent event) {

        try {

            ConcreteAddData concreteAddData = new ConcreteAddData();
            boolean isDstEmpty = dstField.getSelectionModel().isEmpty();
            if (isDstEmpty) {
                dstField.setStyle("-fx-border-color: #ff0000;");
            } else {
                // Attempting to add the airport to the database
                int outcome = concreteAddData.addAirport(nameField.getText(), cityField.getText(), countryField.getText(), iataField.getText(),
                        icaoField.getText(), latitudeField.getText(), longitudeField.getText(), altitudeField.getText(), timezoneField.getText(),
                        dstField.getValue().subSequence(0, 1).toString(), tzField.getText());
                setDefaults();

                // If an error occurred in the saving of data, this statement handles the error messaging.
                if (outcome < 0) {
                    // Setting the error message to be visible and setting its style
                    errorMessage.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 12));
                    errorMessage.setFill(Color.RED);
                    errorMessage.setVisible(true);
                    if (outcome == -1) {
                        // IATA and/or ICAO conflict or null error
                        errorMessage.setText("Please ensure the input iata and/or icao are not already used for an airport within the database and that they are not both empty");
                        iataField.setStyle("-fx-border-color: #ff0000;");
                        icaoField.setStyle("-fx-border-color: #ff0000;");
                    } else if (outcome == -2) {
                        // Name formatting error
                        nameField.setStyle("-fx-border-color: #ff0000;");
                        errorMessage.setText("Please ensure the name field is not empty");
                    } else if (outcome == -3) {
                        // City formatting error
                        cityField.setStyle("-fx-border-color: #ff0000;");
                        errorMessage.setText("Please ensure the city field is not empty");
                    } else if (outcome == -4) {
                        // Country formatting error
                        countryField.setStyle("-fx-border-color: #ff0000;");
                        errorMessage.setText("Please ensure the country field is not empty");
                    } else if (outcome == -5) {
                        // IATA formatting error
                        iataField.setStyle("-fx-border-color: #ff0000;");
                        errorMessage.setText("Please ensure the entered IATA is of valid form");
                    } else if (outcome == -6) {
                        // ICAO formatting error
                        icaoField.setStyle("-fx-border-color: #ff0000;");
                        errorMessage.setText("Please ensure the entered ICAO is of valid form");
                    } else if (outcome == -7) {
                        // Latitude formatting error
                        latitudeField.setStyle("-fx-border-color: #ff0000;");
                        errorMessage.setText("Please ensure the latitude field is not empty");
                    } else if (outcome == -8) {
                        // Longitude formatting error
                        longitudeField.setStyle("-fx-border-color: #ff0000;");
                        errorMessage.setText("Please ensure the longitude field is not empty");
                    } else if (outcome == -9) {
                        // Altitude formatting error
                        altitudeField.setStyle("-fx-border-color: #ff0000;");
                        errorMessage.setText("Please ensure the altitude field is not empty");
                    } else if (outcome == -10) {
                        // Timezone formatting error
                        timezoneField.setStyle("-fx-border-color: #ff0000;");
                        errorMessage.setText("Please ensure the timezone field is not empty");
                    } else if (outcome == -11) {
                        // DST formatting error
                        dstField.setStyle("-fx-border-color: #ff0000;");
                        errorMessage.setText("Please ensure a dst value is selected");
                    } else if (outcome == -12) {
                        // TZ formatting error
                        tzField.setStyle("-fx-border-color: #ff0000;");
                        errorMessage.setText("Please ensure the tz field has been filled with a valid form TZ timezone");
                    }
                } else {
                    // Successful save case
                    setDefaults();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Successfully added airport: " + nameField.getText());
                    errorMessage.setVisible(false);
                    alert.showAndWait();
                    Window window = ((Node)event.getSource()).getScene().getWindow();
                    window.hide();
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid number");
        }
    }

    /**
     * Closes the add airport window.
     *
     * @param event Cancel button pressed.
     */
    @FXML
    public void onCancelPressed(ActionEvent event) {
        Window window = ((Node)event.getSource()).getScene().getWindow();
        window.hide();
    }

    /**
     * Sets the error message and error highlights to default values.
     */
    public void setDefaults() {
        nameField.setStyle("-fx-border-color: #000000;");
        cityField.setStyle("-fx-border-color: #000000;");
        countryField.setStyle("-fx-border-color: #000000;");
        iataField.setStyle("-fx-border-color: #000000;");
        icaoField.setStyle("-fx-border-color: #000000;");
        latitudeField.setStyle("-fx-border-color: #000000;");
        longitudeField.setStyle("-fx-border-color: #000000;");
        altitudeField.setStyle("-fx-border-color: #000000;");
        timezoneField.setStyle("-fx-border-color: #000000;");
        dstField.setStyle("-fx-border-color: #000000;");
        tzField.setStyle("-fx-border-color: #000000;");
        errorMessage.setVisible(false);
    }
}
