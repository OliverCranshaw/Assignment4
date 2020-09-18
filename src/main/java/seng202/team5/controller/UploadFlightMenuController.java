package seng202.team5.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import seng202.team5.data.ReadFile;

import java.awt.*;
import java.io.File;

public class UploadFlightMenuController {

    @FXML
    public void onSelectFilePressed(ActionEvent event) {
        ReadFile readFile = new ReadFile();

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt", "*.csv"));

        File file = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());

        if (file != null) {
            readFile.readFlightData(file);
        }
    }
}
