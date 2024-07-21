package com.example.weatherapp.entities;

public class FutureDomain {
    private String day;
    private String picPath;
    private String status;
    private int highTemp;
    private int loeTemp;

    public FutureDomain(String day, String picPath, String status, int highTemp, int loeTemp) {
        this.day = day;
        this.picPath = picPath;
        this.status = status;
        this.highTemp = highTemp;
        this.loeTemp = loeTemp;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(int highTemp) {
        this.highTemp = highTemp;
    }

    public int getLoeTemp() {
        return loeTemp;
    }

    public void setLoeTemp(int loeTemp) {
        this.loeTemp = loeTemp;
    }
}
