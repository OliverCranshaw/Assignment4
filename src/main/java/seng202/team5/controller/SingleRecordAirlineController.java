package seng202.team5.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import seng202.team5.service.AirlineService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SingleRecordAirlineController {
    private AirlineService service = new AirlineService();
    private int ID = -1;

    public SingleRecordAirlineController() {}

    @FXML
    private Text airlineID;

    @FXML
    private Text airlineName;

    @FXML
    private Text airlineAlias;

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

    public void setID(int ID) {
        this.ID = ID;
        update();
    }

    private void update() {
        assert ID != -1;

        ResultSet resultSet = service.getData(ID);

        try {
            airlineID.setText(resultSet.getString(2));
            airlineName.setText(resultSet.getString(3));
            airlineIATA.setText(resultSet.getString(4));
            airlineICAO.setText(resultSet.getString(5));
            airlineCallsign.setText(resultSet.getString(6));
            airlineCountry.setText(resultSet.getString(7));
            airlineActive.setText(resultSet.getString(8));
        } catch (SQLException e) {
        }
    }
}