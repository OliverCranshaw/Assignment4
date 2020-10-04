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
import seng202.team5.data.ConcreteAddData;


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
    public void onAddPressed(ActionEvent event) {
        try {
            ConcreteAddData concreteAddData = new ConcreteAddData();
            String active = activeField.isSelected() ? "Y" : "N";
            int outcome = concreteAddData.addAirline(nameField.getText(), aliasField.getText(), iataField.getText(),
                    icaoField.getText(), callsignField.getText(), countryField.getText(), active);
            setDefaults();
            if (outcome < 0) {
                errorMessage.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 12));
                errorMessage.setFill(Color.RED);
                errorMessage.setVisible(true);
                if (outcome == -1) {
                    System.out.println("Service Error");
                    errorMessage.setText("Please ensure the input iata and/or icao are not already used for an airline within the database and that they are not both empty");
                } else if (outcome == -2) {
                    nameField.setStyle("-fx-border-color: #ff0000;");
                    errorMessage.setText("Please ensure the name field is not empty");
                } else if (outcome == -3) {
                    iataField.setStyle("-fx-border-color: #ff0000;");
                    errorMessage.setText("Please ensure the Iata is of valid form");
                } else if (outcome == -4) {
                    icaoField.setStyle("-fx-border-color: #ff0000;");
                    errorMessage.setText("Please ensure the Icao is of valid form");
                } else if (outcome == -5) {
                    activeField.setStyle("-fx-border-color: #ff0000;");
                    errorMessage.setText("Please ensure the active is of valid form (Y or N)");
                }
            } else {
                setDefaults();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Success");
                alert.setContentText("Successfully added airline: " + nameField.getText());
                errorMessage.setVisible(false);
                alert.showAndWait();
                Window window = ((Node)event.getSource()).getScene().getWindow();
                window.hide();
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
