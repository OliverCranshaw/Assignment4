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
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        stage.setTitle("Flight Data Manager");
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }

}