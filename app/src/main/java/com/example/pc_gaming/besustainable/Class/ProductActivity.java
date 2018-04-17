package com.example.pc_gaming.besustainable.Class;

import android.content.Intent;
import android.graphics.Bitmap;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView tvProductName = findViewById(R.id.tvProductName);
        TextView tvPvp = findViewById(R.id.tvPvp);
        TextView tvWeight = findViewById(R.id.tvWeight);
        TextView tvDescription = findViewById(R.id.tvDescription);
        TextView tvCategory = findViewById(R.id.tvCategory);
        TextView tvHeadquarter = findViewById(R.id.tvHeadquarter);
        TextView tvPlant = findViewById(R.id.tvPlant);
        ImageView ivImageProduct = findViewById(R.id.ivImageProduct);

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

        //Set data in Layout
        ivImageProduct.setImageBitmap(bitmap);
        tvProductName.setText(name);
        tvPvp.setText(String.valueOf(pvp + " " + "€"));
        tvWeight.setText(String.valueOf(weight) + " " + measure);
        tvCategory.setText(categoryName);
        tvPlant.setText(plant_name);
        tvHeadquarter.setText(hq_name);
        tvDescription.setText(description);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Rate", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
