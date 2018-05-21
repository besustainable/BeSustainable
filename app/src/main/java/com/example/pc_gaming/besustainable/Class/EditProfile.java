package com.example.pc_gaming.besustainable.Class;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.pc_gaming.besustainable.Entity.Consumer;
import com.example.pc_gaming.besustainable.Interface.CustomRequest;
import com.example.pc_gaming.besustainable.R;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class EditProfile extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    //Components
    ImageButton btnDatePicker, ivbtnFemaleGender, ivbtnMaleGender;
    EditText etNickEdit, etDescriptionEdit, etEmailEdit, etBirthdayEdit;
    ImageView ivProfileEdit, ivbtnImageEdit;
    Button btnUpdate, btnPassword;
    Spinner spinnerCountries;
    AutoCompleteTextView autoetEditCity;

    static int GET_FROM_GALLERY = 3;

    final int HEIGHT_MIPMAP = 200;  // Photo Pixel Height
    final int WIDTH_MIPMAP = 200;   //Photo Pixel Width

    //Global Variables
    String ID_CONSUMER, gender, nick, description, city, email, birthday;

    ArrayAdapter<String> adapterCities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().hide();

        // Find Components Layout
        btnDatePicker = findViewById(R.id.btnDatePicker);
        ivbtnFemaleGender = findViewById(R.id.ivbtnFemaleGender);
        ivbtnMaleGender = findViewById(R.id.ivbtnMaleGender);
        etNickEdit = findViewById(R.id.etNickEdit);
        etDescriptionEdit = findViewById(R.id.etDescriptionEdit);
        etEmailEdit = findViewById(R.id.etEmailEdit);
        etBirthdayEdit = findViewById(R.id.etBirthdayEdit);
        ivProfileEdit = findViewById(R.id.ivProfileEdit);
        ivbtnImageEdit = findViewById(R.id.ivbtnImageEdit);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnPassword = findViewById(R.id.btnPassword);
        spinnerCountries = findViewById(R.id.spinnerCountry);
        autoetEditCity = findViewById(R.id.autoetEditCity);

        //Disabled EtCity by default
        autoetEditCity.setEnabled(false);

        loadDataConsumerPreferences();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nick = etNickEdit.getText().toString();
                description = etDescriptionEdit.getText().toString();
                email = etEmailEdit.getText().toString();
                birthday = etBirthdayEdit.getText().toString();

                performUpdate();
                editSharedPreferences();

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

            }
        });

        btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PasswordActivity.class);
                startActivity(i);
            }
        });

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Apply MaterialDateTimePicker android-library https://github.com/wdullaer/MaterialDateTimePicker
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        EditProfile.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");

            }
        });

        ivbtnFemaleGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivbtnMaleGender.setFocusable(false);
                ivbtnMaleGender.setFocusableInTouchMode(false);
                ivbtnMaleGender.requestFocus();

                ivbtnFemaleGender.setFocusable(true);
                ivbtnFemaleGender.setFocusableInTouchMode(true);
                ivbtnFemaleGender.requestFocus();

                //Set Gender
                gender = "female";
            }
        });


        ivbtnMaleGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivbtnFemaleGender.setFocusable(false);
                ivbtnFemaleGender.setFocusableInTouchMode(false);
                ivbtnFemaleGender.requestFocus();

                ivbtnMaleGender.setFocusable(true);
                ivbtnMaleGender.setFocusableInTouchMode(true);
                ivbtnMaleGender.requestFocus();

                // Set Gender
                gender = "male";
            }
        });

        ivbtnImageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);

            }
        });

        // Load the Countries in the Spinner

        //List of Countries
        final List<String> listCountries = loadCountries();


        adapterCities = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listCountries);
        adapterCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountries.setAdapter(adapterCities);


        spinnerCountries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //Toast.makeText(getApplicationContext(), listCountries.get(i).toUpperCase(), Toast.LENGTH_SHORT).show();
                /**
                 * TO DO:
                 * loadCities() method
                 */
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmapGallery  = null;
            try {
                bitmapGallery = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                Bitmap bitmap = Bitmap.createScaledBitmap(bitmapGallery, WIDTH_MIPMAP, HEIGHT_MIPMAP, true);
                ivProfileEdit.setImageBitmap(bitmap);

                //Encode Bitmap to Base64 format
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] byteArrayImage = baos.toByteArray();

                String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
                Log.d("IMAGE", encodedImage);

                // Perform the Update of the Image
                performUpdateImage(encodedImage);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void loadDataConsumerPreferences(){

        //Get Shared Preferences
        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Consumer", "");
        Consumer consumer = gson.fromJson(json, Consumer.class);

        //Set the Values of the COnsumer in the Layout
        etNickEdit.setText(consumer.getNick().toString());
        etDescriptionEdit.setText(consumer.getDescription().toString());
        etEmailEdit.setText(consumer.getEmail().toString());
        etBirthdayEdit.setText(consumer.getBirthday().toString());

        // Set Button Focus by Default
        switch (consumer.getGender().toLowerCase()){
            case "female":
                ivbtnFemaleGender.setFocusable(true);
                ivbtnFemaleGender.setFocusableInTouchMode(true);
                ivbtnFemaleGender.requestFocus();
                gender = "female";
                break;
            case "male":
                ivbtnMaleGender.setFocusable(true);
                ivbtnMaleGender.setFocusableInTouchMode(true);
                ivbtnMaleGender.requestFocus();
                gender = "male";
                break;
        }

        ID_CONSUMER = String.valueOf(consumer.getIdConsumer());

        loadImageProfile();


    }

    public void loadImageProfile(){

        // Request for load the Consumer Image
        String url = getString(R.string.ip) + "/beSustainable/loadProfileImage.php";

        CustomRequest customRequest = new CustomRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = response.getJSONArray("image");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    if(!jsonObject.getString("img").equals("")){
                        byte[] byteCode= Base64.decode(jsonObject.getString("img").toString(),Base64.DEFAULT);

                        Bitmap photo= BitmapFactory.decodeByteArray(byteCode,0, byteCode.length);
                        Bitmap bitmapProfile = Bitmap.createScaledBitmap(photo, WIDTH_MIPMAP, HEIGHT_MIPMAP,true);

                        ivProfileEdit.setImageBitmap(bitmapProfile);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toasty.error(getApplicationContext(), "Exception " + e.getMessage(), Toast.LENGTH_SHORT, true).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toasty.error(getApplicationContext(), "Error to load the Profile Image.", Toast.LENGTH_SHORT, true).show();

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
                return params;
            }

        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(customRequest);

    }

    public void performUpdate(){

        // Request for load the Consumer Image
        String url = getString(R.string.ip) + "/beSustainable/updateConsumer.php";

        CustomRequest customRequest = new CustomRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = response.getJSONArray("request");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    Toasty.success(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT, true).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toasty.error(getApplicationContext(), "Exception " + e.getMessage(), Toast.LENGTH_SHORT, true).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toasty.error(getApplicationContext(), "Error to perform the Update.", Toast.LENGTH_SHORT, true).show();

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
                params.put("nick", nick);
                params.put("description", description);
                params.put("email", email);
                params.put("birthday", birthday);
                params.put("gender", gender);
                return params;
            }

        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(customRequest);

    }

    public void editSharedPreferences(){

        // Get the latest Shared Preferences
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Consumer", "");
        Consumer consumer = gson.fromJson(json, Consumer.class);

        //New SP object for updated
        SharedPreferences mPrefs = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gsonEdit = new Gson();

        //Sets
        consumer.setNick(nick);
        consumer.setDescription(description);
        consumer.setBirthday(Date.valueOf(birthday));
        consumer.setGender(gender);
        consumer.setEmail(email);

        //Cast to Json & Commit
        String jsonEdit = gsonEdit.toJson(consumer);
        prefsEditor.putString("Consumer", jsonEdit);
        prefsEditor.commit();


    }

    public void performUpdateImage(final String imageEncode){

        // Request for load the Consumer Image
        String url = getString(R.string.ip) + "/beSustainable/updateImageProfile.php";

        CustomRequest customRequest = new CustomRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = response.getJSONArray("request");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    Toasty.success(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT, true).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toasty.error(getApplicationContext(), "Exception " + e.getMessage(), Toast.LENGTH_SHORT, true).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toasty.error(getApplicationContext(), "Error to Update Image Profile.", Toast.LENGTH_SHORT, true).show();

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
                params.put("img", imageEncode);
                return params;
            }

        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(customRequest);

    }

    public List<String> loadCountries(){

        String url = getString(R.string.ip) + "/beSustainable/loadCountries.php";

        final List<String> list = new ArrayList<String>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray jsonArray = response.optJSONArray("countries");

                try {

                    for (int i = 0; i < jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String country = jsonObject.getString("idcountry");
                        list.add(country);
                    }

                    //Notify the adapter
                    adapterCities.notifyDataSetChanged();

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
        return list;

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String date = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
        etBirthdayEdit.setText(date);

    }
}
