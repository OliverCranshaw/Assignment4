package seng202.team5.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class SingleRecordAirlineController {
    public SingleRecordAirlineController() {}

    @FXML
    private Text airlineID;

    @FXML
    private Text airlineName;

    @FXML
    private Text airlineIATA;

    @FXML
    private Text airlineICAO;

    @FXML
    private Text airlineCallsign;

    @FXML
    private Text airlineCountry;

    @FXML
    private Text airlineActive;

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