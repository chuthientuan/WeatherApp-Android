package com.example.weatherapp.url;

import com.example.weatherapp.location.LocationCord;

public class URL {
    private String linkDay;
    private String link;

    public URL() {
    }

    public void setLinkDay(String city) {
        linkDay = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&appid=" + LocationCord.API_KEY;
    }

    public String getLinkDay() {
        return linkDay;
    }

    public void setLink(String city) {
        link = "https://api.openweathermap.org/data/2.5/forecast?q=" + city + "&appid=" + LocationCord.API_KEY + "&units=metric";
    }

    public String getLink() {
        return link;
    }
}
