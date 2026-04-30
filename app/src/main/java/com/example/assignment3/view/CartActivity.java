package com.example.assignment3.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.assignment3.R;
import com.example.assignment3.data.local.CartDbHelper;
import com.example.assignment3.data.model.CartItem;
import com.example.assignment3.view.adapter.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView rv;
    private TextView tvTotal;
    private CartDbHelper db;
    private List<CartItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        db = new CartDbHelper(this);
        rv = findViewById(R.id.rv_cart);
        tvTotal = findViewById(R.id.tv_total_price);
        Button btnCheckout = findViewById(R.id.btn_checkout);

        rv.setLayoutManager(new LinearLayoutManager(this));
        refreshCart();

        btnCheckout.setOnClickListener(v -> checkSmsPermission());
    }

    private void refreshCart() {
        list = db.getAllItems();
        rv.setAdapter(new CartAdapter(list, this::calculateTotal));
        calculateTotal();
    }

    private void calculateTotal() {
        double total = 0;
        for (CartItem item : list) {
            total += Double.parseDouble(item.getProduct().price) * item.getQuantity();
        }
        tvTotal.setText(String.format("$%.2f", total));
    }

    private void checkSmsPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.SEND_SMS}, 1);
        } else {
            processCheckout();
        }
    }

    private void processCheckout() {
        if (list.isEmpty()) return;

        StringBuilder summary = new StringBuilder("Order Details:\n");
        for (CartItem i : list) {
            summary.append(i.getProduct().name).append(" x").append(i.getQuantity()).append("\n");
        }
        summary.append("Total: ").append(tvTotal.getText().toString());

        // Requirement 6: SMS
        SmsManager.getDefault().sendTextMessage("8796", null, summary.toString(), null, null);

        // Requirement 7: Firebase History
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Orders").push();
        ref.setValue(list).addOnSuccessListener(aVoid -> {
            db.clearCart();
            Toast.makeText(this, "Order Placed!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}