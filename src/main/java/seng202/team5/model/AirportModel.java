package seng202.team5.model;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import seng202.team5.service.AirportService;

import java.sql.SQLException;


public class AirportModel {

    private SimpleStringProperty airportName;
    private SimpleStringProperty airportCity;
    private SimpleStringProperty airportCountry;
    private Integer id;
    private SimpleIntegerProperty noIncRoutes;
    private SimpleIntegerProperty noOutRoutes;
    AirportService airportService;


    public AirportModel(String name, String city, String country, Integer id) {
        this.airportName = new SimpleStringProperty(name);
        this.airportCity = new SimpleStringProperty(city);
        this.airportCountry = new SimpleStringProperty(country);
        this.id = id;
        airportService = new AirportService();
        try {
            int incRoutes = airportService.getIncRouteCount(id);
            noIncRoutes = new SimpleIntegerProperty((incRoutes == -1) ? 0 : incRoutes);
            noOutRoutes = new SimpleIntegerProperty();
        } catch (SQLException e) {
        }
    }

    public int getNoIncRoutes() {
        return noIncRoutes.get();
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

    public String getAirportCity() {
        return airportCity.get();
    }

    public void setAirportCity(String airportCity) {
        this.airportCity.set(airportCity);
    }

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
