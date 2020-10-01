package seng202.team5.controller;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import seng202.team5.data.ReadFile;

import java.io.File;
import java.util.ArrayList;

public class UploadAirlinesMenuController {

    @FXML
    private Text errorList;

    private ArrayList<String> errors;

    private String errorString = "";

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
            ((Node)event.getSource()).getScene().setCursor(Cursor.WAIT);
            Task<Void> task = new Task<>() {
                @Override
                public Void call() {
                    // Gets the list of errors returned from reading the file and concatenates them to a string which is then displayed to the user
                    errors = (ArrayList<String>)(readFile.readAirlineData(file).get(1));
                    if (!errors.isEmpty()) {
                        for (String error : errors) {
                            errorString += error + "\n";
                        }
                        errorString += "Any other airlines added successfully";
                    } else {
                        errorString = "All airlines added successfully";
                    }

                    return null;
                }
            };
            task.setOnSucceeded(e -> {
                ((Node)event.getSource()).getScene().setCursor(Cursor.DEFAULT);
                errorList.setText(errorString);
            });
            new Thread(task).start();
        }
    }
}
