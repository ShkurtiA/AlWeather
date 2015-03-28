package al.shkurti.weather.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import al.shkurti.weather.android.R;

/**
 * Created by Armando Shkurti on 2015-03-26.
 */
public class TodayFragment extends Fragment{

    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static Fragment newInstance(){
        TodayFragment fragment = new TodayFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_today,container,false);



        return rootView;
    }
}
