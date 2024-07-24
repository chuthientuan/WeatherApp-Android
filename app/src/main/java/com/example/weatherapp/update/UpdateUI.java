package com.example.weatherapp.update;

import com.example.weatherapp.R;

public class UpdateUI {
    public static int getIconID(String icon) {
        switch (icon) {
            case "01d":
                return R.drawable.sunny;
            case "01n":
                return R.drawable.clear_night;
            case "02d":
                return R.drawable.cloudy_sunny;
            case "02n":
                return R.drawable.clear_night;
            case "03d":
            case "03n":
                return R.drawable.cloudy;
            case "04d":
            case "04n":
                return R.drawable.cloudy;
            case "09d":
            case "09n":
                return R.drawable.rainy;
            case "10d":
                return R.drawable.rainy;
            case "10n":
                return R.drawable.rainy;
            case "11d":
            case "11n":
                return R.drawable.storm;
            case "13d":
            case "13n":
                return R.drawable.snowy;
            case "50d":
            case "50n":
                return R.drawable.windy;
            default:
                return R.drawable.wind;
        }
    }
}
