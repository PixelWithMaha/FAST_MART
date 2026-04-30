package com.example.assignment3.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.assignment3.R;
import com.example.assignment3.data.local.CartDbHelper;
import com.example.assignment3.data.model.CartItem;
import com.example.assignment3.data.model.Product;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems;
    private Runnable onDataChanged;

    public CartAdapter(List<CartItem> cartItems, Runnable onDataChanged) {
        this.cartItems = cartItems;
        this.onDataChanged = onDataChanged;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        Product p = item.getProduct();
        CartDbHelper db = new CartDbHelper(holder.itemView.getContext());

        holder.tvName.setText(p.name);
        holder.tvPrice.setText("$" + p.price);
        holder.tvQty.setText(String.valueOf(item.getQuantity()));
        holder.ivImg.setImageResource(p.imageRes != 0 ? p.imageRes : R.drawable.img_sony);

        holder.btnPlus.setOnClickListener(v -> {
            int newQty = item.getQuantity() + 1;
            db.updateQuantity(p.id, newQty);
            item.setQuantity(newQty);
            notifyItemChanged(position);
            onDataChanged.run();
        });

        holder.btnMinus.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                int newQty = item.getQuantity() - 1;
                db.updateQuantity(p.id, newQty);
                item.setQuantity(newQty);
                notifyItemChanged(position);
                onDataChanged.run();
            }
        });

        holder.ivOptions.setOnClickListener(v -> {
            db.deleteItem(p.id);
            cartItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size());
            onDataChanged.run();
        });
    }

    @Override public int getItemCount() { return cartItems.size(); }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvQty, btnPlus, btnMinus;
        ImageView ivImg, ivOptions;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_cart_name);
            tvPrice = itemView.findViewById(R.id.tv_cart_price);
            tvQty = itemView.findViewById(R.id.tv_quantity);
            ivImg = itemView.findViewById(R.id.iv_cart_img);
            ivOptions = itemView.findViewById(R.id.iv_cart_options);
            btnPlus = itemView.findViewById(R.id.btn_plus);
            btnMinus = itemView.findViewById(R.id.btn_minus);
        }
    }
}