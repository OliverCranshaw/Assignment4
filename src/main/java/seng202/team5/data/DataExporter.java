package seng202.team5.data;

import seng202.team5.accessor.AirlineAccessor;
import seng202.team5.accessor.AirportAccessor;
import seng202.team5.accessor.FlightAccessor;
import seng202.team5.accessor.RouteAccessor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DataExporter
 *
 * Contains the functions exportAirlines, exportAirports, exportFlight, exportFlights, and exportRoutes
 * for exporting data from the database to csv files.
 *
 * @author Billie Johnson
 */
public class DataExporter {

    private BufferedWriter fileWriter;
    private String line;
    private final AirlineAccessor airlineAccessor;
    private final AirportAccessor airportAccessor;
    private final FlightAccessor flightAccessor;
    private final RouteAccessor routeAccessor;

    /**
     * Constructor for DataExporter.
     * Gets the connection to the database and initializes the Accessors to access the database.
     */
    public DataExporter() {
        airlineAccessor = new AirlineAccessor();
        airportAccessor = new AirportAccessor();
        flightAccessor = new FlightAccessor();
        routeAccessor = new RouteAccessor();
    }

    /**
     * Exports all the airlines contained in the database to a csv file called airlines.csv.
     */
    public void exportAirlines(File file) {
        // Retrieves all the airlines from the database
        ResultSet airlines = airlineAccessor.getData(null, null, null);

        try {
            // Creates the FileWriter with the filename "airlines.csv"
            fileWriter = new BufferedWriter(new FileWriter(file));

            // Loops through all the airlines in the ResultSet
            while (airlines.next()) {
                // Gets the data from each column of the row
                int airlineID = airlines.getInt("airline_id");
                String name = airlines.getString("airline_name");
                String alias = airlines.getString("alias");
                String iata = airlines.getString("iata");
                String icao = airlines.getString("icao");
                String callsign = airlines.getString("callsign");
                String country = airlines.getString("country");
                String active = airlines.getString("active");

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
     * Exports all the airports contained in the database to a csv file called airports.csv.
     */
    public void exportAirports(File file) {
        // Retrieves all the airports from the database
        ResultSet airports = airportAccessor.getData(null, null, null);

        try {
            // Creates the FileWriter with the filename "airports.csv"
            fileWriter = new BufferedWriter(new FileWriter(file));

            // Loops through all the airports in the ResultSet
            while (airports.next()) {
                // Gets the data from each column of the row
                int airportID = airports.getInt("airport_id");
                String name = airports.getString("airport_name");
                String city = airports.getString("city");
                String country = airports.getString("country");
                String iata = airports.getString("iata");
                String icao = airports.getString("icao");
                double latitude = airports.getDouble("latitude");
                double longitude = airports.getDouble("longitude");
                int altitude = airports.getInt("altitude");
                float timezone = airports.getFloat("timezone");
                String dst = airports.getString("dst");
                String tz = airports.getString("tz_database_timezone");

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
     * Exports all the entries of a particular flight in the database to a csv file called flight-[source]-[destination].csv.
     * @param flightID int The flightID of a given flight that you want to export.
     */
    public void exportFlight(int flightID, File file) {
        // Retrieves all the flight entries with a given flightID from the database
        ResultSet flight = flightAccessor.getData(flightID);

        try {
            // Creates the FileWriter with the filename "flight-[source]-[destination].csv"
            fileWriter = new BufferedWriter(new FileWriter(file));

            // Loops through all the flight entries in the ResultSet
            while (flight.next()) {
                // Gets the data from each column of the row
                int id = flight.getInt("id");
                String location_type = flight.getString("location_type");
                String location = flight.getString("location");
                int altitude = flight.getInt("altitude");
                double latitude = flight.getDouble("latitude");
                double longitude = flight.getDouble("longitude");

                // Creates a formatted line with the values
                line = String.format("%d,%d,%s,%s,%d,%f,%f", id, flightID, location_type, location, altitude, latitude, longitude);

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
     * Exports all the flight entries contained in the database to a csv file called flights.csv.
     */
    public void exportFlights(File file) {
        // Retrieves all the flight entries from the database
        ResultSet flights = flightAccessor.getData(null, null);

        try {
            // Creates the FileWriter with the filename "flights.csv"
            fileWriter = new BufferedWriter(new FileWriter(file));

            // Loops through all the flight entries in the ResultSet
            while (flights.next()) {
                // Gets the data from each column of the row
                int id = flights.getInt("id");
                int flightID = flights.getInt("flight_id");
                String location_type = flights.getString("location_type");
                String location = flights.getString("location");
                int altitude = flights.getInt("altitude");
                double latitude = flights.getDouble("latitude");
                double longitude = flights.getDouble("longitude");

                // Creates a formatted line with the values
                line = String.format("%d,%d,%s,%s,%d,%f,%f", id, flightID, location_type, location, altitude, latitude, longitude);

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
     * Exports all the routes contained in the database to a csv file called routes.csv.
     */
    public void exportRoutes(File file) {
        // Retrieves all the routes from the database
        ResultSet routes = routeAccessor.getData(null, null, -1, null);

        try {
            // Creates the FileWriter with the filename "routes.csv"
            fileWriter = new BufferedWriter(new FileWriter(file));

            // Loops through all the routes in the ResultSet
            while (routes.next()) {
                // Gets the data from each column of the row
                int routeID = routes.getInt("route_id");
                String airline = routes.getString("airline");
                int airlineID = routes.getInt("airline_id");
                String source_airport = routes.getString("source_airport");
                int source_airportID = routes.getInt("source_airport_id");
                String dest_airport = routes.getString("destination_airport");
                int dest_airportID = routes.getInt("destination_airport_id");
                String codeshare = routes.getString("codeshare");
                int stops = routes.getInt("stops");
                String equipment = routes.getString("equipment");

                // Converts any null values to blank
                if (codeshare == null) {
                    codeshare = "";
                }

                // Creates a formatted line with the values
                line = String.format("%d,%s,%d,%s,%d,%s,%d,%s,%d,%s", routeID, airline, airlineID, source_airport, source_airportID, dest_airport, dest_airportID, codeshare, stops, equipment);

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
}
