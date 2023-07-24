package org.comp7506.weather.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import org.comp7506.weather.R;
import org.comp7506.weather.model.LocationInfo;
import org.comp7506.weather.model.WeatherInfo;
import org.comp7506.weather.service.WeatherInquiryService;

public class MainActivity extends Activity implements LocationListener, View.OnClickListener {
    String msg = "Android : ";

    public static String LOCATION_KEY = "lklklk";

    public static String WEATHER_KEY = "wkwkwk";

    private LocationInfo locationInfo = new LocationInfo();

    private LocationManager locationManager;

    private WeatherReceiver weatherReceiver;

    private TextView text;

    private Button nextDaysBtn;

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

        IntentFilter filter=new IntentFilter();

        filter.addAction(this.getString(R.string.current_weather));

        this.registerReceiver(weatherReceiver, filter);

        text = (TextView) findViewById(R.id.description_text_view);

        nextDaysBtn = (Button) findViewById(R.id.next_days_button);
        nextDaysBtn.setOnClickListener(this);
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

        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        locationInfo.setLat(lastKnownLocation.getLatitude());

        locationInfo.setLon(lastKnownLocation.getLongitude());

        initCurrentWeather(locationInfo);
    }

    /** 异步请求current weather，返回的结果由WeatherReceiver处理 */
    void initCurrentWeather(LocationInfo locationInfo) {
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
        if (v.getId() == R.id.next_days_button){
            System.out.println("click button works well");
        }
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
        // 停止位置更新
        locationManager.removeUpdates(this);
    }

    public class WeatherReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            final WeatherInfo weatherInfo = (WeatherInfo) intent.getSerializableExtra(MainActivity.WEATHER_KEY);
            text.setText(weatherInfo.getMain());
        }
    }
}