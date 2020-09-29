package seng202.team5.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import seng202.team5.service.AirportService;

import java.sql.SQLException;

public class AirportModel {

    private final SimpleStringProperty airportName;
    private final SimpleStringProperty airportCity;
    private final SimpleStringProperty airportCountry;
    private Integer id;
    private SimpleIntegerProperty noIncRoutes;
    private SimpleIntegerProperty noOutRoutes;
    private final AirportService airportService;

    public AirportModel(String name, String city, String country, Integer id) {
        this.airportName = new SimpleStringProperty(name);
        this.airportCity = new SimpleStringProperty(city);
        this.airportCountry = new SimpleStringProperty(country);
        this.id = id;

        airportService = new AirportService();

        try {
            int incRoutes = airportService.getIncRouteCount(id);
            int outRoutes = airportService.getOutRouteCount(id);
            noIncRoutes = new SimpleIntegerProperty((incRoutes == -1) ? 0 : incRoutes);
            noOutRoutes = new SimpleIntegerProperty((outRoutes == -1) ? 0 : outRoutes);
        } catch (SQLException e) {
            System.out.println("Failed to retrieve airport data");
            System.out.println(e.getMessage());
        }
    }

    public void setNoIncRoutes(int noIncRoutes) {
        this.noIncRoutes.set(noIncRoutes);
    }

    public int getNoOutRoutes() {
        return noOutRoutes.get();
    }

    public void setNoOutRoutes(int noOutRoutes) {
        this.noOutRoutes.set(noOutRoutes);
    }

    public int getNoIncRoutes() {
        return noIncRoutes.get();
    }

    public void setAirportCity(String airportCity) {
        this.airportCity.set(airportCity);
    }

    public String getAirportCity() { return airportCity.get(); }

    public String getAirportName() {
        return airportName.get();
    }

    public void setAirportName(String airportName) {
        this.airportName.set(airportName);
    }

    public String getAirportCountry() {
        return airportCountry.get();
    }

    public void setAirportCountry(String airportCountry) {
        this.airportCountry.set(airportCountry);
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }
}
