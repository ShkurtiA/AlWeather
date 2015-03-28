package al.shkurti.weather.android.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import al.shkurti.weather.android.R;
import al.shkurti.weather.android.fragment.TodayFragment;
import hugo.weaving.DebugLog;


public class MainActivity extends ActionBarActivity {

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
                    .add(R.id.container_content, TodayFragment.newInstance())
                    .commit();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setupActionBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("MAndi");
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


    private void selectDrawerItem(int identifier) {
        switch (identifier){
            case 1:
                break;
            case 2:
                break;
        }
    }


    /**
     * Show Fragment, if its visible dont do anything TODO
     * */
    private void showFragment(){

    }

}
