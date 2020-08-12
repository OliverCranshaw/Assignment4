package seng202.team5.accessor;

import seng202.team5.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AirlineAccessor implements Accessor {

    private Connection dbHandler;

    public AirlineAccessor() {
        dbHandler = DBConnection.getConnection();
    }

    public int save(ArrayList data) throws SQLException {
        int result;
        try {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "INSERT INTO AIRLINE_DATA VALUES (?, ?, ?, ?, ?, ?, ?)");
            for (int i=1; i < 8; i++) {
                stmt.setObject(i, data.get(i-1));
            }
            result = stmt.executeUpdate();
        } catch (SQLException e) {
            result = -1;
            System.out.println("Failed to save new airport data.");
        }
        return result;
    }

    public int update(int id, String new_name, String new_alias, String new_iata, String new_icao,
                      String new_callsign, String new_country, String new_active) throws SQLException {
        int result;
        try {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "UPDATE AIRLINE_DATA SET airline_name = ?, alias = ?, iata = ?, "
                            + "icao = ?, callsign = ?, country = ?, active = ? WHERE airline_id = ?");
            stmt.setObject(1, new_name);
            stmt.setObject(2, new_alias);
            stmt.setObject(3, new_iata);
            stmt.setObject(4, new_icao);
            stmt.setObject(5, new_callsign);
            stmt.setObject(6, new_country);
            stmt.setObject(7, new_active);
            stmt.setInt(8, id);

            result = stmt.executeUpdate();
        } catch (Exception e) {
            result = -1;
            String str = "Unable to update airline data with id " + id;
            System.out.println(str);
            System.out.println(e);
        }
        return result;
    }

    public boolean delete(int id) throws SQLException {
        boolean result = false;
        try {
            PreparedStatement stmt = dbHandler.prepareStatement("DELETE FROM AIRLINE_DATA WHERE airline_id = ?");
            stmt.setInt(1, id);

            result = stmt.execute();
        } catch (Exception e) {
            String str = "Unable to delete airline data with id " + id;
            System.out.println(str);
            System.out.println(e);
        }
        return result;
    }

    public ResultSet getData(int id) throws SQLException {
        ResultSet result = null;

        try {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT * FROM AIRLINE_DATA WHERE airline_id = ?");
            stmt.setObject(1, id);
            result = stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Failed to retrieve airline with id " + id);
        }

        return result;
    }

    public ResultSet getData(String name, String country, String callign) throws SQLException {
        ResultSet result = null;
        String query = "SELECT * FROM AIRLINE_DATA";
        ArrayList<String> elements = new ArrayList<>();

        try {
            if (name != null) {
                query = query + " WHERE airline_name = ?";
                elements.add(name);
            }

            if (country != null && name == null) {
                if (name != null) {
                    query = query + " and country = ?";
                } else {
                    query = query + " WHERE country = ?";
                }
                elements.add(country);
            }

            if (callign != null) {
                if (country != null || name != null) {
                    query = query + " and callsign = ?";
                    elements.add(callign);
                } else {
                    query = query + " WHERE callsign = ?";
                    elements.add(callign);
                }
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
                    "SELECT COUNT(airport_id) FROM AIRLINE_DATA WHERE airline_name = ? and iata = ? and icao = ?");

            stmt.setObject(1, name);
            stmt.setObject(2, iata);
            stmt.setObject(3, icao);

            result = stmt.execute();
        } catch (Exception e) {
            String str = "Unable to retrieve airline data with name " + name + ", IATA " + iata + ", ICAO " + icao;
            System.out.println(str);
            System.out.println(e);
        }
        return result;
    }
}
