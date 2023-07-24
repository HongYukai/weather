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

import org.comp7506.weather.R;
import org.comp7506.weather.bean.DayWeatherBean;
import org.comp7506.weather.bean.WeatherBean;
import org.comp7506.weather.model.LocationInfo;
import org.comp7506.weather.model.WeatherInfo;
import org.comp7506.weather.service.WeatherInquiryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NextDaysActivity extends ListActivity {

    ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    public final String WEATHER_KEY = "nwk";

    private WeatherReceiver weatherReceiver;

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

        weatherReceiver = new WeatherReceiver();

        IntentFilter filter= new IntentFilter();

        filter.addAction(this.getString(R.string.next_week_weather));

        this.registerReceiver(weatherReceiver, filter);


    }

    public class WeatherReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            final WeatherInfo weatherInfo = (WeatherInfo) intent.getSerializableExtra(WEATHER_KEY);
            /** TODO: your job **/
            Log.d("test", weatherInfo.toString());
        }
    }

}