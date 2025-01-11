package com.example.weatherapp.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HourlyForecastResponse {
    @SerializedName("list")
    private List<HourlyForecast> list;

    public List<HourlyForecast> getList() {
        return list;
    }

    public static class HourlyForecast {
        @SerializedName("dt_txt")
        private String dateTime;
        @SerializedName("main")
        private Main main;
        @SerializedName("weather")
        private List<Weather> weather;

        public String getDateTime() {
            return dateTime;
        }

        public Main getMain() {
            return main;
        }

        public List<Weather> getWeather() {
            return weather;
        }

        public static class Main {
            @SerializedName("temp")
            private double temp;

            public double getTemp() {
                return temp;
            }
        }

        public static class Weather {
            @SerializedName("icon")
            private String icon;

            public String getIcon() {
                return icon;
            }
        }
    }
}
