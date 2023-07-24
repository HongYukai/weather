package org.comp7506.weather.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import org.comp7506.weather.R;
import org.comp7506.weather.bean.DayWeatherBean;
import org.comp7506.weather.bean.WeatherBean;
import org.comp7506.weather.model.WeatherInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NextDaysActivity extends ListActivity {

    ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = this.getIntent();
        ArrayList<String> date = intent.getStringArrayListExtra("date");
        ArrayList<String> day = intent.getStringArrayListExtra("day");

        for(int i = 0; i < date.size(); i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("date", date.get(i));
            map.put("day", day.get(i));
            list.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.activity_next_days,
                                                    new String[]{"date", "day"},
                                                    new int[]{R.id.date, R.id.day});

        setListAdapter(adapter);


    }

    private void updateUiOfWeather(WeatherInfo weatherInfo){
        if(weatherInfo == null){
            return;
        }
        ArrayList<DayWeatherBean> weatherList  = weatherInfo.getDATA_ARRAY();
        DayWeatherBean dayWeather = weatherList.get(0);


    }

}