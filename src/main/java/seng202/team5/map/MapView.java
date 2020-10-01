package seng202.team5.map;

import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.util.Pair;
import netscape.javascript.JSObject;
import seng202.team5.App;

import java.net.URL;
import java.util.*;
import java.util.function.Consumer;

/**
 * A widget that shows an interactive map.
 *
 * Note that before using methods such as "setCentre", "setZoom", "addMarker", etc the
 * underlying WebView needs to be loaded. To ensure this please use the "addLoadListener" method.
 */
public class MapView extends VBox {
    private WebView webView = new WebView();
    private Bridge bridge = new Bridge();

    /**
     * The MapView constructor
     */
    public MapView() {
        addLoadListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                // New page has loaded, process:

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
     * @param icon Icon to use, null for default
     * @return The created marker ID
     */
    public int addMarker(Coord coord, String name, MarkerIcon icon) {
        return addMarker(coord.latitude, coord.longitude, name, icon);
    }

    /**
     * Adds a marker to the map with the given name, icon and coordinate
     *
     * @param latitude New marker latitude
     * @param longitude New marker longitude
     * @param name New marker name
     * @param icon Icon to use, null for default
     * @return The created marker ID
     */
    public int addMarker(double latitude, double longitude, String name, MarkerIcon icon) {
        return (int) callFunction("addMarker", latitude, longitude, name, icon);
    }

    /**
     * Removes a marker from the map with the given marker ID
     *
     * @param markerID The marker ID to remove
     */
    public void removeMarker(int markerID) {
        callFunction("removeMarker", markerID);
        setMarkerListener(markerID, null);
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
     * Creates a line on the map representing a path through the given points with symbols along it.
     *
     * Symbols are represent as a proportion between 0 and 1 and a symbol name.
     * Valid symbol names are:
     *  - BACKWARD_CLOSED_ARROW
     *  - BACKWARD_OPEN_ARROW
     *  - CIRCLE
     *  - FORWARD_CLOSED_ARROW
     *  - FORWARD_OPEN_ARROW
     *
     * @param points The list of points for the new path
     * @param symbols List of symbols along the path, null is interpreted as no symbols
     * @param colour Valid CSS3 colour string (except named colours) for the path, null for default colour
     * @param strokeWeight Width of the path and symbols
     * @return The created path ID
     */
    public int addPath(List<Coord> points, List<Pair<Double, String>> symbols, String colour, double strokeWeight) {
        if (points.size() < 2) {
            throw new RuntimeException("Too few points to define a path");
        }
        if (symbols == null) symbols = List.of();
        if (colour == null) colour = "#FF0000";

        Set<String> validSymbols = new HashSet<>(List.of(
                "BACKWARD_CLOSED_ARROW",
                "BACKWARD_OPEN_ARROW",
                "CIRCLE",
                "FORWARD_CLOSED_ARROW",
                "FORWARD_OPEN_ARROW"
        ));
        for (Pair<Double,String> symbol : symbols) {
            if (!validSymbols.contains(symbol.getValue())) {
                throw new RuntimeException("Invalid symbol name" + symbol.getValue());
            }
        }

        return (int) callFunction("addPath", points, symbols, colour, strokeWeight);
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
        } else if (object instanceof MarkerIcon) {
            MarkerIcon icon = (MarkerIcon) object;
            return String.format("{url:%s,anchor:{x:%f,y:%f}}", convertToJSRepresentation(icon.imageURL), icon.anchorX, icon.anchorY);
        } else if (object instanceof Pair) {
            Pair<?,?> pair = (Pair<?,?>) object;
            return String.format("{first:%s,second:%s}", convertToJSRepresentation(pair.getKey()), convertToJSRepresentation(pair.getValue()));
        } else if (object == null) {
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
