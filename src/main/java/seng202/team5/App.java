package seng202.team5;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * Le main class
 *
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        Button button = new Button();
        button.setText("CLICK ME");

        StackPane layout = new StackPane();

        layout.getChildren().add(l);
        layout.getChildren().add(button);

        Scene scene = new Scene(layout, 640, 480);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        SingleRecordAirline.main(args);
    }

}