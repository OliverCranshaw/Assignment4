package seng202.team5.map;

import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import jdk.jshell.spi.ExecutionControl;
import netscape.javascript.JSObject;
import seng202.team5.App;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A widget that shows an interactive map.
 *
 * Note that before using methods such as "setCentre", "setZoom", "addMarker", etc the
 * underlying WebView needs to be loaded. To ensure this please use the "addLoadListener" method
 *
 * @author Nathan Smithies
 */
public class MapView extends VBox {
    private WebView webView;
    private Bridge bridge = new Bridge();

    /**
     * The MapView constructor
     *
     * @throws IOException If the "map.html" file is not found
     */
    public MapView() throws IOException {
        webView = new WebView();


        addLoadListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                // new page has loaded, process:

                JSObject window = (JSObject) webView.getEngine().executeScript("window");
                window.setMember("bridge", bridge);
            }
        });

        URL path = App.class.getResource("map.html");
        webView.getEngine().load(path.toString());

        webView.prefHeightProperty().bind(this.heightProperty());
        webView.prefWidthProperty().bind(this.widthProperty());

        this.getChildren().add(webView);
    }

    /**
     * Add a listener for changes in the underlying WebView load state
     * If this widget isn't loaded then "setCentre", "setZoom", "addMarker", etc will not work.
     *
     * @param listener The event listener to add
     */
    public void addLoadListener(ChangeListener<Worker.State> listener) {
        webView.getEngine().getLoadWorker().stateProperty().addListener(listener);
    }

    /**
     * Centres the map to the given coordinate.
     *
     * @param coord New centre coordinate
     */
    public void setCentre(Coord coord) {
        setCentre(coord.latitude, coord.longitude);
    }

    /**
     * Centres the map to the given coordinate.
     *
     * @param latitude New centre latitude
     * @param longitude New centre longitude
     */
    public void setCentre(double latitude, double longitude) {
        callFunction("setCentre", latitude, longitude);
    }

    /**
     * Sets the map zoom level to the given value.
     *
     * The scale levels according to the google maps documentation:
     * 1: World
     * 5: Landmass/continent
     * 10: City
     * 15: Streets
     * 20: Buildings
     *
     * @param level New zoom level
     */
    public void setZoom(int level) {
        callFunction("setZoom", level);
    }

    /**
     * Ensures that this map view will fit the given bounds
     *
     * @param bounds Bounds to fit to
     * @param padding Extra padding to the given bounds
     */
    public void fitBounds(Bounds bounds, double padding) { callFunction("fitBounds", bounds, padding); }

    /**
     * Adds a marker to the map with the given name and coordinate
     *
     * @param coord New marker coordinate
     * @param name New marker name
     * @return The created marker ID
     */
    public int addMarker(Coord coord, String name, String iconName) {
        return addMarker(coord.latitude, coord.longitude, name, iconName);
    }

    /**
     * Adds a marker to the map with the given name, icon and coordinate
     *
     * @param latitude New marker latitude
     * @param longitude New marker longitude
     * @param name New marker name
     * @param iconName Icon name to use, null for default
     * @return The created marker ID
     */
    public int addMarker(double latitude, double longitude, String name, String iconName) {
        return (int) callFunction("addMarker", latitude, longitude, name, iconName);
    }

    /**
     * Removes a marker from the map with the given marker ID
     *
     * @param markerID The marker ID to remove
     */
    public void removeMarker(int markerID) {
        callFunction("removeMarker", markerID);
    }

    /**
     * Sets the listener for when the given marker is clicked.
     * If listener is null then the marker listener is removed.
     *
     * @param markerID Marker to listen for
     * @param listener new listener for the given marker
     */
    public void setMarkerListener(int markerID, Consumer<Integer> listener) {
        bridge.markerListeners.put(markerID, listener);
    }

    /**
     * Creates a line on the map representing a path through the given points
     *
     * @param points The list of points for the new path
     * @return The create path ID
     */
    public int addPath(List<Coord> points) {
        if (points.size() < 2) {
            throw new RuntimeException("Too few points to define a path");
        }
        return (int) callFunction("addPath", points);
    }

    /**
     * Removes a path on the map with the given path ID
     *
     * @param pathID The path ID to remove
     */
    public void removePath(int pathID) {
        callFunction("removePath", pathID);
    }

    private String convertToJSRepresentation(Object object) throws RuntimeException {
        if (object instanceof Coord) {
            Coord coord = (Coord)object;
            return String.format("{lat:%f,lng:%f}", coord.latitude, coord.longitude);
        } else if (object instanceof Bounds) {
            Bounds bounds = (Bounds) object;
            return String.format("{south:%f,west:%f,north:%f,east:%f}", bounds.southwest.latitude, bounds.southwest.longitude, bounds.northeast.latitude, bounds.northeast.longitude);
        } if (object == null) {
            return "null";
        } else if (object instanceof String) {
            // Removes special characters
            String cleaned = ((String) object).replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
            return "\"" + cleaned + "\"";
        } else if (object instanceof Number) {
            return object.toString();
        } else if (object instanceof Collection) {
            List<String> components = new ArrayList<>();
            for (Object child : (Collection<?>)object) {
                components.add(convertToJSRepresentation(child));
            }
            return "[" + String.join(",", components) + "]";
        } else {
            throw new RuntimeException("Unsupported object type: " + object.getClass());
        }
    }

    private Object callFunction(String functionName, Object... arguments) {
        List<String> stringified = new ArrayList<>();
        for (Object argument : arguments) {
            stringified.add(convertToJSRepresentation(argument));
        }
        return webView.getEngine().executeScript(functionName + "(" + String.join(",", stringified) + ");");
    }

    // Inner class has to be public due to javascript being unable to call private inner classes
    public static class Bridge {
        private final Map<Integer, Consumer<Integer>> markerListeners = new HashMap<>();

        public void notifyMarkerClicked(int markerID) {
            Consumer<Integer> listener = markerListeners.get(markerID);
            if (listener != null) {
                listener.accept(markerID);
            }
        }

        public void log(String text) {
            System.out.println(text);
        }
    }
}
