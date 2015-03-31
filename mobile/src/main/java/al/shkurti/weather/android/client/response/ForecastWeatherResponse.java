package al.shkurti.weather.android.client.response;

import java.util.ArrayList;

import al.shkurti.weather.android.model.ForecastWeatherModel;


/**
 * Created by Armando Shkurti on 2015-03-31.
 */
public class ForecastWeatherResponse {

    public Data data;
    public Results results;

    public class Data {

        public ArrayList<ForecastWeatherModel> weather;
        public ArrayList<Error> error;

        public class Error{

            public String msg;

            public String getMsg() {
                return msg;
            }
        }

        public ArrayList<ForecastWeatherModel> getWeather() {
            return weather;
        }

        public ArrayList<Error> getError() {
            return error;
        }

    }

    public class Results {

        public Error error;

        public class Error{

            public String type;
            public String message;

            public String getType() {
                return type;
            }

            public String getMessage() {
                return message;
            }
        }

        public Error getError() {
            return error;
        }
    }

    public Data getData() {
        return data;
    }

    public Results getResults() {
        return results;
    }
}
