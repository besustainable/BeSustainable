package com.example.pc_gaming.besustainable.Class;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.pc_gaming.besustainable.Adapters.CategoryAdapter;
import com.example.pc_gaming.besustainable.Entity.Category;
import com.example.pc_gaming.besustainable.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class CategoryFragment extends Fragment {

    GridView gridview;
    ArrayList<Category> categoryList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_category, container, false);

        categoryList = new ArrayList<>();

        gridview = rootView.findViewById(R.id.gridCategory);

        loadCategories();

        // Click Item Listener
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String idcategory = String.valueOf(categoryList.get(i).getIdCategory());

                Intent intent = new Intent(getContext(), ListProductsFilter.class);
                intent.putExtra("category", idcategory);

                // Empty Filters
                intent.putExtra("satisfaction", "");
                intent.putExtra("economics", "");
                intent.putExtra("totalvote", "");
                intent.putExtra("city", "");
                startActivity(intent);


            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }


    public void loadCategories(){

        String url = getString(R.string.ip) + "/beSustainable/loadCategories.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.optJSONArray("categories");

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        Category category = new Category();
                        category.setIdCategory(jsonObject.getInt("idcategory"));
                        category.setName(jsonObject.getString("name"));
                        category.setMeasure(jsonObject.getString("measure"));
                        categoryList.add(category);
                    }

                    // Put in the Adapter
                    CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), categoryList);
                    gridview.setAdapter(categoryAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(getContext(), "Error Loading the Categories...", Toast.LENGTH_SHORT, true).show();
            }
        });
        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);

    }

}
