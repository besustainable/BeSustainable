package com.example.pc_gaming.besustainable.Class;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;

public class ListFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    // Main Variables
    ArrayList<Product> listProducts;
    RecyclerView rvProductsList;
    ProgressBar pbLoadProducts;
    int minProduct = 0;
    int maxProduct = 0;
    int maxNumProducts = 61;

    JsonObjectRequest jsonObjectRequest;
    ProgressDialog progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_list, container, false);

        listProducts = new ArrayList<Product>();

        pbLoadProducts = rootView.findViewById(R.id.pbLoadProducts);
        rvProductsList = rootView.findViewById(R.id.rvProductsList);
        rvProductsList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvProductsList.setHasFixedSize(true);

        cargarWebService();

        // Inflate the layout for this fragment
        return rootView;
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

        Toast.makeText(getContext(), url, Toast.LENGTH_SHORT).show();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
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
    public void onResponse(JSONObject response) {

        JSONArray json = response.optJSONArray("product");

        try {

            for(int i = 0; i < json.length(); i++){
                JSONObject jsonObject = json.getJSONObject(i);


                Product product = new Product();
                product.setIdProduct(jsonObject.getInt("idproduct"));
                product.setName(jsonObject.optString("name"));
                product.setDescription(jsonObject.getString("description"));
                product.setWeight(jsonObject.getDouble("weight"));
                product.setPvp(jsonObject.getDouble("pvp"));
                product.setCategoryName(jsonObject.getString("Category_Name"));
                product.setMeasure(jsonObject.getString("Measure"));
                product.setPlantName(jsonObject.getString("Plant_Name"));
                product.setHqName(jsonObject.getString("Hq_Name"));
                product.setImg(jsonObject.getString("img"));
                product.setEconomyAVG(jsonObject.getInt("EconomyAVG"));
                product.setSatisfactionAVG(jsonObject.getInt("satisfactionAVG"));
                listProducts.add(product);

            }
            //progress.hide();
            final ProductsAdapter adapter = new ProductsAdapter(rvProductsList, getActivity(), listProducts, new OnItemClickListener() {
                @Override
                public void onItemClick(Product item) {
                    Toast.makeText(getContext(), "FUNCIONA!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), ProductActivity.class);

                    // I can't put the object because the image needs a special method for catch from the other side...
                    i.putExtra("imageProduct", item.getImg());
                    i.putExtra("nameProduct", item.getName().toString());
                    i.putExtra("categoryProduct", item.getIdCategory());
                    i.putExtra("pvpProduct", item.getPvp());
                    i.putExtra("weightProduct", item.getWeight());
                    i.putExtra("descriptionProduct", item.getDescription().toString());
                    i.putExtra("category_name", item.getCategoryName().toString());
                    i.putExtra("measure", item.getMeasure().toString());
                    i.putExtra("plant_name", item.getPlantName().toString());
                    i.putExtra("hq_name", item.getHqName().toString());
                    i.putExtra("satisfactionRate", item.getSatisfactionAVG());
                    i.putExtra("economyRate", item.getEconomyAVG());
                    i.putExtra("idproduct", String.valueOf(item.getIdProduct()));
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
                        Toast.makeText(getContext(), "Load Data Completed!", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } catch (JSONException e) {
            Toast.makeText(getContext(), "Error al cargar los Productos...", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            //progress.hide();
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "No es posible conectarse. ", Toast.LENGTH_LONG).show();
        System.out.println();
        pbLoadProducts.setVisibility(View.INVISIBLE);
        Log.d("ERROR: ", error.toString());
        //progress.hide();
    }


}