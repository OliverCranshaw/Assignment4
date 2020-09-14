package seng202.team5.controller;


import javafx.beans.property.SimpleStringProperty;




public class AirportModel {

    private SimpleStringProperty airportName;
    private SimpleStringProperty airportCity;
    private SimpleStringProperty airportCountry;
    private Integer id;


    public AirportModel(String name, String city, String country, Integer id) {
        this.airportName = new SimpleStringProperty(name);
        this.airportCity = new SimpleStringProperty(city);
        this.airportCountry = new SimpleStringProperty(country);
        this.id = id;
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


}
