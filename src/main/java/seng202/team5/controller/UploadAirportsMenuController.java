package seng202.team5.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import seng202.team5.data.ReadFile;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class UploadAirportsMenuController {

    @FXML
    private Text errorList;

    @FXML
    public void onSelectFilePressed(ActionEvent event) {
        ReadFile readFile = new ReadFile();

        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Text Documents (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        extensionFilter = new FileChooser.ExtensionFilter("CSV (Comma-Separated Values) (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());

        if (file != null) {
            ArrayList<String> errors = (ArrayList<String>)(readFile.readAirportData(file).get(1));
            String errorString = "";

            if (!errors.isEmpty()) {
                for (String error : errors) {
                    errorString += error + "\n";
                }
                errorString += "Any other airports added successfully";
            } else {
                errorString = "All airports added successfully";
            }

            errorList.setText(errorString);
        }
    }
}
