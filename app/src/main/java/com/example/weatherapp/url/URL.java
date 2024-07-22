package com.example.weatherapp.url;

import com.example.weatherapp.location.LocationCord;

public class URL {
    private final String link;
    private static String city_url;

    public URL() {
        link = "https://api.openweathermap.org/data/3.0/onecall?lat=" + LocationCord.lat +
            "&lon=" + LocationCord.lon + "&appid=" + LocationCord.API_KEY;
    }

    public String getLink() {
        return link;
    }

    public static void setCity_url(String city) {
        city_url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + LocationCord.API_KEY;
    }

    public static String getCity_url(){
        return city_url;
    }
}
