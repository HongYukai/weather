package org.comp7506.weather.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class WeatherBean {

    @SerializedName("cityid")
    private String CITYID;

    @SerializedName("cityEn")
    private String CITY;

    @SerializedName("countryEn")
    private String COUNTRY;

    @SerializedName("update_time")
    private String UPDATE_TIME;

    @SerializedName("data")
    private List<DayWeatherBean> DATA_ARRAY;

    public String getCITYID() {
        return CITYID;
    }

    public void setCITYID(String CITYID) {
        this.CITYID = CITYID;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getCOUNTRY() {
        return COUNTRY;
    }

    public void setCOUNTRY(String COUNTRY) {
        this.COUNTRY = COUNTRY;
    }

    public String getUPDATE_TIME() {
        return UPDATE_TIME;
    }

    public void setUPDATE_TIME(String UPDATE_TIME) {
        this.UPDATE_TIME = UPDATE_TIME;
    }

    public List<DayWeatherBean> getDATA_ARRAY() {
        return DATA_ARRAY;
    }

    public void setDATA_ARRAY(List<DayWeatherBean> DATA_ARRAY) {
        this.DATA_ARRAY = DATA_ARRAY;
    }
}
