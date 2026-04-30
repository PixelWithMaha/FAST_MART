package com.example.assignment3.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView; // Added
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.assignment3.R;
import com.example.assignment3.data.model.Product; // Verified path
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
        holder.name.setText(p.name);
        holder.price.setText("$" + p.price);

        // Fixed the double dot and ensured productImage is used
        if (p.imageRes != 0) {
            holder.productImage.setImageResource(p.imageRes);
        } else {
            // Use your actual placeholder name here
            holder.productImage.setImageResource(R.drawable.img_apple_airpods);
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(p));
    }

    @Override public int getItemCount() { return list.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView productImage; // Added this

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_item_name);
            price = itemView.findViewById(R.id.tv_item_price);
            // Must match the ID in your item_product.xml
            productImage = itemView.findViewById(R.id.iv_product_img);
        }
    }
}