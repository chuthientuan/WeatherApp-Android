package com.example.weatherapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.adapters.FutureAdapter;
import com.example.weatherapp.entities.FutureDomain;
import com.example.weatherapp.interfaces.WeatherService;
import com.example.weatherapp.response.ForecastResponse;
import com.example.weatherapp.retrofit.RetrofitClient;
import com.example.weatherapp.update.UpdateUI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FutureActivity extends AppCompatActivity {
    public static final String API_KEY = "e5afb6abedc33f32a139cf17a8921af6";
    private static final String UNITS = "metric";
    private ArrayList<FutureDomain> items;
    private FutureAdapter futureAdapter;
    private RecyclerView recyclerViewFuture;

    private TextView textTemperatureToday, textWeatherToday;
    private TextView textFeels, textWind, textHumidity;
    private ImageView imgIcon, imgBack;

    private String nameCity = "";
    private WeatherService weatherService;

    @SuppressLint("DiscouragedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_future);

        weatherService = RetrofitClient.getInstance().create(WeatherService.class);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setMapping();
        recyclerViewFuture.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        items = new ArrayList<>();
        futureAdapter = new FutureAdapter(items);
        recyclerViewFuture.setAdapter(futureAdapter);

        imgBack.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        String city = intent.getStringExtra("name");
        textTemperatureToday.setText(intent.getStringExtra("temperature"));
        textWeatherToday.setText(intent.getStringExtra("state"));
        textFeels.setText(intent.getStringExtra("feelsLike"));
        textWind.setText(intent.getStringExtra("windSpeed"));
        textHumidity.setText(intent.getStringExtra("humidity"));
        String iconImg = intent.getStringExtra("imgIconWeather");

        if (iconImg != null) {
            imgIcon.setImageResource(getResources().getIdentifier(String.valueOf(UpdateUI.getIconID(iconImg)), "drawable", getPackageName()));
        }
        Log.d("result", "Du lieu qua: " + city);
        if (city.isEmpty()) {
            nameCity = "Hanoi";
            get5DaysData(nameCity);
        } else {
            nameCity = city;
            get5DaysData(nameCity);
        }
    }

    private void setMapping() {
        textTemperatureToday = findViewById(R.id.textTemperatureToday);
        textWeatherToday = findViewById(R.id.textWeatherToday);
        textFeels = findViewById(R.id.textFeels);
        textWind = findViewById(R.id.textWind);
        textHumidity = findViewById(R.id.textHumidity);
        imgIcon = findViewById(R.id.imgIcon);
        recyclerViewFuture = findViewById(R.id.recyclerViewFuture);
        imgBack = findViewById(R.id.imgback);
    }

    private void get5DaysData(String city) {
        weatherService.getForecast(city, API_KEY, UNITS)
                .enqueue(new Callback<ForecastResponse>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(@NonNull Call<ForecastResponse> call, @NonNull Response<ForecastResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            try {
                                items.clear();
                                TreeMap<String, FutureDomain> dailyForecasts = new TreeMap<>();

                                for (ForecastResponse.HourlyForecast forecast : response.body().getList()) {
                                    double dt = forecast.getDt();
                                    Date date = new Date((long) (dt * 1000L));
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE", Locale.ENGLISH);
                                    String dateTime = simpleDateFormat.format(date);
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                                    String dateOnly = dateFormat.format(date);

                                    int temp_min = (int) forecast.getMain().getTempMin();
                                    int temp_max = (int) forecast.getMain().getTempMax();

                                    String status = forecast.getWeather().get(0).getMain();
                                    String icon = forecast.getWeather().get(0).getIcon();

                                    if (!dailyForecasts.containsKey(dateOnly)) {
                                        dailyForecasts.put(dateOnly, new FutureDomain(dateTime, icon, status, temp_max, temp_min));
                                    }
                                }
                                items.addAll(dailyForecasts.values());
                                futureAdapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                Log.e("API", "Error parsing JSON: " + e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ForecastResponse> call, @NonNull Throwable t) {
                        Log.e("API", "Error: " + t.getMessage());
                    }
                });
    }
}