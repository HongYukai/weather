# 1. Architecture Diagram

![](https://github.com/HongYukai/weather/blob/main/architecture.png)





# 2. Interfaces

## 2.1 MainActivity

```java
/**  
   * get one week weather info and show
   *
   * @param locationInfo 
   */
  void initOneWeekWeather(
      LocationInfo locationInfo
  );

/**  
   * click to show the details of current weather info(turn to HourlyActivity)
   *
   * @param locationInfo 
   */
  void showHourlyWeather(
      LocationInfo locationInfo
  );

/**  
   * click to show half month weather(turn to HalfMonthActivity)
   *
   * @param locationInfo 
   */
  void showHalfMonthWeather(
      LocationInfo locationInfo
  );

/**  
   * get user's location
   *
   * @param none
   * @return none
   */
  void getLocation();

/**  
   * when location is changed, update the weather
   *
   * @param location     instance of Location, user's new location
   * @return none
   */
  void onLocationChanged(
      Location location
  );

/**  
   * show cities for user to select
   *
   * @param locationInfo 
   */
  void showCitySelectionDialog(
      Location location
  );
```

## 2.2 HourlyActivity

```java
/**  
   * get hourly weather info and show
   *
   * @param locationInfo
   */
  void InitHourlyWeatherInfo(
      LocationInfo locationInfo,
  );
```

## 2.3 HalfMonthActivity

```java
/**  
   * get half month weather info and show
   *
   * @param locationInfo 
   */
  void InitHalfMonthWeatherInfo(
      locationInfo locationInfo,
  );
```

## 2.4 WeatherInquiryService

```java
/**  
   * Get weather info of one city from open weather api
   *
   * async call, see the code and annotation for details
   */
```





# 3. Models

```java
class WeatherInfo {
    String temp;
    String maxTemp;
    String minTemp;
    String humidity;
    String cloud;
    String wind;
    String location;
    String sunset;
    String sunrise;
    String pressure;
    String msg;
    // .......
}

class LocationInfo {
    double lat;
    double lon;
    String city; // name of city
}
// .......
```

