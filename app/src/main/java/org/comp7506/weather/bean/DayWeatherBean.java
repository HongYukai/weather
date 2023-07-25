package org.comp7506.weather.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DayWeatherBean implements Serializable {

    @SerializedName("week")
    private String DAY;

    @SerializedName("date")
    private String DATE;

    @SerializedName("wea_img")
    private String WEATHER_IMG;

    @SerializedName("tem1")
    private String HEIGHT_TEMP;

    @SerializedName("tem2")
    private String LOWEST_TEMP;

    public String getDAY() {
        return DAY;
    }

    public void setDAY(String DAY) {
        this.DAY = DAY;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getWEATHER_IMG() {
        return WEATHER_IMG;
    }

    public void setWEATHER_IMG(String WEATHER_IMG) {
        this.WEATHER_IMG = WEATHER_IMG;
    }

    public String getHEIGHT_TEMP() {
        return HEIGHT_TEMP;
    }

    public void setHEIGHT_TEMP(String HEIGHT_TEMP) {
        this.HEIGHT_TEMP = HEIGHT_TEMP;
    }

    public String getLOWEST_TEMP() {
        return LOWEST_TEMP;
    }

    public void setLOWEST_TEMP(String LOWEST_TEMP) {
        this.LOWEST_TEMP = LOWEST_TEMP;
    }

    @Override
    public String toString() {
        return "DayWeatherBean{" +
                "DAY='" + DAY + '\'' +
                ", DATE='" + DATE + '\'' +
                ", WEATHER_IMG='" + WEATHER_IMG + '\'' +
                ", HEIGHT_TEMP='" + HEIGHT_TEMP + '\'' +
                ", LOWEST_TEMP='" + LOWEST_TEMP + '\'' +
                '}';
    }
}
