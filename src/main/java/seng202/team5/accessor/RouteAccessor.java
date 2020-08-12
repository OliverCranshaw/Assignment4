package seng202.team5.accessor;

import seng202.team5.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RouteAccessor implements Accessor {

    private Connection dbHandler;

    public RouteAccessor() {
        dbHandler = DBConnection.getConnection();
    }

    public int save(ArrayList data) throws SQLException {
        int result;
        try {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO ROUTE_DATA VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for (int i=1; i < 10; i++) {
                stmt.setObject(i, data.get(i-1));
            }
            result = stmt.executeUpdate();
        } catch (SQLException e) {
            result = -1;
            System.out.println("Failed to save new airport data.");
        }
        return result;
    }

    public int update(int id, String new_airline, String new_source_airport, String new_dest_airport,
                      String new_codeshare, int new_stops, String new_equipment) throws SQLException {
        int result;
        ArrayList<Object> elements = new ArrayList<>();
        String search = "UPDATE ROUTE_DATA SET ";

        try {
            if (new_airline != null) {
                // grab airline, airport ids
                PreparedStatement airline_stmt = dbHandler.prepareStatement("SELECT airline_id FROM AIRLINE_DATA WHERE iata = ? OR icao = ?");
                airline_stmt.setObject(1, new_airline);
                airline_stmt.setObject(2, new_airline);
                ResultSet airlines = airline_stmt.executeQuery();
                int new_airline_id = airlines.getInt(1);

                search = search + "airline = ?, airline_id = ?, ";
                elements.add(new_airline);
                elements.add(new_airline_id);
            }
            if (new_source_airport != null) {
                PreparedStatement source_airport_stmt = dbHandler.prepareStatement("SELECT airport_id FROM AIRPORT_DATA WHERE iata = ? OR icao = ?");
                source_airport_stmt.setObject(1, new_source_airport);
                source_airport_stmt.setObject(2, new_source_airport);
                ResultSet source_airports = source_airport_stmt.executeQuery();
                int new_source_airport_id = source_airports.getInt(1);

                search = search + "source_airport = ?, source_airport_id = ?, ";
                elements.add(new_source_airport);
                elements.add(new_source_airport_id);
            }
            if (new_dest_airport != null) {
                PreparedStatement dest_airport_stmt = dbHandler.prepareStatement("SELECT airport_id FROM AIRPORT_DATA WHERE iata = ? OR icao = ?");
                dest_airport_stmt.setObject(1, new_dest_airport);
                dest_airport_stmt.setObject(2, new_dest_airport);
                ResultSet destination_airports = dest_airport_stmt.executeQuery();
                int new_dest_airport_id = destination_airports.getInt(1);

                search = search + "destination_airport = ?, destination_airport_id = ?, ";
                elements.add(new_dest_airport);
                elements.add(new_dest_airport_id);
            }
            if (new_codeshare != null) {
                search = search + "codeshare = ?, ";
                elements.add(new_codeshare);
            }
            if (new_stops != -1) {
                search = search + "stops = ?, ";
                elements.add(new_stops);
            }
            if (new_equipment != null) {
                search = search + "equipment = ? ";
                elements.add(new_equipment);
            }
            if (search.endsWith(", ")) {
                search = search.substring(0, search.length() - 2) + " WHERE route_id = ?";
            } else {
                search = search + "WHERE route_id = ?";
            }
            PreparedStatement stmt = dbHandler.prepareStatement(search);
            int index = 1;
            for (Object element: elements) {
                stmt.setObject(index, element);
                index++;
            }

            result = stmt.executeUpdate();
        } catch (Exception e) {
            result = -1;
            String str = "Unable to update route data with id " + id;
            System.out.println(str);
            System.out.println(e);
        }
        return result;
    }

    public boolean delete(int id) throws SQLException {
        boolean result = false;
        try {
            PreparedStatement stmt = dbHandler.prepareStatement("DELETE FROM ROUTE_DATA WHERE route_id = ?");
            stmt.setInt(1, id);

            result = stmt.execute();
        } catch (Exception e) {
            String str = "Unable to delete route data with id " + id;
            System.out.println(str);
            System.out.println(e);
        }
        return result;
    }

    public ResultSet getData(int id) throws SQLException {
        ResultSet result = null;

        try {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT * FROM ROUTE_DATA WHERE route_id = ?");
            stmt.setObject(1, id);
            result = stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Failed to retrieve airline with id " + id);
        }

        return result;
    }

    public ResultSet getData(String airline, String source_airport, String dest_airport, int stops, String equipment) throws SQLException {
        ResultSet result = null;
        String query = "SELECT * FROM ROUTE_DATA";
        ArrayList<Object> elements = new ArrayList<>();

        try {
            if (airline != null) {
                query = query + " WHERE airline = ?";
                elements.add(airline);
            }

            if (source_airport != null && airline == null) {
                if (airline != null) {
                    query = query + " and airport = ?";
                } else {
                    query = query + " WHERE airport = ?";
                }
                elements.add(source_airport);
            }

            if (dest_airport != null) {
                if (airline != null || source_airport != null) {
                    query = query + " and destination_airport = ?";
                } else {
                    query = query + " WHERE destination_airport = ?";
                }
                elements.add(dest_airport);
            }

            if (stops != -1) {
                if (airline != null || source_airport != null || dest_airport != null) {
                    query = query + " and stops = ?";
                } else {
                    query = query + " WHERE stops = ?";
                }
                elements.add(stops);
            }

            if (equipment != null) {
                if (airline != null || source_airport != null || dest_airport != null || stops != -1) {
                    query = query + " and equipment = ?";
                } else {
                    query = query + " WHERE equipment = ?";
                }
                elements.add(equipment);
            }

            PreparedStatement stmt = dbHandler.prepareStatement(query);
            int index = 1;
            for (Object element: elements) {
                stmt.setObject(index, element);
                index++;
            }

            result = stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Failed to retrieve airline data");
        }

        return result;
    }
}