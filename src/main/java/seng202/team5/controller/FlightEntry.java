package seng202.team5.controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FlightEntry {

    private SimpleIntegerProperty ID;
    private SimpleIntegerProperty flightID;
    private SimpleStringProperty locationType;
    private SimpleStringProperty location;
    private SimpleIntegerProperty altitude;
    private SimpleDoubleProperty latitude;
    private SimpleDoubleProperty longitude;

    public FlightEntry(Integer id, Integer flightID, String locationType, String location, Integer altitude, double latitude, double longitude) {
        this.ID = new SimpleIntegerProperty(id);
        this.flightID = new SimpleIntegerProperty(flightID);
        this.locationType = new SimpleStringProperty(locationType);
        this.location = new SimpleStringProperty(location);
        this.altitude = new SimpleIntegerProperty(altitude);
        this.latitude = new SimpleDoubleProperty(latitude);
        this.longitude = new SimpleDoubleProperty(longitude);
    }


    public int getID() {
        return ID.get();
    }

    public void setID(int ID) {
        this.ID.set(ID);
    }

    public int getFlightID() {
        return flightID.get();
    }

    public void setFlightID(int flightID) {
        this.flightID.set(flightID);
    }

    public String getLocationType() {
        return locationType.get();
    }

    public void setLocationType(String locationType) {
        this.locationType.set(locationType);
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public int getAltitude() {
        return altitude.get();
    }

    public void setAltitude(int altitude) {
        this.altitude.set(altitude);
    }

    public double getLatitude() {
        return latitude.get();
    }

    public void setLatitude(double latitude) {
        this.latitude.set(latitude);
    }

    public double getLongitude() {
        return longitude.get();
    }

    public void setLongitude(double longitude) {
        this.longitude.set(longitude);
    }




}