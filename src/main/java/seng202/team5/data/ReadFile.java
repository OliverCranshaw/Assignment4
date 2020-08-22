package seng202.team5.data;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ReadFile {

    private FileReader fileReader;
    private BufferedReader bufferedReader;
    private String line;
    private ArrayList<String> splitLine;
    private AddData addData = new AddData();

    private void getFile(File file) {
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to get file.");
            System.out.println(e);
        }
    }

    private String removeQuotes(String string) {
        if (string.length() > 2) {
            return string.replaceAll("^\"+|\"+$", "");
        }
        else {
            return string;
        }
    }

    private ArrayList<String> getEntries(String line) {
        splitLine = new ArrayList<>(Arrays.asList(line.split(",")));
        for (String string: splitLine) {
            string = removeQuotes(string);
        }
        return splitLine;
    }

    public void readAirlineData(File file) {
        getFile(file);
        try {
            while ((line = bufferedReader.readLine()) != null) {
                splitLine = getEntries(line);
                if (splitLine.size() == 8) {
                    splitLine.remove(0);
                }
                if (splitLine.get(1).equals("\\N")) {
                    splitLine.set(1, "");
                }
                addData.addAirline(splitLine.get(0), splitLine.get(1), splitLine.get(2), splitLine.get(3),
                                splitLine.get(4), splitLine.get(5), splitLine.get(6).toUpperCase());
            }
        } catch (IOException e) {
            System.out.println("Unable to read file.");
            System.out.println(e);
        }
    }

    public void readAirportData(File file) {
        getFile(file);
        try {
            while ((line = bufferedReader.readLine()) != null) {
                splitLine = getEntries(line);
                if (splitLine.size() == 11) {
                    splitLine.remove(0);
                }
                try {
                    double latitude = Double.parseDouble(splitLine.get(5));
                    double longitude = Double.parseDouble(splitLine.get(6));
                    int altitude = Integer.parseInt(splitLine.get(7));
                    int timezone = Integer.parseInt(splitLine.get(8));

                    addData.addAirport(splitLine.get(0), splitLine.get(1), splitLine.get(2), splitLine.get(3),
                                    splitLine.get(4), latitude, longitude, altitude, timezone, splitLine.get(9),
                                    splitLine.get(10));
                } catch (NumberFormatException e) {
                    System.out.println("File in wrong format, could not convert numbers, could not add airport.");
                    System.out.println(e);
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to read file.");
            System.out.println(e);
        }
    }

    public void readFlightData(File file) {
        getFile(file);
        try {
            // grab MAX(flight_id) + 1 for the next flight id to add to all the entries ??
            int flightID = 0;
            while ((line = bufferedReader.readLine()) != null) {
                splitLine = getEntries(line);

                try {
                    int altitude = Integer.parseInt(splitLine.get(2));
                    double latitude = Double.parseDouble(splitLine.get(3));
                    double longitude = Double.parseDouble(splitLine.get(4));

                    addData.addFlightEntry(flightID, splitLine.get(0), splitLine.get(1), altitude, latitude, longitude);
                } catch (NumberFormatException e) {
                    System.out.println("File in wrong format, could not convert numbers, could not add airport.");
                    System.out.println(e);
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to read file.");
            System.out.println(e);
        }
    }

    public void readRouteData(File file) {
        getFile(file);
        try {
            while ((line = bufferedReader.readLine()) != null) {
                splitLine = getEntries(line);
                splitLine.remove(1);
                splitLine.remove(2);
                splitLine.remove(3);

                try {
                    int stops = Integer.parseInt(splitLine.get(4));

                    addData.addRoute(splitLine.get(0), splitLine.get(1), splitLine.get(2), splitLine.get(3), stops, splitLine.get(5));
                } catch (NumberFormatException e) {
                    System.out.println("File in wrong format, could not convert numbers, could not add route.");
                    System.out.println(e);
                }

            }
        } catch (IOException e) {
            System.out.println("Unable to read file.");
            System.out.println(e);
        }
    }

}
