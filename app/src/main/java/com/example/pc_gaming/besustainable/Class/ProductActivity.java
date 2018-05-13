package com.example.pc_gaming.besustainable.Class;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc_gaming.besustainable.Entity.Product;
import com.example.pc_gaming.besustainable.R;

import java.io.File;

public class ProductActivity extends AppCompatActivity {

    TextView tvProductName, tvPvp, tvWeight, tvDescription, tvCategory, tvHeadquarter, tvPlant, tvRatingProduct;
    ImageView ivImageProduct, ivsatisfactionVote, iveconomyVote;
    ImageButton ibtnFacebook, ibtnEmail, ibtnTwitter, ibtnInstagram, ibtnWhatsapp;
    public static String ID_PRODUCT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvProductName = findViewById(R.id.tvProductName);
        tvPvp = findViewById(R.id.tvPvp);
        tvWeight = findViewById(R.id.tvWeight);
        tvDescription = findViewById(R.id.tvDescription);
        tvCategory = findViewById(R.id.tvCategory);
        tvHeadquarter = findViewById(R.id.tvHeadquarter);
        tvPlant = findViewById(R.id.tvPlant);
        ivImageProduct = findViewById(R.id.ivImageProduct);
        tvRatingProduct = findViewById(R.id.tvRatingProduct);

        //Image Buttons
        ibtnFacebook = findViewById(R.id.ibFacebook);
        ibtnEmail = findViewById(R.id.ibEmail);
        ibtnTwitter = findViewById(R.id.ibTwitter);
        ibtnInstagram = findViewById(R.id.ibInstagram);
        ibtnWhatsapp = findViewById(R.id.ibWhatsapp);

        // ImageView Ratings & Class to Set this Votes/Rates Images
        ivsatisfactionVote = findViewById(R.id.ivsatisfactionVote);
        iveconomyVote = findViewById(R.id.iveconomyVote);

        RatingsImage ratingsImage = new RatingsImage();


        //Collect the data from MainActivity & Adapter Event
        // Implemnt Parcelable
        ID_PRODUCT = getIntent().getExtras().getString("idproduct");
        Bitmap bitmap = getIntent().getParcelableExtra("imageProduct");
        final String name = getIntent().getExtras().getString("nameProduct");
        double pvp = getIntent().getExtras().getDouble("pvpProduct");
        double weight = getIntent().getExtras().getDouble("weightProduct");
        final String categoryName = getIntent().getExtras().getString("category_name");
        String measure = getIntent().getExtras().getString("measure");
        String plant_name = getIntent().getExtras().getString("plant_name");
        String hq_name = getIntent().getExtras().getString("hq_name");
        String description = getIntent().getExtras().getString("descriptionProduct");
        final int satisfactionRate = getIntent().getExtras().getInt("satisfactionRate");
        final int economyRate = getIntent().getExtras().getInt("economyRate");
        final String sustainableAVG = getIntent().getExtras().getString("sustainableAVG");


        //Set data in Layout
        ivImageProduct.setImageBitmap(bitmap);
        tvProductName.setText(name);
        tvPvp.setText(String.valueOf(pvp + " " + "€"));
        tvWeight.setText(String.valueOf(weight) + " " + measure);
        tvCategory.setText(categoryName.toUpperCase());
        tvPlant.setText(plant_name);
        tvHeadquarter.setText(hq_name);
        tvDescription.setText(description);
        tvRatingProduct.setText(sustainableAVG);

        // Set the Votes Images
        Bitmap bmSatisfaction = BitmapFactory.decodeResource(getResources(), ratingsImage.satisfactionRate(satisfactionRate));
        Bitmap bmEconomy = BitmapFactory.decodeResource(getResources(), ratingsImage.economyRate(economyRate));

        ivsatisfactionVote.setImageBitmap(bmSatisfaction);
        iveconomyVote.setImageBitmap(bmEconomy);

        //Messages to Share
        final String sharedMessageWhatsapp = "*" + name + "*" + " \n" + "_" + categoryName.toUpperCase() + "_" + "\n" + satisfactionRate + "⭐  " + economyRate + " \uD83D\uDCB6  " + "Total Vote: " + sustainableAVG.toString() +  " \uD83D\uDD25 \n" + "\uD83D\uDDF3️ Vote Now!! \uD83D\uDDF3️";
        final String sharedMessage = name + " \n" + categoryName.toUpperCase() + "\n" + satisfactionRate + "⭐  " + economyRate + " \uD83D\uDCB6  " + "Total Vote: " + sustainableAVG.toString() +  " \uD83D\uDD25 \n" + " Vote Now!! ";


        //Social Buttons Click Event Listener

        ibtnWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, sharedMessageWhatsapp);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");

                try {
                    startActivity(sendIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(),"You don't have this application installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ibtnTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, sharedMessage);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.twitter.android");

                try {
                    startActivity(sendIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(),"You don't have this application installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ibtnInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, sharedMessage);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.instagram.android");

                try {
                    startActivity(sendIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(),"You don't have this application installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ibtnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, sharedMessage);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.facebook.katana");


                try {
                    startActivity(sendIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(),"You don't have this application installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        ibtnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] TO = {""}; //aquí pon tu correo
                String[] CC = {""};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, CC);
                // Esto podrás modificarlo si quieres, el asunto y el cuerpo del mensaje
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "BESUSTAINABLE");
                emailIntent.putExtra(Intent.EXTRA_TEXT, sharedMessage);

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(),"You don't have this application installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Rate", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent i;
                File filePreferences = new File("/data/data/com.example.pc_gaming.besustainable/shared_prefs/" + getString(R.string.preference_file_key) + ".xml");
                if(!filePreferences.exists())
                    i = new Intent(getApplicationContext(), OptionLoginActivity.class);
                else
                    i = new Intent(getApplicationContext(), ScreenSlidePagerActivity.class);

                startActivity(i);


            }
        });
    }
}
