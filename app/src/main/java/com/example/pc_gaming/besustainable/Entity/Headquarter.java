package com.example.pc_gaming.besustainable.Entity;

/**
 * Created by PC_Gaming on 01/05/2018.
 */

public class Headquarter {

    // Add the values that you want collect/receive
    private int idhq, idcity;
    private String name;

    // Contructor

    public Headquarter(int idhq, int idcity, String name) {
        this.idhq = idhq;
        this.idcity = idcity;
        this.name = name;
    }


    //Getter & Setter Methods


    public int getIdhq() {
        return idhq;
    }

    public void setIdhq(int idhq) {
        this.idhq = idhq;
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
