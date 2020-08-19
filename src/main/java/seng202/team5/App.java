package seng202.team5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;


/**
 * Le main class
 * comment very good
 */
public class App extends Application {


    @Override
    public void start(Stage mainStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        mainStage.setTitle("Flight Data Manager");
        mainStage.setScene(new Scene(root, 1280, 720));
        mainStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }

}