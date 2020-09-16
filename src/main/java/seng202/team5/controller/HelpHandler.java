package seng202.team5.controller;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import seng202.team5.App;

import java.io.IOException;

public class HelpHandler implements EventHandler<MouseEvent> {
    private static final String CURSOR_NAME = App.class.getResource("help_cursor.png").toString();

    public static void startHelp(Scene scene) {
        scene.setCursor(Cursor.cursor(CURSOR_NAME));

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
        if (helpStage != null) {
            helpStage.close();
            endHelp();
            return;
        }

        if (mouseEvent.isSecondaryButtonDown()) {
            endHelp();
            return;
        }

        // Prevents the event from being process further
        mouseEvent.consume();

        PickResult pickResult = mouseEvent.getPickResult();

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
        if (helpText == null) {
            endHelp();
            return;
        }

        System.out.println("Help text is: " + helpText);

        try {
            Bounds bounds = node.getBoundsInLocal();

            showHelpDialog(helpText, node.localToScreen(bounds.getCenterX(), bounds.getHeight()));
        } catch (IOException ignored) {
        }

        scene.setCursor(null);
    }

    private void showHelpDialog(String helpText, Point2D centre) throws IOException {
        helpStage = new Stage(StageStyle.UNDECORATED);
        VBox root = new VBox();
        root.setPadding(new Insets(8));

        root.getChildren().add(new Label(helpText));

        helpStage.setScene(new Scene(root));
        helpStage.setWidth(300);


        helpStage.setX(centre.getX() - 150);
        helpStage.setY(centre.getY() + 2);



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
