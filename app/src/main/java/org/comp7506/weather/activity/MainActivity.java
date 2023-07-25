package org.comp7506.weather.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.card.MaterialCardView;

import org.comp7506.weather.R;
import org.comp7506.weather.model.LocationInfo;
import org.comp7506.weather.model.WeatherInfo;
import org.comp7506.weather.service.WeatherInquiryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class MainActivity extends Activity implements LocationListener, View.OnClickListener, View.OnTouchListener {
    String msg = "Android : ";

    public static String LOCATION_KEY = "lklklk";

    public final String WEATHER_KEY = "cwk";

    private LocationInfo locationInfo = new LocationInfo();

    private LocationManager locationManager;

    private WeatherReceiver weatherReceiver;

    private TextView text;

    private Button nextDaysBtn;

    private MaterialCardView card;

    private ProgressBar progressBar;

    private ImageView imageView;

    private TextView citySelector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Log.d(msg, "The onCreate() event");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // 检查是否授予位置权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            // 如果没有权限，则请求权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        } else {
            // 已有权限，请求位置更新
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, (float) 0, (LocationListener) this);
        }

        weatherReceiver= new WeatherReceiver();

        IntentFilter filter= new IntentFilter();

        filter.addAction(this.getString(R.string.current_weather));

        this.registerReceiver(weatherReceiver, filter);

        progressBar = findViewById(R.id.progressBar);

        imageView = findViewById(R.id.imageView2);

        text = (TextView) findViewById(R.id.description_text_view);

        nextDaysBtn = (Button) findViewById(R.id.next_days_button);

        nextDaysBtn.setOnTouchListener(this);

        card = (MaterialCardView) findViewById(R.id.todayMaterialCard);

        card.setOnTouchListener(this);

        citySelector = findViewById(R.id.select_city);
        citySelector.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showInputDialog();
                return false;
            }
        });
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Type your city name");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String userInput = input.getText().toString();
                LocationInfo locationInfo = new LocationInfo();
                locationInfo.setCity(userInput);
                initCurrentWeather(locationInfo);
                citySelector.setText(userInput + "    🔍");
            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /** 当活动即将可见时调用 */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(msg, "The onStart() event");
    }

    /** 当活动可见时调用 */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(msg, "The onResume() event");
        // 请求位置，解析位置
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

//        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//        locationInfo.setLat(lastKnownLocation.getLatitude());
//
//        locationInfo.setLon(lastKnownLocation.getLongitude());
    }

    /** 异步请求current weather，返回的结果由WeatherReceiver处理 */
    void initCurrentWeather(LocationInfo locationInfo) {

        progressBar.setVisibility(View.VISIBLE);

        imageView.setVisibility(View.INVISIBLE);

        Intent intent = new Intent(this, WeatherInquiryService.class);

        intent.setAction(this.getString(R.string.current_weather));

        Bundle bundle = new Bundle();

        bundle.putSerializable(LOCATION_KEY, locationInfo);

        intent.putExtras(bundle);

        startService(intent);
    }

    /** 当点击按钮可得到接下来七天的信息*/
    @Override
    public void onClick(View v){
//        if (v.getId() == R.id.next_days_button){
//            System.out.println("next days button works well");
//            Intent intent = new Intent(getBaseContext(), NextDaysActivity.class);
//            ArrayList<String> date = new ArrayList<>();
//            ArrayList<String> day = new ArrayList<>();
//            date.add("2023-07-24");
//            day.add("Monday");
//            date.add("2023-07-25");
//            day.add("Tuesday");
//
//            intent.putStringArrayListExtra("date", date);
//            intent.putStringArrayListExtra("day", day);
//            startActivity(intent);
//            nextWeather(locationInfo);
//        }
//
//        if (v.getId() == R.id.contentMainLayout){
//            System.out.println("Main layout click works well");
//            Intent intent = new Intent(getBaseContext(), HourlyWeather.class);
//            hourlyWeather(locationInfo);
//            startActivity(intent);
//        }
    }


    /** 当其他活动获得焦点时调用 */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(msg, "The onPause() event");
    }

    /** 当活动不再可见时调用 */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(msg, "The onStop() event");
    }

    /** 当活动将被销毁时调用 */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(msg, "The onDestroy() event");
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        locationInfo.setLat(location.getLatitude());
        locationInfo.setLon(location.getLongitude());
//        locationInfo.setCity(getCity(locationInfo));
//        locationInfo.setCity("Beijing");
        initCurrentWeather(locationInfo);
        // 停止位置更新
        locationManager.removeUpdates(this);
    }


    private String getCity(LocationInfo locationInfo) {
        List<Address> result = null;
        String res = "";
        try {
            if (locationInfo != null) {
                Geocoder gc = new Geocoder(this, Locale.getDefault());
                result = gc.getFromLocation(locationInfo.getLat(),
                        locationInfo.getLon(), 1);
                Log.i(msg, "获取地址信息："+result.toString());
                res = result.get(0).getLocality();
                Log.i(msg, "城市：" + res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {
        Log.d(msg, "onClick");
        if (v.getId() == R.id.next_days_button){
            System.out.println("next days button works well");
            Intent intent = new Intent(getBaseContext(), NextDaysActivity.class);
            startActivity(intent);
            nextWeather(locationInfo);
        }

        if (v.getId() == R.id.todayMaterialCard){
            System.out.println("Main layout click works well");
            Intent intent = new Intent(getBaseContext(), HourlyWeather.class);
            hourlyWeather(locationInfo);
            startActivity(intent);
        }
        return true;
    }

    private void nextWeather(LocationInfo locationInfo){
        Intent intent = new Intent(this, WeatherInquiryService.class);

        intent.setAction(this.getString(R.string.next_7_days));

        Bundle bundle = new Bundle();

        bundle.putSerializable(LOCATION_KEY, locationInfo);

        intent.putExtras(bundle);

        startService(intent);
    }

    private void hourlyWeather(LocationInfo locationInfo) {
        Intent intent = new Intent(this, WeatherInquiryService.class);

        intent.setAction(this.getString(R.string.hourly_weather));

        Bundle bundle = new Bundle();

        bundle.putSerializable(LOCATION_KEY, locationInfo);

        intent.putExtras(bundle);

        startService(intent);
    }

    public class WeatherReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            final WeatherInfo weatherInfo = (WeatherInfo) intent.getSerializableExtra(WEATHER_KEY);
            progressBar.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
            text.setText(weatherInfo.getMain());
        }
    }
}