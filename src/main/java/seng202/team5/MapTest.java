package seng202.team5;

import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng202.team5.map.Coord;
import seng202.team5.map.MapView;

import java.util.List;

/**
 * Temporary application for testing the MapView widget
 */
public class MapTest extends Application {
    @Override
    public void start(Stage mainStage) throws Exception {
        MapView mapView = new MapView();

        mapView.addLoadListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                // new page has loaded, process:
                mapView.setCentre(5, 0);
                mapView.setZoom(4);

                int marker0 = mapView.addMarker(0, 0, "Test thingy");
                int marker1 = mapView.addMarker(10, 0, "Test thingy2");

                int path0 = mapView.addPath(List.of(new Coord(0, 0), new Coord(20, 20), new Coord(-10, 100)));
                int path1 = mapView.addPath(List.of(new Coord(-10, -10), new Coord(-10, 5)));
            }
        });

        mainStage.setTitle("Flight Data Manager");
        mainStage.setScene(new Scene(mapView, 1280, 720));
        mainStage.show();


    }

    public static void main(String args[]) { launch(args); }
}
