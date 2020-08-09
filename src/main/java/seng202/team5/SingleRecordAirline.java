package seng202.team5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SingleRecordAirline extends Application {



    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("airline_single_record.fxml"));
        stage.setTitle("Airline Record Viewer");
        stage.setScene(new Scene(root, 640, 480));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}