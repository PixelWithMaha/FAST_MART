package com.example.assignment3.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment3.R;
import com.example.assignment3.data.model.Product;
import com.example.assignment3.view.adapter.DealsAdapter; // Added
import com.example.assignment3.view.adapter.ProductAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BuyerHomeActivity extends AppCompatActivity {

    private RecyclerView rvRecommended, rvDeals;
    private ProductAdapter adapter;
    private DealsAdapter dealsAdapter; // Added declaration

    private List<Product> productList;
    private List<Product> dealsList; // Added declaration
    private DatabaseReference productsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_home);

        // 1. Initialize Lists and Firebase
        productsRef = FirebaseDatabase.getInstance().getReference("Products");
        productList = new ArrayList<>();
        dealsList = new ArrayList<>();

        // 2. Setup "Deals of the Day" (Horizontal)
        rvDeals = findViewById(R.id.rv_deals);
        rvDeals.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        dealsAdapter = new DealsAdapter(this, dealsList);
        rvDeals.setAdapter(dealsAdapter);

        // 3. Setup "Recommended" (Grid)
        rvRecommended = findViewById(R.id.rv_recommended);
        rvRecommended.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new ProductAdapter(productList, product -> {
            Intent intent = new Intent(BuyerHomeActivity.this, ProductDetailsActivity.class);
            intent.putExtra("product_data", product);
            startActivity(intent);
        });
        rvRecommended.setAdapter(adapter);

        // 4. Start Syncing
        setupRealtimeListeners();
        setupWelcomeHeader();
        setupNavigation();
    }

    private void setupRealtimeListeners() {
        // ValueEventListener handles Task 2 (Instant reflection) and Task 3 (Continuous updates)
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                dealsList.clear();

                for (DataSnapshot data : snapshot.getChildren()) {
                    Product product = data.getValue(Product.class);
                    if (product != null) {
                        // Task 4: Sort dynamically based on Firebase field
                        if (product.isDeal) {
                            dealsList.add(product);
                        } else {
                            productList.add(product);
                        }
                    }
                }
                // Refresh both UI components
                adapter.notifyDataSetChanged();
                dealsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BuyerHomeActivity.this, "Sync Failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupWelcomeHeader() {
        TextView tvWelcome = findViewById(R.id.tv_welcome_user);
        SharedPreferences pref = getSharedPreferences("FastMartPrefs", Context.MODE_PRIVATE);
        String name = pref.getString("user.name", "Guest");
        tvWelcome.setText("Hello " + name);
    }

    private void setupNavigation() {
        BottomNavigationView bottomView = findViewById(R.id.bottom_navigation);
        bottomView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) return true;

            Class<?> targetActivity = null;
            if (id == R.id.nav_fav) targetActivity = FavActivity.class;
            else if (id == R.id.nav_search) targetActivity = SearchActivity.class;
            else if (id == R.id.nav_cart) targetActivity = CartActivity.class;
            else if (id == R.id.nav_profile) targetActivity = BuyerAccountActivity.class;

            if (targetActivity != null) {
                startActivity(new Intent(BuyerHomeActivity.this, targetActivity));
                return true;
            }
            return false;
        });
    }
}