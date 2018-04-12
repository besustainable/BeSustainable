package com.example.pc_gaming.besustainable.Class;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.pc_gaming.besustainable.Adapters.ProductsAdapter;
import com.example.pc_gaming.besustainable.Entity.Product;
import com.example.pc_gaming.besustainable.Interface.ILoadMore;
import com.example.pc_gaming.besustainable.Interface.OnItemClickListener;
import com.example.pc_gaming.besustainable.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Response.Listener<JSONObject>, Response.ErrorListener {

    ArrayList<Product> listProducts;
    RecyclerView rvProductsList;
    ProgressBar pbLoadProducts;
    int minProduct = 0;
    int maxProduct = 0;
    int maxNumProducts = 61;

    JsonObjectRequest jsonObjectRequest;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listProducts = new ArrayList<Product>();

        pbLoadProducts = findViewById(R.id.pbLoadProducts);
        rvProductsList = findViewById(R.id.rvProductsList);
        rvProductsList.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        rvProductsList.setHasFixedSize(true);

        cargarWebService();

    }

    public void cargarWebService(){
/*
        progress=new ProgressDialog(getApplicationContext());
        progress.setMessage("Consultando...");
        progress.show();
        */

        String ip = getString(R.string.ip);

        sacarMaxyMin();

        String url = ip + "/beSustainable/loadProducts.php?minProduct=" + minProduct + "&maxProduct=" + maxProduct;

        Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void sacarMaxyMin(){

        if(maxProduct + 20 >= maxNumProducts){   // Max Variable
            maxProduct = maxNumProducts;
        }else
            maxProduct += 20;

        if(maxProduct == maxNumProducts){
            int resto = 0;
            while(resto >= 0){
                resto = maxNumProducts - 20;
                if(resto < 20)
                    break;
            }
            minProduct = resto;
        }else if(maxProduct == 20)
            minProduct = 0;
        else
            minProduct += 20;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Override Volley Methods

    @Override
    public void onResponse(JSONObject response) {

        JSONArray json = response.optJSONArray("product");

        try {

            for(int i = 0; i < json.length(); i++){
                    JSONObject jsonObject = json.getJSONObject(i);


                Product product = new Product();
                product.setName(jsonObject.optString("name"));
                product.setDescription(jsonObject.getString("description"));
                product.setWeight(jsonObject.getDouble("weight"));
                product.setPvp(jsonObject.getDouble("pvp"));
                product.setImg(jsonObject.getString("img"));
                listProducts.add(product);

            }
            //progress.hide();
            final ProductsAdapter adapter = new ProductsAdapter(rvProductsList, this, listProducts, new OnItemClickListener() {
                @Override
                public void onItemClick(Product item) {
                    Toast.makeText(getApplicationContext(), "FUNCIONA!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), ProductActivity.class);
                    i.putExtra("product", item.getName().toString());
                    startActivity(i);
                }
            });
            rvProductsList.setAdapter(adapter);
            pbLoadProducts.setVisibility(View.INVISIBLE);

            // Set Load More Event
            adapter.setLoadMore(new ILoadMore() {
                @Override
                public void onLoadMore() {
                    if(listProducts.size() < maxNumProducts) {     //Max Items in List
                        listProducts.add(null);
                        adapter.notifyItemInserted(listProducts.size());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                cargarWebService();
                                adapter.notifyDataSetChanged();
                                adapter.setLoaded();

                            }
                        }, 2000);
                    }else{
                            Toast.makeText(getApplicationContext(), "Load Data Completed!", Toast.LENGTH_SHORT).show();
                        }
                }
            });


        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error al cargar los Productos...", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            //progress.hide();
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "No es posible conectarse. ", Toast.LENGTH_LONG).show();
        System.out.println();
        pbLoadProducts.setVisibility(View.INVISIBLE);
        Log.d("ERROR: ", error.toString());
        //progress.hide();
    }

}
