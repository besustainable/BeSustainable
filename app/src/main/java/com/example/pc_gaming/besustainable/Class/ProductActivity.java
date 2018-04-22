package com.example.pc_gaming.besustainable.Class;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc_gaming.besustainable.Entity.Product;
import com.example.pc_gaming.besustainable.R;

public class ProductActivity extends AppCompatActivity {

    TextView tvProductName, tvPvp, tvWeight, tvDescription, tvCategory, tvHeadquarter, tvPlant;
    ImageView ivImageProduct, ivsatisfactionVote, iveconomyVote;

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

        // ImageView Ratings & Class to Set this Votes/Rates Images
        ivsatisfactionVote = findViewById(R.id.ivsatisfactionVote);
        iveconomyVote = findViewById(R.id.iveconomyVote);

        RatingsImage ratingsImage = new RatingsImage();


        //Collect the data from MainActivity & Adapter Event
        Bitmap bitmap = getIntent().getParcelableExtra("imageProduct");
        String name = getIntent().getExtras().getString("nameProduct");
        double pvp = getIntent().getExtras().getDouble("pvpProduct");
        double weight = getIntent().getExtras().getDouble("weightProduct");
        String categoryName = getIntent().getExtras().getString("category_name");
        String measure = getIntent().getExtras().getString("measure");
        String plant_name = getIntent().getExtras().getString("plant_name");
        String hq_name = getIntent().getExtras().getString("hq_name");
        String description = getIntent().getExtras().getString("descriptionProduct");
        int satisfactionRate = getIntent().getExtras().getInt("satisfactionRate");
        int economyRate = getIntent().getExtras().getInt("economyRate");

        //Set data in Layout
        ivImageProduct.setImageBitmap(bitmap);
        tvProductName.setText(name);
        tvPvp.setText(String.valueOf(pvp + " " + "€"));
        tvWeight.setText(String.valueOf(weight) + " " + measure);
        tvCategory.setText(categoryName);
        tvPlant.setText(plant_name);
        tvHeadquarter.setText(hq_name);
        tvDescription.setText(description);

        // Set the Votes Images
        Bitmap bmSatisfaction = BitmapFactory.decodeResource(getResources(), ratingsImage.satisfactionRate(satisfactionRate));
        Bitmap bmEconomy = BitmapFactory.decodeResource(getResources(), ratingsImage.economyRate(economyRate));

        ivsatisfactionVote.setImageBitmap(bmSatisfaction);
        iveconomyVote.setImageBitmap(bmEconomy);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Rate", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent i = new Intent(getApplicationContext(), ScreenSlidePagerActivity.class);
                startActivity(i);


            }
        });
    }
}
