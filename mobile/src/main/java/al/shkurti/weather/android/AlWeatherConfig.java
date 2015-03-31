package al.shkurti.weather.android;

/**
 * Created by Armando Shkurti on 2015-03-29.
 */
public class AlWeatherConfig {

    /**
     * https://developer.worldweatheronline.com/
     * acquired - Sat, 28 Mar 2015 23:43:52 GMT
     * queries per second - 5
     * queries per day - 1,000
     * */
    public static final String API_KEY = "a119fbf04b646ba832ca9093a1906";

    /**
     * URL needed to call weather api
     * */
    public static final String API_URL = "http://api.worldweatheronline.com/free/v2";

    public static final String LOCATION_LAT_LONG_KEY = "AlWeatherLoc";

    public static final int WEATHER_FORECAST_DAYS = 5;

    public static final double INCH_TO_MILLIMETER = 25.4;

    /**
    * Variables needed for Google Play Location API
    **/
    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;
    public static final String PACKAGE_NAME = "al.shkurti.weather.android";
    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";
    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";

}
