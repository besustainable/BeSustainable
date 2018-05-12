package com.example.pc_gaming.besustainable.Entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.sql.Date;

/**
 * Created by PC_Gaming on 10/05/2018.
 */

public class Consumer {

    private int idConsumer, idCity;
    private String nick, description, email, gender, cityName;
    private Date birthday;
    private Bitmap img;
    private boolean newsletter;

    public Consumer (){}


    public Consumer(int idConsumer, int idCity, String nick, String description, String email, String gender, Date birthday, boolean newsletter, Bitmap img, String cityName) {
        this.idConsumer = idConsumer;
        this.idCity = idCity;
        this.nick = nick;
        this.description = description;
        this.email = email;
        this.gender = gender;
        this.birthday = birthday;
        this.newsletter = newsletter;
        this.img = img;
        this.cityName = cityName;
    }

    public int getIdConsumer() {
        return idConsumer;
    }

    public void setIdConsumer(int idConsumer) {
        this.idConsumer = idConsumer;
    }

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isNewsletter() {
        return newsletter;
    }

    public void setNewsletter(boolean newsletter) {
        this.newsletter = newsletter;
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



}
