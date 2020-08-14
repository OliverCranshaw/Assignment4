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
                      double new_latitude, double new_longitude, int new_altitude, int new_timezone, String new_dst, String new_tz)
            throws SQLException {
        int result;
        ArrayList<Object> elements = new ArrayList<>();
        String search = "UPDATE AIRPORT_DATA SET ";

        try {
            if (new_name != null) {
                search = search + "airport_name = ?, ";
                elements.add(new_name);
            }
            if (new_city != null) {
                search = search + "city = ?, ";
                elements.add(new_city);
            }
            if (new_country != null) {
                search = search + "country = ?, ";
                elements.add(new_country);
            }
            if (new_iata != null) {
                search = search + "iata = ?, ";
                elements.add(new_iata);
            }
            if (new_icao != null) {
                search = search + "icao = ?, ";
                elements.add(new_icao);
            }
            if (new_latitude != -1) {
                search = search + "latitude = ?, ";
                elements.add(new_latitude);
            }
            if (new_longitude != -1) {
                search = search + "longitude = ?, ";
                elements.add(new_longitude);
            }
            if (new_altitude != -1) {
                search = search + "altitude = ?, ";
                elements.add(new_altitude);
            }
            if (new_timezone != -1) {
                search = search + "timezone = ?, ";
                elements.add(new_timezone);
            }
            if (new_dst != null) {
                search = search + "dst = ?, ";
                elements.add(new_dst);
            }
            if (new_tz != null) {
                search = search + "tz_database_timezone = ? ";
                elements.add(new_tz);
            }
            if (search.endsWith(", ")) {
                search = search.substring(0, search.length() - 2) + " WHERE airport_id = ?";
            } else {
                search = search + "WHERE airport_id = ?";
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
            System.out.println("Failed to retrieve airport data with id " + id);
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
            System.out.println("Failed to retrieve airport data");
        }

        return result;
    }

    public boolean dataExists(int id) throws SQLException {
        boolean result = false;
        try {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT COUNT(airport_id) FROM AIRPORT_DATA WHERE airport_id = ?");

            stmt.setInt(1, id);

            result = stmt.execute();
        } catch (Exception e) {
            String str = "Unable to retrieve airport data with id " + id;
            System.out.println(str);
            System.out.println(e);
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

    public boolean dataExists(String code) throws SQLException {
        boolean result = false;
        try {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT COUNT(airport_id) FROM AIRPORT_DATA WHERE iata = ? or icao = ?");

            stmt.setObject(1, code);
            stmt.setObject(2, code);

            result = stmt.execute();
        } catch (Exception e) {
            String str = "Unable to retrieve airport data with IATA or ICAO code " + code;
            System.out.println(str);
            System.out.println(e);
        }
        return result;
    }
}
