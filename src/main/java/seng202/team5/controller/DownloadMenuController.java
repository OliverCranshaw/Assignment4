package seng202.team5.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import seng202.team5.data.DataExporter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class DownloadMenuController {

    @FXML
    private TextField nameField;

    @FXML
    private Text invalidText;

    private ArrayList<String> file;

    private DataExporter dataExporter;

    @FXML
    public void onAirlineSelectFolderPressed(ActionEvent event) {
        file = selectFolder(event);

        if (!file.isEmpty()) {
            dataExporter.exportAirlines(file.get(0), file.get(1));
        }
    }

    @FXML
    public void onAirportSelectFolderPressed(ActionEvent event) {
        file = selectFolder(event);

        if (!file.isEmpty()) {
            dataExporter.exportAirports(file.get(0), file.get(1));
        }
    }

    @FXML
    public void onFlightSelectFolderPressed(ActionEvent event) {
        file = selectFolder(event);
        // implement
        dataExporter.exportAirports(file.get(0), file.get(1));
    }

    @FXML
    public void onAllFlightsSelectFolderPressed(ActionEvent event) {
        file = selectFolder(event);

        if (!file.isEmpty()) {
            dataExporter.exportFlights(file.get(0), file.get(1));
        }
    }

    @FXML
    public void onRouteSelectFolderPressed(ActionEvent event) {
        file = selectFolder(event);

        if (!file.isEmpty()) {
            dataExporter.exportRoutes(file.get(0), file.get(1));
        }
    }

    @FXML
    public ArrayList<String> selectFolder(ActionEvent event) {
        dataExporter = new DataExporter();

        invalidText.setVisible(false);

        String filename = nameField.getText();

        if (filename.length() < 1) {
            invalidText.setVisible(true);
        } else {
            filename = filename.replaceAll("[\"\\\\/*?|<>{}!$#&@:]", "");
            filename += ".csv";

            DirectoryChooser directoryChooser = new DirectoryChooser();

            File directory = directoryChooser.showDialog(((Node) event.getSource()).getScene().getWindow());

            String dir = directory.getAbsolutePath();

            return new ArrayList<>(Arrays.asList(dir, filename));
        }

        return new ArrayList<>();
    }

}
