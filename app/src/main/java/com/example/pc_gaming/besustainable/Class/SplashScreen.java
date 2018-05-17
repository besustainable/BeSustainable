package com.example.pc_gaming.besustainable.Class;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.pc_gaming.besustainable.R;
import com.example.pc_gaming.besustainable.services.ServiceLocation;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Action Bar Hide
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Launch the MainActivity
                Intent intent=new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                //Not show again the SplashScreen
                finish();

            }
        }, 2000);   // Execute Delay

    }
}
