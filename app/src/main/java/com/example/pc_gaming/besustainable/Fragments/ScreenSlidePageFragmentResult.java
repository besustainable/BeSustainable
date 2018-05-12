package com.example.pc_gaming.besustainable.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.pc_gaming.besustainable.Class.MainActivity;
import com.example.pc_gaming.besustainable.Class.RatingsImage;
import com.example.pc_gaming.besustainable.Class.VolleySingleton;
import com.example.pc_gaming.besustainable.Entity.Consumer;
import com.example.pc_gaming.besustainable.R;
import com.google.gson.Gson;
import com.warkiz.widget.IndicatorSeekBar;

import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import static com.example.pc_gaming.besustainable.Class.ProductActivity.ID_PRODUCT;
import static com.example.pc_gaming.besustainable.Fragments.ScreenSlidePageFragment1.resultAnswer1;
import static com.example.pc_gaming.besustainable.Fragments.ScreenSlidePageFragment2.resultAnswer2;
import static com.example.pc_gaming.besustainable.Fragments.ScreenSlidePageFragment3.resultAnswer3;
import static com.example.pc_gaming.besustainable.Fragments.ScreenSlidePageFragment4.resultAnswer4;
import static com.example.pc_gaming.besustainable.Fragments.ScreenSlidePageFragment5.resultAnswer5;
import static com.example.pc_gaming.besustainable.Fragments.ScreenSlidePageFragment6.resultAnswer6;
import static com.example.pc_gaming.besustainable.Fragments.ScreenSlidePageFragment7.resultAnswer7;
import static com.example.pc_gaming.besustainable.Fragments.ScreenSlidePageFragment8.resultAnswer8;
import static com.example.pc_gaming.besustainable.Fragments.ScreenSlidePageFragment9.resultAnswer9;
import static com.example.pc_gaming.besustainable.Fragments.ScreenSlidePageFragment10.resultAnswer10;


public class ScreenSlidePageFragmentResult extends Fragment {

    private ImageView ivEconomy;
    private ImageView ivSatisfaction;
    private ImageView iv_Un_environment;
    private ImageView iv_BeSustainable;;
    private ImageView ivRating;

    private IndicatorSeekBar satisfaction_indicatorSeekBar;
    private IndicatorSeekBar economy_indicatorSeekBar;

    private TextView tvVoteAVG;
    private Button btnVote;

    RatingsImage ratingsImage;

    // Total Variable
    double totalAV = 0;

    private String ID_CONSUMER = "";


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page_result, container, false);

        // Initialize the RatingsImage Class
        ratingsImage = new RatingsImage();

        // Assignation's
        iv_Un_environment = rootView.findViewById(R.id.iv_Un_environment);
        iv_BeSustainable = rootView.findViewById(R.id.iv_BeSustainable);
        ivEconomy = rootView.findViewById(R.id.ivEconomy);
        ivSatisfaction = rootView.findViewById(R.id.ivSatisfaction);
        economy_indicatorSeekBar = rootView.findViewById(R.id.economy_indicatorseekBar);
        satisfaction_indicatorSeekBar = rootView.findViewById(R.id.satisfaction_indicatorseekBar);
        ivRating = rootView.findViewById(R.id.ivRating);
        tvVoteAVG = rootView.findViewById(R.id.tvVoteAVG);
        btnVote = rootView.findViewById(R.id.btnVote);


        // Sets
        iv_Un_environment.setImageResource(R.drawable.un_environment_icon);
        iv_BeSustainable.setImageResource(R.drawable.header);
        ivEconomy.setImageResource(R.drawable.economy);
        ivSatisfaction.setImageResource(R.drawable.satisfaction);
        ivRating.setImageResource(R.drawable.rating);

        // Set the Total Argued Vote
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        totalAV = resultAnswer1 + resultAnswer2 + resultAnswer3 + resultAnswer4 + resultAnswer5 + resultAnswer6 + resultAnswer7 + resultAnswer8 + resultAnswer9 + resultAnswer10;
        totalAV /= 100;

        tvVoteAVG.setText(String.valueOf(df.format(totalAV)));

        // Get ID_CONSUMER
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Consumer", "");
        Consumer consumer = gson.fromJson(json, Consumer.class);
        ID_CONSUMER = String.valueOf(consumer.getIdConsumer());


        // Indicator_SeekBar Methods

        satisfaction_indicatorSeekBar.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {

                Bitmap bmSatisfaction = BitmapFactory.decodeResource(getResources(), ratingsImage.satisfactionRate(seekBar.getProgress()));
                ivSatisfaction.setImageBitmap(bmSatisfaction);
            }

            @Override
            public void onSectionChanged(IndicatorSeekBar seekBar, int thumbPosOnTick, String textBelowTick, boolean fromUserTouch) {

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar, int thumbPosOnTick) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });


        economy_indicatorSeekBar.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {

                Bitmap bmEconomy = BitmapFactory.decodeResource(getResources(), ratingsImage.economyRate(seekBar.getProgress()));
                ivEconomy.setImageBitmap(bmEconomy);

            }

            @Override
            public void onSectionChanged(IndicatorSeekBar seekBar, int thumbPosOnTick, String textBelowTick, boolean fromUserTouch) {

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar, int thumbPosOnTick) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });


        // Vote Button Action Method
        // Get the IDConsumer & IDProduct for Insert SustainableAVG, SatisfactionAVG, EconomyAVG
        // Send POST request for perform this operation

        btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String url = getString(R.string.ip) + "/beSustainable/insertVote.php";

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // AlertDialog Succesfully
                        AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
                        builder.setTitle("Vote Succesfuly!")
                                .setMessage(getString(R.string.message_vote))
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(rootView.getContext(), MainActivity.class);
                                        startActivity(intent);
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        // Posting params to request url
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("idproduct", ID_PRODUCT);
                        params.put("idconsumer", ID_CONSUMER); // Change User Shared Preferences ?
                        params.put("sustainableAVG", String.valueOf(totalAV));
                        params.put("economyAVG", String.valueOf(economy_indicatorSeekBar.getProgress()));
                        params.put("satisfactionAVG", String.valueOf(satisfaction_indicatorSeekBar.getProgress()));
                        return params;
                    }
                };
                //Adding request to request queue
                VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
            }
        });


        return rootView;
    }
}