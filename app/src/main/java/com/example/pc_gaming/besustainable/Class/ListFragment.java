package com.example.pc_gaming.besustainable.Class;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.pc_gaming.besustainable.Adapters.ProductsAdapter;
import com.example.pc_gaming.besustainable.Entity.Product;
import com.example.pc_gaming.besustainable.Interface.CustomRequest;
import com.example.pc_gaming.besustainable.Interface.ILoadMore;
import com.example.pc_gaming.besustainable.Interface.OnItemClickListener;
import com.example.pc_gaming.besustainable.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListFragment extends Fragment{

    // Main Variables
    ArrayList<Product> listProducts;
    RecyclerView rvProductsList;
    ProgressBar pbLoadProducts;
    int minProduct = 0;
    int maxProduct = 0;
    int maxNumProducts = 61;

    // Volley JsonObjectRequest Post request working with this helper Class
    CustomRequest customRequest;
    ProgressDialog progress;

    // Get the Arguments
    String satisfaction = "";
    String economics = "";
    String totalvote= "";
    String city = "";
    String name = "";
    String category = "";

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

        if(getArguments() != null){
            //Arguments from FilterActivity
            satisfaction = getArguments().getString("satisfaction").toString();
            economics = getArguments().getString("economics").toString();
            totalvote = getArguments().getString("totalvote").toString();
            city = getArguments().getString("city").toString();
            // Search Product by Name
            name = getArguments().getString("name").toString();
            // Filter by Category
            category = getArguments().getString("category").toString();
        }


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

        sacarMaxyMin();

        String url = getString(R.string.ip) + "/beSustainable/loadProducts.php";

        Toast.makeText(getContext(), url, Toast.LENGTH_SHORT).show();

        customRequest = new CustomRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
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
                        product.setSustainableAVG(jsonObject.getDouble("sustainableAVG"));
                        product.setEconomyAVG(jsonObject.getInt("EconomyAVG"));
                        product.setSatisfactionAVG(jsonObject.getInt("satisfactionAVG"));
                        listProducts.add(product);

                    }
                    //progress.hide();
                    final ProductsAdapter adapter = new ProductsAdapter(rvProductsList, getActivity(), listProducts, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Product item) {
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
                            i.putExtra("sustainableAVG", String.valueOf(item.getSustainableAVG()));
                            startActivity(i);
                        }
                    });
                    rvProductsList.setAdapter(adapter);
                    pbLoadProducts.setVisibility(View.INVISIBLE);

                    // Set Load More Event
                    if(json.length() > 5)   // Implement for jump the bug with the loader
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "No es posible conectarse. ", Toast.LENGTH_LONG).show();
                System.out.println();
                pbLoadProducts.setVisibility(View.INVISIBLE);
                Log.d("ERROR: ", error.toString());
                //progress.hide();

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("minProduct", String.valueOf(minProduct));
                params.put("maxProduct", String.valueOf(maxProduct));
                params.put("satisfaction", satisfaction);
                params.put("economics", economics);
                params.put("totalvote", totalvote);
                params.put("city", city);
                params.put("name", name);
                params.put("category", category);

                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(customRequest);

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

}
