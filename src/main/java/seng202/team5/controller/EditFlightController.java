package seng202.team5.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import seng202.team5.data.ConcreteUpdateData;

import java.awt.event.ActionEvent;

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

    private FlightEntry currFlightEntry;

    @FXML
    public void submitBtnPressed(javafx.event.ActionEvent event) {
        setDefaults();
        ConcreteUpdateData updater = new ConcreteUpdateData();
        int id = currFlightEntry.getID();
        if (locationTypeField.getText().equals("APT")) {
            locationTypeField.setStyle("-fx-border-color: #ff0000;");
        } else {
            Integer altitude = null;
            Double latitude = null;
            Double longitude = null;
            try {
                altitude = Integer.parseInt(altitudeField.getText());
            } catch (NumberFormatException e) {
                altitudeField.setStyle("-fx-border-color: #ff0000;");
            }
            try {
                latitude = Double.parseDouble(latitudeField.getText());
            } catch (NumberFormatException e) {
                latitudeField.setStyle("-fx-border-color: #ff0000;");
            }
            try {
                longitude = Double.parseDouble(longitudeField.getText());
            } catch (NumberFormatException e) {
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
                    switch(result) {
                        case -3:
                            locationTypeField.setStyle("-fx-border-color: #ff0000;");
                            break;
                        case -4:
                            locationField.setStyle("-fx-border-color: #ff0000;");
                            break;
                        case -5:
                            altitudeField.setStyle("-fx-border-color: #ff0000;");
                            break;
                        case -6:
                            latitudeField.setStyle("-fx-border-color: #ff0000;");
                            break;
                        case -7:
                            longitudeField.setStyle("-fx-border-color: #ff0000;");
                            break;
                    }
                }
            }
        }

    }


    @FXML
    public void cancelBtnPressed(javafx.event.ActionEvent actionEvent) {
        Window window = ((Node)actionEvent.getSource()).getScene().getWindow();
        window.hide();
    }


    public void inflateUI(FlightEntry flightEntry) {
        currFlightEntry = flightEntry;
        String ids = String.format("ID: %d    FlightID: %d", flightEntry.getID(), flightEntry.getFlightID());
        flightIDsLabel.setText(ids);
        locationTypeField.setText(flightEntry.getLocationType());
        if(flightEntry.getLocationType().equals("APT")) {
            locationTypeField.setEditable(false);
            locationField.setEditable(false);
        }
        locationField.setText(flightEntry.getLocation());
        altitudeField.setText(String.valueOf(flightEntry.getAltitude()));
        latitudeField.setText(String.valueOf(flightEntry.getLatitude()));
        longitudeField.setText(String.valueOf(flightEntry.getLongitude()));
    }


    public void setDefaults() {
        locationTypeField.setStyle("-fx-border-color: #000000;");
        locationField.setStyle("-fx-border-color: #000000;");
        altitudeField.setStyle("-fx-border-color: #000000;");
        latitudeField.setStyle("-fx-border-color: #000000;");
        longitudeField.setStyle("-fx-border-color: #000000;");
    }


}
