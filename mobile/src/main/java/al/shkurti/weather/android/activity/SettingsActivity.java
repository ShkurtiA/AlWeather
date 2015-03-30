package al.shkurti.weather.android.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import al.shkurti.weather.android.R;
import al.shkurti.weather.android.fragment.SettingsFragment;
import al.shkurti.weather.android.utility.FunctionUtility;

/**
 * Created by Armando Shkurti on 2015-03-30.
 */
public class SettingsActivity  extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        setupActionBar();

        setupView(savedInstanceState);

    }


    private void setupView(Bundle savedInstanceState) {
        if(savedInstanceState == null){
            // Display the fragment as the main content.
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings_content_frame, new SettingsFragment())
                    .commit();
        }
    }


    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.menu_settings));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home && !(FunctionUtility.hasJellyBean())) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
