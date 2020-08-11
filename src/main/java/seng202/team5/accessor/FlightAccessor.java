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
                stmt.setObject(i, data.get(i));
            }
            result = stmt.executeUpdate();
        } catch (SQLException e) {
            result = -1;
            System.out.println("Failed to save new airport data.");
        }
        return result;
    }

    //public ArrayList getData(int id) {

    //}

    public int update(int id, String new_airline, String new_airport, int new_altitude,
                      double new_latitude, double new_longitude) throws SQLException {
        int result;
        int flight_id;

        try {
            // grab flight id
            PreparedStatement flight_stmt = dbHandler.prepareStatement("SELECT flight_id FROM FLIGHT_DATA WHERE id = ?");
            flight_stmt.setObject(1, id);
            ResultSet flights = flight_stmt.executeQuery();
            flight_id = flights.getInt(1);
            try {
                PreparedStatement stmt = dbHandler.prepareStatement(
                        "UPDATE FLIGHT_DATA SET airline = ?, airport = ?, altitude = ?, "
                                + "latitude = ?, longitude = ? WHERE id = ?");
                stmt.setObject(1, new_airline);
                stmt.setObject(2, new_airport);
                stmt.setInt(3, new_altitude);
                stmt.setDouble(4, new_latitude);
                stmt.setDouble(5, new_longitude);
                stmt.setInt(6, id);

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
}
