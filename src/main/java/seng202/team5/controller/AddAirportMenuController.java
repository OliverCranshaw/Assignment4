package seng202.team5.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Window;
import seng202.team5.data.ConcreteAddData;

import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.List;


public class AddAirportMenuController {
    public AddAirportMenuController() {}

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




    @FXML
    public void addButtonPressed() {


        try {

            String dst = null;
            ConcreteAddData concreteAddData = new ConcreteAddData();
            boolean isDstEmpty = dstField.getSelectionModel().isEmpty();
            if (isDstEmpty) {
                dstField.setStyle("-fx-border-color: #ff0000;");
            } else {
                int outcome = concreteAddData.addAirport(nameField.getText(), cityField.getText(), countryField.getText(), iataField.getText(),
                        icaoField.getText(), latitudeField.getText(), longitudeField.getText(), altitudeField.getText(), timezoneField.getText(),
                        dstField.getValue().subSequence(0, 1).toString(), tzField.getText());
                setDefaults();

                if (outcome < 0) {
                    if (outcome == -1) {
                        System.out.println("Service Error");
                        errorMessage.setText("Please ensure the input iata and/or icao are not already used for an airport within the database");
                        errorMessage.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 12));
                        errorMessage.setFill(Color.RED);
                        errorMessage.setVisible(true);
                    } else if (outcome == -2) {
                        nameField.setStyle("-fx-border-color: #ff0000;");
                    } else if (outcome == -3) {
                        cityField.setStyle("-fx-border-color: #ff0000;");
                    } else if (outcome == -4) {
                        countryField.setStyle("-fx-border-color: #ff0000;");
                    } else if (outcome == -5) {
                        iataField.setStyle("-fx-border-color: #ff0000;");
                    } else if (outcome == -6) {
                        icaoField.setStyle("-fx-border-color: #ff0000;");
                    } else if (outcome == -7) {
                        latitudeField.setStyle("-fx-border-color: #ff0000;");
                    } else if (outcome == -8) {
                        longitudeField.setStyle("-fx-border-color: #ff0000;");
                    } else if (outcome == -9) {
                        altitudeField.setStyle("-fx-border-color: #ff0000;");
                    } else if (outcome == -10) {
                        timezoneField.setStyle("-fx-border-color: #ff0000;");
                    } else if (outcome == -11) {
                        dstField.setStyle("-fx-border-color: #ff0000;");
                    } else if (outcome == -12) {
                        tzField.setStyle("-fx-border-color: #ff0000;");
                    }
                } else {
                    setDefaults();
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid number");
        }

    }


    @FXML
    public void onCancelPressed(ActionEvent event) {
        Window window = ((Node)event.getSource()).getScene().getWindow();
        window.hide();
    }


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
