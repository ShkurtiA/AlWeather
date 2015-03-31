package al.shkurti.weather.android.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import al.shkurti.weather.android.R;

/**
 * Created by Armando Shkurti on 2015-03-31.
 */
public class ForecastViewHolder extends RecyclerView.ViewHolder {

    public ImageView forecastItemIcon;
    public TextView forecastItemDay;
    public TextView forecastItemTemp;
    public TextView forecastItemCondition;

    public ForecastViewHolder(View itemView) {
        super(itemView);
        forecastItemIcon = (ImageView) itemView.findViewById(R.id.fragment_forecast_weather_image);
        forecastItemDay = (TextView) itemView.findViewById(R.id.fragment_forecast_item_day);
        forecastItemTemp = (TextView) itemView.findViewById(R.id.fragment_forecast_item_temp);
        forecastItemCondition = (TextView) itemView.findViewById(R.id.fragment_forecast_item_weather_condition);
    }

}
