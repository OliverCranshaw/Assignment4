package seng202.team5.controller;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import seng202.team5.data.ReadFile;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class UploadAirlinesMenuController extends BaseUploadMenuController {

    @Override
    protected String doUploadOperation(ReadFile readFile, File file) {
        ArrayList<Object> readResult = readFile.readAirlineData(file);

        if (readResult == null) return null;

        ArrayList<String> errors = (ArrayList<String>)(readResult.get(1));
        String errorString = "";

        if (!errors.isEmpty()) {
            for (String error : errors) {
                errorString += error + "\n";
            }
            errorString += "Any other airlines added successfully";
        } else {
            errorString = "All airlines added successfully";
        }

        return errorString;
    }
}
