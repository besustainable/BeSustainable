package com.example.pc_gaming.besustainable.Class;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pc_gaming.besustainable.R;

public class OptionLoginActivity extends AppCompatActivity {

    private Button btnStarted, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_login);

        getSupportActionBar().hide();

        btnStarted = findViewById(R.id.btnStarted);
        btnLogin = findViewById(R.id.btnLogin);

        btnStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Intent i = new Intent(getApplicationContext(), CreateProfile.class);
                startActivity(i);
                */
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);

            }
        });


    }
}
