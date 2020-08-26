package seng202.team5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AddAirportMenu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("add_airport.fxml"));
        } catch (IOException e) {
            return;
        }

        stage.setTitle("Add Airport");
        stage.setScene(new Scene(root, 550, 275));
        stage.show();
    }
}
