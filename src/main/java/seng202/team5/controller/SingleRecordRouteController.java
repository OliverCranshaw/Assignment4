package seng202.team5.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class SingleRecordRouteController {

    @FXML
    private Text routeStops;

    @FXML
    private Text routeEquipment;

    @FXML
    private Text routeAirlineCode;

    @FXML
    private Text routeAirlineID;

    @FXML
    private Text routeSourceAirportCode;

    @FXML
    private Text routeSourceAirportID;

    @FXML
    private Text routeDestinationAirportCode;

    @FXML
    private Text routeDestinationAirportID;

    @FXML
    private Text routeCodeshare;



    public void modifyButton() {
        System.out.println("Modify!"); //Stub
    }

    public void downloadButton() {
        System.out.println("Download!"); //Stub
    }

    public void helpButton() {
        System.out.println("Help!");
    }

    public void backButton() {
        System.out.println("Back!");
    }

}
