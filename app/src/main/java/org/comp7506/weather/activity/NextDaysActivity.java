package org.comp7506.weather.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

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

    private ProgressBar progressBar;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_next_days);

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
    }


    public class WeatherReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(count < 1) {
//                progressBar.setVisibility(View.GONE);
                final WeatherInfo weatherInfo = (WeatherInfo) intent.getSerializableExtra(WEATHER_KEY);
                /** TODO: your job **/
                HashMap<String, Integer> svg_map = new HashMap<String, Integer>();
                svg_map.put("yun", R.raw.wi_day_sunny_overcast);
                svg_map.put("yin", R.raw.wi_cloudy);
                svg_map.put("yu", R.raw.wi_rain);
                svg_map.put("lei", R.raw.wi_day_sleet_storm);
                svg_map.put("xue", R.raw.wi_snow);
                svg_map.put("qing", R.raw.wi_day_sunny);
                svg_map.put("wu", R.raw.wi_fog);
                svg_map.put("bingbao", R.raw.wi_hail);
                svg_map.put("shachen", R.raw.wi_dust);
                ArrayList<DayWeatherBean> weatherArray = weatherInfo.getNEXT_DAYS_ARRAY();
                for (DayWeatherBean dayWeather : weatherArray) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("day", dayWeather.getDAY());
                    map.put("date", dayWeather.getDATE());
                    SVG svg = null;
                    try {
                        svg = SVG.getFromResource(context, svg_map.get(dayWeather.getWEATHER_IMG()) == null ? R.raw.wi_rain : svg_map.get(dayWeather.getWEATHER_IMG()));
                    } catch (SVGParseException e) {
                        throw new RuntimeException(e);
                    }
                    map.put("wea_img",svg.renderToPicture());

                    map.put("tempRange", dayWeather.getLOWEST_TEMP() + " ~ " + dayWeather.getHEIGHT_TEMP());
                    list.add(map);
                }
                count++;
                SimpleAdapter adapter = new SimpleAdapter(context, list, R.layout.activity_next_days,
                        new String[]{"date", "day","wea_img", "tempRange"},
                        new int[]{R.id.date, R.id.day, R.id.wea_img, R.id.temp_range});

                adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                    public boolean setViewValue(View view, Object data,
                                                String textRepresentation) {
                        if (view instanceof ImageView && data instanceof Drawable) {
                            ImageView iv = (ImageView) view;
                            iv.setImageDrawable((Drawable) data);
                            return true;
                        } else
                            return false;
                    }
                });
                setListAdapter(adapter);


                Log.d("test_WeatherArray", weatherArray.toString());
            }
        }
    }

}