package seng202.team5;

import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import seng202.team5.map.Bounds;
import seng202.team5.map.Coord;
import seng202.team5.map.MapView;
import seng202.team5.map.MarkerIcon;

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

                List<Coord> path = List.of(new Coord(0, 0), new Coord(20, 20), new Coord(-10, 100));

                int path0 = mapView.addPath(path, List.of(new Pair<>(0.0, "FORWARD_OPEN_ARROW"), new Pair<>(1.0, "FORWARD_OPEN_ARROW")));

                mapView.fitBounds(Bounds.fromCoordinateList(path), 0.0);


                int marker0 = mapView.addMarker(new Coord(40.0, 20.0), "Hello world", MarkerIcon.PLANE_ICON);
                mapView.setMarkerListener(marker0, (Integer markerID) -> {
                    System.out.println("Marker " + markerID + " clicked!");
                });
            }
        });

        mainStage.setTitle("Flight Data Manager");
        mainStage.setScene(new Scene(mapView, 1280, 720));
        mainStage.show();


    }

    public static void main(String args[]) { launch(args); }
}
