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

    public AirportModel(String name, String city, String country, Integer id, Integer incCount, Integer outCount) {
        this.airportName = new SimpleStringProperty(name);
        this.airportCity = new SimpleStringProperty(city);
        this.airportCountry = new SimpleStringProperty(country);
        this.id = id;
        this.noIncRoutes = new SimpleIntegerProperty(incCount);
        this.noOutRoutes = new SimpleIntegerProperty(outCount);
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
