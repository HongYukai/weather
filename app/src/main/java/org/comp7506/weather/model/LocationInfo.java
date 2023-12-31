package org.comp7506.weather.model;

import java.io.Serializable;

public class LocationInfo implements Serializable {
    private double lat;

    private double lon;

    private String city = "HongKong";

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}