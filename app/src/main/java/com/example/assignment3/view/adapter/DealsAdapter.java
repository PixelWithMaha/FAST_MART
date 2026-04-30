package com.example.assignment3.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment3.R; // Ensure this import exists
import com.example.assignment3.data.model.Product;
import com.example.assignment3.view.ProductDetailsActivity;

import java.util.List;

public class DealsAdapter extends RecyclerView.Adapter<DealViewHolder> {

    private List<Product> dealsList;
    private Context context;

    public DealsAdapter(Context context, List<Product> dealsList) {
        this.context = context;
        this.dealsList = dealsList;
    }

    @NonNull
    @Override
    public DealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Ensure R.layout.item_deal exists
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deal, parent, false);
        return new DealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DealViewHolder holder, int position) {
        Product deal = dealsList.get(position);

        // FIX: Use public fields (name, price, imageRes) from your Product model
        holder.tvName.setText(deal.name);
        holder.tvDiscount.setText("$" + deal.price);

        if (deal.imageRes != 0) {
            holder.ivImage.setImageResource(deal.imageRes);
        } else {
            holder.ivImage.setImageResource(R.drawable.img_rodemic); // Default placeholder
        }

        // Static original price logic
        holder.tvOriginalPrice.setText("$199.99");
        holder.tvOriginalPrice.setPaintFlags(holder.tvOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvDesc.setText(deal.description);

        // Favorites Logic
        SharedPreferences pref = context.getSharedPreferences("FastMartPrefs", Context.MODE_PRIVATE);
        String currentUserEmail = pref.getString("current_user_email", "guest");
        String key = currentUserEmail + ".fav_" + deal.name;

        // Set initial heart icon state
        if (pref.contains(key)) {
            holder.ivFav.setImageResource(R.drawable.ic_filled_heart);
        } else {
            holder.ivFav.setImageResource(R.drawable.ic_fav);
        }

        holder.ivFav.setOnClickListener(v -> {
            if (pref.contains(key)) {
                pref.edit().remove(key).apply();
                holder.ivFav.setImageResource(R.drawable.ic_fav);
                Toast.makeText(context, "Removed from Favorites", Toast.LENGTH_SHORT).show();
            } else {
                // Metadata for easy parsing in FavActivity
                String metadata = deal.name + "|" + deal.price + "|" + deal.description + "|" + deal.imageRes;
                pref.edit().putString(key, metadata).apply();
                holder.ivFav.setImageResource(R.drawable.ic_filled_heart);
                Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show();
            }
        });

        // FIX: Intent logic to use the Serializable object "product_data"
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("product_data", deal); // Passes the whole object
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dealsList.size();
    }
}