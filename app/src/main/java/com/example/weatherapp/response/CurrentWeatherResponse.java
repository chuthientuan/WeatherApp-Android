package com.example.weatherapp.response;

import com.google.gson.annotations.SerializedName;

public class CurrentWeatherResponse {
    @SerializedName("name")
    private String name;
    @SerializedName("dt")
    private double dateTime;
    @SerializedName("weather")
    private Weather[] weather;
    @SerializedName("main")
    private Main main;
    @SerializedName("wind")
    private Wind wind;
    @SerializedName("sys")
    private Sys sys;

    public String getName() {
        return name;
    }

    public double getDateTime() {
        return dateTime;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Sys getSys() {
        return sys;
    }

    public static class Weather {
        @SerializedName("main")
        private String main;
        @SerializedName("icon")
        private String icon;

        public String getMain() {
            return main;
        }

        public String getIcon() {
            return icon;
        }
    }

    public static class Main {
        @SerializedName("temp")
        private double temp;
        @SerializedName("feels_like")
        private double feelsLike;
        @SerializedName("humidity")
        private int humidity;

        public double getTemp() {
            return temp;
        }

        public double getFeelsLike() {
            return feelsLike;
        }

        public int getHumidity() {
            return humidity;
        }
    }

    public static class Wind {
        @SerializedName("speed")
        private double speed;

        public double getSpeed() {
            return speed;
        }
    }

    public static class Sys {
        @SerializedName("country")
        private String country;

        public String getCountry() {
            return country;
        }
    }
}
