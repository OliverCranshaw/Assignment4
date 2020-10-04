package seng202.team5.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RouteModel {



    private SimpleStringProperty routeAirline;
    private SimpleStringProperty routeSrcAirport;
    private SimpleStringProperty routeDstAirport;
    private SimpleIntegerProperty routeStops;
    private SimpleStringProperty routeEquipment;
    private SimpleIntegerProperty routeId;
    private SimpleStringProperty routeSrcAirportCode;
    private SimpleStringProperty routeDstAirportCode;



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
