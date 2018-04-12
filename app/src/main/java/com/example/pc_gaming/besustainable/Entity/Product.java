package com.example.pc_gaming.besustainable.Entity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Toast;

import java.io.Serializable;

/**
 * Created by PC_Gaming on 17/03/2018.
 */

public class Product implements Serializable {

    private int idProduct, idPlant, idCategory, economyAVG, satisfactionAVG;
    private String name, description, origin;
    private double weight, pvp, sustainableAVG, besustainable;
    private Bitmap img;
    private boolean ecology, fairtrade, local;

    // CONSTRUCTOR
    public Product() {
    }

    // OVERLOAD CONSTRUCTOR
    public Product(int idProduct, int idPlant, int idCategory, String name, String description, double weight, double pvp, Bitmap img, double sustainableAVG, int economyAVG, int satisfactionAVG, boolean fairtrade, boolean ecology, boolean local, double besustainable,  String origin) {
        this.idProduct = idProduct;
        this.idPlant = idPlant;
        this.idCategory = idCategory;
        this.economyAVG = economyAVG;
        this.satisfactionAVG = satisfactionAVG;
        this.name = name;
        this.description = description;
        this.origin = origin;
        this.weight = weight;
        this.pvp = pvp;
        this.sustainableAVG = sustainableAVG;
        this.besustainable = besustainable;
        this.img = img;
        this.ecology = ecology;
        this.fairtrade = fairtrade;
        this.local = local;
    }


    // GETTER & SETTER METHOD

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getIdPlant() {
        return idPlant;
    }

    public void setIdPlant(int idPlant) {
        this.idPlant = idPlant;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getEconomyAVG() {
        return economyAVG;
    }

    public void setEconomyAVG(int economyAVG) {
        this.economyAVG = economyAVG;
    }

    public int getSatisfactionAVG() {
        return satisfactionAVG;
    }

    public void setSatisfactionAVG(int satisfactionAVG) {
        this.satisfactionAVG = satisfactionAVG;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPvp() {
        return pvp;
    }

    public void setPvp(double pvp) {
        this.pvp = pvp;
    }

    public double getSustainableAVG() {
        return sustainableAVG;
    }

    public void setSustainableAVG(double sustainableAVG) {
        this.sustainableAVG = sustainableAVG;
    }

    public double getBesustainable() {
        return besustainable;
    }

    public void setBesustainable(double besustainable) {
        this.besustainable = besustainable;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(String imgBytes) {

        try {
            byte[] byteCode= Base64.decode(imgBytes,Base64.DEFAULT);

            int alto=100;//alto en pixeles
            int ancho=150;//ancho en pixeles

            Bitmap foto= BitmapFactory.decodeByteArray(byteCode,0, byteCode.length);
            this.img = Bitmap.createScaledBitmap(foto, alto ,ancho,true);


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public boolean isEcology() {
        return ecology;
    }

    public void setEcology(boolean ecology) {
        this.ecology = ecology;
    }

    public boolean isFairtrade() {
        return fairtrade;
    }

    public void setFairtrade(boolean fairtrade) {
        this.fairtrade = fairtrade;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }
}
