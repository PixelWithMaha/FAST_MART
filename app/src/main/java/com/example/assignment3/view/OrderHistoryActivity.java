package com.example.assignment3.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment3.R;
import com.example.assignment3.data.model.CartItem;
import com.example.assignment3.data.model.Order;
import com.example.assignment3.view.adapter.OrderAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {

    private RecyclerView rvOrders;
    private OrderAdapter adapter;
    private List<Order> orderList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        // UI Setup
        ImageView btnBack = findViewById(R.id.btn_back_orders);
        rvOrders = findViewById(R.id.rv_order_history);

        btnBack.setOnClickListener(v -> finish());

        // List and Adapter Setup
        orderList = new ArrayList<>();
        adapter = new OrderAdapter(orderList);
        rvOrders.setLayoutManager(new LinearLayoutManager(this));
        rvOrders.setAdapter(adapter);

        // Firebase Setup
        mDatabase = FirebaseDatabase.getInstance().getReference("Orders");
        fetchOrderHistory();
    }

    private void fetchOrderHistory() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    // Create an Order object from the Firebase snapshot
                    Order order = new Order();

                    // Since checkout saved a List<CartItem>, we fetch it specifically
                    GenericTypeIndicator<List<CartItem>> t = new GenericTypeIndicator<List<CartItem>>() {};
                    List<CartItem> items = ds.getValue(t);

                    if (items != null) {
                        order.items = items;
                        order.orderId = ds.getKey(); // Using Firebase unique ID
                        orderList.add(order);
                    }
                }
                adapter.notifyDataSetChanged();

                if (orderList.isEmpty()) {
                    Toast.makeText(OrderHistoryActivity.this, "No orders found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(OrderHistoryActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}