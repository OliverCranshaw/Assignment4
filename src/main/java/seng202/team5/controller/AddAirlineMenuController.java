package seng202.team5.controller;

import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Window;
import seng202.team5.data.ConcreteAddData;
import seng202.team5.controller.MainMenuController;

import java.util.ArrayList;
import java.util.List;

public class AddAirlineMenuController {
    public AddAirlineMenuController() {}

    @FXML
    private TextField nameField;
    
    @FXML
    private TextField aliasField;

    @FXML
    private TextField iataField;

    @FXML
    private TextField icaoField;

    @FXML
    private TextField countryField;

    @FXML
    private TextField callsignField;

    @FXML
    private CheckBox activeField;

    @FXML
    private Text errorMessage;

    @FXML
    public void onAddPressed() {
        try {
            ConcreteAddData concreteAddData = new ConcreteAddData();
            String active = activeField.isSelected() ? "Y" : "N";
            int outcome = concreteAddData.addAirline(nameField.getText(), aliasField.getText(), iataField.getText(),
                    icaoField.getText(), callsignField.getText(), countryField.getText(), active);
            setDefaults();
            if (outcome < 0) {
                if (outcome == -1) {
                    System.out.println("Service Error");
                    errorMessage.setText("Please ensure the input iata and/or icao are not already used for an airline within the database");
                    errorMessage.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 12));
                    errorMessage.setFill(Color.RED);
                    errorMessage.setVisible(true);
                } else if (outcome == -2) {
                    nameField.setStyle("-fx-border-color: #ff0000;");
                } else if (outcome == -3) {
                    iataField.setStyle("-fx-border-color: #ff0000;");
                } else if (outcome == -4) {
                    icaoField.setStyle("-fx-border-color: #ff0000;");
                } else if (outcome == -5) {
                    activeField.setStyle("-fx-border-color: #ff0000;");
                }
            } else {
                setDefaults();
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number");
        }
    }


    public void setDefaults() {
        nameField.setStyle("-fx-border-color: #000000;");
        iataField.setStyle("-fx-border-color: #000000;");
        icaoField.setStyle("-fx-border-color: #000000;");
        activeField.setStyle("-fx-border-color: #000000;");
    }

    @FXML
    public void onCancelPressed(ActionEvent event) {
        Window window = ((Node)event.getSource()).getScene().getWindow();
        window.hide();
    }
}
