package seng202.team5.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import seng202.team5.data.DataExporter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class DownloadMenuController {

    @FXML
    private Text downloadLabel;

    @FXML
    private TextField nameField;

    @FXML
    private Text invalidText;

    @FXML
    public void onSelectFolderPressed(ActionEvent event) {
        DataExporter dataExporter = new DataExporter();

        invalidText.setVisible(false);

        String filename = nameField.getText();

        if (filename.length() < 1) {
            invalidText.setVisible(true);
        } else {
            filename.replaceAll("[\"\\\\/*?|<>{}!$#&@:]", "");
            filename += ".csv";

            DirectoryChooser directoryChooser = new DirectoryChooser();

            File directory = directoryChooser.showDialog(((Node) event.getSource()).getScene().getWindow());

            String dir = directory.getAbsolutePath();

            //dataExporter.exportAirports(dir, filename);
        }
    }


}
