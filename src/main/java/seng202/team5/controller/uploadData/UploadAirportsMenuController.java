package seng202.team5.controller.uploadData;

import seng202.team5.data.ReadFile;

import java.io.File;
import java.util.ArrayList;

/**
 * UploadAirportsMenuController
 *
 * Contains the method doUploadOperation which reads airports from a given file.
 */
public class UploadAirportsMenuController extends BaseUploadMenuController {

    /**
     * Reads airports from the given file and returns any error messages.
     *
     * @param readFile The file reader.
     * @param file The file to be read.
     * @return The string of error messages returned from reading the file.
     */
    @Override
    protected String doUploadOperation(ReadFile readFile, File file) {
        // Reads the file
        ArrayList<Object> readResult = readFile.readAirportData(file);

        if (readResult == null) return null;

        // Gets the list of errors returned from reading the file
        ArrayList<String> errors = (ArrayList<String>)(readResult.get(1));
        String errorString = "";

        // If there are errors, iterates through the list and adds each of them to a string
        // Otherwise sets the string to say that all the airports were added successfully
        if (!errors.isEmpty()) {
            for (String error : errors) {
                errorString += error + "\n";
            }
            errorString += "Any other airports added successfully";
        } else {
            errorString = "All airports added successfully";
        }

        return errorString;
    }
}