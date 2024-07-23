package com.example.weatherapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.R;
import com.example.weatherapp.adapters.FutureAdapter;
import com.example.weatherapp.entities.FutureDomain;

import java.util.ArrayList;

public class FutureActivity extends AppCompatActivity {
    private ArrayList<FutureDomain> items;
    private FutureAdapter futureAdapter;
    private RecyclerView recyclerViewFuture;

    private ImageView imgBack;
    private String nameCity = "";

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
        Intent intent = getIntent();
        String city = intent.getStringExtra("name");
        Log.d("result", "Du lieu truyen qua: "+city);
        if (city.equals("")) {
            nameCity = "Hanoi";
            get7DaysData(nameCity);
        }
        else {
            nameCity = city;
            get7DaysData(nameCity);
        }

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
        items.add(new FutureDomain("Thu", "rainy", "Rainy", 23, 12));
        futureAdapter = new FutureAdapter(items);
        recyclerViewFuture.setAdapter(futureAdapter);
    }

    private void get7DaysData(String data) {
        String url = "https://api.openweathermap.org/data/2.5/forecast?q=" + data +"&appid=e5afb6abedc33f32a139cf17a8921af6&units=metric&cnt=7";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("result", "Json : "+ response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("result", "JSON parsing error: " + error.getMessage());
                    }
                });
        requestQueue.add(stringRequest);
    }
}