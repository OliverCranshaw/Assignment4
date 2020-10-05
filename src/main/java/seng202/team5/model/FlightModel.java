package seng202.team5.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

/**
 * FlightModel
 *
 * Model class for flights, used as the object by PropertyValueFactories
 * in the flight table. Consists of only a constructor, getters and setters.
 */
public class FlightModel {

    private final SimpleIntegerProperty flightId;
    private final SimpleStringProperty sourceLocation;
    private final SimpleStringProperty sourceAirport;
    private final SimpleStringProperty destinationLocation;
    private final SimpleStringProperty destinationAirport;
    private ArrayList<Integer> IdRange;


    /**
     * Constructor for FlightModel.
     *
     * @param id - Integer, Database ID of the flight.
     * @param srcLocation - String, Location of source airport.
     * @param srcAirport - String, name of source airport.
     * @param dstLocation - String, Location of destination airport.
     * @param dstAirport - String, name of destination airport.
     * @param ids - ArrayList of Integers, Database IDs of flight entries.
     */
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
