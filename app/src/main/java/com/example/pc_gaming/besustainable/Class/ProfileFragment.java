package com.example.pc_gaming.besustainable.Class;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    TextView tvDescription, tvEmail, tvNick, tvCityName;
    EditText etBirthday;
    ImageView ivProfile, ivGender;
    Switch switchNewsletter;

    // ID_CONSUMER
    private String ID_CONSUMER = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_profile, container, false);

        tvDescription = rootView.findViewById(R.id.tvDescription);
        tvEmail = rootView.findViewById(R.id.tvEmail);
        tvNick = rootView.findViewById(R.id.tvNick);
        etBirthday = rootView.findViewById(R.id.etBirthday);
        ivProfile = rootView.findViewById(R.id.ivProfile);
        ivGender = rootView.findViewById(R.id.ivGender);
        switchNewsletter = rootView.findViewById(R.id.switchNewsletter);
        tvCityName = rootView.findViewById(R.id.tvCity);

        //GetSharedPreferences if exist the "Consumer" Key
        File filePreferences = new File("/data/data/com.example.pc_gaming.besustainable/shared_prefs/" + getString(R.string.preference_file_key) + ".xml");
        if(!filePreferences.exists()){
            Intent i = new Intent(rootView.getContext(), OptionLoginActivity.class);
            startActivity(i);
            return null;
            // Avoid the anterior Activity & finalize the App.
            //getActivity().finish();
        }else{
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("Consumer", "");
            Consumer consumer = gson.fromJson(json, Consumer.class);


            ID_CONSUMER = String.valueOf(consumer.getIdConsumer());

            setTextViewConsumerFields(consumer);

        }
        // Inflate the layout for this fragment
        return rootView;
    }

    public void setTextViewConsumerFields(Consumer consumer){

        tvNick.setText(consumer.getNick().toString());
        tvDescription.setText(consumer.getDescription().toString());
        tvCityName.setText(consumer.getCityName().toString());
        tvEmail.setText(consumer.getEmail().toString());
        etBirthday.setText(consumer.getBirthday().toString());
        switchNewsletter.setChecked(consumer.isNewsletter());


        loadGenderIcon(consumer.getGender().toString());
        loadImageProfile();

    }

    public void loadGenderIcon(String gender){

        int genderImage = (gender.equals("male")) ? R.drawable.male : R.drawable.female;

        Bitmap bmGender = BitmapFactory.decodeResource(getResources(), genderImage);

        ivGender.setImageBitmap(bmGender);


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

                        int height=300;  // Photo Pixel Height
                        int width=350;  //Photo Pixel Width

                        Bitmap photo= BitmapFactory.decodeByteArray(byteCode,0, byteCode.length);
                        Bitmap bitmapProfile = Bitmap.createScaledBitmap(photo, width, height,true);

                        ivProfile.setImageBitmap(bitmapProfile);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Exception " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Error to load the Profile Image.", Toast.LENGTH_SHORT).show();

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
        VolleySingleton.getInstance(getContext()).addToRequestQueue(customRequest);

    }


}
