package seng202.team5.data;

import seng202.team5.service.FlightService;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ReadFile {

    private FileReader fileReader;
    private BufferedReader bufferedReader;
    private String line;
    private ArrayList<String> splitLine;
    private ConcreteAddData concreteAddData = new ConcreteAddData();
    private FlightService flightService = new FlightService();

    public void getFile(File file) {
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to get file.");
            System.out.println(e);
        }
    }

    public String removeQuotes(String string) {
        return string.replaceAll("\"", "");
    }

    public ArrayList<String> getEntries(String line) {
        splitLine = new ArrayList<>(Arrays.asList(line.split(",")));
        for (int i = 0; i < splitLine.size(); i++) {
            splitLine.set(i, removeQuotes(splitLine.get(i)));
        }
        return splitLine;
    }

    public int readAirlineData(File file) {
        int id = -1;
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
                id = concreteAddData.addAirline(splitLine.get(0), splitLine.get(1), splitLine.get(2), splitLine.get(3),
                                                splitLine.get(4), splitLine.get(5), splitLine.get(6).toUpperCase());
            }
        } catch (IOException e) {
            System.out.println("Unable to read file.");
            System.out.println(e);
        }

        return id;
    }

    public int readAirportData(File file) {
        int id = -1;
        getFile(file);

        try {
            while ((line = bufferedReader.readLine()) != null) {
                splitLine = getEntries(line);
                if (splitLine.size() == 12) {
                    splitLine.remove(0);
                }
                System.out.println(splitLine);
                try {
                    double latitude = Double.parseDouble(splitLine.get(5));
                    double longitude = Double.parseDouble(splitLine.get(6));
                    int altitude = Integer.parseInt(splitLine.get(7));
                    int timezone = Integer.parseInt(splitLine.get(8));

                    id = concreteAddData.addAirport(splitLine.get(0), splitLine.get(1), splitLine.get(2), splitLine.get(3),
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

        return id;
    }

    public ArrayList<Integer> readFlightData(File file) {
        int flightID = -1;
        int id = -1;
        getFile(file);

        try {
            flightID = flightService.getMaxFlightID() + 1;

            while ((line = bufferedReader.readLine()) != null) {
                splitLine = getEntries(line);

                try {
                    int altitude = Integer.parseInt(splitLine.get(2));
                    double latitude = Double.parseDouble(splitLine.get(3));
                    double longitude = Double.parseDouble(splitLine.get(4));

                    id = concreteAddData.addFlightEntry(flightID, splitLine.get(0), splitLine.get(1), altitude, latitude, longitude);
                } catch (NumberFormatException e) {
                    System.out.println("File in wrong format, could not convert numbers, could not add flight.");
                    System.out.println(e);
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to read file.");
            System.out.println(e);
        }

        ArrayList<Integer> ids = new ArrayList<>(Arrays.asList(flightID, id));
        return ids;
    }

    public int readRouteData(File file) {
        int id = -1;
        getFile(file);

        try {
            while ((line = bufferedReader.readLine()) != null) {
                splitLine = getEntries(line);
                splitLine.remove(1);
                splitLine.remove(2);
                splitLine.remove(3);

                try {
                    int stops = Integer.parseInt(splitLine.get(4));

                    id = concreteAddData.addRoute(splitLine.get(0), splitLine.get(1), splitLine.get(2), splitLine.get(3), stops, splitLine.get(5));
                } catch (NumberFormatException e) {
                    System.out.println("File in wrong format, could not convert numbers, could not add route.");
                    System.out.println(e);
                }

            }
        } catch (IOException e) {
            System.out.println("Unable to read file.");
            System.out.println(e);
        }

        return id;
    }

}
