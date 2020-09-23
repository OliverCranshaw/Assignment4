package seng202.team5.model;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;


public class FlightModel {


    private SimpleIntegerProperty flightId;
    private SimpleStringProperty sourceLocation;
    private SimpleStringProperty sourceAirport;
    private SimpleStringProperty destinationLocation;
    private SimpleStringProperty destinationAirport;
    private ArrayList<Integer> IdRange;

    public FlightModel(Integer id, String srcLocation, String srcAirport, String dstLocation, String dstAirport, ArrayList<Integer> ids) {
        this.flightId = new SimpleIntegerProperty(id);
        this.sourceLocation = new SimpleStringProperty(srcLocation);
        this.sourceAirport = new SimpleStringProperty(srcAirport);
        this.destinationLocation = new SimpleStringProperty(dstLocation);
        this.destinationAirport = new SimpleStringProperty(dstAirport);
        this.IdRange = ids;
    }

    public int getFlightId() {
        return flightId.get();
    }

    public void setFlightId(int flightId) {
        this.flightId.set(flightId);
    }

    public String getSourceLocation() {
        return sourceLocation.get();
    }

    public void setSourceLocation(String sourceLocation) {
        this.sourceLocation.set(sourceLocation);
    }

    public String getSourceAirport() {
        return sourceAirport.get();
    }

    public void setSourceAirport(String sourceAirport) {
        this.sourceAirport.set(sourceAirport);
    }

    public String getDestinationLocation() {
        return destinationLocation.get();
    }

    public void setDestinationLocation(String destinationLocation) {
        this.destinationLocation.set(destinationLocation);
    }

    public String getDestinationAirport() {
        return destinationAirport.get();
    }

    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport.set(destinationAirport);
    }

    public ArrayList<Integer> getIdRange() {
        return IdRange;
    }

    public void setIdRange(ArrayList<Integer> idRange) {
        IdRange = idRange;
    }





}
