package seng202.team5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import java.io.IOException;


/**
 * Le main class
 *
 */
public class App extends Application {


    @Override
    public void start(Stage mainStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        mainStage.setTitle("Flight Data Manager");
        mainStage.setScene(new Scene(root, 1280, 720));
        mainStage.show();
    }

    public static void launchAddAirportStage() throws IOException {

        Stage addAirportStage = new Stage();

        Parent root = FXMLLoader.load(App.class.getResource("add_airport.fxml"));
        addAirportStage.setTitle("Add Airport");
        addAirportStage.setScene(new Scene(root, 1280, 720));

        addAirportStage.show();

    }


    public static void launchAddAirlineStage() throws IOException {

        Stage addAirlineStage = new Stage();
        Parent root = FXMLLoader.load(App.class.getResource("add_airline.fxml"));
        addAirlineStage.setTitle("Add Airport");
        addAirlineStage.setScene(new Scene(root, 1280, 720));

        addAirlineStage.show();
    }



    public static void launchAddRouteStage() throws IOException {

        Stage addRouteStage = new Stage();
        Parent root = FXMLLoader.load(App.class.getResource("add_route.fxml"));
        addRouteStage.setTitle("Add Airport");
        addRouteStage.setScene(new Scene(root, 1280, 720));

        addRouteStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}