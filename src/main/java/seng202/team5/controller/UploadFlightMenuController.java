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

public class UploadFlightMenuController {

    @FXML
    private Text errorList;

    @FXML
    public void onSelectFilePressed(ActionEvent event) {
        ReadFile readFile = new ReadFile();

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt", "*.csv"));

        File file = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());

        if (file != null) {
            ArrayList<String> errors = (ArrayList<String>)(readFile.readFlightData(file).get(1));
            String errorString = "";

            if (!errors.isEmpty()) {
                for (String error : errors) {
                    errorString += error + "\n";
                }
                errorString += "Flight unable to be added to database";
            } else {
                errorString = "Flight added successfully";
            }

            errorList.setText(errorString);
        }
    }
}
