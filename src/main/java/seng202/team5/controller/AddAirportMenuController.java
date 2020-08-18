package seng202.team5.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Window;

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
    private ComboBox<String> typeField;

    @FXML
    private TextField sourceField;


    @FXML
    public void addButtonPressed() {
        List<Object> fields = new ArrayList<>();

        try {
            fields.add(nameField.getText());
            fields.add(cityField.getText());
            fields.add(countryField.getText());

            fields.add(iataField.getText());
            fields.add(icaoField.getText());

            fields.add(latitudeField.getText().length() == 0 ? null : Double.parseDouble(latitudeField.getText()));
            fields.add(longitudeField.getText().length() == 0 ? null : Double.parseDouble(longitudeField.getText()));
            fields.add(altitudeField.getText().length() == 0 ? null : Double.parseDouble(altitudeField.getText()));

            fields.add(timezoneField.getText().length() == 0 ? null : Double.parseDouble(timezoneField.getText()));
            fields.add(dstField.getValue() == null ? null : dstField.getValue().subSequence(0, 1));
            fields.add(tzField.getText());

            fields.add(typeField.getValue() == null ? null : typeField.getValue().toLowerCase());
            fields.add(sourceField.getText());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number");
            return;
        }


        System.out.println("Added row:");
        System.out.print("1");
        for (Object field : fields) {
            System.out.print(",");
            if (field == null) {
                System.out.print("NULL");
                continue;
            }
            if (field instanceof String) {
                System.out.print("\"" + field + "\"");
                continue;
            }

            System.out.print(field);
        }
        System.out.println();
    }

    @FXML
    public void onCancelPressed(ActionEvent event) {
        Window window = ((Node)event.getSource()).getScene().getWindow();
        window.hide();
    }

}
