package com.example.pc_gaming.besustainable.Entity;

/**
 * Created by PC_Gaming on 02/05/2018.
 */

public class City {

    private int idCity;
    private String name, idCountry;
    private double lat, lon;

    public City(){}

    public City(int idCity, String name, String idCountry, double lat, double lon) {
        this.idCity = idCity;
        this.name = name;
        this.idCountry = idCountry;
        this.lat = lat;
        this.lon = lon;
    }

    // Setter & Getter Methods

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(String idCountry) {
        this.idCountry = idCountry;
    }

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
}
