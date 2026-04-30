package com.example.assignment3.view;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.assignment3.R;
import com.example.assignment3.data.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AddProductActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        findViewById(R.id.btn_save).setOnClickListener(v -> {
            String name = ((EditText)findViewById(R.id.et_name)).getText().toString();
            String type = ((EditText)findViewById(R.id.et_type)).getText().toString();
            String price = ((EditText)findViewById(R.id.et_price)).getText().toString();
            String desc = ((EditText)findViewById(R.id.et_desc)).getText().toString();
            String uid = FirebaseAuth.getInstance().getUid();
            CheckBox cbIsDeal = findViewById(R.id.cb_is_deal);

            // Generate unique ID
            String id = FirebaseDatabase.getInstance().getReference("Products").push().getKey();
            boolean isDeal = cbIsDeal.isChecked();
            // logic to pick an image based on type (optional but better)
            int assignedImg = R.drawable.img_sony;
            if (type.toLowerCase().contains("mic")) assignedImg = R.drawable.img_rodemic;
            if (type.toLowerCase().contains("watch")) assignedImg = R.drawable.img_xiaomi;

            // THE FIX: Add the 'false' parameter for isDeal at the end
            Product product = new Product(id, name, type, price, desc, uid, assignedImg, isDeal);

            if (id != null) {
                FirebaseDatabase.getInstance().getReference("Products").child(id).setValue(product)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(this, "Product Added Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }
}