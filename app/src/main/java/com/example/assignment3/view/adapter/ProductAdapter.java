package com.example.assignment3.view.adapter;

import android.content.Context;
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
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener { void onItemClick(Product product); }

    public ProductAdapter(List<Product> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product p = list.get(position);
        Context context = holder.itemView.getContext(); // Define context

        holder.name.setText(p.name);
        holder.price.setText("$" + p.price);

        if (p.imageRes != 0) {
            holder.productImage.setImageResource(p.imageRes);
        } else {
            holder.productImage.setImageResource(R.drawable.img_apple_airpods);
        }

        FavDbHelper db = new FavDbHelper(context);

        holder.ivHeart.setOnClickListener(v -> {
            db.addFavorite(p); // Use 'p', not 'product'
            holder.ivHeart.setImageResource(R.drawable.ic_filled_heart);
            Toast.makeText(context, "Added to Favourites", Toast.LENGTH_SHORT).show();
        });

        holder.itemView.setOnClickListener(v -> listener.onItemClick(p));
    }

    @Override public int getItemCount() { return list.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView productImage, ivHeart; // Added ivHeart here

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_item_name);
            price = itemView.findViewById(R.id.tv_item_price);
            productImage = itemView.findViewById(R.id.iv_product_img);
            ivHeart = itemView.findViewById(R.id.iv_item_heart); // Initialize this
        }
    }
}