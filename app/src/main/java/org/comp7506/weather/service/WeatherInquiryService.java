package org.comp7506.weather.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import org.comp7506.weather.R;
import org.comp7506.weather.activity.HourlyWeather;
import org.comp7506.weather.activity.MainActivity;
import org.comp7506.weather.activity.NextDaysActivity;
import org.comp7506.weather.bean.DayWeatherBean;
import org.comp7506.weather.model.LocationInfo;
import org.comp7506.weather.model.WeatherInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class WeatherInquiryService extends IntentService {
    String msg = "NET IO : ";
    private static final String API_KEY = "642fb7578586f3a2d61c84a251a41c38";

    private static final String CURRENT_URL = "https://api.openweathermap.org/data/2.5/weather";

    private static final String HU = "https://pro.openweathermap.org/data/2.5/forecast/hourly";

    private static final String NEXT_WEEK_URL = "https://www.tianqiapi.com/api?version=v1&appid=36646344&appsecret=c1lgQbP9";
//    private static final String NEXT_WEEK_URL = "https://devapi.qweather.com/v7/weather/7d?";
//    private static final String KEY = "18b5cb7b53994bff9296aa3195f38d45";
    private static final String HOURLY_URL = "https://devapi.qweather.com/v7/grid-weather/24h?";
    private static final String KEY = "6332640fa6044661b83381f8a3ad5812";

    private static final String CURRENT_WEATHER = "CURRENT_WEATHER";
    private static final String NEXT_WEEK_WEATHER = "NEXT 7 DAYS";

    private static final String HOURLY_WEATHER = "HOURLY WEATHER";
    private static final int TIMEOUT = 5000;

    private static final String TIPS = "Tips:";
    private static final Map<String, String> map = new HashMap<String, String>();
    private static final Map<String, String> IconMap = new HashMap<String, String>();

    public WeatherInquiryService() {
        super("WeatherInquiryService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            System.out.println("intent action: " + action);
            final LocationInfo locationInfo = (LocationInfo) intent.getSerializableExtra(MainActivity.LOCATION_KEY);
            final String query = (String) intent.getSerializableExtra(MainActivity.QUERY_KEY);
            if (CURRENT_WEATHER.equalsIgnoreCase(action)) {
                makeRequest(locationInfo, CURRENT_URL, CURRENT_WEATHER);
                //TODO: 增加hourly weather, halfmonth weather
            } else if (NEXT_WEEK_WEATHER.equalsIgnoreCase(action)){
                makeRequest(locationInfo, NEXT_WEEK_URL, NEXT_WEEK_WEATHER);
            }
            else if (TIPS.equalsIgnoreCase(action)) {
                try {
                    String res = "";
                    String url = "http://audit.nat100.top?query=" + query;
                    URL serverUrl = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) serverUrl.openConnection();
                    connection.setRequestMethod("GET");

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();
                        res = response.toString();
                        Log.d(msg, res);
                    } else {
                        Log.d(msg, "Request failed with response code: " + responseCode);
                    }
                    Intent i = new Intent();

                    i.setAction(MainActivity.QUERY_RESPONSE);

                    Bundle bundle = new Bundle();

                    bundle.putSerializable(MainActivity.QUERY_KEY, res);

                    i.putExtras(bundle);

                    sendBroadcast(i);
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
            else{
                makeRequest(locationInfo, HOURLY_URL, HOURLY_WEATHER);
            }
        }
    }

    private void makeRequest(LocationInfo locationInfo, String url, String flag) {
        System.out.println("current flag is " + flag);
        String lat = String.valueOf(locationInfo.getLat());

        String lon = String.valueOf(locationInfo.getLon());

        String city = String.valueOf(locationInfo.getCity());
        System.out.println("city: " + city);

        map.put("星期一","Monday");
        map.put("星期二", "Tuesday");
        map.put("星期三", "Wednesday");
        map.put("星期四", "Thursday");
        map.put("星期五", "Friday");
        map.put("星期六", "Saturday");
        map.put("星期日", "Sunday");

        IconMap.put("少云", "Clouds");
        IconMap.put("晴", "Clear");
        IconMap.put("雨","Rain");

        try {
            String encodedUrl = "";
            if(CURRENT_WEATHER.equalsIgnoreCase(flag)) {
                // 构建带参数的 URL，可能需要根据不同的请求填充参数，这里只做了current的
                encodedUrl = url + "?q=" + locationInfo.getCity() + /** "?lat=" + URLEncoder.encode(lat, "UTF-8") +
                        "&lon=" + URLEncoder.encode(lon, "UTF-8") + "&exclude=minutely" +
                        **/ "&appid=" + URLEncoder.encode(API_KEY, "UTF-8");
            }else if(NEXT_WEEK_WEATHER.equalsIgnoreCase(flag)){
                encodedUrl = url + "&city=" + mapCity(city);
//                encodeUrl = url + "&location" =
            }else{
                encodedUrl = url + "&location=" + lon + "," + lat + "&key=" + KEY;
            }
            // 创建 URL 对象
            System.out.println(encodedUrl);
            URL apiUrl = new URL(encodedUrl);

            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

            // 设置请求方法为 GET
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(TIMEOUT);
//            System.out.println("already get");

            // 发起请求
            int responseCode = connection.getResponseCode();
            System.out.println(responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 请求成功，读取响应内容
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);

                }

                reader.close();

                // 解析 JSON 响应，可能需要根据不同的响应做对应的解析，这里只做了current的
                JSONObject jsonResponse = new JSONObject(response.toString());

                Log.d(msg, jsonResponse.toString());

                WeatherInfo weatherInfo = new WeatherInfo();
                System.out.println("weatherInfo");

                //current开始
                System.out.println(CURRENT_WEATHER.equalsIgnoreCase(flag));
                if(CURRENT_WEATHER.equalsIgnoreCase(flag)) {
                    // 提取需要的数据,这里需要修改 丢失了湿度和wind的数据在“main“和”wind“
                    JSONArray weather = jsonResponse.getJSONArray("weather");
                    for (int i = 0; i < weather.length(); i++) {
                        JSONObject item = weather.getJSONObject(i);
                        String main = item.getString("main");
                        String desc = item.getString("description");
                        weatherInfo.setMain(main);
                        weatherInfo.setDescription(desc);
                    }

                    JSONObject main = jsonResponse.getJSONObject("main");

                    JSONObject wind = jsonResponse.getJSONObject("wind");

                    weatherInfo.setTemp(main.getDouble("temp") - 273.15);

                    weatherInfo.setHumidity(main.getInt("humidity"));

                    weatherInfo.setWind(wind.getInt("speed"));

                    Log.d(msg, weatherInfo.toString());

                    Intent intent = new Intent();

                    intent.setAction(flag);

                    Bundle bundle = new Bundle();

                    bundle.putSerializable(new MainActivity().WEATHER_KEY, weatherInfo);

                    intent.putExtras(bundle);

                    sendBroadcast(intent);

                } //current到这里

                if (NEXT_WEEK_WEATHER.equalsIgnoreCase(flag)) {
                    JSONArray data = jsonResponse.getJSONArray("data");
                    ArrayList<DayWeatherBean> weathersList = new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        DayWeatherBean dayweather = new DayWeatherBean();
                        JSONObject item = data.getJSONObject(i);
                        String day = item.getString("week");
                        String date= item.getString("date");
                        String weaImg = item.getString("wea_img");
                        String tempH = item.getString("tem1");
                        String tempL = item.getString("tem2");
                        dayweather.setDAY(map.get(day));
                        dayweather.setDATE(date);
                        dayweather.setWEATHER_IMG(weaImg);
                        dayweather.setHEIGHT_TEMP(tempH);
                        dayweather.setLOWEST_TEMP(tempL);
                        weathersList.add(dayweather);
                    }

                    weatherInfo.setNEXT_DAYS_ARRAY(weathersList);

                    Log.d(msg, weatherInfo.toString());

                    Intent intent = new Intent();

                    intent.setAction(flag);

                    Bundle bundle = new Bundle();

                    bundle.putSerializable(new NextDaysActivity().WEATHER_KEY, weatherInfo);

                    intent.putExtras(bundle);

                    sendBroadcast(intent);
                }
//                System.out.println(response.toString());
                if(HOURLY_WEATHER.equalsIgnoreCase(flag)) {
                    String date = jsonResponse.getString("updateTime").substring(0,10);
                    System.out.println(date);
                    JSONArray data = jsonResponse.getJSONArray("hourly");
                    ArrayList<Map<String, String>> hourlyList = new ArrayList<>();

                    JSONObject startItem = data.getJSONObject(0);
                    String curTemp = startItem.getString("temp");
                    String curIcon = startItem.getString("text");

                    for (int i = 0; i < data.length(); i++) {
                        Map<String, String> hourInfo = new HashMap<>();
                        JSONObject item = data.getJSONObject(i);
                        int timezone = Integer.parseInt(item.getString("fxTime").substring(11,13));
                        int time = ((timezone + 7)%24);
                        String temp = item.getString("temp");
                        String weather = item.getString("text");
                        hourInfo.put("time", String.valueOf(time));
                        hourInfo.put("temp", temp);
                        hourInfo.put("weather", weather);
                        hourlyList.add(hourInfo);
                }
                    weatherInfo.setDate(date);

                    weatherInfo.setMain(curIcon);

                    weatherInfo.setTemp(Double.parseDouble(curTemp));

                    weatherInfo.setHOURLY_ARRAY(hourlyList);

                    Log.d(msg, weatherInfo.toString());

                    Intent intent = new Intent();

                    intent.setAction(flag);

                    Bundle bundle = new Bundle();

                    bundle.putSerializable(new HourlyWeather().WEATHER_KEY, weatherInfo);

                    intent.putExtras(bundle);

                    sendBroadcast(intent);

                }

            } else {
                // 请求失败，输出错误信息
                Log.d(msg, "Request failed. Response Code: " + responseCode + "\nFROM: " + encodedUrl);
            }

            // 断开连接
            connection.disconnect();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }
    private String mapCity(String cityEn){
        String result = "";
        if(cityEn.equalsIgnoreCase("Guangzhou")) result = "广州";
        if(cityEn.equalsIgnoreCase("Hong Kong")) result = "香港";
        if(cityEn.equalsIgnoreCase("Beijing")) result = "北京";
        if(cityEn.equalsIgnoreCase("Shanghai")) result = "上海";
        if(cityEn.equalsIgnoreCase("Chengdu")) result = "成都";
        if(cityEn.equalsIgnoreCase("Chongqing")) result = "重庆";
        if(cityEn.equalsIgnoreCase("Hangzhou")) result = "杭州";
        if(cityEn.equalsIgnoreCase("Xian")) result = "西安";
        if(cityEn.equalsIgnoreCase("Shenzhen")) result = "深圳";
        if(cityEn.equalsIgnoreCase("Wuhan")) result = "武汉";
        if(cityEn.equalsIgnoreCase("Tianjin")) result = "天津";
        if(cityEn.equalsIgnoreCase("Nanjing")) result = "南京";
        if(cityEn.equalsIgnoreCase("Suzhou")) result = "苏州";
        if(cityEn.equalsIgnoreCase("Luoyang")) result = "洛阳";
        if(cityEn.equalsIgnoreCase("Kaifeng")) result = "开封";
        if(cityEn.equalsIgnoreCase("Yangzhou")) result = "扬州";
        if(cityEn.equalsIgnoreCase("Changsha")) result = "长沙";
        if(cityEn.equalsIgnoreCase("Kunming")) result = "昆明";
        if(cityEn.equalsIgnoreCase("Haerbin")) result = "哈尔滨";

        return result;
    }



}