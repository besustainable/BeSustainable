package com.example.pc_gaming.besustainable.Class;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.pc_gaming.besustainable.Interface.CustomRequest;
import com.example.pc_gaming.besustainable.R;
import com.example.pc_gaming.besustainable.services.ServiceLocation;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class CreateConsumer extends AppCompatActivity implements LocationListener, DatePickerDialog.OnDateSetListener {

    EditText etCity, etCountry, etNewNick, etNewEmail, etNewDescription, etNewBirthday, etInsertPassword;
    ImageButton ivbtnNewFemaleGender, ivbtnNewMaleGender, btnNewDatePicker;
    Button btnSignUp;
    Switch switchNewNewsletter;

    private android.location.LocationManager locationManager;
    private Criteria criteria;
    private String supplier;
    Location userLocation;
    Context context;

    //Global Vars
    String idcity = "";
    String gender = "";
    String newsletter = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_consumer);

        etCity = findViewById(R.id.etCity);
        etCountry = findViewById(R.id.etCountry);
        etNewNick = findViewById(R.id.etNewNick);
        etNewEmail = findViewById(R.id.etNewEmail);
        etNewDescription = findViewById(R.id.etNewDescription);
        etNewBirthday = findViewById(R.id.etNewBirthday);
        etInsertPassword = findViewById(R.id.etInsertPassword);
        btnNewDatePicker = findViewById(R.id.btnNewDatePicker);
        ivbtnNewFemaleGender = findViewById(R.id.ivbtnNewFemaleGender);
        ivbtnNewMaleGender = findViewById(R.id.ivbtnNewMaleGender);
        btnSignUp = findViewById(R.id.btnSignUp);
        switchNewNewsletter = findViewById(R.id.switchNewNewsletter);

        //Check the permissions & verify the service it's already running with isMyServiceRunning method
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        /*
        if (permissionCheck == PackageManager.PERMISSION_GRANTED)
            if(!isMyServiceRunning(ServiceLocation.class))
                Toasty.success(getApplicationContext(), "The Service begin to run!", Toast.LENGTH_SHORT, true).show();
            else
                Toasty.warning(getApplicationContext(), "The it's already running!", Toast.LENGTH_SHORT, true).show();
        */


        context = getApplicationContext();
        try {
            getCity();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Components Methods
        btnNewDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Apply MaterialDateTimePicker android-library https://github.com/wdullaer/MaterialDateTimePicker
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        CreateConsumer.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");

            }
        });

        ivbtnNewFemaleGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivbtnNewMaleGender.setFocusable(false);
                ivbtnNewMaleGender.setFocusableInTouchMode(false);
                ivbtnNewMaleGender.requestFocus();

                ivbtnNewFemaleGender.setFocusable(true);
                ivbtnNewFemaleGender.setFocusableInTouchMode(true);
                ivbtnNewFemaleGender.requestFocus();

                //Set Gender
                gender = "female";
            }
        });


        ivbtnNewMaleGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivbtnNewFemaleGender.setFocusable(false);
                ivbtnNewFemaleGender.setFocusableInTouchMode(false);
                ivbtnNewFemaleGender.requestFocus();

                ivbtnNewMaleGender.setFocusable(true);
                ivbtnNewMaleGender.setFocusableInTouchMode(true);
                ivbtnNewMaleGender.requestFocus();

                // Set Gender
                gender = "male";
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkFields();
            }
        });

        switchNewNewsletter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                newsletter = (b == true) ? "1" : "0";
            }
        });

    }

    public void checkFields(){
        String message = "";

        if(etNewEmail.getText().toString().isEmpty()){
            message = "The email it's obligatory!";
            throwMessage(message);
        }else if(etInsertPassword.getText().toString().isEmpty()){
            message = "You need a password.. No?";
            throwMessage(message);
        }else if(etNewBirthday.getText().toString().isEmpty()){
            message = "Don't you born ?... Fill the Birthday Field!";
            throwMessage(message);
        }else if(idcity.isEmpty()){
            message = "Where you live? ...";
            throwMessage(message);
        }else if(gender.isEmpty()){
            message = "Are you a hermaphrodite? Complete the Gender Field!";
            throwMessage(message);
        }else{
            performInsert();
        }
    }

    public void performInsert(){

        // Request for load the Consumer Image
        String url = getString(R.string.ip) + "/beSustainable/insertConsumer.php";

        CustomRequest customRequest = new CustomRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = response.getJSONArray("request");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    if(jsonObject.getString("message").toString().contains("successfully")){
                        Toasty.success(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT, true).show();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }
                    else{
                        Toasty.error(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT, true).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Exception " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Error to Sign Up.", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nick", etNewNick.getText().toString());
                params.put("idcity", idcity);
                params.put("email", etNewEmail.getText().toString());
                params.put("description", etNewDescription.getText().toString());
                params.put("password", etInsertPassword.getText().toString());
                params.put("birthday", etNewBirthday.getText().toString());
                params.put("gender", gender);
                params.put("newsletter", newsletter);
                return params;
            }

        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(customRequest);

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().toString().equals(service.service.getClassName().toString())) {
                return true;
            }
        }
        return false;
    }

    public void getCity() throws JSONException {

            criteria = new Criteria();
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            supplier = String.valueOf(locationManager.getBestProvider(criteria, true));
            userLocation = getLastKnownLocation();

            //Creamos objeto con esta estructura  {"coordinates":[lon,lat]}
            //  {"coordinates":[-5.5392118,50.1180225]}
            double[] lonlat = {userLocation.getLongitude(), userLocation.getLatitude()};
            JSONArray coordinates = new JSONArray(lonlat);
            Map location = new HashMap();
            location.put("coordinates", coordinates);

            // System.out.println(new JSONObject(location).toString());  OK

            final String URL = "http://80.211.191.91:3001/getcityApp";
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(location), new Response.Listener<JSONObject>() {


                public void onResponse(JSONObject response) {

                    try {

                        //RECUPERO TODO EL ARCHVIO JSON
                        JSONObject jsonObject = new JSONObject(response.toString(0));

                        String city = jsonObject.getString("name");
                        idcity = String.valueOf(jsonObject.getInt("id"));
                        String country = jsonObject.getString("country");

                        etCity.setText(city);
                        etCountry.setText(country);


                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("ERROR  Formato de Json");
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    System.out.println("ERROR    El servidor Node no esta corriendo");

                }

            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("User-agent", "My useragent");
                    return headers;
                }

            };

            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);

        }

    //Con este metodo nos aseguramos que conseguimos la localización siempre.
    private Location getLastKnownLocation() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {

            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    public void throwMessage(String message){
        Toasty.warning(getApplicationContext(), message, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String date = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
        etNewBirthday.setText(date);

    }

}
