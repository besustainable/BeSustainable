package com.example.pc_gaming.besustainable.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pc_gaming.besustainable.Class.RatingsImage;
import com.example.pc_gaming.besustainable.Entity.Product;
import com.example.pc_gaming.besustainable.Interface.ILoadMore;
import com.example.pc_gaming.besustainable.Interface.OnItemClickListener;
import com.example.pc_gaming.besustainable.R;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by PC_Gaming on 17/03/2018.
 */

// PRODUCT HOLDER CLASS
class ProductsHolder extends RecyclerView.ViewHolder {

    TextView tvName, tvWeight, tvPvp;
    ImageView imgProduct, ivSatisfactionAVG, ivEconomyAVG;

    public ProductsHolder (View itemView){
        super(itemView);
        tvName = itemView.findViewById(R.id.tvName);
        tvWeight = itemView.findViewById(R.id.tvWeight);
        tvPvp = itemView.findViewById(R.id.tvPvp);
        imgProduct = itemView.findViewById(R.id.ivImgProduct);
        ivSatisfactionAVG = itemView.findViewById(R.id.ivSatisfactionAVG);
        ivEconomyAVG = itemView.findViewById(R.id.ivEconomyAVG);
        //... More Attributes
    }

// For OnItemClickListener()
    public void bind(final Product item, final OnItemClickListener listener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(item);
            }
        });
    }
}
// LOADING PROGRESS CLASS
class LoadingViewHolder extends RecyclerView.ViewHolder {

    public ProgressBar pbItem;

    public LoadingViewHolder(View itemView) {
        super(itemView);
        pbItem = itemView.findViewById(R.id.pbItem);
    }
}

public class ProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 1;
    ILoadMore loadMore;
    boolean isLoading;
    Activity activity;
    ArrayList<Product> listProducts;
    int visibleThresHold = 5;
    int lastVisibleitem, totalItemCount;
    private final OnItemClickListener listener;

    // CONSTRUCTOR
    public ProductsAdapter(RecyclerView recyclerView, Activity activity, ArrayList<Product> listProducts, OnItemClickListener listener) {
        this.activity = activity;
        this.listProducts = listProducts;
        this.listener = listener;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleitem = linearLayoutManager.findLastVisibleItemPosition();
                if(!isLoading && totalItemCount <= (lastVisibleitem+visibleThresHold)){

                    if(loadMore != null)
                        loadMore.onLoadMore();
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return listProducts.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM;
    }

    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == VIEW_TYPE_ITEM){

            View view = LayoutInflater.from(activity).inflate(R.layout.item_products_list, parent, false);
            return new ProductsHolder(view);

        }else if(viewType == VIEW_TYPE_LOADING){


            View view = LayoutInflater.from(activity).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ProductsHolder){

            Product product = listProducts.get(position);
            ProductsHolder  viewHolder = (ProductsHolder) holder;
            viewHolder.bind(listProducts.get(position), listener);
            viewHolder.tvName.setText(listProducts.get(position).getName().toString().toUpperCase());
            viewHolder.tvWeight.setText(String.valueOf(listProducts.get(position).getWeight()).toString());
            viewHolder.tvPvp.setText(String.valueOf(listProducts.get(position).getPvp()).toString() + " €");

            // Put img if exist!
            if (listProducts.get(position).getImg() != null){
                viewHolder.imgProduct.setImageBitmap(listProducts.get(position).getImg());
            }else{
                viewHolder.imgProduct.setImageResource(R.drawable.ic_photo_black_24dp);
            }

            RatingsImage ratingsImage = new RatingsImage();

            // Set the Votes Images
            Bitmap bmSatisfaction = BitmapFactory.decodeResource(activity.getResources(), ratingsImage.satisfactionRate(product.getSatisfactionAVG()));
            Bitmap bmEconomy = BitmapFactory.decodeResource(activity.getResources(), ratingsImage.economyRate(product.getEconomyAVG()));

            viewHolder.ivSatisfactionAVG.setImageBitmap(bmSatisfaction);
            viewHolder.ivEconomyAVG.setImageBitmap(bmEconomy);


        }
        else if(holder instanceof LoadingViewHolder){


            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.pbItem.setIndeterminate(true);

        }

    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }

    public void setLoaded(){
        isLoading = false;

    }
}


