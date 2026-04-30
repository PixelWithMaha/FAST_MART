package com.example.assignment3.view;
import com.example.assignment3.R;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.assignment3.MainActivity;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView cartLogo = findViewById(R.id.iv_logo);
        Animation deliveryAnim = AnimationUtils.loadAnimation(this, R.anim.cart_delivery);
        cartLogo.startAnimation(deliveryAnim);

        deliveryAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}


            @Override
            public void onAnimationEnd(Animation animation) {
                SharedPreferences pref = getSharedPreferences("FastMartPrefs", MODE_PRIVATE);

                //pref.edit().clear().apply();
                boolean isFirstTime = pref.getBoolean("user.isFirstTime", true);
                boolean isLoggedIn = pref.getBoolean("user.isLoggedIn", false);
                String accountType = pref.getString("user.type", "Buyer");

                Intent intent;

                if (isFirstTime) {
                    intent = new Intent(SplashActivity.this, OnboardingActivity.class);
                } else if (isLoggedIn) {

                    if ("Seller".equals(accountType)) {
                        intent = new Intent(SplashActivity.this, SellerDashboardActivity.class);
                    } else {
                        intent = new Intent(SplashActivity.this, MainActivity.class);
                    }
                }
                else {
                    intent = new Intent(SplashActivity.this, AuthActivity.class);
                }

                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }
}