package com.example.pc_gaming.besustainable.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pc_gaming.besustainable.Class.ListFragment;
import com.example.pc_gaming.besustainable.Class.ListProductsFilter;
import com.example.pc_gaming.besustainable.Class.MainActivity;
import com.example.pc_gaming.besustainable.Class.RatingsImage;
import com.example.pc_gaming.besustainable.Entity.Product;
import com.example.pc_gaming.besustainable.R;
import com.softw4re.views.InfiniteListAdapter;

import java.util.ArrayList;

/**
 * Created by PC_Gaming on 19/05/2018.
 */
public class MyInfiniteListAdapter<T> extends InfiniteListAdapter<T> {

    private ListFragment mainActivity;
    private int itemLayoutRes;
    private ArrayList<T> itemList;

    public MyInfiniteListAdapter(ListFragment mainActivity, int itemLayoutRes, ArrayList<T> itemList) {
        super(mainActivity.getActivity(), itemLayoutRes, itemList);

        this.mainActivity = mainActivity;
        this.itemLayoutRes = itemLayoutRes;
        this.itemList = itemList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = mainActivity.getLayoutInflater().inflate(itemLayoutRes, parent, false);

            holder = new ViewHolder();
            holder.tvName = convertView.findViewById(R.id.tvName);
            holder.tvWeight = convertView.findViewById(R.id.tvWeight);
            holder.tvPvp = convertView.findViewById(R.id.tvPvp);
            holder.imgProduct = convertView.findViewById(R.id.ivImgProduct);
            holder.ivSatisfactionAVG = convertView.findViewById(R.id.ivSatisfactionAVG);
            holder.ivEconomyAVG = convertView.findViewById(R.id.ivEconomyAVG);
            holder.tvRating = convertView.findViewById(R.id.tvRating);
            holder.layout_background = convertView.findViewById(R.id.layout_background);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product product = (Product) itemList.get(position);
        if (product != null) {
            holder.tvName.setText(product.getName().toString().toUpperCase());
            holder.tvWeight.setText(String.valueOf(product.getWeight()).toString());
            holder.tvPvp.setText(String.valueOf(product.getPvp()).toString() + " €");
            holder.tvRating.setText(String.valueOf(product.getSustainableAVG()));

            if(position % 2 == 0)
                holder.layout_background.setBackgroundColor(Color.argb(5, 180, 247, 175));

            // Put img if exist!
            if (product.getImg() != null){
                holder.imgProduct.setImageBitmap(product.getImg());
            }else{
                holder.imgProduct.setImageResource(R.drawable.ic_photo_black_24dp);
            }

            RatingsImage ratingsImage = new RatingsImage();

            // Set the Votes Images
            Bitmap bmSatisfaction = BitmapFactory.decodeResource(mainActivity.getResources(), ratingsImage.satisfactionRate(product.getSatisfactionAVG()));
            Bitmap bmEconomy = BitmapFactory.decodeResource(mainActivity.getResources(), ratingsImage.economyRate(product.getEconomyAVG()));

            holder.ivSatisfactionAVG.setImageBitmap(bmSatisfaction);
            holder.ivEconomyAVG.setImageBitmap(bmEconomy);
        }

        return convertView;
    }

    @Override
    public void onNewLoadRequired() { mainActivity.loadProducts();}

    @Override
    public void onRefresh() {
        mainActivity.refreshList();
    }

    @Override
    public void onItemClick(int position) {
        mainActivity.clickItem(position);
    }

    @Override
    public void onItemLongClick(int position) {
        mainActivity.longClickItem(position);
    }


    static class ViewHolder {
        TextView tvName, tvWeight, tvPvp, tvRating;
        ImageView imgProduct, ivSatisfactionAVG, ivEconomyAVG;
        LinearLayout layout_background;
    }

}

