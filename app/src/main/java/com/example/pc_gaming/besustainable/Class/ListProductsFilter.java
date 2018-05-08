package com.example.pc_gaming.besustainable.Class;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.pc_gaming.besustainable.R;

public class ListProductsFilter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products_filter);

        ListFragment myFragment = new ListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.fragment_list_products, myFragment).commitAllowingStateLoss();

        // If fragment it's ListFragment
        if(getIntent().getExtras() != null){

            Bundle bundle = new Bundle();
            bundle.putString("satisfaction", getIntent().getExtras().getString("satisfaction"));
            bundle.putString("economics", getIntent().getExtras().getString("economics"));
            bundle.putString("totalvote", getIntent().getExtras().getString("totalvote"));
            bundle.putString("city", getIntent().getExtras().getString("city"));
            bundle.putString("category", getIntent().getExtras().getString("category"));
            // Send name empty
            bundle.putString("name", "");
            myFragment.setArguments(bundle);
        }
    }
}
