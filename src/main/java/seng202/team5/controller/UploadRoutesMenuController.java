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

public class UploadRoutesMenuController extends BaseUploadMenuController {

    @Override
    protected String doUploadOperation(ReadFile readFile, File file) {
        ArrayList<Object> readResult = readFile.readRouteData(file);

        if (readResult == null) return null;

        ArrayList<String> errors = (ArrayList<String>)(readResult.get(1));
        String errorString = "";

        if (!errors.isEmpty()) {
            for (String error : errors) {
                errorString += error + "\n";
            }
            errorString += "Any other routes added successfully";
        } else {
            errorString = "All routes added successfully";
        }

        return errorString;
    }
}