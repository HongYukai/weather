#1. Architecture Diagram



![diagram](../../diagram.png)





# 2. Interfaces

##2.1 MainActivity

```java
/**  
   * get one week weather info and show
   *
   * @param city, String, name of city
   * @return none
   */
  void initOneWeekWeather(
      String city
  );

/**  
   * click to show half month weather
   *
   * @param city, String, name of city
   * @return none
   */
  void showHalfMonthWeather(
      String city
  );

/**  
   * click to show the details of current weather info(turn to HourlyActivity)
   *
   * @param city, String, name of city
   * @return none
   */
  void showHourlyWeather(
      String city
  );

/**  
   * click to show half month weather(turn to HalfMonthActivity)
   *
   * @param city, String, name of city
   * @return none
   */
  void showHalfMonthWeather(
      String city
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
   * @param location	instance of Location, user's current location
   * @return none
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
   * @param city     String, name of city
   */
  WeatherInfo InitHourlyWeatherInfo(
      String city,
  );
```

## 2.3 HalfMonthActivity

```java
/**  
   * get half month weather info and show
   *
   * @param city     String, name of city
   */
  WeatherInfo InitHalfMonthWeatherInfo(
      String city,
  );
```

## 2.4 WeatherInquiryService

```java
/**  
   * Get weather info of one city from open weather api
   *
   * @param city     String, name of city
   * @param dc	int, date count(1->24h 7->a week 15->half month)
   * @return instance of WeatherInfo
   */
  WeatherInfo getWeatherInfo(
      String city,
      int dc,
  );
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
// .......
```

