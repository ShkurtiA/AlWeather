package al.shkurti.weather.android.client.response;

import java.util.ArrayList;

import al.shkurti.weather.android.model.TodayWeatherModel;

/**
 * Created by Armando Shkurti on 2015-03-29.
 */
public class TodayWeatherResponse {

    public Data data;
    public Results results;

    public class Data {
        public ArrayList<TodayWeatherModel> current_condition;
        public ArrayList<Error> error;

        public class Error{

            public String msg;

            public String getMsg() {
                return msg;
            }
        }

        public ArrayList<TodayWeatherModel> getCurrent_condition() {
            return current_condition;
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
