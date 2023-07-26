package org.comp7506.weather.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.comp7506.weather.HourlyAdapter;
import org.comp7506.weather.R;
import org.comp7506.weather.model.CurveView;
import org.comp7506.weather.model.WeatherInfo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

public class HourlyWeather extends Activity {

    public final String WEATHER_KEY = "hwk";

    private HourlyWeather.WeatherReceiver weatherReceiver;

    private TextView DAY;

    private TextView CURTEMP;
    private TextView TempH;
    private TextView TempL;

    private ImageView imageView;

    private RecyclerView HourlyWeather;
    private HourlyAdapter hourlyAdapter;

    private int  LowestTemp = Integer.MAX_VALUE;
    private int  HighestTemp = Integer.MIN_VALUE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_weather);

        weatherReceiver = new HourlyWeather.WeatherReceiver();

        IntentFilter filter= new IntentFilter();

        filter.addAction(this.getString(R.string.hourly_weather));

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

        @SuppressLint({"SetTextI18n", "ResourceType"})
        @Override
        public void onReceive(Context context, Intent intent) {
            final WeatherInfo weatherInfo = (WeatherInfo) intent.getSerializableExtra(WEATHER_KEY);
            Log.d("test_Hourly", weatherInfo.toString());

            DAY = findViewById(R.id.day);
            DAY.setText(weatherInfo.getDate());

            imageView = findViewById(R.id.wea_img);
            imageView.setVisibility(View.VISIBLE);
            HashMap<String, Integer> svg_map = new HashMap<String, Integer>();
            svg_map.put("少云", R.raw.a_duoyun);
            svg_map.put("多云", R.raw.a_duoyun);
            svg_map.put("雨", R.raw.a_xiaoyu);
            svg_map.put("阵雨", R.raw.a_zhenyu);
            svg_map.put("雷阵雨", R.raw.a_leizhenyu);
            svg_map.put("雪", R.raw.a_daxue);
            svg_map.put("晴", R.raw.a_qing);
            svg_map.put("雾", R.raw.a_wu);
//            SVG svg = null;
//            try {
//                svg = SVG.getFromResource(context, svg_map.get(weatherInfo.getMain()) == null ? R.raw.wi_rain : svg_map.get(weatherInfo.getMain()));
//            } catch (SVGParseException e) {
//                throw new RuntimeException(e);
//            }
//            PictureDrawable drawable = new PictureDrawable(svg.renderToPicture());
            int imgSource = svg_map.get(weatherInfo.getMain()) == null ? R.raw.a_unknown: svg_map.get(weatherInfo.getMain());
            imageView.setImageResource(imgSource);

            CURTEMP = findViewById(R.id.temp);
            CURTEMP.setText(Integer.toString((int) weatherInfo.getTemp()) + "℃");

            for(Map<String, String> eachHour: weatherInfo.getHOURLY_ARRAY()){
                String temp = eachHour.get("temp");
                int tempInt  = Integer.parseInt(temp);
                LowestTemp = Math.min(tempInt, LowestTemp);
                HighestTemp = Math.max(tempInt, HighestTemp);
            }

            TempH = findViewById(R.id.tempH);
            TempH.setText(Integer.toString(HighestTemp) + "℃");

            TempL = findViewById(R.id.tempL);
            TempL.setText(Integer.toString(LowestTemp) + "℃");


            weatherInfo.getHOURLY_ARRAY().remove(0);
            hourlyAdapter = new HourlyAdapter(context, weatherInfo.getHOURLY_ARRAY());
            System.out.println("hourlyAdapterworks"+ hourlyAdapter.getItemCount());
            HourlyWeather = findViewById(R.id.hour_recycle_view);
            HourlyWeather.setAdapter(hourlyAdapter);
            System.out.println("setAdapter works");
            LinearLayoutManager layoutManager  =new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            HourlyWeather.setLayoutManager(layoutManager);


        }
    }
}