package com.example.dguamazon;

public class Data {
    public int id;
    public String station;
    public String days;
    public String weather;
    public String hours;
    public String ssid;
    public double score;

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public double getScore() {
        return score;
    }
    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", station='" + station + '\'' +
                ", days='" + days + '\'' +
                ", weather='" + weather + '\'' +
                ", hours=" + hours +
                ", ssid='" + ssid + '\'' +
                ", score=" + score +
                '}';
    }
}