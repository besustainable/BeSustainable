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
import com.example.pc_gaming.besustainable.Entity.City;
import com.example.pc_gaming.besustainable.Entity.Headquarter;
import com.example.pc_gaming.besustainable.Entity.PointOfSale;
import com.example.pc_gaming.besustainable.R;
import com.warkiz.widget.IndicatorSeekBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity {

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
    List<City> listCity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        // instanciate the Lists
        listHeadquarter = new ArrayList<Headquarter>();
        listPointofsale= new ArrayList<PointOfSale>();
        listCity = new ArrayList<City>();

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

        // Number the characters for begin the method
        // To Show in the Autocomplete List
        actvCity.setThreshold(3);

        // Button Apply Action Method
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Open MainActivity for show the list
                Intent i = new Intent(getApplicationContext(), ListProductsFilter.class);

                //Get Values
                i.putExtra("satisfaction", String.valueOf(indicatorSeekBar_satisfaction.getProgress()));
                i.putExtra("economics", String.valueOf(indicatorSeekBar_economics.getProgress()));
                i.putExtra("totalvote", etTotalVote.getText().toString());
                i.putExtra("city", selectedCity(actvCity.getText().toString()));

                /* TESTS
                Toast.makeText(getApplicationContext(), String.valueOf(indicatorSeekBar_satisfaction.getProgress()) + "   " +
                                                        String.valueOf(indicatorSeekBar_economics.getProgress()) + "     " +
                                                        etTotalVote.getText().toString() + "   " +
                                                        selectedCity(actvCity.getText().toString()), Toast.LENGTH_LONG).show();

                /*
                if(!spinnerHq.getSelectedItem().toString().equals("") || spinnerHq.getSelectedItem() != null)
                    i.putExtra("headquarter",spinnerHq.getSelectedItem().toString());
                if(!spinnerPos.getSelectedItem().toString().equals("") || spinnerPos.getSelectedItem() != null)
                    i.putExtra("pointofsale",spinnerPos.getSelectedItem().toString());
                */
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
        // Load Spinners & AutoCompleteView

        // Array Adapter for the Autocomplete
        ArrayAdapter<String> adapterCities = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, loadCities());
        ArrayAdapter<String> adapterHq = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, loadSpinnerHeadquarter());
        ArrayAdapter<String> adapterPos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, loadSpinnerPointOfSale());

        // Specify the layout to use when the list of choices appears
        adapterHq.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterPos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the Spinner & AutoCompleteTextView
        actvCity.setAdapter(adapterCities);
        spinnerHq.setAdapter(adapterHq);
        spinnerPos.setAdapter(adapterPos);

    }

    public String selectedCity(String nameCity){

        for(City city : listCity){
            if (nameCity.toString().equals(city.getName().toString()))
                return String.valueOf(city.getIdCity());
        }

        return "";
    }

    public List loadSpinnerHeadquarter(){

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

        return listSpinnerHq;

    }

    public List<String> loadSpinnerPointOfSale(){

        String url = getString(R.string.ip) + "/beSustainable/loadPointofSale.php";

        final List<String> listSpinnerPos = new ArrayList<String>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray jsonArray = response.optJSONArray("pointofsale");

                try {

                    for (int i = 0; i < jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String name = jsonObject.getString("name");

                        PointOfSale pos = new PointOfSale(jsonObject.getInt("idpos"), jsonObject.getInt("idcity"), name);
                        listPointofsale.add(pos);

                        listSpinnerPos.add(name);
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

        return listSpinnerPos;

    }

    public List<String> loadCities(){

        String url = getString(R.string.ip) + "/beSustainable/loadCities_Plant.php";

        final List<String> listAutoCompleteCities = new ArrayList<String>();

        /* Load the Cities from the cities of Headquarters & Point Of Sale! */
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray jsonArray = response.optJSONArray("cities");

                try {

                    for (int i = 0; i < jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String name = jsonObject.getString("name_city");

                        City city = new City();
                        city.setIdCity(jsonObject.getInt("id_city"));
                        city.setName(jsonObject.getString("name_city"));
                        listCity.add(city);

                        listAutoCompleteCities.add(name);
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

        return listAutoCompleteCities;
    }
}
