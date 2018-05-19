package com.example.pc_gaming.besustainable.Class;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.pc_gaming.besustainable.Adapters.MyInfiniteListAdapter;
import com.example.pc_gaming.besustainable.Entity.Product;
import com.example.pc_gaming.besustainable.Interface.CustomRequest;
import com.example.pc_gaming.besustainable.R;
import com.softw4re.views.InfiniteListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class ListFragment extends Fragment{

    // Main Variables
    private LinearLayout container;
    private InfiniteListView<Product> infiniteListView;

    private ArrayList<Product> itemList;
    private MyInfiniteListAdapter<Product> adapter;

    int limit = 0;
    int maxNumProducts = 0;
    /**
     * Set the list number products in each load
     */
    final int NUMBER_LOAD_PRODUCTS = 20;

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

        // Set Max Number of Products in the List
        setSizeList();

        infiniteListView = (InfiniteListView) rootView.findViewById(R.id.infiniteListView);

        itemList = new ArrayList<Product>();
        adapter = new MyInfiniteListAdapter(this, R.layout.item_products_list, itemList);

        infiniteListView.setAdapter(adapter);

        infiniteListView.startLoading();

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

        loadProducts();
        // Inflate the layout for this fragment
        return rootView;
    }

    public void loadProducts(){


        infiniteListView.startLoading();

        String url = getString(R.string.ip) + "/beSustainable/loadProducts.php";

        //Toast.makeText(getContext(), url, Toast.LENGTH_SHORT).show();

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
                        //Rounded
                        DecimalFormat df = new DecimalFormat("#.#");
                        df.setRoundingMode(RoundingMode.CEILING);
                        String sustainableAVG = df.format(jsonObject.getDouble("sustainableAVG"));
                        product.setSustainableAVG(Double.parseDouble(sustainableAVG.replace(',', '.')));
                        product.setEconomyAVG(jsonObject.getInt("EconomyAVG"));
                        product.setSatisfactionAVG(jsonObject.getInt("satisfactionAVG"));
                        itemList.add(product);
                        //infiniteListView.addNewItem(product);
                    }

                    // SOY UN PUTO GENIO!
                    if(limit == 0)
                        infiniteListView.setAdapter(adapter);

                    limit += NUMBER_LOAD_PRODUCTS;
                    infiniteListView.stopLoading();

                    if(limit < maxNumProducts)
                        infiniteListView.hasMore(true);
                    else
                        infiniteListView.hasMore(false);

                } catch (JSONException e) {
                    Toasty.error(getContext(), "Error al cargar los Productos...", Toast.LENGTH_SHORT, true).show();
                    e.printStackTrace();
                    //progress.hide();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toasty.error(getContext(), "Cannot connect. ", Toast.LENGTH_LONG, true).show();
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
                params.put("limit", String.valueOf(limit));
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

    //DO THIS ON SWIPE-REFRESH
    public void refreshList() {
        limit = 0;
        infiniteListView.clearList();
        loadProducts();
    }

    //DO THIS ON ITEM CLICK
    public void clickItem(int position) {
        Intent i = new Intent(getActivity(), ProductActivity.class);
        Product item = itemList.get(position);

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

    //DO THIS ON ITEM LONG-CLICK
    public void longClickItem(int position) {
        Snackbar.make(container, "Item long-clicked: " + position, Snackbar.LENGTH_SHORT).show();
    }

    public void setSizeList(){

        String url = getString(R.string.ip) + "/beSustainable/getSizeList.php";

        //Toast.makeText(getContext(), url, Toast.LENGTH_SHORT).show();

        customRequest = new CustomRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    maxNumProducts = Integer.parseInt(response.getString("size"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toasty.error(getContext(), "Cannot connect.", Toast.LENGTH_LONG, true).show();
                Log.d("ERROR: ", error.toString());

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
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

}
