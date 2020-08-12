package seng202.team5.accessor;

import seng202.team5.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AirportAccessor implements Accessor {

    private Connection dbHandler;

    public AirportAccessor() {
        dbHandler = DBConnection.getConnection();
    }

    public int save(ArrayList data) throws SQLException {
        int result;
        try {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO AIRPORT_DATA VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for (int i=1; i < 12; i++) {
                stmt.setObject(i, data.get(i-1));
            }
            result = stmt.executeUpdate();
        } catch (SQLException e) {
            result = -1;
            System.out.println("Failed to save new airport data.");
        }
        return result;
    }

    public int update(int id, String new_name, String new_city, String new_country, String new_iata, String new_icao,
                      double new_latitude, double new_longitude, int new_altitude, int new_timezone, String dst, String new_tz)
            throws SQLException {
        int result;
        try {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "UPDATE AIRPORT_DATA SET airport_name = ?, city = ?, country = ?, iata = ?, icao = ?, latitude = ?, "
                            + "longitude = ?, altitude = ?, timezone = ?, dst = ?, tz_database_timezone = ? WHERE airport_id = ?");
            stmt.setObject(1, new_name);
            stmt.setObject(2, new_city);
            stmt.setObject(3, new_country);
            stmt.setObject(4, new_iata);
            stmt.setObject(5, new_icao);
            stmt.setDouble(6, new_latitude);
            stmt.setDouble(7, new_longitude);
            stmt.setInt(8, new_altitude);
            stmt.setInt(9, new_timezone);
            stmt.setObject(10, dst);
            stmt.setObject(11, new_tz);
            stmt.setInt(12, id);

            result = stmt.executeUpdate();
        } catch (Exception e) {
            result = -1;
            String str = "Unable to update airport data with id " + id;
            System.out.println(str);
            System.out.println(e);
        }
        return result;
    }

    public boolean delete(int id) throws SQLException {
        boolean result = false;
        try {
            PreparedStatement stmt = dbHandler.prepareStatement("DELETE FROM AIRPORT_DATA WHERE airport_id = ?");
            stmt.setInt(1, id);

            result = stmt.execute();
        } catch (Exception e) {
            String str = "Unable to delete airport data with id " + id;
            System.out.println(str);
            System.out.println(e);
        }
        return result;
    }

    public ResultSet getData(int id) throws SQLException {
        ResultSet result = null;

        try {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT * FROM AIRPORT_DATA WHERE airport_id = ?");
            stmt.setObject(1, id);
            result = stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Failed to retrieve airline with id " + id);
        }

        return result;
    }

    public ResultSet getData(String name, String city, String country) throws SQLException {
        ResultSet result = null;
        String query = "SELECT * FROM AIRPORT_DATA";
        ArrayList<String> elements = new ArrayList<>();

        try {
            if (name != null) {
                query = query + " WHERE airport_name = ?";
                elements.add(name);
            }

            if (city != null) {
                if (name != null) {
                    query = query + " and city = ?";
                } else {
                    query = query + " WHERE city = ?";
                }
                elements.add(city);
            }

            if (country != null) {
                if (name != null || city != null) {
                    query = query + " and country = ?";
                } else {
                    query = query + " WHERE country = ?";
                }
                elements.add(country);
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

    public boolean dataExists(String name, String iata, String icao) throws SQLException {
        boolean result = false;
        try {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT COUNT(airport_id) FROM AIRPORT_DATA WHERE airport_name = ? and iata = ? and icao = ?");

            stmt.setObject(1, name);
            stmt.setObject(2, iata);
            stmt.setObject(3, icao);

            result = stmt.execute();
        } catch (Exception e) {
            String str = "Unable to retrieve airport data with name " + name + ", IATA " + iata + ", ICAO " + icao;
            System.out.println(str);
            System.out.println(e);
        }
        return result;
    }
}
