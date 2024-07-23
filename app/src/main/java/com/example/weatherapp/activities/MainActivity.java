package com.example.weatherapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.weatherapp.adapters.HourlyAdapter;
import com.example.weatherapp.entities.Hourly;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Hourly> items;
    private HourlyAdapter hourlyAdapter;
    private RecyclerView recyclerViewHourly;

    private TextView textNext7Days, textNameCity, textDateTime, textState, textTemperature, textPercentHumidity, textWindSpeed, textFeelsLike;
    private ImageView imgSearch;
    private EditText editTextSearch;
    private String nameCity = "";

    private long pressBackTime;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerViewHourly = findViewById(R.id.recyclerViewHourly);
        textNext7Days = findViewById(R.id.textNext7Days);
        recyclerViewHourly.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        items = new ArrayList<>();
        items.add(new Hourly("9 pm", 28, "cloudy"));
        items.add(new Hourly("10 pm", 29, "sunny"));
        items.add(new Hourly("11 am", 30, "wind"));
        items.add(new Hourly("12 pm", 31, "rainy"));
        items.add(new Hourly("1 am", 32, "storm"));
        hourlyAdapter = new HourlyAdapter(items);
        recyclerViewHourly.setAdapter(hourlyAdapter);

        textNext7Days.setOnClickListener(v -> {
            String city = editTextSearch.getText().toString();
            Intent intent = new Intent(MainActivity.this, FutureActivity.class);
            intent.putExtra("name", city);
            startActivity(intent);
        });

        imgSearch = findViewById(R.id.imgSearch);
        textDateTime = findViewById(R.id.textDateTime);
        editTextSearch = findViewById(R.id.editTextSearch);
        textState = findViewById(R.id.textState);
        textNameCity = findViewById(R.id.textNameCity);
        textTemperature = findViewById(R.id.textTemperature);
        textPercentHumidity = findViewById(R.id.textPercentHumidity);
        textWindSpeed = findViewById(R.id.textWindSpeed);
        textFeelsLike = findViewById(R.id.textFeelsLike);

        textNameCity.setText("Hanoi");
        textNameCity.setVisibility(View.VISIBLE);
        getCurrentWeatherData("Hanoi");
        imgSearch.setOnClickListener(v -> {
            String city = editTextSearch.getText().toString();
            if (city.equals("")){
                nameCity = "Hanoi";
                getCurrentWeatherData(nameCity);
                textNameCity.setText(nameCity);
                textNameCity.setVisibility(View.VISIBLE);
            }
            else {
                nameCity = city;
                textNameCity.setText(nameCity);
                textNameCity.setVisibility(View.VISIBLE);
                getCurrentWeatherData(nameCity);
            }
        });
    }
    public void getCurrentWeatherData(String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + data +"&units=metric&appid=e5afb6abedc33f32a139cf17a8921af6";
        @SuppressLint("SetTextI18n") StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String day = jsonObject.getString("dt");
                        String name = jsonObject.getString("name");

                        long dt = Long.valueOf(day);
                        Date date = new Date(dt * 1000L);
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd | HH:mm a", Locale.ENGLISH);
                        String dateTime = simpleDateFormat.format(date);

                        textDateTime.setText(dateTime);

                        JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                        String status = jsonObjectWeather.getString("main");
                        String icon = jsonObjectWeather.getString("icon");
                        textState.setText(status);

                        JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                        String temp = jsonObjectMain.getString("temp");
                        String humidity = jsonObjectMain.getString("humidity");
                        String feelsLike = jsonObjectMain.getString("feels_like");
                        Double a = Double.valueOf(temp);
                        String Temp = String.valueOf(a.intValue());
                        textTemperature.setText(Temp + "°C");
                        textPercentHumidity.setText(humidity + "%");
                        textFeelsLike.setText(feelsLike + "°C");

                        JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                        String speed = jsonObjectWind.getString("speed");
                        textWindSpeed.setText(speed + "m/s");

                        JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                        String country = jsonObjectSys.getString("country");
                        textNameCity.setText(name + "-" + country);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - pressBackTime < 2000) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressBackTime = System.currentTimeMillis();
    }
}