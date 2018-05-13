package com.example.pc_gaming.besustainable.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class ServiceLocation extends Service {

    CheckLocation h1;

    @Override
    public void onCreate() {
        /** Servicio creado... */

        System.out.println("Servicio creado...");
        h1=new CheckLocation(getApplicationContext());
        h1.start();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /**  Servicio iniciado...  */

        System.out.println("Servicio iniciado...");
        if(!h1.isAlive()){

            h1=new CheckLocation(getApplicationContext());
            h1.start();
        }

        //El servicio seguira corriendo aunque se cierre la ejecucion
        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}