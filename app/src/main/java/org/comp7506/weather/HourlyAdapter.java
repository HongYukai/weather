package org.comp7506.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.graphics.drawable.PictureDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.WeatherViewHolder>{

    private Context context;
    private List<Map<String, String>> hourWeatherList;
    HashMap<String, Integer> svg_map = new HashMap<String, Integer>();


    public HourlyAdapter(Context context, List<Map<String, String>> hourWeatherList) {
        this.context = context;
        this.hourWeatherList = hourWeatherList;
        svg_map.put("少云", R.raw.wi_day_sunny_overcast);
        svg_map.put("多云", R.raw.wi_cloudy);
        svg_map.put("雨", R.raw.wi_rain);
        svg_map.put("阵雨", R.raw.wi_day_rain);
        svg_map.put("雷阵雨", R.raw.wi_day_sleet_storm);
        svg_map.put("雪", R.raw.wi_snow);
        svg_map.put("晴", R.raw.wi_day_sunny);
        svg_map.put("雾", R.raw.wi_fog);
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.each_hour_weather_layout,parent,false);
        WeatherViewHolder weatherViewHolder = new WeatherViewHolder(view);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        Map<String, String> hourWeather = hourWeatherList.get(position);

        holder.time.setText(hourWeather.get("time"));
        holder.temp.setText(hourWeather.get("temp"));
//        SVG svg;
//        try {
//            svg = SVG.getFromResource(context, svg_map.get(hourWeather.get("weather")) == null ? R.raw.wi_rain : svg_map.get(hourWeather.get("weather")));
//        } catch (SVGParseException e) {
//            throw new RuntimeException(e);
//        }
//        PictureDrawable drawable = new PictureDrawable(svg.renderToPicture());
//        holder.weather.setImageDrawable(drawable);

    }

    @Override
    public int getItemCount() {
        if(hourWeatherList == null){
            return 0;
        }
        return hourWeatherList.size();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder{
        TextView temp, time;
        ImageView weather;
        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);

            temp = itemView.findViewById(R.id.temp);
            time = itemView.findViewById(R.id.time);
            weather = itemView.findViewById(R.id.wea_img);
        }
    }


}
