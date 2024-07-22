package com.example.weatherapp.url;

import com.example.weatherapp.location.LocationCord;

public class URL {
    private final String link;

    public URL() {
        link = "https://api.openweathermap.org/data/3.0/onecall?lat=" + LocationCord.lat +
            "&lon=" + LocationCord.lon + "&appid=" + LocationCord.API_KEY;
    }

    public String getLink() {
        return link;
    }
}
