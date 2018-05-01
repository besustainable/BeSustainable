package com.example.pc_gaming.besustainable.Class;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.pc_gaming.besustainable.Entity.Headquarter;
import com.example.pc_gaming.besustainable.Entity.PointOfSale;
import com.example.pc_gaming.besustainable.R;
import com.warkiz.widget.IndicatorSeekBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button btnApply, btnCancel;
    private IndicatorSeekBar indicatorSeekBar_satisfaction, indicatorSeekBar_economics;
    private Spinner spinnerHq, spinnerPos;
    private AutoCompleteTextView actvCity;
    private EditText etTotalVote;

    // Variables to pass to MainActivity.class
    private int idcity, idhq, idpos;

    // Lists for load the Spinners
    List<Headquarter> listHeadquarter;
    List<PointOfSale> listPointofsale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        // instanciate the Lists
        listHeadquarter = new ArrayList<Headquarter>();
        listPointofsale= new ArrayList<PointOfSale>();

        // Assignment's
        btnApply = findViewById(R.id.btnApply);
        btnCancel = findViewById(R.id.btnCancel);
        indicatorSeekBar_economics = findViewById(R.id.isb_economics);
        indicatorSeekBar_satisfaction = findViewById(R.id.isb_satisfaction);
        spinnerHq = findViewById(R.id.spinnerHq);
        spinnerPos = findViewById(R.id.spinnerPos);
        actvCity = findViewById(R.id.actvCity);
        etTotalVote = findViewById(R.id.etTotalVote);

        // Preparations

        /*
        *   Array Adapter for the Autocomplete
        *   ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        *   android.R.layout.simple_dropdown_item_1line, os);
        * */

        // Number the characters for begin the method
        // To Show in the Autocomplete List
        actvCity.setThreshold(3);

        // Establish the Adapter
        // textView.setAdapter(adapter);

        // Button Apply Action Method
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);

                //Get Values
                i.putExtra("satisfaction", String.valueOf(indicatorSeekBar_satisfaction.getProgress()));
                i.putExtra("economics", String.valueOf(indicatorSeekBar_economics.getProgress()));
                if(!etTotalVote.getText().toString().equals(""))
                    i.putExtra("totalvote", etTotalVote.getText().toString());
                if(!actvCity.getText().toString().equals(""))
                    i.putExtra("city", actvCity.getText().toString());
                if(!spinnerHq.getSelectedItem().toString().equals("") || spinnerHq.getSelectedItem() != null)
                    i.putExtra("headquarter",spinnerHq.getSelectedItem().toString());
                if(!spinnerPos.getSelectedItem().toString().equals("") || spinnerPos.getSelectedItem() != null)
                    i.putExtra("pointofsale",spinnerPos.getSelectedItem().toString());

                startActivity(i);

            }
        });

        // Button Cancel Action Method
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Request Methods for Load the Headquarter & Point of Sale Spinner
        // And for the other hand load the Cities
        // Load Spinners
        loadSpinnerHeadquarter();
        loadSpinnerPointOfSale();
        loadCities();


    }

    public void loadSpinnerHeadquarter(){

        String url = getString(R.string.ip) + "/beSustainable/loadHeadquarters.php";

        final List<String> listSpinnerHq = new ArrayList<String>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                JSONArray jsonArray = response.optJSONArray("headquarter");

                try {

                    for (int i = 0; i < jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String name = jsonObject.getString("name");

                        Headquarter hq = new Headquarter(jsonObject.getInt("idhq"), jsonObject.getInt("idcity"), name);
                        listHeadquarter.add(hq);

                        listSpinnerHq.add(name);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        //Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);


        // Set the values in the Spinner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listSpinnerHq );
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHq.setAdapter(arrayAdapter);

        spinnerHq.setOnItemSelectedListener(this);

    }

    public void loadSpinnerPointOfSale(){

        String url = getString(R.string.ip) + "/beSustainable/loadPointofSale.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        //Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }

    public void loadCities(){

        /* Load the Cities from the cities of Headquarters & Point Of Sale! */

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String texto = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(getApplicationContext(),"DADO", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
