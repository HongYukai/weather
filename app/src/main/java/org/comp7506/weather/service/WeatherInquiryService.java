package org.comp7506.weather.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.comp7506.weather.R;
import org.comp7506.weather.activity.MainActivity;
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
/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class WeatherInquiryService extends IntentService {
    String msg = "NET IO : ";
    private static final String API_KEY = "642fb7578586f3a2d61c84a251a41c38";

    private static final String CURRENT_URL = "https://api.openweathermap.org/data/2.5/weather";

    private static final String NEXT_WEEK_URL = "https://www.yiketianqi.com/free/week?unescape=1&appid=63263372&appsecret=86eRO3z7";

    private static final String CURRENT_WEATHER = "CURRENT_WEATHER";

    private static final String NEXT_WEEK_WEATHER = "NEXT 7 DAYS";

    private static final int TIMEOUT = 5000;

    public WeatherInquiryService() {
        super("WeatherInquiryService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            System.out.println("intent action: " + action);
            final LocationInfo locationInfo = (LocationInfo) intent.getSerializableExtra(MainActivity.LOCATION_KEY);
            if (CURRENT_WEATHER.equalsIgnoreCase(action)) {
                makeRequest(locationInfo, CURRENT_URL, CURRENT_WEATHER);
                //TODO: 增加hourly weather, halfmonth weather
            } else if (NEXT_WEEK_WEATHER.equalsIgnoreCase(action)){
                makeRequest(locationInfo, NEXT_WEEK_URL, NEXT_WEEK_WEATHER);
            }
        }
    }

    private String makeRequest(LocationInfo locationInfo, String url, String flag) {
        System.out.println("current flag is " + flag);
        String lat = String.valueOf(locationInfo.getLat());

        String lon = String.valueOf(locationInfo.getLon());

        String city = String.valueOf(locationInfo.getCity());
        System.out.println("city: " + city);

        try {
            String encodedUrl = "";
            if(CURRENT_WEATHER.equalsIgnoreCase(flag)) {
                // 构建带参数的 URL，可能需要根据不同的请求填充参数，这里只做了current的
                 encodedUrl = url + "?lat=" + URLEncoder.encode(lat, "UTF-8") +
                        "&lon=" + URLEncoder.encode(lon, "UTF-8") + "&exclude=minutely" +
                        "&appid=" + URLEncoder.encode(API_KEY, "UTF-8");
            }else if(NEXT_WEEK_WEATHER.equalsIgnoreCase(flag)){
                encodedUrl = url + "&city=" + URLEncoder.encode(city, "UTF-8");
            }
            // 创建 URL 对象
            java.net.URL apiUrl = new URL(encodedUrl);

            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

            // 设置请求方法为 GET
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(TIMEOUT);

            // 发起请求
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 请求成功，读取响应内容
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);

                }

                //just to check whether it can receive the message

                if(flag == NEXT_WEEK_WEATHER){
                    System.out.println(response.toString());
                    return response.toString();
                }

                reader.close();

                // 解析 JSON 响应，可能需要根据不同的响应做对应的解析，这里只做了current的
                JSONObject jsonResponse = new JSONObject(response.toString());

                Log.d(msg, jsonResponse.toString());

                WeatherInfo weatherInfo = new WeatherInfo();
                // 提取需要的数据
                JSONArray weather = jsonResponse.getJSONArray("weather");
                for (int i = 0; i < weather.length(); i++) {
                    JSONObject item = weather.getJSONObject(i);
                    String main = item.getString("main");
                    String desc = item.getString("description");
                    weatherInfo.setMain(main);
                    weatherInfo.setDescription(desc);
                }

                JSONObject main = jsonResponse.getJSONObject("main");

                weatherInfo.setTemp(main.getDouble("temp") - 273.15);

                Log.d(msg, weatherInfo.toString());

                Intent intent=new Intent();

                intent.setAction(CURRENT_WEATHER);
//                intent.setAction(flag);

                Bundle bundle = new Bundle();

                bundle.putSerializable(MainActivity.WEATHER_KEY, weatherInfo);

                intent.putExtras(bundle);

                sendBroadcast(intent);

            } else {
                // 请求失败，输出错误信息
                Log.d(msg, "Request failed. Response Code: " + responseCode + "\nFROM: " + encodedUrl);
            }

            // 断开连接
            connection.disconnect();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}