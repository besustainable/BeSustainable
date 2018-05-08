package com.example.pc_gaming.besustainable.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc_gaming.besustainable.Entity.Category;
import com.example.pc_gaming.besustainable.R;

import java.util.ArrayList;

/**
 * Created by PC_Gaming on 07/05/2018.
 */

public class CategoryAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Category> categoryList;

    public CategoryAdapter(Context context, ArrayList<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return categoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View vista = view;
        LayoutInflater inflate = LayoutInflater.from(context);
        vista = inflate.inflate(R.layout.item_category, null);

        TextView tvNameCategory = vista.findViewById(R.id.tvNameCategory);

        tvNameCategory.setText(categoryList.get(i).getName().toUpperCase());

        return vista;
    }
}
