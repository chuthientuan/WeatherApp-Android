package com.example.weatherapp.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastResponse {
    @SerializedName("list")
    private List<HourlyForecast> list;

    public List<HourlyForecast> getList() {
        return list;
    }

    public static class HourlyForecast {
        @SerializedName("dt")
        private double dt;
        @SerializedName("dt_txt")
        private String dateTime;
        @SerializedName("main")
        private Main main;
        @SerializedName("weather")
        private List<Weather> weather;

        public double getDt() {
            return dt;
        }

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
            @SerializedName("temp_min")
            private double tempMin;
            @SerializedName("temp_max")
            private double tempMax;
            @SerializedName("temp")
            private double temp;

            public double getTempMin() {
                return tempMin;
            }

            public double getTempMax() {
                return tempMax;
            }

            public double getTemp() {
                return temp;
            }
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
    }
}
