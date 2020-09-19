package seng202.team5.data;

import seng202.team5.service.FlightService;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * ReadFile
 *
 * Reads a given file line by line, splits the entries to pass into ConcreteAddData methods.
 */
public class ReadFile {

    private BufferedReader bufferedReader;
    private String line;
    private ArrayList<String> splitLine;
    private final ConcreteAddData concreteAddData;
    private final ConcreteDeleteData concreteDeleteData;
    private final FlightService flightService;

    /**
     * Constructor for ReadFile.
     * Initializes FlightService, ConcreteAddData, and ConcreteDeleteData.
     */
    public ReadFile() {
        concreteAddData = new ConcreteAddData();
        concreteDeleteData = new ConcreteDeleteData();
        flightService = new FlightService();
    }

    /**
     * Gets the input file, creates a FileReader with it, and then creates a BufferedReader with the new FileReader.
     * If any of this doesn't work, prints an error message.
     *
     * @param file The input file to be read.
     */
    public void getFile(File file) {
        try {
            FileReader fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to get file.");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Removes any quotation marks from a given string.
     *
     * @param string The input string that you want to remove quotation marks from.
     * @return String The new string without quotation marks.
     */
    public String removeQuotes(String string) {
        return string.replaceAll("\"", "");
    }

    /**
     * Splits a given string into multiple strings wherever there is a comma, then removes any quotation marks from each of them.
     *
     * @param line The line that is to be split into separate parts.
     * @return ArrayList<String> splitline The list of strings from the line that has been split and have had any quotation marks removed from them.
     */
    public ArrayList<String> getEntries(String line) {
        // Splits the given line at every comma into an ArrayList of strings
        splitLine = new ArrayList<>(Arrays.asList(line.split(",", -1)));
        // Iterates through the strings in the ArrayList and removes the quotation marks from each of them
        for (int i = 0; i < splitLine.size(); i++) {
            splitLine.set(i, removeQuotes(splitLine.get(i)));
        }

        return splitLine;
    }

    /**
     * Takes a given file, presumably one containing airline data, reads it,
     * and then passes the data from each line into the addAirline method of ConcreteAddData.
     *
     * @param file The input file to be read.
     * @return int id The airline_id of the new airline created by ConcreteAddData.
     */
    public ArrayList<Object> readAirlineData(File file) {
        int id = 0;
        // Calls the getFile method with the input file to create a BufferedReader to read the file
        getFile(file);

        int lineNum = 1;
        ArrayList<String> errors = new ArrayList<>();
        String error;

        try {
            // Reads each line in the file
            while ((line = bufferedReader.readLine()) != null) {
                // Splits the line into individual strings
                splitLine = getEntries(line);
                // Checks that the airline data has the right amount of entries
                if (splitLine.size() > 8) {
                    id = -3;
                    error = "Line " + lineNum + ": Airline data in wrong format, too many entries.";
                    errors.add(error);
                    System.out.println(error);
                }
                else if (splitLine.size() < 7) {
                    id = -2;
                    error = "Line " + lineNum + ": Airline data in wrong format, too few entries.";
                    errors.add(error);
                    System.out.println(error);
                }
                else {
                    // If the airline data contains an airline_id already, removes it
                    if (splitLine.size() == 8) {
                        splitLine.remove(0);
                    }
                    // Passes the parameters into the addAirline method of ConcreteAddData
                    id = concreteAddData.addAirline(splitLine.get(0), splitLine.get(1), splitLine.get(2), splitLine.get(3),
                            splitLine.get(4), splitLine.get(5), splitLine.get(6).toUpperCase());
                    System.out.println(id);

                    switch(id) {
                        case -1:
                            error = "Line " + lineNum + ": Error adding airline, IATA or ICAO code may already exist in database.";
                            errors.add(error);
                            break;
                        case -2:
                            error = "Line " + lineNum + ": Invalid name.";
                            errors.add(error);
                            break;
                        case -3:
                            error = "Line " + lineNum + ": Invalid IATA code.";
                            errors.add(error);
                            break;
                        case -4:
                            error = "Line " + lineNum + ": Invalid ICAO code.";
                            errors.add(error);
                            break;
                        case -5:
                            error = "Line " + lineNum + ": Invalid active.";
                            errors.add(error);
                            break;
                    }
                }
                lineNum++;
            }
            bufferedReader.close();
        } catch (IOException e) {
            // If any of the above fails, prints out an error message
            System.out.println("Unable to read file.");
            System.out.println(e.getMessage());
        }

        return new ArrayList<>(Arrays.asList(id, errors));
    }

    /**
     * Takes a given file, presumably one containing airport data, reads it,
     * and then passes the data from each line into the addAirport method of ConcreteAddData.
     *
     * @param file The input file to be read.
     * @return int id The airport_id of the new airport created by ConcreteAddData.
     */
    public ArrayList<Object> readAirportData(File file) {
        int id = -1;
        // Calls the getFile method with the input file to create a BufferedReader to read the file
        getFile(file);

        int lineNum = 1;
        ArrayList<String> errors = new ArrayList<>();
        String error;

        try {
            // Reads each line in the file
            while ((line = bufferedReader.readLine()) != null) {
                // Splits the line into individual strings
                splitLine = getEntries(line);
                // Checks that the airport data has the right amount of entries
                if (splitLine.size() > 12) {
                    id = -3;
                    error = "Line " + lineNum +": Airport data in wrong format, too many entries.";
                    errors.add(error);
                    System.out.println(error);
                }
                else if (splitLine.size() < 11) {
                    id = -2;
                    error = "Line " + lineNum +": Airport data in wrong format, too few entries.";
                    errors.add(error);
                    System.out.println(error);
                }
                else {
                    // If the airport data contains an airport_id already, removes it
                    if (splitLine.size() == 12) {
                        splitLine.remove(0);
                    }
                    // Passes the parameters into the addAirport method of ConcreteAddData
                    id = concreteAddData.addAirport(splitLine.get(0), splitLine.get(1), splitLine.get(2), splitLine.get(3),
                            splitLine.get(4), splitLine.get(5), splitLine.get(6), splitLine.get(7),
                            splitLine.get(8), splitLine.get(9), splitLine.get(10));
                    System.out.println(id);

                    switch(id) {
                        case -1:
                            error = "Line " + lineNum + ": Error adding airport, IATA or ICAO code may already exist in database.";
                            errors.add(error);
                            break;
                        case -2:
                            error = "Line " + lineNum + ": Invalid name.";
                            errors.add(error);
                            break;
                        case -3:
                            error = "Line " + lineNum + ": Invalid city.";
                            errors.add(error);
                            break;
                        case -4:
                            error = "Line " + lineNum + ": Invalid country.";
                            errors.add(error);
                            break;
                        case -5:
                            error = "Line " + lineNum + ": Invalid IATA code.";
                            errors.add(error);
                            break;
                        case -6:
                            error = "Line " + lineNum + ": Invalid ICAO code.";
                            errors.add(error);
                            break;
                        case -7:
                            error = "Line " + lineNum + ": Invalid latitude.";
                            errors.add(error);
                            break;
                        case -8:
                            error = "Line " + lineNum + ": Invalid longitude.";
                            errors.add(error);
                            break;
                        case -9:
                            error = "Line " + lineNum + ": Invalid altitude.";
                            errors.add(error);
                            break;
                        case -10:
                            error = "Line " + lineNum + ": Invalid timezone.";
                            errors.add(error);
                            break;
                        case -11:
                            error = "Line " + lineNum + ": Invalid DST.";
                            errors.add(error);
                            break;
                        case -12:
                            error = "Line " + lineNum + ": Invalid TZ-formatted timezone.";
                            errors.add(error);
                            break;
                    }
                }
                lineNum++;
            }
            bufferedReader.close();
        } catch (IOException e) {
            // If any of the above fails, prints out an error message
            System.out.println("Unable to read file.");
            System.out.println(e.getMessage());
        }

        return new ArrayList<>(Arrays.asList(id, errors));
    }

    /**
     * Takes a given file, presumably one containing the data of a single flight, reads it,
     * and then passes the data from each line into the addFlightEntry method of ConcreteAddData.
     *
     * @param file The input file to be read.
     * @return ArrayList<Integer> ids An ArrayList containing the flight_id and the unique id of the new flight entry created by ConcreteAddData.
     */
    public ArrayList<Object> readFlightData(File file) {
        int flightID = -1;
        int id = -1;
        // Calls the getFile method with the input file to create a BufferedReader to read the file
        getFile(file);

        int lineNum = 1;
        ArrayList<String> errors = new ArrayList<>();
        String error;

        try {
            // Gets the next available flight_id
            // This will be the flight_id of the entries read from the file
            flightID = flightService.getNextFlightID();

            // Reads each line in the file
            while ((line = bufferedReader.readLine()) != null) {
                // Splits the line into individual strings
                splitLine = getEntries(line);
                // Checks that the flight entry has the right number of entries
                // If it doesn't then deletes all previously added entries, rejects the data, and informs the user of the problem
                if (splitLine.size() != 5) {
                    error = "Line " + lineNum +": Flight entry in the wrong format, does not have 5 entries. Flight could not be added.";
                    System.out.println(error);
                    errors.add(error);

                    concreteDeleteData.deleteFlight(flightID);
                    flightID = -1;
                    id = -1;
                    break;
                }
                // Passes the parameters into the addFlightEntry method of ConcreteAddData
                id = concreteAddData.addFlightEntry(flightID, splitLine.get(0), splitLine.get(1), splitLine.get(2), splitLine.get(3), splitLine.get(4));

                if (id < 0) {
                    switch(id) {
                        case -1:
                            error = "Line " + lineNum + ": Error adding flight entry, may be duplicate flight entry.";
                            errors.add(error);
                            break;
                        case -2:
                            error = "Line " + lineNum + ": Invalid flight ID.";
                            errors.add(error);
                            break;
                        case -3:
                            error = "Line " + lineNum + ": Invalid location type.";
                            errors.add(error);
                            break;
                        case -4:
                            error = "Line " + lineNum + ": Invalid location. If location type is APT, then airport does not exist in database.";
                            errors.add(error);
                            break;
                        case -5:
                            error = "Line " + lineNum + ": Invalid altitude.";
                            errors.add(error);
                            break;
                        case -6:
                            error = "Line " + lineNum + ": Invalid latitude.";
                            errors.add(error);
                            break;
                        case -7:
                            error = "Line " + lineNum + ": Invalid longitude.";
                            errors.add(error);
                            break;
                    }

                    concreteDeleteData.deleteFlight(flightID);
                    flightID = -1;
                    id = -1;
                    break;
                }
                lineNum++;
            }
            bufferedReader.close();
        } catch (IOException e) {
            // If any of the above fails, prints out an error message
            System.out.println("Unable to read file.");
            System.out.println(e.getMessage());
        }

        // Creates an ArrayList containing the flightID and unique id of the flight entry that was created
        ArrayList<Integer> ids = new ArrayList<>(Arrays.asList(flightID, id));
        return new ArrayList<>(Arrays.asList(ids, errors));
    }

    /**
     * Takes a given file, presumably one containing route data, reads it,
     * and then passes the data from each line into the addRoute method of ConcreteAddData.
     *
     * @param file The input file to be read.
     * @return int id The route_id of the new route created by ConcreteAddData.
     */
    public ArrayList<Object> readRouteData(File file) {
        int id = -1;
        // Calls the getFile method with the input file to create a BufferedReader to read the file
        getFile(file);

        int lineNum = 1;
        ArrayList<String> errors = new ArrayList<>();
        String error;

        try {
            // Reads each line in the file
            while ((line = bufferedReader.readLine()) != null) {
                // Splits the line into individual strings
                splitLine = getEntries(line);
                // Checks that the route data has the right number of entries
                if (splitLine.size() < 6) {
                    id = -2;
                    error = "Line " + lineNum + ": Route data in wrong format, too few entries.";
                    errors.add(error);
                    System.out.println(error);
                }
                else if (splitLine.size() > 6 && splitLine.size() < 9) {
                    id = -3;
                    error = "Line " + lineNum + ": Route data in wrong format, too many entries/too few entries.";
                    errors.add(error);
                    System.out.println(error);
                }
                else if (splitLine.size() > 9) {
                    id = -4;
                    error = "Line " + lineNum + ": Route data in wrong format, too many entries.";
                    errors.add(error);
                    System.out.println(error);
                }
                else {
                    // If the route data already contained an airline_id, a source airport_id, and a destination airport_id, removes them
                    if (splitLine.size() == 9) {
                        splitLine.remove(1);
                        splitLine.remove(2);
                        splitLine.remove(3);
                    }
                    // Passes the parameters into the addRoute method of ConcreteAddData
                    id = concreteAddData.addRoute(splitLine.get(0), splitLine.get(1), splitLine.get(2), splitLine.get(3), splitLine.get(4), splitLine.get(5));

                    switch(id) {
                        case -1:
                            error = "Line " + lineNum + ": Error adding route, airline or airports may not exist in database or the route may be a duplicate.";
                            errors.add(error);
                            break;
                        case -2:
                            error = "Line " + lineNum + ": Invalid airline code.";
                            errors.add(error);
                            break;
                        case -3:
                            error = "Line " + lineNum + ": Invalid source airport code.";
                            errors.add(error);
                            break;
                        case -4:
                            error = "Line " + lineNum + ": Invalid destination airport code.";
                            errors.add(error);
                            break;
                        case -5:
                            error = "Line " + lineNum + ": Invalid codeshare.";
                            errors.add(error);
                            break;
                        case -6:
                            error = "Line " + lineNum + ": Invalid number of stops.";
                            errors.add(error);
                            break;
                        case -7:
                            error = "Line " + lineNum + ": Invalid equipment.";
                            errors.add(error);
                            break;
                    }
                }
                lineNum++;
            }
            bufferedReader.close();
        } catch (IOException e) {
            // If any of the above fails, prints out an error message
            System.out.println("Unable to read file.");
            System.out.println(e.getMessage());
        }

        return new ArrayList<>(Arrays.asList(id, errors));
    }
}
