package com.example.weatherapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TreeMap;

public class FutureActivity extends AppCompatActivity {
    private ArrayList<FutureDomain> items;
    private FutureAdapter futureAdapter;
    private RecyclerView recyclerViewFuture;

    private ImageView imgBack;
    private String nameCity = "";

    private TextView textTemperatureToday, textWeatherToday, textFeels, textWind, textHumidity;

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
        textTemperatureToday = findViewById(R.id.textTemperatureToday);
        textWeatherToday = findViewById(R.id.textWeatherToday);
        textFeels = findViewById(R.id.textFeels);
        textWind = findViewById(R.id.textWind);
        textHumidity = findViewById(R.id.textHumidity);
        recyclerViewFuture = findViewById(R.id.recyclerViewFuture);
        recyclerViewFuture.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        imgBack = findViewById(R.id.imgback);
        imgBack.setOnClickListener(v -> finish());

        items = new ArrayList<>();
        futureAdapter = new FutureAdapter(items);
        recyclerViewFuture.setAdapter(futureAdapter);

        Intent intent = getIntent();
        String city = intent.getStringExtra("name");
        textTemperatureToday.setText(intent.getStringExtra("temperature"));
        textWeatherToday.setText(intent.getStringExtra("state"));
        textFeels.setText(intent.getStringExtra("feelsLike"));
        textWind.setText(intent.getStringExtra("windSpeed"));
        textHumidity.setText(intent.getStringExtra("humidity"));
        Log.d("result", "Du lieu truyen qua: " + city);
        if (city.equals("")) {
            nameCity = "Hanoi";
            get7DaysData(nameCity);
        } else {
            nameCity = city;
            get7DaysData(nameCity);
        }
    }

    private void get7DaysData(String data) {
        String url = "https://api.openweathermap.org/data/2.5/forecast?q=" + data + "&appid=e5afb6abedc33f32a139cf17a8921af6&units=metric";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        items.clear();

                        // Use a TreeMap to automatically sort the entries by date
                        TreeMap<String, FutureDomain> dailyForecasts = new TreeMap<>();
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("list");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObjectList = jsonArray.getJSONObject(i);
                            String day = jsonObjectList.getString("dt");

                            long dt = Long.valueOf(day);
                            Date date = new Date(dt * 1000L);
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE", Locale.ENGLISH);
                            String dateTime = simpleDateFormat.format(date);
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                            String dateOnly = dateFormat.format(date);

                            JSONObject jsonObjectMain = jsonObjectList.getJSONObject("main");
                            String maxTemp = jsonObjectMain.getString("temp_max");
                            String minTemp = jsonObjectMain.getString("temp_min");

                            Double a = Double.valueOf(maxTemp);
                            Double b = Double.valueOf(minTemp);
                            int max = a.intValue();
                            int min = b.intValue();

                            JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            String status = jsonObjectWeather.getString("description");
                            String icon = jsonObjectWeather.getString("icon");

                            // Only store the first forecast of each day
                            if (!dailyForecasts.containsKey(dateOnly)) {
                                dailyForecasts.put(dateOnly, new FutureDomain(dateTime, icon, status, max, min));
                            }
                        }
                        items.addAll(dailyForecasts.values());
                        futureAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> Log.e("result", "JSON parsing error: " + error.getMessage()));
        requestQueue.add(stringRequest);
    }
}