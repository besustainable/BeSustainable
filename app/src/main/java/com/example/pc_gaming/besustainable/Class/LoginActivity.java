package com.example.pc_gaming.besustainable.Class;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.pc_gaming.besustainable.Interface.CustomRequest;
import com.example.pc_gaming.besustainable.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText etPassword, etEmail;
    Button btnLogin, btnCancel;
    String email = "";
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnCancel = findViewById(R.id.btnCancel);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(!etEmail.getText().toString().equals("") || !etPassword.getText().toString().equals("")){

                    email = etEmail.getText().toString();
                    password = etPassword.getText().toString();

                    loadDataUser();

                }else
                    Toast.makeText(getApplicationContext(), "Fill all Fields! Please :)", Toast.LENGTH_SHORT).show();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Come Back to Home
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

            }
        });


    }

    public void loadDataUser(){

        // Request for the Login Consumer
        String url = getString(R.string.ip) + "/beSustainable/login.php";

        CustomRequest customRequest = new CustomRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = response.getJSONArray("consumer");

                    // Control Errors
                    if(jsonArray.length() == 1){

                        if(jsonArray.getJSONObject(0).getString("ErrorMessage").equals("PasswordErrorMessage"))
                            Toast.makeText(getApplicationContext(), "Password Error!", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "EmailErrorMessage!", Toast.LENGTH_SHORT).show();

                    // Else Extract Info of the Consumer
                    }else
                        Toast.makeText(getApplicationContext(), "CONSUMER!", Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Exception " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Login Error ", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }

        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(customRequest);


    }

    @Override public void onBackPressed() {

        // No Action when press Back
        //return null;

    }
}
