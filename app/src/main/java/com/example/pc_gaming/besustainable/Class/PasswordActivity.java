package com.example.pc_gaming.besustainable.Class;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.pc_gaming.besustainable.Entity.Consumer;
import com.example.pc_gaming.besustainable.Interface.CustomRequest;
import com.example.pc_gaming.besustainable.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class PasswordActivity extends AppCompatActivity {

    EditText etOldPassword, etNewPassword, etRepeatNewPassword;
    Button btnUpdatePassword;
    String ID_CONSUMER = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        etOldPassword = findViewById(R.id.etOldPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etRepeatNewPassword = findViewById(R.id.etRepeatNewPassword);
        btnUpdatePassword = findViewById(R.id.btnUpdatePassword);

        //Get ID_CONSUMER
        ID_CONSUMER = getIdConsumer();

        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check that the password are the same
                if(!etNewPassword.getText().toString().equals(etRepeatNewPassword.getText().toString())){
                    Toasty.warning(getApplicationContext(), "The passwords must be the same!", Toast.LENGTH_SHORT, true).show();
                }else if(etOldPassword.getText().toString().equals("")){
                    Toasty.warning(getApplicationContext(), "The old password field must be fill!", Toast.LENGTH_SHORT, true).show();
                }else{
                    updatePassword();
                }

            }
        });

    }

    public void updatePassword(){

        // Request for load the Consumer Image
        String url = getString(R.string.ip) + "/beSustainable/updatePassword.php";

        CustomRequest customRequest = new CustomRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = response.getJSONArray("request");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String message = jsonObject.getString("message");

                    if(message.contains("successfully")){
                        Toasty.success(getApplicationContext(), message, Toast.LENGTH_SHORT, true).show();
                        finish();
                    }else{
                        Toasty.error(getApplicationContext(), message, Toast.LENGTH_SHORT, true).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toasty.error(getApplicationContext(), "Exception " + e.getMessage(), Toast.LENGTH_SHORT, true).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toasty.error(getApplicationContext(), "Error to update the Password!", Toast.LENGTH_SHORT, true).show();

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idconsumer", ID_CONSUMER);
                params.put("oldpassword", etOldPassword.getText().toString());
                params.put("newpassword", etNewPassword.getText().toString());
                return params;
            }

        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(customRequest);
    }

    public String getIdConsumer(){
        // Get the latest Shared Preferences
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Consumer", "");
        Consumer consumer = gson.fromJson(json, Consumer.class);
        return String.valueOf(consumer.getIdConsumer());
    }

}
