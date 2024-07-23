package com.example.weatherapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.R;
import com.example.weatherapp.adapters.HourlyAdapter;
import com.example.weatherapp.entities.Hourly;
import com.example.weatherapp.update.UpdateUI;
import com.example.weatherapp.url.URL;

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

    private TextView textNext5Days, textNameCity, textDateTime, textState, textTemperature, textPercentHumidity, textWindSpeed, textFeelsLike;
    private ImageView imgSearch, imgIconWeather;
    private EditText editTextSearch;
    private String nameCity = "";
    private String name, dateTime, status, icon, Temp, humidity, FeelsLike, speed, country;

    private long pressBackTime;

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
        textNext5Days = findViewById(R.id.textNext5Days);
        recyclerViewHourly.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        items = new ArrayList<>();
        items.add(new Hourly("9 pm", 28, "cloudy"));
        items.add(new Hourly("10 pm", 29, "sunny"));
        items.add(new Hourly("11 am", 30, "wind"));
        items.add(new Hourly("12 pm", 31, "rainy"));
        items.add(new Hourly("1 am", 32, "storm"));
        hourlyAdapter = new HourlyAdapter(items);
        recyclerViewHourly.setAdapter(hourlyAdapter);

        textNext5Days.setOnClickListener(v -> {
            setIntentExtras();
        });

        imgSearch = findViewById(R.id.imgSearch);
        textDateTime = findViewById(R.id.textDateTime);
        editTextSearch = findViewById(R.id.editTextSearch);
        textState = findViewById(R.id.textState);
        textNameCity = findViewById(R.id.textNameCity);
        textTemperature = findViewById(R.id.textTemperature);
        imgIconWeather = findViewById(R.id.imgIconWeather);
        textPercentHumidity = findViewById(R.id.textPercentHumidity);
        textWindSpeed = findViewById(R.id.textWindSpeed);
        textFeelsLike = findViewById(R.id.textFeelsLike);

        textNameCity.setText("Hanoi");
        textNameCity.setVisibility(View.VISIBLE);
        getCurrentWeatherData("Hanoi");
        imgSearch.setOnClickListener(v -> {
            String city = editTextSearch.getText().toString();
            if (city.equals("")) {
                nameCity = "Hanoi";
                getCurrentWeatherData(nameCity);
                textNameCity.setText(nameCity);
                textNameCity.setVisibility(View.VISIBLE);
            } else {
                nameCity = city;
                textNameCity.setText(nameCity);
                textNameCity.setVisibility(View.VISIBLE);
                getCurrentWeatherData(nameCity);
            }
        });
    }

    private void setIntentExtras() {
        String city = editTextSearch.getText().toString();
        Intent intent = new Intent(MainActivity.this, FutureActivity.class);
        intent.putExtra("name", city);
        intent.putExtra("state", textState.getText().toString());
        intent.putExtra("temperature", textTemperature.getText().toString());
        intent.putExtra("feelsLike", textFeelsLike.getText().toString());
        intent.putExtra("windSpeed", textWindSpeed.getText().toString());
        intent.putExtra("humidity", textPercentHumidity.getText().toString());
        intent.putExtra("imgIconWeather", icon);
        startActivity(intent);
    }

    private void getCurrentWeatherData(String city) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        URL url = new URL();
        url.setLinkDay(city);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.getLinkDay(),
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String day = jsonObject.getString("dt");
                        long dt = Long.valueOf(day);
                        Date date = new Date(dt * 1000L);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd | HH:mm a", Locale.ENGLISH);
                        dateTime = simpleDateFormat.format(date);

                        JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                        status = jsonObjectWeather.getString("main");
                        icon = jsonObjectWeather.getString("icon");

                        JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                        String temp = jsonObjectMain.getString("temp");
                        humidity = jsonObjectMain.getString("humidity");
                        String feelsLike = jsonObjectMain.getString("feels_like");
                        Double a = Double.valueOf(temp);
                        Temp = String.valueOf(a.intValue());
                        FeelsLike = String.valueOf(Double.valueOf(feelsLike).intValue());

                        JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                        speed = jsonObjectWind.getString("speed");

                        JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                        country = jsonObjectSys.getString("country");
                        name = jsonObject.getString("name");
                        upDateUI();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> Log.e("result", "JSON parsing error: " + error.getMessage()));
        requestQueue.add(stringRequest);
    }

    @SuppressLint("SetTextI18n")
    private void upDateUI() {
        textNameCity.setText(name + "-" + country);
        textDateTime.setText(dateTime);
        textState.setText(status);
        textTemperature.setText(Temp + "°C");
        textPercentHumidity.setText(humidity + "%");
        textFeelsLike.setText(FeelsLike + "°C");
        textWindSpeed.setText(speed + "m/s");
        imgIconWeather.setImageResource(getResources().getIdentifier(String.valueOf(UpdateUI.getIconID(icon)), "drawable", getPackageName()));
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