package seng202.team5.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

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
    public void onAddPressed() {
        List<String> fields = new ArrayList<>();
        fields.add(nameField.getText());
        fields.add(aliasField.getText());
        fields.add(iataField.getText());
        fields.add(icaoField.getText());
        fields.add(countryField.getText());
        fields.add(callsignField.getText());
        fields.add(activeField.isSelected() ? "Y" : "N");

        System.out.println("Added row:");
        System.out.print("1");
        for (String field : fields) {
            System.out.print(",\"" + field + "\"");
        }
        System.out.println();
    }

    @FXML
    public void onCancelPressed() {
        System.out.println("Add airline cancelled");
    }
}
