package com.example.assignment3.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment3.R;
import com.example.assignment3.data.local.FavDbHelper;
import com.example.assignment3.data.model.Product;
import com.example.assignment3.view.ProductDetailsActivity;

import java.util.List;

public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.DealViewHolder> {

    private List<Product> dealsList;
    private Context context;
    private FavDbHelper dbHelper;

    public DealsAdapter(Context context, List<Product> dealsList) {
        this.context = context;
        this.dealsList = dealsList;
        this.dbHelper = new FavDbHelper(context);
    }

    @NonNull
    @Override
    public DealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deal, parent, false);
        return new DealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DealViewHolder holder, int position) {
        Product deal = dealsList.get(position);

        // Set Data
        holder.tvName.setText(deal.name);
        holder.tvDiscount.setText("$" + deal.price);
        holder.tvDesc.setText(deal.description);

        // Image Loading
        if (deal.imageRes != 0) {
            holder.ivImage.setImageResource(deal.imageRes);
        } else {
            holder.ivImage.setImageResource(R.drawable.img_rodemic);
        }

        // UI Styling: Strike-through for original price
        holder.tvOriginalPrice.setText("$199.99");
        holder.tvOriginalPrice.setPaintFlags(holder.tvOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        // --- Requirement 1 & 2: SQLite Favorites Logic ---
        // Check if item is already in SQLite to set correct heart icon
        if (isProductInFavs(deal.id)) {
            holder.ivFav.setImageResource(R.drawable.ic_filled_heart);
        } else {
            holder.ivFav.setImageResource(R.drawable.ic_fav);
        }

        holder.ivFav.setOnClickListener(v -> {
            if (isProductInFavs(deal.id)) {
                dbHelper.removeFavorite(deal.id);
                holder.ivFav.setImageResource(R.drawable.ic_fav);
                Toast.makeText(context, "Removed from Favourites", Toast.LENGTH_SHORT).show();
            } else {
                dbHelper.addFavorite(deal);
                holder.ivFav.setImageResource(R.drawable.ic_filled_heart);
                Toast.makeText(context, "Added to Favourites", Toast.LENGTH_SHORT).show();
            }
        });

        // Intent to Details
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("product_data", deal);
            context.startActivity(intent);
        });
    }

    // Helper method to check SQLite status
    private boolean isProductInFavs(String id) {
        List<Product> favs = dbHelper.getAll();
        for (Product p : favs) {
            if (p.id.equals(id)) return true;
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return dealsList.size();
    }

    // Task: Ensure these IDs match your item_deal.xml
    public static class DealViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDiscount, tvOriginalPrice, tvDesc;
        ImageView ivImage, ivFav;

        public DealViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_deal_name);
            tvDiscount = itemView.findViewById(R.id.tv_deal_discount);
            tvOriginalPrice = itemView.findViewById(R.id.tv_deal_original_price);
            tvDesc = itemView.findViewById(R.id.tv_deal_desc);
            ivImage = itemView.findViewById(R.id.iv_deal_image);
            ivFav = itemView.findViewById(R.id.iv_deal_heart);
        }
    }
}