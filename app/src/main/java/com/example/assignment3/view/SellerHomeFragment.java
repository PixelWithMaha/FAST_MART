package com.example.assignment3.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment3.R;
import com.example.assignment3.view.adapter.ProductAdapter;
import com.example.assignment3.data.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SellerHomeFragment extends Fragment {

    private RecyclerView rvProducts;
    private ProductAdapter adapter;
    private List<Product> productList;
    private FloatingActionButton fabAdd;
    private TextView tvWelcome;

    public SellerHomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_home, container, false);

        // 1. Initialize Views
        rvProducts = view.findViewById(R.id.rv_products);
        fabAdd = view.findViewById(R.id.fab_add_product);
        tvWelcome = view.findViewById(R.id.tv_welcome);

        // 2. Setup RecyclerView (Grid with 2 columns)
        productList = new ArrayList<>();
        rvProducts.setLayoutManager(new GridLayoutManager(getContext(), 2));

        adapter = new ProductAdapter(productList, product -> {
            // Navigate to Product Description Page
            Intent intent = new Intent(getContext(), ProductDetailsActivity.class);
            intent.putExtra("product_data", product);
            startActivity(intent);
        });
        rvProducts.setAdapter(adapter);

        // 3. Floating Button Logic
        fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), AddProductActivity.class));
        });

        // 4. Fetch Products from Firebase
        loadSellerProducts();

        return view;
    }

    private void loadSellerProducts() {
        String currentUid = FirebaseAuth.getInstance().getUid();

        FirebaseDatabase.getInstance().getReference("Products")
                .orderByChild("sellerId").equalTo(currentUid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        productList.clear();
                        for (DataSnapshot data : snapshot.getChildren()) {
                            Product product = data.getValue(Product.class);
                            if (product != null) {
                                productList.add(product);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
    }
}