package al.shkurti.weather.android.client.request;

import al.shkurti.weather.android.client.response.TodayWeatherResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Armando Shkurti on 2015-03-29.
 */
public interface TodayWeatherRequest {

    /**
     * Here we put the parameters needed to make the request
     * Annotations on the interface methods and its parameters indicate how a request will be handled.
     *
     * @param latLong  Latitude/Longitude
     * @param format Output format as JSON, XML, CSV or TAB
     * @param num_of_days (Optional) Changes the number of day forecast you need.
     * @param date (Optional) Get weather for a particular date within next 15 day. It supports today, tomorrow and a date in future.
     * @param fx (Optional) Allows you to enable or disable normal weather output. The possible values are yes or no. By default it is yes.
     * @param cc (Optional) Allows you to enable or disable current weather conditions output. The possible values are yes or no. By default it is yes.
     * @param key API key
     * @param cb the callback parameter (it should be always at the end)
     *
     * <p> Documentation can be found here: <br> https://developer.worldweatheronline.com/page/explorer-free </p>
     *
     * */
    @GET("/weather.ashx")
    void getTodayWeatherResponse(@Query("q") String latLong,
                                 @Query("format") String format,
                                 @Query("num_of_days") String num_of_days,
                                 @Query("date") String date,
                                 @Query("fx") String fx,
                                 @Query("cc") String cc,
                                 @Query("key") String key,
                                 Callback<TodayWeatherResponse> cb);

}
