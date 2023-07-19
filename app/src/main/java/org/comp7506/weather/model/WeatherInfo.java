package org.comp7506.weather.model;

import java.io.Serializable;

public class WeatherInfo implements Serializable {
    private double temp;

    private String main;

    public double getTemp() {
        return temp;
    }

    public String getMain() {
        return main;
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "temp=" + temp +
                ", main='" + main + '\'' +
                ", description='" + description + '\'' +
                '}';
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

    private String description;
}
