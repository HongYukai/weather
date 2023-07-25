package org.comp7506.weather.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.comp7506.weather.R;
import org.comp7506.weather.model.CurveView;
import org.comp7506.weather.model.WeatherInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HourlyWeather extends Activity {

    public final String WEATHER_KEY = "nwk";

    private HourlyWeather.WeatherReceiver weatherReceiver;

    private TextView DAY;

    private RecyclerView HourlyWeather;

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

//        CurveView curveView = findViewById(R.id.curveView);
//        List<String> xList = Arrays.asList("1","2","3","4","5","6","7", "8","9", "10", "1","2","3","4","5","6","7", "8","9", "10", "1","2","3","4");
//        List<String> yList = Arrays.asList("0","50","55","51","53","56","59", "0","50","55","51","53","56","59","0","50","55","51","53","56","59", "0","50","55");
//        List<String> xList = Arrays.asList("1","2","3","4","5","6","7");
//        List<String> yList = Arrays.asList("0","50","55","51","53","56","59");
//        curveView.setData(xList, yList);
    }


    public class WeatherReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            final WeatherInfo weatherInfo = (WeatherInfo) intent.getSerializableExtra(WEATHER_KEY);
            Log.d("test_Hourly", weatherInfo.toString());
            DAY.setText("Friday");
            for(Map<String, String> eachHour: weatherInfo.getHOURLY_ARRAY()){
                String time = eachHour.get("time");
                String temp = eachHour.get("temp");

                HourlyWeather = (RecyclerView) findViewById(R.id.hour_view);
                HourlyWeather.setHasFixedSize(true);

//                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//                layoutManager.setAutoMeasureEnabled(true);
//                HourlyWeather.setLayoutManager(layoutManager);
//
//                RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//                HourlyWeather.addItemDecoration(itemDecoration);
            }


        }
    }
}