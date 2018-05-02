package com.example.pc_gaming.besustainable.Entity;

/**
 * Created by PC_Gaming on 01/05/2018.
 */

public class PointOfSale {

    // Add the values that you want collect/receive
    private int idpos, idcity;
    private String name;

    // Contructor

    public PointOfSale(int idpos, int idcity, String name) {
        this.idpos = idpos;
        this.idcity = idcity;
        this.name = name;
    }


    //Getter & Setter Methods

    public int getIdpos() {
        return idpos;
    }

    public void setIdpos(int idpos) {
        this.idpos = idpos;
    }

    public int getIdcity() {
        return idcity;
    }

    public void setIdcity(int idcity) {
        this.idcity = idcity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
