package com.example.assignment3.view;

import android.os.Bundle;
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

            String id = FirebaseDatabase.getInstance().getReference("Products").push().getKey();
            int assignedImg = R.drawable.img_sony;
            Product product = new Product(id, name, type, price, desc, uid,assignedImg);

            FirebaseDatabase.getInstance().getReference("Products").child(id).setValue(product)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                        finish();
                    });
        });
    }
}