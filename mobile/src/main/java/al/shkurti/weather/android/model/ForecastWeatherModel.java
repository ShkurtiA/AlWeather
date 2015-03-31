package al.shkurti.weather.android.model;

import java.util.ArrayList;

/**
 * Created by Armando Shkurti on 2015-03-30.
 */
public class ForecastWeatherModel {

    public String date;
    public ArrayList<HourlyModel> hourly;

    public class HourlyModel {
        public String tempC;
        public String tempF;

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


        public ArrayList<WeatherDesc> getWeatherDesc() {
            return weatherDesc;
        }

        public ArrayList<WeatherIconUrl> getWeatherIconUrl() {
            return weatherIconUrl;
        }

        public String getTempC() {
            return tempC;
        }

        public String getTempF() {
            return tempF;
        }


    }

    public String getDate() {
        return date;
    }

    public ArrayList<HourlyModel> getHourly() {
        return hourly;
    }

}
