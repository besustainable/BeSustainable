package com.example.pc_gaming.besustainable.Entity;

/**
 * Created by PC_Gaming on 07/05/2018.
 */

public class Category {

    private int idCategory;
    private String name, measure;

    // Constructor

    public Category(){}

    public Category(int idCategory, String name, String measure) {
        this.idCategory = idCategory;
        this.name = name;
        this.measure = measure;
    }


    //Getter & Setter Methods


    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }
}
