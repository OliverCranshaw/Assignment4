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

    public int save(ArrayList data) {
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
            System.out.println("Failed to save new route data");
            System.out.println(e);
        }

        return result;
    }

    public int update(int id, String new_airline, int new_airline_id, String new_source_airport, int new_source_airport_id,
                      String new_dest_airport, int new_dest_airport_id, String new_codeshare, int new_stops, String new_equipment) {
        int result;
        ArrayList<Object> elements = new ArrayList<>();
        String search = "UPDATE ROUTE_DATA SET ";

        try {
            if (new_airline != null) {
                search = search + "airline = ?, airline_id = ?, ";
                elements.add(new_airline);
                elements.add(new_airline_id);
            }
            if (new_source_airport != null) {
                search = search + "source_airport = ?, source_airport_id = ?, ";
                elements.add(new_source_airport);
                elements.add(new_source_airport_id);
            }
            if (new_dest_airport != null) {
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
            System.out.println("Unable to update route data with id " + id);
            System.out.println(e);
        }

        return result;
    }

    public boolean delete(int id) {
        boolean result = false;
        try {
            PreparedStatement stmt = dbHandler.prepareStatement("DELETE FROM ROUTE_DATA WHERE route_id = ?");
            stmt.setInt(1, id);

            result = stmt.execute();
        } catch (Exception e) {
            System.out.println("Unable to delete route data with id " + id);
            System.out.println(e);
        }

        return result;
    }

    public ResultSet getData(int id) {
        ResultSet result = null;

        try {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT * FROM ROUTE_DATA WHERE route_id = ?");
            stmt.setObject(1, id);
            result = stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Failed to retrieve route with id " + id);
            System.out.println(e);
        }

        return result;
    }

    public ResultSet getData(String airline, String dest_airport, int stops, String equipment) {
        ResultSet result = null;
        String query = "SELECT * FROM ROUTE_DATA";
        ArrayList<Object> elements = new ArrayList<>();

        try {
            if (airline != null) {
                query = query + " WHERE airline = ?";
                elements.add(airline);
            }

            if (dest_airport != null) {
                if (airline != null) {
                    query = query + " and destination_airport = ?";
                } else {
                    query = query + " WHERE destination_airport = ?";
                }
                elements.add(dest_airport);
            }

            if (stops != -1) {
                if (airline != null || dest_airport != null) {
                    query = query + " and stops = ?";
                } else {
                    query = query + " WHERE stops = ?";
                }
                elements.add(stops);
            }

            if (equipment != null) {
                if (airline != null || dest_airport != null || stops != -1) {
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
            System.out.println("Failed to retrieve route data");
            System.out.println(e);
        }

        return result;
    }

    public boolean dataExists(int id) {
        boolean result = false;
        try {
            PreparedStatement stmt = dbHandler.prepareStatement(
                    "SELECT COUNT(route_id) FROM ROUTE_DATA WHERE route_id = ?");

            stmt.setInt(1, id);

            result = stmt.execute();
        } catch (Exception e) {
            System.out.println("Unable to retrieve route data with id " + id);
            System.out.println(e);
        }

        return result;
    }
}
