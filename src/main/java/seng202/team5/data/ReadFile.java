package seng202.team5.data;

import seng202.team5.service.FlightService;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * ReadFile
 *
 * Reads a given file line by line, splits the entries to pass into ConcreteAddData methods.
 *
 * @author Billie Johnson
 */
public class ReadFile {

    private FileReader fileReader;
    private BufferedReader bufferedReader;
    private String line;
    private ArrayList<String> splitLine;
    private ConcreteAddData concreteAddData = new ConcreteAddData();
    private FlightService flightService = new FlightService();

    /**
     * Gets the input file, creates a FileReader with it, and then creates a BufferedReader with the new FileReader.
     * If any of this doesn't work, prints an error message.
     *
     * @param file The input file to be read.
     *
     * @author Billie Johnson
     */
    public void getFile(File file) {
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to get file.");
            System.out.println(e);
        }
    }

    /**
     * Removes any quotation marks from a given string.
     *
     * @param string The input string that you want to remove quotation marks from.
     * @return String The new string without quotation marks.
     *
     * @author Billie Johnson
     */
    public String removeQuotes(String string) {
        return string.replaceAll("\"", "");
    }

    /**
     * Splits a given string into multiple strings wherever there is a comma, then removes any quotation marks from each of them.
     *
     * @param line The line that is to be split into separate parts.
     * @return ArrayList<String> splitline The list of strings from the line that has been split and have had any quotation marks removed from them.
     *
     * @author Billie Johnson
     */
    public ArrayList<String> getEntries(String line) {
        // Splits the given line at every comma into an ArrayList of strings
        splitLine = new ArrayList<>(Arrays.asList(line.split(",")));
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
     *
     * @author Billie Johnson
     */
    public int readAirlineData(File file) {
        int id = -1;
        // Calls the getFile method with the input file to create a BufferedReader to read the file
        getFile(file);

        try {
            // Reads each line in the file
            while ((line = bufferedReader.readLine()) != null) {
                // Splits the line into individual strings
                splitLine = getEntries(line);
                // If the file contains an airline_id already, removes it
                if (splitLine.size() == 8) {
                    splitLine.remove(0);
                }
                // Passes the parameters into the addAirline method of ConcreteAddData
                id = concreteAddData.addAirline(splitLine.get(0), splitLine.get(1), splitLine.get(2), splitLine.get(3),
                                                splitLine.get(4), splitLine.get(5), splitLine.get(6).toUpperCase());
            }
        } catch (IOException e) {
            // If any of the above fails, prints out an error message
            System.out.println("Unable to read file.");
            System.out.println(e);
        }

        return id;
    }

    /**
     * Takes a given file, presumably one containing airport data, reads it,
     * and then passes the data from each line into the addAirport method of ConcreteAddData.
     *
     * @param file The input file to be read.
     * @return int id The airport_id of the new airport created by ConcreteAddData.
     *
     * @author Billie Johnson
     */
    public int readAirportData(File file) {
        int id = -1;
        // Calls the getFile method with the input file to create a BufferedReader to read the file
        getFile(file);

        try {
            // Reads each line in the file
            while ((line = bufferedReader.readLine()) != null) {
                // Splits the line into individual strings
                splitLine = getEntries(line);
                // If the file contains an airport_id already, removes it
                if (splitLine.size() == 12) {
                    splitLine.remove(0);
                }
                // Passes the parameters into the addAirport method of ConcreteAddData
                id = concreteAddData.addAirport(splitLine.get(0), splitLine.get(1), splitLine.get(2), splitLine.get(3),
                                                splitLine.get(4), splitLine.get(5), splitLine.get(6), splitLine.get(7),
                                                splitLine.get(8), splitLine.get(9), splitLine.get(10));
            }
        } catch (IOException e) {
            // If any of the above fails, prints out an error message
            System.out.println("Unable to read file.");
            System.out.println(e);
        }

        return id;
    }

    /**
     * Takes a given file, presumably one containing the data of a single flight, reads it,
     * and then passes the data from each line into the addFlightEntry method of ConcreteAddData.
     *
     * @param file The input file to be read.
     * @return ArrayList<Integer> ids An ArrayList containing the flight_id and the unique id of the new flight entry created by ConcreteAddData.
     *
     * @author Billie Johnson
     */
    public ArrayList<Integer> readFlightData(File file) {
        int flightID = -1;
        int id = -1;
        // Calls the getFile method with the input file to create a BufferedReader to read the file
        getFile(file);

        try {
            // Gets the maximum flight_id currently in the database and adds one to it
            // This will be the flight_id of the entries read from the file
            flightID = flightService.getMaxFlightID() + 1;

            // Reads each line in the file
            while ((line = bufferedReader.readLine()) != null) {
                // Splits the line into individual strings
                splitLine = getEntries(line);
                // Passes the parameters into the addFlightEntry method of ConcreteAddData
                id = concreteAddData.addFlightEntry(flightID, splitLine.get(0), splitLine.get(1), splitLine.get(2), splitLine.get(3), splitLine.get(4));
            }
        } catch (IOException e) {
            // If any of the above fails, prints out an error message
            System.out.println("Unable to read file.");
            System.out.println(e);
        }

        // Creates an ArrayList containing the flightID and unique id of the flight entry that was created
        ArrayList<Integer> ids = new ArrayList<>(Arrays.asList(flightID, id));
        return ids;
    }

    /**
     * Takes a given file, presumably one containing route data, reads it,
     * and then passes the data from each line into the addRoute method of ConcreteAddData.
     *
     * @param file The input file to be read.
     * @return int id The route_id of the new route created by ConcreteAddData.
     *
     * @author Billie Johnson
     */
    public int readRouteData(File file) {
        int id = -1;
        // Calls the getFile method with the input file to create a BufferedReader to read the file
        getFile(file);

        try {
            // Reads each line in the file
            while ((line = bufferedReader.readLine()) != null) {
                // Splits the line into individual strings
                splitLine = getEntries(line);
                // If the file already contained an airline_id, a source airport_id, and a destination airport_id, removes them
                if (splitLine.size() > 6) {
                    splitLine.remove(1);
                    splitLine.remove(2);
                    splitLine.remove(3);
                }
                // Passes the parameters into the addRoute method of ConcreteAddData
                id = concreteAddData.addRoute(splitLine.get(0), splitLine.get(1), splitLine.get(2), splitLine.get(3), splitLine.get(4), splitLine.get(5));
            }
        } catch (IOException e) {
            // If any of the above fails, prints out an error message
            System.out.println("Unable to read file.");
            System.out.println(e);
        }

        return id;
    }

}
