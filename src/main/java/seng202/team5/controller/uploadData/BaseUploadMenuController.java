package seng202.team5.controller.uploadData;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import seng202.team5.data.ReadFile;

import java.io.File;

public abstract class BaseUploadMenuController {

    private Service<Void> service = new Service<>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @Override
                protected Void call() {
                    ReadFile readFile = new ReadFile();

                    final long totalSize = chosenFile.length();
                    readFile.setLineChangeListener((bytesRead) -> {
                        updateProgress(bytesRead, totalSize);

                        if (isCancelled()) {
                            readFile.cancel();
                        }
                    });

                    String resultText = doUploadOperation(readFile, chosenFile);

                    // Ensures text is set from the application thread
                    Platform.runLater(() -> errorList.setText(resultText));
                    return null;
                }
            };
        }
    };

    @FXML
    private Text errorList;

    @FXML
    private ProgressBar progressBar;

    private File chosenFile = null;

    @FXML
    public void initialize() {
        progressBar.progressProperty().bind(service.progressProperty());
    }

    /**
     * Must be called after this window is shown.
     *
     * @param scene Scene that the controller is within.
     */
    public final void onShown(Scene scene) {
        EventHandler<WorkerStateEvent> onTaskDone = e -> {
            scene.setCursor(Cursor.DEFAULT);
            service.reset();
            progressBar.setVisible(false);
        };

        service.setOnCancelled(onTaskDone);
        service.setOnFailed(onTaskDone);
        service.setOnSucceeded(onTaskDone);

        scene.getWindow().setOnCloseRequest(e -> {
            if (service.isRunning()) {
                e.consume();


                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.WARNING, "Do you wish to stop this operation?", yes, no);
                alert.setTitle("Data loading in progress");
                alert.setHeaderText("Data is currently being loaded into the database\nCancelling this operation will result in data being partially added to the database");

                alert.showAndWait().ifPresent(rs -> {
                    if (rs.equals(yes)) {
                        service.cancel();
                        scene.getWindow().hide();
                    }
                });
            }
        });
    }

    /**
     * Opens the Open File window, and then begins the file upload when a file has been selected.
     *
     * @param event The select file button has been pressed.
     */
    @FXML
    public void onSelectFilePressed(ActionEvent event) {
        // Don't proceed if another upload is already in progress
        if (service.isRunning()) return;

        FileChooser fileChooser = new FileChooser();
        // Sets the types of files users are allowed to select
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Text Files (*.txt, *.csv)", "*.txt", "*.csv");
        fileChooser.getExtensionFilters().add(extensionFilter);
        // Opens the FileChooser Open window
        chosenFile = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());

        if (chosenFile != null) {
            // Sets the progress bar to be visible, changes the cursor to a waiting one, and then begins the file upload
            progressBar.setVisible(true);
            ((Node)event.getSource()).getScene().setCursor(Cursor.WAIT);

            service.start();
        }
    }
    /**
     * Reads the given file and returns any error messages.
     *
     * @param readFile The file reader.
     * @param file The file to be read.
     * @return The string of error messages returned from reading the file.
     */
    protected abstract String doUploadOperation(ReadFile readFile, File file);
}
