package al.shkurti.weather.android.client;

import al.shkurti.weather.android.AlWeatherConfig;
import al.shkurti.weather.android.client.request.ForecastWeatherRequest;
import al.shkurti.weather.android.client.request.TodayWeatherRequest;
import al.shkurti.weather.android.client.response.ErrorResponse;
import al.shkurti.weather.android.client.response.ForecastWeatherResponse;
import al.shkurti.weather.android.client.response.TodayWeatherResponse;
import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Armando Shkurti on 2015-03-29.
 */
public class APICall {

    /**
     * Here we make the request using Retrofit library (http://square.github.io/retrofit/)
     *  to request for today weather data
     *
     * @param latLong the location in latitude and longitude that we want to get weather information
     * @param apiKey the apiKey of https://developer.worldweatheronline.com Weather API
     * */
    public void getWeatherForToday(String latLong, String apiKey){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setEndpoint(AlWeatherConfig.API_URL)
                .build();

        //The RestAdapter class generates an implementation of the TodayWeatherRequest interface.
        TodayWeatherRequest mTodayWeatherRequest = restAdapter.create(TodayWeatherRequest.class);

        Callback<TodayWeatherResponse> callback = new Callback<TodayWeatherResponse>() {

            @Override
            public void success(TodayWeatherResponse todayWeatherResponse, Response response) {
                if(todayWeatherResponse.getData() == null &&
                        todayWeatherResponse.getData().getCurrent_condition().size() >0 &&
                        todayWeatherResponse.getData().getCurrent_condition().get(0) != null){
                    EventBus.getDefault().postSticky(todayWeatherResponse.getData().getCurrent_condition().get(0));
                }else{
                    String error="";
                    if(todayWeatherResponse.getData() != null &&
                            todayWeatherResponse.getData().getError() !=null &&
                            todayWeatherResponse.getData().getError().size() > 0){
                        error = todayWeatherResponse.getData().getError().get(0).getMsg();
                    }else if (todayWeatherResponse.getResults() != null &&
                            todayWeatherResponse.getResults().getError() != null ){
                        error = todayWeatherResponse.getResults().getError().getType();
                        error = error + ": "+todayWeatherResponse.getResults().getError().getMessage();
                    }
                    EventBus.getDefault().postSticky(new ErrorResponse(error));
                }
            }

            @DebugLog
            @Override
            public void failure(RetrofitError error) {
                EventBus.getDefault().postSticky(new ErrorResponse(error.getMessage()));
            }
        };

        mTodayWeatherRequest.getTodayWeatherResponse(latLong,"json","1","today","no","yes",apiKey,callback);

    }

    /**
     * Here we make the request using Retrofit library (http://square.github.io/retrofit/)
     * to request for weather forecast data
     *
     * @param latLong the location in latitude and longitude that we want to get weather information
     * @param apiKey the apiKey of https://developer.worldweatheronline.com Weather API
     * */
    public void getWeatherForecast(String latLong, String apiKey , String numbOfDays){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setEndpoint(AlWeatherConfig.API_URL)
                .build();

        //The RestAdapter class generates an implementation of the TodayWeatherRequest interface.
        ForecastWeatherRequest mForecastWeatherRequest = restAdapter.create(ForecastWeatherRequest.class);

        Callback<ForecastWeatherResponse> callback = new Callback<ForecastWeatherResponse>() {


            @Override
            public void success(ForecastWeatherResponse forecastWeatherResponse, Response response) {
                if(forecastWeatherResponse.getData() == null &&
                        forecastWeatherResponse.getData().getWeather().size()  == AlWeatherConfig.WEATHER_FORECAST_DAYS ){
                    EventBus.getDefault().postSticky(forecastWeatherResponse.getData().getWeather());
                }else{
                    String error="";
                    if(forecastWeatherResponse.getData() != null &&
                            forecastWeatherResponse.getData().getError() !=null &&
                            forecastWeatherResponse.getData().getError().size() > 0){
                        error = forecastWeatherResponse.getData().getError().get(0).getMsg();
                    }else if (forecastWeatherResponse.getResults() != null &&
                            forecastWeatherResponse.getResults().getError() != null ){
                        error = forecastWeatherResponse.getResults().getError().getType();
                        error = error + ": "+forecastWeatherResponse.getResults().getError().getMessage();
                    }
                    EventBus.getDefault().postSticky(new ErrorResponse(error));
                }

            }

            @DebugLog
            @Override
            public void failure(RetrofitError error) {
                EventBus.getDefault().postSticky(new ErrorResponse(error.getMessage()));
            }
        };

        mForecastWeatherRequest.getForecastWeatherResponse(latLong, "json", numbOfDays, "yes", "no", "no", "24", apiKey, callback);

    }

}
