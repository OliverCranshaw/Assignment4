package seng202.team5.controller;


import javafx.beans.property.SimpleStringProperty;


public class AirlineModel {



    private SimpleStringProperty airlineName;
    private SimpleStringProperty airlineAlias;
    private SimpleStringProperty airlineCountry;
    private SimpleStringProperty airlineActive;


    public AirlineModel(String name, String alias, String country, String active) {
        this.airlineName = new SimpleStringProperty(name);
        this.airlineAlias = new SimpleStringProperty(alias);
        this.airlineCountry = new SimpleStringProperty(country);
        this.airlineActive = new SimpleStringProperty(active);
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
}
