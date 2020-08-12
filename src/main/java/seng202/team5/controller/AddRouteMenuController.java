package seng202.team5.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class AddRouteMenuController {
    public AddRouteMenuController() {}

    @FXML
    private ComboBox<String> airlineField;

    @FXML
    private ComboBox<String> sourceField;

    @FXML
    private ComboBox<String> destField;

    @FXML
    private CheckBox codeshareField;

    @FXML
    private TextField stopsField;

    @FXML
    private TextField equipmentField;

    @FXML
    public void onAddPressed() {
        List<Object> fields = new ArrayList<>();
        try {
            fields.add(airlineField.getValue());
            fields.add(sourceField.getValue());
            fields.add(destField.getValue());
            fields.add(codeshareField.isSelected() ? "Y" : "");
            fields.add(stopsField.getText().isEmpty() ? null : Integer.parseInt(stopsField.getText()));
            fields.add(equipmentField.getText());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number");
            return;
        }

        System.out.println("Added row:");
        System.out.println(fields);
    }

    @FXML
    public void onCancelPressed() {
        System.out.println("Cancelled");
    }
}
