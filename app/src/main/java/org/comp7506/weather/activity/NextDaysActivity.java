package org.comp7506.weather.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.comp7506.weather.R;
import org.comp7506.weather.bean.DayWeatherBean;
import org.comp7506.weather.model.WeatherInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NextDaysActivity extends ListActivity {

    ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    Map<String, Object> map = new HashMap<String, Object>();

    public final String WEATHER_KEY = "nwk";

    private WeatherReceiver weatherReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = this.getIntent();

        ArrayList<String> date = intent.getStringArrayListExtra("date");
        ArrayList<String> day = intent.getStringArrayListExtra("day");

//        for(int i = 0; i < date.size(); i++){
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("date", date.get(i));
//            map.put("day", day.get(i));
//            list.add(map);
//        }

        weatherReceiver = new WeatherReceiver();

        IntentFilter filter= new IntentFilter();

        filter.addAction(this.getString(R.string.next_week_weather));

        this.registerReceiver(weatherReceiver, filter);

        System.out.println("print out  map list: " + list.toString());

        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.activity_next_days,
                new String[]{"date", "day", "wea_img", "highest_temp", "lowest_temp"},
                new int[]{R.id.date, R.id.day, R.id.wea_img, R.id.highest_temp, R.id.lowest_temp});

        setListAdapter(adapter);

        System.out.println("print out  map list: " + list.toString());




    }

    public class WeatherReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            final WeatherInfo weatherInfo = (WeatherInfo) intent.getSerializableExtra(WEATHER_KEY);
            /** TODO: your job **/
            ArrayList<DayWeatherBean> weatherArray = weatherInfo.getNEXT_DAYS_ARRAY();
            for(DayWeatherBean dayWeather: weatherArray){
                map.put("day", dayWeather.getDAY());
                map.put("date", dayWeather.getDATE());
                map.put("wea_img", dayWeather.getWEATHER_IMG());
                map.put("higest_temp", dayWeather.getHEIGHT_TEMP());
                map.put("lowest_temo", dayWeather.getLOWEST_TEMP());
                list.add(map);
            }

            Log.d("test_WeatherArray", weatherArray.toString());
        }
    }

}