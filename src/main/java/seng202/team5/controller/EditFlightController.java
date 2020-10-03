package seng202.team5.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Window;
import seng202.team5.data.ConcreteUpdateData;
import seng202.team5.model.FlightEntryModel;

public class EditFlightController {

    @FXML
    private Label flightIDsLabel;
    @FXML
    private TextField locationTypeField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField altitudeField;
    @FXML
    private TextField latitudeField;
    @FXML
    private TextField longitudeField;
    @FXML
    private Label flightInvalidFormatLbl;

    private FlightEntryModel currFlightEntryModel;


    /**
     * submitBtnPressed
     *
     * Edits the currently editing flight if the values given are valid, otherwise
     * given error signs to the user
     * @param event
     */
    @FXML
    public void submitBtnPressed(javafx.event.ActionEvent event) {
        flightInvalidFormatLbl.setVisible(false);
        setDefaults();
        ConcreteUpdateData updater = new ConcreteUpdateData();
        int id = currFlightEntryModel.getID();
        if (locationTypeField.getText().equals("APT") && locationTypeField.isEditable()) {
            locationTypeField.setStyle("-fx-border-color: #ff0000;");
        } else {
            Integer altitude = null;
            Double latitude = null;
            Double longitude = null;
            try {
                altitude = Integer.parseInt(altitudeField.getText());
            } catch (NumberFormatException e) {
                flightInvalidFormatLbl.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 12));
                flightInvalidFormatLbl.setTextFill(Color.RED);
                flightInvalidFormatLbl.setText("Invalid Altitude Provided");
                flightInvalidFormatLbl.setVisible(true);
                altitudeField.setStyle("-fx-border-color: #ff0000;");
            }
            try {
                latitude = Double.parseDouble(latitudeField.getText());
            } catch (NumberFormatException e) {
                flightInvalidFormatLbl.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 12));
                flightInvalidFormatLbl.setTextFill(Color.RED);
                flightInvalidFormatLbl.setText("Invalid Latitude Provided");
                flightInvalidFormatLbl.setVisible(true);
                latitudeField.setStyle("-fx-border-color: #ff0000;");
            }
            try {
                longitude = Double.parseDouble(longitudeField.getText());
            } catch (NumberFormatException e) {
                flightInvalidFormatLbl.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 12));
                flightInvalidFormatLbl.setTextFill(Color.RED);
                flightInvalidFormatLbl.setText("Invalid Longitude Provided");
                flightInvalidFormatLbl.setVisible(true);
                longitudeField.setStyle("-fx-border-color: #ff0000;");
            }

            if (!(altitude == null || latitude == null || longitude == null)) {
                int result = updater.updateFlightEntry(id, locationTypeField.getText(), locationField.getText(), altitude,
                        latitude, longitude);
                if (result > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Edit success");
                    alert.setContentText("Successfully edited flight entry " + id + ".");
                    alert.showAndWait();
                    Window window = ((Node)event.getSource()).getScene().getWindow();
                    window.hide();
                } else {
                    flightInvalidFormatLbl.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 12));
                    flightInvalidFormatLbl.setTextFill(Color.RED);
                    flightInvalidFormatLbl.setVisible(true);
                    switch(result) {
                        case -3:
                            locationTypeField.setStyle("-fx-border-color: #ff0000;");
                            flightInvalidFormatLbl.setText("Invalid Location Type Provided");
                            break;
                        case -4:
                            locationField.setStyle("-fx-border-color: #ff0000;");
                            flightInvalidFormatLbl.setText("Invalid Location Provided");
                            break;
                        case -5:
                            altitudeField.setStyle("-fx-border-color: #ff0000;");
                            flightInvalidFormatLbl.setText("Invalid Altitude Provided");
                            break;
                        case -6:
                            latitudeField.setStyle("-fx-border-color: #ff0000;");
                            flightInvalidFormatLbl.setText("Invalid Latitude Provided");
                            break;
                        case -7:
                            longitudeField.setStyle("-fx-border-color: #ff0000;");
                            flightInvalidFormatLbl.setText("Invalid Longitude Provided");
                            break;
                    }
                }
            }
        }
    }

    /**
     * cancelBtnPressed
     *
     * Closes the current window
     * @param actionEvent
     */
    @FXML
    public void cancelBtnPressed(javafx.event.ActionEvent actionEvent) {
        flightInvalidFormatLbl.setVisible(false);
        Window window = ((Node)actionEvent.getSource()).getScene().getWindow();
        window.hide();
    }

    /**
     * inflateUI
     *
     * Populates the TextFields with the current values
     * @param flightEntryModel - flightEntry being modified
     */
    public void inflateUI(FlightEntryModel flightEntryModel) {
        flightInvalidFormatLbl.setVisible(false);
        currFlightEntryModel = flightEntryModel;
        String ids = String.format("ID: %d    FlightID: %d", flightEntryModel.getID(), flightEntryModel.getFlightID());
        flightIDsLabel.setText(ids);
        locationTypeField.setText(flightEntryModel.getLocationType());
        if(flightEntryModel.getLocationType().equals("APT")) {
            locationTypeField.setEditable(false);
            locationField.setEditable(false);
        }
        locationField.setText(flightEntryModel.getLocation());
        altitudeField.setText(String.valueOf(flightEntryModel.getAltitude()));
        latitudeField.setText(String.valueOf(flightEntryModel.getLatitude()));
        longitudeField.setText(String.valueOf(flightEntryModel.getLongitude()));
    }

    /**
     * setDefaults
     *
     * Sets the colours of the TextFields to black
     */
    public void setDefaults() {
        flightInvalidFormatLbl.setVisible(false);
        locationTypeField.setStyle("-fx-border-color: #000000;");
        locationField.setStyle("-fx-border-color: #000000;");
        altitudeField.setStyle("-fx-border-color: #000000;");
        latitudeField.setStyle("-fx-border-color: #000000;");
        longitudeField.setStyle("-fx-border-color: #000000;");
    }


}
