package seng202.team5.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.team5.App;

import java.io.IOException;

public class MainMenuController {
    @FXML
    private TabPane mainTabs;

    @FXML
    private Tab tableRecordsTab;

    @FXML
    private Tab generalTab;

    @FXML
    public void onViewMorePressed() {
        mainTabs.getSelectionModel().select(tableRecordsTab);
    }

    @FXML
    public void onBackPressed() {
        mainTabs.getSelectionModel().select(generalTab);
    }
    @FXML
    public void onAddAirportPressed(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(App.class.getResource("add_airport.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Add Airport");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
        stage.show();
    }

    @FXML
    public void onAddAirlinePressed(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(App.class.getResource("add_airline.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Add Airline");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
        stage.show();
    }

    @FXML
    public void onAddRoutePressed(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(App.class.getResource("add_route.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Add Route");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
        stage.show();
    }
}
