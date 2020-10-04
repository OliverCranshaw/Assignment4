package seng202.team5.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * RouteModel
 *
 * Model class for route entries, used as the object by PropertyValueFactories
 * in the route table. Consists of only a constructor, getters and setters.
 */
public class RouteModel {

    private SimpleStringProperty routeAirline;
    private SimpleStringProperty routeSrcAirport;
    private SimpleStringProperty routeDstAirport;
    private SimpleIntegerProperty routeStops;
    private SimpleStringProperty routeEquipment;
    private SimpleIntegerProperty routeId;
    private SimpleStringProperty routeSrcAirportCode;
    private SimpleStringProperty routeDstAirportCode;

    /**
     * Constructor for RouteModel.
     *
     * @param airline - String, airline code (IATA or ICAO).
     * @param srcAirport - String, source airport name.
     * @param dstAirport - String, destination airport name.
     * @param stops - Integer, number of stops.
     * @param equipment - String, equipment of route.
     * @param id - Integer, Database ID of route.
     * @param srcCode - String, source airport code (IATA or ICAO).
     * @param dstCode - String, destination airport code (IATA or ICAO).
     */
    public RouteModel(String airline, String srcAirport, String dstAirport, Integer stops, String equipment, Integer id, String srcCode, String dstCode) {
        this.routeAirline = new SimpleStringProperty(airline);
        this.routeSrcAirport = new SimpleStringProperty(srcAirport);
        this.routeDstAirport = new SimpleStringProperty(dstAirport);
        this.routeStops = new SimpleIntegerProperty(stops);
        this.routeEquipment = new SimpleStringProperty(equipment);
        this.routeId = new SimpleIntegerProperty(id);
        this.routeSrcAirportCode = new SimpleStringProperty(srcCode);
        this.routeDstAirportCode = new SimpleStringProperty(dstCode);
    }

    public String getRouteAirline() {
        return routeAirline.get();
    }

    public void setRouteAirline(String routeAirline) {
        this.routeAirline.set(routeAirline);
    }

    public String getRouteSrcAirport() {
        return routeSrcAirport.get();
    }

    public void setRouteSrcAirport(String routeSrcAirport) {
        this.routeSrcAirport.set(routeSrcAirport);
    }

    public String getRouteDstAirport() {
        return routeDstAirport.get();
    }

    public void setRouteDstAirport(String routeDstAirport) {
        this.routeDstAirport.set(routeDstAirport);
    }

    public int getRouteStops() {
        return routeStops.get();
    }

    public void setRouteStops(int routeStops) {
        this.routeStops.set(routeStops);
    }

    public String getRouteEquipment() {
        return routeEquipment.get();
    }

    public void setRouteEquipment(String routeEquipment) {
        this.routeEquipment.set(routeEquipment);
    }

    public int getRouteId() {
        return routeId.get();
    }

    public void setRouteId(int routeId) {
        this.routeId.set(routeId);
    }

    public String getRouteSrcAirportCode() {
        return routeSrcAirportCode.get();
    }

    public void setRouteSrcAirportCode(String routeSrcAirportCode) {
        this.routeSrcAirportCode.set(routeSrcAirportCode);
    }

    public String getRouteDstAirportCode() {
        return routeDstAirportCode.get();
    }

    public void setRouteDstAirportCode(String routeDstAirportCode) {
        this.routeDstAirportCode.set(routeDstAirportCode);
    }
}
