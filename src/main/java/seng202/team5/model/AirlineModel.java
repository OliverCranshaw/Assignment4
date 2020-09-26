package seng202.team5.model;

import javafx.beans.property.SimpleStringProperty;

public class AirlineModel {

    private final SimpleStringProperty airlineName;
    private final SimpleStringProperty airlineAlias;
    private final SimpleStringProperty airlineCountry;
    private final SimpleStringProperty airlineActive;
    private Integer id;

    public AirlineModel(String name, String alias, String country, String active, Integer id) {
        this.airlineName = new SimpleStringProperty(name);
        this.airlineAlias = new SimpleStringProperty(alias);
        this.airlineCountry = new SimpleStringProperty(country);
        this.airlineActive = new SimpleStringProperty(active);
        this.id = id;
    }

    public String getAirlineName() {
        return airlineName.get();
    }

    public void setAirlineName(String airlineName) {
        this.airlineName.set(airlineName);
    }

    public String getAirlineActive() {
        return airlineActive.get();
    }

    public void setAirlineActive(String airlineActive) {
        this.airlineActive.set(airlineActive);
    }

    public String getAirlineAlias() {
        return airlineAlias.get();
    }

    public void setAirlineAlias(String airlineAlias) {
        this.airlineAlias.set(airlineAlias);
    }

    public String getAirlineCountry() {
        return airlineCountry.get();
    }

    public void setAirlineCountry(String airlineCountry) {
        this.airlineCountry.set(airlineCountry);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
