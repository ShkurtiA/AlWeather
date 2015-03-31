package al.shkurti.weather.android.model;

import java.util.ArrayList;

/**
 * Created by Armando Shkurti on 2015-03-28.
 */
public class TodayWeatherModel {

    public String observation_time;

    public String temp_C;
    public String temp_F;
    public String humidity;
    public String precipMM;
    public String pressure;
    public String winddir16Point;
    public String windspeedKmph;
    public String windspeedMiles;

    public ArrayList<WeatherDesc> weatherDesc;
    public ArrayList<WeatherIconUrl> weatherIconUrl;

    public class WeatherDesc {

        public String value;

        public String getValue() {
            return value;
        }
    }

    public class WeatherIconUrl {

        public String value;

        public String getValue() {
            return value;
        }
    }

    public String getObservation_time() {
        return observation_time;
    }

    public String getTemp_C() {
        return temp_C;
    }

    public String getTemp_F() {
        return temp_F;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPrecipMM() {
        return precipMM;
    }

    public String getPressure() {
        return pressure;
    }

    public String getWinddir16Point() {
        return winddir16Point;
    }

    public String getWindspeedKmph() {
        return windspeedKmph;
    }

    public String getWindspeedMiles() {
        return windspeedMiles;
    }

    public ArrayList<WeatherDesc> getWeatherDesc() {
        return weatherDesc;
    }

    public ArrayList<WeatherIconUrl> getWeatherIconUrl() {
        return weatherIconUrl;
    }
}
