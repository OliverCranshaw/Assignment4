package seng202.team5.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class SingleRecordAirportController {


    @FXML
    private Text airportDST;

    @FXML
    private Text airportTZDatabaseTime;

    @FXML
    private Text airportType;

    @FXML
    private Text airportSource;

    @FXML
    private Text airportID;

    @FXML
    private Text airportName;

    @FXML
    private Text airportCity;

    @FXML
    private Text airportCountry;

    @FXML
    private Text airportIATA;

    @FXML
    private Text airportICAO;

    @FXML
    private Text airportLatitude;

    @FXML
    private Text airportLongitude;

    @FXML
    private Text airportAltitude;

    @FXML
    private Text airportTimezone;

    @FXML
    public void modifyButton() {
        System.out.println("Modify!");
    }

    @FXML
    public void downloadButton() {
        System.out.println("Download!");
    }

    @FXML
    public void helpButton() {
        System.out.println("Help!");
    }

    @FXML
    public void backButton() {
        System.out.println("Back!");
    }






}
