package com.example.assignment3.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.assignment3.R;
import com.google.android.material.navigation.NavigationView;

public class SellerDashboardActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pref = getSharedPreferences("FastMartPrefs", MODE_PRIVATE);
        applyTheme(pref.getBoolean("user.isDarkMode", false));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Setup Custom Navigation Clicks
        View header = navigationView.getHeaderView(0);

        header.findViewById(R.id.menu_home).setOnClickListener(v -> closeDrawer());
        header.findViewById(R.id.menu_orders).setOnClickListener(v -> closeDrawer());
        header.findViewById(R.id.menu_settings).setOnClickListener(v -> closeDrawer());

        // Theme Switchers
        header.findViewById(R.id.btn_light).setOnClickListener(v -> saveTheme(false));
        header.findViewById(R.id.btn_dark).setOnClickListener(v -> saveTheme(true));

        // Update user info from Prefs
        ((TextView)header.findViewById(R.id.tv_header_name)).setText(pref.getString("user.name", "Seller"));
        ((TextView)header.findViewById(R.id.tv_header_email)).setText(pref.getString("user.email", ""));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SellerHomeFragment())
                    .commit();
        }
    }

    private void saveTheme(boolean isDark) {
        pref.edit().putBoolean("user.isDarkMode", isDark).apply();
        applyTheme(isDark);
        recreate(); // Critical to refresh the colors immediately
    }

    private void applyTheme(boolean isDark) {
        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void closeDrawer() {
        drawerLayout.closeDrawers();
    }
}