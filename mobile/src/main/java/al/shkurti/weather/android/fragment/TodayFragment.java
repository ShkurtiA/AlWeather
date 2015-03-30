package al.shkurti.weather.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import al.shkurti.weather.android.AlWeatherConfig;
import al.shkurti.weather.android.R;
import al.shkurti.weather.android.client.APICall;
import al.shkurti.weather.android.client.response.ErrorResponse;
import al.shkurti.weather.android.event.AddressEvent;
import al.shkurti.weather.android.event.LocationEvent;
import al.shkurti.weather.android.model.TodayWeatherModel;
import al.shkurti.weather.android.utility.FunctionUtility;
import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;

/**
 * Created by Armando Shkurti on 2015-03-26.
 */
public class TodayFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    public static String TODAY_FRAGMENT_TAG = "TODAY_FRAGMENT_TAG";

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RelativeLayout mContainerLayout;
    private ImageView mWeatherImage;
    private TextView mLocationText;
    private TextView mWeatherConditionText;
    private TextView mHumidityText;
    private TextView mPrecipitationText;
    private TextView mPressureText;
    private TextView mWindSpeedText;
    private TextView mDirectionText;
    private APICall mApiCall;


    public static Fragment newInstance(){
        TodayFragment fragment = new TodayFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateTodayFragment(savedInstanceState);
        checkPlayServices();
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_today,container,false);
        setupSwipeRefreshLayout(rootView);
        renderView(rootView);

        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().registerSticky(this);
    }


    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    @DebugLog
    @Override
    public void onResume() {
        super.onResume();

        if(!FunctionUtility.isNetworkAvailable(getActivity())){
            //TODO show crouton
            return;
        }

        /**
         * If mContainerLayout is invisible and mSwipeRefreshLayout is not refreshing than we should call the API to receive data
         * */
        if(mContainerLayout.getVisibility() == View.INVISIBLE && !mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.post(new Runnable() {
                @Override public void run() {

                    if(!mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getAndCheckLocationSettings();
                    }
                }
            });

        }
    }


    @DebugLog
    @Override// Is called when user swipes refresh layout
    public void onRefresh() {
        getAndCheckLocationSettings();
    }


    @DebugLog // This method will be called when a TodayWeatherModel event is posted
    public void onEvent(TodayWeatherModel todayWeatherModel) {
        mSwipeRefreshLayout.setRefreshing(false);
        FunctionUtility.showHideViews(mContainerLayout, null);
        loadData(todayWeatherModel);
        Toast.makeText(getActivity(), " ok ", Toast.LENGTH_SHORT).show();
    }


    @DebugLog // This method will be called when a ErrorResponse event is posted
    public void onEvent(ErrorResponse errorResponse) {
        mSwipeRefreshLayout.setRefreshing(false);
        //TODO FunctionUitlity.showHideViews(mErrorLayout,null);
        Toast.makeText(getActivity(), "not ok ", Toast.LENGTH_SHORT).show();
    }

    @DebugLog// This method will be called when a LocationEvent event is posted
    public void onEventMainThread(LocationEvent locationEvent) {
        requestDataFromServer(locationEvent);
    }


    @DebugLog// This method will be called when a AddressEvent event is posted
    public void onEventMainThread(AddressEvent addressEvent) {
        mLocationText.setText(addressEvent.getAddress());
    }


    private void setupSwipeRefreshLayout(View rootView) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fragment_today_swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.global_color_primary);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }


    private void renderView(View rootView) {
        mContainerLayout = (RelativeLayout) rootView.findViewById(R.id.fragment_today_container);

        mWeatherImage = (ImageView) rootView.findViewById(R.id.fragment_today_weather_image);
        mLocationText = (TextView) rootView.findViewById(R.id.fragment_today_location);
        mWeatherConditionText = (TextView) rootView.findViewById(R.id.fragment_today_weather_condition);

        mHumidityText = (TextView) rootView.findViewById(R.id.fragment_today_humidity);
        mPrecipitationText = (TextView) rootView.findViewById(R.id.fragment_today_precipitation);
        mPressureText = (TextView) rootView.findViewById(R.id.fragment_today_pressure);
        mWindSpeedText = (TextView) rootView.findViewById(R.id.fragment_today_wind_speed);
        mDirectionText = (TextView) rootView.findViewById(R.id.fragment_today_direction);

        mContainerLayout.setVisibility(View.INVISIBLE);
    }

    private void requestDataFromServer(LocationEvent locationEvent){
        if(mApiCall == null) mApiCall = new APICall();
        mApiCall.getWeatherForToday(locationEvent.getLatitude()+","+locationEvent.getLongitude(), AlWeatherConfig.API_KEY);
    }

    private void loadData(TodayWeatherModel todayWeatherModel) {

        loadImageUtil.loadBitmapToImageView(mWeatherImage,todayWeatherModel.getWeatherIconUrl().get(0).getValue());

        mWeatherConditionText.setText(getTemperature(todayWeatherModel)+" | "+todayWeatherModel.getWeatherDesc().get(0).getValue());
        mHumidityText.setText(todayWeatherModel.getHumidity()+getString(R.string.global_percentage));
        mPrecipitationText.setText(getPrecipitation(todayWeatherModel.getPrecipMM()));
        mPressureText.setText(todayWeatherModel.getPressure()+ " " +getString(R.string.global_pressure_unit_pa));
        mWindSpeedText.setText(getWindSpeed(todayWeatherModel));
        mDirectionText.setText(todayWeatherModel.getWinddir16Point());

    }



}
