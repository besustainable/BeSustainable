package com.example.pc_gaming.besustainable.Class;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.pc_gaming.besustainable.R;

import es.dmoral.toasty.Toasty;

public class SearchActivity extends AppCompatActivity {

    EditText etSearchProduct;
    ImageButton ibtnSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etSearchProduct = findViewById(R.id.etSearch);
        ibtnSearch = findViewById(R.id.ibtnSearch);

        ListFragment myFragment = new ListFragment();
        final FragmentManager fragmentManager = getSupportFragmentManager();

        final FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.content_fragment_search, myFragment).commitAllowingStateLoss();

        ibtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Send the product name to loadProducts.php
                if(!etSearchProduct.getText().toString().equals("")){

                    String nameProduct = etSearchProduct.getText().toString();

                    Fragment currentFragment = new ListFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content_fragment_search, currentFragment);
                    fragmentTransaction.commitAllowingStateLoss();


                    Bundle bundle = new Bundle();
                    bundle.putString("name", nameProduct);
                    //Send the other variables Empty
                    bundle.putString("satisfaction", "");
                    bundle.putString("economics", "");
                    bundle.putString("totalvote", "");
                    bundle.putString("city", "");
                    bundle.putString("category", "");
                    currentFragment.setArguments(bundle);


                }else {
                    Toasty.warning(getApplicationContext(), "Enter a Product Name!", Toast.LENGTH_SHORT, true).show();
                }

            }
        });


    }
}
