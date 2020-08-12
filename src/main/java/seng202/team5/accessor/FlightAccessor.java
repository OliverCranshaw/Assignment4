package seng202.team5.accessor;

import seng202.team5.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FlightAccessor implements Accessor{

    private Connection dbHandler;

    public FlightAccessor() {
        dbHandler = DBConnection.getConnection();
    }

    public int save(ArrayList data) throws SQLException {
        int result;
        try {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO FLIGHT_DATA VALUES (?, ?, ?, ?, ?, ?)");
            for (int i=1; i < 7; i++) {
                stmt.setObject(i, data.get(i-1));
            }
            result = stmt.executeUpdate();
        } catch (SQLException e) {
            result = -1;
            System.out.println("Failed to save new airport data.");
        }
        return result;
    }

    public int update(int id, String new_airline, String new_airport, int new_altitude,
                      double new_latitude, double new_longitude) throws SQLException {
        int result;
        int flight_id;
        ArrayList<Object> elements = new ArrayList<>();
        String search = "UPDATE FLIGHT_DATA SET ";

        try {
            // grab flight id
            PreparedStatement flight_stmt = dbHandler.prepareStatement("SELECT flight_id FROM FLIGHT_DATA WHERE id = ?");
            flight_stmt.setObject(1, id);
            ResultSet flights = flight_stmt.executeQuery();
            flight_id = flights.getInt(1);
            try {
                if (new_airline != null) {
                    search = search + "airline = ?, ";
                    elements.add(new_airline);
                }
                if (new_airport != null) {
                    search = search + "airport = ?, ";
                    elements.add(new_airport);
                }
                if (new_altitude != -1) {
                    search = search + "altitude = ?, ";
                    elements.add(new_altitude);
                }
                if (new_latitude != -1) {
                    search = search + "latitude = ?, ";
                    elements.add(new_latitude);
                }
                if (new_longitude != -1) {
                    search = search + "longitude = ? ";
                    elements.add(new_longitude);
                }
                if (search.endsWith(", ")) {
                    search = search.substring(0, search.length() - 2) + " WHERE id = ?";
                } else {
                    search = search + "WHERE id = ?";
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
                String str = "Unable to update flight data with id " + id + " and flight id " + flight_id;
                System.out.println(str);
                System.out.println(e);
            }
        } catch (Exception e) {
            result = -1;
            String str = "Unable to get flight id of data with id " + id;
            System.out.println(str);
            System.out.println(e);
        }
        return result;
    }

    public boolean deleteFlight(int id) throws SQLException {
        boolean result = false;
        try {
            PreparedStatement stmt = dbHandler.prepareStatement("DELETE FROM FLIGHT_DATA WHERE flight_id = ?");
            stmt.setInt(1, id);

            result = stmt.execute();
        } catch (Exception e) {
            String str = "Unable to delete flight data with flight id " + id;
            System.out.println(str);
            System.out.println(e);
        }
        return result;
    }

    public boolean delete(int id) throws SQLException {
        boolean result = false;
        try {
            PreparedStatement stmt = dbHandler.prepareStatement("DELETE FROM FLIGHT_DATA WHERE id = ?");
            stmt.setInt(1, id);

            result = stmt.execute();
        } catch (Exception e) {
            String str = "Unable to delete flight data with id " + id;
            System.out.println(str);
            System.out.println(e);
        }
        return result;
    }

    public ResultSet getData(int id) throws SQLException {
        ResultSet result = null;

        try {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT * FROM FLIGHT_DATA WHERE flight_id = ?");
            stmt.setObject(1, id);
            result = stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Failed to retrieve airline with id " + id);
        }

        return result;
    }

    public ResultSet getData(String airline, String airport) throws SQLException {
        ResultSet result = null;
        String query = "SELECT * FROM FLIGHT_DATA";
        ArrayList<String> elements = new ArrayList<>();

        try {
            if (airline != null) {
                query = query + " WHERE airline = ?";
                elements.add(airline);
            }

            if (airport != null) {
                if (airline != null) {
                    query = query + " and airport = ?";
                } else {
                    query = query + " WHERE airport = ?";
                }
                elements.add(airport);
            }

            PreparedStatement stmt = dbHandler.prepareStatement(query);
            int index = 1;
            for (String element: elements) {
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
