package org.comp7506.weather.model;

import org.comp7506.weather.bean.DayWeatherBean;

import java.io.Serializable;
import java.util.ArrayList;

public class WeatherInfo implements Serializable {
    private double temp;

    private String main;

    private String description;

    private ArrayList<DayWeatherBean> DATA_ARRAY;

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


    public ArrayList<DayWeatherBean> getDATA_ARRAY() {
        return DATA_ARRAY;
    }

    public void setDATA_ARRAY(ArrayList<DayWeatherBean> DATA_ARRAY) {
        this.DATA_ARRAY = DATA_ARRAY;
    }


    @Override
    public String toString() {
        return "WeatherInfo{" +
                "temp=" + temp +
                ", main='" + main + '\'' +
                ", description='" + description + '\'' +
                ", DATA_ARRAY=" + DATA_ARRAY +
                '}';
    }

}
