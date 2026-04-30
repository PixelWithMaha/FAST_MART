package com.example.assignment3.view.adapter;

import android.app.AlertDialog;
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

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.Holder> {
    private Context ctx;
    private List<Product> list;
    private FavDbHelper db;

    public FavAdapter(Context ctx, List<Product> list) { this.ctx = ctx; this.list = list; this.db = new FavDbHelper(ctx); }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup p, int t) {
        return new Holder(LayoutInflater.from(ctx).inflate(R.layout.item_favorite, p, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder h, int pos) {
        Product p = list.get(pos);
        h.name.setText(p.name);
        h.price.setText("$" + p.price);
        h.img.setImageResource(p.imageRes != 0 ? p.imageRes : R.drawable.img_apple_airpods);

        // Requirement 4: Triple-dot click with Dialog
        h.more.setOnClickListener(v -> {
            new AlertDialog.Builder(ctx)
                    .setTitle("Delete Product")
                    .setMessage("Do you want to delete this product from favourites?")
                    .setPositiveButton("Yes", (d, w) -> {
                        db.removeFavorite(p.id);
                        list.remove(pos);
                        notifyItemRemoved(pos); // Requirement 3: Instant refresh
                    })
                    .setNegativeButton("No", null).show();
        });

        // Requirement 5: Cart Icon
        h.cart.setOnClickListener(v -> Toast.makeText(ctx, p.name + " added to Cart (Qty: 1)", Toast.LENGTH_SHORT).show());
    }

    @Override public int getItemCount() { return list.size(); }

    static class Holder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView img, cart, more;
        Holder(View i) {
            super(i);
            name = i.findViewById(R.id.tv_fav_name);
            price = i.findViewById(R.id.tv_fav_price);
            img = i.findViewById(R.id.iv_fav_image);
            cart = i.findViewById(R.id.iv_fav_cart);
            more = i.findViewById(R.id.iv_fav_more);
        }
    }
}