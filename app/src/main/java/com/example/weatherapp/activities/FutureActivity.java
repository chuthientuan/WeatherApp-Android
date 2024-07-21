package com.example.weatherapp.activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.adapters.FutureAdapter;
import com.example.weatherapp.entities.FutureDomain;

import java.util.ArrayList;

public class FutureActivity extends AppCompatActivity {
    private ArrayList<FutureDomain> items;
    private FutureAdapter futureAdapter;
    private RecyclerView recyclerViewFuture;

    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_future);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerViewFuture = findViewById(R.id.recyclerViewFuture);
        recyclerViewFuture.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        imgBack = findViewById(R.id.imgback);
        imgBack.setOnClickListener(v -> finish());
        items = new ArrayList<>();
        items.add(new FutureDomain("Sat", "storm", "Stormy", 24, 12));
        items.add(new FutureDomain("Sun", "cloudy", "Cloudy", 25, 16));
        items.add(new FutureDomain("Mon", "windy", "Windy", 29, 15));
        items.add(new FutureDomain("Tue", "cloudy_sunny", "Cloudy Sunny", 23, 12));
        items.add(new FutureDomain("Wen", "sunny", "Sunny", 28, 11));
        items.add(new FutureDomain("Thu", "rainy", "Rainy", 23, 12));
        items.add(new FutureDomain("Sat", "storm", "Stormy", 24, 12));
        items.add(new FutureDomain("Sun", "cloudy", "Cloudy", 25, 16));
        items.add(new FutureDomain("Mon", "windy", "Windy", 29, 15));
        futureAdapter = new FutureAdapter(items);
        recyclerViewFuture.setAdapter(futureAdapter);
    }
}