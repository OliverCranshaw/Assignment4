package seng202.team5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng202.team5.database.DBInitializer;

import java.io.IOException;

/**
 * Main class that activates the GUI and creates the database.
 */
public class App extends Application {

    private DBInitializer dbInitializer;

    @Override
    public void start(Stage mainStage) throws IOException {

        dbInitializer = new DBInitializer();
        dbInitializer.createNewDatabase("flightdata.db");

        setUserAgentStylesheet(STYLESHEET_MODENA);

        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        mainStage.setTitle("Flight Data Manager");
        mainStage.setScene(new Scene(root, 1280, 720));

        mainStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}