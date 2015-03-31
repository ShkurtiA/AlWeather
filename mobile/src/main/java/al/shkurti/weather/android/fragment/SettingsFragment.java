package al.shkurti.weather.android.fragment;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

import al.shkurti.weather.android.R;

/**
 * Created by Armando Shkurti on 2015-03-30.
 */
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preference_settings);
        setupPreferenceView();

    }


    private void setupPreferenceView() {
        // workaround because the preference xml doesnt except string as default value
        ListPreference listPreferenceLength = (ListPreference) findPreference(getString(R.string.preference_key_unit_length));
        if(listPreferenceLength.getValue()==null) {
            // if its null it means we should load the default value
            listPreferenceLength.setValueIndex(0);
        }

        ListPreference listPreferenceTemp = (ListPreference) findPreference(getString(R.string.preference_key_unit_temp));
        if(listPreferenceTemp.getValue()==null) {
            listPreferenceTemp.setValueIndex(0);
        }
    }

}
