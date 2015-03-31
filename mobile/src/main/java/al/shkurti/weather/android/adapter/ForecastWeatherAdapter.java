package al.shkurti.weather.android.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import al.shkurti.weather.android.R;
import al.shkurti.weather.android.model.ForecastWeatherModel;
import al.shkurti.weather.android.model.TodayWeatherModel;
import al.shkurti.weather.android.utility.LoadImageUtil;
import al.shkurti.weather.android.view.ErrorViewHolder;
import al.shkurti.weather.android.view.ForecastViewHolder;

/**
 * Created by Armando Shkurti on 2015-03-30.
 */
public class ForecastWeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private static final int TYPE_ERROR = 0;
    private static final int TYPE_ITEM = 1;

    private ArrayList<ForecastWeatherModel> arrayList;
    private LoadImageUtil loadImageUtil;
    private boolean isOffline;
    private SharedPreferences mSharedPreferences;

    public ForecastWeatherAdapter(ArrayList<ForecastWeatherModel> arrayList, LoadImageUtil loadImageUtil){
        this.arrayList = arrayList;
        this.loadImageUtil = loadImageUtil;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ERROR) {
            // create a new view
            View rowViewE = LayoutInflater.from(parent.getContext()).inflate(R.layout.error_view, parent, false);
            ErrorViewHolder vhE = new ErrorViewHolder(rowViewE);
            return vhE;

        }else if(viewType == TYPE_ITEM){

            // create a new view
            View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_forecast_list_item, parent, false);

            // set the view's size, margins, paddings and layout parameters
            // create the view holder with objects inside
            ForecastViewHolder vhF = new ForecastViewHolder(rowView);
            return vhF;
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        if(holder instanceof ErrorViewHolder){//tregon qe jemi te header
            ErrorViewHolder holderError = (ErrorViewHolder) holder;
            if(isOffline)
                holderError.linearLayout.setVisibility(View.VISIBLE);
            else
                holderError.linearLayout.setVisibility(View.GONE);

            return;
        }else if(holder instanceof ForecastViewHolder){

            ForecastViewHolder holderForecast = (ForecastViewHolder) holder;

            loadImageUtil.loadBitmapToImageView(holderForecast.forecastItemIcon,arrayList.get(position).getHourly().get(0).getWeatherIconUrl().get(0).getValue());
            holderForecast.forecastItemCondition.setText(arrayList.get(position).getHourly().get(0).getWeatherDesc().get(0).getValue());

            try {
                String input_date = arrayList.get(position).getDate();
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                Date dt1 = format1.parse(input_date);
                DateFormat format2 = new SimpleDateFormat("EEEE");
                holderForecast.forecastItemDay.setText(format2.format(dt1));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            holderForecast.forecastItemTemp.setText(getTemperature(arrayList.get(position),holderForecast.forecastItemTemp.getContext()));
        }
    }


    @Override
    public int getItemCount() {
        if(arrayList.size() == 0 & isOffline)
            return 1;
        else
            return arrayList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if(arrayList.size() == 0 & isOffline)
            return TYPE_ERROR;
        else
            return TYPE_ITEM;
    }

    public void setOffline(boolean isOffline) {
        this.isOffline = isOffline;
        if(isOffline)
            notifyDataSetChanged();
    }

    protected String getTemperature(ForecastWeatherModel forecastWeatherModel,Context mContext) {
        Resources r = mContext.getResources();
        if(mSharedPreferences == null)  mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        String tempWithUnit = mSharedPreferences.getString(r.getString(R.string.preference_key_unit_temp), r.getString(R.string.global_temperature_unit_c));
        if(tempWithUnit.contains(r.getString(R.string.global_temperature_unit_c))){
            tempWithUnit = forecastWeatherModel.getHourly().get(0).getTempC() + tempWithUnit;
        }else{
            tempWithUnit = forecastWeatherModel.getHourly().get(0).getTempF() + tempWithUnit;
        }
        return tempWithUnit;
    }
}
