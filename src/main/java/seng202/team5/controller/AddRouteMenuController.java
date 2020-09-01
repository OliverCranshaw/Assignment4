package seng202.team5.controller;

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
import seng202.team5.data.ConcreteAddData;

import java.util.ArrayList;
import java.util.List;

public class AddRouteMenuController {
    public AddRouteMenuController() {}

    @FXML
    private TextField airlineField;

    @FXML
    private TextField sourceField;

    @FXML
    private TextField destinationField;

    @FXML
    private CheckBox codeshareField;

    @FXML
    private TextField stopsField;

    @FXML
    private TextField equipmentField;

    @FXML
    private Text errorMessage;

    @FXML
    public void onAddPressed() {

        ConcreteAddData concreteAddData = new ConcreteAddData();
        String codeShare = codeshareField.isSelected() ? "Y" : null;
        int outcome = concreteAddData.addRoute(airlineField.getText(), sourceField.getText(), destinationField.getText(), codeShare, stopsField.getText(), equipmentField.getText());
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


    @FXML
    public void onCancelPressed(ActionEvent event) {
        Window window = ((Node)event.getSource()).getScene().getWindow();
        window.hide();
    }
}
