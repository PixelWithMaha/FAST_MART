package com.example.assignment3.view;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.assignment3.R;
import com.example.assignment3.data.model.Product; // Make sure this import exists
import com.google.android.material.button.MaterialButton;

public class ProductDetailsActivity extends AppCompatActivity {

    private TextView priceTxt, titleTxt, modelTxt, descTxt;
    private ImageView productImage;
    private Product currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_details);

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        ImageView backBtn = findViewById(R.id.backBtn);
        priceTxt = findViewById(R.id.priceText);
        titleTxt = findViewById(R.id.titleText);
        modelTxt = findViewById(R.id.modelText);
        descTxt = findViewById(R.id.descriptionText);
        productImage = findViewById(R.id.productImage);
        MaterialButton buyButton = findViewById(R.id.buyButton);

        // 3. Get the Product object (Passed from SellerHomeFragment)
        currentProduct = (Product) getIntent().getSerializableExtra("product_data");

        if (currentProduct != null) {
            priceTxt.setText("$" + currentProduct.price);
            titleTxt.setText(currentProduct.name);
            modelTxt.setText("Type: " + currentProduct.type);
            descTxt.setText(currentProduct.description);
            if (currentProduct.imageRes != 0) {
                productImage.setImageResource(currentProduct.imageRes);
            } else {
                productImage.setImageResource(R.drawable.img_sony);
            }
        }

        // Back Button
        backBtn.setOnClickListener(v -> finish());

        // Buy Button
        buyButton.setOnClickListener(v -> {
            if (currentProduct != null) {
                showConfirmationDialog(currentProduct.name);
            }
        });
    }

    private void showConfirmationDialog(String productName) {
        new AlertDialog.Builder(this)
                .setTitle("Add to Cart?")
                .setMessage("Are you sure you want to add " + productName + " to your Cart?")
                .setCancelable(false)
                .setPositiveButton("Confirm", (dialog, which) -> {
                    Toast.makeText(this, productName + " added to cart!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.cancel();
                })
                .show();
    }
}