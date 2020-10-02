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

public class UploadFlightMenuController extends BaseUploadMenuController {

    @Override
    protected String doUploadOperation(ReadFile readFile, File file) {
        ArrayList<Object> readResult = readFile.readFlightData(file);

        if (readResult == null) return null;

        ArrayList<String> errors = (ArrayList<String>)(readResult.get(1));
        String errorString = "";

        if (!errors.isEmpty()) {
            for (String error : errors) {
                errorString += error + "\n";
            }
            errorString += "Flight unable to be added to database";
        } else {
            errorString = "Flight added successfully";
        }

        return errorString;
    }
}