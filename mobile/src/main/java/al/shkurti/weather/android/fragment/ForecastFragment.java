package al.shkurti.weather.android.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import al.shkurti.weather.android.AlWeatherConfig;
import al.shkurti.weather.android.R;
import al.shkurti.weather.android.adapter.ForecastWeatherAdapter;
import al.shkurti.weather.android.client.APICall;
import al.shkurti.weather.android.client.response.ErrorResponse;
import al.shkurti.weather.android.event.LocationEvent;
import al.shkurti.weather.android.event.NetworkChange;
import al.shkurti.weather.android.model.ForecastWeatherModel;
import al.shkurti.weather.android.model.TodayWeatherModel;
import al.shkurti.weather.android.utility.FunctionUtility;
import de.greenrobot.event.EventBus;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import hugo.weaving.DebugLog;

/**
 * Created by Armando Shkurti on 2015-03-30.
 */
public class ForecastFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    public static String FORECAST_FRAGMENT_TAG = "FORECAST_FRAGMENT_TAG";

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private String latLongLocation;
    private APICall mApiCall;
    private ArrayList<ForecastWeatherModel> mArrayList;
    private ForecastWeatherAdapter mForecastWeatherAdapter;

    public static Fragment newInstance(String locationLatLong){
        ForecastFragment mFragment = new ForecastFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(AlWeatherConfig.LOCATION_LAT_LONG_KEY, "");
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateForecastFragment(savedInstanceState);
        checkPlayServices();
        setRetainInstance(true);
        latLongLocation = getArguments().getString(AlWeatherConfig.LOCATION_LAT_LONG_KEY);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_forecast,container,false);
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

    @Override
    public void onResume() {
        super.onResume();

        if(!FunctionUtility.isNetworkAvailable(getActivity())){
            int id =((ViewGroup)getView().getParent()).getId();
            Crouton.makeText(getActivity(),
                    getString(R.string.error_no_connection_available),
                    Style.ALERT,
                    id).show();
            return;
        }

        if(latLongLocation.length()==0 && !mSwipeRefreshLayout.isRefreshing() && mArrayList.size() == 0){// if we didnt receive a location than we should wait till we get one
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(!mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getAndCheckLocationSettings();
                    }
                }
            },600);

        }else if(!mSwipeRefreshLayout.isRefreshing() && mArrayList.size() == 0 ){ //If mSwipeRefreshLayout is not refreshing than we should call the API to receive data
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(!mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(true);
                        requestDataFromServer(latLongLocation);
                    }
                }
            },600);
        }

    }


    @DebugLog
    @Override// Is called when user swipes refresh layout
    public void onRefresh() {
        if(latLongLocation.length()==0){// we dont have location
            getAndCheckLocationSettings();
        }else { // we have location
            requestDataFromServer(latLongLocation);
        }
    }


    @DebugLog// This method will be called when a LocationEvent event is posted
    public void onEventMainThread(LocationEvent locationEvent) {
        latLongLocation = locationEvent.getLatitude()+","+locationEvent.getLongitude();
        EventBus.getDefault().removeStickyEvent(locationEvent);
        if(mSwipeRefreshLayout.isRefreshing()){// it means we have come from a request, next step is calling api
            requestDataFromServer(latLongLocation);
        }
    }

    @DebugLog // This method will be called when a TodayWeatherModel event is posted
    public void onEvent(ArrayList<ForecastWeatherModel> arrayList) {
        mSwipeRefreshLayout.setRefreshing(false);
        mForecastWeatherAdapter.setOffline(false);
        mArrayList.clear();
        mArrayList.addAll(arrayList);
        mForecastWeatherAdapter.notifyDataSetChanged();
    }


    @DebugLog // This method will be called when a TodayWeatherModel event is posted
    public void onEvent(NetworkChange networkChange) {
        if(FunctionUtility.isNetworkAvailable(getActivity())){
            int id =((ViewGroup)getView().getParent()).getId();
            Crouton.makeText(getActivity(),
                    getString(R.string.error_no_connection_available),
                    Style.ALERT,
                    id).show();
            return;
        }
    }

    @DebugLog // This method will be called when a ErrorResponse event is posted
    public void onEvent(ErrorResponse errorResponse) {
        mSwipeRefreshLayout.setRefreshing(false);
        mForecastWeatherAdapter.setOffline(true);
    }


    private void setupSwipeRefreshLayout(View rootView) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fragment_today_swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.global_color_primary);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }


    private void renderView(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycle_view_forecast);

        if(mArrayList == null) mArrayList = new ArrayList<>();
        if(mForecastWeatherAdapter == null) mForecastWeatherAdapter = new ForecastWeatherAdapter(mArrayList, loadImageUtil);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mForecastWeatherAdapter);
        mRecyclerView.getItemAnimator().setChangeDuration(500);
    }

    private void requestDataFromServer(String latLongLocation){
        if(mApiCall == null) mApiCall = new APICall();
        mApiCall.getWeatherForecast(latLongLocation, AlWeatherConfig.API_KEY, AlWeatherConfig.WEATHER_FORECAST_DAYS+"");
    }
}
