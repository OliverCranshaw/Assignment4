package seng202.team5.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import seng202.team5.data.DataExporter;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class MainMenuController {


    @FXML
    public TabPane mainTabs;

    private DataExporter dataExporter = new DataExporter();

    /**
     * setFieldsEmpty
     *
     * Sets given arraylist of TextFields to be invisible and also sets their colour to black
     * @param elementsVisible - ArrayList of TextFields
     */
    public void setFieldsEmpty(ArrayList<TextField> elementsVisible) {
        for (TextField field : elementsVisible) {
            if (field != null) {
                field.setVisible(false);
                field.setEditable(false);
                field.setStyle("-fx-border-color: #000000;");
            }
        }
    }

    /**
     * setLabelsEmpty
     *
     * Sets given labels to be visible or not depending on the given boolean
     * @param elementsVisible ArrayList of Labels
     * @param bool boolean that determines if setting element to visible or not
     */
    public void setLabelsEmpty(ArrayList<Label> elementsVisible, Boolean bool) {
        for (Label lbl : elementsVisible) {
            if (lbl != null) {
                lbl.setVisible(bool);
            }
        }
    }

    /**
     * setLabels
     *
     * Sets given elementsVisible to contain the given elementData
     * @param elementData ResultSet of data to populate TextFields with
     * @param elementsVisible ArrayList of TextFields
     * @throws SQLException occurs when any interactions with the ResultSet fail
     */
    public void setLabels(ResultSet elementData, ArrayList<TextField> elementsVisible) throws SQLException {
        for (TextField field : elementsVisible) {
            field.setVisible(true);
            field.setEditable(false);
        }
        for (int i = 0; i < elementsVisible.size(); i++) {
            elementsVisible.get(i).setText(elementData.getString(i+1));
        }
    }

    /**
     * onHelp
     *
     * Handles the requesting of help by using the HelpHandler to call startHelp
     * @param event user has clicked on the help button
     */
    public void onHelp(ActionEvent event) {
        System.out.println("Help requested: " + event);

        Node e = (Node) event.getSource();
        Scene scene = e.getScene();
        HelpHandler.startHelp(scene);
    }


    /**
     * selectFolder
     *
     * Gets input from the user on which file data should be downloaded to
     * @param event user has clicked on an download button
     * @return - File the file the user has created to download to
     */
    public File selectFolder(ActionEvent event) {
        dataExporter = new DataExporter();

        FileChooser fileChooser = new FileChooser();

        // Sets the types of files that are allowed
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Text Documents (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        extensionFilter = new FileChooser.ExtensionFilter("CSV (Comma-Separated Values) (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extensionFilter);

        return fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());
    }


    /**
     * convertCSStringToArrayList
     *
     * Converts a comma seperated string to an arraylist of strings
     * @param string
     * @return ArrayList of String
     */
    public ArrayList<String> convertCSStringToArrayList(String string) {
        String[] list = string.split(",");
        ArrayList<String> newList = convertToArrayList(list);
        for (int i = 0; i < newList.size(); i++) {
            newList.set(i, newList.get(i).trim());
            if (newList.get(i).equals("")) {
                newList.remove(i--);
            }
        }
        if (newList.isEmpty()) {
            newList = null;
        }
        return newList;
    }


    /**
     * convertToArrayList
     *
     * Converts a primitive array of strings to an arraylist
     * @param list
     * @return ArrayList of String
     */
    public ArrayList<String> convertToArrayList(String[] list) {
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, list);
        return result;
    }


    /**
     * setElementsEditable
     *
     * Sets the given TextFields to be editable or not (depending on given bool)
     * @param bool - boolean
     */
    public void setElementsEditable(ArrayList<TextField> elementsEditable, Boolean bool) {
        for (TextField field : elementsEditable) {
            field.setEditable(bool);
        }
    }

}
