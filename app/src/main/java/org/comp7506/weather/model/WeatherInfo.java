package org.comp7506.weather.model;

import org.comp7506.weather.bean.DayWeatherBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class WeatherInfo implements Serializable {
    private double temp;

    private String date;

    private String main;

    private String description;

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getWind() {
        return wind;
    }

    public void setWind(double wind) {
        this.wind = wind;
    }

    private int humidity;

    private double wind;

    private ArrayList<DayWeatherBean> NEXT_DAYS_ARRAY;

    private ArrayList<Map<String, String>> HOURLY_ARRAY;

    public double getTemp() {
        return temp;
    }


    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }


    public ArrayList<DayWeatherBean> getNEXT_DAYS_ARRAY() {
        return NEXT_DAYS_ARRAY;
    }

    public void setNEXT_DAYS_ARRAY(ArrayList<DayWeatherBean> DATA_ARRAY) {
        this.NEXT_DAYS_ARRAY = DATA_ARRAY;
    }

    public ArrayList<Map<String, String>> getHOURLY_ARRAY() {
        return HOURLY_ARRAY;
    }

    public void setHOURLY_ARRAY(ArrayList<Map<String, String>> HOURLY_ARRAY) {
        this.HOURLY_ARRAY = HOURLY_ARRAY;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "temp=" + temp +
                ", main='" + main + '\'' +
                ", description='" + description + '\'' +
                ", NEXT_DAYS_ARRAY=" + NEXT_DAYS_ARRAY +
                ", HOURLY_ARRAY=" + HOURLY_ARRAY +
                '}';
    }
}
