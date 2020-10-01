package seng202.team5.data;

import seng202.team5.service.FlightService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * DataExporter
 *
 * Contains the functions exportAirlines, exportAirports, exportFlight, exportFlights, and exportRoutes
 * for exporting data from the database to csv files.
 */
public class DataExporter {

    private BufferedWriter fileWriter;
    private String line;
    private final FlightService flightService;

    /**
     * Constructor for DataExporter.
     * Gets the connection to the database and initializes the Services to access the database.
     */
    public DataExporter() {
        flightService = new FlightService();
    }

    /**
     * Exports all the airlines passed into the function to a given csv file.
     *
     * @param file File that the data will be exported into.
     * @param airlines An ArrayList of airlines.
     */
    public void exportAirlines(File file, ArrayList<ArrayList<Object>> airlines) {
        try {
            // Creates the FileWriter with the given file
            fileWriter = new BufferedWriter(new FileWriter(file));

            // Loops through all the airlines in the ArrayList
            for (ArrayList<Object> airline : airlines) {
                // Gets the data from each column of the row
                int airlineID = (int) airline.get(0);
                String name = (String) airline.get(1);
                String alias = (String) airline.get(2);
                String iata = (String) airline.get(3);
                String icao = (String) airline.get(4);
                String callsign = (String) airline.get(5);
                String country = (String) airline.get(6);
                String active = (String) airline.get(7);

                // Converts any null values to blank
                if (alias == null) {
                    alias = "";
                }
                if (iata == null) {
                    iata = "";
                }
                if (icao == null) {
                    icao = "";
                }
                if (callsign == null) {
                    callsign = "";
                }
                if (country == null) {
                    country = "";
                }

                // Creates a formatted line with the values
                line = String.format("%d,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"", airlineID, name, alias, iata, icao, callsign, country, active);

                // Creates a new line in the file, and then writes the formatted line into it
                fileWriter.write(line);
                fileWriter.newLine();
            }

            // Closes the FileWriter when everything is done
            fileWriter.close();
        } catch (IOException e) {
            // If anything goes wrong involving the FileWriter or BufferedWriter, outputs an error message
            System.out.println("Error writing to file.");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Exports all the airports passed into the function to a given csv file.
     *
     * @param file File that the data will be exported into.
     * @param airports An ArrayList of airports.
     */
    public void exportAirports(File file, ArrayList<ArrayList<Object>> airports) {
        try {
            // Creates the FileWriter with the given file
            fileWriter = new BufferedWriter(new FileWriter(file));

            // Loops through all the airports in the ArrayList
            for (ArrayList<Object> airport : airports) {
                // Gets the data from each column of the row
                int airportID = (int) airport.get(0);
                String name = (String) airport.get(1);
                String city = (String) airport.get(2);
                String country = (String) airport.get(3);
                String iata = (String) airport.get(4);
                String icao = (String) airport.get(5);
                double latitude = (double) airport.get(6);
                double longitude = (double) airport.get(7);
                int altitude = (int) airport.get(8);
                double timezone = (double) airport.get(9);
                String dst = (String) airport.get(10);
                String tz = (String) airport.get(11);

                // Converts any null values to blank
                if (iata == null) {
                    iata = "";
                }
                if (icao == null) {
                    icao = "";
                }

                // Creates a formatted line with the values
                line = String.format("%d,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",%f,%f,%d,%.1f,\"%s\",\"%s\"", airportID, name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, tz);

                // Creates a new line in the file, and then writes the formatted line into it
                fileWriter.write(line);
                fileWriter.newLine();
            }

            // Closes the FileWriter when everything is done
            fileWriter.close();
        } catch (IOException e) {
            // If anything goes wrong involving the FileWriter or BufferedWriter, outputs an error message
            System.out.println("Error writing to file.");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Exports all the entries of a particular flight in the database to a given csv file.
     *
     * @param flightID int The flightID of a given flight that you want to export.
     * @param file File that the data will be exported into.
     */
    public void exportFlight(int flightID, File file) {
        // Retrieves all the flight entries with a given flightID from the database
        ResultSet flight = flightService.getData(flightID);

        try {
            // Creates the FileWriter with the given file
            fileWriter = new BufferedWriter(new FileWriter(file));

            // Loops through all the flight entries in the ResultSet
            while (flight.next()) {
                // Gets the data from each column of the row
                int id = flight.getInt("id");
                String locationType = flight.getString("location_type");
                String location = flight.getString("location");
                int altitude = flight.getInt("altitude");
                double latitude = flight.getDouble("latitude");
                double longitude = flight.getDouble("longitude");

                // Creates a formatted line with the values
                line = String.format("%d,%d,%s,%s,%d,%f,%f", id, flightID, locationType, location, altitude, latitude, longitude);

                // Creates a new line in the file, and then writes the formatted line into it
                fileWriter.write(line);
                fileWriter.newLine();
            }

            // Closes the FileWriter when everything is done
            fileWriter.close();
        } catch (SQLException e) {
            // If anything goes wrong when extracting data from the ResultSet, outputs an error message
            System.out.println("Error reading results from database.");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            // If anything goes wrong involving the FileWriter or BufferedWriter, outputs an error message
            System.out.println("Error writing to file.");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Exports all the flight entries contained in the database to a given csv file.
     *
     * @param file File that the data will be exported into.
     */
    public void exportFlights(File file) {
        // Retrieves all the flight entries from the database
        ResultSet flights = flightService.getData(null, null);

        try {
            // Creates the FileWriter with the given file
            fileWriter = new BufferedWriter(new FileWriter(file));

            // Loops through all the flight entries in the ResultSet
            while (flights.next()) {
                // Gets the data from each column of the row
                int id = flights.getInt("id");
                int flightID = flights.getInt("flight_id");
                String locationType = flights.getString("location_type");
                String location = flights.getString("location");
                int altitude = flights.getInt("altitude");
                double latitude = flights.getDouble("latitude");
                double longitude = flights.getDouble("longitude");

                // Creates a formatted line with the values
                line = String.format("%d,%d,%s,%s,%d,%f,%f", id, flightID, locationType, location, altitude, latitude, longitude);

                // Creates a new line in the file, and then writes the formatted line into it
                fileWriter.write(line);
                fileWriter.newLine();
            }

            // Closes the FileWriter when everything is done
            fileWriter.close();
        } catch (SQLException e) {
            // If anything goes wrong when extracting data from the ResultSet, outputs an error message
            System.out.println("Error reading results from database.");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            // If anything goes wrong involving the FileWriter or BufferedWriter, outputs an error message
            System.out.println("Error writing to file.");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Exports all the flight entries passed into the function to a given csv file.
     *
     * @param file File that the data will be exported into.
     * @param flights An ArrayList of flight entries.
     */
    public void exportFlights(File file, ArrayList<ArrayList<Object>> flights) {
        try {
            // Creates the FileWriter with the given file
            fileWriter = new BufferedWriter(new FileWriter(file));

            // Loops through all the flight entries in the ArrayList
            for (ArrayList<Object> flight : flights) {
                int flightID = (int) flight.get(1);
                ResultSet flightEntries = flightService.getData(flightID);

                // Loops through all the flight entries in the ResultSet
                while (flightEntries.next()) {
                    // Gets the data from each column of the row
                    int id = flightEntries.getInt("id");
                    String locationType = flightEntries.getString("location_type");
                    String location = flightEntries.getString("location");
                    int altitude = flightEntries.getInt("altitude");
                    double latitude = flightEntries.getDouble("latitude");
                    double longitude = flightEntries.getDouble("longitude");

                    // Creates a formatted line with the values
                    line = String.format("%d,%d,%s,%s,%d,%f,%f", id, flightID, locationType, location, altitude, latitude, longitude);

                    // Creates a new line in the file, and then writes the formatted line into it
                    fileWriter.write(line);
                    fileWriter.newLine();
                }
            }

            // Closes the FileWriter when everything is done
            fileWriter.close();
        } catch (SQLException e) {
            // If anything goes wrong when extracting data from the ResultSet, outputs an error message
            System.out.println("Error reading results from database.");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            // If anything goes wrong involving the FileWriter or BufferedWriter, outputs an error message
            System.out.println("Error writing to file.");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Exports all the routes passed into the function to a given csv file.
     *
     * @param file File that the data will be exported into.
     * @param routes An ArrayList of routes.
     */
    public void exportRoutes(File file, ArrayList<ArrayList<Object>> routes) {
        try {
            // Creates the FileWriter with the given file
            fileWriter = new BufferedWriter(new FileWriter(file));

            // Loops through all the routes in the ArrayList
            for (ArrayList<Object> route : routes) {
                // Gets the data from each column of the row
                int routeID = (int) route.get(0);
                String airline = (String) route.get(1);
                int airlineID = (int) route.get(2);
                String sourceAirport = (String) route.get(3);
                int sourceAirportID = (int) route.get(4);
                String destAirport = (String) route.get(5);
                int destAirportID = (int) route.get(6);
                String codeshare = (String) route.get(7);
                int stops = (int) route.get(8);
                String equipment = (String) route.get(9);

                // Converts any null values to blank
                if (codeshare == null) {
                    codeshare = "";
                }

                // Creates a formatted line with the values
                line = String.format("%d,%s,%d,%s,%d,%s,%d,%s,%d,%s", routeID, airline, airlineID, sourceAirport, sourceAirportID, destAirport, destAirportID, codeshare, stops, equipment);

                // Creates a new line in the file, and then writes the formatted line into it
                fileWriter.write(line);
                fileWriter.newLine();
            }

            // Closes the FileWriter when everything is done
            fileWriter.close();
        } catch (IOException e) {
            // If anything goes wrong involving the FileWriter or BufferedWriter, outputs an error message
            System.out.println("Error writing to file.");
            System.out.println(e.getMessage());
        }
    }
}
