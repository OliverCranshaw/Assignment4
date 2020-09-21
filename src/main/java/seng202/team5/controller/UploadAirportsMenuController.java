package seng202.team5.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import seng202.team5.data.ReadFile;

import java.io.File;
import java.util.ArrayList;

public class UploadAirportsMenuController {

    @FXML
    private Text errorList;

    @FXML
    public void onSelectFilePressed(ActionEvent event) {
        ReadFile readFile = new ReadFile();

        FileChooser fileChooser = new FileChooser();
        // Sets the types of files users are allowed to select
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Text Files (*.txt, *.csv)", "*.txt", "*.csv");
        fileChooser.getExtensionFilters().add(extensionFilter);
        // Opens the FileChooser Open window
        File file = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());

        if (file != null) {
            // Gets the list of errors returned from reading the file and concatenates them to a string which is then displayed to the user
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
