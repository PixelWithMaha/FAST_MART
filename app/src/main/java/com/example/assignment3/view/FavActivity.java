package com.example.assignment3.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.assignment3.R;
import com.example.assignment3.data.local.FavDbHelper;
import com.example.assignment3.data.model.Product;
import com.example.assignment3.view.adapter.FavAdapter;
import java.util.List;

public class FavActivity extends AppCompatActivity {
    private RecyclerView rv;
    private FavDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        db = new FavDbHelper(this);
        rv = findViewById(R.id.rv_favorites);
        rv.setLayoutManager(new LinearLayoutManager(this));
        load();
    }

    @Override
    protected void onResume() { super.onResume(); load(); }

    private void load() {
        List<Product> list = db.getAll();
        rv.setAdapter(new FavAdapter(this, list));
    }
}