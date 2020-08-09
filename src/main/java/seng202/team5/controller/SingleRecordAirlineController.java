package seng202.team5.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class SingleRecordAirlineController {
    public SingleRecordAirlineController() {}

    @FXML
    private Text IDLabel;

    @FXML
    private Text NameLabel;

    @FXML
    private Text IATALabel;

    @FXML
    private Text ICAOLabel;

    @FXML
    private Text CallsignLabel;

    @FXML
    private Text CountryLabel;

    @FXML
    private Text ActiveLabel;

    @FXML
    public void modifyButton() {
        System.out.println("Modify!");
    }

    @FXML
    public void downloadButton() {
        System.out.println("Download!");
    }

    @FXML
    public void backButton() {
        System.out.println("Back!");
    }

    @FXML
    public void helpButton() {
        System.out.println("Help!");
    }



}