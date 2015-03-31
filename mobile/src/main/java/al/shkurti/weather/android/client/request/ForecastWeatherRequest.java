package al.shkurti.weather.android.client.request;

import al.shkurti.weather.android.client.response.ForecastWeatherResponse;
import al.shkurti.weather.android.client.response.TodayWeatherResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Armando Shkurti on 2015-03-31.
 */
public interface ForecastWeatherRequest {

    /**
     * Here we put the parameters needed to make the request
     * Annotations on the interface methods and its parameters indicate how a request will be handled.
     *
     * @param latLong  Latitude/Longitude
     * @param format Output format as JSON, XML, CSV or TAB
     * @param num_of_days (Optional) Changes the number of day forecast you need.
     * @param fx (Optional) Allows you to enable or disable normal weather output. The possible values are yes or no. By default it is yes.
     * @param cc (Optional) Allows you to enable or disable current weather conditions output. The possible values are yes or no. By default it is yes.
     * @param fx24 (Optional) Returns 24 hourly weather information in a 3 hourly interval response. The possible values are yes or no. By default it is no.
     * @param tp (Optional) Switch between weather forecast time interval from 3 hourly, 6 hourly, 12 hourly (day/night) or 24 hourly (day average). E.g:- tp=24
     * @param key API key
     * @param cb the callback parameter (it should be always at the end)
     *
     * <p> Documentation can be found here: <br> https://developer.worldweatheronline.com/page/explorer-free </p>
     *
     * */
    @GET("/weather.ashx")
    void getForecastWeatherResponse(@Query("q") String latLong,
                                 @Query("format") String format,
                                 @Query("num_of_days") String num_of_days,
                                 @Query("fx") String fx,
                                 @Query("cc") String cc,
                                 @Query("fx24") String fx24,
                                 @Query("tp") String tp,
                                 @Query("key") String key,
                                 Callback<ForecastWeatherResponse> cb);


}
