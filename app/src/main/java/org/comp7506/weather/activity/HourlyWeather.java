package org.comp7506.weather.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.comp7506.weather.R;
import org.comp7506.weather.model.WeatherInfo;

public class HourlyWeather extends AppCompatActivity {

    public final String WEATHER_KEY = "nwk";

    private HourlyWeather.WeatherReceiver weatherReceiver;

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_weather);

        weatherReceiver = new HourlyWeather.WeatherReceiver();

        IntentFilter filter= new IntentFilter();

        filter.addAction(this.getString(R.string.next_week_weather));

        this.registerReceiver(weatherReceiver, filter);

//        System.out.println("print out  map list: " + list.toString());

//        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.activity_next_days,
//                new String[]{"date", "day", "wea_img", "highest_temp", "lowest_temp"},
//                new int[]{R.id.date, R.id.day, R.id.wea_img, R.id.highest_temp, R.id.lowest_temp});
//
//        setListAdapter(adapter);
//
//        System.out.println("print out  map list: " + list.toString());
    }


    public class WeatherReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            final WeatherInfo weatherInfo = (WeatherInfo) intent.getSerializableExtra(WEATHER_KEY);
            Log.d("test_Hourly", weatherInfo.toString());
        }
    }
}