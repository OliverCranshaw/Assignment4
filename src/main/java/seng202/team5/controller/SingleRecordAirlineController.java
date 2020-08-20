package seng202.team5.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import seng202.team5.model.SingleRecordAirlineModel;

public class SingleRecordAirlineController {
    private SingleRecordAirlineModel model;

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


    public void setModel(SingleRecordAirlineModel model) {
        assert this.model == null;
        this.model = model;

        airlineID.setText(String.valueOf(model.ID));
        airlineName.textProperty().bindBidirectional(model.nameProperty);
        airlineAlias.textProperty().bindBidirectional(model.aliasProperty);
        airlineIATA.textProperty().bindBidirectional(model.iataProperty);
        airlineICAO.textProperty().bindBidirectional(model.icaoProperty);
        airlineCallsign.textProperty().bindBidirectional(model.callsignProperty);
        airlineCountry.textProperty().bindBidirectional(model.countryProperty);
        airlineActive.textProperty().bindBidirectional(model.activeProperty);
    }
}