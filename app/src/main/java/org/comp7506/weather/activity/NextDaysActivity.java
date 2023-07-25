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

    public final String WEATHER_KEY = "nwk";

    private WeatherReceiver weatherReceiver;
    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        weatherReceiver = new WeatherReceiver();

        IntentFilter filter= new IntentFilter();

        filter.addAction(this.getString(R.string.next_week_weather));

        this.registerReceiver(weatherReceiver, filter);


//        Intent intent = this.getIntent();

//        ArrayList<String> date = intent.getStringArrayListExtra("date");
//        ArrayList<String> day = intent.getStringArrayListExtra("day");
//        ArrayList<String> tempH = intent.getStringArrayListExtra("tempH");
//        ArrayList<String> tempL = intent.getStringArrayListExtra("tempL");
//        for(int i = 0; i < date.size(); i++){
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("date", date.get(i));
//            map.put("day", day.get(i));
//            map.put("tempH", tempH.get(i));
//            map.put("tempL", tempL.get(i));
//            list.add(map);
//        }

//        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.activity_next_days,
//                new String[]{"date", "day", "wea_img", "highest_temp", "lowest_temp"},
//                new int[]{R.id.date, R.id.day, R.id.wea_img, R.id.highest_temp, R.id.lowest_temp});

//        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.activity_next_days,
//                new String[]{"date", "day", "tempH", "tempL"},
//                new int[]{R.id.date, R.id.day, R.id.highest_temp, R.id.lowest_temp});
//        setListAdapter(adapter);
    }

    public class WeatherReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(count < 1) {
                final WeatherInfo weatherInfo = (WeatherInfo) intent.getSerializableExtra(WEATHER_KEY);
                /** TODO: your job **/
                ArrayList<DayWeatherBean> weatherArray = weatherInfo.getNEXT_DAYS_ARRAY();
                for (DayWeatherBean dayWeather : weatherArray) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("day", dayWeather.getDAY());
                    map.put("date", dayWeather.getDATE());
                    map.put("wea_img", dayWeather.getWEATHER_IMG());
                    map.put("tempRange", dayWeather.getLOWEST_TEMP() + " ~ " + dayWeather.getHEIGHT_TEMP());
                    list.add(map);
                }
                count++;

                SimpleAdapter adapter = new SimpleAdapter(context, list, R.layout.activity_next_days,
                        new String[]{"date", "day", "tempRange", "tempL"},
                        new int[]{R.id.date, R.id.day, R.id.temp_range});
                setListAdapter(adapter);

                Log.d("test_WeatherArray", weatherArray.toString());
            }
        }
    }

}