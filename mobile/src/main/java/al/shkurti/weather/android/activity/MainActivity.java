package al.shkurti.weather.android.activity;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import al.shkurti.weather.android.R;
import al.shkurti.weather.android.event.LocationEvent;
import al.shkurti.weather.android.fragment.ForecastFragment;
import al.shkurti.weather.android.fragment.TodayFragment;
import de.greenrobot.event.EventBus;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import hugo.weaving.DebugLog;


public class MainActivity extends ActionBarActivity {

    public String latLongLocation="";

    private AccountHeader.Result mDrawerHeader;
    private Drawer.Result mDrawer;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupActionBar();
        setupDrawer(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_content, TodayFragment.newInstance(),TodayFragment.TODAY_FRAGMENT_TAG)
                    .commit();
        }
    }


    @Override // we need to get this result when we are at fragments and is open dialog for turning on GPS
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragmentT = getSupportFragmentManager().findFragmentByTag(TodayFragment.TODAY_FRAGMENT_TAG);
        if(fragmentT != null) {
            fragmentT.onActivityResult(requestCode, resultCode, data);
        }

        Fragment fragmentF = getSupportFragmentManager().findFragmentByTag(ForecastFragment.FORECAST_FRAGMENT_TAG);
        if(fragmentF != null) {
            fragmentF.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.menu_settings) {
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }else if (id == R.id.menu_about) {
            new MaterialDialog.Builder(this)
                    .title(R.string.menu_about)
                    .content(R.string.dialog_about_content)
                    .positiveText(android.R.string.ok)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @DebugLog
    private void selectDrawerItem(int identifier) {
        switch (identifier){
            case 1:
                openTodayFragment();
                break;
            case 2:
                openForecastFragment();
                break;
        }
    }


    @DebugLog// if fragment is opened dont do anything
    private void openTodayFragment() {
        toolbar.setTitle(getString(R.string.drawer_today));

        Fragment containerFragment = getSupportFragmentManager().findFragmentByTag(TodayFragment.TODAY_FRAGMENT_TAG);
        if(containerFragment != null) { // this means that fragment is already in container
            return;
        }
        Fragment mFragment = TodayFragment.newInstance();
        showFragment(mFragment);

    }


    @DebugLog
    private void openForecastFragment() {
        toolbar.setTitle(getString(R.string.drawer_forecast));

        Fragment containerFragment = getSupportFragmentManager().findFragmentByTag(ForecastFragment.FORECAST_FRAGMENT_TAG);
        if(containerFragment != null) { // this means that fragment is already in container, dont show
            return;
        }
        Fragment mFragment = ForecastFragment.newInstance(latLongLocation);
        showFragment(mFragment);
    }


    @Override
    protected void onDestroy() {
        Crouton.cancelAllCroutons();
        super.onDestroy();
    }

    private void setupActionBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.drawer_today));
        setSupportActionBar(toolbar);
    }


    private void setupDrawer(Bundle savedInstanceState){
        final IProfile navigationProfile = new ProfileDrawerItem()
                .withName(getString(R.string.app_name)).withIcon(ContextCompat.getDrawable(this, R.mipmap.ic_launcher));

        mDrawerHeader = new AccountHeader()
                .withActivity(this)
                .addProfiles(navigationProfile)
                .withHeaderBackground(R.drawable.global_menu_bg)
                .withProfileImagesClickable(false)
                .withSelectionListEnabledForSingleProfile(false)
                .withSavedInstance(savedInstanceState)
                .build();

        mDrawer = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(mDrawerHeader) //set the AccountHeader we created earlier for the header
                .withSelectedItem(0)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_today).withIcon(R.drawable.ic_drawer_today_dark).withIdentifier(1).withCheckable(true),
                        new PrimaryDrawerItem().withName(R.string.drawer_forecast).withIcon(R.drawable.ic_drawer_forecast_dark).withIdentifier(2).withCheckable(true),
                        new DividerDrawerItem()
                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        //check if the drawerItem is set.
                        //there are different reasons for the drawerItem to be null
                        //--> click on the header
                        //--> click on the footer
                        //those items don't contain a drawerItem
                        if (drawerItem != null) {
                            selectDrawerItem(drawerItem.getIdentifier());
                        }
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();
    }


    /**
     * @param fragment Fragment that we want to show
     * */
    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment
        transaction.replace(R.id.container_content, fragment);
        // Commit the transaction
        transaction.commit();
    }

}
