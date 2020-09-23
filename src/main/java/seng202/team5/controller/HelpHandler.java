package seng202.team5.controller;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import seng202.team5.App;

import java.io.IOException;

/**
 * Class for initiating help sessions.
 * Its only public interface is the startHelp static method.
 */
public class HelpHandler implements EventHandler<MouseEvent> {
    private static final Image CURSOR_IMAGE = new Image(App.class.getResource("help_cursor.png").toString());

    /**
     * Starts the help session on the given scene.
     * This will change the cursor to a question mark and block inputs from being passed into this window.
     * @param scene The window where the help is requested
     */
    public static void startHelp(Scene scene) {
        scene.setCursor(new ImageCursor(CURSOR_IMAGE, CURSOR_IMAGE.getWidth() * (1.0/3.0), CURSOR_IMAGE.getHeight() * (1.0/3.0)));

        HelpHandler helpHandler = new HelpHandler(scene);
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, helpHandler);
    }

    private final Scene scene;
    private Stage helpStage = null;

    private HelpHandler(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        // If the helpStage exists then we must being showing the help dialog. So when we click we can cancel the help
        if (helpStage != null) {
            helpStage.close();
            endHelp();
            return;
        }

        // Right click to cancel the help
        if (mouseEvent.isSecondaryButtonDown()) {
            endHelp();
            return;
        }

        // Prevents the event from being process further. As in it blocks input
        mouseEvent.consume();

        PickResult pickResult = mouseEvent.getPickResult();

        // Search the parent chain to find if there exists any help
        String helpText = null;
        Node node = pickResult.getIntersectedNode();
        while (node != null) {
            Object current = node.getProperties().get("helpText");
            if (current instanceof String) {
                helpText = (String)current;
                break;
            }
            node = node.getParent();
        }

        try {
            if (helpText == null) {
                showHelpDialog("No help available at this location", new Point2D(mouseEvent.getScreenX(), mouseEvent.getScreenY()));
            } else {
                Bounds bounds = node.getBoundsInLocal();
                showHelpDialog(helpText, node.localToScreen(bounds.getWidth() / 2, bounds.getHeight()));
            }
        } catch (IOException ignored) {
        }

        scene.setCursor(null);
    }

    private void showHelpDialog(String helpText, Point2D centre) throws IOException {
        helpStage = new Stage(StageStyle.UNDECORATED);
        VBox root = new VBox();
        root.setPadding(new Insets(8));
        root.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        root.maxHeight(Double.POSITIVE_INFINITY);

        Text label = new Text(helpText);
        label.setWrappingWidth(300);
        label.minWidth(300.0);
        label.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().add(label);

        VBox.setVgrow(label, Priority.ALWAYS);

        helpStage.setScene(new Scene(root));
        helpStage.setWidth(316);

        helpStage.setX(centre.getX() - 158);
        helpStage.setY(centre.getY() + 2);

        // Event handle for closing the help dialog by clicking on it
        helpStage.addEventFilter(MouseEvent.MOUSE_PRESSED, newEvent -> {
            helpStage.close();
            endHelp();
        });
        helpStage.initOwner(scene.getWindow());

        helpStage.show();
    }

    private void endHelp() {
        scene.setCursor(null);
        scene.removeEventFilter(MouseEvent.MOUSE_PRESSED, this);
    }
}
